import java.io.IOException;
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

  private Text scoreField;
  private Label scoreLabel;
  private Text livesField;
  private Label livesLabel;
  private Text timeField;
  private Label timeLabel;

  private String level;
  private GameFactory gameFactory = new GameFactory();
  public Profile profile = Profile.getProfileInstance();
  private TimeCount timerInstance = TimeCount.getTimerInstance();
  private GameConfig gameConfig = GameConfig.getGameConfigInstance();
  public FinishedEventHandler finish = new FinishedEventHandler();

  /**
   * Start game board creation and initiate timer.
   */
  public GamePage() {
    level = gameConfig.getLevel();
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
      insideLayout.setLayoutY(gameConfig.getEasyModeHeight());
      insideLayout.setLayoutX(gameConfig.getEasyModeWidth());
    } else {
      insideLayout.setLayoutY(gameConfig.getNormalModeHeight());
      insideLayout.setLayoutX(gameConfig.getNormalModeWidth());
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
    scoreField = new Text();
    scoreField.setStyle("-fx-font-family: Lucida Console;"
        + "-fx-fill: #6A287E;"
        + "-fx-font-size: 30;");
    scoreLabel = new Label("", scoreIV);
    scoreField.setLayoutY(85);
    scoreField.setLayoutX(200);
    //Bind label to field
    scoreLabel.setLabelFor(scoreField);
    scoreLabel.setLayoutY(30);
    scoreLabel.setLayoutX(100);

    ChangeListener<String> scoreListener = (observableValue, oldValue, newValue) ->
                                                scoreField.setText(newValue);
    profile.addScoreListener(scoreListener);
    Bindings.bindBidirectional(scoreField.textProperty(), profile.scoresProperty());


    Image liveImage = new Image(this.getClass()
                .getResourceAsStream("/resources/Pictures/lives.png"));
    ImageView liveIV = new ImageView(liveImage);
    liveIV.setFitHeight(80);
    liveIV.setFitWidth(100);
    livesField = new Text();
    livesField.setStyle("-fx-font-family: Lucida Console;"
        + "-fx-fill: #6A287E;"
        + "-fx-font-size: 30;");
    livesField.setLayoutY(85);
    livesField.setLayoutX(350);
    livesLabel = new Label("", liveIV);
    //bind label to field
    livesLabel.setLabelFor(livesField);
    livesLabel.setLayoutY(30);
    livesLabel.setLayoutX(250);

    profile.addLivesListener(new LiveHandler());
    Bindings.bindBidirectional(livesField.textProperty(), profile.livesProperty());


    Image timeIcon = new Image(this.getClass()
        .getResourceAsStream("/resources/Pictures/time.png"));
    ImageView timeIV = new ImageView(timeIcon);
    timeIV.setFitHeight(50);
    timeIV.setFitWidth(50);
    timeLabel = new Label("", timeIV);
    timeField = new Text();
    timeField.setStyle("-fx-font-family: Lucida Console;"
        + "-fx-fill: #6A287E;"
        + "-fx-font-size: 25;");
    timeField.setLayoutY(140);
    timeField.setLayoutX(250);
    timeLabel.setLayoutY(110);
    timeLabel.setLayoutX(200);
    //bind label to field
    timeLabel.setLabelFor(timeField);
    timeField.textProperty().bind(timerInstance.getTimerValue());


    layout.getChildren().addAll(insideLayout, returnButton, resetButton,
        scoreField, scoreLabel, livesField, livesLabel,
        timeField, timeLabel);
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
      try {
        profile.writeScore();
      } catch (IOException e) {
        System.out.println("Cant write score but continue!!");
      }
      layout.getChildren().removeAll(insideLayout, resetButton);
      insideLayout = gameFactory.createBoard(level);
      if (level.equalsIgnoreCase("easy")) {
        insideLayout.setLayoutY(gameConfig.getEasyModeHeight());
        insideLayout.setLayoutX(gameConfig.getEasyModeWidth());
      } else {
        insideLayout.setLayoutY(gameConfig.getNormalModeHeight());
        insideLayout.setLayoutX(gameConfig.getNormalModeWidth());
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
      insideLayout.setLayoutY(gameConfig.getEasyModeHeight());
      insideLayout.setLayoutX(gameConfig.getEasyModeWidth());
    } else {
      insideLayout.setLayoutY(gameConfig.getNormalModeHeight());
      insideLayout.setLayoutX(gameConfig.getNormalModeWidth());
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
      livesField.setText(newValue);
      if (Integer.parseInt(newValue) <= 0) {
        displayGameOver();
      }
    }
  }
}

