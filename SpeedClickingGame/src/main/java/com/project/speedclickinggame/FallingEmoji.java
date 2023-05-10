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
    private Image image;
    private ImageView imageView;
    private Emoji[] emojis = {new Emoji("Happy.png", 3, false), new  Emoji("Sad.png", -1, false), new Emoji("Mid.png", 1, false), new Emoji("Mid.png", 1, true)};
    private Timeline animation;
    private Timeline[] animations;
    public Image[] arr = {new Image("Happy.png"),
            new Image("Sad.png")};
    private Text text;
    private int active;
    //constructor initializing variables and animation
    public FallingEmoji(Text text){
        //choosing a random image and setting the initial properties of the imageView and adding it to the pane
        this.image = arr[(int)(Math.random() * 2)];
        this.imageView = new ImageView(this.image);
        this.text = text;
        active = 4;
        imageView.setStyle("-fx-background-color: transparent");
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setY(-100);
        imageView.setX((int)(Math.random() * 701));
        imageView.setCursor(Cursor.HAND);
        int r = (int) (Math.random() * 3);
        emojis[3] = emojis[r].cloneR();
        getChildren().add(imageView);
        getChildren().add(emojis[0]);
        getChildren().add(emojis[1]);
        getChildren().add(emojis[2]);
        getChildren().add(emojis[3]);

        //animation instantiating
        animation = new Timeline(
                new KeyFrame(
                        Duration.millis(50), e -> dropImage()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animations = new Timeline[4];
        for(int i = 0; i < 4; i++) {
            final int j = i;
            animations[i] = new Timeline(new KeyFrame(Duration.millis(35 + 10 * ((j == 3) ? r : j) + (int) (10 * Math.random())), e -> dropImage(j)));
            animations[i].setCycleCount(Timeline.INDEFINITE);
        }
        for(int i = 0; i < 4; i++) {
            Emoji emj = emojis[i];
            Timeline anm = animations[i];
            emj.setOnMousePressed(e -> {
                score += emj.getScore();
                text.setText("Score: " + score);
                emj.prepareEmoji();
                anm.setRate(anm.getRate() + 1 + Math.random() / 2);
            });
        }
    }

    //This method plays the animation
    public void play(){
        animation.play();
        for(Timeline a: animations)
            a.play();
    }

    //This method helps pause the animation
    public void pause(){
        animation.pause();
        for(Timeline a: animations)
            a.pause();
    }

    //This method increases the speed of the animation
    public void increaseSpeed(){
        animation.setRate(animation.getRate() + 1);
    }

    //This method drops the image through the window and deals with the bottom boundary
    protected void dropImage() {
        this.imageView.setY(imageView.getY() + 1);
        if (this.getY() > 700) {
            hideImage();
            pause();
            addScore(getScore());
            displayScores();
        }
    }

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

    //This method hides the image when needed
    public void hideImage(){
        imageView.setVisible(false);
    }

    //This method gets the Y coordinate of the image
    public double getY(){
        return imageView.getY();
    }

    //This method hides the current image, randomly chooses another image, and starts the animation for another image.
    public void changeImage(){
        imageView.setVisible(false);
        imageView.setImage(arr[(int)(Math.random() * 2)]);
        imageView.setY(-100);
        imageView.setX((int)(Math.random() * 701));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setCursor(Cursor.HAND);
        imageView.setVisible(true);
    }

    //This method increases the value of score field
    public void increaseScore(){
        this.score++;
    }

    //This method is a getter for the score
    public int getScore(){
        return this.score;
    }

    //This is a getter for the ImageView
    public ImageView getImage(){
        return this.imageView;
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