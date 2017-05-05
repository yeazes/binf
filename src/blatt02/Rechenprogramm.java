package blatt02;

/**
 *  Verwenden Sie die erweiterte Fraction anschließend für ein einfaches Rechenprogramm, das über die
 *  Kommandozeile zwei Brüche und einen Operator erhält, die so deﬁnierte Rechnung ausführt und das
 *  Ergebnis auf der Standard-Konsole ausgibt. Als Operatoren sind +, -, * und / zulässig. Achten Sie auf
 *  Fehlerbehandlung und eine geeignete Ausgabe von Fehlermeldungen auf System.err. Geben Sie bei
 *  Fehleingaben auch immer eine Anleitung zur Bedienung des Programms auf der Standard-Konsole aus.
 *
 *   Hinweis: Das Symbol * hat auf der Konsole eine besondere Bedeutung, deswegen geben Sie dieses beim
 *   Testen immer in " an. (Beispiel: java Calculator 1/2 " * " -1/2).
 * @author Ronja von Kittlitz, Tillmann Brodbeck
 * @version 24.04.17.
 */
public class Rechenprogramm {

    public static void main(String[] args) {
        String s = "5/4";
        System.out.println(s);

        if (s.matches("(-?)(\\d+)/([1-9])(\\d*)"))
            System.out.println("yes");
        if (s.matches("-?\\d+/[1-9]\\d*"))
            System.out.println("yes");

        String[] splitted = s.split("/");
        System.out.println(splitted[0]);
        System.out.println(splitted[1]);

    }
}