package softuni.utils.interfaces;

public interface FileReadUtil {
    /**
     * Example: "C:\AleksandarUser\Programming\GitHubRepositories\Software-University-Databases-Frameworks-Hibernate-and-Spring-Data\17.
     * JsonProcessing\ProductsShop\src\main\resources\json_seed_files\cars.json"
     * @return String representation of the contents of the file or null if file is not found.
     * If file is empty, an empty string will be returned.
     */
    String readFileFromFullFilePath(String filePath);

    /**
     * Example: "json_seed_files/cars.json"
     * Note: File MUST be in the "resources" folder.
     * @return String representation of the contents of the file or null if file is not found.
     * If file is empty, an empty string will be returned.
     */
    String readFileFromRelativeFilePath(String filePath);
}
