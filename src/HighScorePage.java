import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HighScorePage extends Pane {

  /**Display the high score of players.*/
  private Pane layoutHighScore;
  private Profile profile = Profile.getProfileInstance();
  VBox displayBox;
  Button returnButton;
  Map<String, Integer> scoreMap;

  /**
   * Call high score display.
   */
  public HighScorePage() {
    initHighScoreDisplay();

    getChildren().add(layoutHighScore);
  }

  /**
   * Create high score layout.
   */
  private void initHighScoreDisplay() {
    layoutHighScore = new Pane();
    layoutHighScore.setStyle(
        "-fx-pref-width: 500;"
            + "-fx-pref-height: 600;");
    Image background = new Image("/resources/Pictures/bg13.gif");
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT,
        BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
    layoutHighScore.setBackground(new Background(backgroundImage));


    Image highScoreImage = new Image("/resources/Pictures/highscore.png");
    ImageView highScoreIV = new ImageView(highScoreImage);
    highScoreIV.setFitHeight(100);
    highScoreIV.setFitWidth(120);
    highScoreIV.setLayoutX(190);

    scoreMap = profile.getScoresMap();
    displayBox = new VBox();
    displayBox.setLayoutY(90);
    displayBox.setLayoutX(210);
    displayBox.setAlignment(Pos.CENTER);
    displayBox.setStyle("-fx-border-color: #ADFF2F;"
        + "-fx-spacing: 5;");
    for (String element : scoreMap.keySet()) {
      Text singleScore = new Text();
      singleScore.setText(element + " " + scoreMap.get(element));
      singleScore.setStyle("-fx-font-family: Lucida Console;"
          + "-fx-font-size: 15;"
          + "-fx-fill: yellow;");
      displayBox.getChildren().add(singleScore);
    }

    Image returnButtonImage = new Image("/resources/Pictures/PixelArt.png");
    ImageView returnButtonIV = new ImageView(returnButtonImage);
    returnButtonIV.setFitHeight(40);
    returnButtonIV.setFitWidth(100);
    returnButton = new Button("", returnButtonIV);
    returnButton.setBackground(null);
    returnButton.setLayoutX(5);
    returnButton.setLayoutY(10);
    returnButton.setOnAction(new ReturnButtonHandler());

    layoutHighScore.getChildren().addAll(displayBox, returnButton, highScoreIV);
  }

  /**
   * Reset the high score page.
   */
  class ReturnButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {

      layoutHighScore.getChildren().remove(displayBox);
      scoreMap = profile.getScoresMap();
      displayBox.getChildren().clear();
      displayBox.setLayoutY(90);
      displayBox.setLayoutX(210);
      displayBox.setAlignment(Pos.CENTER);
      displayBox.setStyle("-fx-border-color: #ADFF2F;"
          + "-fx-spacing: 5;");
      for (String element : scoreMap.keySet()) {
        Text singleScore = new Text();
        singleScore.setText(element + " " + scoreMap.get(element));
        singleScore.setStyle("-fx-font-family: Lucida Console;"
            + "-fx-font-size: 15;"
            + "-fx-fill: yellow;");
        displayBox.getChildren().add(singleScore);
      }
      layoutHighScore.getChildren().add(displayBox);
      Main.getMainStage().setScene(Main.getMenuStage());
    }
  }
}

