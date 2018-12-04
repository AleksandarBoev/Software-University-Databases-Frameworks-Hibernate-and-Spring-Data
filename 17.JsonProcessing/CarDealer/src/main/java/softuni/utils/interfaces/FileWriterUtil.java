package softuni.utils.interfaces;

import java.io.IOException;

public interface FileWriterUtil {
    void writeToFile(String fullFilePath, String content) throws IOException;
}
