package it.unicam.cs.mpgc.rpg130662.controller;

import it.unicam.cs.mpgc.rpg130662.model.Arma;
import it.unicam.cs.mpgc.rpg130662.model.ArmaMonouso;
import it.unicam.cs.mpgc.rpg130662.model.Eroe;
import it.unicam.cs.mpgc.rpg130662.model.Gioco;
import it.unicam.cs.mpgc.rpg130662.model.Inventario;
import it.unicam.cs.mpgc.rpg130662.model.Pozione;
import it.unicam.cs.mpgc.rpg130662.model.PozioneTotale;
import it.unicam.cs.mpgc.rpg130662.model.SchedaMostro;

import java.util.List;

/** Definisce come inizia una partita: eroe, oggetti di partenza e mostri disponibili. */
public final class PartitaIniziale {

    private PartitaIniziale() {
    }

    /** I mostri tra cui il giocatore può scegliere. */
    public static List<SchedaMostro> mostri() {
        return List.of(new SchedaMostro("Goblin", 30, 8),
                new SchedaMostro("Orco", 60, 15));
    }

    /** Crea una partita nuova per l'eroe con il nome indicato. */
    public static Gioco crea(String nomeEroe) {
        Inventario inventario = new Inventario(5);
        inventario.aggiungi(new Pozione("Pozione piccola", 20));
        inventario.aggiungi(new Arma("Spada di ferro", 5));
        inventario.aggiungi(new PozioneTotale("Elisir completo"));
        inventario.aggiungi(new ArmaMonouso("Spada di diamante", 30));
        return new Gioco(new Eroe(nomeEroe, 100, 10), inventario, mostri());
    }
}