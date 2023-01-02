package gestione.scuola;

import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import gestione.salvataggio.GestoreSalvataggio;

public class GestoreStudenti {
	
	private ArrayList<Studente> listaStudenti;
	private GestoreSalvataggio gestoreSalvataggio;
	private String percorsoFileSalvataggio;
	
	public GestoreStudenti(String percorsoFileSalvataggio)
	{
		listaStudenti = new ArrayList<Studente>();
		gestoreSalvataggio = new GestoreSalvataggio();
		this.percorsoFileSalvataggio = percorsoFileSalvataggio;
	}
	
	public GestoreStudenti(GestoreStudenti altro)
	{
		listaStudenti = new ArrayList<Studente>(altro.listaStudenti);
		gestoreSalvataggio = new GestoreSalvataggio();
		percorsoFileSalvataggio = altro.percorsoFileSalvataggio;
	}
	
	public String getPercorsoFileSalvataggio() {
		return percorsoFileSalvataggio;
	}

	public void setPercorsoFileSalvataggio(String percorsoFileSalvataggio) {
		this.percorsoFileSalvataggio = percorsoFileSalvataggio;
	}
	
	public void salvaStudente(Studente studente)
	{
		listaStudenti.add(studente);
	}
	
	public ArrayList<Studente> ricercaStudente(String nome, String cognome)
	{
		ArrayList<Studente> lista = new ArrayList<Studente>();
		for(Studente studente : listaStudenti) {
			if(studente.getNome().equals(nome) && studente.getCognome().equals(cognome)){
				lista.add(new Studente(studente));
			}
		}
		
		return lista;
	}
	
	public void modificaStudente(String nome, String cognome)
	{
		/*int n = 0;
		
		while(n < listaStudenti.size()){
			if(listaStudenti.get(n).getNome() == nome && listaStudenti.get(n).getCognome() == cognome){
				
			}
		}*/
		
		ArrayList<Studente> lista = new ArrayList<Studente>();
		for(Studente studente : listaStudenti) {
			if(studente.getNome().equals(nome) && studente.getCognome().equals(cognome)){
				lista.add(new Studente(studente));
			}
		}
	}
	
	
	
	public void eliminaStudente(String nome, String cognome)
	{
		
	}
	
	public void salvaListaStudenti()
	{
		try {
			gestoreSalvataggio.serializzaLista(listaStudenti, percorsoFileSalvataggio);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void caricaListaStudenti()
	{
		try {
			listaStudenti = gestoreSalvataggio.<Studente> deserializzaLista(percorsoFileSalvataggio);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
