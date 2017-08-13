package com.bship.games.endpoints.errors.RequestErrors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = ObjectValidation.Builder.class)
public class ObjectValidation {

    private List<ValidationObjectError> validations;

    private ObjectValidation(Builder builder) {
        validations = builder.validations;
    }

    public List<ValidationObjectError> getValidations() {
        return validations;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private List<ValidationObjectError> validations;

        public Builder withValidations(List<ValidationObjectError> validations) {
            this.validations = validations;
            return this;
        }

        public ObjectValidation build() {
            return new ObjectValidation(this);
        }
    }

    @Override
    public String toString() {
        return "{\"objectValidation\": " + validations + "}";
    }
}
