package softuni.utils.implementations;

import softuni.utils.interfaces.FileWriterUtil;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterUtilImpl implements FileWriterUtil {
    @Override
    public void writeToFile(String fullFilePath, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(fullFilePath);
        fileWriter.write(content);
        fileWriter.close();
    }
}
