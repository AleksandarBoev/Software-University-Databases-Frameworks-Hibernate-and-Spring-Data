package softuni.utils.implementations;

import org.springframework.core.io.ClassPathResource;
import softuni.utils.interfaces.FileReaderUtil;

import java.io.*;

public class FileReaderUtilImpl implements FileReaderUtil {
    @Override
    public String readFile(String relativeFilePath) throws IOException {
        File file = new ClassPathResource(relativeFilePath).getFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder result = new StringBuilder();

        String currentLine = "";
        while ((currentLine = reader.readLine()) != null)
            result.append(currentLine).append(System.lineSeparator());

        reader.close();

        return result.toString().substring(0, result.toString().length() - 1);
    }
}
