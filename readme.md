#Grünflächen Kataster Köln

Dieser Service liest das Shape-File für das Grünflächekataster von [Gruenflaechenkataster Koeln Flaechentypen](http://offenedaten-koeln.de/dataset/gruenflaechenkataster-koeln-flaechentypen) aus und bietet die so gewonnen Informationen als GeoJson - Information an.

## Status

In Entwicklung/ lauffähig/ nicht getestet

## Ziele

Es soll ein Service erstellt werden, der die von der Stadt Köln zur Verfügung gestellten Informationen bzgl. der Grünflächen als GeoJson formatiert. Desweiteren soll es möglich sein, gezielt Flächentypen abfragen zu können. Folgende Flächentypen werden unterstützt:

- [ 2: Kleingärten](https://github.com/codeforcologne/gruenflaechenkoeln/blob/master/src/main/resources/2.json)
- [ 4: Spielplätze](https://github.com/codeforcologne/gruenflaechenkoeln/blob/master/src/main/resources/4.json)
- [ 7: Grünanlagen](https://github.com/codeforcologne/gruenflaechenkoeln/blob/master/src/main/resources/7.json)
- [ 8: Friedhöfe](https://github.com/codeforcologne/gruenflaechenkoeln/blob/master/src/main/resources/8.json)
- [ 9: Biotopflächen](https://github.com/codeforcologne/gruenflaechenkoeln/blob/master/src/main/resources/9.json)
- [11: Sondergrünflächen](https://github.com/codeforcologne/gruenflaechenkoeln/blob/master/src/main/resources/11.json)
- [12: Forsteigene Flächen](https://github.com/codeforcologne/gruenflaechenkoeln/blob/master/src/main/resources/12.json) 

Hierfür werden REST-Endpoints eingerichtet. 

## GeoTools

Zur Interpretation der Shape-Dateien wird das Projekt [GeoTools](http://geotools.org/) verwendet (vgl. auch [GeoTools Quickstart](http://docs.geotools.org/latest/userguide/tutorial/index.html))

## Projektion

Die Polygone stehen als [Gauß-Krüger Koordinatensystem](http://wiki.openstreetmap.org/wiki/DE:Gau%C3%9F-Kr%C3%BCger) zur Verfügung. Für die Verwendung von GeoJson bietet sich aber die Koordination in Dezimalgrad (WGS84) umzuwandeln. Für manuelle Tests kann dies mit dem [Online Koordinaten Umrechner für WGS84, UTM, CH1903, UTMREF(MGRS), Gauß-Krüger, NAC](http://www.deine-berge.de/Rechner/Koordinaten) geschehen.

## Vorgehensweise

Der Service geht folgendermaßen vor:

1. Download der ZIP-Datei in das temporäre Verzeichnis des verwendeten Betriebssystems
2. Entpacken der im ZIP-File vorhanden Dateien
3. Einlesen des shapes
4. ggf. Filtern nach Flächentyp
5. Umwandeln der Projektion
6. Umwandeln in GeoJson

## Schnittstellen

### /gruenflaechenkoeln/service/flaechen

Diese Schnittstelle liefert alle Grünflächen im GeoJson Format.

### /gruenflaechenkoeln/service/flaechen?resource

Da die Berechnung auf Systemen mit geringen Resourcen länger dauern kann, liegen bereits fertig berechnete Ergebnisse im resources-Verzeichnis. Diese werden von der Applikation aus über den Parameter 'resource' erreicht.D

### /gruenflaechenkoeln/service/flaechen/{id}

Diese Schnittstelle liefert ein Subset von Flächen in Abhängigkeit von der Flächen-Typ-Id. Folgende Flächen stehen zu Zeit zur Verfügung:

- 2: Kleingärten
- 4: Spielplätze
- 7: Grünanlagen
- 8: Friedhöfe
- 9: Biotopflächen
- 11: Sondergrünflächen
- 12: Forsteigene Flächen


### /gruenflaechenkoeln/service/flaechen/{id}?resourcee
Da die Berechnung auf Systemen mit geringen Resourcen (z.B. Raspberry Pi) länger dauern kann, liegen bereits fertig berechnete Ergebnisse im resources-Verzeichnis. Diese werden von der Applikation aus über den Parameter 'resource' erreicht. Diese json-Dateien finden sich auch auf github und können von dort direkt bezogen werden:

- [/gruenflaechenkoeln/service/flaechen/2?resource (Kleingärten)](https://raw.githubusercontent.com/codeforcologne/gruenflaechenkoeln/master/src/main/resources/2.json)
- [/gruenflaechenkoeln/service/flaechen/4?resource (Spielplätze)](https://raw.githubusercontent.com/codeforcologne/gruenflaechenkoeln/master/src/main/resources/4.json)
- [/gruenflaechenkoeln/service/flaechen/7?resource (Grünanlagen)](https://raw.githubusercontent.com/codeforcologne/gruenflaechenkoeln/master/src/main/resources/7.json)
- [/gruenflaechenkoeln/service/flaechen/8?resource (Friedhöfe)](https://raw.githubusercontent.com/codeforcologne/gruenflaechenkoeln/master/src/main/resources/8.json)
- [/gruenflaechenkoeln/service/flaechen/9?resource (Biotopflächen)](https://raw.githubusercontent.com/codeforcologne/gruenflaechenkoeln/master/src/main/resources/9.json)
- [/gruenflaechenkoeln/service/flaechen/11?resource (Sondergrünflächen)](https://raw.githubusercontent.com/codeforcologne/gruenflaechenkoeln/master/src/main/resources/11.json)
- [/gruenflaechenkoeln/service/flaechen/12?resource (Forsteigene Flächen)](https://raw.githubusercontent.com/codeforcologne/gruenflaechenkoeln/master/src/main/resources/12.json) 


## Installationn

Der Service kann über git heruntergeladen werden

    git clone https://github.com/codeforcologne/gruenflaechenkoeln.git
    
Danach muss er noch installiert werden. Dabei wird vorausgesetzt, dass auf dem Rechner mindestens JAVA 7 und das aktuelle [maven](https://maven.apache.org/) installiert ist. 

Wechsel ins Verzeichnis

    cd gruenflaechenkoeln

Aufruf des build-Skripts:

    mvn clean install
    
Danach befindet sich im Verzeichnis target die Datei 'gruenflaechenkoeln.war'. Diese kann z.B. in einen Tomcat Server deployed werden.

    mv gruenflaechenkoeln.war $CATALINA_HOME/webapps

Alternativ kann der Service direkt von der Kommandozeile aus gestartet werden:

    mvn jetty:run
    
Der Service kann dann z.B. unter [http://localhost:8080/service/flaechen/4](http://localhost:8080/service/flaechen/4) aufgerufen werden.

## License

<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons Lizenzvertrag" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />Dieses Werk ist lizenziert unter einer <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Namensnennung - Weitergabe unter gleichen Bedingungen 4.0 International Lizenz</a>.
