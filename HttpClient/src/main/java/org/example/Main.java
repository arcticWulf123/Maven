package org.example;

import org.example.models.Forecast;
import org.example.services.WeatherService;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Latitude: ");
        double latitude = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter Longitude: ");
        double longitude = sc.nextDouble(); sc.nextLine();

        WeatherService weatherService = new WeatherService();
        weatherService.getForecast(latitude, longitude);
        for (int i = 0; i < 3; i++) {

        }
    }
}
