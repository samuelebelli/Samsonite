package it.unicam.cs.mpgc.rpg130662.model;

public class PozioneTotale implements Oggetto{

    private final String nome;

    public PozioneTotale(String nome) {
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescrizione() {
        return "Ripristina tutti i punti vita";
    }

    @Override
    public void usa(Eroe eroe) {
        eroe.cura(eroe.getPuntiVitaMassimiGiocatore());
    }
}
