import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Collect scores and lives for each player.
 * @author Sahadporn Charnlertlakha
 */
public class Profile {

  private static int totalScores = 0;

  private StringProperty score = new SimpleStringProperty();

  private static int totalLives = 10;

  private StringProperty lives = new SimpleStringProperty();

  /** Collect name as key and score as value.*/
  private Map<String, Integer> scoresMap = new HashMap<>();

  private String player;

  private static Profile profileInstance = null;

  private GameConfig gameConfig = GameConfig.getGameConfigInstance();

  private ObservableList<Player> playerList = FXCollections.observableArrayList();;

  private Profile() {

  }

  /**
   * Get the profile instance.
   * @return profile instance
   */
  public static Profile getProfileInstance() {
    if (profileInstance == null) {
      profileInstance = new Profile();
    }
    return profileInstance;
  }

  /**
   * Get score.
   * @return score value.
   */
  public final String getScores() {
    return score.get();
  }

  /**
   * Get lives.
   * @return lives value.
   */
  public final String getLives() {
    return lives.get();
  }

  /**
   * Set score value.
   * @param value total score of each game.
   */
  public final void setScores(int value) {
    score.set(Integer.toString(value));
  }

  /**
   * Set lives value.
   * @param value total lives of each game
   */
  public final void setLives(int value) {
    lives.set(Integer.toString(value));
  }

  /**
   * Reset score value.
   */
  public void resetScores() {
    totalScores = 0;
    setScores(totalScores);
  }

  /**
   * Reset lives value.
   */
  public void resetLives() {
    totalLives = 10;
    setLives(totalLives);
  }

  /**
   * Subtract lives.
   */
  public void subtractLives() {
    totalLives--;
    setLives(totalLives);
  }

  /**
   * Get StringProperty of score.
   * @return StringProperty of score
   */
  public StringProperty scoreProperty() {
    return score;
  }

  /**
   * Get StringProperty of lives.
   * @return StringProperty of lives
   */
  public StringProperty livesProperty() {
    return lives;
  }

  /**
   * Add listener to score.
   * @param listener observer object for score.
   */
  public void addScoreListener(ChangeListener<String> listener) {
    score.addListener(listener);
  }

  /**
   * Add listener to lives.
   * @param listener observer object for lives.
   */
  public void addLivesListener(ChangeListener<String> listener) {
    lives.addListener(listener);
  }

  /**
   * Add name to the playerList.
   * @param name player name
   */
  public void addName(String name) {
    player = name;
    playerList.add(new Player(player));
  }

  /**
   * Add score value.
   */
  public void addScore() {
    totalScores++;
    setScores(totalScores);
  }

  /**
   * Add score to the playerList.
   * @param score player score
   */
  public void updateScore(int score) {
//    for (int i = 0; playerList.size() > i; i++) {
//      if (playerList.get(i).getName().equals(player) && playerList.get(i).getScore() < score) {
//        playerList.get(i).setScore(score);
//        break;
//      }
//    }
    for (int i = 0; playerList.size() > i; i++) {
      if (playerList.get(i).getName().equals(player)) {
        playerList.get(i).setScore(score);
        break;
      }
    }
    writeScore();
  }

  /**
   * Read score from text file.
   */
  public void readScores() {
    if (!LoadScores()) {
      Alert noScoreFileAlert = new Alert(Alert.AlertType.ERROR);
      noScoreFileAlert.setTitle("Cannot find score file");
      noScoreFileAlert.showAndWait();
    }
  }

  /**
   * Load scores from text file
   * @return true if file can be loaded in IDE mode.
   */
  private boolean LoadScores() {
    Path path = Paths.get("HighScore.txt");

    if (!Files.exists(path)){
      return false;
    }
    return readFileScores(path);
  }

  /**
   *  This method is loaded score file when in IDE mode.
   * @param filePath  this is a path to file.
   */
  public boolean readFileScores(Path filePath) {

    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(filePath))) {

      Scanner scan = new Scanner(bufferedInputStream);
      while (scan.hasNext()) {
        String name = scan.next();
        int score = scan.nextInt();

        playerList.add(new Player(name, score));

      }
    } catch (IOException e) {
      System.out.println(e + " Cannot read file");
      return false;
    }
    return true;
  }

  /**
   * Write score to text file.
   */
  public void writeScore() {

    Path path = Paths.get("HighScore.txt");

    try (OutputStream out = Files.newOutputStream(path)) {

      for (Player element : playerList) {
        String writeScore = element.getName() + " " + element.getScore() + "\n";
        byte[] buff = writeScore.getBytes();
        out.write(buff, 0, buff.length);
      }

    } catch (IOException e) {
      System.out.println(e + "Write file unsuccessful");
    }
  }

  /**
   * Get playerList.
   * @return list of player names and scores.
   */
  public ObservableList<Player> getPlayerList() {
    return this.playerList;
  }

}

