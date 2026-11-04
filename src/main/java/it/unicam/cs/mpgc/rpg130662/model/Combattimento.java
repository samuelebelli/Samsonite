package it.unicam.cs.mpgc.rpg130662.model;

public class Combattimento {

    private final Eroe eroe;
    private final Mostro mostro;

    public Combattimento(Eroe eroe, Mostro mostro) {
        this.eroe = eroe;
        this.mostro = mostro;
    }

    public void turno() {
        mostro.subisciDanno(eroe.getAttaccoEroe());
        if (mostro.isVivo()) {
            eroe.subisciDanno(mostro.getAttaccoMostro());
        }
    }

    public boolean isFinito() {
        return !eroe.isVivo() || !mostro.isVivo();
    }

    public boolean haVintoEroe() {
        return eroe.isVivo() && !mostro.isVivo();
    }

    public Mostro getMostro() {
        return mostro;
    }
}