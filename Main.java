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
  double azDegree = 0;
  double altDegree = 0;
  double distance = 600;

  //sphere colour
  Vector s1Colour =  new Vector(1, 0, 0);
  Vector s2Colour =  new Vector(1, 0, 1);
  Vector s3Colour =  new Vector(1, 0.5, 0);
  Vector s4Colour =  new Vector(0, 0.5, 1);
  //sphere
  Sphere s = new Sphere(220, 220, -100, 75, s1Colour);
  Sphere s1 = new Sphere(320, 220, -100, 50, s1Colour);
  Sphere s2 = new Sphere(420, 320, 0, 75, s2Colour);
  Sphere s3 = new Sphere(320, 120, 200, 100, s3Colour);
  Sphere s4 = new Sphere(320, 520, 100, 150, s4Colour);

  //direction of camera
  Vector d = Camera.getVPN();
  

  Vector center = new Vector(Width / 2, Height / 2, 50);


  Vector camOrigin = Camera.getVRP(center, distance);
  

  Sphere[] spheres = {s1, s2, s3, s4};

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
    RadioButton r4 = new RadioButton("Sphere 4");
   
    r1.setToggleGroup(tg);
    r2.setToggleGroup(tg);
    r3.setToggleGroup(tg);
    r4.setToggleGroup(tg);
    Slider r_slider = new Slider(0, 255, 0);
    Slider g_slider = new Slider(0, 255, 0);
    Slider b_slider = new Slider(0, 255, 0);
    Slider x_slider = new Slider(0, Width, 0);
    Slider y_slider = new Slider(0, Height, 0);
    Slider z_slider = new Slider(0, 1000, 0);
    Slider az_slider = new Slider(0, 360, 180);
    Slider alt_slider = new Slider(-89, 89, 0);

    //create labels
    Label r_label = new Label("Red");
    Label g_label = new Label("Blue");
    Label b_label = new Label("Green");

    Label x_label = new Label("X Coordinate");
    Label y_label = new Label("Y Coordinate");
    Label z_label = new Label("Z Coordinate");

    Label az_label = new Label("Camera Azimuth");
    Label alt_label = new Label("Camera Altitude");
    
    //set intital selected radio button
    r1.setSelected(true);
    //set initial values of sliders
    r_slider.setValue(spheres[currentIndex].colour.x * 255);
    g_slider.setValue(spheres[currentIndex].colour.y * 255);
    b_slider.setValue(spheres[currentIndex].colour.z * 255);
    x_slider.setValue(spheres[currentIndex].center.x);
    y_slider.setValue(spheres[currentIndex].center.y);
    z_slider.setValue(spheres[currentIndex].center.z);

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

      az_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {

            double  newDegree = 180 - newValue.intValue();
            double difference = azDegree - newDegree;
            azDegree = newDegree;
            Camera.rotateVRV(difference);
            d= Camera.getVPN();
        /* 
            double t = 600;
            Camera.VRP.x = center.x -d.x * t;
            Camera.VRP.z = center.z -d.z * t; */
            Camera.getVRP(center, 600);
            System.out.println(Camera.VRP.x);
            System.out.println(Camera.VRP.y);
            System.out.println(Camera.VRP.z);
            System.out.println(Camera.VRP.x + Camera.VPN.x * 600);
            System.out.println(Camera.VRP.y + Camera.VPN.y * 600);
            System.out.println(Camera.VRP.z + Camera.VPN.z * 600);
            Render(image);
          
        }

        
      });

      alt_slider.valueProperty().addListener(
      new ChangeListener < Number > () {
        public void changed(ObservableValue < ? extends Number >
          observable, Number oldValue, Number newValue) {

            double  newDegree =newValue.intValue();
            double difference = altDegree - newDegree;
            altDegree = newDegree;
            Camera.rotateVUV(difference);
            d= Camera.getVPN();
        
           /*  double t = 600;
            Camera.VRP.y = center.y -d.y * t;
            Camera.VRP.z = center.z -d.z * t; */
            Camera.getVRP(center, 600);
            Render(image);
          
        }

        
      });

    //event occurs when radio buttons are toggled
    tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
    {
      public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n)
        {

          //get currently selected radio button
          RadioButton rb = (RadioButton)tg.getSelectedToggle();

          //check which radio button is selected
          if (rb != null) {
            
            if(rb == r1) {
              currentIndex = 0;
            } else if(rb == r2) {
              currentIndex = 1;
            } else if(rb == r3) {
              currentIndex = 2;
            } else {
              currentIndex = 3;
            }

            System.out.println(currentIndex);
            //update sliders
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
    //add everhting to the grid
    root.add(view, 0, 0);

    root.add(r_label, 0, 1);
    root.add(r_slider, 0, 2);
    root.add(g_label, 0, 3);
    root.add(g_slider, 0, 4);
    root.add(b_label, 0, 5);
    root.add(b_slider, 0, 6);
    
    root.add(x_label, 0, 7);
    root.add(x_slider, 0, 8);
    root.add(y_label, 0, 9);
    root.add(y_slider, 0, 10);
    root.add(z_label, 0, 11);
    root.add(z_slider, 0, 12);

    root.add(r1, 2, 1 );
    root.add(r2, 2, 2 );
    root.add(r3, 2, 3 );
    root.add(r4, 2, 4 );

    root.add(az_label, 2, 5);
    root.add(az_slider, 2, 6);
    root.add(alt_label, 2, 7);
    root.add(alt_slider, 2, 8);


    //Display to user
    Scene scene = new Scene(root, 1024, 1024);
    stage.setScene(scene);
    stage.show();
  }

  public void Render(WritableImage image) {
    //Get image dimensions, and declare loop variables
    int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
    PixelWriter image_writer = image.getPixelWriter();

    Vector col = new Vector(0, 0, 0);

    //camera
    Vector o = new Vector(0, 0, 0);
    
    //point of intersection
    Vector p;

    //source of light origin
    Vector light = new Vector(250, 250, -200);
    //colour of light
    Vector lightColour = new Vector (1, 1, 1);

    camOrigin = Camera.getCamOrigin(w, h);

    for (j = 0; j < h; j++) {
      for (i = 0; i < w; i++) {
        o = (camOrigin.add(Camera.VRV.mul(i))).add(Camera.VUV.mul(j));
        d = Camera.getVPN();
        col = new Vector(0, 0, 0);
        double smallest = h * w * 1000;
        int smallestIndex = -1;
        //check closest point among all spheres
        for(int q = 0; q < spheres.length; q++){
          if(spheres[q].intersectionPoint(o, d) > 0 && spheres[q].intersectionPoint(o, d) <= smallest ){
            smallest = spheres[q].intersectionPoint(o, d);
            smallestIndex = q;
          }
        }
        //if no closest point set colour to 0.
        if(smallestIndex < 0){
          col = new Vector(0, 0, 0);
        }
        else {
          //calculate cos theta
          double t = smallest;
          p = o.add(d.mul(t));
          Vector lv = light.sub(p);
          lv.normalise();
          Vector n = p.sub(spheres[smallestIndex].center);
          n.normalise();
          double dp = lv.dot(n);
          //calculate ambient colour
          Vector ambient = spheres[smallestIndex].getAmbientColour();
          Vector diffuse;
          if(dp > 0) {
            diffuse = spheres[smallestIndex].colour.mul(dp);
          }
          else if(dp > 1) {
            diffuse = spheres[smallestIndex].colour.mul(1);
          }
          else {
            diffuse = spheres[smallestIndex].colour.mul(0);
          }
          //calculate final colour of pixel
          col = ambient.colourAdd(diffuse).multiply(lightColour);
        }

        


        image_writer.setColor(i, j, Color.color(col.x, col.y, col.z, 1.0));
      } // column loop
    } // row loop
  }

  public static void main(String[] args) {
    launch();
  }

}