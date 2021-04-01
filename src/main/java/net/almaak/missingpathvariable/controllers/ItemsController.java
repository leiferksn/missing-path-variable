package net.almaak.missingpathvariable.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {

    @PostMapping(
            value = "/items/{parentItemId}/item",
            produces = { "application/json" })
    public ResponseEntity<String> createNewItemInParent(@PathVariable String parentItemId) {
        try {
            return new ResponseEntity(new ObjectMapper().writeValueAsString(
                    new ExampleResponse(HttpStatus.CREATED.getReasonPhrase(), "An item for the parent with the id: "
                            + parentItemId + " was created successfully!")), HttpStatus.CREATED);
        } catch (final JsonProcessingException jpe) {
            final var sb = new StringBuilder("{")
                    .append("\"code\":\"")
                    .append(HttpStatus.INTERNAL_SERVER_ERROR)
                    .append("\",\"message\":")
                    .append("Error serializing response!\"");
            return new ResponseEntity(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private class ExampleResponse {
        private String code;
        private String message;

        public ExampleResponse(final String code, final String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
