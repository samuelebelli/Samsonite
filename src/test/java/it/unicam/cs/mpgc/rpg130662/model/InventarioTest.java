package it.unicam.cs.mpgc.rpg130662.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    @Test
    void aggiungeFinoAllaCapienza() {
        Inventario inventario = new Inventario(2);
        assertTrue(inventario.aggiungi(new Pozione("Pozione", 20)));
        assertTrue(inventario.aggiungi(new Arma("Spada", 5)));
        assertFalse(inventario.aggiungi(new Pozione("Altra", 20)));
        assertEquals(2, inventario.getOggetti().size());
    }

    @Test
    void rimuoveUnOggettoPresente() {
        Inventario inventario = new Inventario(2);
        Oggetto pozione = new Pozione("Pozione", 20);
        inventario.aggiungi(pozione);
        assertTrue(inventario.rimuovi(pozione));
        assertTrue(inventario.getOggetti().isEmpty());
    }

    @Test
    void laListaRestituitaNonSiPuoModificare() {
        Inventario inventario = new Inventario(2);
        assertThrows(UnsupportedOperationException.class,
                () -> inventario.getOggetti().add(new Arma("Spada", 5)));
    }

    @Test
    void capienzaNonPositivaVieneRifiutata() {
        assertThrows(IllegalArgumentException.class, () -> new Inventario(0));
    }
}