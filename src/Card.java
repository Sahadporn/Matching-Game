import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Contain values and characters of each cards and image file.
 *
 * @author Sahadporn Charnlertlakha
 */
public class Card extends StackPane {
  /**Char that appear on each card. Apply to easy mode.*/
  private Text text;
  /**Image that appear on each card. Apply to normal mode.*/
  private ImageView image;
  /** Image URL used for comparison. */
  private String imageUrl;
  /**Count how many time each card got clicked. Prevent crazy clicking.*/
  private int clickCount;
  /**Status whether the card has been clicked or not.*/
  private static Card selected = null;
  /**Profile collect scores and lives for each game.*/
  private Profile profile;
  private int cardWidth;
  private int cardHeight;
  /**Get instance form TimeCount.*/
  private static TimeCount timerInstance = TimeCount.getTimerInstance();
  private GameConfig gameConfig = GameConfig.getGameConfigInstance();

  /**
   * Contain characteristics of a card in easy mode.
   *
   * @param value   a char (in a String form) that is on the card.
   * @param profile Profile object to collect scores.
   */
  public Card(String value, Profile profile) {
    clickCount = gameConfig.getClickCount();
    cardWidth = gameConfig.getCardWidth();
    cardHeight = gameConfig.getCardHeight();
    Rectangle border = new Rectangle(cardHeight, cardWidth);
    border.setFill(null);
    border.setStroke(Color.DARKBLUE);

    text = new Text(value);
    text.setFont(Font.font(30));

    setAlignment(Pos.CENTER);
    getChildren().addAll(border, text);

    setOnMouseClicked(this::handleMouseClick);

    timerInstance.stop();

    close();

    this.profile = profile;
  }

  /**
   * Contain characteristics of a card in normal mode.
   *
   * @param imageView an image on each card.
   * @param profile   Profile object to collect scores.
   */
  public Card(ImageView imageView, Profile profile, String imageUrl) {
    clickCount = gameConfig.getClickCount();
    cardWidth = gameConfig.getCardWidth();
    cardHeight = gameConfig.getCardHeight();
    Rectangle border = new Rectangle(cardHeight, cardWidth);
    border.setFill(null);
    border.setStyle("-fx-stroke: #CA226B;"
        + "-fx-arc-height: 30;"
        + "-fx-arc-width: 30;"
        + "-fx-stroke-dash-offset: 5;");

    image = imageView;
    this.imageUrl = imageUrl;

    getChildren().add(border);
    getChildren().add(image);

    setOnMouseClicked(this::handleMouseClickImage);

    timerInstance.stop();

    closeImage();

    this.profile = profile;
  }

  /**
   * Check for mouse click. Also check for correct card pairs. Apply for easy mode.
   *
   * @param event MouseEvent for clicking
   */
  public void handleMouseClick(MouseEvent event) {

    if (!timerInstance.isTimerRunning()) {
      timerInstance.start();
    }
    if (isOpen() || clickCount == 0) {
      return;
    }
    clickCount--;

    if (selected == null) {
      selected = this;
      open(() -> {
      });
    } else {
      open(() -> {
        if (!hasSameValue(selected)) {
          profile.subtractLives();
          selected.close();
          this.close();
        } else {
          profile.addScore();
        }
        selected = null;
        clickCount = 2;
      });
    }
  }

  /**
   * Check for mouse click. Also check for correct card pairs. Apply for normal mode.
   *
   * @param event MouseEvent for clicking
   */
  public void handleMouseClickImage(MouseEvent event) {

    if (!timerInstance.isTimerRunning()) {
      timerInstance.start();
    }
    if (isImageOpen() || clickCount == 0) {
      return;
    }
    clickCount--;

    if (selected == null) {
      selected = this;
      openImage(() -> {
      });
    } else {
      openImage(() -> {
        if (!imageHasSameValue(selected)) {
          profile.subtractLives();
          selected.closeImage();
          this.closeImage();
        } else {
          profile.addScore();
        }
        selected = null;
        clickCount = gameConfig.getClickCount();
      });
    }
  }

  /**
   * Check if the card is opened or not. Apply for easy mode.
   *
   * @return true if the card is opened, False otherwise.
   */
  public boolean isOpen() {
    return text.getOpacity() == 1;
  }

  /**
   * Check if the card is opened or not. Apply for normal mode.
   *
   * @return true if the card is opened, False otherwise.
   */
  public boolean isImageOpen() {
    return image.getOpacity() == 1;
  }

  /**
   * Check if card has the same value as other selected card or not. Apply for easy mode.
   *
   * @param other another select Card.
   * @return true if cards are pairs, False otherwise.
   */
  public boolean hasSameValue(Card other) {
    return text.getText().equals(other.text.getText());
  }

  /**
   * Check if card has the same value as other selected card or not. Apply for normal mode.
   *
   * @param other another select Card.
   * @return true if cards are pairs, False otherwise.
   */
  public boolean imageHasSameValue(Card other) {
    return imageUrl.equalsIgnoreCase(other.imageUrl);
    //return image.getImage().getUrl().equalsIgnoreCase(other.image.getImage().getUrl());
  }

  /**
   * Reveal the value of card. In other words, open the card. Apply for easy mode.
   *
   * @param action Runnable
   */
  public void open(Runnable action) {
    FadeTransition ft = new FadeTransition(Duration.seconds(gameConfig.getFadeDuration()), text);
    ft.setToValue(1);
    ft.setOnFinished(event -> action.run());
    ft.play();
  }

  /**
   * Reveal the value of card. In other words, open the card. Apply for normal mode.
   *
   * @param action Runnable
   */
  public void openImage(Runnable action) {
    FadeTransition ft = new FadeTransition(Duration.seconds(gameConfig.getFadeDuration()), image);
    ft.setToValue(1);
    ft.setOnFinished(event -> action.run());
    ft.play();
  }

  /**
   * Close the card, Hide the value of the card. Apply for easy mode.
   */
  public void close() {
    FadeTransition ft = new FadeTransition(Duration.seconds(gameConfig.getFadeDuration()), text);
    ft.setToValue(0);
    ft.play();
  }

  /**
   * Close the card, Hide the value of the card. Apply for normal mode.
   */
  public void closeImage() {
    FadeTransition ft = new FadeTransition(Duration.seconds(gameConfig.getFadeDuration()), image);
    ft.setToValue(0);
    ft.play();
  }
}

