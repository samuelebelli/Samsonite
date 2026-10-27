package it.unicam.cs.mpgc.rpg130662;

import it.unicam.cs.mpgc.rpg130662.model.Eroe;
import it.unicam.cs.mpgc.rpg130662.model.Oggetto;
import it.unicam.cs.mpgc.rpg130662.model.Pozione;
import it.unicam.cs.mpgc.rpg130662.model.Arma;
import it.unicam.cs.mpgc.rpg130662.model.Inventario;
import it.unicam.cs.mpgc.rpg130662.model.Mostro;


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
        System.out.println("Attacco iniziale: " + eroe.getAttacco());
        spada.usa(eroe);
        System.out.println("Dopo l'arma: " + eroe.getAttacco());

        Inventario inventario = new Inventario(2);
        System.out.println(inventario.aggiungi(pozione));  // true
        System.out.println(inventario.aggiungi(spada));    // true
        System.out.println(inventario.aggiungi(pozione));  // false, è pieno
        System.out.println(inventario.rimuovi(pozione));  // true


        Mostro goblin = new Mostro("Goblin", 30, 4);
        System.out.println(goblin.getNome() + " vita: " + goblin.getVita());
        goblin.subisciDanno(12);
        System.out.println("Dopo il colpo: " + goblin.getVita());
        goblin.subisciDanno(100);
        System.out.println("Vivo? " + goblin.isVivo());
    }
}