package eg.edu.alexu.csd.oop.calculator.cs27;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, 580, 270));
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
