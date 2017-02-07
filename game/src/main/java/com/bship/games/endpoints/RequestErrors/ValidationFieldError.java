package com.bship.games.endpoints.RequestErrors;

import org.springframework.validation.FieldError;

public class ValidationFieldError {
    private String code;
    private String field;
    private Object value;
    private String message;

    private ValidationFieldError(Builder builder) {
        code = builder.code;
        field = builder.field;
        value = builder.value;
        message = builder.message;
    }

    public String getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;
        private String field;
        private Object value;
        private String message;

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withField(String field) {
            this.field = field;
            return this;
        }

        public Builder withValue(Object value) {
            this.value = value;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withError(FieldError err) {
            return withCode(err.getCode()).withField(err.getField())
                    .withValue(err.getRejectedValue()).withMessage(err.getDefaultMessage());
        }

        public ValidationFieldError build() {
            return new ValidationFieldError(this);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\": \"" + code + "\", " +
                "\"field\": \"" + field + "\", " +
                "\"value\": \"" + value + "\", " +
                "\"message\": \"" + message + "\"" +
                "}";
    }
}
