package file_util;

import java.io.IOException;
import java.io.OutputStream;

public class FileServer {

    private final OutputStream outputStream;

    public FileServer(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void serveFile(byte [] buffer) {
        try {
            outputStream.write(buffer);
            outputStream.flush();
        }catch(IOException e) {
            System.err.println("Error writing bytes.");
        }
    }
}
