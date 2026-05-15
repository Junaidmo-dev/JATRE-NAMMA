# Jatre-Namma Pride - Complete Development SOP

## Standard Operating Procedure for Android Development

**Project:** Jatre-Namma Pride - Digital Guide to Village Fair  
**Platform:** Android  
**Timeline:** 3 Weeks  
**Developer:** Ready for AI-Assisted Development  

---

## 📋 TABLE OF CONTENTS

1. Project Overview & Architecture
2. Screen-by-Screen Development Prompts
3. Screen Flow & Navigation Map
4. Backend & Database Structure
5. API Integration Guide
6. UI/UX Design System
7. Testing & Quality Checklist
8. Deployment Instructions

---

## 1. PROJECT OVERVIEW & ARCHITECTURE

### 1.1 App Architecture Pattern
**Pattern:** MVVM (Model-View-ViewModel)

**Structure:**
```
app/
├── ui/
│   ├── splash/
│   ├── home/
│   ├── schedule/
│   ├── lostandfound/
│   ├── map/
│   ├── stories/
│   ├── safety/
│   └── chatbot/
├── data/
│   ├── model/
│   ├── repository/
│   └── remote/
├── utils/
└── viewmodel/
```

### 1.2 Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Kotlin | 1.9+ | Primary language |
| Android SDK | API 24+ | Platform |
| Firebase Realtime DB | Latest | Live schedule & Lost&Found |
| Firebase Firestore | Latest | Cultural stories |
| Firebase Storage | Latest | Image storage |
| Firebase Cloud Messaging | Latest | Push notifications |
| Google Maps SDK | Latest | Jatre map |
| Gemini API | 1.5 | AI chatbot |
| Glide | 4.16+ | Image loading |

### 1.3 Firebase Collections Structure

**Realtime Database:**
```json
{
  "schedule": {
    "event_001": {
      "event_name": "Ratha Procession",
      "time": "10:00 AM - 12:00 PM",
      "status": "ongoing|upcoming|completed",
      "location": "Main Ground",
      "description": "...",
      "crowd_level": "high|medium|low",
      "significance": "..."
    }
  },
  "lost_and_found": {
    "post_001": {
      "category": "person|item",
      "description": "...",
      "photo_url": "...",
      "contact": "9876543210",
      "status": "active|resolved",
      "timestamp": 1234567890
    }
  }
}
```

**Firestore:**
```
cultural_stories/
  └── story_001
      ├── title: "Legend of Namma Jatre"
      ├── image_url: "..."
      ├── description: "..."
      └── timestamp: ...
```

---

## 2. SCREEN-BY-SCREEN DEVELOPMENT PROMPTS

### 🎯 SCREEN 1: SPLASH SCREEN

**File:** `SplashActivity.kt`

**AI Development Prompt:**
```
Create a Splash Screen for Jatre-Namma Pride Android app with these specifications:

REQUIREMENTS:
- Display app logo centered on screen
- Background: Gradient from saffron orange (#FF9933) to earthy green (#138808)
- Add traditional Rangoli pattern as watermark (30% opacity)
- Auto-navigate to Home screen after 2 seconds
- Check Firebase connection status during splash
- Show error dialog if no internet connection

DEPENDENCIES:
- Material Design 3
- Firebase SDK
- Kotlin Coroutines for delay

LAYOUT STRUCTURE:
- ConstraintLayout as root
- ImageView for logo (200dp x 200dp)
- TextView for app name below logo (Kannada + English)
- ProgressBar at bottom (indeterminate)

CODE REQUIREMENTS:
- Use Handler.postDelayed for navigation
- Implement NetworkUtil to check connectivity
- Add smooth fade-in animation for logo
- Status bar color matches gradient top color

EDGE CASES:
- Handle Firebase initialization failure
- Show retry option if no network
- Prevent back button from closing app during splash
```

**Navigation:** Splash → Home (after 2s)

---

### 🎯 SCREEN 2: HOME / DASHBOARD

**File:** `HomeActivity.kt`

**AI Development Prompt:**
```
Create the Home Dashboard screen for Jatre-Namma Pride with these specifications:

REQUIREMENTS:
- Bottom Navigation with 5 tabs: Schedule, Lost&Found, Map, Stories, Safety
- Top AppBar with app name and chatbot icon (floating action button)
- Grid layout (2 columns) showing feature cards:
  1. Live Schedule (with "3 ongoing" badge)
  2. Lost & Found (with post count)
  3. Jatre Map
  4. Cultural Stories
  5. Parking & Safety
  6. AI Assistant

DESIGN:
- Card background: White with 8dp elevation
- Card corner radius: 16dp
- Each card has icon (60dp), title, subtitle
- Use Material 3 CardView
- Accent color: Saffron orange (#FF9933)
- Text color: Dark brown (#3E2723)

FUNCTIONALITY:
- onClick for each card navigates to respective screen
- Floating chatbot button (bottom-right) opens bottom sheet
- Pull-to-refresh to reload Firebase data
- Show ProgressBar while loading
- Display "Welcome to Namma Jatre" greeting at top

DEPENDENCIES:
- RecyclerView with GridLayoutManager (span=2)
- Material BottomNavigationView
- Firebase Realtime Database listener for live counts
- Glide for icon loading

NAVIGATION:
- Schedule Card → LiveScheduleActivity
- Lost&Found Card → LostAndFoundActivity
- Map Card → MapActivity
- Stories Card → CulturalStoriesActivity
- Safety Card → SafetyInfoActivity
- Chatbot FAB → ChatBottomSheet

DATA BINDING:
- Fetch event count from /schedule
- Fetch Lost&Found post count from /lost_and_found
- Update badges in real-time
```

**Navigation Map:**
```
Home
├── Live Schedule
├── Lost & Found
├── Map
├── Cultural Stories
├── Safety Info
└── Chatbot (Bottom Sheet)
```

---

### 🎯 SCREEN 3: LIVE SCHEDULE

**File:** `LiveScheduleActivity.kt`

**AI Development Prompt:**
```
Create the Live Schedule screen with real-time event tracking:

REQUIREMENTS:
- RecyclerView list of all Jatre events
- Each item shows:
  * Event name (bold, 18sp)
  * Time (12sp, grey)
  * Location within fair (10sp)
  * Status badge (Ongoing/Upcoming/Completed)
- Highlight ONGOING event with saffron background
- Auto-scroll to current event on screen load
- Real-time updates from Firebase Realtime DB

ITEM LAYOUT:
- CardView with 12dp margin
- Status badge as colored chip (top-right corner)
  * Ongoing: Saffron background, white text
  * Upcoming: Green background, white text
  * Completed: Grey background, dark text
- Event icon on left (e.g., chariot for Ratha)
- Click item → opens Event Detail screen

FIREBASE INTEGRATION:
- Path: /schedule
- Use ValueEventListener for real-time sync
- Sort by time (ascending)
- Auto-update status based on current time

STATUS LOGIC:
- Compare current time with event time
- If current time between start-end → "ongoing"
- If current time before start → "upcoming"
- If current time after end → "completed"

EMPTY STATE:
- Show "No events scheduled" with illustration
- Display message: "Check back later for updates"

CODE STRUCTURE:
- ScheduleAdapter extends RecyclerView.Adapter
- ViewHolder pattern for item binding
- Data class: Event(name, time, status, location, description, crowd, significance)
- Use DiffUtil for efficient list updates

PERFORMANCE:
- Implement view recycling
- Load only visible items
- Cache images with Glide
- Handle 30+ items smoothly
```

**Navigation:** Schedule Item Click → Event Detail Screen

---

### 🎯 SCREEN 4: EVENT DETAIL VIEW

**File:** `EventDetailActivity.kt`

**AI Development Prompt:**
```
Create the Event Detail screen shown when user taps a schedule item:

REQUIREMENTS:
- Scrollable full-screen view
- Hero image at top (event-specific illustration)
- Event name as large title (24sp, bold)
- Time and location in subtitle row
- Crowd level indicator (visual meter: Low/Medium/High)
- Full description (body text, 14sp)
- Cultural significance section (expandable)
- "Set Reminder" button at bottom

LAYOUT:
- NestedScrollView as root
- Hero image height: 250dp
- CollapsingToolbarLayout for parallax effect
- Back button in toolbar
- Share button (top-right) to share event details

CROWD LEVEL UI:
- Progress bar with color coding:
  * Low: Green (0-33%)
  * Medium: Yellow (34-66%)
  * High: Red (67-100%)
- Icon + text label

REMINDER FUNCTIONALITY:
- Button click → Schedule Android notification
- Notification triggers 15 minutes before event start
- Use AlarmManager for scheduling
- Show Toast: "Reminder set for [Event Name]"

DATA SOURCE:
- Receive Event object via Intent from Schedule screen
- Field mapping:
  * event_name → Title
  * description → Body
  * significance → Expandable section
  * crowd_level → Progress value

ANIMATIONS:
- Fade-in animation for content
- Smooth scroll to description on open
- Collapse/expand animation for significance section

ERROR HANDLING:
- If event data missing → show error message
- Disable reminder button if event already passed
```

**Navigation:** Back → Returns to Schedule Screen

---

### 🎯 SCREEN 5: LOST & FOUND BOARD

**File:** `LostAndFoundActivity.kt`

**AI Development Prompt:**
```
Create the Lost & Found community board with post and view functionality:

REQUIREMENTS:
Two tabs (TabLayout):
1. View Feed (RecyclerView of posts)
2. Post Item (Form to submit new entry)

VIEW FEED TAB:
- RecyclerView showing all Lost&Found posts
- Each card shows:
  * Category badge (Person / Item)
  * Photo thumbnail (if uploaded)
  * Description (max 2 lines, ellipsize)
  * Contact number (masked: 98765XXXXX)
  * Timestamp (e.g., "2 hours ago")
  * Status chip (Active / Resolved)
  * "Mark as Resolved" button (only if Active)
- Pull-to-refresh
- Empty state: "No posts yet. Be the first to help!"

POST ITEM TAB FORM:
- Category dropdown (Person / Item)
- Description EditText (multiline, max 200 chars)
- Photo upload button:
  * Opens gallery or camera
  * Compress to <500KB before upload
  * Show preview thumbnail
- Contact number EditText (10 digits, validation)
- Submit button
- Clear button

FIREBASE INTEGRATION:
- Path: /lost_and_found
- On submit:
  1. Validate all fields
  2. Upload photo to Firebase Storage
  3. Get download URL
  4. Push post to Realtime DB with timestamp
- On feed load:
  1. Fetch all posts sorted by timestamp (latest first)
  2. Listen for real-time updates (ChildEventListener)

IMAGE HANDLING:
- Use BitmapFactory to compress image
- Target quality: 80
- Max dimension: 1024px
- Upload to: /lost_and_found_images/{timestamp}_{randomId}.jpg
- Show ProgressDialog during upload

MARK AS RESOLVED:
- Update status field in Firebase: "resolved"
- Change chip color to grey
- Show Toast: "Marked as resolved. Thank you!"
- Disable button after marking

VALIDATION:
- Description: min 10 characters
- Contact: exactly 10 digits
- Show inline errors (TextInputLayout)

DATA MODEL:
```kotlin
data class LostFoundPost(
    val id: String = "",
    val category: String = "item", // "person" | "item"
    val description: String = "",
    val photo_url: String = "",
    val contact: String = "",
    val status: String = "active", // "active" | "resolved"
    val timestamp: Long = System.currentTimeMillis()
)
```

ADAPTER:
- LostFoundAdapter with ViewHolder
- Bind data using Glide for images
- Handle click on "Mark as Resolved" button
```

**Navigation:** None (self-contained)

---

### 🎯 SCREEN 6: JATRE MAP

**File:** `MapActivity.kt`

**AI Development Prompt:**
```
Create an interactive Google Map showing the Jatre fair ground:

REQUIREMENTS:
- Full-screen Google MapView
- Custom markers for different locations:
  * Food Stalls (red marker with food icon)
  * Toy Stalls (blue marker with toy icon)
  * Parking Zones (green marker with P icon)
  * First-Aid Posts (orange marker with + icon)
  * Entry/Exit Gates (purple marker with gate icon)
- Bottom sheet showing location details on marker click
- "My Location" button (top-right corner)
- Legend showing marker types (bottom-left)

MAP CONFIGURATION:
- Initial zoom level: 16
- Center: Jatre ground coordinates (use sample: 15.3647, 75.1240)
- Map type: Normal (satellite option in menu)
- Enable zoom controls and compass

MARKER DATA:
Store in Firestore collection: /map_locations
```json
{
  "location_001": {
    "name": "Food Court 1",
    "type": "food", // food|toy|parking|firstaid|gate
    "latitude": 15.3650,
    "longitude": 75.1245,
    "description": "North Indian & South Indian food stalls"
  }
}
```

CUSTOM MARKERS:
- Use BitmapDescriptorFactory.fromResource()
- Marker icons stored in res/drawable:
  * ic_marker_food.png
  * ic_marker_toy.png
  * ic_marker_parking.png
  * ic_marker_firstaid.png
  * ic_marker_gate.png

BOTTOM SHEET (on marker click):
- Location name (bold, 16sp)
- Type badge
- Description
- "Get Directions" button → opens Google Maps app with directions

MY LOCATION:
- Request location permission (runtime)
- Show blue dot on map
- Center map on user location when button clicked

LEGEND:
- Semi-transparent overlay (bottom-left)
- List of marker types with colored dots
- Toggle visibility with expand/collapse

DEPENDENCIES:
- Google Maps SDK for Android
- Play Services Location
- Firebase Firestore for marker data

PERMISSIONS:
- ACCESS_FINE_LOCATION
- ACCESS_COARSE_LOCATION

ERROR HANDLING:
- Handle permission denial → show rationale dialog
- Handle no GPS → show "Enable location services" message
- Handle map load failure → show retry button
```

**Navigation:** "Get Directions" → Opens external Google Maps app

---

### 🎯 SCREEN 7: PARKING & SAFETY INFO

**File:** `SafetyInfoActivity.kt`

**AI Development Prompt:**
```
Create a Safety Information screen with parking zones and emergency contacts:

REQUIREMENTS:
Two sections in vertical ScrollView:
1. Parking Zones
2. Emergency Contacts & Safety Guidelines

SECTION 1: PARKING ZONES
- Show nearest parking zone based on user location
- Display as cards:
  * Zone name (e.g., "North Parking - Zone A")
  * Distance from user (e.g., "200m away")
  * Capacity status (Available / Full)
  * Icon showing parking type (2-wheeler / 4-wheeler)
- "Navigate" button opens map with directions

SECTION 2: EMERGENCY CONTACTS
- Expandable list (ExpandableListView or RecyclerView)
- Categories:
  * Medical Emergency
  * Police / Security
  * Lost & Found Helpdesk
  * Fire Safety
- Each item shows:
  * Contact name/department
  * Phone number (clickable to dial)
  * Call icon button
- Click phone number → initiates call

SAFETY GUIDELINES:
- Below emergency contacts
- Bullet-point list in CardView:
  * Stay hydrated
  * Keep children in sight
  * Avoid crowded areas during peak hours
  * Use designated crossing points
  * Report suspicious activity
- Icon for each guideline

DATA SOURCE:
Firestore collection: /safety_info
```json
{
  "parking": [
    {
      "zone_name": "North Parking - Zone A",
      "latitude": 15.3655,
      "longitude": 75.1250,
      "capacity": "available", // "available" | "full"
      "type": "4-wheeler"
    }
  ],
  "emergency_contacts": [
    {
      "category": "Medical",
      "name": "First Aid Center",
      "phone": "108"
    }
  ],
  "guidelines": [
    "Stay hydrated during visit",
    "Keep children in sight at all times",
    ...
  ]
}
```

LOCATION LOGIC:
- Use FusedLocationProviderClient to get current location
- Calculate distance using Location.distanceBetween()
- Show nearest parking zone first (sort by distance)

CALL FUNCTIONALITY:
- Intent with ACTION_DIAL
- Pre-fill phone number
- User confirms to call

UI DESIGN:
- Header with alert icon
- Cards with 16dp elevation
- Emergency contacts in red accent color
- Phone icon buttons in green
- Parking status indicator:
  * Available: Green dot
  * Full: Red dot

EMPTY STATE:
- If no parking data → "No parking info available"
- If location disabled → "Enable location to find nearest parking"
```

**Navigation:** "Navigate" button → Opens Map Screen with parking zone highlighted

---

### 🎯 SCREEN 8: CULTURAL STORIES

**File:** `CulturalStoriesActivity.kt`

**AI Development Prompt:**
```
Create a Cultural Stories section preserving Jatre legends and history:

REQUIREMENTS:
- Vertical scrolling list of story cards (RecyclerView)
- Each card shows:
  * Hero image (full-width, 180dp height)
  * Story title (bold, 18sp)
  * Short excerpt (2 lines, grey text)
  * "Read More" button
- Click card → opens full story detail view

STORY CARD DESIGN:
- CardView with rounded corners (16dp)
- Hero image with gradient overlay (bottom)
- Title text overlaid on image (bottom-left)
- Excerpt below image
- "Read More" link (orange color)

DETAIL VIEW:
- Full-screen scrollable view (BottomSheetDialogFragment or new Activity)
- Hero image at top (full-width, 300dp)
- Title (24sp, bold)
- Full story text (14sp, line spacing 1.5)
- Share button (top-right) to share story text
- Close/Back button

DATA SOURCE:
Firestore collection: /cultural_stories
```json
{
  "story_001": {
    "title": "The Legend of Namma Jatre",
    "image_url": "https://...",
    "excerpt": "How the annual village fair began 300 years ago...",
    "full_text": "Long story text...",
    "timestamp": 1234567890
  }
}
```

FIRESTORE QUERY:
- Fetch all stories ordered by timestamp (oldest first)
- Limit to 20 stories initially
- Implement pagination (load more on scroll)

IMAGE LOADING:
- Use Glide with placeholder and error images
- Placeholder: Traditional motif pattern
- Error image: Default Jatre illustration
- Apply cross-fade transition

EMPTY STATE:
- If no stories → Show illustration with message
- "No stories available yet. Check back soon!"

SHARE FUNCTIONALITY:
- Intent.ACTION_SEND (type: text/plain)
- Share text: "[Title]\n\n[Full Text]\n\nShared from Jatre-Namma Pride app"

OFFLINE SUPPORT:
- Enable Firestore offline persistence
- Show cached stories when offline
- Display "Offline Mode" badge at top

ADAPTER:
```kotlin
class StoriesAdapter(
    private val stories: List<CulturalStory>,
    private val onItemClick: (CulturalStory) -> Unit
) : RecyclerView.Adapter<StoriesAdapter.ViewHolder>()

data class CulturalStory(
    val id: String = "",
    val title: String = "",
    val image_url: String = "",
    val excerpt: String = "",
    val full_text: String = "",
    val timestamp: Long = 0
)
```
```

**Navigation:** Story Card → Story Detail View (Fragment/Activity)

---

### 🎯 SCREEN 9: GENAI CHATBOT (Bottom Sheet)

**File:** `ChatBottomSheetFragment.kt`

**AI Development Prompt:**
```
Create an AI-powered chatbot bottom sheet using Gemini API:

REQUIREMENTS:
- BottomSheetDialogFragment (3/4 screen height)
- Chat interface with RecyclerView showing conversation
- Input field (EditText) at bottom with send button
- Support Kannada and English queries
- Context-aware responses about Jatre

CHAT UI:
- Two message types:
  1. User messages (right-aligned, saffron background)
  2. Bot responses (left-aligned, light grey background)
- Each message bubble shows:
  * Message text
  * Timestamp (small, grey)
  * Avatar (user: default icon, bot: app logo)
- Auto-scroll to latest message

INPUT SECTION:
- EditText with hint: "Ask about the Jatre... (ಜಾತ್ರೆಯ ಬಗ್ಗೆ ಕೇಳಿ...)"
- Send button (icon: paper plane)
- Disable send when text is empty
- Show typing indicator when bot is responding

GEMINI API INTEGRATION:
- Use Gemini 1.5 Flash model
- System prompt with Jatre context:
```
You are a helpful assistant for the Jatre-Namma Pride app, a digital guide to village fairs (Jatre) in Karnataka.
Answer questions about:
- Event schedules and timings
- Location of stalls, parking, first-aid
- Cultural significance of events
- Safety guidelines
- Lost & Found process

Respond in the same language as the user query (Kannada or English).
Keep responses concise (max 100 words).
If you don't know, say "I don't have that information. Please check with the fair organizers."
```

API CALL FLOW:
1. User sends message → add to RecyclerView
2. Show "Bot is typing..." indicator
3. Call Gemini API with user message + system prompt
4. On response → add bot message to RecyclerView
5. Hide typing indicator

ERROR HANDLING:
- API call failure → show "Sorry, I couldn't process that. Please try again."
- Network error → "No internet connection. Check your network."
- Rate limit → "Too many requests. Please wait a moment."

SAMPLE QUERIES & RESPONSES:
- User: "Ratha elli ide?" (Where is the Ratha?)
  Bot: "ರಥೋತ್ಸವವು ಮುಖ್ಯ ಮೈದಾನದಲ್ಲಿ ಬೆಳಿಗ್ಗೆ 10:00 ರಿಂದ 12:00 ರವರೆಗೆ ನಡೆಯುತ್ತದೆ."
- User: "Where can I park?"
  Bot: "Parking is available at North Zone (200m) and South Zone (450m). Check the Map screen for directions."

DEPENDENCIES:
```gradle
implementation 'com.google.ai.client.generativeai:generativeai:0.1.2'
```

CODE STRUCTURE:
```kotlin
class ChatBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var messagesAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()
    private val geminiService = GenerativeModel("gemini-1.5-flash", apiKey)
    
    data class ChatMessage(
        val text: String,
        val isUser: Boolean,
        val timestamp: Long = System.currentTimeMillis()
    )
}
```

UI POLISH:
- Smooth animations for new messages
- Haptic feedback on send
- Clear chat button (menu icon, top-right)
- Collapse bottom sheet on outside tap
```

**Navigation:** Opened from Home FAB, dismissed by swipe down

---

## 3. SCREEN FLOW & NAVIGATION MAP

### 3.1 Complete Navigation Architecture

```
┌─────────────────┐
│  SPLASH SCREEN  │ (2 seconds)
└────────┬────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│           HOME / DASHBOARD               │◄─── Back from all screens
│  ┌────────┬────────┬────────┬────────┐  │
│  │Schedule│Lost&Fnd│  Map   │Stories │  │
│  └───┬────┴───┬────┴───┬────┴───┬────┘  │
└──────┼────────┼────────┼────────┼────────┘
       │        │        │        │
       ▼        ▼        ▼        ▼
   ┌───────┐ ┌──────┐ ┌────┐ ┌─────────┐
   │Schedule│ │Lost& │ │Map │ │Stories  │
   │ List  │ │Found │ │View│ │  List   │
   └───┬───┘ └──────┘ └─┬──┘ └────┬────┘
       │                 │         │
       ▼                 ▼         ▼
   ┌────────┐      ┌─────────┐ ┌────────┐
   │ Event  │      │Parking &│ │ Story  │
   │ Detail │      │ Safety  │ │ Detail │
   └────────┘      └─────────┘ └────────┘
   
   ┌──────────────────────────────────┐
   │  CHATBOT (Bottom Sheet)          │
   │  Accessible from Home FAB        │
   └──────────────────────────────────┘
```

### 3.2 Activity/Fragment Breakdown

| Screen | Type | Launch Mode | Parent |
|--------|------|-------------|--------|
| SplashActivity | Activity | singleTask | None |
| HomeActivity | Activity | singleTop | Splash |
| LiveScheduleActivity | Activity | standard | Home |
| EventDetailActivity | Activity | standard | Schedule |
| LostAndFoundActivity | Activity | standard | Home |
| MapActivity | Activity | standard | Home |
| CulturalStoriesActivity | Activity | standard | Home |
| StoryDetailActivity | Activity | standard | Stories |
| SafetyInfoActivity | Activity | standard | Home (or Map) |
| ChatBottomSheetFragment | Fragment | - | Home |

### 3.3 Intent Parameters

**Schedule → Event Detail:**
```kotlin
val intent = Intent(this, EventDetailActivity::class.java)
intent.putExtra("EVENT_ID", event.id)
intent.putExtra("EVENT_NAME", event.name)
intent.putExtra("EVENT_TIME", event.time)
intent.putExtra("EVENT_DESCRIPTION", event.description)
intent.putExtra("EVENT_CROWD", event.crowd_level)
startActivity(intent)
```

**Map → Safety Info (with zone):**
```kotlin
val intent = Intent(this, SafetyInfoActivity::class.java)
intent.putExtra("HIGHLIGHT_ZONE", "parking_north")
startActivity(intent)
```

**Stories → Story Detail:**
```kotlin
val intent = Intent(this, StoryDetailActivity::class.java)
intent.putExtra("STORY_ID", story.id)
intent.putExtra("STORY_TITLE", story.title)
intent.putExtra("STORY_IMAGE", story.image_url)
intent.putExtra("STORY_TEXT", story.full_text)
startActivity(intent)
```

---

## 4. BACKEND & DATABASE STRUCTURE

### 4.1 Firebase Realtime Database Schema

```json
{
  "jatre": {
    "schedule": {
      "event_001": {
        "event_name": "Ratha Procession",
        "time": "10:00 AM - 12:00 PM",
        "start_time_epoch": 1234567890,
        "end_time_epoch": 1234574890,
        "status": "ongoing",
        "location": "Main Ground",
        "description": "The grand chariot procession carrying the deity around the village.",
        "crowd_level": "high",
        "significance": "This ritual has been performed for 300 years...",
        "icon_url": "https://..."
      },
      "event_002": {
        "event_name": "Traditional Wrestling (Kushti)",
        "time": "2:00 PM - 4:00 PM",
        "start_time_epoch": 1234581290,
        "end_time_epoch": 1234588490,
        "status": "upcoming",
        "location": "Wrestling Arena",
        "description": "Watch local wrestlers compete in traditional mud wrestling.",
        "crowd_level": "medium",
        "significance": "Wrestling has been part of Jatre culture since...",
        "icon_url": "https://..."
      },
      "event_003": {
        "event_name": "Yakshagana Drama",
        "time": "7:00 PM - 10:00 PM",
        "start_time_epoch": 1234599690,
        "end_time_epoch": 1234610490,
        "status": "upcoming",
        "location": "Open Air Theater",
        "description": "Classical dance-drama depicting mythological stories.",
        "crowd_level": "high",
        "significance": "Yakshagana is Karnataka's rich cultural heritage...",
        "icon_url": "https://..."
      }
    },
    
    "lost_and_found": {
      "post_001": {
        "category": "person",
        "description": "Lost child, 5 years old, wearing blue shirt, last seen near food stalls",
        "photo_url": "https://storage.googleapis.com/.../lost_child_001.jpg",
        "contact": "9876543210",
        "status": "active",
        "timestamp": 1234567890,
        "posted_by": "Anonymous"
      },
      "post_002": {
        "category": "item",
        "description": "Found ladies purse near parking zone, contains ID and some cash",
        "photo_url": "https://storage.googleapis.com/.../found_purse_002.jpg",
        "contact": "9123456780",
        "status": "resolved",
        "timestamp": 1234560000,
        "posted_by": "Anonymous"
      }
    },
    
    "config": {
      "fair_name": "Namma Jatre 2024",
      "fair_dates": "15-17 March 2024",
      "location": "Hubballi, Karnataka",
      "emergency_phone": "108",
      "helpdesk_phone": "9876543210"
    }
  }
}
```

### 4.2 Firestore Collections

**Collection: `cultural_stories`**
```javascript
{
  // Document ID: story_001
  "title": "The Legend of Namma Jatre",
  "image_url": "https://storage.googleapis.com/.../story_img_001.jpg",
  "excerpt": "How the annual village fair began 300 years ago when a wandering sage blessed the village...",
  "full_text": "Long ago, in a small village in Karnataka, there lived a community plagued by drought and famine. One day, a sage arrived and performed rituals to please the rain gods. The village prospered, and to honor the sage, they started an annual fair. This tradition continues as Namma Jatre.",
  "timestamp": {
    "seconds": 1234567890,
    "nanoseconds": 0
  },
  "author": "Village Elders",
  "language": "en"
}

// Document ID: story_002
{
  "title": "ಜಾತ್ರೆಯ ಇತಿಹಾಸ",
  "image_url": "https://storage.googleapis.com/.../story_img_002.jpg",
  "excerpt": "ನಮ್ಮ ಹಳ್ಳಿಯ ಜಾತ್ರೆಯ 300 ವರ್ಷಗಳ ಇತಿಹಾಸ...",
  "full_text": "೩೦೦ ವರ್ಷಗಳ ಹಿಂದೆ, ನಮ್ಮ ಹಳ್ಳಿಗೆ ಒಬ್ಬ ಸಾಧು ಭೇಟಿ ನೀಡಿದರು...",
  "timestamp": {
    "seconds": 1234567900,
    "nanoseconds": 0
  },
  "author": "ಹಳ್ಳಿಯ ಹಿರಿಯರು",
  "language": "kn"
}
```

**Collection: `map_locations`**
```javascript
{
  // Document ID: location_001
  "name": "Food Court - North",
  "type": "food",
  "latitude": 15.3650,
  "longitude": 75.1245,
  "description": "North Indian, South Indian, and Chinese food stalls. 20+ vendors.",
  "icon": "food"
}

// Document ID: location_002
{
  "name": "Toy Stalls",
  "type": "toy",
  "latitude": 15.3648,
  "longitude": 75.1250,
  "description": "Traditional toys, games, and handicrafts for children.",
  "icon": "toy"
}

// Document ID: location_003
{
  "name": "Parking Zone - North",
  "type": "parking",
  "latitude": 15.3655,
  "longitude": 75.1250,
  "description": "500 vehicle capacity. Both 2-wheeler and 4-wheeler parking available.",
  "icon": "parking",
  "capacity": "available" // or "full"
}

// Document ID: location_004
{
  "name": "First-Aid Center",
  "type": "firstaid",
  "latitude": 15.3652,
  "longitude": 75.1242,
  "description": "24/7 medical assistance. Doctors and ambulance available.",
  "icon": "medical",
  "phone": "108"
}

// Document ID: location_005
{
  "name": "Main Entry Gate",
  "type": "gate",
  "latitude": 15.3647,
  "longitude": 75.1240,
  "description": "Primary entrance to the Jatre ground.",
  "icon": "gate"
}
```

**Collection: `safety_info`**
```javascript
{
  // Document ID: safety_config
  "emergency_contacts": [
    {
      "category": "Medical",
      "name": "First Aid Center",
      "phone": "108",
      "location_lat": 15.3652,
      "location_lng": 75.1242
    },
    {
      "category": "Police",
      "name": "Fair Security Control Room",
      "phone": "100",
      "location_lat": 15.3649,
      "location_lng": 75.1243
    },
    {
      "category": "Lost & Found",
      "name": "Helpdesk",
      "phone": "9876543210",
      "location_lat": 15.3647,
      "location_lng": 75.1241
    },
    {
      "category": "Fire",
      "name": "Fire Station",
      "phone": "101",
      "location_lat": 15.3651,
      "location_lng": 75.1238
    }
  ],
  
  "guidelines": [
    "Stay hydrated - drink plenty of water during your visit",
    "Keep children in sight at all times",
    "Avoid crowded areas during peak hours (12 PM - 3 PM)",
    "Use designated crossing points",
    "Report suspicious activity to security immediately",
    "Keep your belongings secure",
    "Follow parking attendant instructions",
    "Do not litter - use designated waste bins",
    "Respect cultural events and maintain silence during rituals",
    "In case of emergency, call 108 or visit the First-Aid center"
  ],
  
  "parking_zones": [
    {
      "zone_name": "North Parking - Zone A",
      "latitude": 15.3655,
      "longitude": 75.1250,
      "capacity": "available",
      "type": "4-wheeler",
      "distance_from_entry": "200m"
    },
    {
      "zone_name": "South Parking - Zone B",
      "latitude": 15.3640,
      "longitude": 75.1235,
      "capacity": "available",
      "type": "2-wheeler",
      "distance_from_entry": "300m"
    },
    {
      "zone_name": "East Parking - Zone C",
      "latitude": 15.3650,
      "longitude": 75.1255,
      "capacity": "full",
      "type": "4-wheeler",
      "distance_from_entry": "450m"
    }
  ]
}
```

### 4.3 Firebase Storage Structure

```
gs://jatre-namma-pride.appspot.com/
├── lost_and_found_images/
│   ├── 1234567890_abc123.jpg
│   ├── 1234567900_def456.jpg
│   └── ...
├── cultural_stories/
│   ├── story_img_001.jpg
│   ├── story_img_002.jpg
│   └── ...
├── event_icons/
│   ├── ratha_icon.png
│   ├── wrestling_icon.png
│   ├── drama_icon.png
│   └── ...
└── map_markers/
    ├── ic_marker_food.png
    ├── ic_marker_toy.png
    ├── ic_marker_parking.png
    ├── ic_marker_firstaid.png
    └── ic_marker_gate.png
```

### 4.4 Firebase Rules

**Realtime Database Rules:**
```json
{
  "rules": {
    "jatre": {
      "schedule": {
        ".read": true,
        ".write": false // Admin only (future)
      },
      "lost_and_found": {
        ".read": true,
        ".write": true, // Anyone can post (prototype)
        "$post_id": {
          ".validate": "newData.hasChildren(['category', 'description', 'contact', 'status', 'timestamp'])"
        }
      },
      "config": {
        ".read": true,
        ".write": false
      }
    }
  }
}
```

**Firestore Rules:**
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Cultural Stories - read-only for users
    match /cultural_stories/{story} {
      allow read: if true;
      allow write: if false; // Admin only (future)
    }
    
    // Map Locations - read-only
    match /map_locations/{location} {
      allow read: if true;
      allow write: if false;
    }
    
    // Safety Info - read-only
    match /safety_info/{doc} {
      allow read: if true;
      allow write: if false;
    }
  }
}
```

**Storage Rules:**
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    
    // Lost & Found images - anyone can upload
    match /lost_and_found_images/{imageId} {
      allow read: if true;
      allow write: if request.resource.size < 500 * 1024 // Max 500KB
                   && request.resource.contentType.matches('image/.*');
    }
    
    // Other folders - read-only
    match /{allPaths=**} {
      allow read: if true;
      allow write: if false;
    }
  }
}
```

---

## 5. API INTEGRATION GUIDE

### 5.1 Firebase Setup

**Step 1: Create Firebase Project**
1. Go to https://console.firebase.google.com
2. Create new project: "Jatre-Namma-Pride"
3. Enable Google Analytics (optional)
4. Add Android app:
   - Package name: `com.yielders.jatrenammapride`
   - Download `google-services.json`
   - Place in `app/` directory

**Step 2: Add Dependencies**
`build.gradle` (Project level):
```gradle
buildscript {
    dependencies {
        classpath 'com.google.gms:google-services:4.4.0'
    }
}
```

`build.gradle` (App level):
```gradle
plugins {
    id 'com.google.gms.google-services'
}

dependencies {
    // Firebase BOM
    implementation platform('com.google.firebase:firebase-bom:32.7.0')
    
    // Firebase products
    implementation 'com.google.firebase:firebase-database-ktx'
    implementation 'com.google.firebase:firebase-firestore-ktx'
    implementation 'com.google.firebase:firebase-storage-ktx'
    implementation 'com.google.firebase:firebase-messaging-ktx'
    
    // Coroutines for async operations
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3'
}
```

**Step 3: Initialize Firebase**
`Application` class:
```kotlin
class JatreApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        
        // Enable offline persistence
        val firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        FirebaseFirestore.getInstance().firestoreSettings = firestoreSettings
    }
}
```

`AndroidManifest.xml`:
```xml
<application
    android:name=".JatreApp"
    ...>
```

### 5.2 Realtime Database Integration

**Repository Pattern:**
```kotlin
class ScheduleRepository {
    private val database = Firebase.database.reference
    
    fun getSchedule(callback: (List<Event>) -> Unit) {
        database.child("jatre/schedule")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val events = mutableListOf<Event>()
                    snapshot.children.forEach { child ->
                        child.getValue(Event::class.java)?.let { events.add(it) }
                    }
                    callback(events)
                }
                
                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error: ${error.message}")
                }
            })
    }
    
    fun postLostAndFound(post: LostFoundPost, callback: (Boolean) -> Unit) {
        val postId = database.child("jatre/lost_and_found").push().key ?: return
        database.child("jatre/lost_and_found/$postId")
            .setValue(post)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
    
    fun markAsResolved(postId: String, callback: (Boolean) -> Unit) {
        database.child("jatre/lost_and_found/$postId/status")
            .setValue("resolved")
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}
```

### 5.3 Firestore Integration

**Stories Repository:**
```kotlin
class StoriesRepository {
    private val firestore = Firebase.firestore
    
    suspend fun getCulturalStories(): List<CulturalStory> = suspendCoroutine { continuation ->
        firestore.collection("cultural_stories")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val stories = documents.map { it.toObject(CulturalStory::class.java) }
                continuation.resume(stories)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}
```

**Map Locations Repository:**
```kotlin
class MapRepository {
    private val firestore = Firebase.firestore
    
    suspend fun getMapLocations(): List<MapLocation> = suspendCoroutine { continuation ->
        firestore.collection("map_locations")
            .get()
            .addOnSuccessListener { documents ->
                val locations = documents.map { it.toObject(MapLocation::class.java) }
                continuation.resume(locations)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}
```

### 5.4 Firebase Storage Integration

**Image Upload Utility:**
```kotlin
class ImageUploadUtil {
    private val storage = Firebase.storage.reference
    
    fun uploadLostFoundImage(
        imageUri: Uri,
        onProgress: (Int) -> Unit,
        onComplete: (String?) -> Unit
    ) {
        // Compress image first
        val compressedBitmap = compressImage(imageUri)
        val baos = ByteArrayOutputStream()
        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        val data = baos.toByteArray()
        
        // Generate unique filename
        val filename = "${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg"
        val imageRef = storage.child("lost_and_found_images/$filename")
        
        // Upload
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
            onProgress(progress)
        }.addOnSuccessListener {
            // Get download URL
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onComplete(uri.toString())
            }
        }.addOnFailureListener {
            onComplete(null)
        }
    }
    
    private fun compressImage(uri: Uri): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        // Calculate sample size
        options.inSampleSize = calculateInSampleSize(options, 1024, 1024)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeStream(
            context.contentResolver.openInputStream(uri),
            null,
            options
        ) ?: throw Exception("Failed to decode image")
    }
    
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while ((halfHeight / inSampleSize) >= reqHeight
                && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
```

### 5.5 Google Maps Integration

**Setup:**
1. Enable Maps SDK in Google Cloud Console
2. Get API Key
3. Add to `AndroidManifest.xml`:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_MAPS_API_KEY_HERE" />
```

**Add Dependency:**
```gradle
implementation 'com.google.android.gms:play-services-maps:18.2.0'
implementation 'com.google.android.gms:play-services-location:21.1.0'
```

**Map Activity Code:**
```kotlin
class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }
    
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        
        // Set initial position (Jatre ground)
        val jatreLoc = LatLng(15.3647, 75.1240)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(jatreLoc, 16f))
        
        // Load markers from Firestore
        loadMapMarkers()
        
        // Enable My Location
        enableMyLocation()
        
        // Marker click listener
        map.setOnMarkerClickListener { marker ->
            showLocationBottomSheet(marker)
            true
        }
    }
    
    private fun loadMapMarkers() {
        lifecycleScope.launch {
            val locations = MapRepository().getMapLocations()
            locations.forEach { location ->
                val markerOptions = MarkerOptions()
                    .position(LatLng(location.latitude, location.longitude))
                    .title(location.name)
                    .snippet(location.description)
                    .icon(getMarkerIcon(location.type))
                map.addMarker(markerOptions)
            }
        }
    }
    
    private fun getMarkerIcon(type: String): BitmapDescriptor {
        val iconRes = when(type) {
            "food" -> R.drawable.ic_marker_food
            "toy" -> R.drawable.ic_marker_toy
            "parking" -> R.drawable.ic_marker_parking
            "firstaid" -> R.drawable.ic_marker_firstaid
            "gate" -> R.drawable.ic_marker_gate
            else -> R.drawable.ic_marker_default
        }
        return BitmapDescriptorFactory.fromResource(iconRes)
    }
    
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }
    
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}
```

### 5.6 Gemini AI Integration

**Setup:**
1. Get Gemini API key from https://ai.google.dev
2. Store in `local.properties`: `GEMINI_API_KEY=your_key_here`

**Add Dependency:**
```gradle
implementation 'com.google.ai.client.generativeai:generativeai:0.1.2'
```

**Chatbot Service:**
```kotlin
class GeminiChatService(private val apiKey: String) {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey,
        generationConfig = generationConfig {
            temperature = 0.7f
            topK = 40
            topP = 0.95f
            maxOutputTokens = 200
        }
    )
    
    private val systemPrompt = """
        You are a helpful assistant for the Jatre-Namma Pride app, a digital guide to village fairs (Jatre) in Karnataka.
        
        Answer questions about:
        - Event schedules and timings
        - Location of stalls, parking, first-aid
        - Cultural significance of events
        - Safety guidelines
        - Lost & Found process
        
        Respond in the same language as the user query (Kannada or English).
        Keep responses concise (max 100 words).
        If you don't know, say "I don't have that information. Please check with the fair organizers."
        
        Current schedule:
        - Ratha Procession: 10:00 AM - 12:00 PM at Main Ground
        - Traditional Wrestling: 2:00 PM - 4:00 PM at Wrestling Arena
        - Yakshagana Drama: 7:00 PM - 10:00 PM at Open Air Theater
        
        Facilities:
        - Parking: North Zone (200m), South Zone (450m)
        - First-Aid: Near Main Ground
        - Lost & Found: Helpdesk at Entry Gate
    """.trimIndent()
    
    suspend fun sendMessage(userMessage: String): String {
        return try {
            val prompt = "$systemPrompt\n\nUser: $userMessage\nAssistant:"
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Sorry, I couldn't generate a response."
        } catch (e: Exception) {
            "Sorry, I encountered an error: ${e.message}"
        }
    }
}
```

**Chat Bottom Sheet:**
```kotlin
class ChatBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var chatService: GeminiChatService
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_chat_bottom_sheet, container, false)
        
        chatService = GeminiChatService(BuildConfig.GEMINI_API_KEY)
        
        val recyclerView = view.findViewById<RecyclerView>(R.id.chatRecyclerView)
        adapter = ChatAdapter(messages)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        
        val inputField = view.findViewById<EditText>(R.id.chatInput)
        val sendButton = view.findViewById<ImageButton>(R.id.sendButton)
        
        sendButton.setOnClickListener {
            val userMessage = inputField.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                sendMessage(userMessage)
                inputField.text.clear()
            }
        }
        
        return view
    }
    
    private fun sendMessage(text: String) {
        // Add user message
        messages.add(ChatMessage(text, isUser = true))
        adapter.notifyItemInserted(messages.size - 1)
        
        // Show typing indicator
        messages.add(ChatMessage("...", isUser = false, isTyping = true))
        adapter.notifyItemInserted(messages.size - 1)
        
        // Get bot response
        lifecycleScope.launch {
            val response = chatService.sendMessage(text)
            
            // Remove typing indicator
            messages.removeAt(messages.size - 1)
            adapter.notifyItemRemoved(messages.size)
            
            // Add bot response
            messages.add(ChatMessage(response, isUser = false))
            adapter.notifyItemInserted(messages.size - 1)
        }
    }
    
    data class ChatMessage(
        val text: String,
        val isUser: Boolean,
        val isTyping: Boolean = false,
        val timestamp: Long = System.currentTimeMillis()
    )
}
```

### 5.7 Firebase Cloud Messaging (Push Notifications)

**Setup:**
1. Download `google-services.json` from Firebase Console
2. Add dependency:
```gradle
implementation 'com.google.firebase:firebase-messaging-ktx'
```

**Create Service:**
```kotlin
class JatreFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        remoteMessage.notification?.let {
            showNotification(it.title ?: "", it.body ?: "")
        }
    }
    
    private fun showNotification(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "jatre_events"
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Jatre Events",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        
        val intent = Intent(this, EventDetailActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
```

**Register in Manifest:**
```xml
<service
    android:name=".JatreFirebaseMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

**Schedule Local Notification (Event Reminder):**
```kotlin
class NotificationScheduler(private val context: Context) {
    fun scheduleEventReminder(event: Event) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        // Calculate trigger time (15 minutes before event)
        val triggerTime = event.start_time_epoch - (15 * 60 * 1000)
        
        val intent = Intent(context, EventReminderReceiver::class.java).apply {
            putExtra("EVENT_NAME", event.event_name)
            putExtra("EVENT_TIME", event.time)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            event.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }
}

class EventReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val eventName = intent.getStringExtra("EVENT_NAME") ?: return
        val eventTime = intent.getStringExtra("EVENT_TIME") ?: return
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "event_reminders"
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Event Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("$eventName starts in 15 minutes!")
            .setContentText("Time: $eventTime")
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(eventName.hashCode(), notification)
    }
}
```

---

## 6. UI/UX DESIGN SYSTEM

### 6.1 Color Palette

**Primary Colors:**
```xml
<!-- res/values/colors.xml -->
<resources>
    <!-- Primary Colors -->
    <color name="saffron_orange">#FF9933</color>
    <color name="earthy_green">#138808</color>
    <color name="warm_brown">#8B4513</color>
    
    <!-- Secondary Colors -->
    <color name="light_saffron">#FFCC99</color>
    <color name="light_green">#90EE90</color>
    <color name="cream_white">#FFF8DC</color>
    
    <!-- Status Colors -->
    <color name="status_ongoing">#FF5722</color>
    <color name="status_upcoming">#4CAF50</color>
    <color name="status_completed">#9E9E9E</color>
    
    <!-- Text Colors -->
    <color name="text_primary">#3E2723</color>
    <color name="text_secondary">#6D4C41</color>
    <color name="text_tertiary">#A1887F</color>
    
    <!-- Background Colors -->
    <color name="background_light">#FFFBF0</color>
    <color name="card_background">#FFFFFF</color>
    
    <!-- Accent Colors -->
    <color name="accent_red">#D32F2F</color>
    <color name="accent_yellow">#FFC107</color>
</resources>
```

### 6.2 Typography

**Text Styles:**
```xml
<!-- res/values/styles.xml -->
<resources>
    <!-- Headings -->
    <style name="TextAppearance.Jatre.Headline1">
        <item name="android:textSize">32sp</item>
        <item name="android:textColor">@color/text_primary</item>
        <item name="android:fontFamily">@font/noto_sans_bold</item>
    </style>
    
    <style name="TextAppearance.Jatre.Headline2">
        <item name="android:textSize">24sp</item>
        <item name="android:textColor">@color/text_primary</item>
        <item name="android:fontFamily">@font/noto_sans_bold</item>
    </style>
    
    <style name="TextAppearance.Jatre.Headline3">
        <item name="android:textSize">18sp</item>
        <item name="android:textColor">@color/text_primary</item>
        <item name="android:fontFamily">@font/noto_sans_semibold</item>
    </style>
    
    <!-- Body Text -->
    <style name="TextAppearance.Jatre.Body1">
        <item name="android:textSize">16sp</item>
        <item name="android:textColor">@color/text_primary</item>
        <item name="android:fontFamily">@font/noto_sans_regular</item>
        <item name="android:lineSpacingMultiplier">1.5</item>
    </style>
    
    <style name="TextAppearance.Jatre.Body2">
        <item name="android:textSize">14sp</item>
        <item name="android:textColor">@color/text_secondary</item>
        <item name="android:fontFamily">@font/noto_sans_regular</item>
    </style>
    
    <!-- Captions -->
    <style name="TextAppearance.Jatre.Caption">
        <item name="android:textSize">12sp</item>
        <item name="android:textColor">@color/text_tertiary</item>
        <item name="android:fontFamily">@font/noto_sans_regular</item>
    </style>
    
    <!-- Buttons -->
    <style name="TextAppearance.Jatre.Button">
        <item name="android:textSize">16sp</item>
        <item name="android:textColor">@color/card_background</item>
        <item name="android:fontFamily">@font/noto_sans_semibold</item>
        <item name="android:textAllCaps">false</item>
    </style>
</resources>
```

**Fonts:**
```xml
<!-- res/font/noto_sans.xml (font family) -->
<?xml version="1.0" encoding="utf-8"?>
<font-family xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <font
        android:fontStyle="normal"
        android:fontWeight="400"
        android:font="@font/noto_sans_regular"
        app:fontStyle="normal"
        app:fontWeight="400"
        app:font="@font/noto_sans_regular" />
    <font
        android:fontStyle="normal"
        android:fontWeight="600"
        android:font="@font/noto_sans_semibold"
        app:fontStyle="normal"
        app:fontWeight="600"
        app:font="@font/noto_sans_semibold" />
    <font
        android:fontStyle="normal"
        android:fontWeight="700"
        android:font="@font/noto_sans_bold"
        app:fontStyle="normal"
        app:fontWeight="700"
        app:font="@font/noto_sans_bold" />
</font-family>
```

*Download Noto Sans fonts from Google Fonts (supports Kannada)*

### 6.3 Component Styles

**Card Views:**
```xml
<style name="Widget.Jatre.Card" parent="Widget.MaterialComponents.CardView">
    <item name="cardCornerRadius">16dp</item>
    <item name="cardElevation">8dp</item>
    <item name="cardBackgroundColor">@color/card_background</item>
    <item name="contentPadding">16dp</item>
</style>

<style name="Widget.Jatre.Card.Elevated">
    <item name="cardElevation">12dp</item>
</style>
```

**Buttons:**
```xml
<style name="Widget.Jatre.Button" parent="Widget.MaterialComponents.Button">
    <item name="android:textAppearance">@style/TextAppearance.Jatre.Button</item>
    <item name="cornerRadius">8dp</item>
    <item name="backgroundTint">@color/saffron_orange</item>
    <item name="android:paddingTop">12dp</item>
    <item name="android:paddingBottom">12dp</item>
    <item name="android:paddingStart">24dp</item>
    <item name="android:paddingEnd">24dp</item>
</style>

<style name="Widget.Jatre.Button.Outlined" parent="Widget.MaterialComponents.Button.OutlinedButton">
    <item name="android:textAppearance">@style/TextAppearance.Jatre.Button</item>
    <item name="android:textColor">@color/saffron_orange</item>
    <item name="strokeColor">@color/saffron_orange</item>
    <item name="strokeWidth">2dp</item>
    <item name="cornerRadius">8dp</item>
</style>
```

**Chips (Status Badges):**
```xml
<style name="Widget.Jatre.Chip" parent="Widget.MaterialComponents.Chip.Choice">
    <item name="chipBackgroundColor">@color/light_saffron</item>
    <item name="android:textColor">@color/text_primary</item>
    <item name="chipCornerRadius">12dp</item>
    <item name="android:textSize">12sp</item>
</style>

<style name="Widget.Jatre.Chip.Ongoing">
    <item name="chipBackgroundColor">@color/status_ongoing</item>
    <item name="android:textColor">@android:color/white</item>
</style>

<style name="Widget.Jatre.Chip.Upcoming">
    <item name="chipBackgroundColor">@color/status_upcoming</item>
    <item name="android:textColor">@android:color/white</item>
</style>

<style name="Widget.Jatre.Chip.Completed">
    <item name="chipBackgroundColor">@color/status_completed</item>
    <item name="android:textColor">@android:color/white</item>
</style>
```

**TextInputLayout:**
```xml
<style name="Widget.Jatre.TextInputLayout" parent="Widget.MaterialComponents.TextInputLayout.OutlinedBox">
    <item name="boxStrokeColor">@color/saffron_orange</item>
    <item name="boxStrokeWidth">2dp</item>
    <item name="boxCornerRadiusTopStart">8dp</item>
    <item name="boxCornerRadiusTopEnd">8dp</item>
    <item name="boxCornerRadiusBottomStart">8dp</item>
    <item name="boxCornerRadiusBottomEnd">8dp</item>
    <item name="hintTextColor">@color/text_secondary</item>
</style>
```

### 6.4 Dimension Standards

```xml
<!-- res/values/dimens.xml -->
<resources>
    <!-- Spacing -->
    <dimen name="spacing_tiny">4dp</dimen>
    <dimen name="spacing_small">8dp</dimen>
    <dimen name="spacing_medium">16dp</dimen>
    <dimen name="spacing_large">24dp</dimen>
    <dimen name="spacing_xlarge">32dp</dimen>
    
    <!-- Card Dimensions -->
    <dimen name="card_corner_radius">16dp</dimen>
    <dimen name="card_elevation">8dp</dimen>
    <dimen name="card_padding">16dp</dimen>
    
    <!-- Icon Sizes -->
    <dimen name="icon_size_small">24dp</dimen>
    <dimen name="icon_size_medium">48dp</dimen>
    <dimen name="icon_size_large">64dp</dimen>
    
    <!-- Button Dimensions -->
    <dimen name="button_corner_radius">8dp</dimen>
    <dimen name="button_height">48dp</dimen>
    
    <!-- Image Dimensions -->
    <dimen name="hero_image_height">250dp</dimen>
    <dimen name="thumbnail_size">80dp</dimen>
    <dimen name="story_card_image_height">180dp</dimen>
</resources>
```

### 6.5 Layout Templates

**Schedule Item Card:**
```xml
<!-- res/layout/item_schedule.xml -->
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="@dimen/spacing_small"
    app:cardCornerRadius="@dimen/card_corner_radius"
    app:cardElevation="@dimen/card_elevation">
    
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/card_padding">
        
        <!-- Event Icon -->
        <ImageView
            android:id="@+id/eventIcon"
            android:layout_width="@dimen/icon_size_medium"
            android:layout_height="@dimen/icon_size_medium"
            android:src="@drawable/ic_event_default"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
        
        <!-- Event Name -->
        <TextView
            android:id="@+id/eventName"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="@dimen/spacing_medium"
            android:text="Event Name"
            android:textAppearance="@style/TextAppearance.Jatre.Headline3"
            app:layout_constraintEnd_toStartOf="@id/statusChip"
            app:layout_constraintStart_toEndOf="@id/eventIcon"
            app:layout_constraintTop_toTopOf="@id/eventIcon" />
        
        <!-- Event Time -->
        <TextView
            android:id="@+id/eventTime"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/spacing_tiny"
            android:text="10:00 AM - 12:00 PM"
            android:textAppearance="@style/TextAppearance.Jatre.Body2"
            app:layout_constraintEnd_toEndOf="@id/eventName"
            app:layout_constraintStart_toStartOf="@id/eventName"
            app:layout_constraintTop_toBottomOf="@id/eventName" />
        
        <!-- Location -->
        <TextView
            android:id="@+id/eventLocation"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/spacing_tiny"
            android:text="Main Ground"
            android:textAppearance="@style/TextAppearance.Jatre.Caption"
            android:drawableStart="@drawable/ic_location"
            android:drawablePadding="@dimen/spacing_tiny"
            app:layout_constraintEnd_toEndOf="@id/eventName"
            app:layout_constraintStart_toStartOf="@id/eventName"
            app:layout_constraintTop_toBottomOf="@id/eventTime" />
        
        <!-- Status Chip -->
        <com.google.android.material.chip.Chip
            android:id="@+id/statusChip"
            style="@style/Widget.Jatre.Chip"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Ongoing"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
        
    </androidx.constraintlayout.widget.ConstraintLayout>
</com.google.android.material.card.MaterialCardView>
```

**Lost & Found Card:**
```xml
<!-- res/layout/item_lost_found.xml -->
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="@dimen/spacing_small"
    app:cardCornerRadius="@dimen/card_corner_radius"
    app:cardElevation="@dimen/card_elevation">
    
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/card_padding">
        
        <!-- Category Badge -->
        <com.google.android.material.chip.Chip
            android:id="@+id/categoryChip"
            style="@style/Widget.Jatre.Chip"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Person"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
        
        <!-- Timestamp -->
        <TextView
            android:id="@+id/timestamp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="2 hours ago"
            android:textAppearance="@style/TextAppearance.Jatre.Caption"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
        
        <!-- Photo Thumbnail (optional) -->
        <ImageView
            android:id="@+id/photoThumbnail"
            android:layout_width="@dimen/thumbnail_size"
            android:layout_height="@dimen/thumbnail_size"
            android:layout_marginTop="@dimen/spacing_medium"
            android:scaleType="centerCrop"
            android:visibility="gone"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/categoryChip" />
        
        <!-- Description -->
        <TextView
            android:id="@+id/description"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="@dimen/spacing_medium"
            android:layout_marginTop="@dimen/spacing_medium"
            android:text="Description text here..."
            android:textAppearance="@style/TextAppearance.Jatre.Body1"
            android:maxLines="3"
            android:ellipsize="end"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toEndOf="@id/photoThumbnail"
            app:layout_constraintTop_toBottomOf="@id/categoryChip" />
        
        <!-- Contact -->
        <TextView
            android:id="@+id/contact"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/spacing_small"
            android:text="Contact: 98765XXXXX"
            android:textAppearance="@style/TextAppearance.Jatre.Body2"
            app:layout_constraintEnd_toEndOf="@id/description"
            app:layout_constraintStart_toStartOf="@id/description"
            app:layout_constraintTop_toBottomOf="@id/description" />
        
        <!-- Mark as Resolved Button -->
        <com.google.android.material.button.MaterialButton
            android:id="@+id/resolveButton"
            style="@style/Widget.Jatre.Button.Outlined"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/spacing_medium"
            android:text="Mark as Resolved"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@id/contact" />
        
    </androidx.constraintlayout.widget.ConstraintLayout>
</com.google.android.material.card.MaterialCardView>
```

### 6.6 Traditional Motifs & Decorations

**Rangoli Pattern Background:**
- Create vector drawable with traditional rangoli patterns
- Use as watermark on splash screen (30% opacity)
- Apply as subtle background on cards (5% opacity)

**Sample Vector Drawable:**
```xml
<!-- res/drawable/bg_rangoli_pattern.xml -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="200dp"
    android:height="200dp"
    android:viewportWidth="200"
    android:viewportHeight="200">
    
    <!-- Traditional rangoli geometric pattern -->
    <path
        android:fillColor="@color/saffron_orange"
        android:fillAlpha="0.1"
        android:pathData="M100,50 L125,75 L125,125 L100,150 L75,125 L75,75 Z" />
    
    <path
        android:fillColor="@color/earthy_green"
        android:fillAlpha="0.1"
        android:pathData="M100,75 L115,90 L115,110 L100,125 L85,110 L85,90 Z" />
    
    <!-- Add more decorative elements -->
</vector>
```

**Gradient Backgrounds:**
```xml
<!-- res/drawable/bg_gradient_saffron.xml -->
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient
        android:type="linear"
        android:angle="135"
        android:startColor="@color/saffron_orange"
        android:endColor="@color/warm_brown" />
</shape>

<!-- res/drawable/bg_gradient_green.xml -->
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient
        android:type="linear"
        android:angle="135"
        android:startColor="@color/earthy_green"
        android:endColor="@color/light_green" />
</shape>
```

---

## 7. TESTING & QUALITY CHECKLIST

### 7.1 Pre-Submission Testing Checklist

**Functional Testing:**

| Test Case | Expected Result | Status |
|-----------|----------------|--------|
| App launches without crash | Splash screen shows for 2s, navigates to Home | ☐ |
| Home screen loads within 3s | All feature cards visible | ☐ |
| Schedule screen shows events | Events displayed with correct status | ☐ |
| Current event highlighted in saffron | Ongoing event has colored badge | ☐ |
| Event detail opens on click | Full details shown | ☐ |
| Set Reminder button works | Notification scheduled | ☐ |
| Lost & Found post submission | Form validation, image upload, post appears in feed | ☐ |
| Mark as Resolved works | Status changes, chip updates | ☐ |
| Map loads with markers | All 5 marker types visible | ☐ |
| Map marker click shows details | Bottom sheet with location info | ☐ |
| Cultural stories load | List of stories with images | ☐ |
| Story detail opens | Full text readable | ☐ |
| Safety info displays | Emergency contacts, parking zones shown | ☐ |
| Call button initiates call | Dialer opens with number | ☐ |
| Chatbot opens from FAB | Bottom sheet appears | ☐ |
| Chatbot responds to query | Gemini API returns answer | ☐ |
| Push notification received | Notification shows before event | ☐ |
| Offline mode works | Cached data visible without internet | ☐ |

**UI/UX Testing:**

| Test Case | Expected Result | Status |
|-----------|----------------|--------|
| App uses saffron/green color palette | Festive theme throughout | ☐ |
| Cards have 16dp rounded corners | Consistent styling | ☐ |
| Text is readable (contrast check) | WCAG AA compliance | ☐ |
| Kannada text renders correctly | Noto Sans font displays properly | ☐ |
| Buttons have proper touch targets (48dp min) | Easy to tap | ☐ |
| Loading indicators show during fetch | ProgressBar visible | ☐ |
| Empty states are friendly | Illustration + helpful message | ☐ |
| Error messages are clear | User understands what went wrong | ☐ |

**Performance Testing:**

| Test Case | Expected Result | Status |
|-----------|----------------|--------|
| Schedule scrolls smoothly with 30+ items | No lag or stutter | ☐ |
| Images load without blocking UI | Glide async loading | ☐ |
| Lost & Found feed updates in real-time | New posts appear without refresh | ☐ |
| Map markers load within 2s | All markers visible quickly | ☐ |
| Chatbot responds within 3s | Gemini API returns promptly | ☐ |
| App size under 15 MB | APK not bloated | ☐ |
| Memory usage under 100 MB | No memory leaks | ☐ |

**Security Testing:**

| Test Case | Expected Result | Status |
|-----------|----------------|--------|
| API keys not hardcoded | Stored in local.properties | ☐ |
| Firebase rules prevent unauthorized deletion | Write rules enforced | ☐ |
| Images compressed before upload | <500 KB size | ☐ |
| Phone numbers partially masked | Last 5 digits hidden | ☐ |
| No sensitive data in logs | Logcat clean in release build | ☐ |

**Edge Case Testing:**

| Test Case | Expected Result | Status |
|-----------|----------------|--------|
| No internet connection | Graceful error message | ☐ |
| Firebase fetch fails | Retry option shown | ☐ |
| Image upload fails | Error toast, form stays filled | ☐ |
| GPS disabled | Map shows "Enable location" message | ☐ |
| Empty schedule | "No events yet" placeholder | ☐ |
| Empty Lost & Found | "No posts yet" placeholder | ☐ |
| Chatbot API error | "Try again later" message | ☐ |
| Low storage (image upload) | Warning before upload | ☐ |
| Screen rotation | Data persists, no crash | ☐ |
| Back button on Home | Exit app confirmation | ☐ |

### 7.2 Device Testing Matrix

**Test on at least 3 devices:**

| Device | Android Version | Screen Size | Status |
|--------|----------------|-------------|--------|
| Pixel 6 | Android 13 | 6.4" (1080x2400) | ☐ |
| Samsung Galaxy A50 | Android 11 | 6.4" (1080x2340) | ☐ |
| Redmi Note 10 | Android 12 | 6.43" (1080x2400) | ☐ |
| Minimum: Generic API 24 | Android 7.0 | 5.0" (720x1280) | ☐ |

### 7.3 Code Quality Checklist

| Check | Status |
|-------|--------|
| No hardcoded strings (use strings.xml) | ☐ |
| No magic numbers (use dimens.xml) | ☐ |
| Proper null checks (Kotlin null safety) | ☐ |
| Try-catch blocks for network calls | ☐ |
| Coroutines used for async operations | ☐ |
| ViewModels separate from Activities | ☐ |
| Repository pattern for data layer | ☐ |
| No memory leaks (listeners unregistered) | ☐ |
| ProGuard enabled for release build | ☐ |
| Code commented where necessary | ☐ |

---

## 8. DEPLOYMENT INSTRUCTIONS

### 8.1 Build Configuration

**build.gradle (App level):**
```gradle
android {
    compileSdk 34
    
    defaultConfig {
        applicationId "com.yielders.jatrenammapride"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0.0"
        
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        
        // Load API keys from local.properties
        Properties properties = new Properties()
        properties.load(project.rootProject.file('local.properties').newDataInputStream())
        
        buildConfigField "String", "GEMINI_API_KEY", "\"${properties.getProperty('GEMINI_API_KEY')}\""
        manifestPlaceholders = [MAPS_API_KEY: properties.getProperty('MAPS_API_KEY')]
    }
    
    buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            
            // Sign APK
            signingConfig signingConfigs.release
        }
        debug {
            applicationIdSuffix ".debug"
            debuggable true
        }
    }
    
    signingConfigs {
        release {
            storeFile file("../keystore/jatre-release.jks")
            storePassword System.getenv("KEYSTORE_PASSWORD")
            keyAlias System.getenv("KEY_ALIAS")
            keyPassword System.getenv("KEY_PASSWORD")
        }
    }
    
    buildFeatures {
        viewBinding true
        buildConfig true
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = '1.8'
    }
}
```

### 8.2 ProGuard Rules

**proguard-rules.pro:**
```proguard
# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule
-keep enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Gemini AI
-keep class com.google.ai.client.generativeai.** { *; }

# Data Classes
-keep class com.yielders.jatrenammapride.data.model.** { *; }

# Remove logs in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
```

### 8.3 Create Signed APK

**Steps:**

1. **Generate Keystore:**
```bash
keytool -genkey -v -keystore jatre-release.jks -keyalg RSA -keysize 2048 -validity 10000 -alias jatre-key
```

2. **Build Release APK:**
```bash
./gradlew assembleRelease
```

3. **Locate APK:**
```
app/build/outputs/apk/release/app-release.apk
```

4. **Test Release Build:**
```bash
adb install app/build/outputs/apk/release/app-release.apk
```

### 8.4 Firebase Production Setup

**Enable Production Mode:**

1. **Realtime Database Rules:**
```json
{
  "rules": {
    "jatre": {
      "schedule": {
        ".read": true,
        ".write": "auth != null && auth.uid == 'ADMIN_UID'"
      },
      "lost_and_found": {
        ".read": true,
        ".write": true,
        ".indexOn": ["timestamp", "status"]
      }
    }
  }
}
```

2. **Firestore Indexes:**
   - Navigate to Firebase Console → Firestore → Indexes
   - Create composite index:
     - Collection: `cultural_stories`
     - Fields: `timestamp (Descending)`

3. **Storage CORS Configuration:**
```json
[
  {
    "origin": ["*"],
    "method": ["GET"],
    "maxAgeSeconds": 3600
  }
]
```

Upload via:
```bash
gsutil cors set cors.json gs://jatre-namma-pride.appspot.com
```

### 8.5 Google Play Console Submission

**Prepare Assets:**

1. **App Icon:**
   - 512x512 PNG
   - Transparent background not allowed

2. **Feature Graphic:**
   - 1024x500 PNG/JPG
   - Shows app branding + screenshots

3. **Screenshots:**
   - Minimum 2, maximum 8
   - Phone: 1080x1920 or 1080x2340
   - 7" Tablet: 1200x1920
   - 10" Tablet: 1600x2560

4. **App Description:**
```
Jatre-Namma Pride - Your Digital Guide to Karnataka's Village Fairs

Discover, Navigate, and Celebrate:
✅ Live event schedules with real-time updates
✅ Interactive map of fair grounds
✅ Lost & Found community board
✅ Cultural stories preserving Jatre legends
✅ Emergency contacts and safety info
✅ AI-powered chatbot in Kannada & English

Never miss an event again! Get notified 15 minutes before each cultural performance.

Perfect for visitors, locals, and tourists exploring Karnataka's rich Jatre tradition.

Download now and experience Namma Pride!
```

5. **Privacy Policy:**
   - Host at: https://jatrenammapride.web.app/privacy
   - Must include:
     - Data collected (location, photos)
     - How data is used
     - Third-party services (Firebase, Google Maps, Gemini)
     - User rights

**Submission Steps:**

1. Log in to https://play.google.com/console
2. Create new app: "Jatre-Namma Pride"
3. Fill app details:
   - Category: Lifestyle
   - Tags: Events, Travel, Culture, Karnataka
   - Content rating: Everyone
4. Upload signed APK
5. Set pricing: Free
6. Add screenshots and graphics
7. Submit for review (2-7 days)

### 8.6 GitHub Repository Setup

**Final Repository Structure:**
```
jatre-namma-pride/
├── app/
│   ├── src/
│   ├── build.gradle
│   └── google-services.json (gitignored)
├── gradle/
├── screenshots/
│   ├── splash.png
│   ├── home.png
│   ├── schedule.png
│   ├── map.png
│   └── chatbot.png
├── .gitignore
├── README.md
├── LICENSE
└── build.gradle
```

**README.md Template:**
```markdown
# Jatre-Namma Pride 🎉

> Digital Guide to Karnataka's Village Fairs

![App Logo](screenshots/logo.png)

## 📱 Features

- **Live Schedule**: Real-time event tracking with status updates
- **Lost & Found**: Community board for lost items/persons
- **Interactive Map**: Google Maps with custom markers
- **Cultural Stories**: Preserving Jatre legends
- **Safety Info**: Emergency contacts and parking zones
- **AI Chatbot**: Gemini-powered assistant in Kannada & English

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM
- **Backend**: Firebase (Realtime DB, Firestore, Storage, FCM)
- **Maps**: Google Maps SDK
- **AI**: Gemini API 1.5 Flash
- **Image Loading**: Glide

## 📦 Installation

1. Clone repo:
```bash
git clone https://github.com/yielders-g2/jatre-namma-pride.git
cd jatre-namma-pride
```

2. Add API keys to `local.properties`:
```properties
GEMINI_API_KEY=your_gemini_api_key
MAPS_API_KEY=your_maps_api_key
```

3. Add `google-services.json` to `app/` directory

4. Build and run:
```bash
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📸 Screenshots

| Splash | Home | Schedule | Map |
|--------|------|----------|-----|
| ![](screenshots/splash.png) | ![](screenshots/home.png) | ![](screenshots/schedule.png) | ![](screenshots/map.png) |

## 🚀 Roadmap

- [ ] User authentication
- [ ] Stall booking system
- [ ] In-app chat
- [ ] iOS version
- [ ] Web dashboard

## 👨‍💻 Developer

**Md Junaid**  
Yielders G2 Internship Project #35

## 📄 License

MIT License - see LICENSE file for details
```

**.gitignore:**
```gitignore
# Built application files
*.apk
*.aab

# Files for the ART/Dalvik VM
*.dex

# Java class files
*.class

# Generated files
bin/
gen/
out/

# Gradle files
.gradle/
build/

# Local configuration file (sdk path, etc)
local.properties

# API Keys
google-services.json

# Android Studio
.idea/
*.iml
.DS_Store
/captures
.externalNativeBuild
.cxx

# Keystore files
*.jks
*.keystore

# Backup files
*~
*.swp
```

---

## 📚 FINAL DELIVERABLES SUMMARY

### What to Submit:

1. **GitHub Repository Link**
   - Complete source code
   - README with screenshots
   - All required files (except API keys)

2. **APK File**
   - Signed release build
   - Version 1.0.0
   - Tested on real device

3. **Demo Video** (2-3 minutes)
   - Screen recording showing all features
   - Voice-over explaining functionality
   - Upload to YouTube/Google Drive

4. **Project Documentation**
   - This SOP PDF
   - Firebase database export (JSON)
   - API integration guide

5. **Screenshots** (minimum 8)
   - All main screens captured
   - High resolution (1080p)
   - Clean UI (no debug overlays)

---

## 🎯 SUCCESS CRITERIA FINAL CHECK

Before submission, verify:

✅ All 8 screens implemented and working  
✅ Firebase real-time sync functional  
✅ Google Maps displays custom markers  
✅ Gemini chatbot responds in Kannada & English  
✅ Lost & Found post/resolve works  
✅ Image upload compresses to <500KB  
✅ Push notifications trigger before events  
✅ App doesn't crash on any user action  
✅ Offline mode shows cached data  
✅ UI follows festive theme (saffron/green)  
✅ Code is clean, commented, and organized  
✅ APK size under 15 MB  
✅ Tested on 3+ devices  
✅ README complete with instructions  
✅ Privacy policy published  

---

## 📞 SUPPORT & TROUBLESHOOTING

**Common Issues:**

| Issue | Solution |
|-------|----------|
| Firebase not connecting | Check `google-services.json` in `app/` directory |
| Map not loading | Verify MAPS_API_KEY in `local.properties` and API enabled in Google Cloud Console |
| Gemini API error | Check GEMINI_API_KEY and billing enabled |
| Image upload fails | Verify Firebase Storage rules allow write access |
| Notification not showing | Check FCM setup and notification channel creation |

**Resources:**
- Firebase Documentation: https://firebase.google.com/docs
- Google Maps Android SDK: https://developers.google.com/maps/documentation/android-sdk
- Gemini API Docs: https://ai.google.dev/docs
- Kotlin Docs: https://kotlinlang.org/docs

---

## 🏁 CONCLUSION

This SOP provides everything needed to build Jatre-Namma Pride from scratch. Follow the screen prompts, implement the navigation flow, integrate the APIs as specified, and test thoroughly using the checklist.

**Timeline Reminder:**
- **Week 1**: Setup + Core content screens (Schedule, Stories)
- **Week 2**: User interaction features (Lost & Found, Map, Safety)
- **Week 3**: AI integration, polish, testing, delivery

**Final Note:**  
This is a production-ready specification. An AI agent (or developer) can use this document to generate all code, layouts, and configurations without additional input. The end result will be a fully functional Android app ready for Play Store deployment.

Good luck building Jatre-Namma Pride! 🎉

---

**Document Version:** 1.0  
**Last Updated:** 2024  
**Prepared By:** Md Junaid (Yielders G2)  
**Project ID:** #35

---

*End of SOP Document*