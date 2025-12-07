# 🧬 PokeApp Jetpack Compose

**Personal project to learn Jetpack Compose + Clean Architecture using PokeAPI**

A fully Jetpack Compose Android application built with modern Android development tools and Clean Architecture.
This project showcases authentication, persistent sessions, modularization, profile features, and complete Pokémon listing powered by Paging 3 + Remote Mediator.

---

## 🚀 Tech Stack

### Architecture

- MVVM
- Repository Pattern
- Single-Activity Architecture
- Modularization (v1.1)

### UI

- Jetpack Compose
- Navigation Compose
- Material3

### Core Libraries

- Dagger Hilt
- Retrofit
- OkHttp
- Gson
- Room
- Coroutines
- Datastore
- Paging 3 + Remote Mediator
- Coil

### Tooling / DevOps

- CI/CD with GitHub Actions
- Detekt Report (v1.1)
- Jacoco Report (v1.1)
- JUnit (v1.1)

### Debug Tools

- Chucker (v1.1)
- LeakCanary (v1.1)

### Other

- CameraX (v1.1)
- Runtime Permissions

---

## ✨ Features

### Authentication

- Login & Register using Room
- Session using Datastore
- Splash screen determines login or main screen

### Pokémon List & Detail

- Pokémon listing with Paging 3
- Remote Mediator integration
- Pokémon detail screen
- Favorite Pokémon system (Datastore + Room)

### Profile(v1.1)

- Favorite type
- Change password
- Profile picture update (Camera + Storage permissions)

---

## 📌 To-Do List

### Main Development

- [x] Add login, register, main, and detail dummy screen
- [x] Implement simple login & register
- [x] Add Pokémon list & favorite tabs using bottom navigation
- [x] Connect all screens using Navigation Compose
- [x] Add Dagger Hilt and Room for login/register
- [x] Add Retrofit, OkHttp, Gson for Pokémon API
- [x] Add Datastore for session
- [x] Implement Paging 3
- [x] Combine RemoteMediator + Paging 3
- [x] Fetch Pokémon detail + detail screen
- [x] Add favorite feature using Paging 3
- [x] Create GitHub Action to build APK
- [x] Add ProGuard rules
- [ ] Polish Project and Update All Depedencies

### v1.1 Enhancements

- [x] Add LeakCanary & Chucker
- [x] Add Detekt + report in GitHub Actions
- [ ] Separate signing config for local project and GitHub Actions
- [ ] Add CameraX + runtime permissions
- [ ] Implement Profile feature
- [ ] Add Unit Test + Jacoco report in GitHub Actions
- [ ] Add Report to github pages
- [ ] Add link to report, screenshoot and gif to show demo
- [ ] Modularization
- [ ] Add KDoc(?)

---

## 📦 Downloads

- **Latest Release:** [Download here](#)
- **GitHub Artifact:** [View artifact](#)

## 📝 Summary

This project is a deep exploration of modern Android development using Jetpack Compose and Clean Architecture.
It integrates real-world tools such as CI/CD, modularization, runtime permissions, local authentication, persistent sessions, and offline caching via Remote Mediator—making it both a learning journey and a solid portfolio piece.
