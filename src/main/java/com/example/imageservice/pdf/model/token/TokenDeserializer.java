package com.example.imageservice.pdf.model.token;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class TokenDeserializer extends JsonDeserializer<Token> {

    @Override
    public Token deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").asText();

        if ("load".equals(type)) {
            return jp.getCodec().treeToValue(node, LoadToken.class);
        }

        throw new IOException("Unknown token type: " + type);
    }
}
