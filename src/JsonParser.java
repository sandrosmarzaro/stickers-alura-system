import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class JsonParser {

    public List<Map<String, String>> parse(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);

        List<Map<String, String>> list = new ArrayList<>();
        node.get("items").elements().forEachRemaining(item -> {
            Map<String, String> movie = new HashMap<>();
            item.fields().forEachRemaining(attribute ->
                movie.put(attribute.getKey(), attribute.getValue().asText())
            );
            list.add(movie);
        });
        return list;
    }
}
