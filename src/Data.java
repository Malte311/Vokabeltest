import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Data {

    private String trennzeichen = "\\";
    // Speichert den Pfad zum Verzeichnis, in dem diese Datei liegt
    private String localPath;
    // Speichert den Pfad zur Textdatei, in der geschrieben werden soll
    private String path;
    // Repraesentiert unser File Objekt
    private File file;
    // Speichert die verfuegbaren Stapel
    private String[] lists;
    // Speichert die deutschen Woerter
    private String[] germanWords;
    // Speichert die englischen Woerter
    private String[] englishWords;

    public Data() {
        // Trennzeichen abhaengig vom Betriebssystem waehlen
        String os = System.getProperty( "os.name" ).toLowerCase();
        //Betriebssystem ist Windows-basiert
        if ( os.contains( "win" ) ) {
            trennzeichen = "\\";
        }
        //Betriebssystem ist Apple OSX
        else if ( os.contains( "osx" ) ) {
            trennzeichen = "/";
        }
        // Sonst auch einfaches Slash
        else {
            trennzeichen = "/";
        }
        // Das aktuelle Verzeichnis wird gesetzt
        localPath = System.getProperty( "user.dir" );
        path = trennzeichen + "vocab";
    }

    /**
     * Aktualisiert die verfuegbaren Stapel
     */
    public void updateLists() {
        // default Datei erstellen, falls sie fehlt
        if ( !new File( localPath + path + trennzeichen + "default.txt" ).exists() ) {
            try {
                createNewTxT( "default" );
            }
            catch( Exception e ) {
                System.out.println( e );
                e.printStackTrace();
            }
        }

        // Liste der Dateien in dem Array lists speichern
        try {
            // File Objekt zum Ordner der Stapel
            File f = new File( localPath + path );
            lists = f.list();
        }
        catch( Exception e ) {
            System.out.println( e );
            e.printStackTrace();
        }

        /* Dateiendungen entfernen (wir gehen davon aus, dass nur .txt Dateien vorhanden sind)
         * 46 steht fuer den Punkt (.) in ASCII
         */
        for ( int i = 0; i < lists.length; i++ ) {
            lists[i] = lists[i].substring( 0, lists[i].lastIndexOf(46) );
        }
    }

    /**
     * Erstellt unser File Objekt mit angegebenem Pfad
     */
    public void updateFile() {
        try {
             file = new File( localPath + path );
        }
        catch( Exception e ) {
            System.out.println( e );
            e.printStackTrace();
        }
    }

    /**
     * Erstellt eine neue Datei, falls sie nicht bereits existiert
     * @param f Das File Objekt fuer die zu erstellende Datei
     * @throws IOException Wenn ein Fehler mit der Datei passiert
     */
    public void createFile( File f ) throws IOException {
        if ( !f.exists() ) {
            f.createNewFile();
        }
    }

    /**
     * Erstellt eine neue Textdatei mit angegebenem Namen
     * @param name Der Name der Textdatei
     */
    public void createNewTxT( String name ) throws IOException {
        String nameOhneSonderzeichen = name.replaceAll( "[^a-zA-Z0-9]", "" );
        File f = new File( localPath + path + trennzeichen + nameOhneSonderzeichen + ".txt" );

        createFile( f );
    }

    /**
     * Liest eine Textdatei ein und speichert dessen Inhalt in einem Array
     * @param f Die zu lesende Datei
     */
    public void readTxt( String dateiname ) throws IOException {
        File f = new File( localPath + path + trennzeichen + dateiname + ".txt" );

        // Wir zaehlen zunaechst die Zeilen der Datei, um zu wissen, wie viele Vokabeln existieren
        int lineCount = 0;
        try {
            lineCount = countLines( f );
        }
        catch ( Exception e ) {
            System.out.println( e );
            e.printStackTrace();
        }

        // Dann initialisieren wir die Arrays passend
        germanWords = new String[lineCount];
        englishWords = new String[lineCount];

        BufferedReader reader = null;
        // Sicherstellen, dass die Datei auch existiert, bevor wir darin lesen
        if ( !f.exists() || !f.isFile() || !f.canRead() ) {
            throw new IOException();
        }
        else {
            // Inhalt auslesen und in Arrays speichern
            try {
                reader = new BufferedReader( new FileReader( f.getAbsolutePath() ) );
                String line = null;
                int counter = 0;
                while ( ( line = reader.readLine() ) != null ) {
                    /* Gelesene Zeile in deutschen und englischen Teil aufteilen und speichern
                     * Dabei gehen wir davon aus, dass die Teile durch das Gleichheitszeichen (=) getrennt sind
                     * und pro Zeile genau ein deutscher sowie ein englischer Teil existieren
                     */
                     if ( line.trim().contains( "=" ) ) {
                         String[] s = line.trim().split( "=" );

                         germanWords[counter] = s[0];
                         englishWords[counter] = s[1];

                         counter++;
                     }
                }
            }
            catch ( Exception e ) {
                System.out.println( e );
                e.printStackTrace();
            }
            finally {
                if (reader != null) {
                     try {
                        reader.close();
                    }
                    catch (Exception e) {
                        System.out.println( e );
                    }
                }
            }
        }
    }

    /**
     * Zaehlt die Zeilen einer Textdatei, damit wir die Laenge der Arrays wissen
     * @param f Die Datei, in der wir die Zeilen zaehlen
     * @return lines Die Anzahl der Zeilen
     */
    public int countLines( File f ) throws IOException {
        int lines = 0;
        try {
            BufferedReader reader = new BufferedReader( new FileReader( f.getAbsolutePath() ) );
            String line = null;
            while ( ( line = reader.readLine() ) != null ) {
                lines++;
            }
            reader.close();
        }
        catch ( Exception e ) {
            System.out.println( e );
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Fuegt neue Vokabeln hinzu und schreibt die Eintraege in eine Textdatei
     * @param german Das deutsche Wort
     * @param english Das englische Wort
     */
    public void addVocabulary( String german, String english ) {
        // Unnoetige Leerzeichen entfernen
        german = german.trim();
        english = english.trim();
        german = german.replaceAll( "[=]", "" );
        english = english.replaceAll( "[=]", "" );
        // Eintrag zusammenbasteln
        String entry = german + "=" + english;
        // Sofern etwas eingegeben wurde, tragen wir es ein
        if ( ( !german.equals( "" ) ) && ( !english.equals( "" ) ) ) {
            // Pruefen, ob Eintrag bereits existiert
            boolean bereitsVorhanden = false;
            try {
                File f = new File( localPath + path + trennzeichen + ( (String) Main.getFenster().getChooseList().getSelectedItem() ) + ".txt" );
                BufferedReader reader = new BufferedReader( new FileReader( f ) );
                String line = null;
                while ( ( line = reader.readLine() ) != null ) {
                    String germanWord = line.split( "=" )[0].trim();
                    if ( germanWord.equals( german ) ) {
                        bereitsVorhanden = true;
                    }
                }
                reader.close();
            }
            catch ( Exception e ) {
                e.printStackTrace();
                // Benachrichtigung, falls Fehler beim Eintrag
                JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Fehlgeschlagen", "Fehler", JOptionPane.ERROR_MESSAGE );
                Main.getFenster().getFrame().repaint();
            }
            // Wenn der Eintrag noch nicht vorhanden ist, kann er hinzugefuegt werden
            if ( !bereitsVorhanden ) {
                try {
                    // In Textdatei eintragen
                    File f = new File( localPath + path + trennzeichen + ( (String) Main.getFenster().getChooseList().getSelectedItem() ) + ".txt" );
                    BufferedWriter writer = new BufferedWriter( new FileWriter( f, true ) );
                    if ( f.length() > 0 ) writer.newLine();
                    writer.write( entry );
                    writer.close();
                    // Benachrichtigung bei erfolgreichem Eintrag
                    JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Erfolgreich hinzugef\u00FCgt", "Eintrag hinzugef\u00FCgt", JOptionPane.INFORMATION_MESSAGE );
                    Main.getFenster().getFrame().repaint();
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                    // Benachrichtigung, falls Fehler beim Eintrag
                    JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Fehlgeschlagen", "Fehler", JOptionPane.ERROR_MESSAGE );
                    Main.getFenster().getFrame().repaint();
                }
            }
            // Wenn bereits vorhanden, dann Benachrichtigung
            else {
                JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Eintrag bereits vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE );
                Main.getFenster().getFrame().repaint();
            }
        }
        else {
            JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Ung\u00FCltige Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Ermoeglicht dem Benutzer, Eintraege eines Stapels zu loeschen
     */
     public void deleteEntry( String german, String english ) {
         // Unnoetige Leerzeichen entfernen
         german = german.trim();
         english = english.trim();
         // Eintrag zusammenbasteln
         String entry = german + "=" + english;
         // Eintrag suchen und wenn vorhanden, dann loeschen
         try {
             File f = new File( localPath + path + trennzeichen + ( (String) Main.getFenster().getChooseList().getSelectedItem() ) + ".txt" );
             BufferedReader reader = new BufferedReader( new FileReader( f ) );
             String line = null;
             int count = 0;
             boolean found = false;
             // Wir speichern alles, was nicht geloescht werden soll
             String[] vocabs = new String[countLines( f )];
             while ( ( line = reader.readLine() ) != null ) {
                 // Wenn Eintrag nicht geloscht werden soll, wird er gespeichert
                 if ( !( line.trim().equals( entry ) ) ) {
                     vocabs[count] = line;
                     count++;
                 }
                 else {
                     found = true;
                 }
             }
             reader.close();
             // Nun ueberschreiben wir die Datei mit allen Eintraegen, die noch vorhanden sind
             BufferedWriter writer = new BufferedWriter( new FileWriter( f, false ) );
             for ( int i = 0; i < vocabs.length; i++ ) {
                 if ( i != 0 && vocabs[i] != null ) writer.newLine();
                 if ( vocabs[i] != null ) writer.write( vocabs[i] );
             }
             writer.close();
             if ( found ) {
                 // Benachrichtigung bei erfolgreichem Loeschen
                 JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Erfolgreich gel\u00F6scht", "Eintrag gel\u00F6scht", JOptionPane.INFORMATION_MESSAGE );
                 Main.getFenster().getFrame().repaint();
             }
             else {
                 // Benachrichtigung bei erfolglosem Loeschen
                 JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Eintrag nicht vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE );
                 Main.getFenster().getFrame().repaint();
             }
         }
         catch ( Exception e ) {
             e.printStackTrace();
             // Benachrichtigung, falls Fehler beim Eintrag
             JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Fehlgeschlagen", "Fehler", JOptionPane.ERROR_MESSAGE );
             Main.getFenster().getFrame().repaint();
         }
     }

     /**
      * Zeigt dem Benutzer alle Eintraege des aktuell ausgewaehlten Stapels an
      */
     public void listEntries() {
         String[] vocabs = null;
         try {
             File f = new File( localPath + path + trennzeichen + ( (String) Main.getFenster().getChooseList().getSelectedItem() ) + ".txt" );
             BufferedReader reader = new BufferedReader( new FileReader( f ) );
             // Wir speichern alles
             String line = null;
             int count = 0;
             int anzahlZeilen = countLines( f );
             if ( anzahlZeilen > 0 ) vocabs = new String[anzahlZeilen];
             else vocabs = new String[1];
             while ( ( line = reader.readLine() ) != null ) {
                 vocabs[count] = line.trim();
                 count++;
             }
             reader.close();
         }
         catch ( Exception e ) {
             e.printStackTrace();
         }
         // Anzeige (JDialog settings)
         JDialog jd = new JDialog();
         jd.setTitle( "Vokabeln aus " + ( (String) Main.getFenster().getChooseList().getSelectedItem() ) );
         jd.setSize( Main.getFenster().getBreite(), Main.getFenster().getHoehe() );
         jd.setResizable( false );
         jd.setModal( true );
         jd.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
         jd.setLocationRelativeTo( Main.getFenster().getFrame() );
         jd.setLayout( new BorderLayout() );

         // Eintraege anzeigen
         JTextArea jTextArea = new JTextArea();
         int labelWidth = 100;
         int labelHeight = 25;
         // Wir fuegen alle Eintraege zur TextArea hinzu
         if ( vocabs != null ) {
             String[] separate = null;
             for ( int i = 0; i < vocabs.length; i++ ) {
                 if ( vocabs[i] != null ) separate = vocabs[i].split( "=" );
                 if ( separate != null ) {
                     for ( int j = 0; j < separate.length; j++ ) {
                         jTextArea.append( separate[j] + "\t\t\t\t\t" );
                     }
                     jTextArea.append( "\n" );
                 }
             }
         }
         // TextArea settings
         jTextArea.setEditable( false );
         jd.add( jTextArea );
         jTextArea.setVisible(true);

         // ScrollPane hinzufuegen
         JScrollPane jsp = new JScrollPane( jTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
         jd.add( jsp, BorderLayout.CENTER );
         jTextArea.setCaretPosition( 0 );

         // Dialog anzeigen
         jd.setVisible( true );
     }

     /**
      * Erstellt neue Stapel
      * @param s Der Name des neuen Stapels
      */
      public void createList( String s ) {
          s = s.trim();
          if ( s != null ) s = s.replaceAll( "[^a-zA-Z0-9]", "" );
          if ( ( s != null ) && ( s.length() <= 0 ) ) {
              JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Ung\u00FCltige Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE );
          }
          else if ( s != null ) {
              try {
                  createNewTxT( s );
                  updateLists();
                  Main.getFenster().getChooseList().addItem( s );
                  Main.getFenster().getFrame().repaint();
              }
              catch ( Exception e ) {
                  e.printStackTrace();
              }
          }
          else {
              JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Ung\u00FCltige Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE );
          }
      }

      /**
       * Loescht einen Stapel
       * @param s Der Name des Stapels, der geloescht werden soll
       */
       public void deleteList( String s ) {
           s = s.trim();
          if ( s != null ) s = s.replaceAll( "[^a-zA-Z0-9]", "" );
          if ( ( ( s != null ) && ( s.length() <= 0 ) ) || ( ( s != null ) && s.equals( "default" ) ) ) {
              JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Ung\u00FCltige Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE );
          }
          else if ( s != null ) {
              try {
                  File f = new File( localPath + path + trennzeichen + s + ".txt" );
                  if ( f.exists() ) {
                      f.delete();
                      updateLists();
                      Main.getFenster().getChooseList().removeItem( s );
                      JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Erfolgreich gel\u00F6scht", "Stapel gel\u00F6scht ", JOptionPane.INFORMATION_MESSAGE );
                  }
                  else {
                     JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Ung\u00FCltige Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE );
                  }
                  Main.getFenster().getFrame().repaint();
              }
              catch ( Exception e ) {
                  e.printStackTrace();
              }
          }
          else {
              JOptionPane.showMessageDialog( Main.getFenster().getFrame(), "Ung\u00FCltige Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE );
          }
      }

    /**
     * Gibt den Pfad zur Textdatei wieder
     * @return path Der Pfad zur Textdatei
     */
    public String getPath() {
        return path;
    }

    /**
     * Setzt den Pfad zur Textdatei
     * @param path Der neue Pfad
     */
    public void setPath( String path ) {
        this.path = path;
    }

    /**
     * Gibt den Pfad zu dieser Datei wieder
     * @return localPath Der Pfad zu dieser Datei
     */
    public String getLocalPath() {
        return localPath;
    }

    /**
     * Gibt das File Objekt zurueck
     * @return file Das File Objekt fuer unsere Operationen
     */
    public File getFile() {
        return file;
    }

    /**
     * Gibt die verfuegbaren Stapel zurueck
     * @return lists Die verfuegbaren Stapel
     */
    public String[] getLists() {
        return lists;
    }

    /**
     * Gibt das Array mit den deutschen Woertern zurueck
     * @return germanWords Das Array mit den deutschen Woertern
     */
    public String[] getGermanWords() {
        return germanWords;
    }

    /**
     * Gibt das Array mit den englischen Woertern zurueck
     * @return englishWords Das Array mit den englischen Woertern
     */
    public String[] getEnglishWords() {
        return englishWords;
    }

    /**
     * Gibt das aktuell ausgewaehlte Trennzeichen zurueck
     * @return trennzeichen Das aktuell ausgewaehlte Trennzeichen
     */
    public String getTrennzeichen() {
        return trennzeichen;
    }
}
