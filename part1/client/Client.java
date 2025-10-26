import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Client {
  public static void main(String[] args) {
    try {
      String name = "calc";
      Registry registry = LocateRegistry.getRegistry(); //
      localhost by default
        ICalc calc = (ICalc) registry.lookup(name);
      System.out.println("sum(1.5, 2.25) = " + calc.sum(1.5,
            2.25));
      System.out.println("product(3.0, 4.0) = " +
          calc.product(3.0, 4.0));
      try {
        System.out.println("factorial(7) = " +
            calc.factorial(7));
        System.out.println("sqrt(9) = " + calc.sqrt(9));
        System.out.println("sqrt(-4) = " + calc.sqrt(-4)); //
        should fail visibly
      } catch (InvalidInputException e) {
        System.out.println("InvalidInputException (expected):
            " + e.getMessage());
      }
      // These will fail on the first run
      System.out.println("sum2(Args(1.5, 2.25)) = " +
          calc.sum2(new Args(1.5, 2.25)));
      System.out.println("product2(Args(3.0, 4.0)) = " +
          calc.product2(new Args(3.0, 4.0)));
    } catch (Exception e) {
      System.err.println("Client exception:");
      e.printStackTrace();
    }
  }
}
