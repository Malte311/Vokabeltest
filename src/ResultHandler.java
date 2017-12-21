import java.awt.event.*;

public class ResultHandler implements ActionListener {

    public ResultHandler() {

    }

    /**
     * Wird ausgefuhert, wenn ein Ereignis aufritt
     * @param e Das aufgetretene Ereignis
     */
    public void actionPerformed ( ActionEvent e ) {
        if ( e.getSource() == Main.getFenster().getHandler().getTrainer().getTestscreen().getHandler().getEvaluator().getResults().getBackToMainMenu() ) {
            // Zurueck zum Menue
            Main.getFenster().getFrame().setVisible( true );
            Main.getFenster().getFrame().repaint();
            Main.getFenster().getHandler().getTrainer().getTestscreen().getHandler().getEvaluator().getResults().dispose();

            // System.out.println("Zurueck zum Hauptmenue");
        }
    }
}
