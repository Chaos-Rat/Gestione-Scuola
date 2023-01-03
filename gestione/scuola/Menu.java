/**
 * Classe Menu, fornisce un menu di base.
 * 
 * @version 1.1 (3-1-2023)
 * @author Adnaan Juma
 * @author Matteo Del Checcolo
 */

package gestione.scuola;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	public static void main(String[] args)
	{
		GestoreStudenti gestore = new GestoreStudenti();
		Scanner sc = new Scanner(System.in);
		
		try {
			gestore.caricaListaStudenti("lista.bin");
		} catch (FileNotFoundException exception) {
			System.err.println("[AVVERTIMENTO]: Il file di salvataggio non e' presente e non verra' quindi caricata la lista.");
		} catch (IOException exception) {
			System.err.println("[ERRORE]: Errore occorso nell'apertura del file!");
		} catch (ClassNotFoundException exception) {
			System.err.println("[ERRORE]: File di salvataggio corrotto!");
		}
		
		System.out.print("Scegli una delle seguenti opzioni:"
		+ "\n[1]: Salva studente"
		+ "\n[2]: Cerca studente"
		+ "\n[3]: Modifica informazioni studente"
		+ "\n[4]: Elimina studente"
		+ "\n[5]: Visualizza lista studenti\n\n");

		int scelta = -1;

		while (scelta < 1 || scelta > 4) {
			try {
				scelta = sc.nextInt();

				if (scelta < 1 || scelta > 4) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException exception) {
				System.out.println("Input inserito invalido, si prega di reinserirlo");
				sc.nextLine();
			}
		}
		System.out.print("\033[H\033[2J"); // Pulisce lo schermo
		System.out.flush();

		sc = new Scanner(System.in);

		switch(scelta) {
			case 1: // Salva studente
				String nome, cognome;
				LocalDate dataDiNascita;
				String luogoDiNascita;
				Byte classeFrequentata, anniDiRipetizione = (byte)0;
				
				System.out.print(">Salva studente\n\n");
				System.out.print("\tInserisci il nome: ");
				nome = sc.nextLine();
				System.out.print("\tInserisci il cognome: ");
				cognome = sc.nextLine();
				System.out.print("\tInserisci la data di nascita (aaaa-mm-gg): ");
				dataDiNascita = LocalDate.parse(sc.nextLine());
				System.out.print("\tInserisci il luogo di nascita: ");
				luogoDiNascita = sc.nextLine();
				System.out.print("\tInserisci la classe Frequentata (max 127): ");
				classeFrequentata = sc.nextByte();
				sc.nextLine();
				System.out.print("\tLo studente e' ripetente? (si/no): ");

				switch (sc.nextLine()) {
					case "si":
						System.out.print("\tQuanti anni ha dovuto ripetere? (max 127): ");
						anniDiRipetizione = sc.nextByte();
						sc.nextLine();
						break;
					default:
				}

				try {
					gestore.salvaStudente(new Studente(nome, cognome, dataDiNascita, luogoDiNascita, classeFrequentata, anniDiRipetizione));
					System.out.print("\nStudente inserito nella lista con successo! ");
					sc.nextLine();
				} catch (StudenteGiaEsistenteException exception) {
					System.err.print("\n[ERRORE]: Lo studente e' gia' stato inserito nella lista precedentemente, e non verra inserito! ");
					sc.nextLine();
				}

				break;
			case 2: // Cerca studente

				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
		}
	}
}