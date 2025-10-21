📚 BookApp

BookApp is a modern Android application built with Kotlin, following MVVM and Clean Architecture principles.
It fetches book data from an open-source API and displays it in a responsive list that adapts to different screen sizes (mobile, tablet, and desktop).

Features

🔹 Multi-Module Architecture — clear separation between presentation, domain, and data layers.

🔹 Clean Architecture — easy to test, scale, and maintain.

🔹 MVVM Pattern — lifecycle-aware ViewModels with reactive state using Kotlin Flows.

🔹 Adaptive UI — layouts automatically adjust for portrait, landscape, tablet, and desktop screens.

🔹 Error Handling — robust success and failure handling with meaningful UI feedback.

🔹 Kotlin Multiplatform Ready — (optional) modular structure allows easy extension to other platforms.


Project Structure
BookApp/
├── app/               # Application module (entry point)
├── presentation/      # UI layer (Compose UI, ViewModels)
├── domain/            # Business logic (Repository Signature)
├── data/              # Repository + data sources (API)
└── build.gradle(.kts) # Root Gradle config


🛠️ TODO

 Add dark mode support

 Add pagination
