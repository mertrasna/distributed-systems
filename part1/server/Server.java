import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class Server {
  public static void main(String[] args) {
    try {
      String name = "calc";
      CalcImpl impl = new CalcImpl();
      // Export as a unicast remote object (auto-creates the stub).
      ICalc stub = (ICalc)
        UnicastRemoteObject.exportObject(impl, 0);
      // Create and bind a registry locally.
      Registry registry =
        LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
      registry.rebind(name, stub);
      System.out.println("RMI Calculator server ready (bound as \"" + name + "\")");
    } catch (Exception e) {
      System.err.println("Server exception:");
      e.printStackTrace();
    }
  }
}
