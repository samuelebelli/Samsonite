package it.unicam.cs.mpgc.rpg130662.model;

/** Uno scontro a turni tra l'eroe e un mostro. */
public class Combattimento {

    private final Eroe eroe;
    private final Mostro mostro;

    public Combattimento(Eroe eroe, Mostro mostro) {
        this.eroe = eroe;
        this.mostro = mostro;
    }

    /** L'eroe colpisce; se il mostro è ancora vivo, risponde. */
    public RisultatoTurno turno() {
        int dannoAlMostro = eroe.usaAttacco();
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