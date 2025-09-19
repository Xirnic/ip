package kjaro.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import kjaro.Kjaro;

/**
 * Controller for the main GUI.
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

    private Kjaro kjaro;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image kjaroImage = new Image(this.getClass().getResourceAsStream("/images/Kjaro.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Kjaro instance */
    public void setKjaro(Kjaro k) {
        kjaro = k;
        dialogContainer.getChildren().addAll(DialogBox.getKjaroDialog(kjaro.getWelcome(), kjaroImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kjaro.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKjaroDialog(response, kjaroImage)
        );
        userInput.clear();
    }
}
