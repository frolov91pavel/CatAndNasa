import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;

public class Main {

    public static final String apiKey = "0IebSeFEcmbGPq2MuJcfdRTnMwDLpwlQQm9c4d30";

    public static void main(String[] args) throws IOException, ParseException {

        ///+++Задача №1
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);
        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        List<Cat> cats = mapper.readValue(body, new TypeReference<List<Cat>>() {
        });
        
        Stream<Cat> catStream = cats.stream();
        catStream.filter(value -> value.getUpvotes() > 0).forEach(System.out::println);
        ///---Задача №1

        ///+++Задача №2
        HttpGet requestNASA = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=" + apiKey);
        requestNASA.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse responseNASA = httpClient.execute(requestNASA);

        String bodyNASA = new String(responseNASA.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

        Object obj = new JSONParser().parse(bodyNASA);

        JSONObject jo = (JSONObject) obj;

        String url = (String) jo.get("url");

        String[] splitURL = url.split("/");
        String nameFileAndType = splitURL[splitURL.length - 1];

        String[] splitNameType = nameFileAndType.split("\\.");
        String type = splitNameType[splitNameType.length - 1];

        BufferedImage image = ImageIO.read(new URL(url));

        ImageIO.write(image, type, new File(nameFileAndType));
        ///---Задача №2

    }

}
