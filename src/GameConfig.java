
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfig {
  private static GameConfig gameConfigInstance;
  private Properties prop;

  private GameConfig() {
    prop = new Properties();

    try {
      loadConfig();
    } catch (IOException e) {
      System.out.println("Config file error, using default config");
    }
  }

  /**
   * Get GameConfig instance.
   * @return game config instance
   */
  public static GameConfig getGameConfigInstance() {
    if (gameConfigInstance == null) {
      gameConfigInstance = new GameConfig();
    }
    return gameConfigInstance;
  }

  public int getCountdownDuration() {
    return Integer.parseInt(prop.getOrDefault("countdown_duration", "60").toString());
  }

  public int getCountdownEnd() {
    return Integer.parseInt(prop.getOrDefault("countdown_end", "0").toString());
  }

  public double getFadeDuration() {
    return Double.parseDouble(prop.getOrDefault("fade_duration", "0.1").toString());
  }

  public int getClickCount() {
    return Integer.parseInt(prop.getOrDefault("click_count", "3").toString());
  }

  public int getCardWidth() {
    return Integer.parseInt(prop.getOrDefault("card_width", "50").toString());
  }

  public int getCardHeight() {
    return Integer.parseInt(prop.getOrDefault("card_height", "50").toString());
  }

  public int getSceneWidth() {
    return Integer.parseInt(prop.getOrDefault("scene_width", "500").toString());
  }

  public int getSceneHeight() {
    return Integer.parseInt(prop.getOrDefault("scene_height", "600").toString());
  }

  public int getTemplatePrefSize() {
    return Integer.parseInt(prop.getOrDefault("template_size", "400").toString());
  }

  public String getLevel() {
    return prop.getOrDefault("level", "easy").toString();
  }

  public int getEasyModeWidth() {
    return Integer.parseInt(prop.getOrDefault("easy_width", "150").toString());
  }

  public int getEasyModeHeight() {
    return Integer.parseInt(prop.getOrDefault("easy_height", "200").toString());
  }

  public int getNormalModeWidth() {
    return Integer.parseInt(prop.getOrDefault("normal_width", "100").toString());
  }

  public int getNormalModeHeight() {
    return Integer.parseInt(prop.getOrDefault("normal_height", "180").toString());
  }

  public int getEasyModeRows() {
    return Integer.parseInt(prop.getOrDefault("easy_rows", "4").toString());
  }

  public int getNormalModeRows() {
    return Integer.parseInt(prop.getOrDefault("normal_rows", "6").toString());
  }

  public int getNumberOfPairs() {
    return Integer.parseInt(prop.getOrDefault("number_of_pairs", "8").toString());
  }

  public int getNumberOfCards() {
    return Integer.parseInt(prop.getOrDefault("number_of_cards", "36").toString());
  }

  /**
   * This is to load config file.
   * @throws IOException The error will be throw if cant find config.
   *                     Then the default values will be used.
   */
  public void loadConfig() throws IOException {
    InputStream is  = Main.class.getResourceAsStream("resources/Config/Config.properties");

    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(is)){
      prop.load(bufferedInputStream);
    } catch (FileNotFoundException f) {
      System.out.println("Config file not found. Using default value");
    }
  }
}
