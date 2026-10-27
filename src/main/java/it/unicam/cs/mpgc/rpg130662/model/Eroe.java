package it.unicam.cs.mpgc.rpg130662.model;

/** Creazione del personaggio che sarà controllato dal giocatore */
public class Eroe {

    private final String Nome;
    private final int PuntiVitaMassimiGiocatore;
    private int PuntiVitaGiocatore;
    private int AttaccoEroe;

    /** Crea un eroe con la vita al massimo. */
    public Eroe(String nome, int puntiVitaMassimiGiocatore, int attacco) {
        this(nome, puntiVitaMassimiGiocatore, puntiVitaMassimiGiocatore, attacco);
    }

    /** Crea un eroe in uno stato preciso, usato per ricaricare una partita salvata. */
    public Eroe(String nome, int puntiVitaMassimiGiocatore, int puntiVitaAttuali, int attacco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (puntiVitaMassimiGiocatore <= 0) {
            throw new IllegalArgumentException("I punti vita massimi devono essere positivi");
        }
        if (puntiVitaAttuali < 0 || puntiVitaAttuali > puntiVitaMassimiGiocatore) {
            throw new IllegalArgumentException("I punti vita attuali non sono validi");
        }
        this.Nome = nome;
        this.PuntiVitaMassimiGiocatore = puntiVitaMassimiGiocatore;
        this.PuntiVitaGiocatore = puntiVitaAttuali;
        this.AttaccoEroe = attacco;
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

    public int getAttaccoEroe() {
        return AttaccoEroe;
    }

    public boolean isVivo() {
        return PuntiVitaGiocatore > 0;
    }

    /** Il personaggio recupera punti vita, punti che non possono superare il massimo */

    public void cura(int quantita)
    {
        PuntiVitaGiocatore = Math.min(PuntiVitaMassimiGiocatore, PuntiVitaGiocatore + quantita);
    }

    /** Riduce i punti vita, controllando che non si puo scendere sotto lo '0' */
    public void subisciDanno(int quantita) {
        PuntiVitaGiocatore = Math.max(0, PuntiVitaGiocatore - quantita);
    }

    /** Aumenta i punti attacco */
    public void aumentaAttacco(int bonus) {
        AttaccoEroe += bonus;
    }

}
