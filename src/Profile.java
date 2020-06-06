import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

/**
 * Collect scores and lives for each player.
 * @author Sahadporn Charnlertlakha
 */
public class Profile {

  private static int totalScores = 0;

  private StringProperty scores = new SimpleStringProperty();

  private static int totalLives = 10;

  private StringProperty lives = new SimpleStringProperty();

  /** Collect name as key and score as value.*/
  private Map<String, Integer> scoresMap = new HashMap<>();

  private String player;

  private static Profile profileInstance = null;

  private GameConfig gameConfig = GameConfig.getGameConfigInstance();


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
    return scores.get();
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
    scores.set(Integer.toString(value));
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
  public StringProperty scoresProperty() {
    return scores;
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
    scores.addListener(listener);
  }

  /**
   * Add listener to lives.
   * @param listener observer object for lives.
   */
  public void addLivesListener(ChangeListener<String> listener) {
    lives.addListener(listener);
  }

  /**
   * Add name to the scoreMap.
   * @param name player name
   */
  public void addName(String name) {
    player = name;
    if (!scoresMap.containsKey(player)) {
      scoresMap.put(player, 0);
    }
  }

  /**
   * Add score value.
   */
  public void addScore() {
    totalScores++;
    setScores(totalScores);
  }

  /**
   * Add score to the scoreMap.
   * @param score player score
   */
  public void addScoreToMap(int score) {
    if (scoresMap.containsKey(player)) {
      if (scoresMap.get(player) < score) {
        scoresMap.put(player, score);
      }
    }
  }

  /**
   * Read score from text file.
   */
  public void readScore() {
    if (!LoadScore()) {
      Alert noScoreFileAlert = new Alert(Alert.AlertType.ERROR);
      noScoreFileAlert.setTitle("Cannot find score file");
      noScoreFileAlert.showAndWait();
    }
  }

  /**
   * Load scores from text file
   * @return true if file can be loaded in IDE mode.
   */
  private boolean LoadScore() {
    Path path = Paths.get("HighScore.txt");

    if (!Files.exists(path)){
      return false;
    }
    return readFileScore(path);
  }

  /**
   *  This method is loaded score file when in IDE mode.
   * @param filePath  this is a path to file.
   */
  public boolean readFileScore(Path filePath) {

    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(filePath))) {

      Scanner scan = new Scanner(bufferedInputStream);
      while (scan.hasNext()) {
        String name = scan.next();
        int score = scan.nextInt();

        if (!scoresMap.containsKey(name)) {
          scoresMap.put(name, score);
        }

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
      Set<String> name = scoresMap.keySet();
      for (String element : name) {
        String writeScore = element + " " + scoresMap.get(element) + "\n";
        byte[] buff = writeScore.getBytes();
        out.write(buff, 0, buff.length);
      }

    } catch (IOException e) {
      System.out.println(e + "Write file unsuccessful");
    }
  }

  /**
   * Get scoreMap.
   * @return map of player names and scores.
   */
  public Map<String, Integer> getScoresMap() {
    return scoresMap;
  }

}

