package blatt08;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronja on 17.06.17.
 */
public class ReplaceVisitor implements  FileVisitor {

    private boolean recursive;
    private File root;
    private Pattern search;
    private String replacement;

    public ReplaceVisitor(File root, boolean recursive, Pattern search, String replacement) {
        this.search = search;
        this.replacement = replacement;
        this.recursive = recursive;
        this.root = root;
    }

    /**
     * wird nicht benutzt
     * @param dir
     *           the directory that has been visited
     * @return CONTINUE
     */
    @Override
    public FileVisitResult postVisitDirectory(File dir) {
        return FileVisitResult.CONTINUE;
    }

    /**
     *
     * @param dir
     *           the directory that may be visited
     * @return ob directory rekursiv durchlaufen werden soll
     */
    @Override
    public FileVisitResult preVisitDirectory(File dir) {
        if (recursive || this.root.equals(dir)) {
            return FileVisitResult.CONTINUE;
        } else {
            return FileVisitResult.SKIP_SUBTREE;
        }
    }

    /**
     *
     * @param dir
     *           the directory that could not be visited
     * @return
     */
    @Override
    public FileVisitResult visitFailed(File dir) {
        System.out.println(dir + " konnte nicht bearbeitet werden");
        return FileVisitResult.CONTINUE;
    }

    /**
     * Durchläuft eine Datei:
     * Durchlaufen Sie jede dieser Dateien vollständig und ersetzen Sie jedes Vorkom- men eines vom Nutzer gegebenen
     * regulären Ausdrucks durch eine vom Nutzer gegebene Zeichenkette.
     *Da reguläre Ausdrücke auch Zeilenumbrüche enthalten können, müssen Sie die jewei- lige Datei vollständig in einen
     *String umwandeln. Das Zeichen, dass einen Zei- lenumbruch in Ihrem Betriebssystem definiert, erhalten Sie durch
     *den Aufruf von System.getProperty("line.separator"). Schreiben Sie den veränderten String am Ende wieder zurück
     *in die Datei.
     * @param file
     *           the file that is visited
     * @return CONTINUE
     */
    @Override
    public FileVisitResult visitFile(File file) {
        //Inhalt der Datei -> String
        BufferedReader r;
        String nl = System.getProperty("line.separator");
        try {
            r = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        String text = "";
        String line = null;
        try {
            line = r.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null) {
            text += (line + nl);
            line = null;
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // im String Search durch Replace ersetzen
        Matcher m = search.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        String newText = sb.toString();
        FileWriter w = null;
        //String in File zurück schreiben
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            w = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            w.write(newText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }
}

//Fragen: bessere Lösung für Löschen und neu erstellen für überschreiben
//