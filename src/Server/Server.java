package Server;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {

    public static void main(String args[]) throws IOException {


        //odczytywanie nazwy pliku (bez txt)
        //plik Kowalski
        File fileKowalski = new File("C:/Users/mikka/IdeaProjects/Server/kowalski");
        String fileNameKowalski = fileKowalski.getName();
        //plik Gigiel
        File fileGigiel = new File("C:/Users/mikka/IdeaProjects/Server/gigiel");
        String fileNameGigiel = fileGigiel.getName();
        //plik Mozol
        File fileMozol = new File("C:/Users/mikka/IdeaProjects/Server/mozol");
        String fileNameMozol = fileMozol.getName();
        System.out.println(fileNameKowalski);
        System.out.println(fileNameGigiel);
        System.out.println(fileNameMozol);
//        if (nazwaPliku.equals("kowalski"))
//        {
//            System.out.println(nazwaPliku);
//        }
//        else{
//            System.out.println("niepoprwane czytanie");
//        }
        //Scanner scan = new Scanner(plikLogowania);
       // System.out.println("\n" + scan);



        //czytanie konkretnych linii pliku txt
//        String haslo = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/kowalski")).get(2);
//        String gigiel = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/gigiel")).get(2);
//        System.out.println(haslo);
//        System.out.println(gigiel);



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
                    //odczytywanie pierwszej linii od klienta (login, ktory jest nazwa pliku)
                    String line = brinp.readLine();
                    System.out.println("Odczytano linię: " + line);
                    //za kazdym razem, gdy klient wpisuje cos do konsoli trzeba sprawdzic czy nie wpisal quit zeby wyjsc
                    if (line == null || "quit".equals(line)) {
                        socket.close();
                        System.out.println("Zakończenie pracy z klientem...");
                        break;
                    }
                    if (line.equals(fileNameKowalski)){ //sprawdza plik Kowalski
                        out.writeBytes("Witaj " + line +" Podaj hasło" + "\n\r");
                        System.out.println("Wysłano linię: " + "Witaj " + line + "podaj hasło");
                    }else if (line.equals(fileNameGigiel)){//sprawdza plik Gigiel
                        out.writeBytes("Witaj " + line +" Podaj hasło" + "\n\r");
                        System.out.println("Wysłano linię: " + "Witaj " + line + "podaj hasło");
                    }else if (line.equals(fileNameMozol)){ //sprawdza plik Mozol
                        out.writeBytes("Witaj " + line +" Podaj hasło" + "\n\r");
                        System.out.println("Wysłano linię: " + "Witaj " + line + "podaj hasło");
                    }else{
                        out.writeBytes("Niepoprawny login");
                        socket.close();
                        System.out.println("Niepoprawny login");
                    }
                } catch (IOException e) {
                    System.out.println("Błąd wejścia-wyjścia: " + e);
                    break;
                }
            }
        }
    }
}
