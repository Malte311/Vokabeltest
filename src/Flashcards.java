import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Flashcards extends JFrame {

    private int index;
    private boolean alreadyClicked;
    private boolean swapped;
    private boolean finished;

    private int korrekt;
    private int nichtKorrekt;

    // Gibt den ausgewaehlten Stapel an
    private String list;

    private String[] germanWords;
    private String[] englishWords;
    private int anzahlVokabeln;

    // Komponenten
    private JButton backToMenu;
    private JButton confirm;
    private JTextField vorgabe;
    private JTextField eingabe;
    private JTextField loesung;
    private JLabel info;

    public Flashcards( String list ) {
        // Frame settings
        super( "Vokabeln aus " + list + " lernen" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( Main.getFenster().getBreite(), Main.getFenster().getHoehe() );
        setResizable( false );
        setLocationRelativeTo( Main.getFenster().getFrame() );
        setLayout( null );
        getContentPane().setBackground( new Color( 102, 204, 255 ) );
        setVisible( true );
        Main.getFenster().getFrame().setVisible( false );

        // Daten speichern
        this.list = list;
        try {
            Main.getFenster().getData().readTxt( list );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        germanWords = Main.getFenster().getData().getGermanWords();
        englishWords = Main.getFenster().getData().getEnglishWords();
        // Anzahl der Vokabeln festlegen (germanWords.length == englishWords.length)
        anzahlVokabeln = germanWords.length;

        // Woerter mischen und Werte initialisieren
        shuffleWords();

        index = 0;
        alreadyClicked = false;
        swapped = false;
        finished = false;

        korrekt = 0;
        nichtKorrekt = 0;
    }

    /**
     * Startet das Lernen mit Karteikarten
     */
    public void start() {
        // Zurueck zum Menue Button
        int buttonWidth = 120;
        int buttonHeight = 25;
        backToMenu = new JButton( "Hauptmen\u00FC" );
        add( backToMenu );
        backToMenu.setBackground( new Color( 175, 238, 238 ) );
        backToMenu.setBounds( Main.getFenster().getBreite() / 2 - buttonWidth / 2, 10, buttonWidth, buttonHeight );
        backToMenu.setVisible( true );
        backToMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                Main.getFenster().getFrame().setVisible( true );
                Main.getFenster().getFrame().repaint();
                dispose();
            }
        });

        // TextFields fuer die Vorgabe, die Eingabe und die Loesung
        int labelWidth = Main.getFenster().getBreite()/2;
        int labelHeight = 30;
        vorgabe = new JTextField();
        add( vorgabe );
        vorgabe.setBounds( Main.getFenster().getBreite() / 2 - labelWidth / 2, Main.getFenster().getHoehe()/2 - 3 * labelHeight, labelWidth, labelHeight );
        vorgabe.setHorizontalAlignment( JTextField.CENTER );
        vorgabe.setFont( vorgabe.getFont().deriveFont(18f) );
        vorgabe.setVisible( true );
        vorgabe.setEditable( false );
        vorgabe.setText( germanWords[index] );
        vorgabe.setBackground( new Color( 153, 153, 153 ) );

        eingabe = new JTextField();
        add( eingabe );
        eingabe.setBounds( Main.getFenster().getBreite() / 2 - labelWidth / 2, Main.getFenster().getHoehe()/2 - labelHeight, labelWidth, labelHeight );
        eingabe.setHorizontalAlignment( JTextField.CENTER );
        eingabe.setFont( eingabe.getFont().deriveFont(18f) );
        eingabe.setVisible( true );
        eingabe.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                confirm.doClick();
            }
        });
        eingabe.requestFocus();

        loesung = new JTextField();
        add( loesung );
        loesung.setBounds( Main.getFenster().getBreite() / 2 - labelWidth / 2, Main.getFenster().getHoehe()/2 + labelHeight, labelWidth, labelHeight );
        loesung.setHorizontalAlignment( JTextField.CENTER );
        loesung.setFont( loesung.getFont().deriveFont(18f) );
        loesung.setVisible( false );
        loesung.setEditable( false );
        loesung.setText( englishWords[index] );
        loesung.setBackground( new Color( 153, 153, 153 ) );

        // Eingabe bestaetigen Button
        confirm = new JButton( "OK" );
        add( confirm );
        confirm.setBackground( new Color( 175, 238, 238 ) );
        confirm.setBounds( Main.getFenster().getBreite() / 2 + labelWidth / 2 + 5, Main.getFenster().getHoehe()/2 - labelHeight, buttonWidth, labelHeight );
        confirm.setVisible( true );
        confirm.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( !alreadyClicked ) inputConfirmed();
                else nextWord();
            }
        });

        // Info Label
        info = new JLabel( "Korrekt!" );
        add ( info );
        info.setFont( info.getFont().deriveFont(18f) );
        info.setBounds( Main.getFenster().getBreite() / 2 + labelWidth / 2 + 5, Main.getFenster().getHoehe()/2 + labelHeight, labelWidth, labelHeight );
        info.setVisible( false );
    }

    /**
     * Wird ausgefuhert, wenn die Eingabe bestaetigt wird.
     */
    private void inputConfirmed() {
        // Loesung anzeigen, falls Eingabe vorhanden
        if ( !eingabe.getText().trim().equals( "" ) ) {
            confirm.setText( "Weiter" );
            alreadyClicked = true;
            loesung.setVisible( true );
            String[] partsVorgabe = englishWords[index].split( "," );
            String[] partsEingabe = eingabe.getText().trim().split( "," );
            boolean stop = false;
            for ( int i = 0; i < partsVorgabe.length; i++ ) {
                for ( int j = 0; j < partsEingabe.length; j++ ) {
                    // Loesung korrekt
                    if ( partsVorgabe[i].equals( partsEingabe[j] ) ) {
                        info.setText( "Korrekt!" );
                        info.setForeground( new Color( 0, 153, 0 ) );
                        // Wort rausloeschen, da es korrekt erkannt wurde
                        germanWords[index] = null;
                        englishWords[index] = null;
                        korrekt++;
                        stop = true;
                        break;
                    }
                }
                // Es wurde bereits eine korrekte Loesung gefunden: Fertig.
                if ( stop ) break;
                // Loesung nicht korrekt
                else if ( i == partsVorgabe.length - 1 ) {
                    info.setText( "Leider nicht richtig" );
                    info.setForeground( new Color( 204, 0, 0 ) );
                    nichtKorrekt++;
                    break;
                }
            }
            info.setVisible( true );
            eingabe.setEditable( false );
        }
    }

    /**
     * Geht zum naechsten Wort ueber, wenn der Button gedrueckt wird.
     */
    private void nextWord() {
        if ( index < germanWords.length - 1 ) {
            index++;
        }
        else {
            resetWords();
            index = 0;
        }
        if ( !finished ) {
            alreadyClicked = !alreadyClicked;
            confirm.setText( "OK" );
            vorgabe.setText( germanWords[index] );
            info.setVisible( false );
            loesung.setVisible( false );
            loesung.setText( englishWords[index] );
            eingabe.setText( "" );
            eingabe.setEditable( true );
            eingabe.requestFocus();
        }
    }

    /**
     * Loescht richtige Woerter raus, damit falsche wiederholt werden koennen
     */
    private void resetWords() {
        int count = 0;
        for ( int i = 0; i < germanWords.length; i++ ) {
            if ( germanWords[i] != null ) count++;
        }
        if ( count > 0 ) {
            String[] tmp = new String[count];
            String[] tmp2 = new String[count];
            int pos = 0;
            for ( int i = 0; i < germanWords.length; i++ ) {
                if ( germanWords[i] != null ) {
                    tmp[pos] = germanWords[i];
                    tmp2[pos] = englishWords[i];
                    pos++;
                }
            }
            germanWords = tmp;
            englishWords = tmp2;
        }
        else if ( !swapped ) {
            swapWords();
        }
        else {
            showResult();
        }
    }

    /**
     * Zeigt das Ergebnis an
     */
    private void showResult() {
        finished = true;
        confirm.setVisible( false );
        vorgabe.setText( String.valueOf( korrekt ) + " korrekte W\u00F6rter" );
        vorgabe.setForeground( new Color( 0, 153, 0 ) );
        eingabe.setEditable( false );
        eingabe.setBackground( new Color( 153, 153, 153 ) );
        eingabe.setText( String.valueOf( nichtKorrekt ) + " falsche W\u00F6rter" );
        eingabe.setForeground( new Color( 204, 0, 0 ) );
        double quote = (double) korrekt / ( korrekt + nichtKorrekt );
        quote = Math.round( (quote * 100.0) );
        loesung.setText( "Quote: " + String.valueOf( (int) quote ) + "%" );
        loesung.setVisible( true );
        info.setVisible( false );
    }

    /**
     * Vertauscht deutsche und englische Woerter, damit in beide Richtungen gelernt wird
     */
    private void swapWords() {
        try {
            Main.getFenster().getData().readTxt( list );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        englishWords = Main.getFenster().getData().getGermanWords();
        germanWords = Main.getFenster().getData().getEnglishWords();
        swapped = true;
    }

    /**
     * Mischt die Reihenfolge der Woerter
     */
    private void shuffleWords() {
        String tmp1 = "";
        String tmp2 = "";
        int rand = 0;
        Random r = new Random();
        // anzahlVokabeln == germanWords.length == englishWords.length
        for ( int i = 0; i < anzahlVokabeln; i++ ) {
            rand = r.nextInt( anzahlVokabeln );
            // Deutsche Worte mischen
            tmp1 = germanWords[i];
            germanWords[i] = germanWords[rand];
            germanWords[rand] = tmp1;

            // Englische Worte mischen (aber gleichen Index wie deutsche Worte behalten wegen Zuordnung)
            tmp2 = englishWords[i];
            englishWords[i] = englishWords[rand];
            englishWords[rand] = tmp2;
        }
    }

    /**
     * Gibt den ausgewaehlten Stapel zurueck
     * @return list Der ausgewaehlte Stapel
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
     * Gibt die Anzahl der Vokabeln wieder
     * @return anzahlVokabeln Die Anzahl der Vokabeln
     */
    public int getAnzahlVokabeln() {
        return anzahlVokabeln;
    }

    /**
     * Gibt den Button fuer das Zurueckkehren zum Hauptmenue zurueck
     * @return backToMenu Der Button fuer das Zurueckkehren zum Hauptmenue
     */
    public JButton getBackToMenu() {
        return backToMenu;
    }

    /**
     * Gibt den Button fuer das Bestaetigen der Eingabe zurueck
     * @return confirm Der Button fuer das Bestaetigen der Eingabe
     */
    public JButton getConfirm() {
        return confirm;
    }

    /**
     * Gibt das Textfeld fuer die Vorgabe zurueck
     * @return vorgabe Das Textfeld fuer die Vorgabe
     */
    public JTextField getVorgabe() {
        return vorgabe;
    }

    /**
     * Gibt das Textfeld fuer die Eingabe zurueck
     * @return eingabe Das Textfeld fuer die Eingabe
     */
    public JTextField getEingabe() {
        return eingabe;
    }

    /**
     * Gibt das Textfeld fuer die Loesung zurueck
     * @return loesung Das Textfeld fuer die Loesung
     */
    public JTextField getLoesung() {
        return loesung;
    }

    /**
     * Gibt das Label fuer Informationsanzeigen zurueck
     * @return info Das Label fuer Informationsanzeigen
     */
    public JLabel getInfo() {
        return info;
    }
}
