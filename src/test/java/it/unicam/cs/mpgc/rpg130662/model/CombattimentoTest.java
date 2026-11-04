package it.unicam.cs.mpgc.rpg130662.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombattimentoTest {

    @Test
    void l_eroeForteVince() {
        Eroe eroe = new Eroe("Samu", 100, 15);
        Combattimento c = new Combattimento(eroe, new Mostro("Goblin", 30, 4));
        while (!c.isFinito()) {
            c.turno();
        }
        assertTrue(c.haVintoEroe());
    }

    @Test
    void l_eroeDebolePerde() {
        Eroe eroe = new Eroe("Samu", 10, 1);
        Combattimento c = new Combattimento(eroe, new Mostro("Orco", 100, 20));
        while (!c.isFinito()) {
            c.turno();
        }
        assertFalse(c.haVintoEroe());
        assertFalse(eroe.isVivo());
    }

    @Test
    void ilMostroMortoNonRispondeAlColpo() {
        Eroe eroe = new Eroe("Samu", 100, 50);
        Combattimento c = new Combattimento(eroe, new Mostro("Goblin", 30, 4));
        c.turno();
        assertEquals(100, eroe.getPuntiVita());
    }
}