import sqlite3

# Création de la connexion à la base de données
conn = sqlite3.connect('meteo.db')
curseur = conn.cursor()


# Création de la table pour stocker les données
curseur.execute('''CREATE TABLE IF NOT EXISTS meteo (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    ville TEXT,
                    date TEXT,
                    temperature FLOAT,
                    temperature_res FLOAT,
                    temperature_min FLOAT,
                    temperature_max FLOAT,
                    pression FLOAT,
                    humidite FLOAT,
                    vitesse_vent FLOAT,
                    direction_vent TEXT,
                    lever_soleil TEXT,
                    coucher_soleil TEXT
                )''')

conn.close()
