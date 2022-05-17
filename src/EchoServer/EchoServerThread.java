/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EchoServer;

/**
 *
 * @author dzelazny
 */
import Server.Klient;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EchoServerThread implements Runnable
{
  protected Socket socket;
  public EchoServerThread(Socket clientSocket)
  {
    this.socket = clientSocket;
  }
  public void run()
  {
    //Deklaracje zmiennych
    BufferedReader brinp = null;
    DataOutputStream out = null;
    String threadName = Thread.currentThread().getName();
    
    //inicjalizacja strumieni
    try{
      brinp = new BufferedReader(
        new InputStreamReader(
          socket.getInputStream()
        )
      );
      out = new DataOutputStream(socket.getOutputStream());
    }
    catch(IOException e){
      System.out.println(threadName + "| Błąd przy tworzeniu strumieni " + e);
      return;
    }

    List<String> sciezkiDoPlikow = List.of("C:/Users/mikka/IdeaProjects/Server/kowalski",
            "C:/Users/mikka/IdeaProjects/Server/gigiel",
            "C:/Users/mikka/IdeaProjects/Server/mozol");

    List<Klient> listaKlientow = new ArrayList();

    for (String sciezkaDoPliku : sciezkiDoPlikow) {

      Klient klient = new Klient(sciezkaDoPliku);
      listaKlientow.add(klient);
    }
    
    //pętla główna
    while(true){
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
                  }else if(kwotaPrzelewuInt<0) {
                    out.writeBytes("Podaj kwote dodatnia.  ");
                  } else{
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
