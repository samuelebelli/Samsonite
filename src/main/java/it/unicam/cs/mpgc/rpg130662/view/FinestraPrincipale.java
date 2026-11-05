package it.unicam.cs.mpgc.rpg130662.view;

import it.unicam.cs.mpgc.rpg130662.controller.ControllerPrincipale;
import it.unicam.cs.mpgc.rpg130662.model.Arma;
import it.unicam.cs.mpgc.rpg130662.model.Eroe;
import it.unicam.cs.mpgc.rpg130662.model.Gioco;
import it.unicam.cs.mpgc.rpg130662.model.Inventario;
import it.unicam.cs.mpgc.rpg130662.model.Oggetto;
import it.unicam.cs.mpgc.rpg130662.model.Pozione;
import it.unicam.cs.mpgc.rpg130662.model.SchedaMostro;
import it.unicam.cs.mpgc.rpg130662.persistenza.RepositoryFile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** Schermata principale: stato dell'eroe, inventario, combattimento e salvataggio. */
public class FinestraPrincipale extends Application {

    private ControllerPrincipale controller;

    private final Label nome = new Label();
    private final Label vita = new Label();
    private final Label attacco = new Label();
    private final Label mostro = new Label();
    private final Label messaggio = new Label();
    private final ListView<Oggetto> lista = new ListView<>();
    private final ComboBox<SchedaMostro> sceltaMostro = new ComboBox<>();
    private Button attacca;

    @Override
    public void start(Stage stage) {
        controller = creaController();

        configuraLista();
        configuraSceltaMostro();

        Button nuova = new Button("Nuova partita");
        nuova.setOnAction(e -> {
            controller.nuovaPartita();
            messaggio.setText("Nuova partita iniziata");
            aggiorna();
        });

        Button usa = new Button("Usa");
        usa.setOnAction(e -> {
            Oggetto scelto = lista.getSelectionModel().getSelectedItem();
            if (scelto == null) {
                messaggio.setText("Seleziona un oggetto");
                return;
            }
            controller.usaOggetto(scelto);
            messaggio.setText("Hai usato " + scelto.getNome());
            aggiorna();
        });

        Button salva = new Button("Salva");
        salva.setOnAction(e -> {
            try {
                controller.salva();
                messaggio.setText("Partita salvata");
            } catch (IOException | IllegalStateException ex) {
                messaggio.setText("Errore nel salvataggio: " + ex.getMessage());
            }
        });

        Button carica = new Button("Carica");
        carica.setOnAction(e -> {
            try {
                controller.carica();
                messaggio.setText("Partita caricata");
                aggiorna();
            } catch (IOException ex) {
                messaggio.setText("Errore nel caricamento: " + ex.getMessage());
            }
        });

        Button combatti = new Button("Combatti");
        combatti.setOnAction(e -> {
            SchedaMostro scelta = sceltaMostro.getValue();
            if (scelta == null) {
                messaggio.setText("Scegli un mostro");
                return;
            }
            try {
                controller.iniziaCombattimento(scelta);
                messaggio.setText("Scontro iniziato!");
            } catch (IllegalStateException ex) {
                messaggio.setText(ex.getMessage());
            }
            aggiorna();
        });

        attacca = new Button("Attacca");
        attacca.setOnAction(e -> {
            String log = controller.attacca();
            String esito = controller.getEsito();
            messaggio.setText(esito.isEmpty() ? log : log + "\n" + esito);
            aggiorna();
        });

        VBox radice = new VBox(10,
                nome, vita, attacco,
                lista,
                new HBox(10, usa, salva, carica, nuova),
                new HBox(10, sceltaMostro, combatti, attacca),
                mostro,
                messaggio);
        radice.setStyle("-fx-padding: 20;");

        aggiorna();
        stage.setTitle("Samsonite");
        stage.setScene(new Scene(radice, 800, 600));
        stage.show();
    }

    /** Mostra nome e descrizione di ogni oggetto nella lista. */
    private void configuraLista() {
        lista.setCellFactory(l -> new ListCell<>() {
            @Override
            protected void updateItem(Oggetto o, boolean vuota) {
                super.updateItem(o, vuota);
                setText(vuota || o == null ? null : o.getNome() + " - " + o.getDescrizione());
            }
        });
    }

    /** Riempie il menu dei mostri e ne definisce il testo. */
    private void configuraSceltaMostro() {
        sceltaMostro.getItems().setAll(controller.getMostriDisponibili());
        sceltaMostro.setPromptText("Scegli un mostro");
        sceltaMostro.setConverter(new StringConverter<>() {
            @Override
            public String toString(SchedaMostro s) {
                return s == null ? "" : s.nome() + " (vita " + s.vita()
                        + ", attacco " + s.attacco() + ")";
            }

            @Override
            public SchedaMostro fromString(String testo) {
                return null;
            }
        });
    }

    /** Riallinea tutta la schermata allo stato attuale del gioco. */
    private void aggiorna() {
        nome.setText(controller.getTestoNome());
        vita.setText(controller.getTestoVita());
        attacco.setText(controller.getTestoAttacco());
        lista.getItems().setAll(controller.getOggetti());
        mostro.setText(controller.getTestoMostro());
        attacca.setDisable(!controller.isInCombattimento());
    }

    /** Partita di prova: la sostituiremo con una creazione vera. */
    private ControllerPrincipale creaController() {
        Inventario inventario = new Inventario(5);
        inventario.aggiungi(new Pozione("Pozione piccola", 20));
        inventario.aggiungi(new Arma("Spada di ferro", 5));
        Gioco gioco = new Gioco(
                new Eroe("Samu", 100, 10),
                inventario,
                List.of(new SchedaMostro("Goblin", 30, 8),
                        new SchedaMostro("Orco", 60, 15)));
        return new ControllerPrincipale(gioco, new RepositoryFile(Path.of("salvataggio.txt")));
    }
}