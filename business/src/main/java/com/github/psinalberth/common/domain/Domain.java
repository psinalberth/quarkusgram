package com.github.psinalberth.common.domain;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

public interface Domain {

    default void validate() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var violations = validator.validate(this);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
    }
}
