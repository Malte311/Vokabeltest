# Vokabeltest

Mit diesem Programm können Vokabeltests (mit maximal 50 Vokabeln und einem festen Zeitlimit von 7 Minuten) ausgeführt werden. Dieses Programm beinhaltet (bisher) keine Flashcards/Karteikarten!
Sobald der Test abgeschlossen ist, gibt das Programm folgende Daten aus:
- Anzahl korrekte Eingaben
- Anzahl falsche Eingaben
- Anteil an korrekten Eingaben (in Prozent)
- Note (basierend auf den Anteil an korrekten Eingaben)

Die Bewertung erfolgt dabei folgendermaßen:
- Hat ein Begriff mehrere Bedeutungen, reicht es aus, eine richtige Bedeutung zu nennen
- Die Groß- und Kleinschreibung muss streng eingehalten werden (sonst zählt eine Eingabe als Fehler)
- Am Ende des Tests werden die Lösungen zu falschen Eingaben angezeigt

## Installation

Java muss installiert sein, um dieses Programm auszuführen.
Ist dies erledigt, kann wie folgt vorgegangen werden:
- Das Terminal öffnen
- In den Ordner `Vokabeltest/src` navigieren
- Den Befehl `javac *.java` eingeben (dieser kompiliert die `.java`-Quelldateien und erzeugt `.class`-Dateien).
- Nun kann das Programm über die Konsole mittels `java Main` gestartet werden. Um das Programm auf diese Weise starten zu können, müssen die `.class`-Dateien vorhanden sein.

Alternativ kann nach dem Kompilieren auch eine `.jar`-Datei erzeugt werden, die per Doppelklick gestartet werden kann: 
- Das Terminal öffnen
- In den Ordner `Vokabeltest/src` navigieren
- Den Befehl `javac *.java` eingeben
- Den Befehl `jar cvmf META-INF/MANIFEST.MF Dateiname.jar *.class` nach dem Kompilieren in die Konsole eingeben. Nun sollte eine ausführbare `.jar`-Datei im selben Verzeichnis erzeugt worden sein. Die `.java`- sowie die `.class`-Dateien werden nicht benötigt, um das Programm nutzen zu können.

## Benutzung

Das Programm bietet die folgenden Funktionalitäten:
- Anlegen von Stapeln
- Löschen von Stapeln
- Hinzufügen von Einträgen zu einem Stapel
- Löschen von Einträgen aus einem Stapel
- Einträge des aktuell ausgewählten Stapels anzeigen lassen
- Einen Vokabeltest starten, der automatisch bewertet wird

Die Stapel werden als einfache Textdateien gespeichert. Dies geschieht unter dem Pfad `/vocab`.

**Achtung**: Das Programm geht davon aus, dass die gespeicherten Textdateien auch tatsächlich unter dem Pfad `/vocab` liegen. Der Pfad kann nicht manuell geändert werden.
