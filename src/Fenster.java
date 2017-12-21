import javax.swing.*;
import java.awt.*;

public class Fenster {

    // Frame sowie dessen Abmessungen
    private JFrame frame;
    private final int BREITE = 1024;
    private final int HOEHE = 768;

    // Button zum Hinzufuegen eines Eintrages
    private JButton addEntry;
    // Button zum Starten eines Tests
    private JButton startTest;
    // Button zum Anlegen eines neuen Stapels
    private JButton newList;
    // Button zum Loeschen von Eintraegen
    private JButton deleteEntry;
    // Button zum Anzeigen der Eintraege eines Stapels
    private JButton showEntries;
    // Button zum Loeschen von Stapeln
    private JButton deleteList;
    // Abmessungen der Buttons
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 30;
    // Erster y-Wert der obersten Komponenten
    private final int Y_START = 50;
    // Der Listener fuer die Buttons
    private StartscreenHandler handler;

    // Auswahlfeld zur Wahl des Stapels
    private JComboBox<String> chooseList;

    // Unser Data Objekt
    private Data data;

    // Gibt an, ob Initialisierung abgeschlossen ist
    private boolean ready;

    /**
     * Initialisiert den Frame und ruft die Methode init() auf,
     * welche dann weitere Komponenten initialisiert
     */
    public Fenster() {
        // Initialisierung nicht abgeschlossen
        ready = false;

        // Frame settings
        frame = new JFrame( "Vokabeltrainer" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( BREITE, HOEHE );
        frame.setResizable( false );
        frame.setLocationRelativeTo( null );
        frame.setLayout( null );
        frame.setBackground( new Color( 100, 149, 237 ) );
        frame.setVisible( true );

        // Neuen Listener erzeugen
        handler = new StartscreenHandler();

        // Daten laden
        this.loadData();

        // Initialisierung abgeschlossen
        ready = true;
    }

    /**
     * Initialisiert die weiteren Komponenten des Frames
     * @param frame Der Frame, zu dem die Komponenten gehoeren
     */
    private void init( JFrame frame ) {
        // ComboBox zur Wahl des Stapels settings
        chooseList = new JComboBox<String>( data.getLists() );
        chooseList.setBounds( BREITE/2 - BUTTON_WIDTH/2, Y_START, BUTTON_WIDTH, BUTTON_HEIGHT );
        frame.add( chooseList );
        ( (JLabel)chooseList.getRenderer() ).setHorizontalAlignment( SwingConstants.CENTER );
        chooseList.setBackground( new Color( 175, 238, 238 ) );
        chooseList.setVisible( true );

        // showEntries Button settings
        showEntries = new JButton( "Eintr\u00E4ge anzeigen" );
        showEntries.setBounds( BREITE/2 - BUTTON_WIDTH/2, Y_START + BUTTON_HEIGHT + 10, BUTTON_WIDTH, BUTTON_HEIGHT );
        frame.add( showEntries );
        showEntries.setBackground( new Color( 175, 238, 238 ) );
        showEntries.setVisible( true );
        showEntries.addActionListener( handler );
        // addEntry Button settings
        addEntry = new JButton( "Eintrag hinzuf\u00FCgen" );
        addEntry.setBounds( BREITE/2 - BUTTON_WIDTH/2, Y_START + 2 * BUTTON_HEIGHT + 20, BUTTON_WIDTH, BUTTON_HEIGHT );
        frame.add( addEntry );
        addEntry.setBackground( new Color( 175, 238, 238 ) );
        addEntry.setVisible( true );
        addEntry.addActionListener( handler );
        // deleteEntry Button settings
        deleteEntry = new JButton( "Eintrag l\u00F6schen" );
        deleteEntry.setBounds( BREITE/2 - BUTTON_WIDTH/2, Y_START + 3 * BUTTON_HEIGHT + 30, BUTTON_WIDTH, BUTTON_HEIGHT );
        frame.add( deleteEntry );
        deleteEntry.setBackground( new Color( 175, 238, 238 ) );
        deleteEntry.setVisible( true );
        deleteEntry.addActionListener( handler );
        // newList Button settings
        newList = new JButton( "Stapel hinzuf\u00FCgen" );
        newList.setBounds( BREITE/2 - BUTTON_WIDTH/2, Y_START + 4 * BUTTON_HEIGHT + 40, BUTTON_WIDTH, BUTTON_HEIGHT );
        frame.add( newList );
        newList.setBackground( new Color( 175, 238, 238 ) );
        newList.setVisible( true );
        newList.addActionListener( handler );
        // deleteList Button settings
        deleteList = new JButton( "Stapel l\u00F6schen" );
        deleteList.setBounds( BREITE/2 - BUTTON_WIDTH/2, Y_START + 5 * BUTTON_HEIGHT + 50, BUTTON_WIDTH, BUTTON_HEIGHT );
        frame.add( deleteList );
        deleteList.setBackground( new Color( 175, 238, 238 ) );
        deleteList.setVisible( true );
        deleteList.addActionListener( handler );
        // startTest Button settings
        startTest = new JButton( "Test starten" );
        startTest.setBounds( BREITE/2 - BUTTON_WIDTH/2, Y_START + 6 * BUTTON_HEIGHT + 60, BUTTON_WIDTH, BUTTON_HEIGHT );
        frame.add( startTest );
        startTest.setBackground( new Color( 30, 144, 255 ) );
        startTest.setVisible( true );
        startTest.addActionListener( handler );
    }

    /**
     * Laedt die noetigen Daten
     */
    private void loadData() {
        // Neues Objekt erzeugen und verfuegbare Stapel aktualisieren
        data = new Data();
        data.updateLists();

        // Nun initialisieren wir restlichen Komponenten
        this.init( frame );

        // Neuzeichnen, nachdem Daten geladen wurden
        frame.repaint();
    }

    /**
     * Gibt den Frame zurueck
     * @return frame Der Frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Gibt die Breite zurueck
     * @return BREITE Die Breite des Fensters
     */
    public int getBreite() {
        return BREITE;
    }

    /**
     * Gibt die Hoehe zurueck
     * @return HOEHE Die Hoehe des Fensters
     */
    public int getHoehe() {
        return HOEHE;
    }

    /**
     * Gibt den Button zum Hinzufuegen von Eintraegen zurueck
     * @return addEntry Der Button zum Hinzufuegen von Eintraegen zurueck
     */
    public JButton getAddEntryButton() {
        return addEntry;
    }

    /**
     * Gibt den Button zum Starten von einem Test zurueck
     * @return addEntry Der Button zum Starten von einem Test
     */
    public JButton getStartTestButton() {
        return startTest;
    }

    /**
     * Gibt den Button zum Hinzufuegen eines neuen Stapels zurueck
     * @return newList Der Button zum Hinzufuegen eines neuen Stapels
     */
    public JButton getNewListButton() {
        return newList;
    }

    /**
     * Gibt die Auswahlliste zur Auswahl des Stapels zurueck
     * @return chooseList Die Auswahlliste zur Auswahl des Stapels
     */
    public JComboBox<String> getChooseList() {
        return chooseList;
    }

    /**
     * Gibt das Data Objekt zurueck
     * @return data Das Data Objekt
     */
    public Data getData() {
        return data;
    }

    /**
     * Gibt die Variable, die angibt, ob die Initialisierung abgeschlossen ist, zurueck
     * @return ready Die Variable, die angibt, ob die Initialisierung abgeschlossen ist
     */
    public boolean getReady() {
        return ready;
    }

    /**
     * Gibt den Listener zurueck
     * @return handler Der Listener
     */
    public StartscreenHandler getHandler() {
        return handler;
    }

    /**
     * Gibt den Button zum Loeschen von Eintraegen zurueck
     * @return deleteEntry Der Button zum Loeschen von Eintraegen
     */
    public JButton getDeleteEntryButton() {
        return deleteEntry;
    }

    /**
     * Gibt den Button, der die Eintraege eines Stapels anzeigt, zurueck
     * @return showEntries Der Button, der die Eintraege eines Stapels anzeigt
     */
    public JButton getShowEntriesButton() {
        return showEntries;
    }

    /**
     * Gibt den Button zum Loeschen von Stapeln zurueck
     * @return deleteList Der Button zum Loeschen von Stapeln
     */
    public JButton getDeleteList() {
        return deleteList;
    }

    /**
     * Gibt die Breite der Buttons zurueck
     * @return BUTTON_WIDTH Die Breite der Buttons
     */
    public int getButtonWidth() {
        return BUTTON_WIDTH;
    }

    /**
     * Gibt die Hoehe der Buttons zurueck
     * @return BUTTON_HEIGHT Die Hoehe der Buttons
     */
    public int getButtonHeight() {
        return BUTTON_HEIGHT;
    }

    /**
     * Gibt den ersten y-Wert zurueck
     * @return Y_START Der erste y-Wert
     */
    public int getYStart() {
        return Y_START;
    }
}
