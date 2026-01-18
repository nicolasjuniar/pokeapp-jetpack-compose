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
- UDF (Unidirectional Data Flow)
- State / Event / Signal pattern
- Modularization

### UI

- Jetpack Compose
- Navigation Compose

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
- Detekt Report
- Jacoco Report
- JUnit

### Debug Tools

- Chucker

### Other

- CameraX

---

## ✨ Features

### Authentication

- Login & Register using Room
- Session using Datastore
- Splash screen determines login or main screen

### Dashboard

- Pokémon listing with:
  - Paging 3 + Remote Mediator Integration
  - Detail Screen and Add/Remove Favorite
- Favorite Pokémon system (Datastore + Room)
- Profile with:
  - Change Password
  - Profile picture update (CameraX + Gallery)
  - Dark Mode Toggle

---

## 📌 To-Do List

- [ ] Add link to report, screenshoot and gif to show demo
- [ ] Add KDoc(?)

---

## 📦 Downloads

- **Latest Release:** [Download here](https://github.com/nicolasjuniar/pokeapp-jetpack-compose/releases)
- **GitHub Artifact:** [View artifact](https://github.com/nicolasjuniar/pokeapp-jetpack-compose/actions/runs/20007859465)
- **Detekt Report:** [View here](https://nicolasjuniar.github.io/pokeapp-jetpack-compose-report-detekt/detekt.html)
- **Jacoco Report:** [View here](https://nicolasjuniar.github.io/pokeapp-jetpack-compose-report-jacoco/index.html)

## 📝 Summary

This project is a deep exploration of modern Android development using Jetpack Compose and Clean Architecture.
It integrates real-world tools such as CI/CD, modularization, runtime permissions, local authentication, persistent sessions, and offline caching via Remote Mediator—making it both a learning journey and a solid portfolio piece.