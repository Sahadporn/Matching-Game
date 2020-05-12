import javafx.scene.layout.Pane;

/**
 * An interface for creating game template.
 * @author Sahadporn Charnlertlakha
 */
public interface BoardTemplateStrategy {
  /**
   * Create game template with random cards.
   * @param numberOfPairs Total pairs of cards in one game.
   * @param numberOfRows Rows of the game's board.
   * @param profile Collect score for each correct pairs.
   * @return Pane for the game.
   */
  Pane makeTemplate(int numberOfPairs, int numberOfRows, Profile profile);
}

