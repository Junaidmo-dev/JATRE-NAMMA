package com.yielders.jatrenammapride.data

enum class EventStatus(val label: String) {
    Ongoing("Ongoing"),
    Upcoming("Upcoming"),
    Completed("Completed")
}

enum class CrowdLevel(val label: String, val progress: Float) {
    Low("Low", 0.25f),
    Medium("Medium", 0.6f),
    High("High", 0.95f)
}

data class JatreEvent(
    val id: String = "",
    val name: String = "",
    val time: String = "",
    val location: String = "",
    val description: String = "",
    val status: EventStatus = EventStatus.Upcoming,
    val crowdLevel: CrowdLevel = CrowdLevel.Low,
    val significance: String = ""
)

data class LostFoundPost(
    val id: String = "",
    val category: String = "",
    val description: String = "",
    val contact: String = "",
    val status: String = "active",
    val timestamp: Long = 0L
)

data class MapLocation(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val description: String = ""
)

data class CulturalStory(
    val id: String = "",
    val title: String = "",
    val excerpt: String = "",
    val fullText: String = "",
    val imageLabel: String = "",
    val timestamp: Long = 0L
)

data class ParkingZone(
    val name: String = "",
    val distance: String = "",
    val capacity: String = "",
    val type: String = ""
)

data class EmergencyContact(
    val category: String = "",
    val name: String = "",
    val phone: String = ""
)

data class ChatMessage(
    val text: String = "",
    val fromUser: Boolean = false,
    val timestamp: String = ""
)
