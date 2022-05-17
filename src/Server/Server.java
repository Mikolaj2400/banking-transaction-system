package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        List<String> sciezkiDoPlikow = List.of("C:/Users/mikka/IdeaProjects/Server/kowalski",
                "C:/Users/mikka/IdeaProjects/Server/gigiel",
                "C:/Users/mikka/IdeaProjects/Server/mozol");

        List<Klient> listaKlientow = new ArrayList();

        for (String sciezkaDoPliku : sciezkiDoPlikow) {

            Klient klient = new Klient(sciezkaDoPliku);
            listaKlientow.add(klient);
        }

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
                //out = new PrintWriter(socket.getOutputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Błąd przy tworzeniu strumieni.");
                System.exit(-1);
            }
            System.out.println("Zakończona inicjalizacja strumieni...");
            System.out.println("Rozpoczęcie pętli głównej...");


            //  Logowanie

            try {
                // Login
                String line = brinp.readLine();
                System.out.println("Odczytano linię: " + line);
                String login = line;
                // haslo
                out.writeBytes(" Podaj haslo" + "\n");
                System.out.println("Wysłano linię: " + "Witaj " + line + "Podaj haslo");
                line = brinp.readLine();
                String haslo = line;

                System.out.println("Odczytano linię: " + line);

                Klient zalogowanyKlient = null;
                for (Klient klient : listaKlientow) {
                    if (klient.czyZgadzaSieLoginIHaslo(login, haslo)) {
                        zalogowanyKlient = klient;
                        break;
                    }
                }
                if (zalogowanyKlient == null) {
                    out.writeBytes("Niepoprawne login lub haslo\n");
                    socket.close();
                } else {
                    out.writeBytes("Zalogowano pomyslnie. ");

                    // operacje
                    boolean czyKoniec = false;
                    while (!czyKoniec) {
                        out.writeBytes("Jaka transakcje chcesz wykonac? (Wpisz numer lub quit zeby zakonczyc)" +
                                " 1. Przelew na inne konto"
                                + " 2. Wyplacic srodki"
                                + " 3. Wplacic srodki"
                                + " 4. Sprawdzic stan konta" + "\n");

                        line = brinp.readLine();

                        switch (line) {
                            case "1":
                                System.out.println("Podaj nr konta bankowego, na ktory chcesz zrobic przelew.");
                                out.writeBytes("Podaj nr konta bankowego, na ktory chcesz zrobic przelew.\n");
                                line = brinp.readLine();
                                String nrBankowy = line;

                                Klient kleintDocelowy = null;
                                for (Klient klient : listaKlientow) {
                                    if (klient.czyZgadzaSieNumerKonta(nrBankowy)) {
                                        kleintDocelowy = klient;
                                    }
                                }
                                if (kleintDocelowy == null) {
                                    System.out.println("Brak nr bankowego w bazie.");
                                    out.writeBytes("Brak nr bankowego w bazie. ");
                                } else {

                                    System.out.println("Podaj kwote przelewu ");
                                    out.writeBytes("Podaj kwote przelewu \n");
                                    line = brinp.readLine();
                                    String kwotaPrzelewu = line;
                                    int kwotaPrzelewuInt = Integer.parseInt(kwotaPrzelewu);

                                    if (kwotaPrzelewuInt > zalogowanyKlient.getStanKonta()) {
                                        System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna przelac srodkow. ");
                                        out.writeBytes("Ta kwota przekracza Panskie saldo. Nie mozna przelac srodkow. ");
                                    } else {
                                        zalogowanyKlient.zmniejszStanKonta(kwotaPrzelewuInt);
                                        kleintDocelowy.zwiekszStanKonta(kwotaPrzelewuInt);
                                        out.writeBytes("Przelew wykonany pomyslnie. ");
                                    }
                                }
                                break;

                            case "2":
                                System.out.println("Jaka kwote chcesz wyplacic z konta?");
                                out.writeBytes("Jaka kwote chcesz wyplacic z konta?\n");
                                line = brinp.readLine();
                                String kwota = line;
                                int kwotaTransakcji = Integer.parseInt(kwota);

                                if (zalogowanyKlient.getStanKonta() < kwotaTransakcji) {
                                    System.out.println("Ta kwota przekracza Panskie saldo. Nie mozna wyplacic gotowki");
                                    out.writeBytes("Ta kwota przekracza Panskie saldo. Nie mozna wyplacic gotowki. ");
                                } else {
                                    zalogowanyKlient.zmniejszStanKonta(kwotaTransakcji);
                                    out.writeBytes("Srodki zostaly wyplacone. ");
                                }
                                break;

                            case "3":
                                System.out.println("Jaka kwote chcesz wplacic na konto?");
                                out.writeBytes("Jaka kwote chcesz wplacic na konto?\n");
                                line = brinp.readLine();
                                kwota = line;
                                kwotaTransakcji = Integer.parseInt(kwota);

                                zalogowanyKlient.zwiekszStanKonta(kwotaTransakcji);

                                out.writeBytes("Udalo sie dokonac wplaty. ");
                                break;

                            case "4":
                                System.out.println("Twoj stan konta to " + zalogowanyKlient.getStanKonta());
                                out.writeBytes("Twoj stan konta " + zalogowanyKlient.getStanKonta() + ". ");
                                break;

                            case "quit":
                                czyKoniec = true;
                                socket.close();
                                break;

                            default:
                                System.out.println("Zły numer");
                                out.writeBytes("Podales zly numer transakcji.  ");
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}