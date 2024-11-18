import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    private double memory = 0;

    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double subtract(double a, double b) throws RemoteException {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        return a * b;
    }

    @Override
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) {
            throw new RemoteException("Divisão por zero não permitida");
        }
        return a / b;
    }

    @Override
    public double squareRoot(double a) throws RemoteException {
        return Math.sqrt(a);
    }

    @Override
    public double negate(double a) throws RemoteException {
        return -a;
    }

    @Override
    public void memoryClear() throws RemoteException {
        memory = 0;
    }

    @Override
    public double memoryRecall() throws RemoteException {
        return memory;
    }

    @Override
    public void memoryAdd(double a) throws RemoteException {
        memory += a;
    }

    @Override
    public void memorySubtract(double a) throws RemoteException {
        memory -= a;
    }

    @Override
    public void clearEntry() throws RemoteException {
    
    }
}
