package it.unicam.cs.mpgc.rpg130662.model;

public class Mostro {

    private final String NomeMostro;
    private int PuntiVitaMostro;
    private final int AttaccoMostro;

    public Mostro(String nome, int vita, int attacco){
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (vita <= 0) {
            throw new IllegalArgumentException("La vita deve essere positiva");
        }
        if (attacco < 0) {
            throw new IllegalArgumentException("L'attacco non può essere negativo");
        }
        this.NomeMostro = nome;
        this.PuntiVitaMostro = vita;
        this.AttaccoMostro = attacco;
    }

    public String getNome() {
        return NomeMostro;
    }

    public int getPuntiVitaMostro() {
        return PuntiVitaMostro;
    }

    public int getAttaccoMostro() {
        return AttaccoMostro;
    }

    public boolean isVivo() {
        return PuntiVitaMostro > 0;
    }

    /** Riduce la vita del mostro, senza scendere sotto zero. */
    public void subisciDanno(int danno) {
        if (danno < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        PuntiVitaMostro = Math.max(0, PuntiVitaMostro - danno);
    }
}
