package softuni.utils.implementations;

import org.springframework.core.io.ClassPathResource;
import softuni.utils.interfaces.FileReadUtil;

import java.io.*;

public class FileReadUtilImpl implements FileReadUtil {

    @Override
    public String readFileFromFullFilePath(String filePath) {
        try {
            File file = new File(filePath);
            return this.getContents(file);
        } catch (IOException ioe) {
            return null;
        }
    }

    @Override
    public String readFileFromRelativeFilePath(String filePath) {
        try {
            File file = new ClassPathResource(filePath).getFile();
            return this.getContents(file);
        } catch (IOException ioe) {
            return null;
        }
    }

    private String getContents(File file) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        StringBuilder result = new StringBuilder();
        String currentLine = "";
        while ((currentLine = fileReader.readLine()) != null)
            result.append(currentLine).append(System.lineSeparator());

        return result.toString().trim();
    }
}
