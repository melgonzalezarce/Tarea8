package Broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Melissa on 27/09/2015.
 */
public class MultiServerBrokerThread extends Thread {
    private Socket socket = null;

    public MultiServerBrokerThread(Socket socket) {
        super("MultiServerBrokerThread");
        this.socket = socket;
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Se ha detectado una nueva conexión.");
    }

    public void run() {

        try (
                PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            toClient.println("Bienvenido al Servidor. Que es lo que quiere comprar?");

            while ((inputLine = fromClient.readLine()) != null) {
                outputLine = procesarPeticion(inputLine);
                toClient.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String procesarPeticion(String inputLine){
        String output;
        switch (inputLine){
            case "pasteles":
                output = "Si lo tenemos";
                break;
            case "Bye":
                output = "Bye";
                break;
            default:
                output = "No tenemos ese servicio";
                break;
        }
        return output;
    }
}
