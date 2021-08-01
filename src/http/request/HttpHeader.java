package http.request;

public class HttpHeader {

    private final Type type;
    private final String value;

    public HttpHeader(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public enum Type {

        ACCEPT("Accept"),
        ACCEPT_CHARSET("Accept-Charset"),
        ACCEPT_DATETIME("Accept-Datetime"),
        ACCEPT_ENCODING("Accept-Encoding"),
        ACCEPT_LANGUAGE("Accept-Language"),
        CONNECTION("Connection"),
        LOCATION("Location"),
        CACHE_CONTROL("Cache-Control"),
        CONTENT_ENCODING("Content-Encoding"),
        CONTENT_TYPE("Content-Type"),
        CONTENT_LENGTH("Content-Length"),
        DATE("Date"),
        HOST("Host"),
        USER_AGENT("User-Agent"),
        UNKNOWN("Unknown");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Type parseType(String value) {
            for(Type type : Type.values()) {
                if(value.equals(type.getValue()))
                    return type;
            }
            return Type.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return type.name() + " , " + value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
