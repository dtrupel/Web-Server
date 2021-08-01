package file_util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    public static byte [] readBytes(File file) {

        byte [] buffer = new byte [(int)file.length()];
        try {
            FileInputStream input = new FileInputStream(file);
            input.read(buffer, 0, buffer.length);
            input.close();
        }catch(IOException e) {
            System.err.println("Not good");
        }
        return buffer;
    }

}
