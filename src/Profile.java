import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Collect scores and lives for each player.
 * @author Sahadporn Charnlertlakha
 */
public class Profile {

  private static int totalScores = 0;

  private StringProperty scores = new SimpleStringProperty();

  private static int totalLives = 5;

  private StringProperty lives = new SimpleStringProperty();

  /** Collect name as key and score as value.*/
  private Map<String, Integer> scoresMap = new HashMap<>();

  private String player;

  private static Profile profileInstance = null;

  private List<ImageView> images = new ArrayList<>();
  private final List<String> imagesUrls = new ArrayList<>();
  private boolean ide = true;


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
    totalLives = 5;
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
   * This will load resources content within jar file.
   * @param fileOrPath  It is a path used to check which resources to load within jar.
   * @param ins this is a file stream for relevant resources in jar to be loaded.
   * @return success or fail result of loading.
   */
  public  boolean loadResources(String fileOrPath, InputStream ins) {

    if (fileOrPath.contains("HighScore")) {
      try (BufferedInputStream in = new BufferedInputStream(ins)) {

        Scanner scan = new Scanner(in);
        while (scan.hasNext()) {
          String playerName = scan.next();
          int score = scan.nextInt();

          if (!scoresMap.containsKey(playerName)) {
            scoresMap.put(playerName, score);
          }
        }
      } catch (IOException e) {
        System.out.println(e + "Read file unsuccessful " + fileOrPath);
        System.exit(1);
      }
    } else {
      if (fileOrPath.contains("png") || fileOrPath.contains("jpg")) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(ins);
        Scanner scan = new Scanner(bufferedInputStream);
        while (scan.hasNext()) {
          ImageView im1 = new ImageView(new Image(fileOrPath));
          images.add(im1);
          imagesUrls.add(fileOrPath);
          ImageView im2 = new ImageView(new Image(fileOrPath));
          images.add(im2);
          imagesUrls.add(fileOrPath);
          im1.setFitHeight(50);
          im1.setFitWidth(50);
          im2.setFitHeight(50);
          im2.setFitWidth(50);
          break;
        }
      }

    }
    return true;
  }

  /**
   *  This method to get proper path in jar file.
   * @param currPath  the absolute path
   * @return  proper path.
   * @throws IOException  raised exception if relevant resources couldnt be loaded.
   */
  public String getPath(String currPath) throws IOException {

    String folder  = new String("resources/");

    // i want to know if i am inside the jar or working on the IDE.
    if (currPath.contains("jar")) {
      // jar case
      try {
        URL jar = Main.class.getProtectionDomain().getCodeSource().getLocation();
        Path jarFile = Paths.get(jar.toString().substring("file:".length()));
        FileSystem fileSystem = FileSystems.newFileSystem(jarFile, null);
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fileSystem.getPath(folder));
        for (Path path: directoryStream) {
          InputStream is = Main.class.getResourceAsStream(path.toString());
          if (loadResources(path.toString(), is) == false) {
            System.out.println("error high score");
            throw new IOException();
          }
        }
      } catch (IOException e) {
        System.out.println("Exception");
      }
    }

    return currPath;
  }

  /**
   * Read score from text file.
   */
  public void readScore() throws IOException {
    URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
    String  urls = url.toString();
    String correctPath = null;
    String currentPath = new File(".").getCanonicalPath();

    if (urls.contains("jar")) {
      ide = false;
      try {
        correctPath = getPath(urls + currentPath);

      } catch (IOException e) {
        System.out.println(e + "Read file unsuccessful "
                  + currentPath + " correctPath " + correctPath);
        System.exit(1);
      }
    } else {
      readFileScore(currentPath);
    }

    return;
  }

  /**
   *  This method is loaded score file when in IDE mode.
   * @param currPath  this is a path to file.
   */
  public void readFileScore(String currPath) {

    try (BufferedReader in = new BufferedReader(new FileReader(currPath
                                                  + "/src/resources/HighScore.txt"))) {

      Scanner scan = new Scanner(in);
      while (scan.hasNext()) {
        String name = scan.next();
        int score = scan.nextInt();

        if (!scoresMap.containsKey(name)) {
          scoresMap.put(name, score);
          System.out.println(scoresMap.keySet());
        }

      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Write score to text file.
   */
  public void writeScore() throws IOException {

    String currentPath = new File(".").getCanonicalPath();

    String fileName = new String(currentPath + "/src/resources/HighScore.txt");

    try (OutputStream out = new FileOutputStream(fileName)) {
      Set<String> name = scoresMap.keySet();
      for (String element : name) {
        String writeScore = element + " " + scoresMap.get(element) + "\n";
        byte[] buff = writeScore.getBytes();
        out.write(buff, 0, buff.length);
      }

    } catch (IOException e) {
      //System.out.println(e + "Write file unsuccessful");
    }
  }

  /**
   * Get scoreMap.
   * @return map of player names and scores.
   */
  public Map<String, Integer> getScoresMap() {
    return scoresMap;
  }

  public List<ImageView> getImages() {
    return images;
  }

  public List<String>    getImageUrl() {
    return imagesUrls;
  }

  public boolean isIde() {
    return ide;
  }

}

