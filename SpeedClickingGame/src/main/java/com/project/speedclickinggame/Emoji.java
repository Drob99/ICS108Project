package com.project.speedclickinggame;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//the class that defines the emoji(extends ImageView to be able to display the images)
public class Emoji extends ImageView implements Cloneable{
    private final int END_OF_GAME = 30;
    private String imgName;         //the name of the emoji image file
    private int score;              //the score for the individual emoji when clicked
    private boolean random;         //determining weather the emoji changes randomly when clicked
    private static int counter;     //a counter for the number of emojis dropped in a round
    private static final Emoji[] emjList = {new Emoji("Happy.png", 3, false), new  Emoji("Sad.png", -1, false), new Emoji("Mid.png", 1, false)};
    //a reference to choose the random emoji from
    private final int NUM_OF_EMOJIS = 3;

    //constructor initializing the instance variables
    public Emoji(String imgName, int score, boolean random) {
        //setting the image
        super(new Image(imgName));

        //initializing the instance variables
        this.imgName = imgName;
        this.score = score;
        this.random = random;
    }

    //a getter for the score of the emoji
    public int getScore() {
        return score;
    }

    //a method to clone an emoji for the random emoji('random' is always 'true' because this method is only used with the random emoji)
    @Override
    public Emoji clone() {
        return new Emoji(imgName, score, true);
    }

    //resetting the emoji to drop from the top and counting the number of emojis dropped
    public boolean prepareEmoji() {
        //a flag to check if the game is over or not
        if(counter >= END_OF_GAME) {
            this.setVisible(false);
            return true;
        }

        //updating the number of emojis dropped
        ++counter;

        //randomizing the emoji if it is random
        if(random) {
            int r = (int) (Math.random() * NUM_OF_EMOJIS);
            score = emjList[r].score;
            imgName = emjList[r].imgName;
            try {
                this.setImage(new Image(imgName));
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        //setting the position and specifications of the emoji
        setStyle("-fx-background-color: transparent");
        setFitWidth(100);
        setFitHeight(100);
        setY(-100);
        setX((int)(Math.random() * 701));
        setCursor(Cursor.HAND);

        //telling that the game is not over just yet
        return false;
    }

    //a static method to reset the count of emojis dropped when replaying the game
    public static void reset() {
        counter = 0;
    }
}