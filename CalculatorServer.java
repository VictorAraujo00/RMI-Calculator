import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Inicia o registro RMI
            LocateRegistry.createRegistry(1099);
            Calculator calculator = new CalculatorImpl();
            Naming.rebind("CalculatorService", calculator);
            System.out.println("Calculator Server is ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

