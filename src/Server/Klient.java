package Server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Klient {

    private String login;
    private String imie;
    private String nazwisko;
    private String haslo;
    private String nrKonta;
    private int saldo;
    private String pesel;

    private final String sciezkaDoPliku;
    private List<String> fileContent;

    public Klient(String sciezkaDoPliku) {

        this.sciezkaDoPliku = sciezkaDoPliku;

        File file = new File(sciezkaDoPliku);
        login = file.getName();

        try {
            imie = Files.readAllLines(Paths.get(sciezkaDoPliku)).get(0);
            nazwisko = Files.readAllLines(Paths.get(sciezkaDoPliku)).get(1);
            haslo = Files.readAllLines(Paths.get(sciezkaDoPliku)).get(2);
            pesel = Files.readAllLines(Paths.get(sciezkaDoPliku)).get(3);
            nrKonta = Files.readAllLines(Paths.get(sciezkaDoPliku)).get(4);
            saldo = Integer.parseInt(Files.readAllLines(Paths.get(sciezkaDoPliku)).get(5));
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(sciezkaDoPliku), StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean czyZgadzaSieLoginIHaslo(String danyLogin, String daneHaslo) {

        return daneHaslo.equals(haslo) && danyLogin.equals(login);
    }

    public boolean czyZgadzaSieNumerKonta(String numer) {

        return numer.equals(nrKonta);
    }

    public void zwiekszStanKonta(int kwota) {

        try {
            //zmiana w pliku
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(Integer.toString(saldo))) {
                        saldo += kwota;
                    fileContent.set(i, Integer.toString(saldo));
                    break;
                }
            }
            Files.write(Path.of(sciezkaDoPliku), fileContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void zmniejszStanKonta(int kwota) {

            zwiekszStanKonta(-kwota);
    }

    public int getStanKonta() throws IOException {
       int saldoPoPrzelewie = Integer.parseInt(Files.readAllLines(Paths.get(sciezkaDoPliku)).get(5));
        return saldoPoPrzelewie;
    }
}