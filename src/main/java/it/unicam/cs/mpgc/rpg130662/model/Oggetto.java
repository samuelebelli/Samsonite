package it.unicam.cs.mpgc.rpg130662.model;

/**
 * Classe che permette di andare a implementare
 * l'oggetto che l'eroe può raccogliere e usare
 */
public interface Oggetto {

    String getNome();

    String getDescrizione();

    /** Applica l'effetto dell'oggetto all'eroe che lo usa. */
    void usa(Eroe eroe);

    /** Tipo dell'oggetto nel file di salvataggio (es. "POZIONE"). */
    String getTipo();

    /** Valore numerico dell'oggetto (0 se non ne ha uno). */
    int getValore();
}
