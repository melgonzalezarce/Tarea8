package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Melissa on 27/09/2015.
 */
public class ProxyClient {
    public static void main(String[] args) throws IOException {

        /*if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }*/

        String hostName = "localhost";
        int portNumber = 4444;

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromServer = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader mensaje =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServerStream;
            String fromUser;

            while ((fromServerStream = fromServer.readLine()) != null) {
                System.out.println("Server: " + fromServerStream);
                if (fromServerStream.equals("Bye."))
                    break;

                fromUser = mensaje.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    toServer.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No conozco el host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede leer/escribir para la conexion de " +
                    hostName);
            System.exit(1);
        }
    }


}
