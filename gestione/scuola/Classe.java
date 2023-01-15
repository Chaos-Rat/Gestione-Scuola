/**
 * Classe Classe, immagazzina anno e sezione di una classe di un'istituto scolastico.
 * 
 * @version 1.1 (6-1-2023)
 * @author Adnaan Juma
 */

package gestione.scuola;

import java.io.Serializable;

public class Classe implements Serializable {
	private byte anno;
	private String sezione;
	private static final long serialVersionUID = -5707193536720403455L;

	/**
	 * Costruttore che inizializza gli attributi con i valori di input
	 * 
	 * @param anno valore da assegnare all'anno
	 * @param sezione valore da assegnare alla sezione
	 */
	public Classe(byte anno, String sezione)
	{
		this.anno = anno;
		this.sezione = sezione;
	}

	/**
	 * Costruttore di copia, inizializza gli attributi ai valore degli stessi di un'altra istanza
	 * 
	 * @param altro istanza da cui verranno copiati gli attributi
	 */
	public Classe(Classe altro)
	{
		anno = altro.anno;
		sezione = altro.sezione;
	}

	/**
	 * Getter per l'attributo anno
	 * 
	 * @return valore dell'attributo anno
	 */
	public byte getAnno()
	{
		return anno;
	}

	/**
	 * Setter per l'attributo anno
	 * 
	 * @param anno valore da assegnare all'attributo anno
	 */
	public void setAnno(byte anno)
	{
		this.anno = anno;
	}

	/**
	 * Getter per l'attributo sezione
	 * 
	 * @return valore dell'attributo sezione
	 */
	public String getSezione()
	{
		return sezione;
	}

	/**
	 * Setter per l'attributo sezione
	 * 
	 * @param anno valore da assegnare all'attributo sezione
	 */
	public void setSezione(String sezione)
	{
		this.sezione = sezione;
	}

	/**
	 * Converte questo oggetto in formato <code>String</code>
	 * 
	 * @return una rappresentazione di questo oggetto in <code>String</code>
	 */
	@Override
	public String toString()
	{
		return anno + sezione; // EX: 4BIF
	}

	/**
	 * Ritorna se questa istanza di <code>Classe</code> e' uguale ad un'altro oggetto inserito da input
	 * 
	 * @param oggetto oggetto da comparare a questa
	 * @return <code>true</code> se l'oggetto rappresenta una instanza <code>Classe</code> equivalente a questa, <code>false</code> in caso contrario
	 */
	@Override
	public boolean equals(Object oggetto)
	{
		if (this == oggetto) {
			return true;
		}
		if (oggetto instanceof Classe) {
			return anno == ((Classe)oggetto).anno && sezione.equals(((Classe)oggetto).sezione);
		}
		return false;
	}

	/**
	 * Ritorna se questa istanza di <code>Classe</code> e' uguale ad un'altro oggetto inserito da input, ignorando maiuscole/minuscole per la sezione
	 * 
	 * @param oggetto oggetto da comparare a questa
	 * @return <code>true</code> se l'oggetto rappresenta una instanza <code>Classe</code> equivalente a questa, avendo ignorato il casing della sezione, <code>false</code> in caso contrario
	 */
	public boolean equalsIgnoreCase(Object oggetto)
	{
		if (this == oggetto) {
			return true;
		}
		if (oggetto instanceof Classe) {
			return anno == ((Classe)oggetto).anno && sezione.equalsIgnoreCase(((Classe)oggetto).sezione);
		}
		return false;
	}
	
	/**
	 * Ottiene un'istanza di <code>Classe</code> da una stringa del tipo <code>3CIF</code>
	 * 
	 * @param stringa stringa su cui fare il parse nel formato: [<i>numero da 1 a 127, estremi inclusi</i>][<i>stringa di caratteri dell'alfabeto maiuscoli e minuscoli</i>]
	 * esempi: 4BIF, 1C, 2iF, 2lm
	 * @return l'istanza costruita a partire dalla stringa 
	 * @throws FormatoClasseException se la stringa inserita non puo' essere convertita in un oggetto <code>Classe</code>
	 */
	public static Classe parse(String stringa) throws FormatoClasseException
	{
		String stringaNumerica = "";
		byte parteNumerica = 0;
		
		String stringaLetterale = "";
		
		int i = 0;

		if (stringa == null) {
			throw new FormatoClasseException();
		}

		/* Parse dell'anno */
		for (; i < stringa.length() && (stringa.charAt(i) >= '0' && stringa.charAt(i) <= '9'); i++) {
			stringaNumerica += stringa.charAt(i);
		}

		try {
			parteNumerica = Byte.parseByte(stringaNumerica);
		} catch (NumberFormatException exception) {
			throw new FormatoClasseException();
		}

		if (parteNumerica == 0) { // La classe 0BIF non puo' esistere
			throw new FormatoClasseException();
		}

		/* Parse della sezione */

		for (; i < stringa.length(); i++) {
			if (!(stringa.charAt(i) >= 'A' && stringa.charAt(i) <= 'Z' || stringa.charAt(i) >= 'a' && stringa.charAt(i) <= 'z')) {
				throw new FormatoClasseException();
			}

			stringaLetterale += stringa.charAt(i);
		}

		if (stringaLetterale.length() == 0) {
			throw new FormatoClasseException();
		}

		return new Classe(parteNumerica, stringaLetterale);
	}
}