package com.project.speedclickinggame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

//This class works with the 2nd stage that has the falling images in it
public class Stage2 extends Application {
    @Override
    public void start(Stage stage){

        //instantiating a falling image and setting the background's color
        FallingEmoji fallingEmoji= new FallingEmoji();
        fallingEmoji.setBackground(Background.fill(Paint.valueOf("Teal")));

        //Setting the text displaying the scores and characterizing it
        Text text = new Text("Score: " + fallingEmoji.getScore());
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Kristen ITC", 30));
        text.setFill(Paint.valueOf("Yellow"));
        
        //Creating the hBox containing the text and adding the text to it
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.getChildren().add(text);

        //adding the hBox to the fallingEmoji pane and setting its alignment
        fallingEmoji.getChildren().add(hBox);
        hBox.setAlignment(Pos.TOP_LEFT);

        //dealing with what happens after the image is clicked on using the customized methods in the fallingEmoji class
        fallingEmoji.getImage().setOnMousePressed(e -> {
            fallingEmoji.increaseScore();
            text.setText("Score: " + fallingEmoji.getScore());
            if (fallingEmoji.getScore() == 30) {
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
}
