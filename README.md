# LGU Connect (Balayan, Batangas)

Android app for LGU broadcast announcements, emergency alerts, and public notices with Firebase-backed dynamic posting.

## Stack

- Android (Java, SDK 34 / min SDK 26)
- Firebase Firestore
- Firebase Authentication (Email/Password)
- Firebase Cloud Messaging
- Firebase Storage

## Firebase Setup

1. Create a project in [Firebase Console](https://console.firebase.google.com/) named `LGUConnect`.
2. Add Android app package `com.lguconnect`.
3. Download `google-services.json` and place it in `app/google-services.json`.
4. Enable Firestore, Authentication (Email/Password), Storage, and Cloud Messaging.
5. Use these Firestore rules:

```txt
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /announcements/{id} {
      allow read: if true;
      allow write: if request.auth != null;
    }
    match /categories/{id} {
      allow read: if true;
      allow write: if request.auth != null;
    }
  }
}
```

## Run

1. Open project in Android Studio.
2. Sync Gradle.
3. Connect an emulator/device (Android 8.0+).
4. Build and run.

## Important Notes

- App subscribes residents to topic `all_residents` at launch.
- Push display logic is implemented in `FCMNotificationService`.
- For secure FCM HTTP v1 sending, use a Cloud Function/backend with service-account credentials.
- Announcements and categories are loaded in real-time from Firestore (`addSnapshotListener`).
