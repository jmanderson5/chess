import server.Server;

public class Main {
    public static void main(String[] args) {
        Server newServer = new Server();

        newServer.run(8081);

        System.out.println("♕ 240 Chess Server: http://localhost:" + 8081);
    }
}