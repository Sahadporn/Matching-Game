import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Main menu for choosing game difficulties and high score page.
 * @author Sahadporn Charnlertlakha
 */
public class MainMenu extends Pane {

  /** Main layout.*/
  private Pane menuLayout;

  private GamePage gamePage;

  /** TextField for inputting name.*/
  private TextField nameInput;

  private Profile profile = Profile.getProfileInstance();

  /**
   * Call menu page and read scores.
   */
  public MainMenu() {

    initMenu();
    profile.readScores();
    getChildren().add(menuLayout);
  }

  /**
   * Create menu pane.
   */
  private void initMenu() {
    menuLayout = new Pane();
    menuLayout.setStyle(
        "-fx-pref-width: 500;"
            + "-fx-pref-height: 600;");
    Image background = new Image("/resources/Pictures/bg11.gif");
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT,
        BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
    menuLayout.setBackground(new Background(backgroundImage));

    Image icon = new Image("/resources/Pictures/icon1.png");
    ImageView iconIV = new ImageView(icon);
    iconIV.setLayoutX(100);
    iconIV.setLayoutY(30);
    iconIV.setFitHeight(300);
    iconIV.setFitWidth(300);

    ImageView easyButtonImage = new ImageView("/resources/Pictures/easy.png");
    easyButtonImage.setFitHeight(73);
    easyButtonImage.setFitWidth(84);
    Button easyModeButton = new Button("", easyButtonImage);
    easyModeButton.setLayoutX(130);
    easyModeButton.setLayoutY(370);
    easyModeButton.setOnAction(new GamePageHandler());
    easyModeButton.setStyle("-fx-background-color: transparent;");


    ImageView normalButtonImage = new ImageView("/resources/Pictures/normal.png");
    normalButtonImage.setFitHeight(73);
    normalButtonImage.setFitWidth(100);
    Button normalModeButton = new Button("", normalButtonImage);
    normalModeButton.setLayoutX(250);
    normalModeButton.setLayoutY(370);
    normalModeButton.setOnAction(new NormalPageHandler());
    normalModeButton.setStyle("-fx-background-color: transparent;");


    nameInput = new TextField();
    nameInput.setPromptText("Input your name");
    nameInput.setPrefWidth(200);
    nameInput.setPrefHeight(30);
    nameInput.setLayoutY(330);
    nameInput.setLayoutX(150);
    nameInput.setStyle("-fx-background-color: #FFDFFD;"
        + "-fx-text-fill: #583759;"
        + "-fx-font-family: Lucida Console;");

    nameInput.setOnKeyPressed(event -> {if (event.getCode().equals(KeyCode.ENTER)) {
                                profile.addName(nameInput.getText().trim()); }});

//    ImageView enterIconImage = new ImageView("/resources/Pictures/icon.png");
//    enterIconImage.setFitWidth(30);
//    enterIconImage.setFitHeight(30);
//    Button enterButton = new Button("Enter");
//    enterButton.setGraphic(enterIconImage);
//    enterButton.setFont(Font.font("Lucida Console"));
//    enterButton.setTextFill(Color.YELLOW);
//    enterButton.setBackground(Background.EMPTY);
//    enterButton.setOnAction(new InputHandler());
//    enterButton.setLayoutY(320);
//    enterButton.setLayoutX(300);


    Image highScoreImage = new Image("/resources/Pictures/bar.png");
    ImageView highScoreIV = new ImageView(highScoreImage);
    highScoreIV.setFitHeight(60);
    highScoreIV.setFitWidth(125);
    Button highScoreButton = new Button("", highScoreIV);
    highScoreButton.setOnAction(new HighScoreHandler());
    highScoreButton.setBackground(null);
    highScoreButton.setLayoutX(170);
    highScoreButton.setLayoutY(440);


    Image exitImg = new Image("/resources/Pictures/exit.png");
    ImageView exitIV = new ImageView(exitImg);
    exitIV.setFitHeight(23);
    exitIV.setFitWidth(70);
    Button exitButton = new Button("", exitIV);
    exitButton.setBackground(null);
    exitButton.setLayoutY(10);
    exitButton.setLayoutX(5);
    exitButton.setOnAction(actionEvent -> Platform.exit());

    menuLayout.getChildren().addAll(iconIV, easyModeButton, normalModeButton,
        nameInput, highScoreButton, exitButton);
  }

  /**
   * Set the game page.
   * @param gamePage game page
   */
  public void setGamePage(GamePage gamePage) {
    this.gamePage = gamePage;
  }

  /**
   * Build and rebuild the game board depend on the difficulties.
   * @param level difficulties of the game.
   */
  public void buildBoard(String level) {
    gamePage.setString(level);
    gamePage.reBuildBoard(level);
    Main.getMainStage().setScene(Main.getGameStage());
  }

  /**
   * Read high score and enter the high score page.
   */
  class HighScoreHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
      Main.getMainStage().setScene(Main.getHighScoreStage());
    }
  }

//  /**
//   * Accept name input and put to scoreMap.
//   */
//  class InputHandler implements EventHandler<ActionEvent> {
//
//    @Override
//    public void handle(ActionEvent event) {
//      profile.addName(nameInput.getText());
//    }
//  }

  /**
   * Handle the building easy mode game board.
   */
  class GamePageHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
      buildBoard("easy");
    }
  }

  /**
   * Handle the building normal game board.
   */
  class NormalPageHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
      buildBoard("normal");
    }
  }

}

