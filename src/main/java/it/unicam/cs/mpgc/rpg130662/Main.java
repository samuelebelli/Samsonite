package it.unicam.cs.mpgc.rpg130662;

import it.unicam.cs.mpgc.rpg130662.model.Arma;
import it.unicam.cs.mpgc.rpg130662.model.Combattimento;
import it.unicam.cs.mpgc.rpg130662.model.Eroe;
import it.unicam.cs.mpgc.rpg130662.model.Gioco;
import it.unicam.cs.mpgc.rpg130662.model.Inventario;
import it.unicam.cs.mpgc.rpg130662.model.Mostro;
import it.unicam.cs.mpgc.rpg130662.model.Oggetto;
import it.unicam.cs.mpgc.rpg130662.model.Pozione;
import it.unicam.cs.mpgc.rpg130662.model.SchedaMostro;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Eroe eroe = new Eroe("Samu", 100, 10);
        Oggetto pozione = new Pozione("Pozione piccola", 20);

        System.out.println("Vita iniziale: " + eroe.getPuntiVita());

        eroe.subisciDanno(30);
        System.out.println("Dopo il danno: " + eroe.getPuntiVita());

        System.out.println("Uso: " + pozione.getNome() + " (" + pozione.getDescrizione() + ")");
        pozione.usa(eroe);
        System.out.println("Dopo la pozione: " + eroe.getPuntiVita());

        Oggetto spada = new Arma("Spada di ferro", 5);
        System.out.println("Attacco iniziale: " + eroe.getAttaccoEroe());
        spada.usa(eroe);
        System.out.println("Dopo l'arma: " + eroe.getAttaccoEroe());

        Inventario inventario = new Inventario(2);
        System.out.println(inventario.aggiungi(pozione));  // true
        System.out.println(inventario.aggiungi(spada));    // true
        System.out.println(inventario.aggiungi(pozione));  // false, è pieno
        System.out.println(inventario.rimuovi(pozione));   // true

        Mostro goblin = new Mostro("Goblin", 30, 4);
        System.out.println(goblin.getNome() + " vita: " + goblin.getPuntiVitaMostro());
        goblin.subisciDanno(12);
        System.out.println("Dopo il colpo: " + goblin.getPuntiVitaMostro());
        goblin.subisciDanno(100);
        System.out.println("Vivo? " + goblin.isVivo());

        Mostro orco = new Mostro("Orco", 40, 8);
        Combattimento scontro = new Combattimento(eroe, orco);
        while (!scontro.isFinito()) {
            scontro.turno();
            System.out.println("Eroe: " + eroe.getPuntiVita() + " | Orco: " + orco.getPuntiVitaMostro());
        }
        System.out.println("Ha vinto l'eroe? " + scontro.haVintoEroe());

        // --- Prova della classe Gioco ---
        System.out.println("--- Gioco ---");
        Inventario borsa = new Inventario(5);
        borsa.aggiungi(new Arma("Spada classica", 5));
        borsa.aggiungi(new Pozione("Pozione piccola", 20));

        Gioco gioco = new Gioco(
                new Eroe("Samu", 100, 10),
                borsa,
                List.of(new SchedaMostro("Goblin", 30, 4), new SchedaMostro("Orco", 40, 8)));

        Oggetto primo = gioco.getInventario().getOggetti().get(0);
        gioco.usaOggetto(primo);
        System.out.println("Attacco: " + gioco.getEroe().getAttaccoEroe());
        System.out.println("Oggetti rimasti: " + gioco.getInventario().getOggetti().size());

        Combattimento c = gioco.iniziaCombattimento(gioco.getMostriDisponibili().get(0));
        while (!c.isFinito()) {
            c.turno();
        }
        System.out.println("Ha vinto l'eroe? " + c.haVintoEroe());
    }
}