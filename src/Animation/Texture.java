package Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * This class loads 90% of the images in the game.
 * It loads the walls
 * It is used for the flipbook effect for TextureAnimation
 */


public class Texture {
    public int[] pixels;
    private String loc;
    public final int SIZE;
    //Slight change because intellij is Picky
    int width;
    int height;
    BufferedImage image;





    public Texture(String location, int size) throws IOException {
        //BufferedImage image = ImageIO.read(new File(location));
        loc = location;
        SIZE = size;
        this.pixels=new int[SIZE * SIZE];
        load();
        //this.pixels= image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        this.width = image.getWidth();
        this.height = image.getHeight();
       //load();

    }


    public Texture(String location, int width, int height) throws IOException {
        //BufferedImage image = ImageIO.read(new File(location));
        loc = location;
        SIZE=width;

        this.pixels=new int[SIZE * height];
        load();
        //this.pixels= image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        this.width = image.getWidth();
        this.height = image.getHeight();
        //load();

    }



    /**
     * 	For when the image is provided and cropping is needed
     * @param image	the image to me cropped
     * @param size	The size of the image
     * @param startX	the starting x location to crop
     * @param startY	the starting y location to crop
     */
    public Texture(BufferedImage image, int size, int startX, int startY){
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        width = SIZE;
        height= SIZE;
        image.getRGB(startX, startY, width, height, pixels, 0, width);
    }





    /**
     * creates a texture to use in the game
     * @param location	The location the the texture on the computer
     * @param size	The square size of the texture ie 64x64 pixel texture = 64 as size
     * @param startX	The starting x location to crop the image
     * @param startY	The starting y location to crop the image
     */
    public Texture(String location, int size, int startX, int startY) {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load(startX, startY);
    }


//    private void load() throws IOException {
//        BufferedImage image = ImageIO.read(new File(loc));
//        int w = image.getWidth();
//        int h = image.getHeight();
//        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
//        image.getRGB(0, 0, w, h, pixels, 0, w);
//    }


    private void load() {
        try {
            image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * loads the image into the game by converting it into many pixels
     * @param startX	The starting x location to pixelate the image
     * @param startY	The starting y location to pixelate the image
     */
    private void load(int startX, int startY) {
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = SIZE;
            int h = SIZE;
            image.getRGB(startX, startY, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getPixel(int u, int v) {
        return pixels[v *width + u];
    }

    public int getPixel(double u, double v) {
        return getPixel((int)(u * width) % width, (int)(v * height) % height);
    }


    private int getPixelClipping(int u, int v) {
        return getPixel(u % width, v % height);
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
/*        if(width>64) return 64;
        else*/
            return width;
    }

    public int getHeight() {
        return height;
    }




    //TODO
    //Final phase=>grace stage:
    //Make a static blog instead as exemplified in class
//    public static Animation.Texture wood = new Animation.Texture("res/wood.png", 64);
//    public static Animation.Texture brick = new Animation.Texture("res/redbrick.png", 64);
//    public static Animation.Texture bluestone = new Animation.Texture("res/bluestone.png", 64);
//    public static Animation.Texture stone = new Animation.Texture("res/greystone.png", 64);


    public static Texture wood;
//USE SKINGLTON PATTERN HERE!
    //CLASS SCHEDULE
    static {
        try {
            wood = new Texture("resources/wood.png", 64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Texture brick;

    static {
        try {
            brick = new Texture("resources/redbrick.png", 64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Texture bluestone;

    static {
        try {
            bluestone = new Texture("resources/bluestone.png", 64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Texture stone;

    static {
        try {
            stone = new Texture("resources/greystone.png", 64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //public static Animation.Texture enemySimple = new Animation.Texture("res/enemy.png", 64);
    //public static Animation.Texture loadingPageBackGround = new Animation.Texture("res/wood.png", 64);

    public static Texture nothing;

    static {
        try {
            nothing = new Texture("resources/nothing3232.png", 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Texture terroist;

    static {
        try {
            nothing = new Texture("resources/terr.png", 64);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Texture walkingRobot1;

    static {
        try {
            nothing = new Texture("resources/WalkingDude/rsz_1robot1.png", 64);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Texture walkingRobot2;

    static {
        try {
            nothing = new Texture("resources/WalkingDude/rsz_robot2.png", 64);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



//    public static Texture deadterroist;
//
//    static {
//        try {
//            nothing = new Texture("res/deadterr.png", 16);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}