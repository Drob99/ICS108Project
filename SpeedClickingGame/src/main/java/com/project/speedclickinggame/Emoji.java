package com.project.speedclickinggame;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Emoji extends ImageView implements Cloneable{
    private String imgName;
    private int score;
    private boolean random;
    private static int counter;
    private static Emoji[] emjList = {new Emoji("Happy.png", 3, false), new  Emoji("Sad.png", -1, false), new Emoji("Mid.png", 1, false)};
    public Emoji(String imgName, int score, boolean random) {
        super(new Image(imgName));
        this.imgName = imgName;
        this.score = score;
        this.random = random;
        counter = 0;
        prepareEmoji();
    }
    public int getScore() {
        return score;
    }

    public Emoji cloneR() {
        return new Emoji(imgName, score, true);
    }
    public boolean prepareEmoji() {
        if(counter >= 30) {
            this.setVisible(false);
            return true;
        }
        ++counter;
        if(random) {
            int r = (int) (Math.random() * 3);
            score = emjList[r].score;
            imgName = emjList[r].imgName;
            this.setImage(new Image(imgName));
        }
        setStyle("-fx-background-color: transparent");
        setFitWidth(100);
        setFitHeight(100);
        setY(-100);
        setX((int)(Math.random() * 701));
        setCursor(Cursor.HAND);
        return false;
    }
}