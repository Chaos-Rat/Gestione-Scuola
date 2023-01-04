/**
 * Classe GestoreStudente, permette di gestire una lista di studenti e di salvarla fisicamente attraverso un gestore per il salvataggio
 * 
 * @version 1.2 (4-1-2023)
 * @author Lorenzo Freccero
 * @author Adnaan Juma
 */

package gestione.scuola;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import gestione.salvataggio.GestoreSalvataggio;

public class GestoreStudenti {
	private ArrayList<Studente> listaStudenti;
	private GestoreSalvataggio gestoreSalvataggio;

	/**
	 * Costruttore di default, instanzia gli attributi listaStudenti e gestoreSalvataggio chiamando i loro costruttori
	 */
	public GestoreStudenti()
	{
		listaStudenti = new ArrayList<Studente>();
		gestoreSalvataggio = new GestoreSalvataggio();
	}
	
	/**
	 * Costruttore di copia, inizializza l'attributo listaStudenti con il costruttore di copia di ArrayList su la lista studenti
	 * dell'altra istanza, mentre inizializza gestoreSalvataggio chiamando il suo costruttore
	 * 
	 * @param altro istanza da cui dovra' essere effettuata la copia
	 */
	public GestoreStudenti(GestoreStudenti altro)
	{
		listaStudenti = new ArrayList<Studente>(altro.listaStudenti);
		gestoreSalvataggio = new GestoreSalvataggio();
	}

	/**
	 * Ritorna una copia della lista studenti
	 * 
	 * @return copia della lista studenti
	 */
	public ArrayList<Studente> getListaStudenti() {
		return new ArrayList<Studente>(listaStudenti);
	}

	/**
	 * Cerca gli studenti all'interno della lista che hanno tale nome e cognome li ritorna
	 * 
	 * @param nome nome dello/degli studente/studenti da cercare
	 * @param cognome cognome dello/degli studente/studenti da cercare
	 * @return array con le copie delle istanze degli studenti trovate
	 * @throws StudenteNonTrovatoException nessuno studente possiede tale nome e cognome
	 */
	public Studente[] cercaStudente(String nome, String cognome) throws StudenteNonTrovatoException
	{
		ArrayList<Studente> temp = new ArrayList<Studente>();

		for (Studente studente : listaStudenti) {
			if (studente.getNome().equals(nome) && studente.getCognome().equals(cognome)) {
				temp.add(new Studente(studente));
			}
		}

		if (temp.size() == 0) {
			throw new StudenteNonTrovatoException();
		}

		return temp.toArray(new Studente[0]);
	}
	
	/**
	 * Salva uno studente all'interno della lista facendo una copia
	 * 
	 * @param studente lo studente da salvare nella lista
	 * @throws StudenteGiaEsitenteException se lo studente e' gia' all'interno della lista degli studenti
	 */
	public void salvaStudente(Studente studente) throws StudenteGiaEsistenteException
	{
		for (Studente st : listaStudenti) {
			if (st.equals(studente)) {
				throw new StudenteGiaEsistenteException();
			}
		}

		listaStudenti.add(new Studente(studente));
	}
	
	/**
	 * Cerca lo studente dentro la lista che ha gli stessi attributi di quello passato da input,
	 * e poi li sovrascrive con quelli dell'altro studente passato da input
	 * 
	 * @param vecchioStudente studente da ricercare all'interno della lista
	 * @param nuovoStudente studente da sovrascrivere a quello trovato
	 * @throws StudenteNonTrovatoException se vecchioStudente non e' stato trovato nella lista degli studenti
	 */
	public void modificaStudente(Studente vecchioStudente, Studente nuovoStudente) throws StudenteNonTrovatoException
	{	
		for (int i = 0; i < listaStudenti.size(); i++) {
			if (vecchioStudente.equals(listaStudenti.get(i))) {
				listaStudenti.set(i, new Studente(nuovoStudente));
				return;
			}
		}

		throw new StudenteNonTrovatoException();
	}
	
	/**
	 * Cerca lo studente dentro la lista che ha gli stessi attributi di quello passato da input e lo elimina da essa
	 * 
	 * @param studente studente da ricercare all'interno della lista
	 * @throws StudenteNonTrovatoException se lo studente non e' stato trovato nella lista degli studenti
	 */
	public void eliminaStudente(Studente studente) throws StudenteNonTrovatoException
	{
		for (int i = 0; i < listaStudenti.size(); i++) {
			if (studente.equals(listaStudenti.get(i))) {
				listaStudenti.remove(i);
				return;
			}
		}

		throw new StudenteNonTrovatoException();
	}
	
	/**
	 * Salva la lista studenti serializzandola con il gestore salvataggio
	 * 
	 * @param percorsoFileSalvataggio percorso del file su cui verra' serializzata la lista
	 * @throws FileNotFoundException se: <ul><li>il file corrisponde a una cartella e non a un file vero e proprio</li>
	 * <li>non esiste e non e' possibile crearlo</li>
	 * <li>e' impossibile aprirlo per qualsiasi altra ragione</li></ul>
	 * @throws IOException se un errore di IO (Input/Output) occore in fase di scrittura dell'header dello stream
	 */
	public void salvaListaStudenti(String percorsoFileSalvataggio) throws FileNotFoundException, IOException
	{
		gestoreSalvataggio.serializzaLista(listaStudenti, percorsoFileSalvataggio);
	}
	
	/**
	 * Carica la lista degli studenti deserializzandola con il gestore salvataggio
	 * 
	 * @param percorsoFileSalvataggio percorso del file da cui verra' deserializzata la lista
	 * @throws FileNotFoundException se: <ul><li>il file non esiste</li>
	 * <li>il file corrisponde a una cartella e non a un file vero e proprio</li>
	 * <li>e' impossibile aprirlo per qualsiasi altra ragione</li></ul>
	 * @throws IOException se un errore di IO (Input/Output) occore in fase di lettura dell'header dello stream
	 * @throws ClassNotFoundException se non e' stato possibile trovare la lista all'interno del file
	 */
	public void caricaListaStudenti(String percorsoFileSalvataggio) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		listaStudenti = gestoreSalvataggio.<Studente>deserializzaLista(percorsoFileSalvataggio);
	}
}
