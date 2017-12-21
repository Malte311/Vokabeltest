import java.awt.event.*;

public class TestHandler implements ActionListener {

    // Bewerter
    private Evaluator evaluator;

    public TestHandler() {

    }

    /**
     * Wird ausgefuhert, wenn ein Ereignis aufritt
     * @param e Das aufgetretene Ereignis
     */
    public void actionPerformed ( ActionEvent e ) {
        // Abgeben Button gedrueckt
        if ( e.getSource() == Main.getFenster().getHandler().getTrainer().getTestscreen().getAbgeben() ) {
            // Timer beenden und Bewertung anzeigen
            Main.getFenster().getHandler().getTrainer().getTestscreen().getTimer().cancel();
            Main.getFenster().getHandler().getTrainer().getTestscreen().getTimer().purge();
            evaluator = new Evaluator();
        }
    }

    /**
     * Gibt den Bewerter zurueck
     * @return evaluator Der Bewerter
     */
    public Evaluator getEvaluator() {
        return evaluator;
    }
}
