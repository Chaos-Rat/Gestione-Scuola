/**
 * Classe Studente, avente lo scopo di immagazzinare i dati di base di uno studente tra cui:
 * nome, cognome, data di nascita, luogo di nascita, classe frequentata e anni di ripetizione.
 * Implementa la classe <b>Serializable</b> ed e' quindi possibile serializzarla.
 * 
 * @version 1.5 (5-1-2023)
 * @author Adnaan Juma
 * @author Lorenzo Freccero
 */
package gestione.scuola;

import java.io.Serializable;
import java.time.LocalDate;

public class Studente implements Serializable {
	private String nome, cognome;
	private LocalDate dataDiNascita;
	private String luogoDiNascita;
	private Classe classeFrequentata;
	private byte anniDiRipetizione;
	private boolean bocciato;
	private static final long serialVersionUID = 2480852755366942996L;
	
	/**
	 * Costruttore di default, inizializza tutti gli interi a zero, le stringe a stringhe vuote, la data a null, e bocciato a falso
	 */
	public Studente()
	{
		nome = "";
		cognome = "";
		dataDiNascita = null;
		luogoDiNascita = "";
		classeFrequentata = new Classe((byte)0, "");
		anniDiRipetizione = 0;
		bocciato = false;
	}

	/**
	 * Costruttore con il compito di inizializzare tutte gli attributi con i valori inseriti da input
	 * 
	 * @param nome nome dello studente
	 * @param cognome cognome dello studente
	 * @param dataDiNascita data di nascita dello studente
	 * @param luogoDiNascita luogo di nascita dello studente
	 * @param classeFrequentata classe frequentata dallo studente
	 * @param anniDiRipetizione anni di ripetizione dello studente nel caso in cui sia stato bocciato. In caso non sia mai stato bocciato ha valore <i>null</i>
	 * @param bocciato se lo studente e' bocciato per quest'anno o no
	 */
	public Studente(String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, Classe classeFrequentata, byte anniDiRipetizione, boolean bocciato)
	{
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.luogoDiNascita = luogoDiNascita;
		this.classeFrequentata = new Classe(classeFrequentata);
		this.anniDiRipetizione = anniDiRipetizione;
		this.bocciato = bocciato;
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
		classeFrequentata = new Classe(altro.classeFrequentata);
		anniDiRipetizione = altro.anniDiRipetizione;
		bocciato = altro.bocciato;
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
	public Classe getClasseFrequentata()
	{
		return new Classe(classeFrequentata);
	}

	/**
	 * Setter per l'attributo classeFrequentata
	 * 
	 * @param nome valore da assegnare all'attributo classeFrequentata
	 */
	public void setClasseFrequentata(Classe classeFrequentata)
	{
		this.classeFrequentata = new Classe(classeFrequentata);
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
	
	/**
	 * Getter per l'attributo bocciato
	 * 
	 * @return valore attributo bocciato
	 */
	public boolean isBocciato()
	{
		return bocciato;
	}
	
	/**
	 * Setter per l'attributo bocciato
	 * 
	 * @param nome valore da assegnare all'attributo bocciato
	 */
	public void setBocciato(boolean bocciato)
	{
		this.bocciato = bocciato;
	}

	/**
     * Converte questo oggetto in formato <code>String</code>
     * 
     * @return una rappresentazione di questo oggetto in <code>String</code>
     */
	@Override
	public String toString()
	{
		return "nome = \"" + nome + "\", cognome = \"" + cognome + "\", data di nascita = " + (dataDiNascita == null ? "null" : dataDiNascita.toString())
			+ ", luogo di nascita = \"" + luogoDiNascita + "\", classe frequentata = " + classeFrequentata.toString()
			+ ", anni di ripetizione = " + (anniDiRipetizione == 0 ? "null" : anniDiRipetizione)
			+ ", lo studente " + (bocciato ? "" : "non") + " verra' bocciato quest'anno";
	}

	/**
     * Ritorna se questa istanza di <code>Studente</code> e' uguale ad un'altro oggetto inserito da input
     * 
     * @param oggetto oggetto da comparare a questa
     * @return <code>true</code> se l'oggetto rappresenta una istanza <code>Studente</code> equivalente a questa, <code>false</code> in caso contrario
     */
	@Override
	public boolean equals(Object oggetto)
	{
		if (this == oggetto) {
            return true;
        }
        if (oggetto instanceof Studente) {
            return nome.equals(((Studente)oggetto).nome) && cognome.equals(((Studente)oggetto).cognome) && dataDiNascita.equals(((Studente)oggetto).dataDiNascita)
				&& luogoDiNascita.equals(((Studente)oggetto).luogoDiNascita) && classeFrequentata.equals(((Studente)oggetto).classeFrequentata) 
				&& anniDiRipetizione == ((Studente)oggetto).anniDiRipetizione && bocciato == ((Studente)oggetto).bocciato;
        }
        return false;
	}
}