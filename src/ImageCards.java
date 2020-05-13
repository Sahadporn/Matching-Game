import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


  /**
   * Add image to the list.
   */
  public void addImage() throws IOException {

    images      = new ArrayList<>();
    imagesUrls = new ArrayList<>();

    String currentPath = new File(".").getCanonicalPath();
    String newPath = new String(currentPath + "/src/resources/");

    String file = "";
    File folder = null;

    try {
      folder = new File(newPath);
    } catch (NullPointerException e) {
      System.out.println("Resources file not found");
      System.exit(1);
    }
    File[] files = null;
    try {
      files = folder.listFiles();
      for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
        if (files[i].getName().endsWith("png")) {
          file = "file:" + files[i].getAbsolutePath();
          ImageView im1 = new ImageView(new Image(file));
          images.add(im1);
          imagesUrls.add(file);
          ImageView im2 = new ImageView(new Image(file));
          images.add(im2);
          imagesUrls.add(file);
          im1.setFitHeight(50);
          im1.setFitWidth(50);
          im2.setFitHeight(50);
          im2.setFitWidth(50);
        }
      }
    } catch (NullPointerException e) {
      System.exit(1);
    }
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

