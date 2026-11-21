package it.unicam.cs.mpgc.rpg130662.view;

import it.unicam.cs.mpgc.rpg130662.controller.ControllerPrincipale;
import it.unicam.cs.mpgc.rpg130662.model.*;
import it.unicam.cs.mpgc.rpg130662.persistenza.RepositoryFile;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Region;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** Finestra del gioco: schermata iniziale, di gioco, di vittoria e di sconfitta. */
public class FinestraPrincipale extends Application {

    private ControllerPrincipale controller;
    private Scene scena;

    private final Label nome = new Label();
    private final Label vita = new Label();
    private final Label attacco = new Label();
    private final Label mostro = new Label();
    private final Label messaggio = new Label();
    private final ProgressBar barraVita = new ProgressBar(1);
    private final ProgressBar barraMostro = new ProgressBar(1);
    private final TextArea registro = new TextArea();
    private final FlowPane inventarioBox = new FlowPane(12, 12);
    private VBox boxMostro;
    private Button attacca;
    private final HBox mostriBox = new HBox(12);

    @Override
    public void start(Stage stage) {
        controller = creaController();
        messaggio.getStyleClass().add("messaggio");

        scena = new Scene(creaSchermataIniziale(), 1000, 680);
        scena.getStylesheets().add(getClass().getResource("/stile.css").toExternalForm());
        stage.setTitle("Samsonite");
        stage.setScene(scena);
        stage.show();
    }

    /** Prima schermata: il giocatore sceglie il nome e avvia la partita. */
    private Parent creaSchermataIniziale() {
        Label titolo = new Label("SAMSONITE");
        titolo.getStyleClass().add("titolo");

        Label invito = new Label("Inserisci il tuo nome");
        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome dell'eroe");
        campoNome.setMaxWidth(250);

        Label errore = new Label();
        errore.getStyleClass().add("errore");

        Button avvia = new Button("Avvia partita");
        avvia.setOnAction(e -> {
            try {
                controller.nuovaPartita(campoNome.getText().trim());
                registro.clear();
                messaggio.setText("Buona avventura, " + controller.getTestoNome() + "!");
                scena.setRoot(creaSchermataGioco());
            } catch (IllegalArgumentException ex) {
                errore.setText(ex.getMessage());
            }
        });

        Button carica = new Button("Carica partita");
        carica.setOnAction(e -> {
            try {
                controller.carica();
                registro.clear();
                messaggio.setText("Partita caricata");
                scena.setRoot(creaSchermataGioco());
            } catch (IOException ex) {
                errore.setText("Nessun salvataggio valido: " + ex.getMessage());
            }
        });

        HBox bottoni = new HBox(10, avvia, carica);
        bottoni.setAlignment(Pos.CENTER);

        VBox radice = new VBox(15, titolo, invito, campoNome, bottoni, errore);
        radice.setAlignment(Pos.CENTER);
        return radice;
    }

    /** Schermata di gioco a due colonne: eroe e inventario a sinistra, scontro a destra. */
    private Parent creaSchermataGioco() {
        ricostruisciMostri();

        Label titolo = new Label("SAMSONITE");
        titolo.getStyleClass().add("titolo-piccolo");

        // ---- Colonna sinistra: eroe e inventario ----
        nome.getStyleClass().add("nome-eroe");
        barraVita.getStyleClass().add("barra-vita");
        barraVita.setMaxWidth(Double.MAX_VALUE);
        inventarioBox.setPrefWrapLength(420);

        VBox pannelloEroe = new VBox(8,
                titoloSezione("EROE"), nome, vita, barraVita, attacco,
                titoloSezione("INVENTARIO"), inventarioBox);
        pannelloEroe.getStyleClass().add("pannello");
        HBox.setHgrow(pannelloEroe, Priority.ALWAYS);

        attacca = new Button("ATTACCA");
        attacca.getStyleClass().add("bottone-attacco");
        attacca.setMaxWidth(Double.MAX_VALUE);
        attacca.setOnAction(e -> {
            String log = controller.attacca();
            registro.appendText(log + "\n");
            if (controller.isCombattimentoFinito()) {
                scena.setRoot(controller.haVintoEroe()
                        ? creaSchermataVittoria()
                        : creaSchermataSconfitta());
                return;
            }
            aggiorna();
        });

        barraMostro.getStyleClass().add("barra-mostro");
        barraMostro.setMaxWidth(Double.MAX_VALUE);
        boxMostro = new VBox(6, mostro, barraMostro);
        mostro.getStyleClass().add("nome-mostro");

        registro.setEditable(false);
        registro.setWrapText(true);
        VBox.setVgrow(registro, Priority.ALWAYS);


        VBox pannelloScontro = new VBox(10,
                titoloSezione("SCONTRO"), mostriBox,
                boxMostro, attacca, registro);
        pannelloScontro.getStyleClass().add("pannello");
        pannelloScontro.setPrefWidth(380);
        pannelloScontro.setMinWidth(380);

        HBox centro = new HBox(16, pannelloEroe, pannelloScontro);
        VBox.setVgrow(centro, Priority.ALWAYS);

        // ---- Barra in basso ----
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
                registro.clear();
                messaggio.setText("Partita caricata");
                aggiorna();
            } catch (IOException ex) {
                messaggio.setText("Errore nel caricamento: " + ex.getMessage());
            }
        });

        Button nuova = new Button("Nuova partita");
        nuova.setOnAction(e -> scena.setRoot(creaSchermataIniziale()));

        HBox comandi = new HBox(10, salva, nuova);

        VBox radice = new VBox(14, titolo, centro, comandi, messaggio);
        radice.getStyleClass().add("schermata-gioco");

        aggiorna();
        return radice;
    }

    /** Schermata mostrata quando l'eroe vince uno scontro. */
    private Parent creaSchermataVittoria() {
        Label titolo = new Label("VITTORIA!");
        titolo.getStyleClass().add("titolo-vittoria");
        Label testo = new Label("Hai sconfitto il mostro, " + controller.getTestoNome() + "! 🎉");

        Button continua = new Button("Continua l'avventura");
        continua.setOnAction(e -> {
            controller.terminaCombattimento();
            messaggio.setText("Pronto per un nuovo scontro");
            scena.setRoot(creaSchermataGioco());
        });

        VBox radice = new VBox(15, titolo, testo, continua);
        radice.setAlignment(Pos.CENTER);
        radice.getStyleClass().add("schermata-vittoria");
        return radice;
    }

    /** Schermata mostrata quando l'eroe viene sconfitto. */
    private Parent creaSchermataSconfitta() {
        Label titolo = new Label("SEI STATO SCONFITTO");
        titolo.getStyleClass().add("titolo-sconfitta");
        Label testo = new Label(controller.getTestoNome() + " è caduto in battaglia... 😢");

        Button menu = new Button("Torna al menu");
        menu.setOnAction(e -> {
            controller.terminaCombattimento();
            scena.setRoot(creaSchermataIniziale());
        });

        VBox radice = new VBox(15, titolo, testo, menu);
        radice.setAlignment(Pos.CENTER);
        radice.getStyleClass().add("schermata-sconfitta");
        return radice;
    }

    /** Intestazione di una sezione di pannello. */
    private Label titoloSezione(String testo) {
        Label l = new Label(testo);
        l.getStyleClass().add("titolo-sezione");
        return l;
    }

    /** Ricostruisce le carte dell'inventario in base allo stato attuale. */
    private void ricostruisciInventario() {
        inventarioBox.getChildren().clear();
        for (Oggetto o : controller.getOggetti()) {
            inventarioBox.getChildren().add(creaCarta(o));
        }
    }

    /** Una carta per oggetto: icona, nome, descrizione e bottone Usa. */
    private VBox creaCarta(Oggetto o) {
        String icona = switch (o.getTipo()) {
            case "POZIONE" -> "💊";
            case "POZIONETOTALE" -> "❤";
            case "ARMA" -> "⚔";
            case "ARMAMONOUSO" -> "💎";
            default -> "🎒";
        };

        Label iconaLabel = new Label(icona);
        iconaLabel.getStyleClass().add("carta-icona");

        Label nomeLabel = new Label(o.getNome());
        nomeLabel.setWrapText(true);
        nomeLabel.setMinHeight(Region.USE_PREF_SIZE);
        nomeLabel.getStyleClass().add("carta-nome");

        Label descrizione = new Label(o.getDescrizione());
        descrizione.setWrapText(true);
        descrizione.setMinHeight(Region.USE_PREF_SIZE);
        descrizione.getStyleClass().add("carta-descrizione");

        Button usa = new Button("Usa");
        usa.setOnAction(e -> {
            controller.usaOggetto(o);
            messaggio.setText("Hai usato " + o.getNome());
            aggiorna();
        });

        VBox carta = new VBox(6, iconaLabel, nomeLabel, descrizione, usa);
        carta.setAlignment(Pos.CENTER);
        carta.setPrefWidth(150);
        carta.getStyleClass().add("carta");
        return carta;
    }

    /** Crea una carta per ogni mostro tra cui scegliere. */
    private void ricostruisciMostri() {
        mostriBox.getChildren().clear();
        String[] icone = {"👺", "👹", "🐉"};
        int i = 0;
        for (SchedaMostro scheda : controller.getMostriDisponibili()) {
            mostriBox.getChildren().add(creaCartaMostro(scheda, icone[i % icone.length]));
            i++;
        }
    }

    /** Una carta per mostro: icona, nome, statistiche e bottone Combatti. */
    private VBox creaCartaMostro(SchedaMostro scheda, String icona) {
        Label iconaLabel = new Label(icona);
        iconaLabel.getStyleClass().add("carta-icona");
        Label nomeLabel = new Label(scheda.nome());
        nomeLabel.getStyleClass().add("carta-nome");
        Label statistiche = new Label("Vita " + scheda.vita() + "  ·  Attacco " + scheda.attacco());
        statistiche.getStyleClass().add("carta-descrizione");

        Button combatti = new Button("Combatti");
        combatti.setOnAction(e -> {
            try {
                controller.iniziaCombattimento(scheda);
                registro.clear();
                registro.appendText("Un " + scheda.nome() + " ti sbarra la strada!\n");
                messaggio.setText("Scontro iniziato!");
            } catch (IllegalStateException ex) {
                messaggio.setText(ex.getMessage());
            }
            aggiorna();
        });

        VBox carta = new VBox(6, iconaLabel, nomeLabel, statistiche, combatti);
        carta.setAlignment(Pos.CENTER);
        carta.setPrefWidth(160);
        carta.getStyleClass().addAll("carta", "carta-mostro");
        return carta;
    }

    /** Riallinea la schermata di gioco allo stato attuale. */
    private void aggiorna() {
        nome.setText(controller.getTestoNome());
        vita.setText(controller.getTestoVita());
        attacco.setText(controller.getTestoAttacco());
        barraVita.setProgress(controller.getFrazioneVita());
        ricostruisciInventario();

        boolean inScontro = controller.isInCombattimento();
        boxMostro.setVisible(inScontro);
        boxMostro.setManaged(inScontro);
        mostro.setText(controller.getTestoMostro());
        barraMostro.setProgress(controller.getFrazioneVitaMostro());
        attacca.setDisable(!inScontro);
        mostriBox.setVisible(!inScontro);
        mostriBox.setManaged(!inScontro);
    }

    /** Partita segnaposto: viene sostituita da nuovaPartita() con "Avvia partita". */
    private ControllerPrincipale creaController() {
        return new ControllerPrincipale(new RepositoryFile(Path.of("salvataggio.txt")));
    }
}