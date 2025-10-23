📚 BookApp

BookApp is a modern Android application built with Kotlin, following MVVM and Clean Architecture principles.
It fetches book data from an open-source API and displays it in a responsive list that adapts to different screen sizes (mobile, tablet, and desktop).

*** Features ***

🔹 Multi-Module Architecture — clear separation between presentation, domain, and data layers.

🔹 Clean Architecture — easy to test, scale, and maintain.

🔹 MVVM Pattern — lifecycle-aware ViewModels with reactive state using Kotlin Flows.

🔹 Adaptive UI — layouts automatically adjust for portrait, landscape, tablet, and desktop screens.

🔹 Error Handling — robust success and failure handling with meaningful UI feedback.

🔹 Koin - Using koin for dependency injection to make app more towards KMP.

🔹 Kotlin Multiplatform Ready — (optional) modular structure allows easy extension to other platforms.


*** Project Structure ***
BookApp/
├── app/               # Application module (entry point)
├── presentation/      # UI layer (Compose UI, ViewModels)
├── domain/            # Business logic (Repository Signature)
├── data/              # Repository + data sources (API)
└── build.gradle(.kts) # Root Gradle config


*** Improvements ***

🔹API doesn't support pagination. Need to update api response format to support pagination.

🔹Add pagination inside the app when displaying list view. 

🔹Creating a custom pagination class to support all format of api response. 

🔹Supporting light and dark theme.

🔹Moving from retrofit to OkHttp to support KMP in future.

🔹Creating a build-logic project to support build.gradle of different module in one place.

🔹User is hardcode in viewmodel. This can be passed to viewmodel in later stages.
