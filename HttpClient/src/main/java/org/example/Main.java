package org.example;

import org.example.models.WeatherResponse;
import org.example.services.WeatherService;

import java.util.Scanner;

public class Main {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Latitude: ");
        double latitude = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter Longitude: ");
        double longitude = sc.nextDouble(); sc.nextLine();

        WeatherService weatherService = new WeatherService();
        WeatherResponse response = weatherService.getForecast(latitude, longitude);
        if (response == null) {
            System.out.println("Could not retrieve weather data");
            System.exit(1);
        }
         for (int i = 0; i < 3; i++) {
             System.out.println(response.getForecastsString(i));
         }
    }
}
