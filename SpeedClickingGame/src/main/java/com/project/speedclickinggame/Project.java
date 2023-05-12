package com.project.speedclickinggame;

import javafx.application.Application;
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

//This class works with the welcome stage which is first opened to the user.
public class Project extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        //The major pane of the 1st Scene
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(Background.fill(Paint.valueOf("Teal")));
        Border border = new Border(new BorderStroke(Paint.valueOf("Yellow"), BorderStrokeStyle.DASHED, new CornerRadii(5), new BorderWidths(7)));
        borderPane.setBorder(border);


        //where the text will be at the center of the borderpane
        StackPane stackPane = new StackPane();
        stackPane.setBackground(Background.fill(Paint.valueOf("Teal")));

        //hbox which will have the button in the center later
        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));

        //creating a text 
        Text text = new Text("Welcome to the Game.\nClick on Button to Start.");
        text.setFont(Font.font("Vivaldi", 75));
        text.setFill(Paint.valueOf("Yellow"));
        text.setTextAlignment(TextAlignment.CENTER);

        //adding the text to stackPane
        stackPane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        //creating a button and customizing its appearance and properties
        Button button = new Button("START");
        button.setPrefWidth(500);
        button.setPrefHeight(200);
        button.setBackground(Background.fill(Paint.valueOf("Teal")));
        button.setCursor(Cursor.HAND);
        button.setFont(Font.font("Jokerman", 50));
        button.setOnAction(e -> changeStages((Stage) button.getScene().getWindow()));

        //adding the button to the hbox
        hBox.getChildren().add(button);


        //Adjusting the alignments
        borderPane.setCenter(stackPane);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        //new Scene with customized fixed size
        Scene scene = new Scene(borderPane, 800, 700);

        //adding and scene 1 to stage and customizing
        stage.setResizable(false);
        stage.setTitle("Speed Clicking Game");
        stage.setScene(scene);
        stage.show();
    }

    //This changes the scenes into the one in which the playing occurs
    public void changeStages(Stage stage) {
        stage.close();
        Stage2 stage2 = new Stage2();
        Stage stage1 = new Stage();
        stage2.start(stage1);
    }
}