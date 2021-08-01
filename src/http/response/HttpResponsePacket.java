package http.response;

public class HttpResponsePacket {

    private final byte [] fileBytes;
    private final String responseText;

    public HttpResponsePacket(byte [] fileBytes, String responseText) {
        this.fileBytes = fileBytes;
        this.responseText = responseText;
    }

    public HttpResponsePacket(String responseText) {
        this.responseText = responseText;
        fileBytes = null;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public String getResponseText() {
        return responseText;
    }
}
