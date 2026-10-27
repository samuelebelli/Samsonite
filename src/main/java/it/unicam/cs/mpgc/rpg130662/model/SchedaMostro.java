package it.unicam.cs.mpgc.rpg130662.model;

/** Descrive un mostro del menu e ne crea un'istanza nuova a ogni scontro. */
public record SchedaMostro(String nome, int vita, int attacco) {

    public Mostro creaMostro() {
        return new Mostro(nome, vita, attacco);
    }
}

