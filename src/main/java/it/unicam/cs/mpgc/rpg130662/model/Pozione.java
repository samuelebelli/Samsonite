package it.unicam.cs.mpgc.rpg130662.model;

public class Pozione implements Oggetto{

    private final String nome;
    private final int cura;

    /** Costruttore della Pozione che permette di inizializzare il nome e i
     * punti vita che vengono curati */
    public Pozione(String nome, int cura) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (cura <= 0) {
            throw new IllegalArgumentException("La cura deve essere positiva");
        }
        this.nome = nome;
        this.cura = cura;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public int getCura() {
        return cura;
    }

    @Override
    public String getDescrizione() {
        return "Ripristina " + cura + " punti vita";
    }

    @Override
    public void usa(Eroe eroe) {
        eroe.cura(cura);
    }

    @Override
    public String getTipo() {
        return "POZIONE";
    }

    @Override
    public int getValore() {
        return cura;
    }
}
