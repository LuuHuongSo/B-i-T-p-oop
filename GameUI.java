import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GameUI extends Application {

    @Override
    public void start(Stage UIStage) {
        Image backgroundImage = new Image("file:C:/Users/Admin/Downloads/TankGame.jpg");

        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.setFitWidth(800); 
        backgroundImageView.setFitHeight(600);
        backgroundImageView.setPreserveRatio(true);

        Button bt = new Button("Play game");
        bt.setOnAction(event -> {
            TankGame tankgame = new TankGame();
            Stage gameStage = new Stage();
            tankgame.start(gameStage);
            UIStage.close();
        });
        // Đặt vị trí của nút
        // playButton.setLayoutX(350); // Tọa độ X
        // playButton.setLayoutY(500); // Tọa độ Y

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, bt);

        Scene sc = new Scene(root, 800, 600);

        UIStage.setTitle("TankGame");
        UIStage.setScene(sc);
        UIStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

