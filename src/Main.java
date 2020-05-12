import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

  static Stage mainStage;
  static Scene menuStage;
  static Scene gameStage;
  static Scene highScoreStage;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    mainStage = stage;

    MainMenu mainmenu = new MainMenu();
    GamePage gameLayout = new GamePage();
    HighScorePage highScorePage = new HighScorePage();

    menuStage = new Scene(mainmenu, 500, 600, Color.MISTYROSE);
    gameStage = new Scene(gameLayout, 500, 600, Color.LEMONCHIFFON);
    highScoreStage = new Scene(highScorePage, 500, 600);

    mainmenu.setGamePage(gameLayout);
    mainStage.setScene(menuStage);
    mainStage.setTitle("Welcome");
    mainStage.show();

  }

  /**
   * Get the main scene.
   * @return Main scene
   */
  public static Stage getMainStage() {
    return mainStage;
  }

  /**
   * Get the main menu scene.
   * @return main menu scene
   */
  public static Scene getMenuStage() {
    return menuStage;
  }

  /**
   * Get the game scene.
   * @return game scene
   */
  public static Scene getGameStage() {
    return gameStage;
  }

  /**
   * Get the high score scene.
   * @return high score scene
   */
  public static Scene getHighScoreStage() {
    return highScoreStage;
  }
}

