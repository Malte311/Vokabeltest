# Vokabeltest

Mit diesem Programm können Vokabeltests (mit einem festen Zeitlimit von 7 Minuten) ausgeführt werden. Sobald der Test abgeschlossen ist, gibt das Programm folgende Daten aus:
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
- Im Ordner `/src` müssen die Quelldateien kompiliert werden. Dies geschieht, indem man das Terminal in diesem Ordner öffnet und dann den Befehl `javac *.java` eingibt.
- Nun kann das Programm über die Konsole mittels `java Main` gestartet werden.
Alternativ kann nach dem Kompilieren auch eine `.jar`-Datei erzeugt werden, die per Doppelklick gestartet werden kann. Dazu geben wir den Befehl `jar cvmf META-INF/MANIFEST.MF Dateiname.jar *.class` nach dem Kompilieren in die Konsole ein. Nun sollte eine ausführbare Datei im selben Verzeichnis erzeugt worden sein.

## Benutzung

Das Programm bietet die folgenden Funktionalitäten:
- Anlegen von Stapeln
- Löschen von Stapeln
- Hinzufügen von Einträgen zu einem Stapel
- Löschen von Einträgen aus einem Stapel
- Einträge des aktuell ausgewählten Stapels anzeigen lassen
- Einen Vokabeltest starten, der automatisch bewertet wird

Die Stapel werden als einfache Textdateien gespeichert. Dies geschieht unter dem Pfad `/vocab`.
*Achtung*: Das Programm geht davon aus, dass unter dem Pfad `/vocab` auch tatsächlich nur Textdateien (`.txt`) liegen und dieser Pfad kann auch nicht manuell geändert werden.
