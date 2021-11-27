package server;

import server.controller.ServerController;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerController server = new ServerController();
        server.run();
    }
}
