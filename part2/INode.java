import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface INode extends Remote {
  // Add 'fromId' to local members for group.
  void joinGroup(String group, String fromId) throws RemoteException;
  // remove 'fromId' from its local members for group (if present).
  void leaveGroup(String group, String fromId) throws RemoteException;
  // Deliver an application message locally (e.g., print to console).
  void sendToMember(String group, String fromId, String message) throws RemoteException;
  // return a copy of the current members for group.
  Set<String> getMembers(String group) throws RemoteException;
}
