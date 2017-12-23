import java.awt.*;

import javax.swing.*;

public class Vokabeltrainer {

    // Speichert die Anzahl der Vokabeln fuer einen Test
    private int anzahlVokabeln;
    // Speichert den Stapel fuer den Test
    private String list;
    // Speichert die deutschen Woerter
    private String[] germanWords;
    // Speichert die englischen Woerter
    private String[] englishWords;
    // Speichert das Fenster des Tests
    private Testscreen testscreen;

    public Vokabeltrainer( String list ) {
        // Liste abspeichern und einlesen
        this.list = list;
        try {
            Main.getFenster().getData().readTxt( list );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        // Nun speichern wir die vorhandenen Daten ab
        germanWords = Main.getFenster().getData().getGermanWords();
        englishWords = Main.getFenster().getData().getEnglishWords();

        // Anzahl der Vokabeln festlegen (germanWords.length == englishWords.length)
        anzahlVokabeln = germanWords.length;
    }

    /**
     * Startet den Test
     */
    public void startTest() {
        testscreen = new Testscreen();
    }

    /**
     * Gibt die Anzahl der Vokabeln wieder
     * @return anzahlVokabeln Die Anzahl der Vokabeln
     */
    public int getAnzahlVokabeln() {
        return anzahlVokabeln;
    }

    /**
     * Setzt die Anzahl der Vokabeln
     * @param anzahl Die neue Anzahl
     */
    public void setAnzahlVokabeln( int anzahl ) {
        anzahlVokabeln = anzahl;
    }

    /**
     * Gibt den aktuell ausgewaehlten Stapel zurueck
     * @return list Der aktuell ausgewaehlte Stapel
     */
    public String getList() {
        return list;
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
     * @return englishWords Die englsichen Woerter
     */
    public String[] getEnglishWords() {
        return englishWords;
    }

    /**
     * Gibt den Testscreen zurueck
     * @return testscreen Der Testscreen
     */
    public Testscreen getTestscreen() {
        return testscreen;
    }
}
