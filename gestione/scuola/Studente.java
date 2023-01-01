/**
 * Classe Studente, avente lo scopo di immagazzinare i dati di base di uno studente tra cui:
 * nome, cognome, data di nascita, luogo di nascita, classe frequentata e anni di ripetizione.
 * Implementa la classe <b>Serializable</b> ed e' quindi possibile serializzarla.
 * 
 * @version 1.0 (1-1-2023)
 * @author Adnaan Juma
 */
package gestione.scuola;

import java.io.Serializable;
import java.time.LocalDate;

public class Studente implements Serializable {
	private String nome, cognome;
	private LocalDate dataDiNascita;
	private String luogoDiNascita;
	private byte classeFrequentata, anniDiRipetizione;
	private static final long serialVersionUID = -4459309551349666457L;
	
	/**
	 * Costruttore con il compito di iniazializzare tutte gli attributi con i valori inseriti da input
	 * 
	 * @param nome nome dello studente
	 * @param cognome cognome dello studente
	 * @param dataDiNascita data di nascita dello studente
	 * @param luogoDiNascita luogo di nascita dello studente
	 * @param classeFrequentata classe frequentata dallo studente
	 * @param anniDiRipetizione anni di ripetizione dello studente nel caso in cui sia stato bocciato. In caso non sia mai stato bocciato ha valore <i>null</i>
	 */
	public Studente(String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, byte classeFrequentata, byte anniDiRipetizione)
	{
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.luogoDiNascita = luogoDiNascita;
		this.classeFrequentata = classeFrequentata;
		this.anniDiRipetizione = anniDiRipetizione;
	}

	/**
	 * Costruttore di copia, inizializza gli attributi con i valori degli stessi di un'altra istanza
	 * 
	 * @param altro instanza da cui dovra' essere effettuata la copia
	 */
	public Studente(Studente altro)
	{
		nome = altro.nome;
		cognome = altro.cognome;
		dataDiNascita = altro.dataDiNascita;
		luogoDiNascita = altro.luogoDiNascita;
		classeFrequentata = altro.classeFrequentata;
		anniDiRipetizione = altro.anniDiRipetizione;
	}
}