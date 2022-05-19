package com.epam.esm.validations;

import com.epam.esm.exceptions.IncorrectParamException;
import com.epam.esm.exceptions.IncorrectParamExceptionCode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdValidation extends IncorrectParamExceptionCode {

    private final int MIN_ID = 1;

    public static void validateId(long id) throws IncorrectParamException {
        if (id < MIN_ID) {
            throw new IncorrectParamException(BAD_ID);
        }
    }


}
