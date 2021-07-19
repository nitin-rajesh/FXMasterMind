package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.PrefStack.PrefReader;
import sample.PrefStack.PrefSettings;
import sample.PrefStack.PrefWriter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConfigPage implements Initializable {
    @FXML
    ComboBox<String> varCountBox;

    @FXML
    ComboBox<String> constCountBox;

    @FXML
    CheckBox boolRepBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        varCountBox.getItems().addAll("4","6","8");
        constCountBox.getItems().addAll("8","10","12","16");
        PrefSettings reader = new PrefReader();
        ArrayList<String> settings = reader.readValues();
        varCountBox.getSelectionModel().select(settings.get(0));
        constCountBox.getSelectionModel().select((settings.get(1)));
        boolRepBox.setSelected(!settings.get(2).equals("0"));
    }

    @FXML
    public void saveButton(ActionEvent e) throws Exception {
        String varTemp= varCountBox.getSelectionModel().getSelectedItem();
        String constTemp = constCountBox.getSelectionModel().getSelectedItem();
        boolean isRep = boolRepBox.isSelected();

        try {
            PrefSettings writer = new PrefWriter();
            writer.writeValues(varTemp,constTemp,isRep?"1":"0");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home_page.fxml")));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
