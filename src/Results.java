import javax.swing.*;
import java.awt.*;

public class Results extends JFrame {

    // Abmessungen des Fensters
    private final int BREITE = 1024;
    private final int HOEHE = 768;
    // Ergebnisdaten (ohne Getter)
    private int anzahlKorrekteWoerter;
    private int anzahlFehler;
    private String[] gErrors;
    private String[] eErrors;
    private String[] gKorrektur;
    private String[] eKorrektur;
    // Label
    private JLabel anzahlKorrekt;
    private JLabel anzahlFalsch;
    // Button
    private JButton backToMainMenu;
    // handler
    private ResultHandler handler;
    // Anzahl Rechtschreibfehler
    private boolean[] anzahlCaseSensitive;
    // Wenn Bedeutungen fehlen
    private boolean[] allMeaningsIncluded;
    // Marker fuer Farbwahl
    private boolean[] markerG;
    private boolean[] markerE;
    private int abweichung = 0;

    public Results( int anzahlKorrekteWoerter, int anzahlFehler, String[] gErrors, String[] eErrors,
    String[] gKorrektur, String[] eKorrektur, boolean[] anzahlCaseSensitive, boolean[] allMeaningsIncluded, boolean[] markerG, boolean[] markerE, int abweichung ) {
        // Frame initialisieren
        super( "Bewertung" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( BREITE, HOEHE );
        setResizable( false );
        setLocationRelativeTo( Main.getFenster().getHandler().getTrainer().getTestscreen() );
        setLayout( null );
        setVisible( true );

        // Daten holen
        this.abweichung = abweichung;
        this.anzahlKorrekteWoerter = anzahlKorrekteWoerter;
        this.anzahlFehler = anzahlFehler;
        this.gErrors = gErrors;
        this.eErrors = eErrors;
        this.gKorrektur = gKorrektur;
        this.eKorrektur = eKorrektur;
        this.anzahlCaseSensitive = anzahlCaseSensitive;
        this.allMeaningsIncluded = allMeaningsIncluded;
        this.markerG = markerG;
        this.markerE = markerE;

        // Komponenten initialisieren
        initLabel();
        initButton();

        // Korrekte Loesungen von falschen Vokabeln anzeigen
        // (fuer maximal anzahlVokabeln / 2 = 50 / 2 = 25 falsche Woerter)
        if ( anzahlFehler > Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte() ) {
            anzahlFehler = Main.getFenster().getHandler().getTrainer().getTestscreen().getMaxAnzahlVokabelnProSpalte();
        }
        showSolution( gErrors, eErrors, 10, anzahlFehler + abweichung, 1 );
        showSolution( gKorrektur, eKorrektur, BREITE / 2 + 100, anzahlFehler + abweichung, 2 );
    }

    /**
     * Initialisiert die Label
     */
    public void initLabel() {
        anzahlKorrekt = new JLabel( "Anzahl korrekte W\u00F6rter: " + anzahlKorrekteWoerter );
        add( anzahlKorrekt );
        anzahlKorrekt.setBounds( 10, 10, 180, 25 );
        anzahlKorrekt.setVisible( true );
        anzahlFalsch = new JLabel( "Anzahl falsche W\u00F6rter: " + anzahlFehler );
        add( anzahlFalsch );
        anzahlFalsch.setBounds( 10, 40, 180, 25 );
        anzahlFalsch.setVisible( true );
    }

    /**
     * Initialisiert den Button zum Zurueckkehren
     */
    public void initButton() {
        backToMainMenu = new JButton( "Hauptmen\u00FC" );
        add( backToMainMenu );
        backToMainMenu.setBounds( BREITE / 2 - 60, 10, 120, 25 );
        backToMainMenu.setVisible( true );
        handler = new ResultHandler();
        backToMainMenu.addActionListener( handler );
    }

    /**
     * Zeigt die korrekten Bedeutungen an
     * @param links Die Eingabefelder links
     * @param rechts Die Eingabefelder rechts
     * @param xPos Die x Position der Eingabefelder
     * @param arrayLength Die Laenge des Arrays, das die Eingabefelder speichert
     * @param num Die Nummer der Spalte
     */
    public void showSolution( String[] links, String[] rechts, int xPos, int arrayLength, int num ) {
        // Abmessungen der Felder
        int inputFieldWidth = 100;
        int inputFieldHeight = 20;
        // linke Seite
        TextField[] temp = new TextField[arrayLength];
        for ( int i = 0; i < temp.length; i++ ) {
            temp[i] = new TextField();
            add( temp[i] );
            temp[i].setBounds( xPos, 70 + i * inputFieldHeight, inputFieldWidth, inputFieldHeight );
            temp[i].setVisible( true );
            temp[i].setEditable( false );
            // Spalte 1
            if ( num == 1 ) {
                if ( markerG[i] ) {
                    temp[i].setForeground( Color.RED );
                }
                temp[i].setText( gErrors[i] );
            }
            // Spalte 2
            else if ( num == 2 ) {
                if ( markerG[i] ) {
                    temp[i].setForeground( Color.GREEN );
                }
                temp[i].setText( gKorrektur[i] );
            }
        }
        // rechte Seite
        temp = new TextField[arrayLength];
        for ( int j = 0; j < temp.length; j++ ) {
            temp[j] = new TextField();
            add( temp[j] );
            temp[j].setBounds( xPos + 40 + inputFieldWidth, 70 + j * inputFieldHeight, inputFieldWidth, inputFieldHeight );
            temp[j].setVisible( true );
            temp[j].setEditable( false );
            // Spalte 1
            if ( num == 1 ) {
                if ( markerE[j] ) {
                    temp[j].setForeground( Color.RED );
                }
                temp[j].setText( eErrors[j] );
            }
            // Spalte 2
            else if ( num == 2 ) {
                if ( markerE[j] ) {
                    temp[j].setForeground( Color.GREEN );
                }
                temp[j].setText( eKorrektur[j] );
            }
            // Case Sensitive
            if ( anzahlCaseSensitive[j] && num == 1 && ! allMeaningsIncluded[j] ) {
                JLabel caseSensitive = new JLabel( "Gro\u00DF-/Kleinschreibung" );
                add( caseSensitive );
                caseSensitive.setBounds( xPos + inputFieldWidth + 45 + inputFieldWidth, 70 + j * inputFieldHeight, inputFieldWidth + 50, inputFieldHeight );
                caseSensitive.setForeground( Color.ORANGE );
                caseSensitive.setVisible( true );
            }
            // weitere Bedeutungen
            if ( allMeaningsIncluded[j] && num == 1 ) {
                JLabel allIncluded = new JLabel( "weitere Bedeutungen" );
                add( allIncluded );
                allIncluded.setBounds( xPos + inputFieldWidth + 45 + inputFieldWidth, 70 + j * inputFieldHeight, inputFieldWidth + 50, inputFieldHeight );
                allIncluded.setForeground( Color.ORANGE );
                allIncluded.setVisible( true );
            }
        }
    }

    /**
     * Gibt das Label "Anzahl korrekte Woerter" zurueck
     * @return anzahlKorrekt Das Label "Anzahl korrekte Woerter"
     */
    public JLabel getAnzahlKorrekt() {
        return anzahlKorrekt;
    }

    /**
     * Gibt das Label "Anzahl falsche Woerter" zurueck
     * @return anzahlFalsch Das Label "Anzahl falsche Woerter"
     */
    public JLabel getAnzahlFalsch() {
        return anzahlFalsch;
    }

    /**
     * Gibt den Button "Zurueck zum Menue" zurueck
     * @return backToMainMenu Der Button "Zurueck zum Menue"
     */
    public JButton getBackToMainMenu() {
        return backToMainMenu;
    }

    /**
     * Gibt den Handler zurueck
     * @return handler Der Handler
     */
    public ResultHandler getHandler() {
        return handler;
    }
}
