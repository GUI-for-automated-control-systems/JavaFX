package com.example.realtimecharts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static final int PORT = 19000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for connection...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from: " + socket.getInetAddress());

                // Создаем отдельные потоки для чтения и записи
                Thread readThread = new Thread(() -> handleClientRead(socket));
                Thread writeThread = new Thread(() -> handleClientWrite(socket));

                readThread.start();
                writeThread.start();

                // Ждем завершения потоков
                try {
                    readThread.join();
                    writeThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRead(Socket socket) {
        try (InputStream in = socket.getInputStream()) {
            while (true) {
                byte[] buf = new byte[32 * 1024];
                int readBytes = in.read(buf);

                if (readBytes == -1) {
                    break; // Клиент отключился
                }

                String clientMessage = new String(buf, 0, readBytes);
                System.out.println("\t\t\t\tClient: " + clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientWrite(Socket socket) {
        try (OutputStream out = socket.getOutputStream()) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Server: ");
                String serverMessage = scanner.nextLine();

                out.write(serverMessage.getBytes());
                out.flush();

                if (serverMessage.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
