package net.almaak.missingpathvariable.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "/itemx",
            consumes = { "application/json" },
            produces = { "application/json" })
    public ResponseEntity<String> createNewItemx(@RequestBody ExampleRequest exampleRequest) {
        try {
            return new ResponseEntity(new ObjectMapper().writeValueAsString(
                    new ExampleResponse(HttpStatus.CREATED.getReasonPhrase(), "An item for the parent with the name: "
                            + exampleRequest.getItemName() + " was created successfully!")), HttpStatus.CREATED);
        } catch (final JsonProcessingException jpe) {
            final var sb = new StringBuilder("{")
                    .append("\"code\":\"")
                    .append(HttpStatus.INTERNAL_SERVER_ERROR)
                    .append("\",\"message\":")
                    .append("Error serializing response!\"");
            return new ResponseEntity(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/itemx/{itemxId}",
            produces = { "application/json" })
    public ResponseEntity<ExampleItem> getItemXById(@PathVariable String itemxId) {
        final var exampleItem = new ExampleItem("just a test example item", itemxId);
        return new ResponseEntity(exampleItem, HttpStatus.OK);
    }

    private static class ExampleItem implements Serializable {
        private static final long serialVersionUID = 1L;

        private String itemName;
        private String itemId;

        public ExampleItem() {
        }

        public ExampleItem(final String aItemName, final String aItemId) {
            this.itemName = aItemName;
            this.itemId = aItemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(final String aItemName) {
            this.itemName = aItemName;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
    }

    private static class ExampleRequest implements Serializable {
        private static final long serialVersionUID = 1L;

        private String itemName;

        public ExampleRequest() {

        }

        public ExampleRequest(final String aItemName) {
            this.itemName = aItemName;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(final String aItemName) {
            this.itemName = aItemName;
        }

    }

    private static class ExampleResponse {
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
