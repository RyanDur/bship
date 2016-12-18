package com.bship.games.endpoints.RequestErrors;

import java.util.List;

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
