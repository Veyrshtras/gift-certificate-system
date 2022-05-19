package com.epam.esm.controllers;

import com.epam.esm.dtos.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.ErrorResponseExceptionCodes;
import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.MyDtoException;
import com.epam.esm.services.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Class {@code GiftCertificateController}
 * which allows to perform CRUD and addition operations on gift certificates.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/gift-certificates".
 * So that {@code GiftCertificateController} is accessed by sending request to /gift-certificates.
 *
 * @author Shohboz Juraev
 *
 */
@RestController
@RequestMapping("api/module2/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }


    /**
     * Method for getting all gift certificates from data source.
     *
     * @return List of found gift certificates
     */

    @GetMapping("/list")
    public ResponseEntity listCertificates() {

        return (!service.getAll().isEmpty())?ResponseEntity.ok(service.getAll())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("bazadan ma'lumot topilmadi");
    }

    /**
     * Method for inserting and saving new gift certificate.
     *
     * @param certificate gift certificate for saving
     * @return CREATED HttpStatus
     * @throws MyDtoException                the exception thrown in case of saving error
     * @throws IncorrectParamException an exception thrown in case of invalid gift certificate information
     */
    @PostMapping("/create")
    public ResponseEntity createCertificate(@RequestBody GiftCertificateDto certificate)throws MyDtoException, IncorrectParamException{
        service.insert(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
    }

    /**
     * Method for updating tags from the gift certificate.
     *
     * @param certificate gift certificate entity, which include information to update
     * @param id              ID of gift certificate
     * @return OK HttpStatus
     * @throws MyDtoException                the exception thrown in case of updating error
     * @throws IncorrectParamException an exception thrown in case of invalid gift certificate information
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity updateCertificate(@PathVariable Long id, @RequestBody GiftCertificateDto certificate)
            throws MyDtoException, IncorrectParamException{
        service.update(id, certificate);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
    }

    /**
     * Method for removing gift certificate by ID.
     *
     * @param id ID of gift certificate to remove
     * @return OK HttpStatus
     * @throws MyDtoException                an exception thrown in case gift certificate with such ID not found
     * @throws IncorrectParamException an exception thrown in case of invalid ID
     */
    @DeleteMapping("/delete/id={id}")
    public ResponseEntity deleteCertificate(@PathVariable Long id)throws MyDtoException, IncorrectParamException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }

    /**
     * Method for getting found gift certificates from data source.
     *
     * @return List of gift certificates which contain of tag name
     *
     */
    @GetMapping("/getByTagName/{tagName}")
    public ResponseEntity getByTagName(@PathVariable String tagName) throws MyDtoException, IncorrectParamException {
        return (!service.getByTagName(tagName).isEmpty())?ResponseEntity.ok(service.getByTagName(tagName))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("bazadan ma'lumot topilmadi");
    }

    /**
     * Method for getting all gift certificates from data source which ordered by name.
     *
     * @return List of sorted gift certificates by name
     *
     */
    @GetMapping("/sortByName")
    public ResponseEntity sortByName(){

        return (!service.sortByName().isEmpty())?ResponseEntity.ok(service.sortByName())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("bazadan ma'lumot topilmadi");
    }

    /**
     * Method for getting all gift certificates from data source which ordered by date.
     *
     * @return List of sorted gift certificates by date
     *
     */
    @GetMapping("/sortByDate")
    public ResponseEntity sortByDate(){

        return (!service.sortByDate().isEmpty())?ResponseEntity.ok(service.sortByDate())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("bazadan ma'lumot topilmadi");
    }

    /**
     * Method for getting found gift certificates from data source.
     *
     * @return List of gift certificates which contain of name and description
     * @throws MyDtoException                an exception thrown in case gift certificate with such ID not found
     * @throws IncorrectParamException an exception thrown in case of invalid params
     */
    @GetMapping("/searchByNameOrDescription/{name}/{desc}")
    public ResponseEntity searchByNameOrDescription(@PathVariable String name, @PathVariable String desc)
            throws MyDtoException, IncorrectParamException {
        return (!service.searchByNameOrDescription(name, desc).isEmpty())?
                ResponseEntity.ok(service.searchByNameOrDescription(name,desc))
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("bazadan ma'lumot topilmadi");
    }

}
