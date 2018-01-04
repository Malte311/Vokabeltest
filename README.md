# Vokabeltrainer

Mit diesem Programm können Vokabeln aller Art gelernt werden.
Das Programm bietet Karteikarten an, mit denen die Vokabeln geübt werden können. Darüber hinaus können Vokabeltests (mit einem festen Zeitlimit von 7 Minuten) durchgeführt werden.

## Installation

Java muss installiert sein, um dieses Programm auszuführen.
Ist dies erledigt, kann wie folgt vorgegangen werden:

- Dieses Projekt herunterladen
- Das Terminal (bzw. die Eingabeaufforderung) öffnen
- In den Ordner `Vokabeltrainer/src` navigieren
- Den Befehl `javac *.java` eingeben und mit Enter bestätigen
- Den Befehl `jar cvmf META-INF/MANIFEST.MF Dateiname.jar *.class` eingeben und mit Enter bestätigen

Nun wurde die Datei `Dateiname.jar` im selben Verzeichnis erzeugt. Diese Datei beinhaltet das Programm und kann per Doppelklick gestartet werden.

*Hinweis*: Die `.java`-Dateien sowie die `.class`-Dateien werden nicht mehr benötigt, um das Programm nutzen zu können.

Die Stapel werden als einfache Textdateien gespeichert. Dies geschieht unter dem Pfad `Vokabeltrainer/vocab`.

**Achtung**: Das Programm geht davon aus, dass die gespeicherten Textdateien auch tatsächlich unter dem Pfad `Vokabeltrainer/vocab` liegen. Der Pfad kann nicht manuell geändert werden. Die Textdateien sollten nicht manuell bearbeitet werden, sonst kann es vorkommen, dass das Programm nicht mehr korrekt funktioniert.

## Features

Das Programm bietet die folgenden Funktionalitäten:
- Anlegen von Stapeln
- Löschen von Stapeln
- Hinzufügen von Einträgen zu einem Stapel (möchte man ein Wort mit mehreren Bedeutungen hinzufügen, so sind die einzelnen Bedeutungen durch Kommata zu trennen)
- Löschen von Einträgen aus einem Stapel
- Einträge des aktuell ausgewählten Stapels anzeigen lassen
- Karteikarten zum Lernen von Vokabeln
- Einen Vokabeltest durchführen, der automatisch bewertet wird

Ablauf beim Lernen der Vokabeln mit Karteikarten:
- Wörter werden so lange wiederholt, bis die Antwort korrekt ist
- Alle Wörter kommen in beide Richtungen vor
- Am Ende wird eine Statistik (Anzahl richtige Wörter, Anzahl falsche Wörter, Quote in Prozent) ausgegeben

Ablauf eines Vokabeltests:
- Der Nutzer hat 7 Minuten Zeit, den Test durchzuführen
- Am Ende des Tests werden folgende Daten ausgegeben:
  - Anzahl korrekte Eingaben, Anzahl falsche Eingaben, Anteil an korrekten Eingaben (in Prozent)
  - Note (basierend auf den Anteil an korrekten Eingaben)
  - Die Lösungen zu falschen Eingaben werden angezeigt

## Bewertung der Antworten

Die Bewertung erfolgt folgendermaßen:
- Hat ein Begriff mehrere Bedeutungen, reicht es aus, eine richtige Bedeutung zu nennen (weitere Antworten sind egal)
- Die Groß- und Kleinschreibung muss streng eingehalten werden (sonst zählt eine Eingabe als Fehler)
