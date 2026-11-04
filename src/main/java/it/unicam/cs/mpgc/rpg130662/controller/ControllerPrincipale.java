package it.unicam.cs.mpgc.rpg130662.controller;

import it.unicam.cs.mpgc.rpg130662.model.Gioco;
import it.unicam.cs.mpgc.rpg130662.model.Oggetto;
import it.unicam.cs.mpgc.rpg130662.persistenza.RepositoryPartita;
import it.unicam.cs.mpgc.rpg130662.persistenza.StatoPartita;
import it.unicam.cs.mpgc.rpg130662.model.Combattimento;
import it.unicam.cs.mpgc.rpg130662.model.SchedaMostro;

import java.io.IOException;
import java.util.List;


public class ControllerPrincipale {

    private Gioco gioco;
    private final RepositoryPartita repository;
    private Combattimento combattimento;


    public ControllerPrincipale(Gioco gioco, RepositoryPartita repository)
    {
        this.gioco = gioco;
        this.repository = repository;
    }

    public String getTestoNome(){
        return gioco.getEroe().getNome();
    }

    public String getTestoVita(){
        return "Vita: " + gioco.getEroe().getPuntiVita()
                + "/" + gioco.getEroe().getPuntiVitaMassimiGiocatore();
    }

    public String getTestoAttacco() {
        return "Attacco: " + gioco.getEroe().getAttaccoEroe();
    }

    public List<Oggetto> getOggetti() {
        return gioco.getInventario().getOggetti();
    }

    public void usaOggetto(Oggetto oggetto) {
        gioco.usaOggetto(oggetto);
    }

    public List<SchedaMostro> getMostriDisponibili() {
        return gioco.getMostriDisponibili();
    }

    public void iniziaCombattimento(SchedaMostro scheda) {
        combattimento = gioco.iniziaCombattimento(scheda);
    }

    public boolean isInCombattimento() {
        return combattimento != null && !combattimento.isFinito();
    }

    public void attacca() {
        if (combattimento == null) {
            throw new IllegalStateException("Nessun combattimento in corso");
        }
        combattimento.turno();
    }

    public String getTestoMostro() {
        if (combattimento == null) {
            return "";
        }
        return combattimento.getMostro().getNome()
                + " - Vita: " + combattimento.getMostro().getPuntiVitaMostro();
    }

    /** Messaggio di esito se lo scontro è finito, altrimenti stringa vuota. */
    public String getEsito() {
        if (combattimento == null || !combattimento.isFinito()) {
            return "";
        }
        return combattimento.haVintoEroe() ? "Hai vinto!" : "Sei stato sconfitto...";
    }

    public void salva() throws IOException {
        repository.salva(gioco.getEroe(), gioco.getInventario());
    }

    /** Eroe e inventario in Gioco sono final: dopo il caricamento si crea un nuovo Gioco. */
    public void carica() throws IOException {
        StatoPartita stato = repository.carica();
        gioco = new Gioco(stato.eroe(), stato.inventario(), gioco.getMostriDisponibili());
        combattimento = null;
    }
}
