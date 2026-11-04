package it.unicam.cs.mpgc.rpg130662.model;

public class Combattimento {

    private final Eroe eroe;
    private final Mostro mostro;

    public Combattimento(Eroe eroe, Mostro mostro) {
        this.eroe = eroe;
        this.mostro = mostro;
    }

    public RisultatoTurno turno() {
        int dannoAlMostro = eroe.getAttaccoEroe();
        mostro.subisciDanno(dannoAlMostro);
        int dannoAlEroe = 0;
        if (mostro.isVivo()) {
            dannoAlEroe = mostro.getAttaccoMostro();
            eroe.subisciDanno(dannoAlEroe);
        }
        return new RisultatoTurno(dannoAlMostro, dannoAlEroe);
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