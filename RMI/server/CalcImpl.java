import java.rmi.RemoteException;
public class CalcImpl implements ICalc {
  public CalcImpl() {}
  @Override
  public int factorial(int n) throws RemoteException,
         InvalidInputException {
           if (n < 0) {
             throw new InvalidInputException("factorial: n must be >= 0 (got " + n + ")");
           }
           int res = 1;
           for (int i = 2; i <= n; i++) res *= i;
           return res;
  }
  @Override
  public double sum(double a, double b) throws RemoteException {
    return a + b;
  }
  @Override
  public double product(double a, double b) throws RemoteException {
    return a * b;
  }
  @Override
  public double sum2(Args args) throws RemoteException {
    // To be implemented
    return 0.0;
  }
  @Override
  public double product2(Args args) throws RemoteException {
    // To be implemented
    return 0.0;
  }
  @Override
  public double sqrt(double x) throws RemoteException,
         InvalidInputException {
           // To be implemented: make sure to use the invalid input exception
             return 0.0;
  }
}
