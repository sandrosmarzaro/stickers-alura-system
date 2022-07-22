package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ReadJsonException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImdbContentExtractor implements ContentExtractor {

    public List<Content> extract(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new ReadJsonException("Error reading JSON");
        }
        json = node.get("items").toString();

        List<Map<String, String>> attributesList =  new JsonParser().parse(json);
        List<Content> contentList = new ArrayList<>();

        attributesList.forEach(attributes -> {
            String imageUrl = attributes.get("image")
                .replaceAll("(@+)(.*).jpg$", "$1.jpg");
            String title = attributes.get("title");
            contentList.add(new Content(title, imageUrl));
        });
        return contentList;
    }
}
