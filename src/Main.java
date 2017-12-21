public class Main {

    // Speichert unser Fenster-Objekt
    private static Fenster f;

    /**
     * Erzeugt ein neues Fenster-Objekt
     */
    public static void main( String[] args ) {
        f = new Fenster();
    }

    /**
     * Gibt das Fenster-Objekt zurueck
     * @return f Das Fenster Objekt
     */
    public static Fenster getFenster() {
        return f;
    }
}
