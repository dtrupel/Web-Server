package file_util;

import java.io.File;
import java.util.Objects;

public class FileValidator {

    private final File directory;

    private boolean found;
    private String returnPath;

    public FileValidator(File directory) {
        this.directory = directory;
        found = false;
        returnPath = "";
    }

    public boolean fileExists(String path) {
        return fileExists(directory, path);
    }

    private boolean fileExists(File directory, String path) {

        String [] pathParts = path.split("/");
        String relativePath = "/"+pathParts[pathParts.length-1];

        File [] files = directory.listFiles();

        for(File file : Objects.requireNonNull(files)) {
            if(file.isDirectory()) {
                found = fileExists(file, relativePath);
            }
            if(file.getAbsolutePath().endsWith(relativePath)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public String findNewPath(String path) {
        return findNewPath(directory, path);
    }

    private String findNewPath(File directory, String path) {

        String [] pathParts = path.split("/");
        String relativePath = "/"+pathParts[pathParts.length-1];

        File [] files = directory.listFiles();

        for(File file : Objects.requireNonNull(files)) {
            if(file.isDirectory()) {
                returnPath = findNewPath(file, relativePath);
            }else {
                String absPath = file.getAbsolutePath();
                if (absPath.endsWith(relativePath)) {
                    returnPath = absPath;
                    break;
                }
            }
        }
        return returnPath;
    }

}
