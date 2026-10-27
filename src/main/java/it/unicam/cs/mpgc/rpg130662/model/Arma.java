package it.unicam.cs.mpgc.rpg130662.model;

public class Arma implements Oggetto {

    private final String Nome;
    private final int BonusAttacco;

    /** Costruttore dell'oggetto arma usata dall'eroe */

    public Arma(String nome, int bonusattacco){
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (bonusattacco <= 0){
            throw new IllegalArgumentException("Il bonus di attacco deve essere positivo");
        }
        this.Nome = nome;
        this.BonusAttacco = bonusattacco;
        }

        @Override
        public String getNome() {
            return "Aumenta l'attacco di " + BonusAttacco;
        }

        @Override
        public String getDescrizione() {
            return "Aumenta l'attacco di " + BonusAttacco;
        }

        @Override
        public void usa(Eroe eroe) {
            eroe.aumentaAttacco(BonusAttacco);
        }
    }
