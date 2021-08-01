package file_util;

import exceptions.ForbiddenFileRequestException;

import java.io.File;
import java.io.FileNotFoundException;

public class FileFolder {

    private final File sharedDirectory;
    private final FileValidator fileValidator;

    private boolean foundElsewhere;

    public FileFolder(File sharedDirectory) {
        this.sharedDirectory = sharedDirectory;
        fileValidator = new FileValidator(this.sharedDirectory);
        foundElsewhere = false;
    }

    public synchronized File getFile(String path) throws FileNotFoundException, ForbiddenFileRequestException{

        if(path.length() == 0) {
            String newPath = "/index.html";
            File file = new File(sharedDirectory, newPath);
            if(file.exists())
                return file;
        }
        if(path.endsWith("/")) {
            String newPath = path + "index.html";
            File file = new File(sharedDirectory, newPath);
            if(file.exists())
                return file;
        }

        if(path.contains("admin"))
            throw new ForbiddenFileRequestException("Forbidden 403 http.request");

        File file = new File(sharedDirectory, path);
        if(file.exists())
            return file;

        if(fileValidator.fileExists(path)) {
            String newPath = fileValidator.findNewPath(path);
            if(newPath.contains("admin"))
                throw new ForbiddenFileRequestException("Forbidden 403 http.request");

            file = new File(newPath);
            foundElsewhere = true;

            return file;
        }

        throw new FileNotFoundException();

    }

    public boolean isFoundElsewhere() {
        return foundElsewhere;
    }

}
