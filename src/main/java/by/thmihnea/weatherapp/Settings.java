package by.thmihnea.weatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Settings {

    public static final String
            WEATHER_API_KEY = "Formatted",
            WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    public static final String
            LANG_INPUT_LOCATION = "Please select a location.",
            LANG_ERROR = "An error has occurred! Please, try again!";

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

}
