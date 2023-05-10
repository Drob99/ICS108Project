package com.project.speedclickinggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;

//This class sets the properties of the fallingEmoji pane which extends the Pane class
public class FallingEmoji extends Pane {

    //setting the needed fields in the class
    private int score = 0; //current score
    private static ArrayList<Integer> scores = new ArrayList<>(); //stores the highest five scores
    private Emoji[] emojis = {new Emoji("Happy.png", 3, false), new  Emoji("Sad.png", -1, false), new Emoji("Mid.png", 1, false), new Emoji("Mid.png", 1, true)};
    private Timeline[] animations;
    private Text text;
    private int active;
    //constructor initializing variables and animation
    public FallingEmoji(Text text){
        Emoji.reset();
        //choosing a random image and setting the initial properties of the imageView and adding it to the pane
        this.text = text;
        active = 4;
        int r = (int) (Math.random() * 3);
        emojis[3] = emojis[r].cloneR();

        //animation instantiating
        animations = new Timeline[4];
        for(int i = 0; i < 4; i++) {
            final int j = i;
            animations[i] = new Timeline(new KeyFrame(Duration.millis(20 + 5 * ((j == 3) ? r : j) + (int) (20 * Math.random())), e -> dropImage(j)));
            animations[i].setCycleCount(Timeline.INDEFINITE);
            Emoji emj = emojis[i];
            emj.prepareEmoji();
            getChildren().add(emj);
            Timeline anm = animations[i];
            emj.setOnMousePressed(e -> {
                score += emj.getScore();
                text.setText("Score: " + score);
                emj.prepareEmoji();
                anm.setRate(anm.getRate() + Math.random() / 2 + 0.5);
            });
        }
    }

    //This method plays the animation
    public void play(){
        for(Timeline a: animations)
            a.play();
    }

    //This method drops the image through the window and deals with the bottom boundary

    protected void dropImage(int i) {
        this.emojis[i].setY(emojis[i].getY() + 1);
        if (emojis[i].getY() > 700) {
            if(emojis[i].prepareEmoji()) {
                animations[i].pause();
                active--;
                if(active == 0) {
                    addScore(score);
                    displayScores();
                }
            }
        }
    }

    //This method adds the current score to the list of highest 5 scores if suitable and sorts the list as needed
    public void addScore(int score){
        if (scores.size() < 5){
            scores.add(score);
            Collections.sort(scores, Collections.reverseOrder());
        }
        else if (score > scores.get(4)) {
            scores.set(4, score);
            Collections.sort(scores, Collections.reverseOrder());
        }
    }

    //This method sets the final scene that displays the scores and has a replay button for the game
    public void displayScores(){

        //creating a borderpane, stackPane, and text
        BorderPane borderPane = new BorderPane();
        StackPane stackPane = new StackPane();
        Text text = new Text();

        //Customizing the text
        text.setTextAlignment(TextAlignment.CENTER);
        String str = "";
        for (Integer i : scores){
            str += i + "\n";
        }
        text.setText("Previous Scores\n " + str);
        text.setFont(Font.font("Jokerman", 50));

        //Creating an hBox to contain the replay button
        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setAlignment(Pos.CENTER);

        //Creating the button and customizing it
        Button button = new Button("REPLAY");
        button.setPrefWidth(500);
        button.setPrefHeight(200);
        button.setFont(Font.font("Jokerman", 50));
        button.setBackground(Background.fill(Paint.valueOf("Teal")));
        button.setCursor(Cursor.HAND);

        //dealing with changing the scenes
        button.setOnAction(e -> {
            FallingEmoji fallingEmoji = new FallingEmoji(new Text());
            Scene scene = new Scene(fallingEmoji, 800, 700);
            fallingEmoji.setBackground(Background.fill(Paint.valueOf("Teal")));
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(scene);
            Stage2 stage2 = new Stage2();
            stage.close();
            stage2.start(stage);
        });

        //adding the button to the hBox
        hBox.getChildren().add(button);

        //adding the text and stackPane to the main borderPane and customizing
        stackPane.getChildren().add(text);
        borderPane.setCenter(stackPane);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        BorderPane.setAlignment(text, Pos.CENTER);

        //creating scene and changes the scene
        Scene scene = new Scene(borderPane, 800, 700);
        borderPane.setBackground(Background.fill(Paint.valueOf("Teal")));
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
    }
}