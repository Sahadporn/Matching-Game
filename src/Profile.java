import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

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
  private final List<String>    images_url = new ArrayList<>();



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
  public  boolean LoadResources( String file_or_path , InputStream ins ){

    if ( file_or_path.contains("HighScore") ){
      try (BufferedInputStream in = new BufferedInputStream(ins) ) {

        Scanner scan = new Scanner(in);
        while (scan.hasNext()) {
          String player_name = scan.next();
          int score = scan.nextInt();

          if (!scoresMap.containsKey(player_name)) {
            scoresMap.put(player_name, score);
          }
        }
      } catch (IOException e) {
        System.out.println(e + "Read file unsuccessful " + file_or_path );
        System.exit(1);
      }
    }else{
      if ( file_or_path.contains("png") || file_or_path.contains("jpg") ){
        int i = 0;
        BufferedInputStream isr = new BufferedInputStream(ins);
        Scanner scan = new Scanner(isr);
        while (scan.hasNext()) {
          String file_name = scan.nextLine();
          ImageView im1 = new ImageView(new Image(file_or_path));
          images.add(im1);
          images_url.add(file_or_path);
          ImageView im2 = new ImageView(new Image(file_or_path));
          images.add(im2);
          images_url.add(file_or_path);
          im1.setFitHeight(50);
          im1.setFitWidth(50);
          im2.setFitHeight(50);
          im2.setFitWidth(50);
//          System.out.println("i " + ++i + " name " + file_or_path);
          break;
        }
      }

    }
    return true;
  }
  public String getPath(String curr_path) throws IOException {

    String fooFolder  = new String("src/resources/");

/** i want to know if i am inside the jar or working on the IDE*/
    if( curr_path.contains("jar")){
      //System.out.println("inside jar " + curr_path);
      /** jar case */
      try{
        URL jar = Main.class.getProtectionDomain().getCodeSource().getLocation();
        String name = jar.toString().substring(0,jar.toString().length()-15);
        System.out.println(jar);
        //jar.toString() begins with file:
        //i want to trim it out...
        Path jarFile = Paths.get(jar.toString().substring("file:".length()));
        FileSystem fs = FileSystems.newFileSystem(jarFile, null);
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fs.getPath(fooFolder));
        for(Path p: directoryStream){
          InputStream is = Main.class.getResourceAsStream(p.toString()) ;
          if ( LoadResources(p.toString() , is ) == false ){
            System.out.println("erro high score");
            throw new IOException();
          }
        }
      }catch(IOException e) {
        System.out.println("Exception");
      }
    }

    return curr_path;
  }

  /**
   * Read score from text file.
   */
  public void readScore() throws IOException {

    String respath = "/resources/HighScore.txt";
    InputStream ins = Main.class.getResourceAsStream(respath);
    URL uu = Main.class.getProtectionDomain().getCodeSource().getLocation();
    String  ss = uu.toString();
    String sp  = uu.getPath();
//    String  ns = uu.getFile();
    String tt = null;
    String current_Path = new File(".").getCanonicalPath();
    try{
      tt = getPath( ss + current_Path );

    }catch(IOException e){
      System.out.println(e + "Read file unsuccessful " + current_Path + " tt " + tt);
      System.exit(1);

    }

    return ;
  }

  /**
   * Write score to text file.
   */
  public void writeScore() throws IOException {

    String current_Path = new File(".").getCanonicalPath();
    // new FileReader("ProjectBeta/src/resources/HighScore.txt"))) {

    String file_name = new String( current_Path + "/src/resources/HighScore.txt");

    try (OutputStream out = new FileOutputStream(file_name)) {
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
  public List<ImageView> getImages(){ return images; }
  public List<String>    getImageUrl(){  return images_url; }
}

