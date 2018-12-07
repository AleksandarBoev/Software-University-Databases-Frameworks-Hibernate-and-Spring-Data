package softuni.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import softuni.domain.entities.BaseEntity;
import softuni.services.interfaces.BaseService;
import softuni.utils.interfaces.FileReaderUtil;
import softuni.utils.interfaces.FileWriterUtil;
import softuni.utils.interfaces.ValidatorUtil;

public abstract class BaseServiceImpl<E extends BaseEntity, R extends JpaRepository<E, Long>, C> implements BaseService<E, R> {
    private R repository;
    private ModelMapper modelMapper;
    private FileReaderUtil fileReaderUtil;
    private FileWriterUtil fileWriterUtil;
    private ValidatorUtil<C> validatorUtil;

    protected BaseServiceImpl(R repository, ModelMapper modelMapper, FileReaderUtil fileReaderUtil, FileWriterUtil fileWriterUtil, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.fileReaderUtil = fileReaderUtil;
        this.fileWriterUtil = fileWriterUtil;
        this.validatorUtil = validatorUtil;
    }

    protected R getRepository() {
        return this.repository;
    }

    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    protected FileReaderUtil getFileReaderUtil() {
        return this.fileReaderUtil;
    }

    protected FileWriterUtil getFileWriterUtil() {
        return this.fileWriterUtil;
    }

    protected ValidatorUtil<C> getValidatorUtil() {
        return this.validatorUtil;
    }

    @Override
    public E getById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Iterable<E> getAll() {
        return this.repository.findAll();
    }

    @Override
    public boolean register(E element) {
        if (this.repository.findById(element.getId()).orElse(null) != null)
            return false;

        this.repository.save(element);
        return true;
    }

    @Override
    public boolean update(E element) {
        if (this.repository.findById(element.getId()).orElse(null) == null)
            return false;

        this.repository.save(element);
        return true;
    }

    @Override
    public void saveAll(Iterable<E> elements) {
        this.repository.saveAll(elements);
    }
}
