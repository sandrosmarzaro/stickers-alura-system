import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {

//        If you want to use environment variables
//        final String apiKey = System.getenv("IMDB_API_KEY");
        final String apiKey = loadProperties().getProperty("apiKey");
//        Other Endpoints of the API:
//        final String top250Url = "https://imdb-api.com/en/API/Top250Movies/";
        final String mostPopularUrl = "https://imdb-api.com/en/API/MostPopularMovies/";
        final String url = mostPopularUrl + apiKey;
//         Alternative URL for consulting the API:
//        final String url = "https://alura-filmes.herokuapp.com/conteudos";

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        var parser = new JsonParser();
        List<Map<String, String>> moviesList = parser.parse(body);
        
        clearConsole();
        for (Map<String,String> movie : moviesList) {
            String imageUrl = movie.get("image");
            boolean hasBetterImage = imageUrl.contains("._V1_");
            if (hasBetterImage) {
                int startIndex = imageUrl.indexOf("._V1_");
                int endIndex = imageUrl.indexOf(".jpg");
                imageUrl = imageUrl.replace(imageUrl.substring(startIndex, endIndex), "");
            }
            System.out.println("Processing title: " + movie.get("title"));
            System.out.println();
            InputStream inputStream;
            try {
                inputStream = new URL(imageUrl).openStream();
            }
            catch (Exception e) {
                System.out.println("Error reading image");
                continue;
            }
            StickerGenerator.generate(inputStream, movie.get("title") + ".png");
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static Properties loadProperties() {
        final String fileName = "src/imdb.properties";
        try (FileInputStream fileInputStream = new FileInputStream(fileName)){
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
