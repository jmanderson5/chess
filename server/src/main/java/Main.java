import server.Server;

public class Main {
    public static void main(String[] args) {
        Server newServer = new Server();

        newServer.run(0);

        System.out.println("♕ 240 Chess Server: http://localhost:" + 8080);
    }
}