package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Stage stage;
    private Scene scene;
    ArrayList<Rectangle> GuessBoxes;
    @FXML
    public void switchScene(ActionEvent e) throws IOException {
        GameTime instance = new GameTime(8,10);
        AnchorPane root = new AnchorPane();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        VBox grid = instance.initBoxes();
        grid.setSpacing(5);
        grid.setPadding(new Insets(5, 20, 10, instance.getSceneWidth() - instance.getVariableCount()*27));
        root.getChildren().add(grid);
        HBox bar1 = instance.initGuessBar(1,instance.getConstantCount()/2 + 1);
        bar1.setPadding(new Insets(instance.getSceneHeight() - 120,0,0,0));
        BorderPane centerPane = new BorderPane();
        root.getChildren().add(bar1);
        HBox bar2 = instance.initGuessBar(instance.getConstantCount()/2 + 1,instance.getConstantCount() + 1);
        bar2.setPadding(new Insets(instance.getSceneHeight() - 60,0,0,0));
        root.getChildren().add(bar2);


        scene = new Scene(root, instance.getSceneWidth(), instance.getSceneHeight());
        stage.setScene(scene);
        stage.show();
    }
}
