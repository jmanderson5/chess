import server.ServerFacade;
import ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        ServerFacade serverFacade = new ServerFacade("http://localhost:" + 8080);
        System.out.println();
        System.out.println("👑 Welcome to chess. Type Help to start. 👑");
        System.out.println();

        new UserInterface().run(serverFacade);
    }
}