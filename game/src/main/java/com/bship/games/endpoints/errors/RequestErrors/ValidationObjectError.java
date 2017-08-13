package com.bship.games.endpoints.errors.RequestErrors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.validation.ObjectError;

@JsonDeserialize(builder = ValidationObjectError.Builder.class)
public class ValidationObjectError {

    private String code;
    private String type;
    private String message;

    private ValidationObjectError(Builder builder) {
        code = builder.code;
        type = builder.type;
        message = builder.message;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String code;
        private String type;
        private String message;

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withError(ObjectError err) {
            return withCode(err.getCode()).withType(err.getObjectName()).withMessage(err.getDefaultMessage());
        }

        public ValidationObjectError build() {
            return new ValidationObjectError(this);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\": \"" + code + "\", " +
                "\"type\": \"" + type + "\", " +
                "\"message\": \"" + message + "\"" +
                '}';
    }
}
