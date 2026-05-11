package org.example.services;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.example.models.WeatherResponse;

public class WeatherService {
    public WeatherService (){}
    private HttpClient client;
    private Gson gson = new Gson();

    public WeatherResponse getForecast(double lat, double lon) {
        client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://www.7timer.info/bin/astro.php?lon=%.2f&lat=%.2f&ac=0&unit=metric&output=json", lon, lat))) //add json at =
                .GET()
                .build();
        System.out.println("Requesting to the server....");
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());

            if (response.statusCode() == 200) {
                System.out.println("SUCCESS! Raw JSON data received:");
                System.out.println(response.body());
                return gson.fromJson(response.body(), WeatherResponse.class);
            } else {
                return null;
            }

        } catch (IOException e) {
            System.err.println("Cannot fetch data");
        } catch (InterruptedException e) {
            System.out.println("NETWORK ERROR: " + e.getMessage());
        }
        return null;
    }
}
