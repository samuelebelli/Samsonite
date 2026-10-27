package it.unicam.cs.mpgc.rpg130662.model;

public class Pozione implements Oggetto{

    private final String Nome;
    private final int Cura;

    /** Costruttore della Pozione che permette di inizializzare il nome e i
     * punti vita che vengono curati */
    public Pozione(String nome, int cura) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (cura <= 0) {
            throw new IllegalArgumentException("La cura deve essere positiva");
        }
        this.Nome = nome;
        this.Cura = cura;
    }

    @Override
    public String getNome() {
        return Nome;
    }

    public int getCura() {
        return Cura;
    }

    @Override
    public String getDescrizione() {
        return "Ripristina " + Cura + " punti vita";
    }

    @Override
    public void usa(Eroe eroe) {
        eroe.cura(Cura);
    }
}
