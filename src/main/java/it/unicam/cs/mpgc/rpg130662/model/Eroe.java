package it.unicam.cs.mpgc.rpg130662.model;

/** Creazione del personaggio che sarà controllato dal giocatore */
public class Eroe {

    private final String Nome;
    private final int PuntiVitaMassimiGiocatore;
    private int PuntiVitaGiocatore;
    private int attacco;

    public Eroe(String nome, int puntiVitaMassimiGiocatore, int attacco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (puntiVitaMassimiGiocatore <= 0) {
            throw new IllegalArgumentException("I punti vita massimi devono essere positivi");
        }
        this.Nome = nome;
        this.PuntiVitaMassimiGiocatore = puntiVitaMassimiGiocatore;
        this.PuntiVitaGiocatore = puntiVitaMassimiGiocatore;
        this.attacco = attacco;
    }

    public String getNome() {
        return Nome;
    }
    public int getPuntiVita() {
        return PuntiVitaGiocatore;
    }

    public int getPuntiVitaMassimiGiocatore() {
        return PuntiVitaMassimiGiocatore;
    }

    public int getAttacco() {
        return attacco;
    }

    public boolean isVivo() {
        return PuntiVitaGiocatore > 0;
    }

    /** Il personaggio recupera punti vita, punti che non possono superare il massimo */

    public void cura(int quantita)
    {
        PuntiVitaGiocatore = Math.min(PuntiVitaGiocatore, PuntiVitaGiocatore + quantita);
    }

    /** Riduce i punti vita, controllando che non si puo scendere sotto lo '0' */
    public void subisciDanno(int quantita) {
        PuntiVitaGiocatore = Math.max(0, PuntiVitaGiocatore - quantita);
    }

    public void aumentaAttacco(int bonus) {
        attacco += bonus;
    }
}
