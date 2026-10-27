package it.unicam.cs.mpgc.rpg130662.model;

public class Mostro {

    private final String Nome;
    private int Vita;
    private final int Attacco;

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
        this.Nome = nome;
        this.Vita = vita;
        this.Attacco = attacco;
    }

    public String getNome() {
        return Nome;
    }

    public int getVita() {
        return Vita;
    }

    public int getAttacco() {
        return Attacco;
    }

    public boolean isVivo() {
        return Vita > 0;
    }

    /** Riduce la vita del mostro, senza scendere sotto zero. */
    public void subisciDanno(int danno) {
        if (danno < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        Vita = Math.max(0, Vita - danno);
    }
}
