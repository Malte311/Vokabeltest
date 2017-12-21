import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Testscreen extends JFrame {

    // Abmessungen des Fensters
    private final int BREITE = 800;
    private final int HOEHE = 600;
    // Anzahl Vokabeln
    private int anzahlVokabeln;
    // Anzahl Vokabeln pro Spalte
    private int maxAnzahlVokabelnProSpalte = 25;
    // Maximale Anzahl an Vokabeln
    private int maxAnzahlVokabeln = 2 * maxAnzahlVokabelnProSpalte;
    // Abgabebutton
    private JButton abgeben;
    // Listener
    private TestHandler handler;
    // Eingabefelder links
    private TextField[] inputFieldsL;
    // Eingabefelder rechts
    private TextField[] inputFieldsR;
    // Eingabefelder links Spalte 2
    private TextField[] inputFieldsL2;
    // Eingabefelder rechts Spalte 2
    private TextField[] inputFieldsR2;
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
        if ( anzahlVokabeln > maxAnzahlVokabeln ) {
            anzahlVokabeln = maxAnzahlVokabeln;
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
        // Wenn zu gross fuer eine Spalte, dann anpassen
        if ( arrayLength > maxAnzahlVokabelnProSpalte ) {
            arrayLength = maxAnzahlVokabelnProSpalte;
        }
        // Eine Spalte erzeugen
        createColumns( inputFieldsL, inputFieldsR, 10, arrayLength, 1 );
        // Pruefen, ob eine weitere Spalte noetig ist
        arrayLength = anzahlVokabeln - maxAnzahlVokabelnProSpalte;
        // Wenn immernoch zu gross, dann abschneiden
        if ( arrayLength > maxAnzahlVokabeln ) {
            arrayLength = maxAnzahlVokabeln;
        }
        // Wenn keine Spalte uebrig ist, kann negatives Ergebnis entstehen,
        // wir setzen die Laenge daher wieder auf 0
        if ( arrayLength < 0 ) {
            arrayLength = 0;
        }
        // Zweite Spalte erzeugen
        createColumns( inputFieldsL2, inputFieldsR2, BREITE / 2 + 100, arrayLength, 2 );
    }

    /**
     * Fuehrt die Initialisierung der Eingabefelder durch,
     * damit wir mehrere Spalten initialisieren koennen
     * @param links Die Eingabefelder links
     * @param rechts Die Eingabefelder rechts
     * @param xPos Die x Position der Eingabefelder
     * @param arrayLength Die Laenge des Arrays, das die Eingabefelder speichert
     * @param num Die Nummer der Spalte
     */
    public void createColumns( TextField[] links, TextField[] rechts, int xPos, int arrayLength, int num ) {
        // Abmessungen der Felder
        int inputFieldWidth = 100;
        int inputFieldHeight = 20;
        // Label platzieren. Wenn arrayLength = 0, dann existiert keine zweite Spalte und wir
        // platzieren dort auch keine Label
        if ( arrayLength > 0 ) {
            addLabel( xPos, inputFieldWidth, inputFieldHeight );
        }
        // linke Seite
        links = new TextField[arrayLength];
        for (int i = 0; i < links.length; i++) {
            links[i] = new TextField();
            add( links[i] );
            links[i].setBounds( xPos, 50 + i * inputFieldHeight, inputFieldWidth, inputFieldHeight );
            links[i].setVisible( true );
            // nur jedes zweite Feld als Input
            if ( ( i % 2 ) == 0 ) {
                links[i].setEditable( false );
                // Spalte 1
                if ( num == 1 ) {
                    links[i].setText( germanWords[i] );
                }
                // Spalte 2
                else if ( num == 2 ) {
                    links[i].setText( germanWords[maxAnzahlVokabelnProSpalte + i] );
                }
            }
        }
        // Daten sichern
        if ( num == 1 ) {
            inputFieldsL = links;
        }
        else if ( num == 2 ) {
            inputFieldsL2 = links;
        }

        // rechte Seite
        rechts = new TextField[arrayLength];
        for (int j = 0; j < rechts.length; j++) {
            rechts[j] = new TextField();
            add( rechts[j] );
            rechts[j].setBounds( xPos + 40 + inputFieldWidth, 50 + j * inputFieldHeight, inputFieldWidth, inputFieldHeight );
            rechts[j].setVisible( true );
            // nur jedes zweite Feld als Input
            if ( ( j % 2 ) == 1 ) {
                rechts[j].setEditable( false );
                // Spalte 1
                if ( num == 1 ) {
                    rechts[j].setText( englishWords[j] );
                }
                // Spalte 2
                else if ( num == 2 ) {
                    rechts[j].setText( englishWords[maxAnzahlVokabelnProSpalte + j] );
                }
            }
        }
        // Daten sichern
        if ( num == 1 ) {
            inputFieldsR = rechts;
        }
        else if ( num == 2 ) {
            inputFieldsR2 = rechts;
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
            // System.out.println( minutes + ":" + "0" + seconds );
        }
        else {
            output = minutes + ":" + seconds;
            clock.setText( output );
            repaint();
            // System.out.println( minutes + ":" + seconds );
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
     * @return maxAnzahlVokabeln Die maximale Anzahl an Vokabeln
     */
    public int getMaxAnzahlVokabeln() {
        return maxAnzahlVokabeln;
    }

    /**
     * Setzt die maximale Anzahl an Vokabeln
     * @param maxAnzahl Die maximale Anzahl an Vokabeln
     */
    public void setMaxAnzahlVokabeln( int maxAnzahl ) {
        maxAnzahlVokabeln = maxAnzahl;
    }

    /**
     * Gibt die maximale Anzahl an Vokabeln pro Spalte zurueck
     * @return maxAnzahlVokabelnProSpalte Die maximale Anzahl an Vokabeln pro Spalte
     */
    public int getMaxAnzahlVokabelnProSpalte() {
        return maxAnzahlVokabelnProSpalte;
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
     * Gibt die Eingabefelder links in Spalte 2 zurueck
     * @return inputFieldsL2 Die Eingabefelder links in Spalte 2
     */
    public TextField[] getInputFieldsL2() {
        return inputFieldsL2;
    }

    /**
     * Gibt die Eingabefelder rechts der Spalte 2 zurueck
     * @return inputFieldsR2 Die Eingabefelder rechts in Spalte 2
     */
    public TextField[] getInputFieldsR2() {
        return inputFieldsR2;
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
