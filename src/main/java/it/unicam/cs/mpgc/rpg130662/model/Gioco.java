package it.unicam.cs.mpgc.rpg130662.model;

import java.util.List;

/** Coordina l'eroe, il suo inventario e la scelta dei mostri da combattere. */
public class Gioco {

    private final Eroe eroe;
    private final Inventario inventario;
    private final List<SchedaMostro> mostriDisponibili;

    public Gioco(Eroe eroe, Inventario inventario, List<SchedaMostro> mostriDisponibili) {
        if (eroe == null || inventario == null || mostriDisponibili == null) {
            throw new IllegalArgumentException("Eroe, inventario e mostri non possono essere nulli");
        }
        this.eroe = eroe;
        this.inventario = inventario;
        this.mostriDisponibili = List.copyOf(mostriDisponibili);
    }

    public Eroe getEroe() {
        return eroe;
    }

    public Inventario getInventario() {
        return inventario;
    }

    /** Il menu dei mostri tra cui scegliere. */
    public List<SchedaMostro> getMostriDisponibili() {
        return mostriDisponibili;
    }

    /** L'eroe usa un oggetto dell'inventario, che poi sparisce dalla borsa. */
    public void usaOggetto(Oggetto oggetto) {
        if (!inventario.getOggetti().contains(oggetto)) {
            throw new IllegalArgumentException("L'oggetto non è nell'inventario");
        }
        oggetto.usa(eroe);
        inventario.rimuovi(oggetto);
    }

    /** Prepara uno scontro con un mostro nuovo, creato dalla scheda scelta. */
    public Combattimento iniziaCombattimento(SchedaMostro scheda) {
        if (!eroe.isVivo()) {
            throw new IllegalStateException("L'eroe è morto e non può combattere");
        }
        if (!mostriDisponibili.contains(scheda)) {
            throw new IllegalArgumentException("Mostro non presente nel menu");
        }
        return new Combattimento(eroe, scheda.creaMostro());
    }
}