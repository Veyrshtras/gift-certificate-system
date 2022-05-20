package com.epam.esm.repository;

import com.epam.esm.configs.DBConfigurationTest;
import com.epam.esm.dtos.GiftCertificateDto;
import com.epam.esm.dtos.TagDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.repositories.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ContextConfiguration(classes = DBConfigurationTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class GiftCertificateRepositoryTest {


    @Autowired
    private GiftCertificateRepository repository;

    private List<GiftCertificateDto> getAll(){
        List<GiftCertificateDto> list=new ArrayList<>();
        GiftCertificateDto certificate=new GiftCertificateDto("giftCertificate1","description1",BigDecimal.valueOf(10.10),1,
                "2022-05-10T20:54:58.369Z",Collections.singletonList(new TagDto("tagName3")));
        list.add(certificate);

        certificate=new GiftCertificateDto("giftCertificate3","description3",BigDecimal.valueOf(30.30),3,
                "2022-05-10T20:54:58.369Z",Collections.singletonList(new TagDto(null)));
        list.add(certificate);


        certificate=new GiftCertificateDto("giftCertificate2","description2",BigDecimal.valueOf(20.20),2,
                "2022-05-10T20:54:58.369Z",Collections.singletonList(new TagDto("tagName3")));
        list.add(certificate);

        certificate=new GiftCertificateDto("giftCertificate4","description4",BigDecimal.valueOf(26.20),4,
                "2022-05-10T20:54:58.369Z",Collections.singletonList(new TagDto(null)));;

        list.add(certificate);

        return list;
    }
    @Test
    public void listTest()throws MyDtoException, InterruptedException{
        List<GiftCertificateDto> actual=repository.list().stream()
                .map(certificate -> (new GiftCertificateDto().apply(certificate)))
                .collect(Collectors.toList());

        List<GiftCertificateDto> expected=getAll();

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void sortByDateTest(){
        List<GiftCertificateDto> actual=repository.sortByDate().stream()
                .map(certificate -> (new GiftCertificateDto().apply(certificate)))
                .collect(Collectors.toList());

        List<GiftCertificateDto> expected=getAll().stream()
                .sorted(Comparator.comparing(GiftCertificateDto::getLastUpdateDate)).collect(Collectors.toList());

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void sortByNameTest(){
        List<GiftCertificateDto> actual=repository.sortByName().stream()
                .map(certificate -> (new GiftCertificateDto().apply(certificate)))
                .collect(Collectors.toList());

        List<GiftCertificateDto> expected=getAll().stream()
                .sorted(Comparator.comparing(GiftCertificateDto::getName)).collect(Collectors.toList());

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void getByTagNameTest() throws MyDtoException {
        List<GiftCertificateDto> actual=repository.getByTagName("tagName3").stream()
                .map(certificate -> (new GiftCertificateDto().apply(certificate)))
                .collect(Collectors.toList());

        List<GiftCertificateDto> expected=getAll().stream()
                .filter(certificateDto -> certificateDto.getTags().contains(new TagDto("tagName3")))
                .collect(Collectors.toList());

        Assertions.assertEquals(actual, expected);
    }


    @Test
    public void searchByNameOrDescriptionTest() throws MyDtoException {
        List<GiftCertificateDto> actual=repository.searchByNameOrDescription("giftCert","description3").stream()
                .map(certificate -> (new GiftCertificateDto().apply(certificate)))
                .collect(Collectors.toList());

        List<GiftCertificateDto> expected=getAll().stream()
                .filter(certificateDto -> certificateDto.getName().contains("giftCert")
                        || certificateDto.getDescription().contains("description3"))
                .collect(Collectors.toList());

        Assertions.assertEquals(actual, expected);
    }

}
