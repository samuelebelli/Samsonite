package it.unicam.cs.mpgc.rpg130662.model;

public class Mostro {

    private final String nomeMostro;
    private int puntiVitaMostro;
    private final int attaccoMostro;

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
        this.nomeMostro = nome;
        this.puntiVitaMostro = vita;
        this.attaccoMostro = attacco;
    }

    public String getNome() {
        return nomeMostro;
    }

    public int getPuntiVitaMostro() {
        return puntiVitaMostro;
    }

    public int getAttaccoMostro() {
        return attaccoMostro;
    }

    public boolean isVivo() {
        return puntiVitaMostro > 0;
    }

    /** Riduce la vita del mostro, senza scendere sotto zero. */
    public void subisciDanno(int danno) {
        if (danno < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        puntiVitaMostro = Math.max(0, puntiVitaMostro - danno);
    }
}
