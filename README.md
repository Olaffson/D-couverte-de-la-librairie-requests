# Découverte de la librairie Requests

Récupération de données météorologiques avec **Requests**, **Pandas** et l'API **OpenWeatherMap**.

Le projet interroge l'API OpenWeatherMap pour 20 villes françaises, rassemble les données dans un DataFrame Pandas puis les exporte en CSV. Une base SQLite permet de conserver un historique.

## Contenu du dépôt

| Fichier | Rôle |
|---|---|
| `test.ipynb` | Notebook principal : requêtes à l'API, construction du DataFrame et export CSV |
| `dataframe.csv` | Données exportées par la cellule 3 du notebook (noms de colonnes en anglais) |
| `informations_meteo.csv` | Données exportées par la cellule 6 du notebook, avec la direction du vent en points cardinaux |
| `creer_bd.py` | Crée la base SQLite `meteo.db` et sa table `meteo` |
| `crud.py` | Fonction `creer_meteo()` pour insérer un relevé dans la base |
| `meteo.db` | Base SQLite (vide) |
| `requirements.txt` | Dépendances Python |

## Données récupérées

Pour chaque ville :

- température actuelle, ressentie, minimale et maximale (°C) ;
- pression atmosphérique (hPa) ;
- humidité (%) ;
- vitesse (m/s) et direction du vent ;
- heures de lever et de coucher du soleil, à l'heure locale de la ville.

## Installation

```bash
pip install -r requirements.txt
```

## Configuration

Le notebook lit la clé API OpenWeatherMap dans la variable d'environnement `OPENWEATHER_API_KEY`. Une clé gratuite s'obtient en créant un compte sur [openweathermap.org](https://openweathermap.org/api).

```bash
export OPENWEATHER_API_KEY="votre_cle_api"
```

## Utilisation

Lancer le notebook et exécuter les cellules dans l'ordre :

```bash
jupyter notebook test.ipynb
```

Les fichiers `dataframe.csv` et `informations_meteo.csv` sont régénérés à chaque exécution.

Pour créer la base de données puis y insérer un relevé :

```bash
python creer_bd.py
```

```python
from crud import creer_meteo

creer_meteo("Paris", "2024-01-01", 11.0, 9.5, 10.0, 12.4, 1025, 51, 6.7, "ENE", "07:24:51", "20:22:48")
```

## Avancement

- [x] Récupération des données de 20 villes françaises dans un DataFrame
- [x] Export CSV
- [x] Conversion des heures de lever et de coucher du soleil
- [ ] Bonus : tests des requêtes avec Postman
- [ ] Bonus : historique en base SQL (base et insertion prêtes, pas encore alimentées par le notebook)
- [ ] Bonus : application Streamlit (carte interactive, prévisions sur plusieurs jours, toutes les villes de France)
- [ ] Bonus : conteneur Docker et déploiement sur Azure

## Sujet

Vous êtes des data scientists juniors qui cherchent à se familiariser avec la manipulation des API et la récupération de données en ligne. Vous devez commencer par découvrir les différents types de requêtes HTTP (GET, POST, etc.) et monter en compétences sur la librairie Requests en travaillant sur un notebook. Votre objectif est d'interagir avec l'API OpenWeatherMap pour récupérer et analyser les données météorologiques.

Récupérer les informations suivantes et les stocker dans un DataFrame (pandas) pour 20 villes françaises :

- Température actuelle
- Température ressentie
- Température minimale et maximale
- Pression atmosphérique
- Humidité
- Vitesse du vent
- Direction du vent
- Lever du soleil (attention à bien convertir en information compréhensible pour un humain)
- Coucher du soleil (attention à bien convertir en information compréhensible pour un humain)

Vous devez récupérer les informations actuelles. Les informations seront d'abord stockées dans un DataFrame, puis vous exporterez ce DataFrame dans un fichier CSV.

Vous devrez faire une veille technologique afin de monter en compétences.

**Bonus :**

- Tester les requêtes avec Postman
- Créer une base de données SQL (historique)
- Créer une app avec Streamlit qui affichera les informations météorologiques quand on sélectionne une ville (en requêtant l'API en direct)
- Fonctionnalités de l'application : carte interactive, prévisions sur plusieurs jours, ajout de toutes les villes de France disponibles sur OpenWeatherMap, conteneurisation avec Docker, déploiement sur Azure, interaction entre Streamlit et la base SQL

## Licence

Projet distribué sous licence MIT, voir [LICENSE](LICENSE).
