import http.request.HttpRequest;

public class ClientThread extends Thread {

    private final ClientConnection connection;

    public ClientThread(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        long time = System.currentTimeMillis();
        connection.connect();
        handleRequest();
        connection.disconnect();
        System.out.println("Thread lifecycle time: " + (System.currentTimeMillis() - time) + "ms");

    }

    public void handleRequest() {
        HttpRequest request = connection.getRequest();
        connection.sendResponse(request);
    }

}
