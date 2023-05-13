package com.project.speedclickinggame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.ObjectInputFilter;

//This class works with the 2nd stage that has the falling images in it
public class Stage2 extends Application {
    private final int IMG_HEIGHT = 40;
    private final int IMG_WIDTH = 40;
    private static final int FONT_SIZE = 30;
    @Override
    public void start(Stage stage) {

        //Creating the text showing the score and formatting it
        Text text = new Text("Score: " + 0);
        format(text);

        //instantiating the pane that runs the game and setting its color
        FallingEmoji fallingEmoji = null;
        try {
            fallingEmoji = new FallingEmoji(text);
        }

        catch (ExceptionInInitializerError ex) {
            System.out.println("Emoji is Not Found");
            System.exit(1);
        }
        fallingEmoji.setBackground(Background.fill(Paint.valueOf("Teal")));

        //Creating an hBox to hold the score of the happy emoji
        Text scoreH = new Text(" : 3");
        format(scoreH);
        ImageView happy = new ImageView(new Image("Happy.png"));
        happy.setFitWidth(IMG_WIDTH);
        happy.setFitHeight(IMG_HEIGHT);
        HBox habbyHBox = new HBox();
        habbyHBox.setPadding(new Insets(5, 5, 5, 5));
        habbyHBox.getChildren().add(happy);
        habbyHBox.getChildren().add(scoreH);

        //Creating an hBox to hold the score of the mid emoji
        Text scoreM = new Text(" : 1");
        format(scoreM);
        ImageView mid = new ImageView(new Image("Mid.png"));
        mid.setFitWidth(40);
        mid.setFitHeight(40);
        HBox midHBox = new HBox();
        midHBox.setPadding(new Insets(5, 5, 5, 5));
        midHBox.getChildren().add(mid);
        midHBox.getChildren().add(scoreM);

        //Creating an hBox to hold the score of the sad emoji
        Text scoreS = new Text(" : -1");
        format(scoreS);
        ImageView sad = new ImageView(new Image("Sad.png"));
        sad.setFitWidth(IMG_WIDTH);
        sad.setFitHeight(IMG_HEIGHT);
        HBox sadHBox = new HBox();
        sadHBox.setPadding(new Insets(5, 5, 5, 5));
        sadHBox.getChildren().add(sad);
        sadHBox.getChildren().add(scoreS);

        //Adding the score and the values to the vBox
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.getChildren().add(text);
        vBox.getChildren().add(habbyHBox);
        vBox.getChildren().add(midHBox);
        vBox.getChildren().add(sadHBox);

        //Displaying the vBox and setting its alignment
        fallingEmoji.getChildren().add(vBox);
        vBox.setAlignment(Pos.TOP_LEFT);

        //creating the scene containing the fallingEmoji pane and adding it to the stage
        Scene scene = new Scene(fallingEmoji, 800, 700);
        stage.setTitle("Falling Emojis");
        stage.setScene(scene);
        stage.show();


    }
    //This method is used to format the text in this scene
    public static void format(Text text) {
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Kristen ITC", FONT_SIZE));
        text.setFill(Paint.valueOf("Yellow"));
    }
}