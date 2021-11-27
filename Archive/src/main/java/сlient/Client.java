package сlient;

import сlient.controller.ClientController;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {
        ClientController client = new ClientController();
        client.run();
    }
}
