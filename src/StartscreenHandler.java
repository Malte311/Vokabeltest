import java.awt.event.*;
import javax.swing.*;

public class StartscreenHandler implements ActionListener {

    private Vokabeltrainer trainer;
    private Flashcards flashcards;
    // Standardgemaess Trennzeichen fuer Windows
    private String trennzeichen = "\\";

    public StartscreenHandler() {
        // Trennzeichen korrekt setzen
        String os = System.getProperty( "os.name" ).toLowerCase();
        // Betriebssystem ist Windows-basiert?
        if ( os.contains( "win" ) ) {
            trennzeichen = "\\";
        }
        // Betriebssystem ist OSX?
        else if ( os.contains( "osx" ) ) {
            trennzeichen = "/";
        }
        // Sonst auch einfaches Slash
        else {
            trennzeichen = "/";
        }
    }

    /**
     * Wird ausgefuhert, wenn ein Ereignis aufritt
     * @param e Das aufgetretene Ereignis
     */
    public void actionPerformed ( ActionEvent e ) {
        // Der Button zum Hinzufuegen von Eintraegen wurde gedrueckt
        if ( e.getSource() == Main.getFenster().getAddEntryButton() && Main.getFenster().getReady() ) {
            // Komponenten des Dialogfensters
            JTextField de = new HintTextField( "Deutsch" );
            JTextField en = new HintTextField( "Englisch" );
            JPanel panel = new JPanel();
            panel.setLayout( new BoxLayout(panel, BoxLayout.PAGE_AXIS) );
            panel.add( de );
            panel.add( en );
            // Der Dialog nimmt die Eingabedaten entgegen
            int result = JOptionPane.showConfirmDialog(Main.getFenster().getFrame(), panel, "Neuer Eintrag", JOptionPane.OK_CANCEL_OPTION);
            if ( result == JOptionPane.OK_OPTION ) {
                Main.getFenster().getData().addVocabulary( de.getText(), en.getText() );
            }
        }

        // Der Button zum Starten eines Tests wurde gedrueckt
        if ( e.getSource() == Main.getFenster().getStartTestButton() && Main.getFenster().getReady() ) {
            // Neuen Test mit aktuell ausgewaehltem Stapel starten
            String list = (String) Main.getFenster().getChooseList().getSelectedItem();
            trainer = new Vokabeltrainer( list );
            // Den Test und den Timer starten
            trainer.startTest();
        }

        // Der Button zum Erstellen eines neuen Stapels wurde gedrueckt
        if ( e.getSource() == Main.getFenster().getNewListButton() && Main.getFenster().getReady() ) {
            // Es oeffnet sich ein neuer Dialog, in dem wir nun den Namen des neuen Stapels angeben
            String s = (String) JOptionPane.showInputDialog(
                    Main.getFenster().getFrame(),
                    "Wie soll der neue Stapel hei\u00DFen?",
                    "Neuen Stapel erstellen",
                    JOptionPane.PLAIN_MESSAGE);
            if ( (s != null) && (s.length() > 0) ) {
                Main.getFenster().getData().createList( s );
            }
        }

        // Der Button zum Anzeigen eines Stapels wurde gedrueckt
        if ( e.getSource() == Main.getFenster().getShowEntriesButton() && Main.getFenster().getReady() ) {
            Main.getFenster().getData().listEntries();
        }

        // Der Button zum Loeschen eines Eintrags wurde gedrueckt
        if ( e.getSource() == Main.getFenster().getDeleteEntryButton() && Main.getFenster().getReady() ) {
            // Komponenten des Dialogfensters
            JTextField de = new HintTextField( "Deutsch" );
            JTextField en = new HintTextField( "Englisch" );
            JPanel panel = new JPanel();
            panel.setLayout( new BoxLayout(panel, BoxLayout.PAGE_AXIS) );
            panel.add( de );
            panel.add( en );
            // Der Dialog nimmt die Eingabedaten entgegen
            int result = JOptionPane.showConfirmDialog(Main.getFenster().getFrame(), panel, "Eintrag l\u00F6schen", JOptionPane.OK_CANCEL_OPTION);
            if ( result == JOptionPane.OK_OPTION ) {
                Main.getFenster().getData().deleteEntry( de.getText(), en.getText() );
            }
        }

        // Der Button zum Loeschen eines Stapels wurde gedrueckt
        if ( e.getSource() == Main.getFenster().getDeleteList() && Main.getFenster().getReady() ) {
            // Es oeffnet sich ein neuer Dialog, in dem wir nun den Namen des Stapels, der geloescht werden soll, angeben
            String s = (String) JOptionPane.showInputDialog(
                    Main.getFenster().getFrame(),
                    "Welcher Stapel soll gel\u00F6scht werden?",
                    "Stapel l\u00F6schen",
                    JOptionPane.PLAIN_MESSAGE);
            if ( (s != null) && (s.length() > 0) ) {
                Main.getFenster().getData().deleteList( s );
            }
        }
        // Der Button zum Lernen der Karteikarten wurde gedrueckt
        if ( e.getSource() == Main.getFenster().getStartFlashcards() && Main.getFenster().getReady() ) {
            String list = (String) Main.getFenster().getChooseList().getSelectedItem();
            flashcards = new Flashcards( list );
            flashcards.start();
        }
    }

    /**
     * Gibt das Vokabeltrainer Objekt zurueck
     * @return trainer Das Vokabeltrainer Objekt
     */
    public Vokabeltrainer getTrainer() {
        return trainer;
    }

    /**
     * Gibt das Flashcards Objekt zurueck
     * @return flashcards Das Flashcards Objekt
     */
    public Flashcards getFlashcards() {
        return flashcards;
    }
}
