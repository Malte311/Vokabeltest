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
    // Maximale Anzahl an Vokabeln (mehr passen nicht ins Fenster)
    private final int MAX_ANZAHL_VOKABELN = 50;

    // Abgabebutton
    private JButton abgeben;
    // Listener
    private TestHandler handler;

    // Eingabefelder links
    private JTextField[] inputFieldsL;
    // Eingabefelder rechts
    private JTextField[] inputFieldsR;
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
    // Speichert alle weiteren Komponenten (dient als Container)
    private JPanel panel;

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
        setLayout( new BorderLayout() );
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
        panel = new JPanel();
        panel.setBackground( new Color( 102, 204, 255 ) );
        panel.setLayout( null );
        panel.setLocation( 0, 0 );
        int newHeight = anzahlVokabeln * 30 + 100;
        panel.setPreferredSize( new Dimension( BREITE, newHeight ) );

        initButton( 120, 25 );
        initInputFields();

        // Test beginnen
        startTimer();

        // Neu zeichnen
        repaint();
        validate();
    }

    /**
     * Initialisiert den Button zum Abgeben
     * @param width Breite des Buttons
     * @param height Hoehe des Buttons
     */
    public void initButton( int width, int height ) {
        abgeben = new JButton( "Abgeben" );
        panel.add( abgeben );
        abgeben.setBackground( new Color( 175, 238, 238 ) );
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
        int inputFieldWidth = BREITE/2 - 20;
        int inputFieldHeight = 30;
        // Label fuer Eingabefelder
        addLabel( inputFieldWidth, inputFieldHeight );

        // links (Eingabefelder fuer deutsche Woerter)
        inputFieldsL = new JTextField[arrayLength];
        for (int i = 0; i < inputFieldsL.length; i++) {
            inputFieldsL[i] = new JTextField();
            panel.add( inputFieldsL[i] );
            inputFieldsL[i].setBounds( 10, 70 + i * inputFieldHeight, inputFieldWidth + 10, inputFieldHeight );
            inputFieldsL[i].setHorizontalAlignment( JTextField.CENTER );
            inputFieldsL[i].setFont( inputFieldsL[i].getFont().deriveFont(18f) );
            inputFieldsL[i].setVisible( true );
            // nur jedes zweite Feld als Input
            if ( ( i % 2 ) == 0 ) {
                inputFieldsL[i].setEditable( false );
                inputFieldsL[i].setText( germanWords[i] );
                inputFieldsL[i].setBackground( new Color( 153, 153, 153 ) );
            }
        }

        // rechte Seite
        inputFieldsR = new JTextField[arrayLength];
        for (int j = 0; j < inputFieldsR.length; j++) {
            inputFieldsR[j] = new JTextField();
            panel.add( inputFieldsR[j] );
            inputFieldsR[j].setBounds( BREITE/2, 70 + j * inputFieldHeight, inputFieldWidth, inputFieldHeight );
            inputFieldsR[j].setHorizontalAlignment( JTextField.CENTER );
            inputFieldsR[j].setFont( inputFieldsR[j].getFont().deriveFont(18f) );
            inputFieldsR[j].setVisible( true );
            // nur jedes zweite Feld als Input
            if ( ( j % 2 ) == 1 ) {
                inputFieldsR[j].setEditable( false );
                inputFieldsR[j].setText( englishWords[j] );
                inputFieldsR[j].setBackground( new Color( 153, 153, 153 ) );
            }
        }
        // ScrollPane hinzufuegen
        JScrollPane jsp = new JScrollPane( panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        add( jsp, BorderLayout.CENTER );
        jsp.getVerticalScrollBar().setUnitIncrement(12);
    }

    /**
     * Fuegt die Label "Deutsch" und "Englisch" hinzu
     * @param inputFieldWidth Die Breite des Labels (identisch zur Breite der Felder)
     * @param inputFieldHeight Die Hoehe des Labels (identisch zur Hoehe der Felder)
     */
    public void addLabel( int inputFieldWidth, int inputFieldHeight ) {
        // Deutsch
        JLabel gLabel = new JLabel( "Deutsch" );
        panel.add ( gLabel );
        gLabel.setFont( gLabel.getFont().deriveFont(18f) );
        gLabel.setBounds( 10, 30, inputFieldWidth, inputFieldHeight );
        gLabel.setVisible( true );

        // Englisch
        JLabel eLabel = new JLabel( "Englisch" );
        panel.add ( eLabel );
        eLabel.setFont( eLabel.getFont().deriveFont(18f) );
        eLabel.setBounds( BREITE/2, 30, inputFieldWidth, inputFieldHeight );
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
        clock.setBounds( 10, 5, 80, 25 );
        panel.add( clock );
        clock.setVisible( true );
        try {
            timer = new Timer();
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    time--;
                    paintTime( time );
                    if ( time <= 0 ) handler.startEvaluator();
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
            validate();
        }
        else {
            output = minutes + ":" + seconds;
            clock.setText( output );
            repaint();
            validate();
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
     * Gibt die tatsaechliche Anzahl an Vokabeln zurueck
     * @return anzahlVokabeln Die Anzahl an Vokabeln
     */
    public int getAnzahlVokabeln() {
        return anzahlVokabeln;
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
    public JTextField[] getInputFieldsL() {
        return inputFieldsL;
    }

    /**
     * Gibt die Eingabefelder rechts zurueck
     * @return inputFieldsR Die Eingabefelder rechts
     */
    public JTextField[] getInputFieldsR() {
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
