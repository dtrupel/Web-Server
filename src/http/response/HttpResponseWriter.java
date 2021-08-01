package http.response;

import file_util.FileFolder;
import file_util.FileServer;
import http.ContentType;
import http.response.HttpResponse;

import java.util.Date;

public class HttpResponseWriter {

    public String writeResponse(HttpResponse.StatusCode code) {

        return code.getMessage() + "\r\n" +
                "Date: " + new Date() + "\r\n\r\n";
    }

    public String writeResponse(HttpResponse.StatusCode code, ContentType contentType, int fileLength) {

        System.out.println(code.name());
        String builder = code.getMessage() + "\r\n" +
                "Date: " + new Date() + "\r\n" +
                "Content-Type: " + contentType.getType() + "\r\n" +
                "Content-Length: " + fileLength + "\r\n\r\n";
        return builder;
    }
}
