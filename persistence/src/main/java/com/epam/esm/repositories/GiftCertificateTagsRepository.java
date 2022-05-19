package com.epam.esm.repositories;

import com.epam.esm.dtos.TagDto;
import com.epam.esm.entities.GiftCertificateTags;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.exceptions.MyDtoExceptionMassageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateTagsRepository extends MyDtoExceptionMassageCode {

    private final JdbcTemplate jdbcTemplate;
    private final TagRepository repository;

    @Autowired
    public GiftCertificateTagsRepository(JdbcTemplate jdbcTemplate, TagRepository repository) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
    }

    public void insert(Long certificate_id, List<TagDto> tags)throws MyDtoException {

        try {


            List<Long> ids=new ArrayList<>();
            for (TagDto tag:
                 tags) {
                if(isContain(tag)) {
                    repository.insert(tag);
                }
            }
            for (TagDto tag:
                    tags) {
                String queryTag="select id from tag where name=\'"+tag.getName()+"\'";
                ids.add(jdbcTemplate.queryForObject(queryTag,Long.class));
            }
            for (Long item:
                    ids) {
                String query="insert into certificate_tag (certificate_id, tag_id) " +
                        "values ("+certificate_id+", "+item+")";
                jdbcTemplate.execute(query);

            }
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY_ID);
        }

    }

    private boolean isContain(TagDto tag){
        List<Tag> inDb=repository.list();
        for (Tag item:
             inDb) {
            if(item.getName().equals(tag.getName()))
                return false;
        }
        return true;
    }

    public List<Long> getTagIds(Long certId)throws MyDtoException{

       try {
           String query="select * from certificate_tag where certificate_id="+certId;
           return getRowMap(query).stream()
                   .map(giftCertificateTags -> giftCertificateTags.getTag_id())
                   .collect(Collectors.toList());
       }
       catch (DataAccessException e){
           throw new MyDtoException(NO_ENTITY_ID);
       }
    }

    private List<GiftCertificateTags> getRowMap(String query) throws DataAccessException{

        return jdbcTemplate.query(query, new RowMapper<GiftCertificateTags>() {
            @Override
            public GiftCertificateTags mapRow(ResultSet rs, int rowNum) throws SQLException {
                GiftCertificateTags obj=new GiftCertificateTags();
                obj.setCertificate_id(rs.getLong(1));
                obj.setTag_id(rs.getLong(2));
                return obj;
            }
        });
    }
}
