package blatt04;

/**
 * Contains a main class to execute a Calculate instance, which calculates an
 * operation on two Fraction instances.
 *
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class Calculator {

   /*
    * The Operators
    */
   public static final String ADD = "+";
   public static final String SUBSTRACT = "-";
   public static final String MULTIPLY = "*";
   public static final String DIVIDE = "/";

   /**
    * @param args
    */
   public static void main(String[] args) {

      if (args.length != 3) {
         System.err.println("invalid number of arguments (must be three)");
         printUsage();
         System.exit(1);
      } else {
         Calculator calc = new Calculator();
         String result = calc.calc(args[0], args[1], args[2]);
         if (result == null) {
            System.err.println(calc.getErrorMessage());
            printUsage();
            System.exit(1);
         } else {
            System.out.println("= " + result);
         }
      }
   }

   /**
    * Prints a short description of the usage on the standard console.
    */
   private static void printUsage() {
      System.out.println("Usage: java Calculator fraction operator fraction");
      System.out.println("a fraction is defined by " + Fraction.REGEX);
      System.out.println("valid operators are +,-, *, /");
   }

   /**
    * Holds the error message of the last call of
    * {@link #calc(String, String, String)} which went wrong.
    */
   private String errorMessage;

   /**
    * Calculates the formula given by <code>a operator b</code>. If a, operator
    * or b are not valid, null will be returned and errorMessage will hold a
    * description of the error that occurred.
    *
    * @param a        the first Fraction argument
    * @param operator operator to connect the arguments with
    * @param b        the second Fraction argument
    * @return The result of the operation as Fraction or null.
    */
   private Fraction calc(Fraction a, String operator, Fraction b) {

      Fraction result;
      /*
       * differentiate between operators and compute regarding operation.
       */
      switch (operator) {

         case ADD:
            result = a.add(b);
            break;
         case SUBSTRACT:
            result = a.subtract(b);
            break;
         case MULTIPLY:
            result = a.multiply(b);
            break;
         case DIVIDE:
            if (b.getNumerator() == 0) {
               this.errorMessage = "divides zero";
               return null;
            } else {
               result = a.divide(b);
            }
            break;
         default:
            this.errorMessage = "Operation " + operator + " unknown";
            return null;
      }

      return result;

   }

   /**
    * Calculates the formula given by <code>a operator b</code>. If a, operator
    * or b are not valid, null will be returned and errorMessage will hold a
    * description of the error that occurred.
    *
    * @param a        String representation of the first argument
    * @param operator operator to connect the arguments with
    * @param b        String representation of the second argument
    * @return The result of the operation as String or null.
    */
   public String calc(String a, String operator, String b) {

      Fraction fractionA = parseFraction(a);
      Fraction fractionB = parseFraction(b);

      if (operator == null)
          return null;

      if (fractionA == null || fractionB == null) {
          // falls es Gleitkommaargumente sind
         double da = Double.parseDouble( a );
         double db = Double.parseDouble( b );
         Fraction result2 = calc(da, operator, db);
         if (result2 == null) {
              return null;
         }
         return result2.toString();
      }

      Fraction result = calc(fractionA, operator, fractionB);

      if (result == null) {
         return null;
      }

      return result.toString();

   }

   /**
    * Return the error message of the last call of
    * {@link #calc(String, String, String)} which went wrong.
    *
    * @return the last error message
    */
   public String getErrorMessage() {
      return this.errorMessage;
   }

   /**
    * Parses the given String to a Fraction and returns it. If it cannot be
    * parsed, null will be returned and errorMessage will hold a description of
    * the error that occurred.
    *
    * @param fraction String to be parsed to a fraction
    * @return A Fraction representing the given String or null.
    */
   private Fraction parseFraction(String fraction) {
      if (!fraction.matches(Fraction.REGEX)) {
         return null;
      }
      return Fraction.parseFraction(fraction);

   }


    /**
     * Erweitern Sie nun den Bruchrechner vom letzten Aufgabenblatt um die Möglichkeit nicht nur zwei Brüche, sondern
     * auch natürliche und Fließkommazahlen beliebig miteinander zu verknüpfen. Die Ausgabe soll stets eine
     * Fließkommazahl sein, es sei denn, zwei Brüche wurden eingegeben. In diesem Fall soll die Ausgabe in der Form
     * eines Bruches (z.B. 4/1) erfolgen.
     * @param a erstes Fließkommaargument
     * @param operator
     * @param b zweites Fließkommaargument
     * @return Bruch
     */
   public Fraction calc(double a, String operator, double b) {
        int i = (int)(a/b);
        Fraction af = new Fraction(i,1);
        Fraction bf = new Fraction(1,1);

        return calc(af, operator, bf);

   }



}
