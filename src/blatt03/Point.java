package blatt03;

import java.util.Arrays;

/**
 * Repräsentiert einen n-dimensionalen Datenpunkt, der mit einer variablen Parameter-liste von double - Werten erzeugt werden
 * kann.
 *
 * @author Ronja von Kittlitz, Tillmann Brodbeck
 * @version 03.05.17
 */
public class Point extends Geometry implements Comparable {

    private double[] coords;

    public Point(double... coords) {
        super(coords.length);
        this.coords = coords;
    }

    public double[] getCoords() {
        return coords;
    }


    @Override
    public double volume() {
        return 0;
    }

    /**
     * encapsulate(Geometry other) - Umgibt diese und die übergebene Geometry mit der gleichen Anzahl an Dimensionen mit
     * einer neuen Geometry und gibt diese zurück. Die neue Geometry muss einerseits eine minimale Ausdehnung haben und
     * andererseits diese und die übergebene Geometry vollständig enthalten. Wenn zwei Geometry - Instanzen eine
     * unterschiedliche Anzahl an Dimensionen haben, wird null zurückgeliefert.
     * @param other
     * @return
     */
    public Geometry encapsulate(Geometry other) {
        if (dimensions() != other.dimensions()) return null;
        if (other instanceof Point){
            if (((Point)other).getCoords() == getCoords())
                return this;
            return new Volume(this, (Point)other);
        }
        if (other instanceof Volume) {
            Volume o = (Volume)other;
            return o.encapsulate(this );
        }
        return null;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Geometry) {
            if (this.volume() > ((Geometry) o).volume())
                return 1;
            if (this.volume() == ((Geometry) o).volume())
                return 0;
            if (this.volume() < ((Geometry) o).volume())
                return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Point{" +
                "coords=" + Arrays.toString( coords ) +
                "} " + super.toString();
    }
}

//wäre es nicht fast klüger das der n-dimensionale Punkt den 2-dim. extended?  Siehe Circle Ellipse im Skript