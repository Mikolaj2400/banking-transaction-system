/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author dzelazny
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {

    public static void main(String args[]) throws FileNotFoundException {

        //File plikLogowania = new File("C:/Users/mikka/IdeaProjects/Server/users");
        //Scanner scan = new Scanner(plikLogowania);
       // System.out.println("\n" + scan);


        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            System.out.println(
                    "Błąd przy tworzeniu gniazda serwerowego.");
            System.exit(-1);
        }
        System.out.println("Inicjalizacja gniazda zakończona...");
        System.out.println("Parametry gniazda: " + serverSocket);
        while (true) {
            try {
                System.out.println("Trwa oczekiwanie na połączenie...");
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println(e);
                System.exit(-1);
            }
            System.out.println("Nadeszło połączenie...");
            System.out.println("Parametry połączenia: " + socket);
            try {
                System.out.println("Inicjalizacja strumieni...");
                brinp = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Błąd przy tworzeniu strumieni.");
                System.exit(-1);
            }
            System.out.println("Zakończona inicjalizacja strumieni...");
            System.out.println("Rozpoczęcie pętli głównej...");
            while (true) {
                try {
                    String line = brinp.readLine();
                    System.out.println("Odczytano linię: " + line);
                    if (line == null || "quit".equals(line)) {
                        socket.close();
                        System.out.println("Zakończenie pracy z klientem...");
                        break;
                    }
                    out.writeBytes("podaj hasło" + "\n\r");
                    System.out.println("Wysłano linię: " + "podaj hasło");
                } catch (IOException e) {
                    System.out.println("Błąd wejścia-wyjścia: " + e);
                    break;
                }
            }
        }
    }
}
