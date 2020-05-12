import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Count down time when initiate the game.
 * @author Sahadporn Charnlertlakha
 */
public class TimeCount {
  private int countdownDuration;
  private int countdownEnd;
  private Timeline timeline;
  private static TimeCount timerInstance = null;
  private Pane gamePage = null;
  private GamePage.FinishedEventHandler finish;
  private IntegerProperty timeIntSeconds = new SimpleIntegerProperty(countdownDuration);
  private final GameConfig gameConfigInstance = GameConfig.getGameConfigInstance();

  private TimeCount() {
    timeline = new Timeline();
    countdownDuration = gameConfigInstance.getCountdownDuration();
    countdownEnd = gameConfigInstance.getCountdownEnd();
  }

  public void setFinish(GamePage.FinishedEventHandler finish) {
    this.finish = finish;
  }

  /**
   * Get timer instance.
   * @return timer instance
   */
  public static TimeCount getTimerInstance() {
    if (timerInstance == null) {
      timerInstance = new TimeCount();
    }
    return timerInstance;
  }

  /**
   * Check if timer is running or not.
   * @return true if timer is running, false otherwise
   */
  public boolean isTimerRunning() {
    return timeline.getStatus() == Animation.Status.RUNNING;
  }

  /**
   * Start the timer.
   */
  public void start() {
    timeIntSeconds.set(countdownDuration);
    timeline.getKeyFrames().add(
        new KeyFrame(Duration.seconds(countdownDuration + 1),
            new KeyValue(timeIntSeconds, 0))
    );
    timeline.playFromStart();
    timeline.setOnFinished(finish);
  }

  /**
   * Stop the timer.
   */
  public void stop() {
    if (timeline.getStatus() == Animation.Status.RUNNING) {
      timeline.stop();
    }
    timeIntSeconds.setValue(0);
  }

  /**
   * Get time as seconds.
   * @return time as seconds
   */
  public String getTimeSeconds() {
    return timeIntSeconds.toString();
  }

  /**
   * Get timer value with StringBinding.
   * @return timer value
   */
  public StringBinding getTimerValue() {
    return timeIntSeconds.asString();
  }

  /**
   * Reset the timer.
   */
  public void resetTimer() {
    timeIntSeconds.setValue(0);
  }

}

