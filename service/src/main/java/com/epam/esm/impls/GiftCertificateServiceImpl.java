package com.epam.esm.impls;

import com.epam.esm.dtos.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.repositories.GiftCertificateRepository;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.validations.GiftCertificateValidation;
import com.epam.esm.validations.IdValidation;
import com.epam.esm.validations.TagValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GiftCertificate> getAll(){

        return repository.list();
    }

    @Override
    public void insert(GiftCertificateDto entity)throws MyDtoException, IncorrectParamException {
        GiftCertificateValidation.validate(entity);
        repository.insert(entity);
    }

    @Override
    public void delete(long id) throws MyDtoException, IncorrectParamException{
        IdValidation.validateId(id);
        repository.delete(id);
    }

    @Override
    public void update(long id, GiftCertificateDto entity)throws MyDtoException, IncorrectParamException {
        GiftCertificateValidation.validateForUpdate(entity);
        IdValidation.validateId(id);
        repository.update(id, entity);
    }

    @Override
    public List<GiftCertificate> getByTagName(String tagName) throws MyDtoException, IncorrectParamException {
        TagValidation.validateName(tagName);
        return repository.getByTagName(tagName);
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(String certificateName, String certificateDescription)
            throws MyDtoException, IncorrectParamException {
        GiftCertificateValidation.validateName(certificateName);
        GiftCertificateValidation.validateDescription(certificateDescription);
        return repository.searchByNameOrDescription(certificateName, certificateDescription);
    }

    @Override
    public List<GiftCertificate> sortByDate() {
        return repository.sortByDate();
    }

    @Override
    public List<GiftCertificate> sortByName(){
        return repository.sortByName();
    }
}
