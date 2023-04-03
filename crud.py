import sqlite3


def creer_meteo(ville:str, date:str, temperature:float, temperature_res:float, temperature_min:float, temperature_max:float, pression:float, humidite:float, vitesse_vent:float, direction_vent:str, lever_soleil:str, coucher_soleil:str) -> None:
    conn = sqlite3.connect('meteo.db')
    conn.execute('''INSERT INTO meteo (
                    ville,
                    date,
                    temperature,
                    temperature_res,
                    temperature_min,
                    temperature_max,
                    pression,
                    humidite,
                    vitesse_vent,
                    direction_vent,
                    lever_soleil,
                    coucher_soleil
                ) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                (ville, date, temperature, temperature_res, temperature_min, temperature_max, pression, humidite, vitesse_vent, direction_vent, lever_soleil, coucher_soleil))

    conn.commit()
    conn.close()
