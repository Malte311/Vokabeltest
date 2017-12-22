import javax.swing.*;
import java.awt.*;

public class Results extends JFrame {

    // Abmessungen des Fensters
    private final int BREITE = 1024;
    private final int HOEHE = 768;

    // Ergebnisdaten (ohne Getter)
    private int anzahlKorrekteWoerter;
    private int anzahlFehler;
    private String[] errors;
    private String[] corrections;

    // Label
    private JLabel anzahlKorrekt;
    private JLabel anzahlFalsch;
    // Button
    private JButton backToMainMenu;
    // handler
    private ResultHandler handler;

    public Results( int anzahlKorrekteWoerter, int anzahlFehler, String[] errors, String[] corrections ) {
        // Frame initialisieren
        super( "Bewertung" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( BREITE, HOEHE );
        setResizable( false );
        setLocationRelativeTo( Main.getFenster().getHandler().getTrainer().getTestscreen() );
        setLayout( null );
        setVisible( true );

        // Daten holen
        this.anzahlKorrekteWoerter = anzahlKorrekteWoerter;
        this.anzahlFehler = anzahlFehler;
        this.errors = errors;
        this.corrections = corrections;

        // Komponenten initialisieren
        initLabel();
        initButton();

        // Korrekte Loesungen von falschen Vokabeln anzeigen
        showSolution( errors, corrections );
    }

    /**
     * Initialisiert die Label
     */
    public void initLabel() {
        anzahlKorrekt = new JLabel( "Anzahl korrekte W\u00F6rter: " + anzahlKorrekteWoerter );
        add( anzahlKorrekt );
        anzahlKorrekt.setBounds( 10, 10, 180, 25 );
        anzahlKorrekt.setForeground( Color.GREEN );
        anzahlKorrekt.setVisible( true );
        anzahlFalsch = new JLabel( "Anzahl falsche W\u00F6rter: " + anzahlFehler );
        add( anzahlFalsch );
        anzahlFalsch.setBounds( 10, 40, 180, 25 );
        anzahlFalsch.setForeground( Color.RED );
        anzahlFalsch.setVisible( true );
    }

    /**
     * Initialisiert den Button zum Zurueckkehren
     */
    public void initButton() {
        backToMainMenu = new JButton( "Hauptmen\u00FC" );
        add( backToMainMenu );
        backToMainMenu.setBounds( BREITE / 2 - 60, 10, 120, 25 );
        backToMainMenu.setBackground( new Color( 175, 238, 238 ) );
        backToMainMenu.setVisible( true );
        handler = new ResultHandler();
        backToMainMenu.addActionListener( handler );
    }

    /**
     * Zeigt die korrekten Bedeutungen an
     * @param gErrors Fehler bei deutschen Woertern
     * @param eErrors Fehler bei englischen Woertern
     */
    public void showSolution( String[] errors, String[] corrections ) {
        // Abmessungen der Felder
        int inputFieldWidth = BREITE/2 - 50;
        int inputFieldHeight = 40;

        TextField temp;
        // Alle Fehler anzeigen
        for ( int i = 0; i < errors.length; i++ ) {
            temp = new TextField();
            add( temp );
            if ( errors[i] != null && corrections[i] != null ) {
                // Fehler anzeigen
                temp.setBounds( 10, 70 + i * inputFieldHeight, inputFieldWidth, inputFieldHeight );
                temp.setVisible( true );
                temp.setEditable( false );
                temp.setForeground( Color.RED );
                if ( errors[i].equals( "" ) ) {
                    temp.setText( "-" );
                }
                else {
                    temp.setText( errors[i] );
                }
                // Korrektur anzeigen
                temp = new TextField();
                add( temp );
                temp.setBounds( BREITE/2, 70 + i * inputFieldHeight, inputFieldWidth, inputFieldHeight );
                temp.setVisible( true );
                temp.setEditable( false );
                temp.setForeground( Color.GREEN );
                temp.setText( corrections[i] );
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
