package http.request;

import http.ContentType;

import java.util.List;

public class HttpRequest {

    private final Method method;
    private final String relativePath;
    private final List<HttpHeader> headers;
    private String body;

    public HttpRequest(Method method, String relativePath, List<HttpHeader> headers) {
        this.method = method;
        this.relativePath = relativePath;
        this.headers = headers;
    }

    public HttpRequest(Method method, String relativePath, List<HttpHeader> headers, String body) {
        this(method, relativePath, headers);
        this.body = body;
    }

    public enum Method {

        GET,
        POST,
        PUT,
        UNKNOWN;

        public static Method parseMethod(String method) {
            for(Method m : Method.values()) {
                if(method.trim().equals(m.name()))
                    return m;
            }
            return Method.UNKNOWN;
        }
    }

    public Method getMethod() {
        return method;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public ContentType getContentType() {

        String [] parts = relativePath.split("\\.");
        if(parts.length == 2)
            return ContentType.parseContentType(parts[1]);

        return ContentType.parseContentType("html");
    }

    public String getBody() {
        return body;
    }
}
