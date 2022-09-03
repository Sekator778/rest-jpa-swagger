package com.example.bookjpa.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class BookValidationErrorBuilder {
    public static BookValidationError fromBindingErrors(Errors errors) {
        BookValidationError error = new BookValidationError(" Validation failed. "
                + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()
        ) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
