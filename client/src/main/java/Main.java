import server.Server;
import server.ServerFacade;
import ui.UserInterface;

public class Main {

    private static Server server;
    private static ServerFacade serverFacade;

    public static void main(String[] args) {
        server = new Server();
        int port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new ServerFacade("http://localhost:" + port);
        System.out.println();

        System.out.println("👑 Welcome to chess. Type Help to start. 👑");
        System.out.println();

        UserInterface userInterface = new UserInterface();
        userInterface.run(serverFacade);
        server.stop();
    }
}