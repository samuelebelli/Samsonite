package it.unicam.cs.mpgc.rpg130662.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EroeTest {

    @Test
    void laCuraNonSuperaIlMassimo() {
        Eroe eroe = new Eroe("Samu", 100, 10);
        eroe.subisciDanno(10);
        eroe.cura(50);
        assertEquals(100, eroe.getPuntiVita());
    }

    @Test
    void ilDannoNonPortaSottoZero() {
        Eroe eroe = new Eroe("Samu", 100, 10);
        eroe.subisciDanno(500);
        assertEquals(0, eroe.getPuntiVita());
        assertFalse(eroe.isVivo());
    }

    @Test
    void ilBonusAumentaLAttacco() {
        Eroe eroe = new Eroe("Samu", 100, 10);
        eroe.aumentaAttacco(5);
        assertEquals(15, eroe.getAttaccoEroe());
    }

    @Test
    void nomeVuotoVieneRifiutato() {
        assertThrows(IllegalArgumentException.class, () -> new Eroe(" ", 100, 10));
    }
}