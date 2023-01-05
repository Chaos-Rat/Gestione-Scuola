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
import java.io.StreamCorruptedException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	/**
	 * Pulisce la console
	 */
	public static void cls()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Stampa un messaggio di avvertimento e attende il premersi del tasto invio da console
	 */
	public static void attendiInvio()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("\nPremere invio per tornare al menu principale...");
		sc.nextLine();
		sc.close();
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
		boolean inputValido = false;
		Scanner sc = new Scanner(System.in);
		int scelta = 0;

		/* Stringhe per gli errori */
		final String formatoInvalido = "Input inserito invalido, si prega di seguire il formato corretto e di reinserirlo";
		final String erroreScrittura = "Attenzione, e' occorso un errore nella scrittura del file di salvataggio! La lista non verra' salvata.";
		final String erroreLettura = "Attenzione, e' occorso un errore nella lettura del file di salvataggio! La lista non verra' caricata.";
		final String erroreFile = "Attenzione si e' verificato un problema con la creazione/apertura del file di salvataggio! La lista non verra caricata.";

		/* Caricamento in fase di avvio */
		try {
			gestore.caricaListaStudenti(pathSalvataggio);
			System.out.println("Attenzione, la lista degli studenti e' stata caricata dal file di salvataggio.");
		} catch (FileNotFoundException exception) {
			System.out.println("Attenzione, il file di salvataggio non e' presente e non verra' quindi caricata la lista.");
		} catch (IOException exception) {
			System.out.println(erroreLettura);
		} catch (ClassNotFoundException exception) {
			System.out.println("Attenzione, il file di salvataggio non contiene dati della lista, essa non verra' caricata.");
		}

		/* Menu */
		while (!chiudiProgramma) {
			System.out.print("Scegli una delle seguenti opzioni:"
			+ "\n[1]: Salva studente"
			+ "\n[2]: Cerca studente"
			+ "\n[3]: Modifica informazioni studente"
			+ "\n[4]: Elimina studente"
			+ "\n[5]: Visualizza lista studenti"
			+ "\n[6]: Visualizza classe"
			+ "\n[7]: Elimina classe"
			+ "\n[8]: Promuovi studenti"
			+ "\n[9]: Chiudi il programma\n\n");

			do {
				try {
					scelta = Integer.parseInt(sc.nextLine());

					if (scelta < 1 || scelta > 9) {
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
							System.out.println(formatoInvalido);
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
							System.out.println(formatoInvalido + " (Suggerimento: il numero massimo per l'anno della classe e' 127)");
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
										System.out.println(formatoInvalido + " (Suggerimento: il numero massimo di anni e' 127)");
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
						
						attendiInvio();
						cls();
					} catch (StudenteGiaEsistenteException exception) {
						System.out.println("\nAttenzione lo studente e' gia' stato inserito nella lista precedentemente e non verra inserito!");
						attendiInvio();
						cls();
					} catch (FileNotFoundException exception) {
						System.out.println(erroreFile);
						attendiInvio();
						cls();
					} catch (IOException exception) {
						System.out.println(erroreScrittura);
						attendiInvio();
						cls();
					}

					break;
				}
				case 2: { // Cerca studente
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

						attendiInvio();
						cls();
					} catch (StudenteNonTrovatoException exception) {
						System.out.println("\nNessuno Studente con tali credenziali trovato nella lista.");
						attendiInvio();
						cls();
					}

					break;
				}
				case 3: { // Modifica informazioni studente
					System.out.print(">Modifica informazioni studente\n\n");

					String nome, cognome;

					System.out.print("\tInserire il nome dello studente i quali dati si desiderano modificare: ");
					nome = sc.nextLine();

					System.out.print("\tInserire il cognome dello studente i quali dati si desiderano modificare: ");
					cognome = sc.nextLine();

					try {
						Studente[] risultato = gestore.cercaStudente(nome, cognome);
						Studente nuovoStudente;
						
						if (risultato.length == 1) {
							scelta = 0;
							nuovoStudente = new Studente(risultato[0]);
							
							System.out.print("\nStudente trovato! Se non si desidera cambiare un certo campo, lasicarlo vuoto (premere direttamente invio).\n\n");
							
							String input = "";
							
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
									System.out.println(formatoInvalido);
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
									nuovoStudente.setClasseFrequentata(Classe.parse(sc.nextLine()));
									inputValido = true;
								} catch (FormatoClasseException exception) {
									System.out.println(formatoInvalido + " (Suggerimento: il numero massimo per l'anno della classe e' 127)");
									inputValido = false;
								}
							} while (!inputValido);
							
							System.out.print("\tInserire i nuovi anni di ripetizione (vecchi anni di ripetizione: \"" + (nuovoStudente.getAnniDiRipetizione() == 0 ? "null" : risultato[0].getAnniDiRipetizione()) + "\", se lo studente non e' mai stato bocciato inserire 0): ");
							do {
								try {
									input = sc.nextLine();
									if (input.isEmpty()) {
										break;
									}
									nuovoStudente.setAnniDiRipetizione(Byte.parseByte(input));
									inputValido = true;
								} catch (NumberFormatException exception) {
									System.out.println(formatoInvalido + " (Suggerimento: il numero massimo di anni e' 127)");
									inputValido = false;
								}
							} while (!inputValido);
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

							nuovoStudente = new Studente(risultato[scelta]);

							System.out.print("\nSe non si desidera cambiare un certo campo, lasicarlo vuoto (premere direttamente invio).\n\n");
							
							String input = "";
							
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
									System.out.println(formatoInvalido);
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
									nuovoStudente.setClasseFrequentata(Classe.parse(sc.nextLine()));
									inputValido = true;
								} catch (FormatoClasseException exception) {
									System.out.println(formatoInvalido + " (Suggerimento: il numero massimo per l'anno della classe e' 127)");
									inputValido = false;
								}
							} while (!inputValido);
							
							System.out.print("\tInserire i nuovi anni di ripetizione (vecchi anni di ripetizione: \"" + (nuovoStudente.getAnniDiRipetizione() == 0 ? "null" : risultato[0].getAnniDiRipetizione()) + "\", se lo studente non e' mai stato bocciato inserire 0): ");
							do {
								try {
									input = sc.nextLine();
									if (input.isEmpty()) {
										break;
									}
									nuovoStudente.setAnniDiRipetizione(Byte.parseByte(input));
									inputValido = true;
								} catch (NumberFormatException exception) {
									System.out.println(formatoInvalido + " (Suggerimento: il numero massimo di anni e' 127)");
									inputValido = false;
								}
							} while (!inputValido);
						}

						gestore.modificaStudente(risultato[scelta], nuovoStudente);
						System.out.println("\nInformazioni studente modificate con successo!");
						
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
						
						attendiInvio();
						cls();
					} catch (StudenteNonTrovatoException exception) {
						System.out.println("\nNessuno studente con tali credenziali trovato nella lista. ");
						attendiInvio();
						cls();
					} catch (FileNotFoundException exception) {
						System.out.println(erroreFile);
						attendiInvio();
						cls();
					} catch (IOException exception) {
						System.out.println(erroreScrittura);
						attendiInvio();
						cls();
					}

					break;
				}
				case 4: { // Elimina studente
					System.out.print(">Elimina studente\n\n");

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

						System.out.print("\nSi vuole davvero eliminarlo dalla lista? (si/no): ");

						do {
							switch (sc.nextLine()) {
								case "si":
									gestore.eliminaStudente(risultato[scelta]);
									System.out.println("\nLo studente e' stato rimosso dalla lista con successo.");
									
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

						attendiInvio();
						cls();
					} catch (StudenteNonTrovatoException exception) {
						System.out.print("\nNessuno studente con tali credenziali trovato nella lista. ");
						sc.nextLine();
						cls();
					} catch (FileNotFoundException exception) {
						System.out.println(erroreFile);
						attendiInvio();
						cls();
					} catch (IOException exception) {
						System.out.println(erroreScrittura);
						attendiInvio();
						cls();
					}
					
					break;
				}
				case 5: { // Visualizza lista studenti
					System.out.print(">Visualizza lista studenti\n\n");

					for (Studente studente : gestore.getListaStudenti()) {
						System.out.print("\n\t" + studente.toString() + "\n");
					}

					attendiInvio();
					cls();

					break;
				}
				case 9:
					chiudiProgramma = true;
					sc.close();
			}
		}
	}
}