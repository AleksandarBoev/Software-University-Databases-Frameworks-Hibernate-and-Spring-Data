package mostwanted.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilImpl implements FileUtil {
    @Override
    public String readFile(String filePath) throws IOException {
        File file = new ClassPathResource(filePath).getFile();

        StringBuilder fileContent = new StringBuilder();
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        String input;
        while ((input = fileReader.readLine()) != null)
            fileContent.append(input).append(System.lineSeparator());


        return fileContent.toString().trim();
    }
}
