package it.unicam.cs.mpgc.rpg130662;

import it.unicam.cs.mpgc.rpg130662.model.Eroe;
import it.unicam.cs.mpgc.rpg130662.model.Oggetto;
import it.unicam.cs.mpgc.rpg130662.model.Pozione;

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
    }
}