package http.response;

import exceptions.ForbiddenFileRequestException;
import file_util.FileFolder;
import file_util.FileReader;
import http.request.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;


public class HttpResponse {

    private StatusCode statusCode;
    private File file;

    public HttpResponse(HttpRequest request, FileFolder fileFolder) {

        if(request.getMethod() == HttpRequest.Method.GET)
            initGetResponse(request, fileFolder);
        else
            System.err.println("Unknown request method");
    }

    private void initGetResponse(HttpRequest request, FileFolder fileFolder) {

        String relativePath = request.getRelativePath();

        try {
            /*
            if(true)
                throw new Exception();
             */
            file = fileFolder.getFile(relativePath);
            if (fileFolder.isFoundElsewhere()) {
                this.statusCode = StatusCode.FILE_FOUND;
            }
            else
                this.statusCode = StatusCode.OK;
        }catch(FileNotFoundException e) {
            this.statusCode = StatusCode.FILE_NOT_FOUND;
        }catch(ForbiddenFileRequestException e) {
            this.statusCode = StatusCode.FORBIDDEN;
            file = null;
        }catch(Exception e) {
            this.statusCode = StatusCode.INTERNAL_SERVER_ERROR;
        }
    }

    public enum StatusCode {

        OK("HTTP/1.1 200 OK", ""),
        FILE_FOUND("HTTP/1.1 302 Found", ""),
        FORBIDDEN("HTTP/1.1 403 Forbidden", "<html><head></head><body><h1>403 Forbidden</h1></body></html>"),
        FILE_NOT_FOUND("HTTP/1.1 404 Not Found", "<html><head></head><body><h1>404 Not Found</h1></body></html>"),
        INTERNAL_SERVER_ERROR("HTTP/1.1 500 Internal Server Error", "<html><head></head><body><h1>500 Internal Server Error</h1></body></html>");

        private final String message;
        private final String htmlMessage;

        StatusCode(String message, String htmlMessage) {
            this.message = message;
            this.htmlMessage = htmlMessage;
        }

        public String getMessage() {
            return message;
        }
        public String getHtmlMessage() {
            return htmlMessage;
        }
    }

    public HttpResponsePacket getResponsePacket(HttpRequest request) {

        HttpResponseWriter responseWriter = new HttpResponseWriter();

        String responseText;
        if(statusCode == StatusCode.OK ||
                statusCode == StatusCode.FILE_FOUND) {
            int fileLength = (int)file.length();
            responseText = responseWriter.writeResponse(statusCode, request.getContentType(), fileLength);
        }else
            responseText = responseWriter.writeResponse(statusCode);

        System.out.println(" --- " + responseText + " --- ");

        if(file != null) {
            byte[] fileBytes = FileReader.readBytes(file);
            return new HttpResponsePacket(fileBytes, responseText);
        }
        return new HttpResponsePacket(responseText);
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

}
