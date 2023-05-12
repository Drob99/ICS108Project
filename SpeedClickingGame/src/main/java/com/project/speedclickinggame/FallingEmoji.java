package com.project.speedclickinggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

//This class sets the properties of the fallingEmoji pane which extends the Pane class
public class FallingEmoji extends Pane {

    //setting the needed fields in the class
    private int score = 0; //current score
    private Emoji[] emojis = {new Emoji("Happy.png", 3, false), new  Emoji("Sad.png", -1, false), new Emoji("Mid.png", 1, false), new Emoji("Mid.png", 1, true)};
    //The objects falling in the animation
    private Timeline[] animations;
    //The timelines of the objects(different timelines for different speeds)
    private int active;
    //The number of active emojis at a given time(initially 4, the game ends when it is 0)

    //constructor initializing variables and animation
    public FallingEmoji(Text text){         //The text input is the one holding the score in Stage2

        //Resetting the counter for the number of emojis dropped so far
        Emoji.reset();

        //initializing 'active'
        active = 4;

        //initializing the 4th emoji to be random
        emojis[3] = emojis[(int) (Math.random() * 3)].cloneR();

        //animation instantiating
        animations = new Timeline[4];
        for(int i = 0; i < 4; i++) {
            //initializing j to be usable in the lambda expression
            final int j = i;

            //initializing the animation to drop the image(the time control depends on the emoji but is partially random as well)
            animations[i] = new Timeline(new KeyFrame(Duration.millis(20 + 5 * j + (int) (20 * Math.random())), e -> dropImage(j)));
            animations[i].setCycleCount(Timeline.INDEFINITE);

            //Preparing the emojis and adding them to the pane
            Emoji emj = emojis[i];
            emj.prepareEmoji();
            getChildren().add(emj);

            //initializing anm to be usable in the lambda expression
            Timeline anm = animations[i];

            //setting the event when the mouse is pressed
            emj.setOnMousePressed(e -> {
                //incrementing the score and updating it
                score += emj.getScore();
                text.setText("Score: " + score);

                //resetting the emoji and making it faster(slightly random)
                emj.prepareEmoji();
                anm.setRate(anm.getRate() + 0.5 + Math.random() / 2);
            });
        }
        //playing all the animations
        for(Timeline a: animations)
            a.play();
    }

    //This method drops the emoji through the window and deals with the bottom boundary
    public void dropImage(int i) {
        //moving the emoji down
        this.emojis[i].setY(emojis[i].getY() + 1);

        //handling the bottom boundary
        if (emojis[i].getY() > 700) {
            //resetting the emoji if the game is not over, ending it otherwise
            if(emojis[i].prepareEmoji()) {
                //pausing the animation and decreasing the counter of active emojis
                animations[i].pause();
                active--;

                //checking if the game is over
                if(active == 0)
                    displayScores();
            }
        }
    }

    //This method sets the final scene that displays the scores and has a replay button for the game
    public void displayScores(){
        //initializing the array that will hold the leaderboard
        Integer[] topScores = new Integer[5];
        try {
            //reading the top scores from the leaderboard file
            File leaderboard = new File("Top.txt");
            Scanner in = new Scanner(leaderboard);
            for(int i = 0; i < 5; i++)
                topScores[i] = in.nextInt();

            //checking if the new score made it to the leaderboard and updating it
            if(score > topScores[4]) {
                topScores[4] = score;
                Arrays.sort(topScores, Collections.reverseOrder());
                PrintWriter pw = new PrintWriter(leaderboard);
                for(int i: topScores)
                    pw.print(i + " ");
                pw.close();
            }
        } catch (FileNotFoundException e) {}

        //creating a borderpane, stackPane, and text
        BorderPane borderPane = new BorderPane();
        StackPane stackPane = new StackPane();
        Text text = new Text();
        text.setTextAlignment(TextAlignment.CENTER);

        //setting the text to contain the score along with the leaderboard
        String str = "";
        for(int i: topScores)
            str += "\n" + i;
        text.setText("Your Score: " + score + "\nTop Scores" + str);
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