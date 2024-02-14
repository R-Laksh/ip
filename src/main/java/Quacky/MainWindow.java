package quacky;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Quacky quacky = new Quacky();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String greeting = quacky.startQuacky();
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(greeting, dukeImage));

    }

    public void setQuacky(Quacky q) {
        quacky = q;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = quacky.getResponse(input);
        assert response != null : "The response from Quacky should not be null.";

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );

        // Check for the "bye" command and perform saving and exit operations
        if (input.trim().equalsIgnoreCase("bye")) {
            handleExit();
        }

        userInput.clear();
    }

    /**
     * Saves the data and exits the application.
     */
    private void handleExit() {
        quacky.saveData();
        Platform.exit();
    }
}
