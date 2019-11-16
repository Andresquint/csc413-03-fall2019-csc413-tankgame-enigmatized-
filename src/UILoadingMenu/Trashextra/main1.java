package UILoadingMenu.Trashextra;


import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import Game.Game;
import UILoadingMenu.LoadingMusic;
import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;

public class main1 extends Application{

    private Parent createContent() {
        Pane root = new Pane();

        root.setPrefSize(1050, 600);

        try(InputStream is = Files.newInputStream(Paths.get("resources/cs.png"))){
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1050);
            img.setFitHeight(600);
            root.getChildren().add(img);
        }
        catch(IOException e) {
            System.out.println("Couldn't load image");
        }

        Title title = new Title ("COUNTER-STRIKE");
        title.setTranslateX(150);
        title.setTranslateY(200);

        MenuBox vbox = new MenuBox(
                (new MenuItem("Play De_Dust")),
                new MenuItem("Play Space_Ship"),
                new MenuItem("NUll Button"),
                new MenuItem("Credits"),
                new MenuItem("Exit Game.Game"));

        vbox.setTranslateX(100);
        vbox.setTranslateY(300);

        LoadingMusic intro = new LoadingMusic();
        //intro.play("/DefenseLine.mp3");

        root.getChildren().addAll(title,vbox);

        return root;

    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("VIDEO GAME");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class Title extends StackPane{
        public Title(String name) {
            Rectangle bg = new Rectangle(375, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
        }
    }

    private static class MenuBox extends VBox{


        public MenuBox(MenuItem...items) {
            getChildren().add(createSeperator());

            for(MenuItem item : items) {
                getChildren().addAll(item, createSeperator());

            }





        }



        private Line createSeperator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }

    }


    private static class MenuItem extends StackPane{
        public String name;
        public MenuItem(String name) {
            this.name=name;
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.DARKBLUE),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKBLUE)

            });

            Rectangle bg = new Rectangle(200,30);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);

            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });
            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET);
                System.out.println("ButtonPushed");
                //TODO
                //Make this a hashmap, or seperate method.
                //Also this is the game will play multiple maps
                if(this.name.equals("Play De_Dust")) System.out.println(" Play De_Dust");
                if(this.name.equals("Play Space_Ship")) ;

            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });



        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}