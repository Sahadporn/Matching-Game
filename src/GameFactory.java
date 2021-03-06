import javafx.scene.layout.Pane;

/**
 * Call the makeTemplate depends on the level selected by player.
 * @author Sahadporn Charnlertlakha
 */
public class GameFactory {

  private static final Profile profile = Profile.getProfileInstance();

  private static final ImageCards imageCards = new ImageCards();

  private GameConfig gameConfig = GameConfig.getGameConfigInstance();

  /**
   *  This is a constructor to create GameFactory.
   */
  public GameFactory() {
    imageCards.LoadImages();
  }

  /**
   * This is a method to create game board base on level selected by player.
   * @param level : This is game level
   * @return Pane : return scene that contain this game board.
   */
  public Pane createBoard(String level) {
    if (level.equalsIgnoreCase("easy")) {
      return new Easy().makeTemplate(gameConfig.getNumberOfPairs(), gameConfig.getEasyModeRows(),
                                      profile);
    } else if (level.equalsIgnoreCase("normal")) {
      return new Normal().makeTemplate(gameConfig.getNumberOfCards(),
                                       gameConfig.getNormalModeRows(),
                                      profile, imageCards.getImages(), imageCards.getImagesUrls());
    }
    return null;
  }

}