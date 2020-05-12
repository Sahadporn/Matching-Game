import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 * Base code for creating a game template.
 * @author Sahadporn Charnlertlakha.
 */
public class BaseTemplateStrategy implements BoardTemplateStrategy {

  /**
   * Create game template with random cards.
   * @param numberOfPairs Total pairs of cards in one game.
   * @param numberOfRows Rows of the game's board.
   * @param profile Collect score for each correct pairs.
   * @return Pane for the game.
   */
  @Override
  public Pane makeTemplate(int numberOfPairs, int numberOfRows, Profile profile) {
    Card card;
    Card pair;
    Pane root = new Pane();
    root.setPrefSize(400, 400);

    // Generate new char-base cards. Starts from 'A'.
    char c = 'A';
    //New list for containing cards.
    List<Card> cards = new ArrayList<>();
    for (int i = 0; i < numberOfPairs; i++) {
      card = new Card(String.valueOf(c), profile);
      cards.add(card);
      pair = new Card((String.valueOf(c)), profile);
      cards.add(pair);
      c++;
    }

    Collections.shuffle(cards);

    // Add cards to the layout pane.
    for (int i = 0; i < cards.size(); i++) {
      Card cardLayout = cards.get(i);
      cardLayout.setTranslateX(50 * (i % numberOfRows));
      cardLayout.setTranslateY(50 * (i / numberOfRows));
      root.getChildren().add(cardLayout);
    }
    return root;
  }
}

