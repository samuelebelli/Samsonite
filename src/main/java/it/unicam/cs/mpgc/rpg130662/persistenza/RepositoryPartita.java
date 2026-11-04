package it.unicam.cs.mpgc.rpg130662.persistenza;

import it.unicam.cs.mpgc.rpg130662.model.Eroe;
import it.unicam.cs.mpgc.rpg130662.model.Inventario;

import java.io.IOException;

/** Salva e carica lo stato di una partita, senza dire in che formato. */
public interface RepositoryPartita {

    void salva(Eroe eroe, Inventario inventario) throws IOException;

    StatoPartita carica() throws IOException;
}