package alararestaurant.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilImpl implements FileUtil {
    @Override
    public String readFile(String filePath) throws IOException {
        StringBuilder fileContent = new StringBuilder();

        BufferedReader fileReader = new BufferedReader(
                new FileReader(
                        new ClassPathResource(filePath).getFile()));

        String line = "";
        while ((line = fileReader.readLine()) != null)
            fileContent.append(line).append(System.lineSeparator());

        return fileContent.toString().trim();
    }
}
