package it.unicam.cs.mpgc.rpg130662.model;

/** Creazione del personaggio che sarà controllato dal giocatore */
public class Eroe {

    private final String nome;
    private final int puntiVitaMassimiGiocatore;
    private int puntiVitaGiocatore;
    private int attaccoEroe;
    private int bonusColpo;

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
        this.nome = nome;
        this.puntiVitaMassimiGiocatore = puntiVitaMassimiGiocatore;
        this.puntiVitaGiocatore = puntiVitaAttuali;
        this.attaccoEroe = attacco;
    }

    public String getNome() {
        return nome;
    }
    public int getPuntiVita() {
        return puntiVitaGiocatore;
    }

    public int getPuntiVitaMassimiGiocatore() {
        return puntiVitaMassimiGiocatore;
    }

    public int getAttaccoEroe() {
        return attaccoEroe;
    }

    public boolean isVivo() {
        return puntiVitaGiocatore > 0;
    }

    /** Il personaggio recupera punti vita, punti che non possono superare il massimo */

    public void cura(int quantita)
    {
        puntiVitaGiocatore = Math.min(puntiVitaMassimiGiocatore, puntiVitaGiocatore + quantita);
    }

    /** Riduce i punti vita, controllando che non si puo scendere sotto lo '0' */
    public void subisciDanno(int quantita) {
        puntiVitaGiocatore = Math.max(0, puntiVitaGiocatore - quantita);
    }

    /** Aumenta i punti attacco */
    public void aumentaAttacco(int bonus) {
        attaccoEroe += bonus;
    }

    /** Aggiunge un bonus che vale solo per il prossimo attacco. */
    public void caricaColpo(int bonus) {
        bonusColpo += bonus;
    }

    /** Restituisce il danno di questo attacco e consuma il bonus del colpo. */
    public int usaAttacco() {
        int totale = bonusColpo > 0 ? bonusColpo : attaccoEroe;
        bonusColpo = 0;
        return totale;
    }
}
