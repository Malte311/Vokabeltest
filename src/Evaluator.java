import java.awt.*;

public class Evaluator {

    // Die deutschen Vokabeln
    private String[] germanWords;
    // Die englischen Vokabeln
    private String[] englishWords;
    // Die Nutzereingaben
    private String[] inputL;
    private String[] inputL2;
    private String[] inputR;
    private String[] inputR2;
    // Speichert alle falschen Eingaben
    private String[] gErrors;
    private String[] eErrors;
    // Speichert Korrekturen
    private String[] gKorrektur;
    private String[] eKorrektur;
    // Speichert, wie viele Woerter korrekt waren
    private int anzahlKorrekteWoerter = 0;
    private int anzahlFehler = 0;
    // Das Ergebnisfenster
    private Results results;
    // Anzahl Rechtschreibfehler
    private boolean[] anzahlCaseSensitive;
    // Wenn nicht alle Bedeutungen genannt werden
    private boolean[] allMeaningsIncluded;
    // Markierung fuer Farben
    private boolean[] markerG;
    private boolean[] markerE;
    private int abweichung = 0;

    public Evaluator() {
        // Die korrekten Begriffe speichern
        germanWords = Main.getFenster().getData().getGermanWords();
        englishWords = Main.getFenster().getData().getEnglishWords();
        // Laengen anpassen
        if ( germanWords.length > Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabeln() ) {
            String[] temp = new String[Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabeln()];
            for ( int i = 0; i < Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabeln(); i++ ) {
                temp[i] = germanWords[i];
            }
            germanWords = temp;
        }
        if ( englishWords.length > Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabeln() ) {
            String[] temp = new String[Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabeln()];
            for ( int i = 0; i < Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabeln(); i++ ) {
                temp[i] = englishWords[i];
            }
            englishWords = temp;
        }
        anzahlCaseSensitive = new boolean[germanWords.length];
        allMeaningsIncluded = new boolean[germanWords.length];
        markerG = new boolean[germanWords.length];
        markerE = new boolean[germanWords.length];
        for ( boolean b : anzahlCaseSensitive ) {
            b = false;
        }
        for ( boolean b : allMeaningsIncluded ) {
            b = false;
        }
        for ( boolean b : markerG ) {
            b = false;
        }
        for ( boolean b : markerE ) {
            b = false;
        }

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
        // linke Spalte 1
        TextField[] arr = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL();
        if ( arr != null ) {
            inputL = new String[arr.length];
            for ( int i = 0; i < arr.length; i++ ) {
                inputL[i] = arr[i].getText().trim();
                // System.out.println( inputL[i] );
            }
        }

        // linke Spalte 2
        arr = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL2();
        if ( arr != null ) {
            inputL2 = new String [arr.length];
            for ( int i = 0; i < arr.length; i++ ) {
                inputL2[i] = arr[i].getText().trim();
                // System.out.println( inputL2[i] );
            }
        }

        // rechte Spalte 1
        arr = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR();
        if ( arr != null ) {
            inputR = new String[arr.length];
            for ( int i = 0; i < arr.length; i++ ) {
                inputR[i] = arr[i].getText().trim();
                // System.out.println( inputR[i] );
            }
        }

        // rechte Spalte 2
        arr = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR2();
        if ( arr != null ) {
            inputR2 = new String[arr.length];
            for ( int i = 0; i < arr.length; i++ ) {
                inputR2[i] = arr[i].getText().trim();
                // System.out.println( inputR2[i] );
            }
        }

        // Arrays fuer spaeter initialisieren
        gErrors = new String[germanWords.length];
        eErrors = new String[germanWords.length];
        gKorrektur = new String[germanWords.length];
        eKorrektur = new String[germanWords.length];
    }

    /**
     * Prueft, wo Fehler vorhanden sind
     */
    public void evaluate() {
        // Index fuer Korrektur Arrays und Fehler Arrays
        int index = 0;
        for ( int i = 0; i < germanWords.length; i++ ) {
            // System.out.println( "DE: " + germanWords[i] + " = " + inputL[i] );
            // System.out.println( "EN: " + englishWords[i] + " = " + inputR[i] );
            // erste Spalte
            if ( i < Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte() ) {
                // Pruefen, ob ein Wort mehrere Bedeutungen hat
                String[] inputG = inputL[i].split( "[,;]" );
                String[] loesG = germanWords[i].split( "[,;]" );
                String[] inputE = inputR[i].split( "[,;]" );
                String[] loesE = englishWords[i].split( "[,;]" );
                // Bedeutung ist eindeutig
                if ( ( inputG.length <= 1 ) && ( inputE.length <= 1 ) && ( loesG.length <= 1 ) && ( loesE.length <= 1 ) ) {
                    // richtiges Wort
                    if ( ( germanWords[i].equals( inputL[i] ) ) && ( englishWords[i].equals( inputR[i] ) ) ) {
                        anzahlKorrekteWoerter++;
                    }
                    // falsches Wort
                    else {
                        // Wort richtig, aber Gross- bzw. Kleinschreibung falsch
                        if ( ( germanWords[i].toLowerCase().equals( inputL[i].toLowerCase() ) ) && ( englishWords[i].toLowerCase().equals( inputR[i].toLowerCase() ) ) ) {
                            anzahlKorrekteWoerter++;
                            anzahlCaseSensitive[i] = true;
                            gErrors[index] = inputL[i];
                            eErrors[index] = inputR[i];
                            gKorrektur[index] = germanWords[i];
                            eKorrektur[index] = englishWords[i];
                            markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                            markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                            index++;
                        }
                        // Anordnung der Zeichen nicht korrekt
                        else {
                            gErrors[index] = inputL[i];
                            eErrors[index] = inputR[i];
                            gKorrektur[index] = germanWords[i];
                            eKorrektur[index] = englishWords[i];
                            markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                            markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                            index++;
                            anzahlFehler++;
                        }
                    }
                }
                // Wenn ein Wort mehrere Bedeutungen hat
                else {
                    // englisches Wort hat mehrere Bedeutungen
                    if ( ( inputG.length > 1 ) || ( loesG.length > 1 ) ) {
                        for ( int k = 0; k < inputG.length; k++ ) {
                            if ( ( inputG[k].length() > 0 ) && ( germanWords[i].contains( inputG[k].trim() ) ) && ( englishWords[i].equals( inputR[i] ) ) ) {
                                anzahlKorrekteWoerter++;
                                for ( int g = 0; g < inputG.length; g++ ) {
                                    if ( ( loesG.length > inputG.length ) || !( ( inputG[g].length() > 0 ) && ( germanWords[i].contains( inputG[g].trim() ) ) && ( englishWords[i].equals( inputR[i] ) ) ) ) {
                                        allMeaningsIncluded[i] = true;
                                        abweichung++;
                                        gErrors[index] = inputL[i];
                                        eErrors[index] = inputR[i];
                                        gKorrektur[index] = germanWords[i];
                                        eKorrektur[index] = englishWords[i];
                                        markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                        markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                        index++;
                                        break;
                                    }
                                }
                                break;
                            }
                            else if ( ( inputG[k].length() > 0 ) && ( germanWords[i].toLowerCase().contains( inputG[k].toLowerCase().trim() ) )
                            && ( englishWords[i].toLowerCase().equals( inputR[i].toLowerCase() ) ) ) {
                                anzahlKorrekteWoerter++;
                                anzahlCaseSensitive[i] = true;
                                gErrors[index] = inputL[i];
                                eErrors[index] = inputR[i];
                                gKorrektur[index] = germanWords[i];
                                eKorrektur[index] = englishWords[i];
                                markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                index++;
                                break;
                            }
                            else if ( k == ( inputG.length - 1 ) ) {
                                // Wort richtig, aber Gross- bzw. Kleinschreibung falsch
                                if ( ( inputG[k].length() > 0 ) && ( germanWords[i].toLowerCase().contains( inputG[k].toLowerCase().trim() ) )
                                && ( englishWords[i].toLowerCase().equals( inputR[i].toLowerCase() ) ) ) {
                                    anzahlKorrekteWoerter++;
                                    anzahlCaseSensitive[i] = true;
                                    gErrors[index] = inputL[i];
                                    eErrors[index] = inputR[i];
                                    gKorrektur[index] = germanWords[i];
                                    eKorrektur[index] = englishWords[i];
                                    markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                    markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                    index++;
                                }
                                else {
                                    gErrors[index] = inputL[i];
                                    eErrors[index] = inputR[i];
                                    gKorrektur[index] = germanWords[i];
                                    eKorrektur[index] = englishWords[i];
                                    markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                    markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                    index++;
                                    anzahlFehler++;
                                }
                            }
                        }
                    }
                    // deutsches Wort hat mehrere Bedeutungen
                    else {
                        for ( int k = 0; k < inputE.length; k++ ) {
                            if ( ( inputE[k].length() > 0 ) && ( englishWords[i].contains( inputE[k].trim() ) ) && ( germanWords[i].equals( inputL[i] ) ) ) {
                                anzahlKorrekteWoerter++;
                                for ( int g = 0; g < inputG.length; g++ ) {
                                    if ( ( loesE.length > inputE.length ) || !( ( inputE[g].length() > 0 ) && ( englishWords[i].contains( inputE[g].trim() ) ) && ( germanWords[i].equals( inputL[i] ) ) ) ) {
                                        allMeaningsIncluded[i] = true;
                                        abweichung++;
                                        gErrors[index] = inputL[i];
                                        eErrors[index] = inputR[i];
                                        gKorrektur[index] = germanWords[i];
                                        eKorrektur[index] = englishWords[i];
                                        markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                        markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                        index++;
                                        break;
                                    }
                                }
                                break;
                            }
                            else if ( ( inputE[k].length() > 0 ) && ( inputE[k].length() > 0 ) && ( englishWords[i].toLowerCase().contains( inputE[k].toLowerCase().trim() ) )
                            && ( germanWords[i].toLowerCase().equals( inputL[i].toLowerCase() ) ) ) {
                                anzahlKorrekteWoerter++;
                                anzahlCaseSensitive[i] = true;
                                gErrors[index] = inputL[i];
                                eErrors[index] = inputR[i];
                                gKorrektur[index] = germanWords[i];
                                eKorrektur[index] = englishWords[i];
                                markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                index++;
                                break;
                            }
                            else if ( k == ( inputE.length - 1 ) ) {
                                // Wort richtig, aber Gross- bzw. Kleinschreibung falsch
                                if ( ( inputE[k].length() > 0 ) && ( englishWords[i].toLowerCase().contains( inputE[k].toLowerCase().trim() ) )
                                && ( germanWords[i].toLowerCase().equals( inputL[i].toLowerCase() ) ) ) {
                                    anzahlKorrekteWoerter++;
                                    anzahlCaseSensitive[i] = true;
                                    gErrors[index] = inputL[i];
                                    eErrors[index] = inputR[i];
                                    gKorrektur[index] = germanWords[i];
                                    eKorrektur[index] = englishWords[i];
                                    markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                    markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                    index++;
                                }
                                else {
                                    gErrors[index] = inputL[i];
                                    eErrors[index] = inputR[i];
                                    gKorrektur[index] = germanWords[i];
                                    eKorrektur[index] = englishWords[i];
                                    markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()[i].isEditable();
                                    markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()[i].isEditable();
                                    index++;
                                    anzahlFehler++;
                                }
                            }
                        }
                    }
                }
            }
            // zweite Spalte
            else if ( inputL2 != null && inputR2 != null ) {
                if ( inputL2.length > 0 && inputR2.length > 0 ) {
                    // Pruefen, ob ein Wort mehrere Bedeutungen hat
                    String[] inputG = inputL2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].split( "[,;]" );
                    String[] loesG = germanWords[i].split( "[,;]" );
                    String[] inputE = inputR2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].split( "[,;]" );
                    String[] loesE = englishWords[i].split( "[,;]" );
                    // Bedeutung ist eindeutig
                    if ( ( inputG.length <= 1 ) && ( inputE.length <= 1 ) && ( loesG.length <= 1 ) && ( loesE.length <= 1 ) ) {
                        // richtiges Wort
                        if ( ( germanWords[i].equals( inputL2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()] ) )
                        && ( englishWords[i].equals( inputR2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()] ) ) ) {
                            anzahlKorrekteWoerter++;
                        }
                        // falsches Wort
                        else {
                            gErrors[index] = inputL2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()];
                            eErrors[index] = inputR2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()];
                            gKorrektur[index] = germanWords[i];
                            eKorrektur[index] = englishWords[i];
                            markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()
                            [i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].isEditable();
                            markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()
                            [i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].isEditable();
                            index++;
                            anzahlFehler++;
                        }
                    }
                    // Bedeutung ist nicht eindeutig
                    else {
                        // englisches Wort hat mehrere Bedeutungen
                        if ( ( inputG.length > 1 ) || ( loesG.length > 1 ) ) {
                            for ( int k = 0; k < inputG.length; k++ ) {
                                if ( ( inputG[k].length() > 0 ) && ( germanWords[i].contains( inputG[k].trim() ) )
                                && ( englishWords[i].equals( inputR2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()] ) ) ) {
                                    anzahlKorrekteWoerter++;
                                    break;
                                }
                                else if ( k == ( inputG.length - 1 ) ) {
                                    gErrors[index] = inputL2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()];
                                    eErrors[index] = inputR2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()];
                                    gKorrektur[index] = germanWords[i];
                                    eKorrektur[index] = englishWords[i];
                                    markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()
                                    [i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].isEditable();
                                    markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()
                                    [i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].isEditable();
                                    index++;
                                    anzahlFehler++;
                                }
                            }
                        }
                        // deutsches Wort hat mehrere Bedeutungen
                        else {
                            for ( int k = 0; k < inputE.length; k++ ) {
                                if ( ( inputE[k].length() > 0 ) && ( englishWords[i].contains( inputE[k].trim() ) )
                                && ( germanWords[i].equals( inputL2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()] ) ) ) {
                                    anzahlKorrekteWoerter++;
                                    break;
                                }
                                else if ( k == ( inputE.length - 1 ) ) {
                                    gErrors[index] = inputL2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()];
                                    eErrors[index] = inputR2[i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()];
                                    gKorrektur[index] = germanWords[i];
                                    eKorrektur[index] = englishWords[i];
                                    markerG[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsL()
                                    [i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].isEditable();
                                    markerE[index] = Main.getFenster().getHandler().getTrainer().getTestscreen().getInputFieldsR()
                                    [i-Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte()].isEditable();
                                    index++;
                                    anzahlFehler++;
                                }
                            }
                        }
                    }
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
        results = new Results( anzahlKorrekteWoerter, anzahlFehler, gErrors, eErrors, gKorrektur, eKorrektur, anzahlCaseSensitive, allMeaningsIncluded, markerG, markerE, abweichung );
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
    * Gibt den deutschen Input der zweiten Spalte zurueck
    * @return inputL2 Der deutsche Input der zweiten Spalte
    */
    public String[] getInputL2() {
        return inputL2;
    }

    /**
    * Gibt den englischen Input der ersten Spalte zurueck
    * @return inputR Der englische Input der ersten Spalte
    */
    public String[] getInputR() {
        return inputR;
    }

    /**
    * Gibt den englischen Input der zweiten Spalte zurueck
    * @return inputR Der englische Input der zweiten Spalte
    */
    public String[] getInputR2() {
        return inputR2;
    }

    /**
    * Gibt fehlerhafte deutsche Woerter zurueck
    * @return gErrors Die fehlerhaften deutschen Woerter
    */
    public String[] getGErrors() {
        return gErrors;
    }

    /**
    * Gibt fehlerhafte englische Woerter zurueck
    * @return eErrors Die fehlerhaften englischen Woerter
    */
    public String[] getEErrors() {
        return eErrors;
    }

    /**
     * Gibt die korrigierten deutschen Woerter zurueck
     * @return gKorrektur Die korrigierten deutschen Woerter
     */
     public String[] getGKorrektur() {
         return gKorrektur;
     }

     /**
      * Gibt die korrigierten englischen Woerter zurueck
      * @return eKorrektur Die korrigierten englischen Woerter
      */
      public String[] getEKorrektur() {
          return eKorrektur;
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

      /**
       * Gibt die Anzahl der Woerter, dessen Gross- bzw. Kleinschreibung falsch ist, zurueck
       * @return anzahlCaseSensitive Die Anzahl der Woerter, dessen Gross- bzw. Kleinschreibung falsch ist
       */
      public boolean[] getAnzahlCaseSensitive() {
          return anzahlCaseSensitive;
      }

      /**
       * Gibt das Array, welches speichert, wo nicht alle Bedeutungen genannt wurden, zurueck
       * @return allMeaningsIncluded Speichert, wo nicht alle Bedeutungen genannt wurden
       */
      public boolean[] getAllMeaningsIncluded() {
          return allMeaningsIncluded;
      }

      /**
       * Gibt den Marker fuer die Farbwahl zurueck
       * @return marker Der Marker fuer die Farbwahl
       */
      public boolean[] getMarkerG() {
          return markerG;
      }

      /**
       * Gibt den Marker fuer die Farbwahl zurueck
       * @return marker Der Marker fuer die Farbwahl
       */
      public boolean[] getMarkerE() {
          return markerE;
      }

      /**
       * Gibt die Abweichung zurueck (Anzahl korrekte Woerter, die nicht perfekt waren)
       * @return abweichung Die Abweichung
       */
      public int getAbweichung() {
          return abweichung;
      }
}
