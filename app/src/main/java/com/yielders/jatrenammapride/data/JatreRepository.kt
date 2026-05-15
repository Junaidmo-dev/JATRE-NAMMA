package com.yielders.jatrenammapride.data

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.callbackFlow

class JatreRepository(private val scope: CoroutineScope) {
    private val db = FirebaseFirestore.getInstance()

    // Real-time flows from Firestore
    val posts: StateFlow<List<LostFoundPost>> = createFlow("lost_found", sampleLostFound)
    val events: StateFlow<List<JatreEvent>> = createFlow("events", sampleEvents)
    val stories: StateFlow<List<CulturalStory>> = createFlow("stories", sampleStories)
    val parkingZones: StateFlow<List<ParkingZone>> = createFlow("parking", sampleParking)
    val mapLocations: StateFlow<List<MapLocation>> = createFlow("map_locations", sampleMapLocations)

    private inline fun <reified T : Any> createFlow(collectionPath: String, initial: List<T>): StateFlow<List<T>> {
        return callbackFlow {
            val subscription = db.collection(collectionPath)
                .addSnapshotListener { snapshot, error ->
                    if (error == null && snapshot != null) {
                        val items = snapshot.documents.mapNotNull { it.toObject<T>() }
                        trySend(if (items.isEmpty()) initial else items)
                    } else {
                        trySend(initial)
                    }
                }
            awaitClose { subscription.remove() }
        }.stateIn(
            scope = scope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = initial
        )
    }

    val emergencyContacts: List<EmergencyContact> = sampleContacts
    val safetyGuidelines: List<String> = listOf(
        "Stay hydrated and use shaded rest areas during peak afternoon hours.",
        "Keep children close and agree on a meeting point before entering crowded areas.",
        "Use designated entry, exit, and crossing points.",
        "Contact the nearest volunteer for medical, security, or lost item help.",
        "Avoid pushing near processions and follow public announcements."
    )

    fun submitLostFound(category: String, description: String, contact: String) {
        val id = UUID.randomUUID().toString()
        val post = LostFoundPost(
            id = id,
            category = category,
            description = description.trim(),
            contact = contact.trim(),
            status = "active",
            timestamp = System.currentTimeMillis()
        )
        db.collection("lost_found").document(id).set(post)
    }

    fun markResolved(id: String) {
        db.collection("lost_found").document(id).update("status", "resolved")
    }

    fun answerQuestion(question: String): String {
        val lower = question.lowercase(Locale.getDefault())
        return when {
            "schedule" in lower || "event" in lower ->
                "Today's key events include the Ratha Procession, folk dance, food mela, and evening deepotsava. Open Live Schedule for timings and crowd levels."
            "parking" in lower ->
                "North Parking - Zone A is the best starting point for four-wheelers. Use the Safety screen for directions and availability."
            "lost" in lower || "found" in lower ->
                "Open Lost & Found to post details with your contact number. Volunteers can mark posts as resolved once the person or item is returned."
            "map" in lower || "where" in lower ->
                "The Jatre Map highlights food stalls, toy stalls, first-aid posts, gates, and parking zones around the fair ground."
            else ->
                "Namaskara. I can help with schedules, parking, stories, safety, directions, and Lost & Found for Jatre Namma Pride."
        }
    }

    fun nowLabel(): String = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
}

private val sampleEvents = listOf(
    JatreEvent(
        id = "ev_001",
        name = "Grand Rathotsava (Chariot)",
        time = "10:30 AM",
        location = "Main Temple Street",
        description = "The main attraction where the deity's chariot is pulled through the streets by thousands. A symbol of unity and devotion.",
        status = EventStatus.Upcoming,
        crowdLevel = CrowdLevel.High,
        significance = "Centuries-old tradition marking the peak of the festival."
    ),
    JatreEvent(
        id = "ev_002",
        name = "Kusthi Habba (Traditional Wrestling)",
        time = "2:00 PM",
        location = "Garadi Mane Ground",
        description = "A fierce display of strength and technique by local pehlwans. One of the oldest sports traditions in Karnataka.",
        status = EventStatus.Upcoming,
        crowdLevel = CrowdLevel.High,
        significance = "Honoring the physical prowess and discipline of village youth."
    ),
    JatreEvent(
        id = "ev_003",
        name = "Yakshagana: Dashavatara",
        time = "9:00 PM - Late Night",
        location = "Kala Vedike (Main Stage)",
        description = "Experience the legendary folk theater with elaborate costumes, powerful music, and epic storytelling.",
        status = EventStatus.Upcoming,
        crowdLevel = CrowdLevel.Medium,
        significance = "Preserving the oral epics and traditional art forms of the region."
    ),
    JatreEvent(
        id = "ev_004",
        name = "Krishi Mela (Agri-Expo)",
        time = "All Day",
        location = "Agriculture Office Compound",
        description = "Showcasing the best local harvests, traditional seeds, and modern farming tools. includes awards for best livestock.",
        status = EventStatus.Ongoing,
        crowdLevel = CrowdLevel.Medium,
        significance = "Celebrating the backbone of our village: the farmers."
    ),
    JatreEvent(
        id = "ev_005",
        name = "Dollu Kunitha Procession",
        time = "4:30 PM",
        location = "Mahadwara to Temple",
        description = "Rhythmic drum beats and high-energy dance performed by the Kuruba community devotees.",
        status = EventStatus.Upcoming,
        crowdLevel = CrowdLevel.High,
        significance = "Symbolizing the power of rhythm and community celebration."
    )
)

private val sampleLostFound = listOf(
    LostFoundPost(
        id = "pl_001",
        category = "Person",
        description = "Child wearing a green shirt last seen near the wrestling pit.",
        contact = "9876543210",
        status = "active",
        timestamp = System.currentTimeMillis() - 3600000
    ),
    LostFoundPost(
        id = "pl_002",
        category = "Item",
        description = "Found a bunch of keys near the North Parking gate.",
        contact = "9845012345",
        status = "resolved",
        timestamp = System.currentTimeMillis() - 7200000
    )
)

private val sampleStories = listOf(
    CulturalStory(
        id = "st_001",
        title = "The Legend of the Village Deity",
        excerpt = "How the temple came to be and the miracles witnessed by ancestors.",
        fullText = "Legend says that the deity appeared in a local farmer's dream 500 years ago, asking for a simple shrine. Since then, the village has never seen a drought during the Jatre season. The idol is said to be self-manifested (Swayambhu).",
        imageLabel = "Sacred Sanctum",
        timestamp = 101
    ),
    CulturalStory(
        id = "st_002",
        title = "Art of the Weaver",
        excerpt = "The story of the unique handloom patterns seen at our Jatre.",
        fullText = "The local weavers spend months preparing for this fair. Their unique 'Jatre-Special' patterns use natural dyes from forest barks and flowers, a technique passed down through ten generations.",
        imageLabel = "Weavers Colony",
        timestamp = 102
    ),
    CulturalStory(
        id = "st_003",
        title = "The Healing Kalyani",
        excerpt = "Why thousands take a holy dip in the temple tank.",
        fullText = "The Kalyani (Temple Tank) is believed to be connected to an ancient underground spring. Pilgrims take a ritual dip here before entering the temple, a practice believed to purify the soul and heal ailments.",
        imageLabel = "Temple Kalyani",
        timestamp = 103
    )
)

private val sampleMapLocations = listOf(
    MapLocation("ml_001", "Food Street (Oota)", "Food", 15.3650, 75.1245, "Authentic Jolada Rotti, Yennegayi, and local sweets."),
    MapLocation("ml_002", "Wrestling Pit (Akhada)", "Sports", 15.3643, 75.1241, "Where the Kusthi Habba matches take place."),
    MapLocation("ml_003", "Artisan Stalls", "Toy", 15.3655, 75.1250, "Traditional wooden toys, clay pots, and handlooms."),
    MapLocation("ml_004", "Emergency Medical Post", "First Aid", 15.3648, 75.1236, "Doctors and ambulances on standby."),
    MapLocation("ml_005", "North Parking Zone", "Parking", 15.3640, 75.1249, "Main parking for buses and four-wheelers.")
)

private val sampleParking = listOf(
    ParkingZone("North Parking - Zone A", "200 m away", "Available", "4-wheeler"),
    ParkingZone("East Parking - Zone B", "420 m away", "Available", "2-wheeler"),
    ParkingZone("South School Ground", "650 m away", "Full", "4-wheeler")
)

private val sampleContacts = listOf(
    EmergencyContact("Medical Emergency", "First Aid Center", "108"),
    EmergencyContact("Police / Security", "Jatre Security Desk", "100"),
    EmergencyContact("Lost & Found Helpdesk", "Volunteer Counter", "9876543210"),
    EmergencyContact("Fire Safety", "Fire Control Room", "101")
)
