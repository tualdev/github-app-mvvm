<h1 align="center">Github MVVM App</h1>

<p align="center">  
Github App is a small demo application based on modern Android application tech-stacks and MVVM architecture.
</p>

<p align="center">
<img src="/previews/screenshot.jpg"/>
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt (alpha) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Sandwich](https://github.com/skydoves/Sandwich) - construct lightweight http API response and handling error responses.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- [TransformationLayout](https://github.com/skydoves/transformationlayout) - implementing transformation motion animations.
- [WhatIf](https://github.com/skydoves/whatif) - checking nullable object and empty collections more fluently.
- [Bundler](https://github.com/skydoves/bundler) - Android Intent & Bundle extensions that insert and retrieve values elegantly.
- [Timber](https://github.com/JakeWharton/timber) - logging.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- Custom Views
  - [Rainbow](https://github.com/skydoves/rainbow) - An easy way to apply gradations and tinting for Android.
  - [AndroidRibbon](https://github.com/skydoves/androidribbon) - A simple way to implement a  beautiful ribbon with the shimmering on Android.
  - [ProgressView](https://github.com/skydoves/progressview) - A polished and flexible ProgressView, fully customizable with animations.
  
## Architecture
Github App is based on MVVM architecture and a repository pattern.

## Github API

This app using the [Github API](https://api.github.com/) for constructing RESTful API.<br>
