import server.ServerFacade;
import ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        String url = "http://localhost:" + 8080;
        ServerFacade serverFacade = new ServerFacade(url);
        System.out.println();
        System.out.println("👑 Welcome to chess. Type Help to start. 👑");
        System.out.println();

        new UserInterface().run(serverFacade, url);
    }
}