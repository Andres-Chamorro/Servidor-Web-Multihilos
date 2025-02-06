import java.net.*;
import java.util.concurrent.*;

public final class ServidorWeb{
    private static final int NUM_HILOS = 10;
    private static final int PUERTO = 6789;
    public static void main(String argv[]) throws Exception{
        
        ServerSocket socketServidor = new ServerSocket(PUERTO);
        System.out.println("Servidor Web escuchando en el puerto " + PUERTO + "...");

        ExecutorService pool = Executors.newFixedThreadPool(NUM_HILOS);
        
        try {
            while(true){
                Socket socketCliente = socketServidor.accept();
                pool.execute(new SolicitudHttp(socketCliente));
            }
        } finally {
            pool.shutdown();
            socketServidor.close();
        }

    }
}
