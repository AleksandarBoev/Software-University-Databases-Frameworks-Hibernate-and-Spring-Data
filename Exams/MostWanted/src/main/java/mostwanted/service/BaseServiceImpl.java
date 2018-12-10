package mostwanted.service;

import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseServiceImpl<E, R extends JpaRepository<E, Integer>> {
    protected static final String IMPORT_FILE_RELATIVE_PATH = "files/";

    private R mainRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private FileUtil fileUtil;

    protected BaseServiceImpl(R mainRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil) {
        this.mainRepository = mainRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }

    protected R getMainRepository() {
        return this.mainRepository;
    }

    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    protected ValidationUtil getValidationUtil() {
        return this.validationUtil;
    }

    protected FileUtil getFileUtil() {
        return this.fileUtil;
    }
}
