package org.conghung.lab01.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    /**
     * Mở kết nối
     *
     * @param method là web method (GET, POST, PUT hay DELETE)
     * @param url    địa chỉ URL của REST API
     */
    public static HttpURLConnection openConnection(String method, String url) throws IOException {
        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        return connection;
    }

    /**
     * Đọc dữ liệu trả về từ kết nối
     *
     * @param connection kết nối HTTP
     * @return mảng byte chứa dữ liệu đọc được
     */
    public static byte[] readData(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            try (var out = new ByteArrayOutputStream();
                 var is = connection.getInputStream()) {
                var block = new byte[4 * 1024];
                int n;
                while ((n = is.read(block)) != -1) {
                    out.write(block, 0, n);
                }
                return out.toByteArray();
            } finally {
                connection.disconnect();
            }
        }
        connection.disconnect();
        throw new IOException("Server returned error: " + responseCode + " - " + connection.getResponseMessage());
    }

    /**
     * Ghi dữ liệu vào kết nối và đọc phản hồi
     *
     * @param connection kết nối HTTP
     * @param data       mảng byte chứa dữ liệu cần ghi
     * @return mảng byte chứa dữ liệu phản hồi
     */
    public static byte[] writeData(HttpURLConnection connection, byte[] data) throws IOException {
        connection.setDoOutput(true);
        try (var outputStream = connection.getOutputStream()) {
            outputStream.write(data);
            outputStream.flush();
        }
        return readData(connection);
    }
}
