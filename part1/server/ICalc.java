import java.rmi.Remote;
import java.rmi.RemoteException;
/**
* Remote calculator contract.
*/
public interface ICalc extends Remote {
  int factorial(int n) throws RemoteException,
      InvalidInputException;

  double sum(double a, double b) throws RemoteException;
  double product(double a, double b) throws RemoteException;

  double sum2(Args args) throws RemoteException;
  double product2(Args args) throws RemoteException;

  double sqrt(double x) throws RemoteException,
         InvalidInputException;
}
