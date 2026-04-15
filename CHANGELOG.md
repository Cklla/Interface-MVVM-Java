# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

## [1.0.0] — 2026-04-13

### Added

- **Tests unitaires** — couverture de `DetailsViewModel` :
  - `getAverageRating` : cas null, liste vide, cas nominal
  - `getRatingPercentage` : cas null, liste vide, cas nominal
  - `addReview` : insertion en position 0 vérifiée via `observeForever`
- **Ajout d'un avis** — bouton "Valider" dans `ReviewsFragment` lit le commentaire et la note,
  crée un `Review("Manon Garcia", …)` et appelle `ViewModel.addReview()` ;
  le nouvel avis apparaît en tête de liste en temps réel via LiveData
- **`ReviewsFragment`** — écran d'avis complet avec `RecyclerView`, formulaire de saisie
  (note + commentaire), avatar de l'utilisateur courant chargé via Glide
- **Navigation** — clic sur "Laisser un avis" dans `DetailsFragment` ouvre `ReviewsFragment`
  via une transaction Fragment (`replace` + `addToBackStack`)
- **`ReviewsAdapter`** — `RecyclerView.Adapter` avec `ViewHolder` et View Binding ;
  chargement des avatars en cercle via Glide ; méthode `setReviews()` pour mise à jour de la liste
- **Layout `fragment_reviews.xml`** — ligne avatar/nom/RatingBar/bouton Valider,
  champ commentaire multilignes, `RecyclerView` en bas de page
- **Layout `item_review.xml`** — ligne individuelle : avatar circulaire, nom, RatingBar, commentaire
- **Distribution des notes** — `DetailsFragment` affiche la moyenne, une `RatingBar`, cinq
  `ProgressBar` de répartition par étoile et le nombre total d'avis
- **Connexion MVVM** — `DetailsFragment` observe le `LiveData` de `DetailsViewModel` ;
  les calculs (`getAverageRating`, `getRatingPercentage`) restent dans le ViewModel

### Fixed

- Avatar de Manon Garcia non affiché au premier chargement du fragment (`ReviewsFragment`)

### Changed

- `RestaurantFakeApi.reviews` migré de `Arrays.asList()` vers `new ArrayList<>(Arrays.asList(…))`
  pour autoriser les mutations à l'exécution
- `RestaurantRepository` — `_reviews` devient un `MutableLiveData` unique avec méthode `addReview()`

### Documentation

- Mise à jour du `README.md` (architecture, instructions de build, fonctionnalités)

---

## [0.1.0] — 2023-09-06

### Added

- Navigation entre fragments via `FragmentContainerView` dans `MainActivity`
- Javadoc sur les méthodes publiques, ressources de chaînes (`strings.xml` EN + FR), polices Jakarta
- Structure initiale du projet : `DetailsFragment`, `DetailsViewModel`, `RestaurantRepository`,
  `RestaurantFakeApi`, module Hilt `AppModule`, `TajMahalApplication`

---

[Unreleased]: https://github.com/Cklla/Interface-MVVM-Java/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/Cklla/Interface-MVVM-Java/compare/v0.1.0...v1.0.0
[0.1.0]: https://github.com/Cklla/Interface-MVVM-Java/releases/tag/v0.1.0
