import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    double add(double a, double b) throws RemoteException;
    double subtract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;
    double squareRoot(double a) throws RemoteException;
    double negate(double a) throws RemoteException;
    void memoryClear() throws RemoteException;
    double memoryRecall() throws RemoteException;
    void memoryAdd(double a) throws RemoteException;
    void memorySubtract(double a) throws RemoteException; 
    void clearEntry() throws RemoteException; // Limpar entrada
}
