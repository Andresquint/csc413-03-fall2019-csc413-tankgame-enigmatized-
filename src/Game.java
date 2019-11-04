import UILoadingMenu.SplashPageDriver;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

public class Game extends JFrame implements Runnable{

    private static final long serialVersionUID = 1L;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Texture> textures;
    public static Camera camera;
    public Screen screen;
    public static int[][] map =
            {
                    {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                    {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
            };
    ArrayList<Entity> enemies;
    public static LevelInfo levelInfo;
    public static int screenWidth = 640;//640
    public static int screenHeight = 480;//480
    public static Graphics g;
    int tickCount=0;

    //Health Bar Assets
    private ImageIcon HB = new ImageIcon("res/Interface/HealthBarBorder.png");//Creates the HealthBarBorder
    private Image HealthBarBorder = HB.getImage();


    public Game() throws InterruptedException, IOException {
        new SplashPageDriver();

        levelInfo= new LevelInfo();

        Thread.sleep(2000);
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<Texture>();
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
        enemies= new ArrayList<>();
        enemies.add(new Entity(3.5, 3.5));
        screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
        addKeyListener(camera);
        setSize(640, 480);
        setResizable(false);
        setTitle("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }
    private synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void render() throws IOException {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        if(camera.getHealth() <= 100){
//            g.drawImage(getSizeOf(0,0,(int)(136*camera.getHealth()), 36, "res/Interface/HealthBar.png"), (int)(screenWidth*.60), (int)(screenHeight*.80), (int)((screenWidth*.3445)*camera.getHealth()), (int)(screenHeight*.075), this);
        }
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        //TOdo
        //Customize the x and Y for each weapon
        g.drawImage(camera.currentWeapon.getImage(), -67, 80, image.getWidth(), (int)(image.getHeight()*.95), null);
        g.drawImage(getSizeOf(0,0,(int)(136*camera.getHealth()), 36, "res/Interface/HealthBar.png"), (int)(screenWidth*.05), (int)(screenHeight*.90), (int)((screenWidth*.3445)*camera.getHealth()), (int)(screenHeight*.075), null);
        bs.show();
    }
    public void run() {
        //TODO
        //add a frame persecond counter
        //Helpful just to see whats up
        long lastTime = System.nanoTime();
        int frames=0;
        int updates=0;
        final double ns = 1000000000.0 / 60.0;//60 times per second //Adding the 60.0 is important for percision, otherwise java treats it like int
        double delta = 0;
        requestFocus();
        while(running) {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 60 times a second
            {
                //Update Sprite

                //handles all of the logic restricted time

                screen.update(camera, pixels);
                camera.update(map);


                //Update Sprites
                //BTW a java Stream would likely be better


//                for(int spriteNum=0;spriteNum<levelInfo.getEnemiesListSize();spriteNum++){
//                    levelInfo.getEnemyFromEnemyList(spriteNum).updateBehavior();
//                    levelInfo.getEnemyFromEnemyList(spriteNum).updatePlayer();
//                }


                //Used for testing Sprite Movement
                if(tickCount>200){
                    //levelInfo.getEnemies().get(0).updateBehavior();
                    levelInfo.getEnemyFromEnemyList(0).updateBehavior();
                    levelInfo.getEnemyFromEnemyList(0).updatePlayer(map);
                    System.out.println("Updating Sprite Behavior");}
                tickCount++;
                delta--;

            }
            try {
                render();//displays to the screen unrestricted time
            } catch (IOException e) {
                System.out.println("Oh shit rendering error, probably a loading image error from health bar");
                e.printStackTrace();
            }
        }
    }
    public static void main(String [] args) throws InterruptedException, IOException {
        Game game = new Game();
    }

    /**
     * When called plays the sound that is specified.
     *
     * @param s	is the string of the location of the file. Currently can only take .wav files
     */
    public static void playSound(String s){
        try{
            File soundFile = new File(s);
            AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip;
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This is used to cut and/or grab images from a sheet image
     *
     * @param startX	The starting x location to begin the cut
     * @param startY	The starting y location to begin the cut
     * @param endX	The ending x location of the cut
     * @param endY	The ending y location of the cut
     * @param s		The location of the image to be used
     * @return returns the cropped image that will be used
     */
    public BufferedImage getSizeOf(int startX, int startY, int endX, int endY, String s) throws IOException{
        BufferedImage img = ImageIO.read(new File(s));
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
        BufferedImage toReturn = copyOfImage.getSubimage(startX, startY, endX, endY);
        Graphics g = toReturn.getGraphics();
        g.drawImage(img, 0, 0, null);
        return toReturn;
    }


}