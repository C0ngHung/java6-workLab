package org.conghung.lab01.client;

import org.conghung.lab01.utils.HttpClient;

import java.io.IOException;

public class RestClient {
    static String host = "https://poly-java-6-e08ab-default-rtdb.asia-southeast1.firebasedatabase.app";

    private static void getAll() {
        var url = host + "/students.json";
        try {
            var connection = HttpClient.openConnection("GET", url);
            var response = HttpClient.readData(connection);
            System.out.println(new String(response));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getByKey() {
        var url = host + "/students/-OenhfEdVCGXKC--ykLP.json";
        try {
            var connection = HttpClient.openConnection("GET", url);
            var response = HttpClient.readData(connection);
            System.out.println(new String(response));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void post() {
        var url = host + "/students.json";
        var data = "{\"id\": \"PS43444\", \"name\": \"Đào Công Hùng\", \"marks\": 8.5, \"gender\": true}";
        try {
            var connection = HttpClient.openConnection("POST", url);
            var response = HttpClient.writeData(connection, data.getBytes());
            System.out.println(new String(response));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void put() {
        var url = host + "/students/PS43444.json";
        var data = "{\"email\": \"hungnc@fpt.edu.vn\", \"fullname\": \"Đào Công Hùng\", \"marks\": 9.0, \"gender\": true, \"country\": \"VN\"}";
        try {
            var connection = HttpClient.openConnection("PUT", url);
            var response = HttpClient.writeData(connection, data.getBytes());
            System.out.println(new String(response));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void delete() {
        var url = host + "/students/-OenhfEdVCGXKC--ykLP.json";
        try {
            var connection = HttpClient.openConnection("DELETE", url);
            var response = HttpClient.readData(connection);
            System.out.println(new String(response));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void resetData() {
        var url = host + "/students.json";
        try {
            var connection = HttpClient.openConnection("DELETE", url);
            HttpClient.readData(connection);
            System.out.println("Data reset successfully.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
//        System.out.println("--- RESET DATA ---");
//        resetData();

        System.out.println("--- POST ---");
        post();
        System.out.println("\n--- GET ALL ---");
        getAll();
        System.out.println("\n--- GET BY KEY ---");
        getByKey();
        System.out.println("\n--- PUT ---");
        put();
        System.out.println("\n--- DELETE ---");
        delete();
    }
}
