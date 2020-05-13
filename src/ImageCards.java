import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
  List<String>    images_url;


  public boolean LoadImageResource(String path , InputStream ins ){
    File folder = null;
    File[] files = null;

    try (BufferedInputStream in = new BufferedInputStream(ins) ) {
      try {
        files = folder.listFiles();
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
          if (files[i].getName().endsWith("png")) {
//            file = "file:" + files[i].getAbsolutePath();
//            ImageView im1 = new ImageView(new Image(file));
//            images.add(im1);
//            images_url.add(file);
//            ImageView im2 = new ImageView(new Image(file));
//            images.add(im2);
//            images_url.add(file);
//            im1.setFitHeight(50);
//            im1.setFitWidth(50);
//            im2.setFitHeight(50);
//            im2.setFitWidth(50);
          }
        }

        System.out.println("img size " + images.size() + " url size " + images_url.size());
      } catch (NullPointerException e) {
        System.exit(1);
      }

    }catch (IOException e ){
      System.out.println(" No image Abort " + path );
      System.exit(1);
    }

    return true;
  }
  /**
   * Add image to the list.
   */
  public void addImage() throws IOException {

    //String path = this.getClass().getResource("/resources/TsumTsum/").toString();
    //String newPath = path.substring(5);

    images      = new ArrayList<>();
    images_url  = new ArrayList<>();

    String current_Path = new File(".").getCanonicalPath();
    String newPath = new String(current_Path + "/src/resources/");
    // new FileReader("ProjectBeta/src/resources/HighScore.txt"))) {

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
          images_url.add(file);
          ImageView im2 = new ImageView(new Image(file));
          images.add(im2);
          images_url.add(file);
          im1.setFitHeight(50);
          im1.setFitWidth(50);
          im2.setFitHeight(50);
          im2.setFitWidth(50);
        }
      }

      System.out.println("img size " + images.size() + " url size " + images_url.size());
    } catch (NullPointerException e) {
      System.exit(1);
    }
  }

  public void addImage( List<ImageView> imageViews , List<String> imagesURLs){
    images      = new ArrayList<>(imageViews);
    images_url  = new ArrayList<>(imagesURLs);

  }
  /**
   * Get image list.
   * @return list of imageView
   */
  public List<ImageView> getImages() {
    return images;
  }

  public List<String> getImagesURL() {
    return images_url;
  }

}

