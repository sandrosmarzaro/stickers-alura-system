package enums;

import config.PropertiesApi;
import models.ContentExtractor;
import models.ImdbContentExtractor;
import models.LanguagesContentExtrator;
import models.NasaContentExtractor;

public enum UrlExtractorEnum {

//        If you want to use environment variables
//        final String apiKey = System.getenv("IMDB_API_KEY");

    NASA_APOD("https://api.nasa.gov/planetary/apod?api_key=" +
            new PropertiesApi().load().getProperty("nasa"),
            new NasaContentExtractor()),
    IMDB_TOP250_MOVIES("https://imdb-api.com/en/API/Top250Movies/" +
            new PropertiesApi().load().getProperty("imdb"),
            new ImdbContentExtractor()),
    IMDB_TOP250__MOVIES_ALURA("https://alura-filmes.herokuapp.com/conteudos",
            new ImdbContentExtractor()),
    IMDB_MOSTPOPULAR_MOVIES("https://imdb-api.com/en/API/MostPopularMovies/" +
            new PropertiesApi().load().getProperty("imdb"),
            new ImdbContentExtractor()),
    PROGRAMMING_LANGUAGES("https://programming-languages-alura.herokuapp.com/languages/",
            new LanguagesContentExtrator());

    private final String url;
    private final ContentExtractor contentExtractor;

    private UrlExtractorEnum(String url, ContentExtractor contentExtractor) {
        this.url = url;
        this.contentExtractor = contentExtractor;
    }

    public String getUrl() {
        return url;
    }

    public ContentExtractor getContentExtractor() {
        return contentExtractor;
    }

}
