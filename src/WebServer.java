import file_util.FileFolder;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class WebServer {

    private static ServerSocket serverSocket;

    private final Port port;
    private final FileFolder sharedDirectory;

    private WebServer(Port port, FileFolder sharedDirectory) {
        this.port = port;
        this.sharedDirectory = sharedDirectory;
    }

    public static void main(String[] args) {

        WebServer server = createInstance(args);
        server.start();
    }

    public void start() {

        connect();

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(clientSocket, sharedDirectory);
                new ClientThread(connection).start();
            }
        }catch(IOException e) {
            System.err.println("Server shutting down...");
            System.exit(1);
        }finally {
            try {
                serverSocket.close();
            }catch(IOException e) {
                //Ignore
            }
        }
    }

    private void connect() {

        try {
            serverSocket = new ServerSocket();
            SocketAddress localAddress = new InetSocketAddress(port.getNumber());
            serverSocket.bind(localAddress);
        }catch(IOException e) {
            System.err.println("Server could not connect.");
            System.exit(1);
        }
    }


    private static WebServer createInstance(String [] args) {

        if(args.length != 2) {
            System.out.println("Enter arguments in format [port][resource file]");
            System.exit(1);
        }
        Port port = null;
        FileFolder folder = null;
        try {
            port = new Port(Integer.parseInt(args[0]));
            folder = new FileFolder(new File(args[1]));
        }catch(NumberFormatException e) {
            System.err.println("Port should be a number: " + e.getMessage());
            System.exit(1);
        }
        return new WebServer(port, folder);
    }
}
