package blatt10;




import java.util.Observable;
import java.util.Random;

/**
 * Entwerfen Sie Klassen zur Repräsentation der Spiellogik.
 * Das Spiel selber als auch alle darin enthaltenen Felder
 * sollten durch eigene Objekte dargestellt werden können.
 * Achten Sie darauf, dass solche Klassen, die später mit
 * der View kommunizieren sollen, vom Typ
 * java.util.Observable sind.
 *
 * @author Ronja von Kittlitz, Tillmann Brodbeck
 * @version 29.06.17
 */
public class MSModel extends Observable {

    private int hoehe;
    private int breite;
    private Feld[][] felder;
    private int zustand;

    private int numberOfBombs;

    /**
     * The actual number of flags set on all {@code Field} elements of this
     * {@code GameBoard}.
     */
    private int numberOfFlags;

    /**
     * The actual number of revealed {@code Field} elements of this
     * {@code GameBoard}.
     */
    private int numberOfRevealed;

    public MSModel(int hoehe, int breite, int bomben) {
        if(hoehe < 0||breite < 0|| bomben <0 || bomben >= breite * hoehe) throw new IllegalArgumentException("Die Werte müssen positiv sein und nicht mehr Bomben als Felder");
        this.hoehe = hoehe;
        this.breite = breite;
        zustand = 0;
        felder = new Feld[hoehe][breite];
        initialize(bomben, felder);
    }

    /**
     * Startposition zufällig erstellen
     * @param bomben Anzahl der Bomben
     * @param felder Spielfeld
     */
    private void initialize(int bomben, Feld[][] felder) {

        // Felder erstellen
        for (int i = 0; i < hoehe; i++) {
            for (int j = 0; j < breite; j++) {
                felder[i][j] = new Feld(this);
            }
        }

        //Bomben einfügen
        Random random = new Random();
        for (int b = 0; b < bomben; b++) {
            int i = random.nextInt( hoehe - 1 );
            int j = random.nextInt( breite - 1 );
            //wenn es da schon ne Bombe gibt zähle nicht hoch
            if (felder[i][j].getBombe())
                b--;
            else
                felder[i][j].setBombe();
        }

        // Nachbaren initialisieren
        for (int i = 0; i < hoehe; i++) {
            for (int j = 0; j < breite; j++) {
                felder[i][j].setNachbarn( listNachbaren( i, j ) );
            }
        }

    }

    /**
     * Nachbaren eines Feldes auflisten
     * @param i
     * @param j
     * @return Liste der Nachbaren
     */
    private Feld[] listNachbaren(int i, int j){
        Feld[] list = new Feld[8];
        list[0] = getEintrag(i-1, j);
        list[1] = getEintrag(i-1, j+1);
        list[2] = getEintrag(i, j+1);
        list[3] = getEintrag(i+1, j+1);
        list[4] = getEintrag(i+1, j);
        list[5] = getEintrag(i+1, j-1);
        list[6] = getEintrag(i, j);
        list[7] = getEintrag(i, j-1);
        return list;
    }

    /**
     * Feld aus Spielfeld
     * @param i
     * @param j
     * @return Feld oder null
     */
    private Feld getEintrag(int i, int j) {
        if (i>=0 && j>=0 && i<hoehe && j<breite) {
            return felder[i][j];
        }
        else return null;
    }

    public Feld[][] getFelder() {
        return felder;
    }

    public int getHoehe() {
        return hoehe;
    }

    public int getBreite() {
        return breite;
    }


    public void fieldRevealed(Feld f) {

        this.numberOfRevealed++;
        if (f.getBombe()) {
            this.zustand = -1;
        } else if (numberOfRevealed + numberOfBombs == felder.length
                * felder[0].length) {
            this.zustand = 1;
        }

        this.setChanged();
        this.notifyObservers();
    }


    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public int getNumberOfRevealed() {
        return numberOfRevealed;
    }

    public int getZustand() {

        return zustand;
    }

    public void flagChanged(Feld f) {

        if (f.getFlagged()) {
            this.numberOfFlags++;
        } else {
            this.numberOfFlags--;
        }

        this.setChanged();
        this.notifyObservers();
    }

}
