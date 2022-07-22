package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ReadJsonException;

import java.util.*;

public class JsonParser {

    public List<Map<String, String>> parse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new ReadJsonException("Error reading JSON");
        }

        List<Map<String, String>> list = new ArrayList<>();
        node.elements().forEachRemaining(item -> {
            Map<String, String> content = new HashMap<>();
            item.fields().forEachRemaining(attribute ->
                content.put(attribute.getKey(), attribute.getValue().asText())
            );
            list.add(content);
        });
        return list;
    }
}
