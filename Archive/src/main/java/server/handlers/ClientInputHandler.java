package server.handlers;

import server.controller.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientInputHandler implements Runnable {
    private final Socket client;
    private final BufferedReader in;
    private final PrintWriter out;
    private final ArrayList<ClientInputHandler> clients;
    private boolean isConnected = true;

    public ClientInputHandler(Socket clientSocket, ArrayList<ClientInputHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while(isConnected) {
                String[] request = in.readLine().split(" ");

                switch (request[1]) {
                    case "help":
                        out.println(CommandHandler.sendHelp());
                        break;
                    case "all":
                        out.println(CommandHandler.showAllStudents(request));
                        break;
                    case "find":
                        out.println(CommandHandler.findStudentById(request));
                        break;
                    case "add":
                        out.println(CommandHandler.addNewStudent(request));
                        break;
                    case "edit":
                        out.println(CommandHandler.editStudent(request));
                        break;
                    case "quit":
                        ServerController.showMessage("Client disconnected!");
                        isConnected = false;
                        break;
                    default:
                        out.println("Enter a valid command\n\0");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("IO exception in client handler");
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
