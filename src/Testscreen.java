import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Testscreen extends JFrame {

    // Abmessungen des Fensters
    private final int BREITE = 1024;
    private final int HOEHE = 768;

    // Anzahl Vokabeln
    private int anzahlVokabeln;
    // Maximale Anzahl an Vokabeln
    private final int MAX_ANZAHL_VOKABELN = 50;

    // Abgabebutton
    private JButton abgeben;
    // Listener
    private TestHandler handler;

    // Eingabefelder links
    private TextField[] inputFieldsL;
    // Eingabefelder rechts
    private TextField[] inputFieldsR;
    // Englische Vokabeln
    private String[] englishWords;
    // Deutsche Vokabeln
    private String[] germanWords;

    // Timer fuer die Zeit
    private Timer timer;
    // Aktuelle Zeitanzeige
    private int time;
    // Label, das die Zeit anzeigt
    private JLabel clock;

    /**
     * Setzt die Einstellungen des Fensters
     */
    public Testscreen() {
        // Frame initialisieren
        super( "Vokabeltest" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( BREITE, HOEHE );
        setResizable( false );
        setLocationRelativeTo( Main.getFenster().getFrame() );
        setLayout( null );
        setVisible( true );
        Main.getFenster().getFrame().setVisible( false );

        // Listener initialisieren
        handler = new TestHandler();

        anzahlVokabeln = Main.getFenster().getHandler().getTrainer().getAnzahlVokabeln();

        // Anzahl beschraenken (damit alle in das Fenster passen)
        if ( anzahlVokabeln > MAX_ANZAHL_VOKABELN ) {
            anzahlVokabeln = MAX_ANZAHL_VOKABELN;
        }

        // Vokabeln holen
        germanWords = Main.getFenster().getData().getGermanWords();
        englishWords = Main.getFenster().getData().getEnglishWords();

        // Wir waehlen eine zufaellige Reihenfolge der Woerter aus
        shuffleWords();

        // Komponenten initialisieren
        initButton( 100, 20 );
        initInputFields();

        // Test beginnen
        startTimer();

        // Neu zeichnen
        repaint();
    }

    /**
     * Initialisiert den Button zum Abgeben
     * @param width Breite des Buttons
     * @param height Hoehe des Buttons
     */
    public void initButton( int width, int height ) {
        abgeben = new JButton( "Abgeben" );
        add( abgeben );
        abgeben.setBounds( BREITE / 2 - width / 2, 10, width, height );
        abgeben.setVisible( true );
        abgeben.addActionListener( handler );
    }

    /**
     * Initialisiert die Eingabefelder
     */
    public void initInputFields() {
        // Anzahl Vokabeln als Array Laenge
        int arrayLength = anzahlVokabeln;
        // Abmessungen der Felder
        int inputFieldWidth = 100;
        int inputFieldHeight = 20;
        // Label fuer Eingabefelder
        addLabel( 10, inputFieldWidth, inputFieldHeight );

        // links (Eingabefelder fuer deutsche Woerter)
        inputFieldsL = new TextField[arrayLength];
        for (int i = 0; i < inputFieldsL.length; i++) {
            inputFieldsL[i] = new TextField();
            add( inputFieldsL[i] );
            inputFieldsL[i].setBounds( 10, 50 + i * inputFieldHeight, inputFieldWidth, inputFieldHeight );
            inputFieldsL[i].setVisible( true );
            // nur jedes zweite Feld als Input
            if ( ( i % 2 ) == 0 ) {
                inputFieldsL[i].setEditable( false );
                inputFieldsL[i].setText( germanWords[i] );
            }
        }

        // rechte Seite
        inputFieldsR = new TextField[arrayLength];
        for (int j = 0; j < inputFieldsR.length; j++) {
            inputFieldsR[j] = new TextField();
            add( inputFieldsR[j] );
            inputFieldsR[j].setBounds( BREITE/2, 50 + j * inputFieldHeight, inputFieldWidth, inputFieldHeight );
            inputFieldsR[j].setVisible( true );
            // nur jedes zweite Feld als Input
            if ( ( j % 2 ) == 1 ) {
                inputFieldsR[j].setEditable( false );
                inputFieldsR[j].setText( englishWords[j] );
            }
        }
    }

    /**
     * Fuegt die Label "Deutsch" und "Englisch" hinzu
     * @param xPos Die x Position des Labels
     * @param inputFieldWidth Die Breite des Labels (identisch zur Breite der Felder)
     * @param inputFieldHeight Die Hoehe des Labels (identisch zur Hoehe der Felder)
     */
    public void addLabel( int xPos, int inputFieldWidth, int inputFieldHeight ) {
        // Deutsch
        JLabel gLabel = new JLabel( "Deutsch" );
        add ( gLabel );
        gLabel.setBounds( xPos, 50 - inputFieldHeight, inputFieldWidth, inputFieldHeight );
        gLabel.setVisible( true );

        // Englisch
        JLabel eLabel = new JLabel( "Englisch" );
        add ( eLabel );
        eLabel.setBounds( xPos + 40 + inputFieldWidth, 50 - inputFieldHeight, inputFieldWidth, inputFieldHeight );
        eLabel.setVisible( true );
    }

    /**
     * Startet den Timer
     */
    public void startTimer() {
        // 7 Minuten Zeit ( 7*60 Sekunden = 420 Sekunden )
        // Faengt bei 421 an, damit wir bei 0 aufhoeren koennen
        time = 421;
        clock = new JLabel( String.valueOf( time ) );
        clock.setForeground( Color.RED );
        clock.setFont( clock.getFont().deriveFont(18f) );
        add( clock );
        clock.setBounds( 10, 5, 80, 25 );
        clock.setVisible( true );
        try {
            timer = new Timer();
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    time--;
                    paintTime( time );
                    if ( time <= 0 ) abgeben.doClick();
                }
            }, 0, 1000 );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Zeigt die uebrige Zeit an
     */
    public void paintTime( int time ) {
        int minutes = time / 60;
        int seconds = time % 60;
        String output;
        if ( seconds < 10 ) {
            output = minutes + ":" + "0" + seconds;
            clock.setText( output );
            repaint();
        }
        else {
            output = minutes + ":" + seconds;
            clock.setText( output );
            repaint();
        }
    }

    /**
     * Mischt die Reihenfolge der Woerter
     */
    public void shuffleWords() {
        String tmp1 = "";
        String tmp2 = "";
        int rand = 0;
        Random r = new Random();
        // anzahlVokabeln == germanWords.length == englishWords.length
        for ( int i = 0; i < anzahlVokabeln; i++ ) {
            rand = r.nextInt( anzahlVokabeln );
            // Deutsche Worte mischen
            tmp1 = Main.getFenster().getHandler().getTrainer().getGermanWords()[i];
            Main.getFenster().getHandler().getTrainer().getGermanWords()[i] = Main.getFenster().getHandler().getTrainer().getGermanWords()[rand];
            Main.getFenster().getHandler().getTrainer().getGermanWords()[rand] = tmp1;

            // Englische Worte mischen (aber gleichen Index wie deutsche Worte behalten wegen Zuordnung)
            tmp2 = Main.getFenster().getHandler().getTrainer().getEnglishWords()[i];
            Main.getFenster().getHandler().getTrainer().getEnglishWords()[i] = Main.getFenster().getHandler().getTrainer().getEnglishWords()[rand];
            Main.getFenster().getHandler().getTrainer().getEnglishWords()[rand] = tmp2;
        }
    }

    /**
     * Gibt die maximale Anzahl an Vokabeln zurueck
     * @return MAX_ANZAHL_VOKABELN Die maximale Anzahl an Vokabeln
     */
    public int getMaxAnzahlVokabeln() {
        return MAX_ANZAHL_VOKABELN;
    }

    /**
     * Gibt den Abgabebutton zurueck
     * @return abgeben Der Abgabebutton
     */
    public JButton getAbgeben() {
        return abgeben;
    }

    /**
     * Gibt den Handler zurueck
     * @return handler Der Handler
     */
    public TestHandler getHandler() {
        return handler;
    }

    /**
     * Gibt die Eingabefelder links zurueck
     * @return inputFieldsL Die Eingabefelder links
     */
    public TextField[] getInputFieldsL() {
        return inputFieldsL;
    }

    /**
     * Gibt die Eingabefelder rechts zurueck
     * @return inputFieldsR Die Eingabefelder rechts
     */
    public TextField[] getInputFieldsR() {
        return inputFieldsR;
    }

    /**
     * Gibt den Timer zurueck
     * @return timer Der Timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Gibt die aktuelle Zeit zurueck
     * @return time Die aktuelle Zeit
     */
    public int getTime() {
        return time;
    }

    /**
     * Gibt das Label, was die Zeit angezeigt, zurueck
     * @return clock Das Label, was die Zeit angezeigt
     */
    public JLabel getClock() {
        return clock;
    }
}
