package com.example.demo4;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleClient {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7070)) {
            System.out.println("Сервер запущен. Ожидание подключений...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключен клиент: " + clientSocket.getInetAddress().getHostAddress());

                try (
                        Scanner socketIn = new Scanner(clientSocket.getInputStream());
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                ) {
                    // Читаем запрос от клиента
                    String clientRequest = socketIn.nextLine();
                    System.out.println("Запрос от клиента: " + clientRequest);

                    String response = null;
                    if (clientRequest.equals("Get workers")) {
                        ArrayList<String[]> workers = new ArrayList<>();
                        workers.add(new String[]{"Alim", "127.0.0.1", "5000"});
                        workers.add(new String[]{"RedRouse", "127.0.0.1", "5001"});
                        workers.add(new String[]{"Alisher", "127.0.0.1", "5002"});

                        for (String[] worker : workers) {
                            out.println(String.join(" ", worker));
                        }
                    } else if (clientRequest.startsWith("Get result")) {
                        response = "Sample result 1\nSample result 2\nSample result 3";
                        out.println(response);
                    } else {
                        response = "Invalid request";
                        out.println(response);
                    }
                    System.out.println("Ответ клиенту: " + response);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    clientSocket.close();
                    System.out.println("Клиент отключен.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}