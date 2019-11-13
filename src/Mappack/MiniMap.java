package Mappack;

import Entity.Entity;
import Entity.Enemy;
import Entity.Powerup;
import Game.Game;
import Game.Camera;

import java.awt.*;

public class MiniMap {
    private static int SCALE=5;
    private static Color WALLCOLOR= Color.WHITE;
    private static Color ENTITYCOLOR=Color.RED;
    private static Color BASECOLOR=Color.BLACK;
    private static Color POWERUP=Color.BLUE;
    private static Color PLAYER=Color.YELLOW;
    private static int xpos=500;
    private static int ypos=75;


    int SIZE;
    int Mappixel[][];
    int entitylocations[][];



        public MiniMap(Map map){
            int height=map.map.length;
            int width=map.map[0].length;
            Mappixel= new int[width*SCALE][height*SCALE];
            entitylocations= new int[width*SCALE][height*SCALE];
            for(int row=0; row<height; row++){
                for(int col=0; col<width; col++){
                    if(map.map[row][col]==0) fill(row, col, BASECOLOR);
                    else fill(row, col, WALLCOLOR);
                }
            }

            //generate Map Pixels, using map
            //


        }

        private void fill(int row, int col, Color color){
            row=row*SCALE;
            col=col*SCALE;
            for(int i=row; i<row+SCALE; i++){
                for(int j=col; j<col+SCALE; j++){
                    Mappixel[i][j]=color.getRGB();
                }
            }

        }

    private void efill(int row, int col, Color color){
        row=row*SCALE;
        col=col*SCALE;
        for(int i=row; i<row+SCALE; i++){
            for(int j=col; j<col+SCALE; j++){
                entitylocations[i][j]=color.getRGB();
            }
        }

    }

        //2 functions needed
        //
        //1)Generate entity Locattion
        //2)render Function(take in Pixel array){
        //Copy Map pixels to pixels with scaling
        //
        //


        public int[] paint(int[] pixels, int width, Camera c){
            for(int row=0; row<Mappixel.length; row++) {
                for (int col = 0; col < Mappixel[0].length; col++) {
                    entitylocations[row][col]=BASECOLOR.getRGB();


                }
            }
            for(Entity e: Game.levelInfo.getEnemies()){
                if(e instanceof Enemy) {
                    efill((int) e.yPos, (int) e.xPos, ENTITYCOLOR);
                } else if(e instanceof Powerup){
                    efill((int) e.yPos, (int) e.xPos, POWERUP);
                }

            }
            efill((int)c.yPos, (int)c.xPos, PLAYER);
            for(int row=0; row<Mappixel.length; row++){
                for(int col=0; col<Mappixel[0].length; col++){
                    pixels[(row+ypos)*width+col+xpos]=Mappixel[row][col];//   pixels[y1 * width + x] = texel;
                    if(entitylocations[row][col]!=BASECOLOR.getRGB()){
                        pixels[(row+ypos)*width+col+xpos]=entitylocations[row][col];
                    }
                }
            }
            return pixels;
        }

    //Make
}
