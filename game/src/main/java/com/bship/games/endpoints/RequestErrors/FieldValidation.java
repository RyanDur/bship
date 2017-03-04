package com.bship.games.endpoints.RequestErrors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = FieldValidation.Builder.class)
public class FieldValidation {
    private List<ValidationFieldError> validations;

    private FieldValidation(Builder builder) {
        validations = builder.validations;
    }

    public List<ValidationFieldError> getValidations() {
        return validations;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private List<ValidationFieldError> validations;

        public Builder withValidations(List<ValidationFieldError> validations) {
            this.validations = validations;
            return this;
        }

        public FieldValidation build() {
            return new FieldValidation(this);
        }
    }

    @Override
    public String toString() {
        return "{\"fieldValidation\": " + validations + "}";
    }
}
