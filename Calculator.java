import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    double add(double a, double b) throws RemoteException;
    double subtract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;

    // Operações adicionais
    double squareRoot(double a) throws RemoteException; // Raiz quadrada
    double negate(double a) throws RemoteException; // Inverter sinal
    void memoryClear() throws RemoteException; // Limpar memória
    double memoryRecall() throws RemoteException; // Recall da memória
    void memoryAdd(double a) throws RemoteException; // Adicionar à memória
    void memorySubtract(double a) throws RemoteException; // Subtrair da memória
    void clearEntry() throws RemoteException; // Limpar entrada
}
