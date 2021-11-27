package server.controller;

import server.handlers.ClientInputHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private static final int PORT = 9090;

    private static final ArrayList<ClientInputHandler> clients = new ArrayList<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public void run() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        while(true) {
            System.out.println("[Server] Waiting for clients...");
            Socket client = listener.accept();
            System.out.println("[Server] New client connected! ");

            ClientInputHandler clientThread = new ClientInputHandler(client, clients);
            clients.add(clientThread);

            pool.execute(clientThread);
        }
    }

    public static void showMessage(String message) {
        System.out.println("[Server] " + message + "\n");
    }
}
