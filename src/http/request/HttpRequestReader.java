package http.request;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequestReader {

    private final InputStream stream;

    public HttpRequestReader(InputStream stream) {
        this.stream = stream;
    }

    public synchronized String readRequest() {

        byte[] buff = new byte[2048];
        StringBuilder buffer = new StringBuilder();
        try {
            do {
                int read = stream.read(buff, 0, buff.length);
                //System.out.println(read);
                if(read == -1)
                    continue;
                String readBytes = new String(buff, 0, read);
                buffer.append(readBytes);
            }while(stream.available() > 0);
        }catch(IOException e) {
            //
        }
        System.out.println("REQUEST : \n" +buffer.toString());
        return buffer.toString();
    }

}
