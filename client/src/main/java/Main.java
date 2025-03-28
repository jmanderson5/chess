import ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        System.out.println("👑 Welcome to chess. Type Help to start. 👑");
        System.out.println();

        UserInterface userInterface = new UserInterface();
        userInterface.run();
    }
}