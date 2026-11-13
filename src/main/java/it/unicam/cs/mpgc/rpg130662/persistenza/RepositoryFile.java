package it.unicam.cs.mpgc.rpg130662.persistenza;

import it.unicam.cs.mpgc.rpg130662.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Salva e carica la partita in un semplice file di testo. */
public class RepositoryFile implements RepositoryPartita {

    private final Path percorso;

    public RepositoryFile(Path percorso) {
        if (percorso == null) {
            throw new IllegalArgumentException("Il percorso non può essere nullo");
        }
        this.percorso = percorso;
    }

    @Override
    public void salva(Eroe eroe, Inventario inventario) throws IOException {
        List<String> righe = new ArrayList<>();
        righe.add("EROE;" + eroe.getNome() + ";" + eroe.getPuntiVitaMassimiGiocatore()
                + ";" + eroe.getPuntiVita() + ";" + eroe.getAttaccoEroe());
        righe.add("CAPIENZA;" + inventario.getCapienza());
        for (Oggetto o : inventario.getOggetti()) {
            if (o instanceof Arma a) {
                righe.add("ARMA;" + a.getNome() + ";" + a.getBonusAttacco());
            } else if (o instanceof Pozione p) {
                righe.add("POZIONE;" + p.getNome() + ";" + p.getCura());
            } else if (o instanceof PozioneTotale t) {
            righe.add("POZIONETOTALE;" + t.getNome());
            } else if (o instanceof ArmaMonouso m) {
            righe.add("ARMAMONOUSO;" + m.getNome() + ";" + m.getDanno());
        }
        }
        Files.write(percorso, righe);
    }

    @Override
    public StatoPartita carica() throws IOException {
        Eroe eroe = null;
        Inventario inventario = null;
        List<Oggetto> oggetti = new ArrayList<>();

        for (String riga : Files.readAllLines(percorso)) {
            if (riga.isBlank()) {
                continue;
            }
            String[] campi = riga.split(";");
            switch (campi[0]) {
                case "EROE" -> eroe = new Eroe(campi[1], Integer.parseInt(campi[2]),
                        Integer.parseInt(campi[3]), Integer.parseInt(campi[4]));
                case "CAPIENZA" -> inventario = new Inventario(Integer.parseInt(campi[1]));
                case "ARMA" -> oggetti.add(new Arma(campi[1], Integer.parseInt(campi[2])));
                case "POZIONE" -> oggetti.add(new Pozione(campi[1], Integer.parseInt(campi[2])));
                case "POZIONETOTALE" -> oggetti.add(new PozioneTotale(campi[1]));
                case "ARMAMONOUSO" -> oggetti.add(new ArmaMonouso(campi[1], Integer.parseInt(campi[2])));
                default -> throw new IOException("Riga non riconosciuta: " + riga);
            }
        }
        if (eroe == null || inventario == null) {
            throw new IOException("Salvataggio incompleto");
        }
        for (Oggetto o : oggetti) {
            inventario.aggiungi(o);
        }
        return new StatoPartita(eroe, inventario);
    }
}