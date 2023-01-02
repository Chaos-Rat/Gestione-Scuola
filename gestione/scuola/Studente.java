/**
 * Classe Studente, avente lo scopo di immagazzinare i dati di base di uno studente tra cui:
 * nome, cognome, data di nascita, luogo di nascita, classe frequentata e anni di ripetizione.
 * Implementa la classe <b>Serializable</b> ed e' quindi possibile serializzarla.
 * 
 * @version 1.1 (2-1-2023)
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
	private static final long serialVersionUID = -1880554790296497074L;
	
	
	/**
	 * Costruttore con il compito di inizializzare tutte gli attributi con i valori inseriti da input
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

	/**
	 * Getter per l'attributo nome
	 * 
	 * @return valore attributo nome
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * Setter per l'attributo nome
	 * 
	 * @param nome valore da assegnare all'attributo nome
	 */
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	/**
	 * Getter per l'attributo cognome
	 * 
	 * @return valore attributo cognome
	 */
	public String getCognome()
	{
		return cognome;
	}

	/**
	 * Setter per l'attributo cognome
	 * 
	 * @param nome valore da assegnare all'attributo cognome
	 */
	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}

	/**
	 * Getter per l'attributo dataDiNascita
	 * 
	 * @return valore attributo dataDiNascita
	 */
	public LocalDate getDataDiNascita()
	{
		return dataDiNascita;
	}

	/**
	 * Setter per l'attributo dataDiNascita
	 * 
	 * @param nome valore da assegnare all'attributo dataDiNascita
	 */
	public void setDataDiNascita(LocalDate dataDiNascita)
	{
		this.dataDiNascita = dataDiNascita;
	}

	/**
	 * Getter per l'attributo luogoDiNascita
	 * 
	 * @return valore attributo luogoDiNascita
	 */
	public String getLuogoDiNascita()
	{
		return luogoDiNascita;
	}

	/**
	 * Setter per l'attributo luogoDiNascita
	 * 
	 * @param nome valore da assegnare all'attributo luogoDiNascita
	 */
	public void setLuogoDiNascita(String luogoDiNascita)
	{
		this.luogoDiNascita = luogoDiNascita;
	}

	/**
	 * Getter per l'attributo classeFrequentata
	 * 
	 * @return valore attributo classeFrequentata
	 */
	public byte getClasseFrequentata()
	{
		return classeFrequentata;
	}

	/**
	 * Setter per l'attributo classeFrequentata
	 * 
	 * @param nome valore da assegnare all'attributo classeFrequentata
	 */
	public void setClasseFrequentata(byte classeFrequentata)
	{
		this.classeFrequentata = classeFrequentata;
	}

	/**
	 * Getter per l'attributo anniDiRipetizione
	 * 
	 * @return valore attributo anniDiRipetizione
	 */
	public byte getAnniDiRipetizione()
	{
		return anniDiRipetizione;
	}

	/**
	 * Setter per l'attributo anniDiRipetizione
	 * 
	 * @param nome valore da assegnare all'attributo anniDiRipetizione
	 */
	public void setAnniDiRipetizione(byte anniDiRipetizione)
	{
		this.anniDiRipetizione = anniDiRipetizione;
	}

	@Override
	public String toString()
	{
		return "Studente [nome = \"" + nome + "\", cognome = \"" + cognome + "\", dataDiNascita = " + dataDiNascita.toString()
				+ "\n, luogoDiNascita = \"" + luogoDiNascita + "\", classeFrequentata = " + classeFrequentata
				+ ", anniDiRipetizione = " + anniDiRipetizione + "]";
	}	
}