package Client;

/**
 *
 * @author dzelazny
 */
import java.net.*;
import java.io.*;


public class Client {

    public static void main(String args[]) throws FileNotFoundException {
        String host = "localhost";
        int port = 0;
        try {
            port = Integer.parseInt("6666");
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy argument: port");
            System.exit(-1);
        }
        //Inicjalizacja gniazda klienckiego
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.out.println("Nieznany host.");
            System.exit(-1);
        } catch (ConnectException e) {
            System.out.println("Połączenie odrzucone.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Błąd wejścia-wyjścia: " + e);
            System.exit(-1);
        }
        System.out.println("Połączono z " + clientSocket);

        //Deklaracje zmiennych strumieniowych
        String line = null;
        BufferedReader brSockInp = null;
        BufferedReader brLocalInp = null;
        DataOutputStream out = null;



        //w tym miejscy robimy switcha, za pomoca ktorego user wybierze co chce zrobic:
        //przelew na inne konto (jeśli ma środki i nr konta docelowego istnieje),
        //wypłacić środki (jeśli są dostępne),
        //dokonać wpłaty (po prostu zwiększa saldo),
        //sprawdzić stan konta (czyli odczytać saldo).

        //Utworzenie strumieni
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            brSockInp = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            brLocalInp = new BufferedReader(
                    new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Błąd przy tworzeniu strumieni: " + e);
            System.exit(-1);
        }

        //tutaj musimy zrobic logowanie
        System.out.println("Podaj swój login");
        try{
            line = brLocalInp.readLine();
            System.out.println("Wysyłam: " + line);
            out.writeBytes(line + '\n');
            out.flush();
            line = brSockInp.readLine();
            System.out.println("Otrzymano: " + line);
        }catch(Exception e){
            System.out.println("Błąd przy logowaniu");
        }


        //Pętla główna klienta
        while (true) {
            try {
                line = brLocalInp.readLine();
                if (line != null) {
                    System.out.println("Wysyłam: " + line);
                    out.writeBytes(line + '\n');
                    out.flush();
                }
                if (line == null || "quit".equals(line)) {
                    System.out.println("Kończenie pracy...");
                    clientSocket.close();
                    System.exit(0);
                }
                brSockInp.readLine();
                System.out.println("Otrzymano: " + line);
            } catch (IOException e) {
                System.out.println("Błąd wejścia-wyjścia: " + e);
                System.exit(-1);
            }
        }
    }
}