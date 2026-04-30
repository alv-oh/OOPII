package com.example;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.stream.Collectors;

//MoviesView is a VBox — a vertical layout container. Everything stacks top to bottom inside it.
public class MoviesView extends VBox {

    private final GenreStore store = GenreStore.getInstance();

    // --- Add section ---
//    These are declared as fields (not inside a method) so that both buildUI() and wireEvents() can access them.
    private final ComboBox<String> genreComboBox     = new ComboBox<>();
    private final TextField        movieNameField     = new TextField();
    private final Button           saveButton         = new Button("Save Movie");
    private final Label            saveStatusLabel    = new Label();

    // --- Remove section ---
    private final ComboBox<String> registeredComboBox = new ComboBox<>();
    private final Button           removeButton        = new Button("Remove Movie");
    private final Label            removeStatusLabel   = new Label();

    public MoviesView() {
        buildUI(); //buildUI() assembles the visual layout — sets fonts, colors, sizes, and arranges everything into rows using makeRow().
        wireEvents(); //wireEvents() connects user actions to logic:
    }

    // ------------------------------------------------------------------ UI --
    private void buildUI() {
        setSpacing(14);
        setPadding(new Insets(28));
        setStyle("-fx-background-color: #f0f4f8;");
        setPrefWidth(400);

        // Title
        Label title = new Label("Movies");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setTextFill(Color.web("#1a1a2e"));

        // ---- ADD SECTION ----
        Label addHeader = sectionHeader("Add a Movie");

        genreComboBox.setItems(FXCollections.observableArrayList(GenreStore.GENRES));
        genreComboBox.setPromptText("Select a genre");
        genreComboBox.setPrefWidth(220);

        movieNameField.setPromptText("Enter movie name");
        movieNameField.setPrefWidth(220);

        styleButton(saveButton, "#3a86ff");

        saveStatusLabel.setFont(Font.font(12));

        // ---- REMOVE SECTION ----
        Label removeHeader = sectionHeader("Registered Movies");

        registeredComboBox.setPromptText("Movies in selected genre");
        registeredComboBox.setPrefWidth(220);

        styleButton(removeButton, "#ef233c");

        removeStatusLabel.setFont(Font.font(12));

        // Assemble rows
        getChildren().addAll(
                title,
                new Separator(),
                addHeader,
                //makeRow() is a helper that creates one horizontal line — a label on the left, a control on the right:
                makeRow("Genre:",      genreComboBox),
                makeRow("Name:",       movieNameField),
                makeRow("",            saveButton),
                saveStatusLabel,
                new Separator(),
                removeHeader,
                makeRow("Registered:", registeredComboBox),
                makeRow("",            removeButton),
                removeStatusLabel
        );
    }

    // --------------------------------------------------------------- Events --
    private void wireEvents() {
        genreComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    refreshRegistered(newVal);
                    saveStatusLabel.setText("");
                    removeStatusLabel.setText("");
                });

        saveButton.setOnAction(e -> handleSave());
        removeButton.setOnAction(e -> handleRemove());
    }

    private void handleSave() {
        String genre = genreComboBox.getValue();
        String name  = movieNameField.getText();

        if (genre == null) {
            setStatus(saveStatusLabel, "⚠ Please select a genre first.", false);
            return;
        }
        if (name == null || name.isBlank()) {
            setStatus(saveStatusLabel, "⚠ Movie name cannot be empty.", false);
            return;
        }

        boolean added = store.addMovie(genre, name);
        if (added) {
            setStatus(saveStatusLabel, "\"" + name.trim() + "\" saved to " + genre + ".", true);
            movieNameField.clear();
            refreshRegistered(genre);
        } else {
            setStatus(saveStatusLabel, "Movie already exists in this genre.", false);
        }
    }

    private void handleRemove() {
        String genre    = genreComboBox.getValue();
        String selected = registeredComboBox.getValue();

        if (genre == null) {
            setStatus(removeStatusLabel, "⚠ Select a genre first.", false);
            return;
        }
        if (selected == null) {
            setStatus(removeStatusLabel, "⚠ Select a movie to remove.", false);
            return;
        }

        boolean removed = store.removeMovie(genre, selected);
        if (removed) {
            setStatus(removeStatusLabel, " \"" + selected + "\" removed.", true);
            refreshRegistered(genre);
        } else {
            setStatus(removeStatusLabel, " Could not remove movie.", false);
        }
    }

    private void refreshRegistered(String genre) {
        if (genre == null) {
            registeredComboBox.setItems(FXCollections.emptyObservableList());
            return;
        }
        List<String> names = store.getMovies(genre)
                .stream()
                .map(Movie::getName)
                .collect(Collectors.toList());
        registeredComboBox.setItems(FXCollections.observableArrayList(names));
        registeredComboBox.getSelectionModel().clearSelection();
    }

    // ------------------------------------------------------------ Helpers ---
    // HBox is a horizontal container. The 12 is the spacing between the label and the control.
    private HBox makeRow(String labelText, javafx.scene.Node control) {
        Label lbl = new Label(labelText);
        lbl.setPrefWidth(90);
        lbl.setFont(Font.font(13));
        lbl.setTextFill(Color.web("#333"));
        HBox row = new HBox(12, lbl, control);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Label sectionHeader(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        lbl.setTextFill(Color.web("#555"));
        return lbl;
    }

    private void styleButton(Button btn, String hex) {
        btn.setPrefWidth(220);
        btn.setStyle(
                "-fx-background-color: " + hex + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        );
    }

    private void setStatus(Label lbl, String msg, boolean success) {
        lbl.setText(msg);
        lbl.setTextFill(Color.web(success ? "#28a745" : "#ef233c"));
    }
}