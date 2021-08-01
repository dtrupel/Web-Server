package http.request;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {

    public HttpRequest parseRequest(String request) {

        HttpRequest.Method method = parseMethod(request);
        String path = readRelativePath(request);
        List<HttpHeader> headers = parseHeaders(request);

        return new HttpRequest(method, path, headers);
    }

    private HttpRequest.Method parseMethod(String request) {

        String [] lines = request.split("\r\n");
        String firstLine = lines[0];
        String [] firstLineParts = firstLine.split("\\s");

        return HttpRequest.Method.parseMethod(firstLineParts[0]);
    }

    private String readRelativePath(String request) {

        String [] lines = request.split("\r\n");
        String firstLine = lines[0];
        String [] firstLineParts = firstLine.split("\\s");

        return firstLineParts[1];
    }

    private List<HttpHeader> parseHeaders(String request) {

        List<HttpHeader> headers = new ArrayList<>();
        String [] lines = request.split("\r\n");

        for(int i = 1; i < lines.length; i++) {
            String[] lineParts = lines[i].split(":\\s");
            HttpHeader header = new HttpHeader(HttpHeader.Type.parseType(lineParts[0]), lineParts[1]);
            headers.add(header);
        }

        return headers;
    }


}