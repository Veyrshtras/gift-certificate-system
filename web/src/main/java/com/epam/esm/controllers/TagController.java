package com.epam.esm.controllers;

import com.epam.esm.dtos.TagDto;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class {@code TagController} which allows to perform CRD operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tags".
 * So that {@code TagController} is accessed by sending request to /tags.
 *
 * @author Shohboz Juraev
 *
 */
@RestController
@RequestMapping("api/module2/tags")
public class TagController {

    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    /**
     * Method for getting list of tags from data source.
     *
     * @return List of found tags
     *
     */
    @GetMapping("/list")
    public ResponseEntity list(){

        return (!service.getAll().isEmpty())?ResponseEntity.ok(service.getAll())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("bazadan ma'lumot topilmadi");
    }

    /**
     * Method for creating new tag.
     *
     * @param tag tag for inserting and saving
     * @return CREATED HttpStatus
     * @throws MyDtoException                the exception thrown in case of saving error
     * @throws IncorrectParamException an exception thrown in case of invalid tag name
     */
    @PostMapping(value = "/create")
    public ResponseEntity createTag(@RequestBody TagDto tag)throws MyDtoException, IncorrectParamException {
        service.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
    }

    /**
     * Method for removing tag by ID.
     *
     * @param id ID of tag to remove
     * @return OK HttpStatus
     * @throws MyDtoException                an exception thrown in case tag with such ID not found
     * @throws IncorrectParamException an exception thrown in case of invalid ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id)throws MyDtoException, IncorrectParamException{
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }

}
