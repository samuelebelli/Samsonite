package it.unicam.cs.mpgc.rpg130662.controller;

import it.unicam.cs.mpgc.rpg130662.model.*;
import it.unicam.cs.mpgc.rpg130662.persistenza.RepositoryPartita;
import it.unicam.cs.mpgc.rpg130662.persistenza.StatoPartita;

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

    /** Esegue un turno e restituisce la riga di log da mostrare. */
    public String attacca() {
        if (combattimento == null) {
            throw new IllegalStateException("Nessun combattimento in corso");
        }
        RisultatoTurno r = combattimento.turno();
        String nomeMostro = combattimento.getMostro().getNome();
        String riga = "Colpisci " + nomeMostro + " per " + r.dannoAlMostro();
        if (r.dannoAlEroe() > 0) {
            riga += " - " + nomeMostro + " ti colpisce per " + r.dannoAlEroe();
        }
        return riga;
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

    public boolean isSalvabile() {
        return gioco.getEroe().isVivo();
    }


    public boolean isCombattimentoFinito(){
        return combattimento != null && combattimento.isFinito();
    }

    public boolean haiVinto(){
        return combattimento != null && combattimento.haVintoEroe();
    }

    public void terminaCombattimento(){
        combattimento = null;
    }

    public void salva() throws IOException {
        if (!isSalvabile()) {
            throw new IllegalStateException("Non puoi salvare: l'eroe è morto");
        }
        repository.salva(gioco.getEroe(), gioco.getInventario());
    }

    /** Eroe e inventario in Gioco sono final: dopo il caricamento si crea un nuovo Gioco. */
    public void carica() throws IOException {
        StatoPartita stato = repository.carica();
        gioco = new Gioco(stato.eroe(), stato.inventario(), gioco.getMostriDisponibili());
        combattimento = null;
    }

    public void nuovaPartita(String nomeEroe) {
        if (nomeEroe.contains(";"))
            throw new IllegalArgumentException("Il nome non può contenere ';' ");
        Inventario inventario = new Inventario(5);
        inventario.aggiungi(new Pozione("Pozione piccola", 20));
        inventario.aggiungi(new Arma("Spada di ferro", 5));
        gioco = new Gioco(new Eroe(nomeEroe, 100,10), inventario, gioco.getMostriDisponibili());
        combattimento = null;
    }

}
