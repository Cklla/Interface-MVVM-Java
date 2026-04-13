# 🍛 Taj Mahal — Application Android

![Android](https://img.shields.io/badge/Android-API%2024%2B-3DDC84?style=flat&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-8-ED8B00?style=flat&logo=openjdk&logoColor=white)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-blue?style=flat)
![Hilt](https://img.shields.io/badge/DI-Hilt-orange?style=flat)
![LiveData](https://img.shields.io/badge/LiveData-ViewModel-purple?style=flat)
![License](https://img.shields.io/badge/Licence-OpenClassrooms-lightgrey?style=flat)

Application Android de fiche restaurant pour le **Taj Mahal**, développée dans le cadre du Projet 3 OpenClassrooms. Elle permet de consulter les informations du restaurant, de parcourir les avis clients et d'en laisser un nouveau — le tout en suivant une architecture **MVVM + Clean Architecture**.

---

## 📱 Captures d'écran

| Fiche restaurant | Avis clients | Laisser un avis |
|:---:|:---:|:---:|
| ![Fiche restaurant](screenshots/screen1.png) | ![Liste des avis](screenshots/screen2.png) | ![Nouveau avis](screenshots/screen3.png) |

---

## ✨ Fonctionnalités

- **Fiche restaurant** — nom, adresse, horaires, site web, numéro de téléphone (liens cliquables)
- **Note globale** — moyenne calculée dynamiquement, affichée via une `RatingBar`
- **Répartition des étoiles** — 5 barres de progression (1 à 5 étoiles) mises à jour en temps réel
- **Nombre total d'avis** — affiché et recalculé à chaque ajout
- **Liste des avis** — `RecyclerView` avec avatar (chargé via Glide), nom, note et commentaire
- **Ajout d'un avis** — formulaire avec note via `RatingBar` et champ texte ; le nouvel avis s'insère en tête de liste
- **Navigation fluide** — retour arrière géré via `addToBackStack`

---

## 🛠️ Stack technique

| Composant | Technologie |
|---|---|
| Langage | Java 8 |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 36 |
| Architecture | MVVM + Clean Architecture |
| Injection de dépendances | Hilt 2.44 |
| Observabilité | LiveData + ViewModel (Jetpack) |
| Layouts | ConstraintLayout, RecyclerView |
| Liaison de vues | View Binding |
| Chargement d'images | Glide 4.16.0 |
| Tests unitaires | JUnit 4 + AndroidX Core Testing |
| Build | Gradle (Kotlin DSL) |

---

## 🚀 Installation et lancement

### Prérequis

- **Android Studio** Hedgehog (2023.1.1) ou plus récent
- **JDK 17** (fourni avec Android Studio)
- Un émulateur ou appareil physique sous **Android 7.0 (API 24)** minimum

### Cloner le projet

```bash
git clone https://github.com/Cklla/Interface-MVVM-Java.git
cd Interface-MVVM-Java
```

### Ouvrir dans Android Studio

1. Lancer Android Studio
2. Sélectionner **File > Open** et pointer vers le dossier cloné
3. Attendre la synchronisation Gradle
4. Lancer l'app avec le bouton ▶ ou via la commande ci-dessous

### Commandes Gradle

```bash
./gradlew assembleDebug          # Compiler l'APK debug
./gradlew installDebug           # Compiler et installer sur un appareil connecté
./gradlew test                   # Lancer les tests unitaires
./gradlew connectedAndroidTest   # Tests instrumentés (émulateur/appareil requis)
./gradlew lint                   # Analyse statique du code
./gradlew clean                  # Nettoyer les fichiers générés
```

---

## 🏗️ Architecture du projet

L'application suit le patron **MVVM** (Model — View — ViewModel) avec une séparation stricte des responsabilités entre les couches.

```
com.openclassrooms.tajmahal/
│
├── TajMahalApplication.java          # Point d'entrée Hilt (@HiltAndroidApp)
│
├── ui/                               # Couche présentation (View)
│   ├── MainActivity.java             # Single Activity — hôte des Fragments
│   ├── restaurant/
│   │   ├── DetailsFragment.java      # Affiche la fiche restaurant + notes
│   │   └── DetailsViewModel.java     # Calculs (moyenne, %, ajout avis) + LiveData
│   └── reviews/
│       ├── ReviewsFragment.java      # Écran liste des avis + formulaire
│       └── ReviewsAdapter.java       # Adapter RecyclerView pour les avis
│
├── data/                             # Couche données
│   ├── repository/
│   │   └── RestaurantRepository.java # Source unique de vérité — expose LiveData
│   └── service/
│       ├── RestaurantApi.java        # Interface du contrat de données
│       └── RestaurantFakeApi.java    # Implémentation simulée (pas de réseau)
│
├── domain/model/                     # Modèles métier
│   ├── Restaurant.java
│   └── Review.java
│
└── di/
    └── AppModule.java                # Module Hilt — fournit RestaurantApi
```

### Flux de données

```
DetailsFragment
    └── observe LiveData
            └── DetailsViewModel
                    └── appelle RestaurantRepository
                            └── appelle RestaurantFakeApi
                                    └── retourne List<Review> / Restaurant
```

Les données remontent via `LiveData` : le Fragment observe sans jamais interroger directement le dépôt. Seul le `Repository` peut muter les données (`MutableLiveData` privé).

### Layouts

```
res/layout/
├── activity_main.xml       # FragmentContainerView (hôte unique)
├── fragment_details.xml    # Fiche restaurant avec notes et bouton avis
├── fragment_reviews.xml    # Liste des avis + formulaire d'ajout
└── item_review.xml         # Ligne individuelle d'un avis (avatar, nom, note, texte)
```

---

## 🧪 Tests unitaires

Les tests couvrent la logique métier du `DetailsViewModel`, sans Hilt (instanciation manuelle), en suivant le patron **AAA** (Arrange / Act / Assert).

| Méthode testée | Cas couverts |
|---|---|
| `getAverageRating()` | liste `null`, liste vide, cas nominal |
| `getRatingPercentage()` | liste `null`, liste vide, cas nominal |
| `addReview()` | insertion en position 0 vérifiée via `observeForever` |

```bash
./gradlew test
```

---

## 📁 Structure complète du projet

```
Interface-MVVM-Java/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/openclassrooms/tajmahal/   # Code source
│   │   │   ├── res/
│   │   │   │   ├── drawable/     # Icônes vectorielles, images, splash
│   │   │   │   ├── font/         # Jakarta Bold, SemiBold, Regular
│   │   │   │   ├── layout/       # Layouts XML
│   │   │   │   ├── values/       # Couleurs, dimensions, thème, strings (EN)
│   │   │   │   └── values-fr/    # Strings localisées (FR)
│   │   │   └── AndroidManifest.xml
│   │   └── test/                 # Tests unitaires JUnit
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 🌐 Localisation

L'application est disponible en **anglais** (défaut) et en **français** (`values-fr/strings.xml`). La langue est sélectionnée automatiquement selon les paramètres système de l'appareil.

---

*Projet réalisé dans le cadre de la formation Développeur d'application Android — OpenClassrooms.*
