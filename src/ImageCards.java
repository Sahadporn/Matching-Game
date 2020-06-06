import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Make Image cards for normal mode.
 * @author Sahadoirn Charnlertlakha
 */
public class ImageCards {
  /** List of images.*/
  List<ImageView> images;
  List<String> imagesUrls;

  private GameConfig gameConfig = GameConfig.getGameConfigInstance();


  public ImageCards() {
    images = new ArrayList<ImageView>();
    imagesUrls = new ArrayList<String>();
  }

  /**
   *  This is to copy reference of Images List and it names.
   * @param imageViews  This is a list of real images.
   * @param imagesUrls  This is a list of image name.
   */
  public void addImage(List<ImageView> imageViews, List<String> imagesUrls) {
    images    = new ArrayList<>(imageViews);
    this.imagesUrls = new ArrayList<>(imagesUrls);
  }

  public void LoadImages() {
    String folder = "resources/TsumTsum/";

    String ImageNameList ="alice.png;alien.png;boo.png;cheshire.png;chip.png;daisy.png;donald.png;" +
        "dumbo.png;mickey.png;mike.png;perry.png;pluto.png;pooh.png;Rabbit.png;" +
        "sally.png;sully.png;tigger.png;woody.png";
    StringTokenizer tokenizer = new StringTokenizer(ImageNameList, ";");
    InputStream inputStream;
    String imageName;

    while (tokenizer.hasMoreTokens()) {
      imageName = folder + tokenizer.nextToken();
      inputStream = Main.class.getResourceAsStream(imageName);
      ImageToImageList(inputStream, imageName);
    }
  }

  private void ImageToImageList(InputStream inputStream, String imageName) {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
    Scanner scanner = new Scanner(bufferedInputStream);
    if (scanner.hasNext()) {
      ImageView imageView1 = new ImageView(new Image(imageName));
      images.add(imageView1);
      imagesUrls.add(imageName);
      ImageView imageView2 = new ImageView(new Image(imageName));
      images.add(imageView2);
      imagesUrls.add(imageName);
      imageView1.setFitWidth(gameConfig.getCardWidth());
      imageView1.setFitHeight(gameConfig.getCardHeight());
      imageView2.setFitWidth(gameConfig.getCardWidth());
      imageView2.setFitHeight(gameConfig.getCardHeight());
    }
  }

  /**
   * Get image list.
   * @return list of imageView
   */
  public List<ImageView> getImages() {
    return images;
  }

  public List<String> getImagesUrls() {
    return imagesUrls;
  }

}

