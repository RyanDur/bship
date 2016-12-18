package com.bship.games.endpoints.RequestErrors;

import java.util.List;

public class FieldValidation {
    private List<String> errors;

    private FieldValidation(Builder builder) {
        errors = builder.errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> errors;

        public Builder withErrors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public FieldValidation build() {
            return new FieldValidation(this);
        }
    }

    @Override
    public String toString() {
        return "{\"fieldValidation\": " + errors + "}";
    }
}
