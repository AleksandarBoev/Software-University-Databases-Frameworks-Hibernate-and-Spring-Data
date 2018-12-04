package softuni.utils.interfaces;

import java.io.IOException;

public interface FileWriteUtil {
    void writeToFile(String fullFilePath, String content) throws IOException;
}
