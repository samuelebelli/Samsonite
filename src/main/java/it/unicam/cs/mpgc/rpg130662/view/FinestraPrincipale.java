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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** Finestra del gioco: schermata iniziale (nome dell'eroe) e schermata di gioco. */
public class FinestraPrincipale extends Application {

    private ControllerPrincipale controller;
    private Scene scena;

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
        scena = new Scene(creaSchermataIniziale(), 800, 600);
        stage.setTitle("Samsonite");
        stage.setScene(scena);
        stage.show();
    }

    /** Prima schermata: il giocatore sceglie il nome e avvia la partita. */
    private Parent creaSchermataIniziale() {
        Label titolo = new Label("SAMSONITE");
        titolo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label invito = new Label("Inserisci il tuo nome");
        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome dell'eroe");
        campoNome.setMaxWidth(250);

        Label errore = new Label();

        Button avvia = new Button("Avvia partita");
        avvia.setOnAction(e -> {
            try {
                controller.nuovaPartita(campoNome.getText().trim());
                scena.setRoot(creaSchermataGioco());
                messaggio.setText("Buona avventura, " + controller.getTestoNome() + "!");
            } catch (IllegalArgumentException ex) {
                errore.setText(ex.getMessage());
            }
        });

        Button carica = new Button("Carica partita");
        carica.setOnAction(e -> {
            try {
                controller.carica();
                scena.setRoot(creaSchermataGioco());
                messaggio.setText("Partita caricata");
            } catch (IOException ex) {
                errore.setText("Nessun salvataggio valido: " + ex.getMessage());
            }
        });

        VBox radice = new VBox(15, titolo, invito, campoNome,
                new HBox(10, avvia, carica), errore);
        radice.setAlignment(Pos.CENTER);
        return radice;
    }

    /** Seconda schermata: eroe, inventario, combattimento e salvataggio. */
    private Parent creaSchermataGioco() {
        configuraLista();
        configuraSceltaMostro();

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

        Button nuova = new Button("Nuova partita");
        nuova.setOnAction(e -> scena.setRoot(creaSchermataIniziale()));

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
            if (controller.isCombattimentoFinito()){
                scena.setRoot(controller.haiVinto()
                        ? creaSchermataVittoria()
                        :creaSchermataSconfitta());
                return;
            }
            messaggio.setText(log);
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
        return radice;
    }

    /** Schermata mostrata quando l'eroe vince uno scontro. */
    private Parent creaSchermataVittoria() {
        Label titolo = new Label("VITTORIA!");
        titolo.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        Label testo = new Label("Hai sconfitto il mostro, " + controller.getTestoNome() + "! 🎉");

        Button continua = new Button("Continua l'avventura");
        continua.setOnAction(e -> {
            controller.terminaCombattimento();
            scena.setRoot(creaSchermataGioco());
            messaggio.setText("Pronto per un nuovo scontro");
        });

        VBox radice = new VBox(15, titolo, testo, continua);
        radice.setAlignment(Pos.CENTER);
        return radice;
    }

    /** Schermata mostrata quando l'eroe viene sconfitto. */
    private Parent creaSchermataSconfitta() {
        Label titolo = new Label("SEI STATO SCONFITTO");
        titolo.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        Label testo = new Label(controller.getTestoNome() + " è caduto in battaglia... 😢");

        Button riprova = new Button("Nuova partita");
        riprova.setOnAction(e -> {
            controller.terminaCombattimento();
            scena.setRoot(creaSchermataIniziale());
        });

        Button carica = new Button("Carica ultimo salvataggio");
        Label errore = new Label();
        carica.setOnAction(e -> {
            try {
                controller.carica();
                scena.setRoot(creaSchermataGioco());
                messaggio.setText("Partita caricata");
            } catch (IOException ex) {
                errore.setText("Nessun salvataggio valido: " + ex.getMessage());
            }
        });

        VBox radice = new VBox(15, titolo, testo, new HBox(10, riprova, carica), errore);
        radice.setAlignment(Pos.CENTER);
        return radice;
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

    /** Riallinea la schermata di gioco allo stato attuale. */
    private void aggiorna() {
        nome.setText(controller.getTestoNome());
        vita.setText(controller.getTestoVita());
        attacco.setText(controller.getTestoAttacco());
        lista.getItems().setAll(controller.getOggetti());
        mostro.setText(controller.getTestoMostro());
        attacca.setDisable(!controller.isInCombattimento());
    }

    /** Partita segnaposto: viene sostituita da nuovaPartita() quando si preme "Avvia partita". */
    private ControllerPrincipale creaController() {
        Inventario inventario = new Inventario(5);
        inventario.aggiungi(new Pozione("Pozione piccola", 20));
        inventario.aggiungi(new Arma("Spada di ferro", 5));
        Gioco gioco = new Gioco(
                new Eroe("Eroe", 100, 10),
                inventario,
                List.of(new SchedaMostro("Goblin", 30, 8),
                        new SchedaMostro("Orco", 60, 15)));
        return new ControllerPrincipale(gioco, new RepositoryFile(Path.of("salvataggio.txt")));
    }
}