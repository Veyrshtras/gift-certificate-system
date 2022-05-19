package com.epam.esm.validations;

import com.epam.esm.dtos.TagDto;
import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.IncorrectParamExceptionCode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagValidation extends IncorrectParamExceptionCode {

    private final int MIN_LENGTH_NAME=3;
    private final int MAX_LENGTH_NAME=25;


    public void validate(TagDto tag) throws IncorrectParamException {
        validateName(tag.getName());
    }

    public void validateName(String name) throws IncorrectParamException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParamException(BAD_NAME);
        }
    }
}
