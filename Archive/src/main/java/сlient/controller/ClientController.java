package сlient.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private static String role;

    public void run() throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.print("Enter your role> ");
        role = keyboard.readLine();
        System.out.print("Type 'help' for help\n");

        while(true) {
            System.out.print("You> ");
            String command = keyboard.readLine();

            if(command.equals("quit")) {
                out.println(role + " " + command);
                break;
            }

            out.println(role + " " + command);

            System.out.print("Server> ");
            String line;
            while (!(line = input.readLine()).equals("\0")) {
                System.out.print(line + "\n");
            }
        }

        socket.close();
    }
}
