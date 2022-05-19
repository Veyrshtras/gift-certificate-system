package com.epam.esm.repositories;

import com.epam.esm.dtos.TagDto;
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

@Repository
public class TagRepository extends MyDtoExceptionMassageCode {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(TagDto certificate) throws MyDtoException {

        try {
            String query="insert into tag (name) " +
                    "values (\'"+certificate.getName()+"\')";
            jdbcTemplate.execute(query);
        }
        catch (DataAccessException e){
            throw new MyDtoException(SAVING_ERROR);
        }
    }

    public void delete(Long id)throws MyDtoException{

        try {
            String query="DELETE FROM tag WHERE id="+id;
            jdbcTemplate.execute(query);
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY_ID);
        }
    }

    public List<Tag> list(){
            String query="select * from tag";
            return getRowMap(query);
    }

    public List<TagDto> getTagsById(List<Long> list)throws MyDtoException{

        try {
            List<TagDto> res=new ArrayList<>();
            for (Long id:
                 list) {
                res.add(new TagDto().apply(getById(id)));
            }
            return res;
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY);
        }
    }

    private Tag getById(Long id)throws MyDtoException{

        try {
            String query="select * from tag where id="+id;
            return (getRowMap(query).isEmpty())? null:getRowMap(query).get(0);
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY_ID);
        }
    }

    private List<Tag> getRowMap(String query) throws DataAccessException{

        return jdbcTemplate.query(query, new RowMapper<Tag>() {
            @Override
            public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tag obj=new Tag();
                obj.setId(rs.getLong(1));
                obj.setName(rs.getString(2));
                return obj;
            }
        });
    }
}
