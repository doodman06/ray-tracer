/*
CS-255 Getting started code for the assignment
I do not give you permission to post this code online
Do not post your solution online
Do not copy code
Do not use JavaFX functions or other libraries to do the main parts of the assignment (i.e. ray tracing steps 1-7)
All of those functions must be written by yourself
You may use libraries to achieve a better GUI
*/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Light;
import javafx.scene.control.Toggle;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.io.*;
import java.lang.Math.*;
import javafx.geometry.HPos;

public class Main extends Application {
  int Width = 640;
  int Height = 640;
  int currentIndex = 0;

  //sphere colour
  Vector s1Colour =  new Vector(1, 0, 0);
  Vector s2Colour =  new Vector(1, 0, 0);
  Vector s3Colour =  new Vector(1, 0, 0);
  //sphere
  Sphere s = new Sphere(220, 220, 0, 75, s1Colour);
  Sphere s1 = new Sphere(220, 220, 0, 75, s1Colour);
  Sphere s2 = new Sphere(520, 520, 0, 75, s2Colour);
  Sphere s3 = new Sphere(420, 520, 100, 75, s3Colour);
  

  Sphere[] spheres = {s1, s2, s3};

  int green_col = 255; //just for the test example

  @Override
  public void start(Stage stage) throws FileNotFoundException {
    stage.setTitle("Ray Tracing");

    //We need 3 things to see an image
    //1. We create an image we can write to
    WritableImage image = new WritableImage(Width, Height);
    //2. We create a view of that image
    ImageView view = new ImageView(image);
    //3. Add to the pane (below)

    //Create the simple GUI

    ToggleGroup tg = new ToggleGroup();

    RadioButton r1 = new RadioButton("Sphere 1");
    RadioButton r2 = new RadioButton("Sphere 2");
    RadioButton r3 = new RadioButton("Sphere 3");
    r1.setToggleGroup(tg);
    r2.setToggleGroup(tg);
    r3.setToggleGroup(tg);
    Slider r_slider = new Slider(0, 255, 0);
    Slider g_slider = new Slider(0, 255, 0);
    Slider b_slider = new Slider(0, 255, 0);
    Slider x_slider = new Slider(0, Width, 0);
    Slider y_slider = new Slider(0, Height, 0);
    Slider z_slider = new Slider(0, 1000, 0);
    
    r1.setSelected(true);
    r_slider.setValue(spheres[currentIndex].colour.x * 255);
    g_slider.setValue(spheres[currentIndex].colour.y * 255);
    b_slider.setValue(spheres[currentIndex].colour.z * 255);

    //Add all the event handlers
    r_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {
          double d = newValue.intValue();
          spheres[currentIndex].colour.x = d /255;
          Render(image);
        }
      });

    g_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {
          double d = newValue.intValue();
          spheres[currentIndex].colour.y = d /255;
          Render(image);
        }
      });

      b_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {
          double d = newValue.intValue();
          spheres[currentIndex].colour.z = d /255;
          Render(image);
        }
      });

      x_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {
          spheres[currentIndex].center.x = newValue.intValue();
          Render(image);
        }
      });

      y_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {
          spheres[currentIndex].center.y = newValue.intValue();
          Render(image);
        }
      });

      z_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {
          spheres[currentIndex].center.z = newValue.intValue();
          Render(image);
        }
      });

    tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
    {
      public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n)
        {

          RadioButton rb = (RadioButton)tg.getSelectedToggle();

          if (rb != null) {
            if(rb == r1) {
              currentIndex = 0;
            } else if (rb == r2) {
              currentIndex = 1;
            } else {
              currentIndex = 2;
            }
            System.out.println(currentIndex);
            r_slider.setValue(spheres[currentIndex].colour.x * 255);
            g_slider.setValue(spheres[currentIndex].colour.y * 255);
            b_slider.setValue(spheres[currentIndex].colour.z * 255);
            x_slider.setValue(spheres[currentIndex].center.x);
            y_slider.setValue(spheres[currentIndex].center.y);
            z_slider.setValue(spheres[currentIndex].center.z);
          }
        }
    });

    //The following is in case you want to interact with the image in any way
    //e.g., for user interaction, or you can find out the pixel position for debugging
    view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
      System.out.println(event.getX() + " " + event.getY());
      event.consume();
    });

    Render(image);

    GridPane root = new GridPane();
    root.setVgap(12);
    root.setHgap(12);

    //3. (referring to the 3 things we need to display an image)
    //we need to add it to the pane
    root.add(view, 0, 0);
    root.add(r_slider, 0, 1);
    root.add(g_slider, 0, 2);
    root.add(b_slider, 0, 3);
    root.add(r1, 2, 1 );
    root.add(r2, 2, 2 );
    root.add(r3, 2, 3 );
    root.add(x_slider, 3, 1);
    root.add(y_slider, 3, 2);
    root.add(z_slider, 3, 3);

    //Display to user
    Scene scene = new Scene(root, 1024, 768);
    stage.setScene(scene);
    stage.show();
  }

  public void Render(WritableImage image) {
    //Get image dimensions, and declare loop variables
    int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
    PixelWriter image_writer = image.getPixelWriter();

    //double c = green_col / 255.0;
    Vector col = new Vector(0.5, 0.5, 0.5);


    
    //camera
    Vector o = new Vector(0, 0, 0);
    //direction of camera
    Vector d = new Vector(0, 0, 1);
    //point of intersection
    Vector p;

    Vector light = new Vector(250, 250, -200);

    for (j = 0; j < h; j++) {
      for (i = 0; i < w; i++) {
        o = new Vector(i, j, -400);
        col = new Vector(0, 0, 0);
        double smallest = h * w;
        int smallestIndex = -1;
        for(int q = 0; q < spheres.length; q++){
          if(spheres[q].intersectionPoint(o, d) > 0 && spheres[q].intersectionPoint(o, d) <= smallest ){
            smallest = spheres[q].intersectionPoint(o, d);
            smallestIndex = q;
          }
        }
        if(smallestIndex < 0){
          smallestIndex = 0;
        }
        double t = smallest;
        p = o.add(d.mul(t));
        Vector lv = light.sub(p);
        lv.normalise();
        Vector n = p.sub(spheres[smallestIndex].center);
        n.normalise();
        double dp = lv.dot(n);
        if(dp > 0) {
          col = spheres[smallestIndex].colour.mul(dp);
        }
        if(dp > 1) {
          col = spheres[smallestIndex].colour.mul(1);
        }
        if(dp < 0) {
          col = spheres[smallestIndex].colour.mul(0);
        }


        image_writer.setColor(i, j, Color.color(col.x, col.y, col.z, 1.0));
      } // column loop
    } // row loop
  }

  public static void main(String[] args) {
    launch();
  }

}