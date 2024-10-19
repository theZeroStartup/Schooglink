# Schooglink Assignment

## Description
The app fetches data from an End-point which mainly contains two lists which are displayed in 2 different recycler views
The app's main purpose is to showcase the MVVM architecture, quality of code and correctness of functionalities

## Features
- List of features in the app:
    - API calls using Retrofit with error handling and loading states
    - RecyclerViews with search/filter functionality
    - Clean architecture on top of MVVM using ViewModel and LiveData
    - Kotlin Coroutines for asynchronous operations
    - DI using Hilt for reusable code and ease of access of Retrofit, Repositories, etc.
    - Navigation component for traversing between Fragments
    - Shimmer Effect for loading state animations

## Usage
- Run the app on an emulator or physical device.
- App fetches data from db and displays
- Search in the search field to filter through the retrieved lists
- Traverse through different 'Link' tabs and Bottom Navigation
- Open any item from RecyclerView to open Details screen 

## Dependencies
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- [Shimmer](https://facebook.github.io/shimmer-android/) - A shimmer loading effect for Android, created by Facebook.
- [Hilt](https://dagger.dev/hilt/) - Hilt is a dependency injection library for Android, built on top of Dagger.

