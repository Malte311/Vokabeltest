import java.awt.*;
import javax.swing.*;

public class Evaluator {

    // Die deutschen Vokabeln
    private String[] germanWords;
    // Die englischen Vokabeln
    private String[] englishWords;

    // Die Nutzereingaben
    private String[] inputL;
    private String[] inputR;
    // Speichert alle falschen Eingaben
    private String[] vorgabe;
    private String[] errors;
    private String[] corrections;

    // Speichert, wie viele Woerter korrekt waren
    private int anzahlKorrekteWoerter = 0;
    // Speichert, wie viele Woerter falsch waren
    private int anzahlFehler = 0;

    // Das Ergebnisfenster
    private Results results;

    public Evaluator() {
        // Die korrekten Begriffe speichern
        germanWords = Main.getFenster().getData().getGermanWords();
        englishWords = Main.getFenster().getData().getEnglishWords();

        // Die Eingaben holen
        getInput();
        // Bewerten
        evaluate();
        // Ergebnisse ausgeben
        showResults();
    }

    /**
     * Speichert die Eingaben des Nutzers
     */
    public void getInput() {
        // linke Spalte
        JTextField[] arr = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL();
        if ( arr != null ) {
            inputL = new String[arr.length];
            for ( int i = 0; i < arr.length; i++ ) {
                inputL[i] = arr[i].getText().trim();
            }
        }

        // rechte Spalte
        arr = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR();
        if ( arr != null ) {
            inputR = new String[arr.length];
            for ( int i = 0; i < arr.length; i++ ) {
                inputR[i] = arr[i].getText().trim();
            }
        }

        // Arrays fuer spaeter initialisieren
        vorgabe = new String[germanWords.length];
        errors = new String[germanWords.length];
        corrections = new String[germanWords.length];
    }

    /**
     * Prueft, wo Fehler vorhanden sind
     */
    public void evaluate() {
        int index = 0;
        String in;
        String correct;
        String[] parts;
        boolean ger = true;
        for ( int i = 0; i < inputL.length; i++ ) {
            // Richtiges TextFeld auswaehlen
            if ( Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable() ) {
                vorgabe[index] = englishWords[i];
                in = inputL[i];
                correct = germanWords[i];
                ger = true;
            }
            else {
                vorgabe[index] = germanWords[i];
                in = inputR[i];
                correct = englishWords[i];
                ger = false;
            }
            // Zunaechst zerlegen wir den Input in einzelne Bedeutungen
            if ( in.contains( "," ) ) {
                parts = in.split( "," );
            }
            else if ( in.contains( ";" ) ) {
                parts = in.split( ";" );
            }
            else {
                parts = new String[]{ in };
            }

            // Nun durchlaufen wir die einzelnen Bedeutungen
            for ( int j = 0; j < parts.length; j++ ) {
                // Wenn Input enthalten ist, ist das Wort schonmal korrekt
                if ( !parts[j].equals( "" ) && ger && germanWords[i].contains( parts[j] ) ) {
                    anzahlKorrekteWoerter++;
                    break;
                }
                else if ( !parts[j].equals( "" ) && !ger && englishWords[i].contains( parts[j] ) ) {
                    anzahlKorrekteWoerter++;
                    break;
                }

                // Ansonsten war die Eingabe leider falsch (wenn alle Bedeutungen durchprobiert wurden)
                else if ( j == parts.length - 1 ) {
                    errors[index] = in;
                    corrections[index] = correct;
                    anzahlFehler++;
                    index++;
                }
            }
        }
    }

    /**
     * Zeigt die Ergebnisse an
     */
    public void showResults() {
        // Aktuellen Frame beenden
        Main.getFenster().getHandler().getTrainer().getTestscreen().dispose();
        // Ergebnisfenster oeffnen
        results = new Results( anzahlKorrekteWoerter, anzahlFehler, vorgabe, errors, corrections );
    }

    /**
     * Gibt die deutschen Woerter zurueck
     * @return germanWords Die deutschen Woerter
     */
    public String[] getGermanWords() {
        return germanWords;
    }

    /**
     * Gibt die englischen Woerter zurueck
     * @return englishWords Die englischen Woerter
     */
    public String[] getEnglishWords() {
        return englishWords;
    }

    /**
     * Gibt den deutschen Input der ersten Spalte zurueck
     * @return inputL Der deutsche Input der ersten Spalte
     */
    public String[] getInputL() {
        return inputL;
    }

    /**
    * Gibt den englischen Input der ersten Spalte zurueck
    * @return inputR Der englische Input der ersten Spalte
    */
    public String[] getInputR() {
        return inputR;
    }

    /**
    * Gibt fehlerhafte deutsche Woerter zurueck
    * @return gErrors Die fehlerhaften deutschen Woerter
    */
    public String[] getErrors() {
        return errors;
    }

    /**
    * Gibt fehlerhafte englische Woerter zurueck
    * @return eErrors Die fehlerhaften englischen Woerter
    */
    public String[] getCorrections() {
        return corrections;
    }

      /**
       * Gibt die Anzahl der richtigen Woerter zurueck
       * @return anzahlKorrekteWoerter Die Anzahl der richtigen Woerter
       */
      public int getAnzahlKorrekteWoerter() {
          return anzahlKorrekteWoerter;
      }

      /**
       * Gibt die Anzahl der falschen Woerter zurueck
       * @return anzahlFehler Die Anzahl der falschen Woerter
       */
      public int getAnzahlFehler() {
          return anzahlFehler;
      }

      /**
       * Gibt das Ergebnisfenster zurueck
       * @return results Das Ergebnisfenster
       */
      public Results getResults() {
          return results;
      }
}
