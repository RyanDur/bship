package com.bship.games.endpoints.RequestErrors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = GameErrors.Builder.class)
public class GameErrors {
    private List errors;

    private GameErrors(Builder builder) {
        errors = builder.errors;
    }

    public List getErrors() {
        return errors;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private List errors;

        public Builder withErrors(List errors) {
            this.errors = errors;
            return this;
        }

        public GameErrors build() {
            return new GameErrors(this);
        }
    }

    @Override
    public String toString() {
        return "{\"errors\": " + errors + "}";
    }
}
