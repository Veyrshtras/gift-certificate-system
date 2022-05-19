package com.epam.esm.services;

import com.epam.esm.dtos.TagDto;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.MyDtoException;

import java.util.List;

public interface TagService {

    List<Tag> getAll();

    void insert(TagDto entity) throws MyDtoException, IncorrectParamException;

    void delete(long id) throws MyDtoException, IncorrectParamException;
}
