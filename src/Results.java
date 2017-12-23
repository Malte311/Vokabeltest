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

    // Panel, dass als Container fungiert
    private JPanel panel;
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
        setLayout( new BorderLayout() );
        setVisible( true );

        // Daten holen
        this.anzahlKorrekteWoerter = anzahlKorrekteWoerter;
        this.anzahlFehler = anzahlFehler;
        this.errors = errors;
        this.corrections = corrections;

        // Komponenten initialisieren
        panel = new JPanel();
        panel.setLayout( null );
        panel.setLocation( 0, 0 );
        // ermittelt die Hoehe des Panels
        int newHeight = 0;
        for ( int i = 0; i < errors.length; i++ ) {
            if ( errors[i] != null  ) newHeight++;
        }
        newHeight = newHeight * 30 + 100;
        panel.setPreferredSize( new Dimension( BREITE, newHeight ) );

        initLabel();
        initButton();

        // Korrekte Loesungen von falschen Vokabeln anzeigen
        showSolution( errors, corrections );

        repaint();
    }

    /**
     * Initialisiert die Label
     */
    public void initLabel() {
        anzahlKorrekt = new JLabel( "Anzahl korrekte W\u00F6rter: " + anzahlKorrekteWoerter );
        panel.add( anzahlKorrekt );
        anzahlKorrekt.setBounds( 10, 10, 180, 25 );
        anzahlKorrekt.setForeground( Color.GREEN );
        anzahlKorrekt.setVisible( true );

        anzahlFalsch = new JLabel( "Anzahl falsche W\u00F6rter: " + anzahlFehler );
        panel.add( anzahlFalsch );
        anzahlFalsch.setBounds( 10, 40, 180, 25 );
        anzahlFalsch.setForeground( Color.RED );
        anzahlFalsch.setVisible( true );

        JLabel prozent = new JLabel( "Note: " + String.valueOf( calculateGrade( getProzent() ) ) + " (" + String.valueOf( (int)getProzent() ) + "%)" );
        panel.add( prozent );
        prozent.setBounds( 200, 25, 180, 25 );
        prozent.setForeground( Color.BLUE );
        prozent.setVisible( true );
    }

    /**
     * Ermittelt die Prozentangabe der richtigen Eingaben
     * @return die Prozentangabe
     */
     private double getProzent() {
         double dec = (double)anzahlKorrekteWoerter / (double)Main.getFenster().getHandler().getTrainer().getTestscreen().getAnzahlVokabeln();
         dec = Math.round( (dec * 100.0) );
         return dec;
     }

     /**
      * Berechnet die Note anhand der Prozentzahl
      * @param percent Die Prozentzahl
      * @return die Note
      */
     private double calculateGrade( double percent ) {
         if ( percent >= 95.0 ) {
             return 1.0;
         }
         else if ( percent >= 90.0 ) {
             return 1.3;
         }
         else if ( percent >= 85.0 ) {
             return 1.7;
         }
         else if ( percent >= 80.0 ) {
             return 2.0;
         }
         else if ( percent >= 75.0 ) {
             return 2.3;
         }
         else if ( percent >= 70.0 ) {
             return 2.7;
         }
         else if ( percent >= 65.0 ) {
             return 3.0;
         }
         else if ( percent >= 60.0 ) {
             return 3.3;
         }
         else if ( percent >= 55.0 ) {
             return 3.7;
         }
         else if ( percent >= 50.0 ) {
             return 4.0;
         }
         else {
             return 5.0;
         }
     }

    /**
     * Initialisiert den Button zum Zurueckkehren
     */
    public void initButton() {
        backToMainMenu = new JButton( "Hauptmen\u00FC" );
        panel.add( backToMainMenu );
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
        int inputFieldWidth = BREITE/2 - 20;
        int inputFieldHeight = 30;

        JTextField temp;
        // Alle Fehler anzeigen
        for ( int i = 0; i < errors.length; i++ ) {
            temp = new JTextField();
            panel.add( temp );
            if ( errors[i] != null && corrections[i] != null ) {
                // Fehler anzeigen
                temp.setBounds( 10, 70 + i * inputFieldHeight, inputFieldWidth + 10, inputFieldHeight );
                temp.setHorizontalAlignment( JTextField.CENTER );
                temp.setFont( temp.getFont().deriveFont(18f) );
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
                temp = new JTextField();
                panel.add( temp );
                temp.setBounds( BREITE/2, 70 + i * inputFieldHeight, inputFieldWidth, inputFieldHeight );
                temp.setHorizontalAlignment( JTextField.CENTER );
                temp.setFont( temp.getFont().deriveFont(18f) );
                temp.setVisible( true );
                temp.setEditable( false );
                temp.setForeground( Color.GREEN );
                temp.setText( corrections[i] );
            }
        }
        JScrollPane jsp = new JScrollPane( panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        add( jsp, BorderLayout.CENTER );
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
