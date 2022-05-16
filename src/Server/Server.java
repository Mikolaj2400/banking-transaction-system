package Server;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Server {
    public static void main(String args[]) throws IOException {
        //stałe ze sciezka do pliku
        final String FILE_PATH_KOWALSKI = "";
        final String FILE_PATH_GIGIEL = "";
        final String FILE_PATH_MOZOL = "";


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

        //lista czytajaca plik txt (kowalski), sluzy do pozniejszej zmiany danych w pliku
        List<String> fileContentKowalski = new ArrayList<>(Files.readAllLines(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), StandardCharsets.UTF_8));
        List<String> fileContentGigiel = new ArrayList<>(Files.readAllLines(Path.of("C:/Users/mikka/IdeaProjects/Server/gigiel"), StandardCharsets.UTF_8));
        List<String> fileContentMozol = new ArrayList<>(Files.readAllLines(Path.of("C:/Users/mikka/IdeaProjects/Server/mozol"), StandardCharsets.UTF_8));

        //czytanie haseł z plików txt
        String passwordKowalski = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/kowalski")).get(2);
        String passwordGigiel = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/gigiel")).get(2);
        String passwordMozol = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/mozol")).get(2);


        //czytanie salda bankowego
        String saldoBankoweKowalski = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/kowalski")).get(5);
        String saldoBankoweGigiel = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/gigiel")).get(5);
        String saldoBankoweMozol = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/mozol")).get(5);

        //czytanie nr kont
        String numerKontaKowalski = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/kowalski")).get(4);
        String numerKontaGigiel = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/gigiel")).get(4);
        String numerKontaMozol = Files.readAllLines(Paths.get("C:/Users/mikka/IdeaProjects/Server/mozol")).get(4);

        //booleany do otwierania i zamykania petli while
        boolean whileLogin = true;
        boolean whilePassword = false;

        Scanner sc = new Scanner(System.in);
        //String wpis = sc.nextLine();
        Scanner kw = new Scanner(System.in);
        Scanner nrb = new Scanner(System.in);
        int saldoKowalski = Integer.parseInt(saldoBankoweKowalski);
        int saldoGigiel = Integer.parseInt(saldoBankoweGigiel);
        int saldoMozol = Integer.parseInt(saldoBankoweMozol);
        //wstepny switch na klienta (dla usera Kowalski)
//        switch(wpis)
//        {
//            case "1":
//                //System.out.println("1. Przelew na inne konto");
//                System.out.println("Podaj nr konta bankowego, na ktory chcesz zrobic przelew");
//                String nrBankowy = kw.nextLine();
//                if(nrBankowy.equals(numerKontaGigiel)) {
//                    System.out.println("Podaj kwote przelewu ");
//                    String kwotaPrzelewu = kw.nextLine();
//                    int kwotaPrzelewuInt = Integer.parseInt(kwotaPrzelewu);
//                    if(kwotaPrzelewuInt > saldoKowalski) {
//                        System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna przelac srodkow");
//                    }else{
//                        System.out.println("Kwota zostanie przelana na konto bankowe nr: "+ nrBankowy);
//                        saldoKowalski -= kwotaPrzelewuInt;
//                        saldoGigiel += kwotaPrzelewuInt;
//                        System.out.println(saldoKowalski);
//                        System.out.println(saldoGigiel);
//                        String saldoPoPrzelewieKowalski= Integer.toString(saldoKowalski);
//                        System.out.println(saldoPoPrzelewieKowalski);
//                        String saldoPoPrzelewieGigiel= Integer.toString(saldoGigiel);
//                        System.out.println(saldoPoPrzelewieGigiel);
//                        //zmiana w pliku gigiel
//                        for (int i = 0; i < fileContentGigiel.size(); i++) {
//                            if (fileContentGigiel.get(i).equals(saldoBankoweGigiel)) {
//                                fileContentGigiel.set(i, saldoPoPrzelewieGigiel);
//                                break;
//                            }
//                        }
//                        Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/gigiel"), fileContentGigiel, StandardCharsets.UTF_8);
//                        //zmiana w pliku kowalski
//                        for (int i = 0; i < fileContentKowalski.size(); i++) {
//                            if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
//                                fileContentKowalski.set(i, saldoPoPrzelewieKowalski);
//                                break;
//                            }
//                        }
//                        Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
//                    }
//                }else if(nrBankowy.equals(numerKontaMozol)){
//                    System.out.println("Podaj kwote przelewu ");
//                    String kwotaPrzelewu = kw.nextLine();
//                    int kwotaPrzelewuInt = Integer.parseInt(kwotaPrzelewu);
//                    if(kwotaPrzelewuInt > saldoKowalski) {
//                        System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna przelac srodkow");
//                    }else{
//                        System.out.println("Kwota zostanie przelana na konto bankowe nr: "+ nrBankowy);
//                        saldoKowalski -= kwotaPrzelewuInt;
//                        saldoMozol += kwotaPrzelewuInt;
//                        System.out.println(saldoKowalski);
//                        System.out.println(saldoMozol);
//                        String saldoPoWyplacieKowalski= Integer.toString(saldoKowalski);
//                        String saldoPoWyplacieMozol= Integer.toString(saldoMozol);
//
//                        //zmiana w pliku mozol
//                        for (int i = 0; i < fileContentMozol.size(); i++) {
//                            if (fileContentMozol.get(i).equals(saldoBankoweMozol)) {
//                                fileContentMozol.set(i, saldoPoWyplacieMozol);
//                                break;
//                            }
//                        }
//                        Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/mozol"), fileContentMozol, StandardCharsets.UTF_8);
//
//                        //zmiana w pliku kowalski
//                        for (int i = 0; i < fileContentKowalski.size(); i++) {
//                            if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
//                                fileContentKowalski.set(i, saldoPoWyplacieKowalski);
//                                break;
//                            }
//                        }
//                        Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
//                    }
//                }else{
//                    System.out.println("Brak nr bankowego w bazie");
//                }
//                break;
//            case "2":
//               // System.out.println("2. Wypłacić środki");
//                System.out.println("Jaka kwote chcesz wyplacic z konta?");
//                String kwota = kw.nextLine();
//                int kwotaTransakcji = Integer.parseInt(kwota);
//                saldoKowalski = Integer.parseInt(saldoBankoweKowalski);
//                if(kwotaTransakcji > saldoKowalski)
//                {
//                    System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna wyplacic gotowki");
//                }else{
//                    saldoKowalski -= kwotaTransakcji;
//                    String saldoPoWyplacie= Integer.toString(saldoKowalski);
//                    System.out.println(saldoPoWyplacie);
//                    //zmiana w pliku
//                    for (int i = 0; i < fileContentKowalski.size(); i++) {
//                        if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
//                            fileContentKowalski.set(i, saldoPoWyplacie);
//                            break;
//                        }
//                    }
//                    Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
//                }
//                break;
//            case "3":
//               // System.out.println("3. Dokonać wpłaty ");
//                System.out.println("Jaka kwote chcesz wplacic na konto?");
//                kwota = kw.nextLine();
//                kwotaTransakcji = Integer.parseInt(kwota);
//                saldoKowalski = Integer.parseInt(saldoBankoweKowalski);
//                saldoKowalski += kwotaTransakcji;
//                String saldoPoWplacie= Integer.toString(saldoKowalski);
//                System.out.println(saldoPoWplacie);
//                //zmiana w pliku
//
//                for (int i = 0; i < fileContentKowalski.size(); i++) {
//                    if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
//                        fileContentKowalski.set(i, saldoPoWplacie);
//                        break;
//                    }
//                }
//                Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
//                break;
//            case "4":
//                //System.out.println("4. Sprawdzić stan konta");
//                System.out.println("Twoj stan konta to "+ saldoBankoweKowalski);
//                break;
//            default:
//                // code block
//        }

        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader brinp = null;
        BufferedWriter writer = null;
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
            while (true)
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
                    if (line.equals(fileNameKowalski)) { //sprawdza plik Kowalski
                        out.writeBytes("Witaj " + line + " Podaj haslo" + "\n\r");
                        System.out.println("Wysłano linię: " + "Witaj " + line + "\nPodaj hasło");
                        out.flush();
                        line = brinp.readLine();
                        System.out.println("Odczytano linię: " + line);
                        if (line.equals(passwordKowalski)) {
                            out.writeBytes("Poprawne haslo, udalo sie zalogowac" + "Jaką transakcję chcesz wykonać? (Wpisz numer)" +
                                    "1. Przelew na inne konto"
                                    + "2. Wypłacić środki"
                                    + "3. Dokonać wpłaty"
                                    + "4. Sprawdzić stan konta" + "\n");
                            line = brinp.readLine();
                            System.out.println("Odczytano linię: " + line);

                            switch (line) {
                                case "1":
                                    //System.out.println("1. Przelew na inne konto");
                                    System.out.println("Podaj nr konta bankowego, na ktory chcesz zrobic przelew");
                                    String nrBankowy = kw.nextLine();
                                    if (nrBankowy.equals(numerKontaGigiel)) {
                                        System.out.println("Podaj kwote przelewu ");
                                        String kwotaPrzelewu = kw.nextLine();
                                        int kwotaPrzelewuInt = Integer.parseInt(kwotaPrzelewu);
                                        if (kwotaPrzelewuInt > saldoKowalski) {
                                            System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna przelac srodkow");
                                        } else {
                                            System.out.println("Kwota zostanie przelana na konto bankowe nr: " + nrBankowy);
                                            saldoKowalski -= kwotaPrzelewuInt;
                                            saldoGigiel += kwotaPrzelewuInt;
                                            System.out.println(saldoKowalski);
                                            System.out.println(saldoGigiel);
                                            String saldoPoPrzelewieKowalski = Integer.toString(saldoKowalski);
                                            System.out.println(saldoPoPrzelewieKowalski);
                                            String saldoPoPrzelewieGigiel = Integer.toString(saldoGigiel);
                                            System.out.println(saldoPoPrzelewieGigiel);
                                            //zmiana w pliku gigiel
                                            for (int i = 0; i < fileContentGigiel.size(); i++) {
                                                if (fileContentGigiel.get(i).equals(saldoBankoweGigiel)) {
                                                    fileContentGigiel.set(i, saldoPoPrzelewieGigiel);
                                                    break;
                                                }
                                            }
                                            Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/gigiel"), fileContentGigiel, StandardCharsets.UTF_8);
                                            //zmiana w pliku kowalski
                                            for (int i = 0; i < fileContentKowalski.size(); i++) {
                                                if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
                                                    fileContentKowalski.set(i, saldoPoPrzelewieKowalski);
                                                    break;
                                                }
                                            }
                                            Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
                                        }
                                    } else if (nrBankowy.equals(numerKontaMozol)) {
                                        System.out.println("Podaj kwote przelewu ");
                                        String kwotaPrzelewu = kw.nextLine();
                                        int kwotaPrzelewuInt = Integer.parseInt(kwotaPrzelewu);
                                        if (kwotaPrzelewuInt > saldoKowalski) {
                                            System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna przelac srodkow");
                                        } else {
                                            System.out.println("Kwota zostanie przelana na konto bankowe nr: " + nrBankowy);
                                            saldoKowalski -= kwotaPrzelewuInt;
                                            saldoMozol += kwotaPrzelewuInt;
                                            System.out.println(saldoKowalski);
                                            System.out.println(saldoMozol);
                                            String saldoPoWyplacieKowalski = Integer.toString(saldoKowalski);
                                            String saldoPoWyplacieMozol = Integer.toString(saldoMozol);

                                            //zmiana w pliku mozol
                                            for (int i = 0; i < fileContentMozol.size(); i++) {
                                                if (fileContentMozol.get(i).equals(saldoBankoweMozol)) {
                                                    fileContentMozol.set(i, saldoPoWyplacieMozol);
                                                    break;
                                                }
                                            }
                                            Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/mozol"), fileContentMozol, StandardCharsets.UTF_8);

                                            //zmiana w pliku kowalski
                                            for (int i = 0; i < fileContentKowalski.size(); i++) {
                                                if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
                                                    fileContentKowalski.set(i, saldoPoWyplacieKowalski);
                                                    break;
                                                }
                                            }
                                            Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
                                        }
                                    } else {
                                        System.out.println("Brak nr bankowego w bazie");
                                    }
                                    break;
                                case "2":
                                    // System.out.println("2. Wypłacić środki");
                                    System.out.println("Jaka kwote chcesz wyplacic z konta?");
                                    String kwota = kw.nextLine();
                                    int kwotaTransakcji = Integer.parseInt(kwota);
                                    saldoKowalski = Integer.parseInt(saldoBankoweKowalski);
                                    if (kwotaTransakcji > saldoKowalski) {
                                        System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna wyplacic gotowki");
                                    } else {
                                        saldoKowalski -= kwotaTransakcji;
                                        String saldoPoWyplacie = Integer.toString(saldoKowalski);
                                        System.out.println(saldoPoWyplacie);
                                        //zmiana w pliku
                                        for (int i = 0; i < fileContentKowalski.size(); i++) {
                                            if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
                                                fileContentKowalski.set(i, saldoPoWyplacie);
                                                break;
                                            }
                                        }
                                        Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
                                    }
                                    break;
                                case "3":
                                    // System.out.println("3. Dokonać wpłaty ");
                                    System.out.println("Jaka kwote chcesz wplacic na konto?");
                                    kwota = kw.nextLine();
                                    kwotaTransakcji = Integer.parseInt(kwota);
                                    saldoKowalski = Integer.parseInt(saldoBankoweKowalski);
                                    saldoKowalski += kwotaTransakcji;
                                    String saldoPoWplacie = Integer.toString(saldoKowalski);
                                    System.out.println(saldoPoWplacie);
                                    //zmiana w pliku

                                    for (int i = 0; i < fileContentKowalski.size(); i++) {
                                        if (fileContentKowalski.get(i).equals(saldoBankoweKowalski)) {
                                            fileContentKowalski.set(i, saldoPoWplacie);
                                            break;
                                        }
                                    }
                                    Files.write(Path.of("C:/Users/mikka/IdeaProjects/Server/kowalski"), fileContentKowalski, StandardCharsets.UTF_8);
                                    break;
                                case "4":
                                    //System.out.println("4. Sprawdzić stan konta");
                                    System.out.println("Twoj stan konta to " + saldoBankoweKowalski);
                                    out.writeBytes("Twoj stan konta " + saldoBankoweKowalski +"\n");
                                    break;
                                default:
                                    System.out.println("Zły numer");
                            }

                            //out.flush();
                            //line = brinp.readLine();
                            System.out.println("Odczytano linię: " + line);
                        } else {
                            out.writeBytes("Niepoprawne haslo");
                            System.out.println("Wysłano linię: " + "Niepoprawne haslo");
                        }
//                    } else if (line.equals(fileNameGigiel)) {//sprawdza plik Gigiel
//                        out.writeBytes("Witaj " + line + " Podaj hasło" + "\n\r");
//                        System.out.println("Wysłano linię: " + "Witaj " + line + "\nPodaj hasło");
//                    } else if (line.equals(fileNameMozol)) { //sprawdza plik Mozol
//                        out.writeBytes("Witaj " + line + " Podaj hasło" + "\n\r");
//                        System.out.println("Wysłano linię: " + "Witaj " + line + "\nPodaj hasło");
                    }else{
                            out.writeBytes("Niepoprawny login");
                            socket.close();
                            System.out.println("Niepoprawny login");
                        }
                        line = brinp.readLine();
                        System.out.println("Odczytano linię: " + line);
                        if (line == null || "quit".equals(line)) {
                            socket.close();
                            System.out.println("Zakończenie pracy z klientem...");
                            break;
                        }
                        //out.writeBytes(line + "\n\r");
                        System.out.println("Wysłano linię: " + line);
                    } catch(IOException e){
                        System.out.println("Błąd wejścia-wyjścia: " + e);
                        break;
                    }
                }

        }
    }

