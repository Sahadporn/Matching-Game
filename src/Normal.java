import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Create game template for normal mode.
 * @author Sahadporn Charnlertlakha
 */
class Normal extends BaseTemplateStrategy {

  private GameConfig gameConfig = GameConfig.getGameConfigInstance();

  @Override
  public Pane makeTemplate(int numberOfPairs, int numberOfRows, Profile profile) {
    return super.makeTemplate(numberOfPairs, numberOfRows, profile);
  }

  /**
   * Create game template with random images.
   * @param numberOfCards Total cards in one game.
   * @param numberOfRows Rows of the game's board.
   * @param profile Collect score for each correct pairs.
   * @return Pane for the game.
   */
  public Pane makeTemplate(int numberOfCards, int numberOfRows, Profile profile,
                           List<ImageView> images, List<String> imageUrls) {
    Card card;
    Pane normalTemplate = new Pane();
    normalTemplate.setPrefSize(gameConfig.getTemplatePrefSize(), gameConfig.getTemplatePrefSize());

    List<Card> cards = new ArrayList<>();
    for (int i = 0; i < numberOfCards; i++) {
      card = new Card(images.get(i), profile, imageUrls.get(i));
      cards.add(card);
    }

    Collections.shuffle(cards);

    for (int i = 0; i < cards.size(); i++) {
      Card cardLayout = cards.get(i);
      cardLayout.setTranslateX(gameConfig.getCardWidth() * (i % numberOfRows));
      cardLayout.setTranslateY(gameConfig.getCardHeight() * (i / numberOfRows));
      normalTemplate.getChildren().add(cardLayout);
    }

    return normalTemplate;
  }
}

