import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Collect player information as an Object.
 */
public class Player {

  private StringProperty name;
  private IntegerProperty score;

  public Player(String name, int highScore) {
    this.name = new SimpleStringProperty(name);
    this.score = new SimpleIntegerProperty(highScore);
  }

  public Player(String name) {
    this(name, 0);
  }

  /**
   * Get StringProperty of name.
   * @return SimpleStringProperty name
   */
  public StringProperty nameProperty() {
    return this.name;
  }

  /**
   * Get name as String.
   * @return name as String
   */
  public String getName() {
    return this.name.getValue();
  }

  /**
   * Get IntegerProperty of score.
   * @return SimpleIntegerProperty score
   */
  public IntegerProperty scoreProperty() {
    return this.score;
  }

  /**
   * Get score as int.
   * @return score as int
   */
  public int getScore() {
    return this.score.getValue();
  }

  /**
   * Set score property.
   * @param newScore player new score
   */
  public void setScore(int newScore) {
    if (newScore > this.score.get()) this.score.setValue(newScore);
  }

}
