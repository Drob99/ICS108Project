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

//This class works with the 2nd stage that has the falling images in it
public class Stage2 extends Application {
    @Override
    public void start(Stage stage){



        //Creating the text showing the score
        Text text = new Text("Score: " + 0);
        format(text);

        //instantiating a falling image and setting the background's color
        FallingEmoji fallingEmoji= new FallingEmoji(text);
        fallingEmoji.setBackground(Background.fill(Paint.valueOf("Teal")));

        //Creating an hBox to hold the score of the happy emoji
        Text scoreH = new Text(" : 3");
        format(scoreH);
        ImageView happy = new ImageView(new Image("Happy.png"));
        happy.setFitWidth(40);
        happy.setFitHeight(40);
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
        sad.setFitWidth(40);
        sad.setFitHeight(40);
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

        //dealing with what happens after the image is clicked on using the customized methods in the fallingEmoji class
        fallingEmoji.getImage().setOnMousePressed(e -> {
            fallingEmoji.increaseScore();
            text.setText("Score: " + fallingEmoji.getScore());
            if (fallingEmoji.getScore() >= 30) {
                fallingEmoji.hideImage();
                fallingEmoji.pause();
                fallingEmoji.getChildren().remove(text);
                fallingEmoji.addScore(fallingEmoji.getScore());
                fallingEmoji.displayScores();
            }
            else{
                fallingEmoji.increaseSpeed();
                fallingEmoji.changeImage();
                fallingEmoji.play();
            }
        });

        //plays the animation
        fallingEmoji.play();

        //creating the scene containing the fallingEmoji pane and adding it to the stage
        Scene scene = new Scene(fallingEmoji, 800, 700);
        stage.setTitle("Falling Emojis");
        stage.setScene(scene);
        stage.show();
    }
    public static void format(Text text) {
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Kristen ITC", 30));
        text.setFill(Paint.valueOf("Yellow"));
    }
}