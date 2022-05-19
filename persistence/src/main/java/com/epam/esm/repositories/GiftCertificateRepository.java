package com.epam.esm.repositories;

import com.epam.esm.DateFormatISO8601.CurrentDate;
import com.epam.esm.dtos.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.exceptions.MyDtoExceptionMassageCode;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateRepository extends MyDtoExceptionMassageCode {

    private final JdbcTemplate jdbcTemplate;
    private final CurrentDate currentDate;
    private final TagRepository tagRepository;
    private final GiftCertificateTagsRepository giftCertificateTagsRepository;

    @Autowired
    public GiftCertificateRepository(JdbcTemplate jdbcTemplate, CurrentDate currentDate, TagRepository tagRepository, GiftCertificateTagsRepository giftCertificateTagsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.currentDate = currentDate;
        this.tagRepository = tagRepository;
        this.giftCertificateTagsRepository = giftCertificateTagsRepository;
    }

    public void insert(GiftCertificateDto certificate) throws MyDtoException {

       try{
           String query="insert into gift_certificate (name,description, price, duration, create_date, last_update_date) " +
                   "values (\'"+certificate.getName()+"\'"+
                   ",\'"+certificate.getDescription()+"\'"+
                   ","+certificate.getPrice() +
                   ", "+certificate.getDuration()+
                   ",\'"+currentDate.getCurrentDate() +"\'"+
                   ",\'"+currentDate.getCurrentDate()+"\') returning id;";

           long id=jdbcTemplate.queryForObject(query, Long.class);
           if (!certificate.getTags().isEmpty()) {
               giftCertificateTagsRepository.insert(id, certificate.getTags());
           }

       }
       catch (DataAccessException e) {
           throw new MyDtoException(SAVING_ERROR);
       }
    }

    public void update(Long id, GiftCertificateDto giftCertificateDto)throws MyDtoException{

        try{
            String query="UPDATE gift_certificate SET ";
            String check=query;
            GiftCertificateDto item=getById(id);
            if (!item.getName().equals(giftCertificateDto.getName())){
                query=query+"name = \'"+giftCertificateDto.getName()+"\'";
            }
            if (!item.getDescription().equals(giftCertificateDto.getDescription())){
                query=query+", description = \'"+giftCertificateDto.getDescription()+"\'";
            }
            if (item.getPrice()!=giftCertificateDto.getPrice()){
                query=query+", price = "+giftCertificateDto.getPrice();
            }
            if (item.getDuration()!=giftCertificateDto.getDuration()){
                query=query+", duration = "+giftCertificateDto.getDuration();
            }

            if(check.equals(query))
                return ;
            else {
                query = query + ", last_update_date=\'" + currentDate.getCurrentDate() + "\' WHERE id=" + id;
                jdbcTemplate.execute(query);
            }
        }
        catch (DataAccessException e) {
            throw new MyDtoException(NO_ENTITY_ID);
        }
    }

    public void delete(Long id)throws MyDtoException{

        try{

            String query="DELETE FROM gift_certificate WHERE id="+id;
            jdbcTemplate.execute(query);
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY_ID);
        }
    }

    public List<GiftCertificate> list(){
        String query="select * from gift_certificate";
        return getRowMap(query);
    }

    private GiftCertificateDto getById(Long id)throws MyDtoException{

        try{
            String query="select * from gift_certificate where id="+id;
            return (new GiftCertificateDto()).apply(getRowMap(query).get(0));
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY_ID);
        }
    }

    public List<GiftCertificate> sortByDate(){

            return list().stream()
                    .sorted(Comparator.comparing(GiftCertificate::getLastUpdateDate).reversed())
                    .collect(Collectors.toList());


    }

    public List<GiftCertificate> sortByName(){

            return list().stream()
                    .sorted(Comparator.comparing(GiftCertificate::getName))
                    .collect(Collectors.toList());

    }

    public List<GiftCertificate> getByTagName(String tagName)throws MyDtoException{

        try{
            String query="select * " +
                    "from gift_certificate " +
                    "where id in (select  certificate_Id " +
                    "from certificate_tag " +
                    "where tag_id=(select id " +
                    "from tag " +
                    "where name=\'"+tagName+"\'))";

            return getRowMap(query);
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY_NAME);
        }

    }
    public List<GiftCertificate> searchByNameOrDescription(String name, String description)throws MyDtoException{

        try{
            String query="select * " +
                    "from gift_certificate " +
                    "where name like \'%"+name+"%\' or description like \'%"+description+"%\'";

            return getRowMap(query);
        }
        catch (DataAccessException e){
            throw new MyDtoException(NO_ENTITY_PARAMETERS);
        }

    }

    private List<GiftCertificate> getRowMap(String query)throws DataAccessException {

        return jdbcTemplate.query(query, new RowMapper<GiftCertificate>() {
            @SneakyThrows
            @Override
            public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
                GiftCertificate obj=new GiftCertificate();
                obj.setId(rs.getLong(1));
                obj.setName(rs.getString(2));
                obj.setDescription(rs.getString(3));
                obj.setPrice(rs.getBigDecimal(4));
                obj.setDuration(rs.getInt(5));
                obj.setCreateDate(rs.getString(6));
                obj.setLastUpdateDate(rs.getString(7));
                obj.setTags(tagRepository.getTagsById(giftCertificateTagsRepository.getTagIds(rs.getLong(1))));
                return obj;
            }
        });
    }



}
