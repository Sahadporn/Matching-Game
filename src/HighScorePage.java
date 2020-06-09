import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;


public class HighScorePage extends Pane {

  /**Display the high score of players.*/
  private Pane layoutHighScore;
  private Profile profile = Profile.getProfileInstance();
  TableView<Player> displayTable;
  Button returnButton;

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

    displayTable = tableCreator();

    Image returnButtonImage = new Image("/resources/Pictures/PixelArt.png");
    ImageView returnButtonIV = new ImageView(returnButtonImage);
    returnButtonIV.setFitHeight(40);
    returnButtonIV.setFitWidth(100);
    returnButton = new Button("", returnButtonIV);
    returnButton.setBackground(null);
    returnButton.setLayoutX(5);
    returnButton.setLayoutY(10);
    returnButton.setOnAction(new ReturnButtonHandler());

    layoutHighScore.getChildren().addAll(displayTable, returnButton, highScoreIV);
  }

  private TableView<Player> tableCreator() {
    TableView<Player> table = new TableView<Player>(profile.getPlayerList());
    table.setLayoutY(90);
    table.setLayoutX(110);
    table.setEditable(false);
    table.setStyle("-fx-border-color: transparent;"
                    + "-fx-background-color: transparent;");
    TableColumn<Player, String> nameColumn = new TableColumn<Player, String>("Player");
    nameColumn.setMinWidth(100);
    nameColumn.setStyle("-fx-background-color: black;"
                        + "-fx-text-background-color: #ADFF2F;"
                        + "-fx-alignment: center;"
                        + "-fx-border-color: #ADFF2F;"
                        + "-fx-border-insets: 2;");
    //nameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("player"));
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    TableColumn<Player, Number> scoreColumn = new TableColumn<Player, Number>("High Score");
    scoreColumn.setMinWidth(100);
    scoreColumn.setStyle("-fx-background-color: black;"
        + "-fx-text-background-color: #ADFF2F;"
        + "-fx-alignment: center;"
        + "-fx-border-color: #ADFF2F;"
        + "-fx-border-insets: 2;");
    //scoreColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("highscore"));
    scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());
    table.getColumns().addAll(nameColumn, scoreColumn);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    return table;
  }

  /**
   * Reset the high score page.
   */
  class ReturnButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {

      layoutHighScore.getChildren().remove(displayTable);
      displayTable.getColumns().clear();
      displayTable = tableCreator();

      layoutHighScore.getChildren().add(displayTable);

      Main.getMainStage().setScene(Main.getMenuStage());
    }
  }
}

