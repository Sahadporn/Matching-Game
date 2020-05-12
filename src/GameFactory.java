import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Call the makeTemplate depends on the level selected by player.
 * @author Sahadporn Charnlertlakha
 */
public class GameFactory {

  private static final Profile profile = Profile.getProfileInstance();

  private static final ImageCards imageCards = new ImageCards();

  public GameFactory() {
    imageCards.addImage( profile.getImages() , profile.getImageUrl() );
  }

  /**
   * This is a method to create game board base on level selected by player.
   * @param level : This is game level
   * @return Pane : return scene that contain this game board.
   */
  public Pane createBoard(String level) {
    if (level.equalsIgnoreCase("easy")) {
      return new Easy().makeTemplate(8, 4, profile);
    } else if (level.equalsIgnoreCase("normal")) {
      return new Normal().makeTemplate(6 * 6, 6, profile, imageCards.getImages() , imageCards.getImagesURL() );
    }
    return null;
  }

}