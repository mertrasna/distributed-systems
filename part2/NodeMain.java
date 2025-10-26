import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.stream.Collectors;

public class NodeMain {

    private static final String NODE_PREFIX = "node:";

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: java NodeMain <groupName> <nodeId>");
            System.err.println("Example: java NodeMain chatA 0   (creates registry)");
            System.err.println("         java NodeMain chatA 1   (locates registry)");
            System.exit(1);
        }

        final String groupName = args[0];
        final String nodeId = args[1];
        final String selfBinding = NODE_PREFIX + nodeId; // e.g., "node:0"

        // 1) Create or locate registry
        if ("0".equals(nodeId)) {
            try {
                LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                System.out.println("Created local RMI registry on port " + Registry.REGISTRY_PORT);
            } catch (Exception e) {
                System.out.println("Registry already running (or creation failed): " + e.getMessage());
            }
        }
        Registry registry = LocateRegistry.getRegistry(); // localhost:1099

        // 2) Export & bind our Node implementation
        NodeImpl impl = new NodeImpl(selfBinding, groupName, registry);
        registry.rebind(selfBinding, impl);
        System.out.printf("Bound as %s for group '%s'%n", selfBinding, groupName);

        // 3) One-shot discovery: list all nodes registered as "node:*"
        List<String> allNodeNames = Arrays.stream(registry.list())
                .filter(n -> n.startsWith(NODE_PREFIX))
                .collect(Collectors.toList());
        System.out.println("Discovered nodes: " + allNodeNames);

        // 4) Send joinGroup to ALL discovered nodes
        for (String peerName : allNodeNames) {
            if (peerName.equals(selfBinding)) continue;
            try {
                INode peer = (INode) registry.lookup(peerName);
                peer.joinGroup(groupName, selfBinding);
            } catch (Exception e) {
                // Best-effort in this lab: ignore failures
            }
        }

        // 5) Simple client interface to test code
        System.out.println("Commands: /members | /send <msg> | /leave | /quit");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                if (!sc.hasNextLine()) break;
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                if (line.equalsIgnoreCase("/members")) {
                    System.out.println("Members (local view): " + impl.localMembers());
                    continue;
                }

                if (line.startsWith("/send ")) {
                    String msg = line.substring(6).trim();
                    impl.multicast(msg);
                    continue;
                }

                if (line.equalsIgnoreCase("/leave")) {
                    Set<String> snapshot = new HashSet<>(impl.localMembers());
                    for (String memberId : snapshot) {
                        if (memberId.equals(selfBinding)) continue;
                        try {
                            INode peer = (INode) registry.lookup(memberId);
                            peer.leaveGroup(groupName, selfBinding);
                        } catch (Exception ignored) {}
                    }
                    try { registry.unbind(selfBinding); } catch (Exception ignored) {}
                    System.out.println("Left group and unbound. Bye.");
                    System.exit(0);
                }

                if (line.equalsIgnoreCase("/quit")) {
                    System.out.println("Exiting without leave. Bye.");
                    System.exit(0);
                }

                System.out.println("Unknown command.");
            }
        }
    }
}
