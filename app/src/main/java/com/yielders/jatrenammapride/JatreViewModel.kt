package com.yielders.jatrenammapride

import androidx.lifecycle.ViewModel
import com.yielders.jatrenammapride.data.ChatMessage
import com.yielders.jatrenammapride.data.CulturalStory
import com.yielders.jatrenammapride.data.JatreEvent
import com.yielders.jatrenammapride.data.JatreRepository
import com.yielders.jatrenammapride.data.LostFoundPost
import com.yielders.jatrenammapride.data.ParkingZone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import java.util.Locale

class JatreViewModel : ViewModel() {
    private val repository = JatreRepository(viewModelScope)

    val events: StateFlow<List<JatreEvent>> = repository.events
    val stories: StateFlow<List<CulturalStory>> = repository.stories
    val parkingZones: StateFlow<List<ParkingZone>> = repository.parkingZones
    val mapLocations: StateFlow<List<MapLocation>> = repository.mapLocations
    val posts: StateFlow<List<LostFoundPost>> = repository.posts
    val emergencyContacts = repository.emergencyContacts
    val safetyGuidelines = repository.safetyGuidelines

    private val generativeModel = GenerativeModel(
        modelName = "gemini-3-flash-preview",
        apiKey = BuildConfig.GEMINI_API_KEY,
        generationConfig = generationConfig {
            temperature = 0.7f
            topK = 40
            topP = 0.95f
        }
    )

    private val _chatMessages = MutableStateFlow(
        listOf(ChatMessage("Namaskara! I am your Jatre Guide. How can I help you today?", false, repository.nowLabel()))
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    fun submitLostFound(category: String, description: String, contact: String): String? {
        if (description.trim().length < 10) return "Description must be at least 10 characters."
        if (!contact.matches(Regex("\\d{10}"))) return "Enter a valid 10 digit contact number."
        repository.submitLostFound(category, description, contact)
        return null
    }

    fun markResolved(id: String) {
        repository.markResolved(id)
    }

    fun sendChat(text: String) {
        val clean = text.trim()
        if (clean.isEmpty()) return
        
        _chatMessages.value = _chatMessages.value + ChatMessage(clean, true, repository.nowLabel())
        
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey.contains("your_")) {
            _chatMessages.value = _chatMessages.value + ChatMessage("Namaskara! I see you added your API key, but the app needs a 'Project Sync' and 'Rebuild' to activate it. Please Sync Gradle and Rebuild the project in Android Studio.", false, repository.nowLabel())
            return
        }

        viewModelScope.launch {
            _isTyping.value = true
            try {
                // Check if it's a simple local query first for speed
                val localAnswer = repository.answerQuestion(clean)
                val prompt = if (localAnswer.contains("don't know")) {
                    "You are a helpful guide for 'Jatre Namma Pride' festival in Karnataka. User asks: $clean. " +
                    "Answer in a friendly tone. Use local terms like 'Namaskara', 'Jatre', and 'Oota'. " +
                    "If you don't know the specifics, talk about the general beauty of village festivals like Dollu Kunitha dances, chariots, and the spirit of community (Annadasoha)."
                } else {
                    "You are a helpful guide for 'Jatre Namma Pride'. Context: $localAnswer. User asks: $clean. " +
                    "Provide a warm, concise answer using this context. Mention that the information is specific to this year's fair."
                }

                val response = generativeModel.generateContent(prompt)
                val answer = response.text ?: "I'm having trouble connecting to my knowledge base. Please try again."
                
                _chatMessages.value = _chatMessages.value + ChatMessage(answer, false, repository.nowLabel())
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Unknown error"
                val keyStatus = if (apiKey.isEmpty()) "Missing" else if (apiKey.length < 10) "Too short" else "Detected (${apiKey.take(4)}...)"
                _chatMessages.value = _chatMessages.value + ChatMessage("Namaskara. I'm having trouble connecting to Gemini. \n\nError: $errorMsg \nKey Status: $keyStatus \n\nTip: Please Ensure you have 'Synced Gradle' and 'Rebuilt' the project after adding the key to local.properties.", false, repository.nowLabel())
            } finally {
                _isTyping.value = false
            }
        }
    }
}
