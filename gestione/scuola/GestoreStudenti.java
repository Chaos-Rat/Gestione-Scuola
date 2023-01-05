/**
 * Classe GestoreStudente, permette di gestire una lista di studenti e di salvarla fisicamente attraverso <code>GestoreSalvataggio</code>
 * 
 * @version 1.2 (4-1-2023)
 * @author Adnaan Juma
 * @author Lorenzo Freccero
 * @see gestione.salvataggio.GestoreSalvataggio
 * @see gestione.scuola.Studente
 */

 package gestione.scuola;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import gestione.salvataggio.GestoreSalvataggio;

 public class GestoreStudenti {
	private ArrayList<Studente> listaStudenti;

	/**
	 * Costruttore di default, instanzia l'attributo listaStudenti con il suo costruttore
	 */
	public GestoreStudenti()
	{
		listaStudenti = new ArrayList<Studente>();
	}
	
	/**
	 * Costruttore di copia, inizializza l'attributo listaStudenti con il costruttore di copia di ArrayList su la lista studenti
	 * dell'altra istanza
	 * 
	 * @param altro istanza da cui dovra' essere effettuata la copia
	 */
	public GestoreStudenti(GestoreStudenti altro)
	{
		listaStudenti = new ArrayList<Studente>(altro.listaStudenti);
	}

	/**
	 * Ritorna una copia della lista studenti
	 * 
	 * @return copia della lista studenti
	 */
	public Studente[] getListaStudenti() {
		return listaStudenti.toArray(new Studente[0]);
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
		ArrayList<Studente> lista = new ArrayList<Studente>();

		for (Studente studente : listaStudenti) {
			if (studente.getNome().equals(nome) && studente.getCognome().equals(cognome)) {
				lista.add(new Studente(studente));
			}
		}

		if (lista.size() == 0) {
			throw new StudenteNonTrovatoException();
		}

		return lista.toArray(new Studente[0]);
	}
	
	/**
	 * Salva uno studente all'interno della lista facendo una copia
	 * 
	 * @param studente lo studente da salvare nella lista
	 * @throws StudenteGiaEsistenteException se lo studente e' gia' all'interno della lista degli studenti
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
	 * Ritorna la lista di tutti gli studenti di una certa classe
	 * 
	 * @param classe classe a cui devono appartenere gli studenti
	 * @return lista degli studenti appartenenti alla classe data da input
	 * @throws ClasseNonTrovataException se nessuno studente nella lista appartiene alla classe data da input
	 */
	public Studente[] getClasse(Classe classe) throws ClasseNonTrovataException
	{
		int n = 0;
		ArrayList<Studente> lista = new ArrayList<Studente>();
		
		while(n < listaStudenti.size()) {
			if(listaStudenti.get(n).getClasseFrequentata().equals(classe)) {
				lista.add(listaStudenti.get(n));
			}
			
			n++;
		}

		if (lista.size() == 0) {
			throw new ClasseNonTrovataException();
		}

		return lista.toArray(new Studente[0]);
	}
	
	/**
	 * Elimina un intera classe di studenti
	 * 
	 * @param classe classe a cui devono appartenere gli studenti
	 * @throws ClasseNonTrovataException se nessuno studente nella lista appartiene alla classe data da input
	 */
	public void eliminaClasse(Classe classe) throws ClasseNonTrovataException
	{
		int n = 0;
		int vecchiaLunghezza = listaStudenti.size();
		
		while(n < listaStudenti.size()) {
			if(listaStudenti.get(n).getClasseFrequentata().equals(classe)) {
				listaStudenti.remove(n);
			}
			
			n++;
		}

		if (vecchiaLunghezza == listaStudenti.size()) {
			throw new ClasseNonTrovataException();
		}
	}
	
	/**
	 * Fa avanzare di un anno la classe di tutti gli studenti della lista tranne quelli bocciati per l'anno, e se uno studente si trova in 5, lo rimuove dall'elenco direttamente
	 */
	public void promuoviStudenti()
	{
		int n = 0;
		
		while(n < listaStudenti.size()) {
			if(listaStudenti.get(n).getClasseFrequentata().getAnno() >= 5) {
				listaStudenti.remove(n);
			} else if (!listaStudenti.get(n).isBocciato()) { 
				Studente copia = new Studente(listaStudenti.get(n));
				copia.setClasseFrequentata(new Classe((byte)(copia.getClasseFrequentata().getAnno() + 1), copia.getClasseFrequentata().getSezione()));
				listaStudenti.set(n, copia);
			} else { // Studente bocciato
				Studente copia = new Studente(listaStudenti.get(n));
				copia.setBocciato(false); // Non ha avanzato l'anno, ma per il prossimo anno non sara' piu' in stato di bocciatura
				listaStudenti.set(n, copia);
			}

			n++;
		}
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
		GestoreSalvataggio.serializzaLista(listaStudenti, percorsoFileSalvataggio);
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
		listaStudenti = GestoreSalvataggio.<Studente>deserializzaLista(percorsoFileSalvataggio);
	}
}
