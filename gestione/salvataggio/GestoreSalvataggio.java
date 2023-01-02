/**
 * Classe GestoreSalvataggio, fornisce dei metodi per la serializzazione e deserializzazione degli oggetti,
 * attualmente solo per le ArrayList.
 * 
 * @version 1.1 (2-1-2023)
 * @author Adnaan Juma
 */

package gestione.salvataggio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GestoreSalvataggio {
    /**
     * Serializza la lista data in input su un file nel percorso specificato da input.
     * 
     * @param <T> Tipo degli elementi contenuti dalla lista. <b>ATTENZIONE</b>: Esso deve essere serializzabile o verra tirata un'eccezione!
     * @param lista Lista da serializzare
     * @param percorsoFile Percorso del file di output della serializzazione, in caso non esista gia' verra creato automaticamente
     * @throws FileNotFoundException se: <ul><li>il file corrisponde a una cartella e non a un file vero e proprio</li>
     * <li>non esiste e non e' possibile crearlo</li>
     * <li>e' impossibile aprirlo per qualsiasi altra ragione</li></ul>
     * @throws IOException se un errore di IO (Input/Output) occore in fase di scrittura dell'header dello stream
     */
    public <T> void serializzaLista(ArrayList<T> lista, String percorsoFile) throws FileNotFoundException, IOException
    {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(percorsoFile));
        stream.writeObject(lista);
        stream.close();
    }

    /**
     * Deserializza la lista contenuta sul file specificato dal percorso di input
     * 
     * @param <T> Tipo degli elementi contenuti dalla lista.
     * @param percorsoFile Percorso del file di input della deserializzazione
     * @return Lista deserializzata
     * @throws FileNotFoundException se: <ul><li>il file non esiste</li>
     * <li>il file corrisponde a una cartella e non a un file vero e proprio</li>
     * <li>e' impossibile aprirlo per qualsiasi altra ragione</li></ul>
     * @throws IOException se un errore di IO (Input/Output) occore in fase di lettura dell'header dello stream
     * @throws ClassNotFoundException se non e' stato possibile trovare la lista all'interno del file
     */
    public <T> ArrayList<T> deserializzaLista(String percorsoFile) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(percorsoFile));
        @SuppressWarnings("unchecked")
        ArrayList<T> lista = (ArrayList<T>)(stream.readObject());
        stream.close();
        return lista;
    }
}
