package com.bship.games.endpoints.RequestErrors;

import java.util.List;

public class ObjectValidation {

    private List<String> errors;

    private ObjectValidation(Builder builder) {
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

        public ObjectValidation build() {
            return new ObjectValidation(this);
        }
    }

    @Override
    public String toString() {
        return "{\"objectValidation\": " + errors + "}";
    }
}
