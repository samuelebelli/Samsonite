package it.unicam.cs.mpgc.rpg130662.persistenza;

import it.unicam.cs.mpgc.rpg130662.model.Eroe;
import it.unicam.cs.mpgc.rpg130662.model.Inventario;

/** I dati ricostruiti da un salvataggio. */
public record StatoPartita(Eroe eroe, Inventario inventario) {
}