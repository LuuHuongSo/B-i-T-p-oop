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

        Button bt = new Button("Play vs Human");
        bt.setOnAction(event -> {
            TankGame tankgame = new TankGame();
            Stage gameStage = new Stage();
            tankgame.start(gameStage);
            UIStage.close();
        });
        Button bt1 = new Button("Play vs Com");
        bt1.setOnAction(event ->{
            Game game = new Game();
            Stage gameStage = new Stage();
            game.start(gameStage);
            UIStage.close();
        });

        Button bt2 = new Button("Exit");
        bt2.setOnAction(event -> System.exit(0));

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, bt, bt1, bt2);

        bt.setTranslateY(-50);
        bt1.setTranslateX(-100);
        bt1.setTranslateY(-50);
        bt2.setTranslateX(100);
        bt2.setTranslateY(-50);

        Scene sc = new Scene(root, 800, 600);

        UIStage.setTitle("TankGame");
        UIStage.setScene(sc);
        UIStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

