/**
 * Classe Menu, fornisce un menu di base.
 * 
 * @version 1.4 (5-1-2023)
 * @author Adnaan Juma
 * @author Matteo Del Checcolo
 * @author Lorenzo Freccero
 */

package gestione.scuola;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
	/**
	 * Pulisce la console
	 */
	private static void cls()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Stampa un messaggio di avvertimento e attende il premersi del tasto invio da console
	 * 
	 * @param sc istanza dello scanner attualmente in uso
	 */
	private static void attendiInvio(Scanner sc)
	{
		System.out.print("\nPremere invio per tornare al menu principale...");
		sc.nextLine();
	}

	/**
	 * Ottiene le nuove credenziali di uno studente partendo da uno di base e ritorna una nuova istanza di <code>Studente</code> con esse.
	 * <b>Da utilizzare per la modifica delle informazioni studente (opzione menu 3)</b>
	 * 
	 * @param base studente con le credenziali che si vogliono cambiare
	 * @param sc istanza dello scanner attualmente in uso
	 * @param formatoInvalido stringa di errore da stampare in caso l'utente inserisca un input sbagliato
	 * @return istanza di <code>Studente</code> con le credenziali aggiornate
	 */
	private static Studente ottieniNuoveCredenziali(Studente base, Scanner sc, String formatoInvalido)
	{
		String input = "";
		boolean inputValido;

		Studente nuovoStudente = new Studente(base);
		
		System.out.print("\tInserire il nuovo nome (vecchio nome: \"" + nuovoStudente.getNome() + "\"): ");
		input = sc.nextLine();
		if (!input.isEmpty()) {
			nuovoStudente.setNome(input);
		}

		System.out.print("\tInserire il nuovo cognome (vecchio cognome: \"" + nuovoStudente.getCognome() + "\"): ");
		input = sc.nextLine();
		if (!input.isEmpty()) {
			nuovoStudente.setCognome(input);
		}

		System.out.print("\tInserire la nuova data di nascita (vecchia data di nascita: \"" + nuovoStudente.getDataDiNascita().toString() + "\") (aaaa-mm-gg): ");
		do {
			try {
				input = sc.nextLine();
				if (input.isEmpty()) {
					break;
				}
				nuovoStudente.setDataDiNascita(LocalDate.parse(input));
				inputValido = true;
			} catch (DateTimeParseException exception) {
				System.out.print(formatoInvalido + "\n\t");
				inputValido = false;
			}
		} while (!inputValido);
		
		System.out.print("\tInserire il nuovo luogo di nascita (vecchio luogo di nascita: \"" + nuovoStudente.getLuogoDiNascita() + "\"): ");
		input = sc.nextLine();
		if (!input.isEmpty()) {
			nuovoStudente.setLuogoDiNascita(input);
		}

		System.out.print("\tInserire la nuova classe frequentata (vecchia classe frequentata: \"" + nuovoStudente.getClasseFrequentata().toString() + "\") (esempio: 3cif): ");
		do {
			try {
				input = sc.nextLine();
				if (input.isEmpty()) {
					break;
				}
				nuovoStudente.setClasseFrequentata(Classe.parse(input));
				inputValido = true;
			} catch (FormatoClasseException exception) {
				System.out.print(formatoInvalido + " (Suggerimento: il numero massimo per l'anno della classe e' 127)\n\t");
				inputValido = false;
			}
		} while (!inputValido);
		
		System.out.print("\tInserire i nuovi anni di ripetizione (vecchi anni di ripetizione: \"" + (nuovoStudente.getAnniDiRipetizione() == 0 ? "null" : nuovoStudente.getAnniDiRipetizione()) + "\", se lo studente non e' mai stato bocciato inserire 0): ");
		do {
			try {
				input = sc.nextLine();
				if (input.isEmpty()) {
					break;
				}
				nuovoStudente.setAnniDiRipetizione(Byte.parseByte(input));
				inputValido = true;
			} catch (NumberFormatException exception) {
				System.out.print(formatoInvalido + " (Suggerimento: il numero massimo di anni e' 127)\n\t");
				inputValido = false;
			}
		} while (!inputValido);

		return nuovoStudente;
	}

	/**
	 * Chiede all'utente se vuole salvare la lista degli studenti nel file di salvataggio.
	 * <b>Da utilizzare con quelle azioni da menu (esempio: [1] salva studente) che modificano la lista degli studenti del gestore</b>
	 * 
	 * @param gestore istanza di <code>GestoreStudenti</code> attualmente in uso
	 * @param pathSalvataggio percorso del file di salvataggio
	 * @param sc istanza dello scanner attualmente in uso
	 * @param formatoInvalido stringa di errore da stampare in caso l'utente inserisca un input sbagliato
	 * @throws FileNotFoundException se: <ul><li>il file corrisponde a una cartella e non a un file vero e proprio</li>
	 * <li>non esiste e non e' possibile crearlo</li>
	 * <li>e' impossibile aprirlo per qualsiasi altra ragione</li></ul>
	 * @throws IOException se un errore di IO (Input/Output) occore in fase di scrittura dell'header dello stream
	 */
	private static void richiediSalvataggio(GestoreStudenti gestore, String pathSalvataggio, Scanner sc, String formatoInvalido) throws FileNotFoundException, IOException
	{
		boolean inputValido = false;

		System.out.print("Desideri salvare la lista degli studenti sul file di salvataggio? (si/no): ");
		do {
			switch (sc.nextLine()) {
				case "si":
					gestore.salvaListaStudenti(pathSalvataggio);
					System.out.println("La lista e' stata salvata correttamente!");
					inputValido = true;
					break;
				case "no":
					inputValido = true;
					break;
				default:
					System.out.println(formatoInvalido);
					inputValido = false;
			}
		} while (!inputValido);
	}
	
	/**
	 * Funzione Main
	 * 
	 * @param args argomenti da console
	 */
	public static void main(String[] args)
	{
		GestoreStudenti gestore = new GestoreStudenti();
		String pathSalvataggio = "lista.bin";
		boolean chiudiProgramma = false;
		boolean inputValido = false; // serve determinare se i vari do while di verifica input nel codice continuino o no
		Scanner sc = new Scanner(System.in);
		int scelta = 0; // serve a immagizzinare l'input laddove e' presente un menu numerato

		/* Stringhe per gli errori */
		final String formatoInvalido = "Input inserito invalido, si prega di seguire il formato corretto e di reinserirlo";
		final String erroreScrittura = "Attenzione, e' occorso un errore nella scrittura del file di salvataggio! La lista non verra' salvata.";
		final String erroreLettura = "Attenzione, e' occorso un errore nella lettura del file di salvataggio! La lista non verra' caricata.";
		final String erroreFile = "Attenzione si e' verificato un problema con la creazione/apertura del file di salvataggio! La lista non verra caricata.";

		/* Caricamento in fase di avvio */
		try {
			gestore.caricaListaStudenti(pathSalvataggio);
			System.out.println("Avvertimento: la lista degli studenti e' stata caricata dal file di salvataggio con successo.");
		} catch (FileNotFoundException exception) {
			System.out.println("Attenzione, il file di salvataggio non e' presente e non verra' quindi caricata la lista.");
		} catch (IOException exception) {
			System.out.println(erroreLettura);
		} catch (ClassNotFoundException exception) {
			System.out.println("Attenzione, il file di salvataggio non contiene dati della lista, essa non verra' caricata.");
		}

		/* Menu */
		while (!chiudiProgramma) {
			System.out.print("\nScegli una delle seguenti opzioni:"
			+ "\n[1]: Salva studente"
			+ "\n[2]: Cerca studente"
			+ "\n[3]: Modifica informazioni studente"
			+ "\n[4]: Elimina studente"
			+ "\n[5]: Visualizza lista studenti"
			+ "\n[6]: Visualizza classe"
			+ "\n[7]: Elimina classe"
			+ "\n[8]: Promuovi studenti"
			+ "\n[9]: Salva lista studenti"
			+ "\n[10]: Carica lista studenti"
			+ "\n[11]: Chiudi il programma\n\n");

			do {
				try {
					scelta = Integer.parseInt(sc.nextLine());

					if (scelta < 1 || scelta > 11) {
						throw new NumberFormatException();
					}

					inputValido = true;
				} catch (NumberFormatException exception) {
					System.out.println(formatoInvalido);
					inputValido = false;
				}
			} while (!inputValido);
			
			cls();
			
			switch(scelta) {
				case 1: { // Salva studente
					System.out.print(">Salva Studente\n\n");
					
					/* Ottenimento informazioni */
					Studente studente = new Studente();
					
					System.out.print("\tInserire il nome: ");
					studente.setNome(sc.nextLine());
					
					System.out.print("\tInserire il cognome: ");
					studente.setCognome(sc.nextLine());
					
					System.out.print("\tInserire la data di nascita (aaaa-mm-gg): ");
					do {
						try {
							studente.setDataDiNascita(LocalDate.parse(sc.nextLine()));
							inputValido = true;
						} catch (DateTimeParseException exception) {
							System.out.print(formatoInvalido + "\n\t");
							inputValido = false;
						}
					} while (!inputValido);

					System.out.print("\tInserire il luogo di nascita: ");
					studente.setLuogoDiNascita(sc.nextLine());
					
					System.out.print("\tInserire la classe frequentata (ex: 4BIF): ");
					do {
						try {
							studente.setClasseFrequentata(Classe.parse(sc.nextLine()));
							inputValido = true;
						} catch (FormatoClasseException exception) {
							System.out.print(formatoInvalido + " (Suggerimento: il numero massimo per l'anno della classe e' 127)\n\t");
							inputValido = false;
						}
					} while (!inputValido);
					
					System.out.print("\tLo studente e' ripetente? (si/no): ");
					do {
						switch (sc.nextLine()) {
							case "si":
								System.out.print("\tQuanti anni ha dovuto ripetere?: ");
								do {
									try {
										studente.setAnniDiRipetizione(Byte.parseByte(sc.nextLine()));
										inputValido = true;
									} catch (NumberFormatException exception) {
										System.out.print(formatoInvalido + " (Suggerimento: il numero massimo di anni e' 127)\n\t");
										inputValido = false;
									}
								} while (!inputValido);
								
								inputValido = true;
								break;
							case "no":
								inputValido = true;
								break;
							default:
								System.out.println(formatoInvalido);
								inputValido = false;
						}
					} while (!inputValido);

					/* Salvataggio */
					try {
						gestore.salvaStudente(studente);
						System.out.println("\nStudente inserito nella lista con successo!");
						richiediSalvataggio(gestore, pathSalvataggio, sc, formatoInvalido);
					} catch (StudenteGiaEsistenteException exception) {
						System.out.println("\nAttenzione lo studente e' gia' stato inserito nella lista precedentemente e non verra inserito!");
					} catch (FileNotFoundException exception) {
						System.out.println(erroreFile);
					} catch (IOException exception) {
						System.out.println(erroreScrittura);
					}

					attendiInvio(sc);
					cls();

					break;
				} case 2: { // Cerca studente
					System.out.print(">Cerca studente\n\n");

					String nome, cognome;

					System.out.print("\tInserire il nome dello/degli studente/studenti che si desidera cercare: ");
					nome = sc.nextLine();

					System.out.print("\tInserire il cognome dello/degli studente/studenti che si desidera cercare: ");
					cognome = sc.nextLine();

					try {
						for (Studente studente : gestore.cercaStudente(nome, cognome)) {
							System.out.print("\n" + studente.toString() + "\n");
						}
					} catch (StudenteNonTrovatoException exception) {
						System.out.println("\nNessuno Studente con tali credenziali trovato nella lista.");
					}

					attendiInvio(sc);
					cls();

					break;
				} case 3: { // Modifica informazioni studente
					System.out.print(">Modifica informazioni studente\n\n");

					/* Ricerca */
					String nome, cognome;

					System.out.print("\tInserire il nome dello studente i quali dati si desiderano modificare: ");
					nome = sc.nextLine();

					System.out.print("\tInserire il cognome dello studente i quali dati si desiderano modificare: ");
					cognome = sc.nextLine();

					/* Ottenimento informazioni */
					try {
						Studente[] risultato = gestore.cercaStudente(nome, cognome);
						Studente nuovoStudente;
						
						if (risultato.length == 1) {
							scelta = 0;
							
							System.out.print("\nStudente trovato! Se non si desidera cambiare un certo campo, lasciarlo vuoto (premere direttamente invio).\n\n");
							
							nuovoStudente = ottieniNuoveCredenziali(risultato[0], sc, formatoInvalido);
							
						} else {
							System.out.print("\nPiu' di uno studente trovato!\n");

							for (int i = 0; i < risultato.length; i++) {
								System.out.print("\n\t[" + i + "] " + risultato[i].toString() + "\n");
							}

							System.out.print("\nQuale dei " + risultato.length + " studenti vuole modificare? ");
							
							do {
								try {
									scelta = Integer.parseInt(sc.nextLine());
				
									if (scelta < 0 || scelta > risultato.length - 1) {
										throw new NumberFormatException();
									}
				
									inputValido = true;
								} catch (NumberFormatException exception) {
									System.out.println(formatoInvalido);
									inputValido = false;
								}
							} while (!inputValido);

							System.out.print("\nSe non si desidera cambiare un certo campo, lasciarlo vuoto (premere direttamente invio).\n\n");

							nuovoStudente = ottieniNuoveCredenziali(risultato[scelta], sc, formatoInvalido);
						}

						/* Modifica e salvataggio */
						gestore.modificaStudente(risultato[scelta], nuovoStudente);
						System.out.println("\nInformazioni studente modificate con successo!");
						richiediSalvataggio(gestore, pathSalvataggio, sc, formatoInvalido);
					} catch (StudenteNonTrovatoException exception) {
						System.out.println("\nNessuno studente con tali credenziali trovato nella lista. ");
					} catch (FileNotFoundException exception) {
						System.out.println(erroreFile);
					} catch (IOException exception) {
						System.out.println(erroreScrittura);
					}

					attendiInvio(sc);
					cls();

					break;
				} case 4: { // Elimina studente
					System.out.print(">Elimina studente\n\n");

					/* Ricerca */
					String nome, cognome;

					System.out.print("\tInserire il nome dello studente che si vuole eliminare: ");
					nome = sc.nextLine();

					System.out.print("\tInserire il cognome dello studente che si vuole eliminare: ");
					cognome = sc.nextLine();

					try {
						Studente[] risultato = gestore.cercaStudente(nome, cognome);
						
						if (risultato.length == 1) {
							scelta = 0;
							System.out.print("\nStudente trovato!\n\n");
							System.out.println("\t" + risultato[0].toString());
						} else {
							System.out.print("\nPiu' di uno studente trovato!\n");

							for (int i = 0; i < risultato.length; i++) {
								System.out.print("\n\t[" + i + "] " + risultato[i].toString() + "\n");
							}

							System.out.print("\nQuale dei " + risultato.length + " studenti vuoi eliminare? ");
							
							do {
								try {
									scelta = Integer.parseInt(sc.nextLine());
				
									if (scelta < 0 || scelta > risultato.length - 1) {
										throw new NumberFormatException();
									}
				
									inputValido = true;
								} catch (NumberFormatException exception) {
									System.out.println(formatoInvalido);
									inputValido = false;
								}
							} while (!inputValido);
						}

						/* Conferma ed eliminazione */
						System.out.print("\nSi vuole davvero eliminarlo dalla lista? (si/no): ");

						do {
							switch (sc.nextLine()) {
								case "si":
									gestore.eliminaStudente(risultato[scelta]);
									System.out.println("\nLo studente e' stato rimosso dalla lista con successo.");
									richiediSalvataggio(gestore, pathSalvataggio, sc, formatoInvalido);
									inputValido = true;
									break;
								case "no":
									System.out.println("\nOperazione annullata.");
									inputValido = true;
									break;
								default:
									System.out.println(formatoInvalido);
									inputValido = false;
							}
						} while (!inputValido);
					} catch (StudenteNonTrovatoException exception) {
						System.out.println("\nNessuno studente con tali credenziali trovato nella lista.");
					} catch (FileNotFoundException exception) {
						System.out.println(erroreFile);
					} catch (IOException exception) {
						System.out.println(erroreScrittura);
					}

					attendiInvio(sc);
					cls();
					
					break;
				} case 5: { // Visualizza lista studenti
					System.out.print(">Visualizza lista studenti\n\n");

					for (Studente studente : gestore.getListaStudenti()) {
						System.out.print("\n\t" + studente.toString() + "\n");
					}

					attendiInvio(sc);
					cls();

					break;
				} case 11: {
					chiudiProgramma = true;
					sc.close();
				}
			}
		}
	}
}