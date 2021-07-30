package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelpMenu implements Initializable {
    Stage stage;

    @FXML
    Text hiddenBox;

    @FXML
    ToolBar baseBar;

    @FXML
    public void nextPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/help_menu_p2.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Help page");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void prevPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/help_menu_p1.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Help page");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void closePage(ActionEvent e) {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }



    public void showMessage(ActionEvent e) {
        hiddenBox.setText("Psst\nAlt+Shift+A shows the answer");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
