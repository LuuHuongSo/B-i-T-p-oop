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
        Image backgroundImage = new Image("file:C:/Users/Admin/Desktop/B-i-T-p-oop-Ch-t/B-i-T-p-oop/551461 (1).jpg/");

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
        bt.setStyle("-fx-font-size: 12px; -fx-background-color: #0000FF; -fx-text-fill: white;");

        Button bt1 = new Button("Play vs Com");
        bt1.setOnAction(event ->{
            Game game = new Game();
            Stage gameStage = new Stage();
            game.start(gameStage);
            UIStage.close();
        });
        bt1.setStyle("-fx-font-size: 12px; -fx-background-color: #0000FF; -fx-text-fill: white;");

        Button bt2 = new Button("Exit");
        bt2.setOnAction(event -> System.exit(0));
        bt2.setStyle("-fx-font-size: 12px; -fx-background-color: #0000FF; -fx-text-fill: white;");
        Button bt3 = new Button("Instruction");
        bt3.setStyle("-fx-font-size: 12px; -fx-background-color: #0000FF; -fx-text-fill: white;");
        bt3.setOnAction(event -> {
            Stage stage = new Stage();
            stage.setTitle("Instruction");
            Image image = new Image("file:C:/Users/Admin/Downloads/445984569_817851723275963_7414955992420982266_n.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);
            imageView.setPreserveRatio(true);
            StackPane root1 = new StackPane();
            Button bt4 = new Button("Return to Main Menu");
            bt4.setStyle("-fx-font-size: 12px; -fx-background-color: #0000FF; -fx-text-fill: white;");
            bt4.setOnAction(returnEvent -> {
                UIStage.show();
                stage.close();
            });


            bt4.setTranslateX(320);
            bt4.setTranslateY(250);
            root1.getChildren().addAll(imageView, bt4);

            Scene scene1 = new Scene(root1, 800, 600);
            stage.setScene(scene1);
            stage.show();
            UIStage.close();
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, bt, bt1, bt2, bt3);

        bt.setTranslateY(150);
        bt1.setTranslateX(-130);
        bt1.setTranslateY(150);
        bt2.setTranslateX(100);
        bt2.setTranslateY(150);
        bt3.setTranslateX(320);
        bt3.setTranslateY(-200);

        Scene sc = new Scene(root, 800, 450);

        UIStage.setTitle("TankGame");
        UIStage.setScene(sc);
        UIStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

