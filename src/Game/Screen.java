package Game;

import Animation.Texture;
import Entity.Entity;
import Mappack.Map;
import Mappack.MiniMap;
import RayCasting.MathAssist;

import java.util.ArrayList;
import java.awt.Color;
import java.util.Collections;


public class Screen {
    //public int[][] map;
    public Map map;
    public int mapWidth, mapHeight, width, height;
    public ArrayList<Texture> textures;  //This will be used for enemies later
    private MiniMap mm;


    private double[] zBuffer;
    private int[] buffer;

    private int verticalDisplace = 0;
    private int gunVerticalDisplace = 0;
    private int spritesOnMapX;
    private int spritesOnMapY;
    int mapX;
    int mapY;
    int stepXSprite;
    int stepYSprite;
    double rayDirXSpriteRender;
    double rayDirYSpriteRender;
    int lineHeightFORSPRITE;



//    public void tick(Game.Camera camera, double frameTime) {
//
//        camera.update(Game.Game.levelInfo, frameTime);
//
//
//        // look up/down amount same for every pixel
//        int yShear = (int) (height * camera.yShear);
//
//
//        // odd shearing amount causes texture mapping to break
//        if (yShear % 2 != 0) {
//            yShear += (yShear < 0) ? -1 : 1;
//        }
//    }

    public Screen(Map m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h) {
        map = m;
        mapWidth = mapW;//Will equal 15, related to Matrix in Game.Game.java
        mapHeight = mapH;
        textures = tex;///Is this really the best way to deal with this?
        //Meaning having enemies as in a array list and passing it through the screen constructor?
        width = w; //Pixil of screen //640
        height = h;
        this.zBuffer = new double[width];
        mm= new MiniMap(m);

    }

    private int clipVertically(int y) {
        return (y >= height) ? height - 1 : (y < 0) ? 0 : y;
    }

    private int clipHorizontally(int x) {
        return (x >= width) ? width - 1 : (x < 0) ? 0 : x;
    }

    public int[] update(Camera camera, int[] pixels) {
        //Note: screen renders half the screen two different colors first
        //This later creates the floor and sky/cieling
        for (int n = 0; n < pixels.length / 2; n++) {
            //Fills in the cielling/sky
            if (pixels[n] != Color.DARK_GRAY.getRGB()) pixels[n] = Color.RED.getRGB();
        }
        for (int i = pixels.length / 2; i < pixels.length; i++) {
            //Fills in the ground
            if (pixels[i] != Color.gray.getRGB()) pixels[i] = Color.gray.getRGB();
        }//Proccess ends

        //Beginning proccess of RaYcasting walls


        for (int x = 0; x < width; x = x + 1) {     //Width=ScreenWidth is set for 640
            //Not 100% sure what this line is?
            // oh. so we are dividing by width, -1, but I don't totally get?
            //Todo
            //Get this line?
            //It used for the trig like function below
            double cameraX = 2 * x / (double) (width) - 1;//The current vertical slice.-->OH VERTICAL SLICE OF CAMERA VIEW!!!

            //oh So it is the ray for the specific pixil that it is at?
            //Wait so this is is a flor floop, oh gosh I think I get why this is so slow down below

            //Creates a ray vector that will be "casted" into the environment
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;


            //Mappack.Map position
            //Mappack.Map position means within the array of MAP
            //Confusion: how is camerea able to give its map array cordinates?
            //AKA how are they related
            mapX = (int) camera.xPos;
            mapY = (int) camera.yPos;

            //length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;

            //Length of ray from one side to next in map
            //Pythagorean theorem and some scaling
            //Think of it like scaleFactor = 1/rayDirX;  --->scaledX = rayDirX * (1/rayDirX) = 1
            //Now, we want to know the length. Now Pythagorean comes into play.
            //Which is length = sqrt((x * x) + (y * y))
            double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
            double perpWallDist;
            //Direction to go in x and y
            int stepX, stepY;

            //Setting up if wall is hit
            boolean hit = false;
            //Setting up to check if wall vertical or horizontal
            int side = 0;
            //Figure out the step direction and initial distance to a side
            //Detects the direction the ray is being casted
            //If the direction in either direction is negative,
            //the step will be negative and the sideDistX is calculated.
            //Else, the step will be positive and the sideDist is calculated.
            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (camera.xPos - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (camera.yPos - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
            }
            //Knowing the information of the increments for the ray in the x and y
            //direction, the ray is extended via the below loop until it collides
            //and detects a wall.

            //Loop to find where the ray hits a wall
            while (!hit) {
                //Jump to next square
                //Checks if the ray's vector should be incremented by its sideDist X or sideDistY
                //Depending on which is more dominant in the vector's growth.
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                //Check if ray has hit a wall
                //System.out.println(mapX + ", " + mapY + ", " + map[mapX][mapY]);
                if (map.map[mapX][mapY] > 0) hit = true;
            }

            //Calculate distance to the point of impact
            if (side == 0)
                perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
            else
                perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
            //Now calculate the height of the wall based on the distance from the camera
            zBuffer[x] = perpWallDist;
            int lineHeight;
            if (perpWallDist > 0) lineHeight = Math.abs((int) (height / perpWallDist));
            else lineHeight = height;
            //calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + height / 2;
            if (drawStart < 0)
                drawStart = 0;
            int drawEnd = lineHeight / 2 + height / 2;
            if (drawEnd >= height)
                drawEnd = height - 1;
            //add a texture
            int texNum = map.map[mapX][mapY] - 1;
            double wallDeltaX;
            double wallDeltaY;
            double wallX;//Exact position of where wall was hit
            if (side == 1) {//If its a y-axis wall
                wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
                //Getting exact position on the wall it is hitting
                wallDeltaX = wallX - Math.floor(wallX);
                wallDeltaY = (stepX == -1) ? 0 : 1;
            } else {//X-axis wall
                wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
                wallDeltaY = wallX - Math.floor(wallX);
                wallDeltaX = (stepY == -1) ? 0 : 1;
            }
            wallX -= Math.floor(wallX);//point value
            //x coordinate on the texture
            int texX = (int) (wallX * (textures.get(texNum).SIZE));

            //Flipping texture around based on what viewing angle
            if (side == 0 && rayDirX > 0) texX = textures.get(texNum).SIZE - texX - 1;
            if (side == 1 && rayDirY < 0) texX = textures.get(texNum).SIZE - texX - 1;
            //calculate y coordinate on texture

            //For each row we draw pixel that row
            for (int y = drawStart; y < drawEnd; y++) {
                int texY = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2;
                int color;
                if (side == 0) color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)];
                else
                    color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)] >> 1) & 8355711;//Make y sides darker
                pixels[x + y * (width)] = color;
            }

            double wallHitX = mapX + wallDeltaX;
            double wallHitY = mapY + wallDeltaY;

            for (Entity enemy : Game.levelInfo.getEnemies()) {
                enemy.distance=MathAssist.distanceBetweenPoints(camera.xPos, camera.yPos, enemy.xPos, enemy.yPos);

            }
            Collections.sort(Game.levelInfo.getEnemies(), Collections.reverseOrder());
            for (Entity enemy : Game.levelInfo.getEnemies()) {
                double hitDist = (enemy.getDistFromLine(camera.xPos, camera.yPos, wallHitX, wallHitY));

                if (hitDist < 0.5) {
                    double hitside = enemy.getSideFromLine(camera.xPos, camera.yPos, wallHitX, wallHitY);
//
//                double spriteX = enemy.xpos - camera.xPos;
//                double spriteY = enemy.ypos - camera.yPos;
//
//                //  transform - multiply by inversion of camera matrix.
//                double invDet = 1.0 / (camera.xPlane * camera.yDir - camera.xDir * camera.yPlane);
//                enemy.xTransformed = invDet * (camera.yDir * spriteX - camera.xDir * spriteY);
//                enemy.yTransformed = invDet * (-camera.yPlane * spriteX + camera.xPlane * spriteY);
//
//                // center point of the sprite on screen and its height
                    //int spriteScreenX = (int) ((width / 2) * (1 + enemy.xTransformed / enemy.yTransformed));
                    int spriteHeight = (height) / 2;

                    //  assume sprites are squares, so only one dimension is needed to be calculated
                    //   but store in separate vars for the future
//                int spriteHeight = (int) Math.abs(height / enemy.yTransformed);
//                int spriteWidth = spriteHeight;

//                int drawStartX = clipHorizontally(-spriteWidth / 2 + spriteScreenX);
//                int drawEndX = clipHorizontally(spriteWidth / 2 + spriteScreenX);
//                    int drawStartY = clipVertically(-spriteHeight / 2 + (height) / 2);
//                    int drawEndY = clipVertically(spriteHeight / 2 + (height) / 2);

                    //Animation.Texture texture = enemy.getTexture();

                    // draw the sprite
                    Texture texture = enemy.getTexture();

                    double perpEnemyDist = MathAssist.distanceBetweenPoints(camera.xPos, camera.yPos, enemy.xPos, enemy.yPos);
                    if (perpEnemyDist > 0) lineHeight = Math.abs((int) (height / perpEnemyDist));
                    else lineHeight = height;
                    //calculate lowest and highest pixel to fill in current stripe
                    int drawStartY = -lineHeight / 2 + height / 2;
                    if (drawStartY < 0)
                        drawStartY = 0;
                    int drawEndY = lineHeight / 2 + height / 2;
                    if (drawEndY >= height)
                        drawEndY = height - 1;
                    // System.out.println("drawStartY: " + drawStartY + "\tdrawEndY: " + drawEndY);

                    int u;
                    //left side
                    if (hitside < 0) {
                       // System.out.print(texture.getWidth());
                        u = (int) ((0.5 - hitDist) * texture.getWidth());
                    } else {//Right side
                        u = (int) ((0.5 + hitDist) * texture.getWidth());
                    }

                    u=Math.abs(u);

                    //Used to find center of sprite for Hitting and missing attacks
                    Game.levelInfo.getEnemyFromEnemyList(0).centerX= (drawStart+drawEnd)/2;

                    // if is in front of the camera (yTransformed > 0) but before the wall
                    for (int y = drawStartY; y < drawEndY; y++) {
                        if (perpEnemyDist <= zBuffer[x]) {
                            int v = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2;
                            //Also intresting note.
                            //Pixel/image size doesn't seem to change the print out on screen.
                            //32by32 is the same size of 64?
                            //This is the problem child!!!

                            //Try catch block returns black
                            int texel = /*Color.WHITE.getRGB();*/ texture.getPixel(u, v);//When I make u -> X it renders the more correct image, but slow as shit

                            //How would you fix that?
                            //I think it has to be relative to X

                            //Weird the second time I tried I now get it to crash.

                            if (texel != Color.WHITE.getRGB()) {
                                // apply effects if required
                                int y1 = clipVertically(y + verticalDisplace);
                                pixels[y1 * width + x] = texel;
                            }
                           /* zBuffer[x] = perpEnemyDist;*/
                        }
                    }
                }
            }
        }
        int midH=height/2;
        int midW=width/2;
        if(camera.shooting){
            for(int y=midH; y<height;y++){
                pixels[y*width+midW]=Color.RED.getRGB();
            }


        }
        pixels=mm.paint(pixels, width, camera);
        return pixels;

    }


    public void renderSprite(double x, double y, double z) {


    }

}



