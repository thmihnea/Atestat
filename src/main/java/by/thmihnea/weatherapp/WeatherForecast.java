package by.thmihnea.weatherapp;

import by.thmihnea.weatherapp.request.WeatherRequest;

import java.util.Scanner;

public class WeatherForecast {

    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        print(Settings.LANG_INPUT_LOCATION);
        try {
            var locationInput = SCANNER.nextLine();
            var response = new WeatherRequest(locationInput)
                    .getWeatherResponse()
                    .join()
                    .formatWeatherMessage();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
            print(Settings.LANG_ERROR);
            main(args);
        }
    }

    public static void print(final String message)
    {
        System.out.println("(Weather Forecast) - " + message);
    }

}
