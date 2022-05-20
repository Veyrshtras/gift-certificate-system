package com.epam.esm.service;

import com.epam.esm.configs.DBConfigurationTest;
import com.epam.esm.dtos.TagDto;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.services.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ContextConfiguration(classes = DBConfigurationTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TagServiceTest {


    @Autowired
    private TagService service;

    private List<TagDto> list(){
        List<TagDto> all=new ArrayList<>();
        TagDto tag=new TagDto();
        tag.setName("tagName1");
        all.add(tag);
        TagDto tag2=new TagDto();
        tag2.setName("tagName3");
        all.add(tag2);
        TagDto tag3=new TagDto();
        tag3.setName("tagName5");
        all.add(tag3);
        TagDto tag4=new TagDto();
        tag4.setName("tagName4");
        all.add(tag4);
        TagDto tag5=new TagDto();
        tag5.setName("tagName7");
        all.add(tag5);
        TagDto tag6=new TagDto();
        tag6.setName("tagName6");
        all.add(tag6);

        return all;
    }

    @Test
    public void getAllTest() throws MyDtoException {
        List<TagDto> actual=service.getAll().stream()
                .map(tag -> new TagDto().apply(tag))
                .collect(Collectors.toList());

        // TODO: 20.05.2022 create, update, delete tests 
        
        Assertions.assertEquals(actual,list());
    }
}
