package com.bship.games.endpoints.RequestErrors;

import java.util.List;

public class ObjectValidation {

    private List<ValidationObjectError> objectValidations;

    private ObjectValidation(Builder builder) {
        objectValidations = builder.errors;
    }

    public List<ValidationObjectError> getObjectValidations() {
        return objectValidations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ValidationObjectError> errors;

        public Builder withErrors(List<ValidationObjectError> errors) {
            this.errors = errors;
            return this;
        }

        public ObjectValidation build() {
            return new ObjectValidation(this);
        }
    }

    @Override
    public String toString() {
        return "{\"objectValidation\": " + objectValidations + "}";
    }
}
