import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public final class SolicitudHttp implements Runnable{
    final static String CRLF = "\r\n";
    Socket socket;

    public SolicitudHttp(Socket socket) throws Exception{
        this.socket = socket;
    }

    public void run(){
        try{
            proceseSolicitud();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void proceseSolicitud() throws Exception{
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String lineaDeSolicitud = br.readLine();

        System.out.println();
        System.out.println(lineaDeSolicitud);

        StringTokenizer partesLinea = new StringTokenizer(lineaDeSolicitud);
        partesLinea.nextToken();
        String nombreArchivo = partesLinea.nextToken();

        nombreArchivo = "." + nombreArchivo;

        String lineaDelHeader = null;
        while((lineaDelHeader = br.readLine()).length() != 0){
            System.out.println(lineaDelHeader);
        }

        FileInputStream fis = null;
        boolean existeArchivo = true;
        try{
            fis = new FileInputStream(nombreArchivo);
        } catch (FileNotFoundException e){
            existeArchivo = false;
        }

        System.out.println("Archivo solicitado: " + nombreArchivo);
        String lineaDeEstado = null;
        String lineaDeTipoContenido = null;
        String cuerpoMensaje = null;

        if(existeArchivo){
            lineaDeEstado = "HTTP/1.0 200 OK" + CRLF;
            lineaDeTipoContenido = "Content-type: " + contentType(nombreArchivo) + CRLF;
        } else {
            lineaDeEstado = "HTTP/1.0 404 Not Found" + CRLF;
            lineaDeTipoContenido = "Content-type: text/html" + CRLF;
            cuerpoMensaje = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
        }

        os.writeBytes(lineaDeEstado);
        os.writeBytes(lineaDeTipoContenido);
        os.writeBytes(CRLF);

        if(existeArchivo){
            enviarBytes(fis, os);
            fis.close();
        } else {
            os.writeBytes(cuerpoMensaje);
        }

        os.close();
        br.close();
        socket.close();
    }

     private static String contentType(String nombreArchivo) {
        if (nombreArchivo.endsWith(".htm") || nombreArchivo.endsWith(".html")) {
            return "text/html";
        }
        if (nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (nombreArchivo.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream"; 
    }

    private static void enviarBytes(FileInputStream fis, DataOutputStream os) throws Exception {
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }
}



   