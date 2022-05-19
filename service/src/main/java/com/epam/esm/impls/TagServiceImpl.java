package com.epam.esm.impls;

import com.epam.esm.dtos.TagDto;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.repositories.TagRepository;
import com.epam.esm.services.TagService;
import com.epam.esm.validations.IdValidation;
import com.epam.esm.validations.TagValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tag> getAll() {
        return repository.list();
    }

    @Override
    public void insert(TagDto entity) throws MyDtoException, IncorrectParamException {
        TagValidation.validate(entity);
        repository.insert(entity);
    }

    @Override
    public void delete(long id) throws MyDtoException, IncorrectParamException {

        IdValidation.validateId(id);
        repository.delete(id);
    }
}
