import file_util.FileFolder;
import file_util.FileServer;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.request.HttpRequestReader;
import http.response.HttpResponse;
import http.response.HttpResponsePacket;

import java.io.*;
import java.net.Socket;

public class ClientConnection {

    private final Socket socket;
    private final FileFolder directory;

    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintWriter printWriter;

    public ClientConnection(Socket socket, FileFolder directory) {
        this.socket = socket;
        this.directory = directory;
    }

    public void disconnect() {
        try {
            outputStream.close();
            printWriter.close();
            socket.close();
        }catch (IOException e) {
            //Ignore
        }
    }

    public void connect() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
        }catch(IOException e) {
            System.err.println("Error connecting streams.");
        }
    }

    public HttpRequest getRequest() {

        HttpRequestReader reader = new HttpRequestReader(inputStream);
        String requestString = reader.readRequest();
        HttpRequestParser parser = new HttpRequestParser();

        return parser.parseRequest(requestString);
    }

    public void sendResponse(HttpRequest request) {

        HttpResponse response = new HttpResponse(request, directory);
        HttpResponsePacket responsePacket = response.getResponsePacket(request);

        HttpResponse.StatusCode code = response.getStatusCode();
        String htmlMessage = code.getHtmlMessage();

        String responseText = responsePacket.getResponseText();
        byte [] bytes = responsePacket.getFileBytes();

        FileServer writer = new FileServer(outputStream);
        printWriter.print(responseText);

        if(bytes == null)
            printWriter.write(htmlMessage);
        printWriter.flush();

        if(bytes != null)
            writer.serveFile(bytes);
    }

}
