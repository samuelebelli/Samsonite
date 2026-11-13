package it.unicam.cs.mpgc.rpg130662.model;

public class ArmaMonouso implements Oggetto{

    private final String nome;
    private final int danno;

    public ArmaMonouso(String nome, int danno) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (danno <= 0) {
            throw new IllegalArgumentException("Il danno deve essere positivo");
        }
        this.nome = nome;
        this.danno = danno;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public int getDanno() {
        return danno;
    }

    @Override
    public String getDescrizione() {
        return "Il prossimo colpo infligge " + danno + " danni extra (monouso)";
    }

    @Override
    public void usa(Eroe eroe) {
        eroe.caricaColpo(danno);
    }
}
