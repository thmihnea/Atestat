package by.thmihnea.weatherapp.request;

import by.thmihnea.weatherapp.Settings;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class WeatherRequest {

    private final String locationInput;

    static WeatherResponse NULL_RESPONSE = new WeatherResponse(
            "Weather data not found!",
            "0",
            "0",
            "0"
    );

    @Contract(pure = true)
    public WeatherRequest(final @NotNull String locationInput)
    {
        this.locationInput = locationInput;
    }

    public CompletableFuture<WeatherResponse> getWeatherResponse()
    {
        String apiUrl = String.format(
                Settings.WEATHER_API_URL,
                this.locationInput,
                Settings.WEATHER_API_KEY
        );
        return CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return new URL((apiUrl));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenApply(url -> {
                    HttpURLConnection connection;
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        var builder = new StringBuilder();
                        String inputLine;
                        while ((inputLine = reader.readLine()) != null)
                        {
                            builder.append(inputLine);
                        }
                        reader.close();
                        return builder.toString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenApply(responseString -> {
                    var jsonObject = JsonParser.parseString(responseString).getAsJsonObject();
                    var weatherDescription = jsonObject
                            .get("weather")
                            .getAsJsonArray()
                            .get(0)
                            .getAsJsonObject()
                            .get("description")
                            .toString();
                    var mainObject = jsonObject.get("main").getAsJsonObject();
                    return new WeatherResponse(
                            weatherDescription,
                            mainObject.get("temp_min").toString(),
                            mainObject.get("temp_max").toString(),
                            mainObject.get("temp").toString()
                    );
                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return NULL_RESPONSE;
                });
    }

    public record WeatherResponse(String weatherDescription, String tempMin, String tempMax, String temp)
    {
        public String formatWeatherMessage()
        {
            return "Weather status: %s".formatted(weatherDescription.substring(1, 2).toUpperCase() + weatherDescription.substring(2, weatherDescription.length() - 1)) +
                    System.lineSeparator() +
                    "Current temperature: %s".formatted(temp) +
                    System.lineSeparator() +
                    "Minimum temperature today: %s".formatted(tempMin) +
                    System.lineSeparator() +
                    "Maximum temperature today: %s".formatted(tempMax);
        }
    }
}
