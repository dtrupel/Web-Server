package http;

public enum ContentType {

    TEXT_HTML("text/html","html,htm"),
    IMAGE_PNG("image/png","png"),
    IMAGE_GIF("image/gif","gif"),
    IMAGE_JPEG("image/jpeg","jpg,jpeg"),
    UNKNOWN_EXTENSION("application/unknown","*");

    private final String type;
    private final String[] extensions;

    ContentType(String type, String extensions) {
        this.type = type;
        this.extensions = extensions.split(",");
    }

    public String getType(){
        return type;
    }

    public static ContentType parseContentType(String extension) {

        for (ContentType type : values()) {
            for (String ex : type.extensions) {
                if (extension.equals(ex))
                    return type;
            }
        }
        return UNKNOWN_EXTENSION;
    }
}
