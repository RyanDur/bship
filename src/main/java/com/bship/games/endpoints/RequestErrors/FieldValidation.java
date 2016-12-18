package com.bship.games.endpoints.RequestErrors;

import java.util.List;

public class FieldValidation {
    private List<ValidationFieldError> fieldValidations;

    private FieldValidation(Builder builder) {
        fieldValidations = builder.errors;
    }

    public List<ValidationFieldError> getFieldValidations() {
        return fieldValidations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ValidationFieldError> errors;

        public Builder withErrors(List<ValidationFieldError> errors) {
            this.errors = errors;
            return this;
        }

        public FieldValidation build() {
            return new FieldValidation(this);
        }
    }

    @Override
    public String toString() {
        return "{\"fieldValidation\": " + fieldValidations + "}";
    }
}
