import enums.UrlExtractorEnum;
import models.*;
import utils.ClearConsole;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        UrlExtractorEnum urlExtractor = UrlExtractorEnum.NASA_APOD;
        String json = new ClientHttp().findData(urlExtractor.getUrl());
        ContentExtractor contentExtractor = urlExtractor.getContentExtractor();
        List<Content> contentList = contentExtractor.extract(json);
        
        ClearConsole.clear();
        for (Content content : contentList) {
            System.out.println("Processing title: " + content.title() + "\n");
            InputStream inputStream;
            try {
                inputStream = new URL(content.url()).openStream();
            }
            catch (Exception e) {
                System.out.println("Error reading image");
                continue;
            }
            StickerGenerator.generate(inputStream, content.title() + ".png");
        }
    }
}
