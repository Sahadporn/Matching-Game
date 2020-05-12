import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * The pane for playing the game.
 * Containing the board template and displaying stats.
 * @author Sahadporn Charnlertlakha
 */
public class GamePage extends Pane {
  /** Main layout of the game.*/
  private Pane layout;
  /** Layout of the game template inside the main layout.*/
  private Pane insideLayout;
  /** The reset button.*/
  private Button resetButton;

  private Text scoreFld;
  private Label scoreLbl;
  private Text livesFld;
  private Label livesLbl;
  private Text timeFld;
  private Label timeLbl;

  /** Check for first mouse click in this scene.*/
  private boolean isClick = true;
  private String level = "easy";
  private static GameFactory gameFactory = new GameFactory();
  public static Profile profile = Profile.getProfileInstance();
  private static TimeCount timerInstance = TimeCount.getTimerInstance();
  public FinishedEventHandler finish = new FinishedEventHandler();

  /**
   * Start game board creation and initiate timer.
   */
  public GamePage() {
    initGameBoard();

    getChildren().add(layout);

    timerInstance.setFinish(finish);
  }

  /**
   * Create game board depend on level.
   */
  private void initGameBoard() {
    layout = new Pane();
    layout.setStyle(
        "-fx-pref-width: 500;"
            + "-fx-pref-height: 600;");
    Image background = new Image(this.getClass()
        .getResourceAsStream("/resources/Pictures/bg12.gif"));
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT,
        BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
    layout.setBackground(new Background(backgroundImage));

    insideLayout = gameFactory.createBoard(level);

    if (level.equalsIgnoreCase("easy")) {
      insideLayout.setLayoutY(200);
      insideLayout.setLayoutX(150);
    } else {
      insideLayout.setLayoutY(180);
      insideLayout.setLayoutX(100);
    }


    Image returnButtonImage = new Image(this.getClass()
        .getResourceAsStream("/resources/Pictures/return.png"));
    ImageView returnButtonIV = new ImageView(returnButtonImage);
    returnButtonIV.setFitHeight(80);
    returnButtonIV.setFitWidth(100);
    Button returnButton = new Button("", returnButtonIV);
    returnButton.setLayoutX(5);
    returnButton.setLayoutY(10);
    returnButton.setStyle("-fx-background-color: null;");
    returnButton.setOnAction(event -> Main.getMainStage().setScene(Main.getMenuStage()));


    Image resetButtonImage = new Image(this.getClass()
        .getResourceAsStream("/resources/Pictures/reset.png"));
    ImageView resetButtonIV = new ImageView(resetButtonImage);
    resetButtonIV.setFitHeight(40);
    resetButtonIV.setFitWidth(80);
    resetButton = new Button("", resetButtonIV);
    resetButton.setLayoutY(500);
    resetButton.setLayoutX(200);
    resetButton.setBackground(null);
    resetButton.setOnAction(new ResetHandler());


    Image scoreImage = new Image(this.getClass()
        .getResourceAsStream("/resources/Pictures/pairs.png"));
    ImageView scoreIV = new ImageView(scoreImage);
    scoreIV.setFitHeight(80);
    scoreIV.setFitWidth(100);
    scoreFld = new Text();
    scoreFld.setStyle("-fx-font-family: Lucida Console;"
        + "-fx-fill: #6A287E;"
        + "-fx-font-size: 30;");
    scoreLbl = new Label("", scoreIV);
    scoreFld.setLayoutY(85);
    scoreFld.setLayoutX(200);
    //Bind label to field
    scoreLbl.setLabelFor(scoreFld);
    scoreLbl.setLayoutY(30);
    scoreLbl.setLayoutX(100);

    ChangeListener <String> scoreListener = (observableValue, oldValue, newValue) -> scoreFld.setText(newValue);
    profile.addScoreListener(scoreListener);
    Bindings.bindBidirectional(scoreFld.textProperty(), profile.scoresProperty());


    Image liveImage = new Image(this.getClass().getResourceAsStream("/resources/Pictures/lives.png"));
    ImageView liveIV = new ImageView(liveImage);
    liveIV.setFitHeight(80);
    liveIV.setFitWidth(100);
    livesFld = new Text();
    livesFld.setStyle("-fx-font-family: Lucida Console;"
        + "-fx-fill: #6A287E;"
        + "-fx-font-size: 30;");
    livesFld.setLayoutY(85);
    livesFld.setLayoutX(350);
    livesLbl = new Label("", liveIV);
    //bind label to field
    livesLbl.setLabelFor(livesFld);
    livesLbl.setLayoutY(30);
    livesLbl.setLayoutX(250);

    profile.addLivesListener(new LiveHandler());
    Bindings.bindBidirectional(livesFld.textProperty(), profile.livesProperty());


    Image timeIcon = new Image(this.getClass()
        .getResourceAsStream("/resources/Pictures/time.png"));
    ImageView timeIV = new ImageView(timeIcon);
    timeIV.setFitHeight(50);
    timeIV.setFitWidth(50);
    timeLbl = new Label("", timeIV);
    timeFld = new Text();
    timeFld.setStyle("-fx-font-family: Lucida Console;"
        + "-fx-fill: #6A287E;"
        + "-fx-font-size: 25;");
    timeFld.setLayoutY(140);
    timeFld.setLayoutX(250);
    timeLbl.setLayoutY(110);
    timeLbl.setLayoutX(200);
    //bind label to field
    timeLbl.setLabelFor(timeFld);
    timeFld.textProperty().bind(timerInstance.getTimerValue());


    layout.getChildren().addAll(insideLayout, returnButton, resetButton,
        scoreFld, scoreLbl, livesFld, livesLbl,
        timeFld, timeLbl);
  }

  /**
   * Set the board's level.
   * @param level difficulties of the game
   */
  public void setString(String level) {
    this.level = level;
  }

  /**
   * Call alert box when live equal to 0 or time runs out.
   */
  public void displayGameOver() {
    Alert input = new Alert(Alert.AlertType.INFORMATION);
    input.setTitle("Game Over");
    input.setHeaderText("Your score: " + profile.getScores());
    input.setOnCloseRequest(dialogEvent -> {
      int totalScore = Integer.parseInt(profile.scoresProperty().get());
      profile.addScoreToMap(totalScore);
      try{
        profile.writeScore();
      }catch (IOException e){
        System.out.println("Cant write score but continue!!");
      }
      layout.getChildren().removeAll(insideLayout, resetButton);
      insideLayout = gameFactory.createBoard(level);
      if (level.equalsIgnoreCase("easy")) {
        insideLayout.setLayoutY(200);
        insideLayout.setLayoutX(150);
      } else {
        insideLayout.setLayoutY(180);
        insideLayout.setLayoutX(100);
      }
      profile.resetScores();
      profile.resetLives();
      timerInstance.resetTimer();
      layout.getChildren().addAll(insideLayout, resetButton);
      Main.getMainStage().setScene(Main.getMenuStage());
    });
    timerInstance.stop();
    Platform.runLater(input::showAndWait);
  }

  /**
   * Rebuild game board.
   * @param level difficulties of the game
   */
  public void reBuildBoard(String level) {
    layout.getChildren().removeAll(insideLayout, resetButton);
    insideLayout = gameFactory.createBoard(level);
    if (level.equalsIgnoreCase("easy")) {
      insideLayout.setLayoutY(200);
      insideLayout.setLayoutX(150);
    } else {
      insideLayout.setLayoutY(180);
      insideLayout.setLayoutX(100);
    }
    profile.resetScores();
    profile.resetLives();
    layout.getChildren().addAll(insideLayout, resetButton);
  }

  /**
   * Call displayGameOver to stop the game.
   */
  class FinishedEventHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
      displayGameOver();
    }
  }

  /**
   * Call the reBuildBoard and reset the game.
   */
  class ResetHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
      reBuildBoard(level);
    }
  }

  /**
   * Handle displaying lives and stop the game when live equal to 0.
   */
  class LiveHandler implements ChangeListener<String> {
    @Override
    public void changed(ObservableValue<? extends String> observableValue,
                        String oldValue, String newValue) {
      livesFld.setText(newValue);
      if (Integer.parseInt(newValue) <= 0) {
        displayGameOver();
      }
    }
  }
}

