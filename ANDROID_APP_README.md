# Jatre Namma Pride Android App

Kotlin Android application for the Jatre-Namma Pride digital village fair guide.

## What Is Implemented

- Splash screen with festive saffron/green identity
- Home dashboard with feature cards and bottom navigation
- Live schedule with event status, crowd level, and detail pages
- Lost & Found feed, validation, local post submission, and resolve action
- Jatre map screen with typed locations and Google Maps directions intents
- Cultural stories list and detail pages with sharing
- Parking, emergency contacts, dialer actions, and safety guidelines
- Chat assistant bottom sheet with offline context-aware replies
- MVVM-friendly repository and model structure

## Open In Android Studio

1. Open this folder as an Android project.
2. Copy `local.properties.example` to `local.properties`.
3. Add your SDK path and API keys:

```properties
sdk.dir=C\:\\Users\\<you>\\AppData\\Local\\Android\\Sdk
GEMINI_API_KEY=your_gemini_api_key
MAPS_API_KEY=your_google_maps_api_key
```

4. Sync Gradle.
5. Run the `app` configuration on an emulator or Android device.

## Build

```bash
gradle assembleDebug
```

If you add a Gradle wrapper in Android Studio, use:

```bash
./gradlew assembleDebug
```

## Next Production Integrations

- Add Firebase Realtime Database for schedule and Lost & Found
- Add Firestore for cultural stories, map locations, parking, and contacts
- Add Firebase Storage for Lost & Found photo uploads
- Replace the map preview with Maps Compose or a native `MapView`
- Replace offline chat replies with Gemini API calls using `BuildConfig.GEMINI_API_KEY`
