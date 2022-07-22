package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NasaContentExtractor implements ContentExtractor {

    public List<Content> extract(String json) {
        json = "[" + json + "]";
        List<Map<String, String>> attributesList =  new JsonParser().parse(json);
        List<Content> contentList = new ArrayList<>();

        attributesList.forEach(attributes -> {
            String imageUrl = attributes.get("url");
            String title = attributes.get("title");
            contentList.add(new Content(title, imageUrl));
        });
        return contentList;
    }
}
