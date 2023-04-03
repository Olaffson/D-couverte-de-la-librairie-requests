# D-couverte-de-la-librairie-requests
Requests, Pandas et OpenWeatherMap

Contexte du projet

Vous êtes des data scientists juniors qui cherchent à se familiariser avec la manipulation des API et la récupération de données en ligne. Vous devez commencer par découvrir les différents types de requêtes HTTP (GET, POST, etc.) et monter en compétences sur la librairie Requests en travaillant sur un notebook. Votre objectif est d'interagir avec l'API OpenWeatherMap pour récupérer et analyser les données météorologiques.

​

Récupérer les informations suivantes et stocker les dans un DataFrame (pandas) pour 20 villes françaises :

    Température actuelle
    Température ressentie
    Température minimale et maximale
    Pression atmosphérique
    Humidité
    Vitesse du vent
    Direction du vent
    Lever du soleil (Attention à bien convertir en information compréhensible pour un humain)
    Coucher du soleil (Attention à bien convertir en information compréhensible pour un humain)

​

Vous devez récupérer les informations actuelles. Les informations seront d'abord stockés dans un DataFrame et ensuite vous exporterez ce dataframe dans un fichier csv.

​

Vous devrez faire une veille technologique afin de monter en compétences.

​

Bonus :

    Tester les requêtes avec postman
    Créer une base de données SQL (historique)
    Créer une app avec streamlit qui affichera les informations météorologiques quand on sélectionne une ville (requêté en direct l'API pour avoir les informations en direct)
    Features de l'application : Carte intéractive, prévision à plusieurs jours, ajouter toutes les villes de France (celle disponible sur openweathermap), mettre votre application streamlit dans un docker, déployer votre application sur Azure, Intéraction entre streamlit et la BDD SQL

