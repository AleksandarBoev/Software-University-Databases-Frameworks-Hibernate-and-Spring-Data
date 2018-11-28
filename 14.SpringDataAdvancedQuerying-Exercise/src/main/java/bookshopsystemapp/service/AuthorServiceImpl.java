package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorServiceImpl implements AuthorService {

    //TODO relative path not working for some reason
    private final static String AUTHORS_FILE_PATH = "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Software-University-Databases-Frameworks-Hibernate-and-Spring-Data\\14.SpringDataAdvancedQuerying-Exercise\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authorFileContent = this.fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Author author = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public String getAuthorsWhoseFirstNameEndsWith(String firstNameEnding) {
        StringBuilder result = new StringBuilder();

        this.authorRepository.findAuthorByFirstNameEndsWith(firstNameEnding)
                .forEach(a -> result.append(a.getFirstName()).append(" ").append(a.getLastName()).append(System.lineSeparator()));

        return result.toString().trim();
    }

    @Override
    public String getAuthorNamesAndBookCountsOrderedByCountDesc() {
        StringBuilder result = new StringBuilder();

        this.authorRepository.getAuthorNamesAndBooksCountOrderedByCountDesc()
                .forEach(authorBookCount -> result.append(String.format("%s - %s%n", "" + authorBookCount[0], "" + authorBookCount[1])));

        return result.toString().trim();
    }
}
