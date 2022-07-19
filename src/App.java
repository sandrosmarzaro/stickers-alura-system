import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
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
//        final String url = "https://api.mocki.io/v2/549a5d8b";

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        var parser = new JsonParser();
        List<Map<String, String>> moviesList = parser.parse(body);
        
        clearConsole();
        final String boldCode = "\u001b[1m";
        final String resetCode = "\u001b[0m";
        final String greenBackCode = "\u001b[42;1m";
        final String whiteFontCode = "\u001b[37;1m";
        for (Map<String,String> movie : moviesList) {
            System.out.println(boldCode + "Title: " + resetCode + movie.get("title"));
            System.out.println(boldCode + "Poster: " + resetCode + movie.get("image"));
            System.out.println(greenBackCode + whiteFontCode + 
                "Rating: " + movie.get("imDbRating") + resetCode
            );
            int intRating = 0;
            try {
                intRating = (int) Math.round(Double.parseDouble(movie.get("imDbRating")));
            }
            catch (Exception e) {
                System.out.println("❌");
            }
            System.out.println("⭐".repeat(intRating));
            System.out.println();
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
