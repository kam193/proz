package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("PROZ");
        primaryStage.setScene(new Scene(root, 600, 600));
        root.requestFocus();
        Controller controller = loader.getController();
        primaryStage.setOnHidden(e -> controller.shutdown());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
