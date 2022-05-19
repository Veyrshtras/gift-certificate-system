package com.epam.esm.services;

import com.epam.esm.dtos.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.MyDtoException;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificate> getAll();

    void insert(GiftCertificateDto entity) throws MyDtoException, IncorrectParamException;

    void delete(long id) throws MyDtoException, IncorrectParamException;

    void update(long id, GiftCertificateDto entity) throws MyDtoException, IncorrectParamException;

    List<GiftCertificate> getByTagName(String tagName)throws MyDtoException, IncorrectParamException;

    List<GiftCertificate> searchByNameOrDescription(String certificateName, String certificateDescription) throws MyDtoException, IncorrectParamException;

    List<GiftCertificate> sortByDate();

    List<GiftCertificate> sortByName();
}
