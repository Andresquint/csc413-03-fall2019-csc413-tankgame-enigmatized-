package Animation;

import Animation.Texture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
/**
 * Object that stores an image with a given filname
 * and creates a "flipbook" effect via a creation of
 * frames from the original image. This is designed
 * to be done at a rate of 60 frames per second outside
 * of this Object.
 *
 * @author Michael Mueller, Paul Coen, Dakota Oria
 *
 */
public class TextureAnimation {
    //Default and custom delays between frames
    final static int FRAME_DELAY = 5;
    int frameDelay;
    int timer;
    private static int rand;

    //current frame of animation and animation frames
    int currentFrame;
    ArrayList<Texture> animationFrames;
    Texture[] myAnimation= new Texture[]{new Texture("res/rsz_powerup.png", 64, 64), Texture.stone, new Texture("res/WalkingDude/rsz_1robot1.png", 64, 64)  };
    Texture[] myPowerUpAnime=new Texture[]{new Texture("res/rsz_powerup.png", 64, 64),};
    //Name of animation spreadsheet
    String filename;

    //Whether or not to loop the animation
    boolean looping;

    Random random = new Random();






    /**
     * Creates an animation with a default frame delay,
     * a desired animation spreadsheet, and a certain
     * amount of frames
     *
     * @param s Name of file
     * @param frameNum number of frames
     */
    public TextureAnimation(String s, int frameNum) throws IOException {
        this(s, frameNum, FRAME_DELAY);
    }

    /**
     * Creates an animation with a default frame delay,
     * a desired animation spreadsheet, a certain amount
     * of frames, and a specific frame delay.
     *
     * @param s Name of file
     * @param frameNum Number of frames
     * @param frameDelay Amount of time between frames
     */
    public TextureAnimation(String s, int frameNum, int frameDelay) throws IOException {
        this(s, frameNum, frameDelay, true);//assumes that the animation is loopable
    }

    /**
     * Creates an animation with a default frame delay,
     * a desired animation spreadsheet, a certain amount
     * of frames, a specific frame delay, and whether or
     * not the animation is loopable.
     *
     * @param s Name of file
     * @param frameNum Number of frames
     * @param frameDelay Amount of time between frames
     * @param loopable Specifies if animation is loopable
     */
    public TextureAnimation(String s, int frameNum, int frameDelay, boolean loopable) throws IOException {
        this.frameDelay = frameDelay;
        looping = loopable;
        animationFrames = new ArrayList<>();
        currentFrame = 0;
        for(int i = 0; i < frameNum; i++){
            Texture temp = new Texture(s, 64, 0, (i*64));
            animationFrames.add(temp);
        }
        //System.out.println("Loaded Textures for: " + s);
    }


    public TextureAnimation(Texture[] s, int frameDelay, boolean loopable) throws IOException {
        this.frameDelay = frameDelay;
        looping = loopable;
        animationFrames = new ArrayList<>();
        currentFrame = 0;
        for(int i = 0; i < s.length; i++){
           animationFrames.add(s[i]);
        }
        //System.out.println("Loaded Textures for: " + s);
    }





    /**
     * Cycles the animation, returning the next image of
     * the spreadsheet. Will reset the animation if
     * desired.
     *
     * @return Current frame of animation.
     */
    public Texture Animate(){


//        for(int i =0; i<2; i++){
//            int randomInteger= random.nextInt(3);
//            System.out.println("pseudo random int in a range : " + randomInteger);
//            rand= randomInteger;
//        }

//        Texture result = animationFrames.get(rand);

        if(timer>frameDelay){
            timer =0;
            currentFrame++;
        }
        timer++;
        ensureCurrentFrame();
        return animationFrames.get(currentFrame);
    }




    /**
     * Ensures that the animation is not outside of
     * its limit of frames.
     */
    public void ensureCurrentFrame(){
        currentFrame =currentFrame%animationFrames.size();
    }

    /**
     * Determines if an animation has completed its cycle at
     * a given frame.
     *
     * @return
     * True if the animation has just completed.
     * False if the animation is in progress.
     */
    public boolean animationFinished(){
        if(animationFrames.size()>1)
            return currentFrame == animationFrames.size();
        else
            return timer%frameDelay==0;
    }


    public TextureAnimation(String s, String r, int frameNum, int frameDelay, boolean loopable) throws IOException {
        this.frameDelay = frameDelay;
        looping = loopable;
        //Todo Will to change this so I actually load from file loaction
        //This is just a test run
        animationFrames= new ArrayList<>();
        currentFrame = 0;
        for(int i = 0; i < frameNum; i++){
            Texture temp=myAnimation[i];
            animationFrames.add(temp);
        }
        System.out.println("Loaded Textures for: " );
    }



    public static TextureAnimation enemyAlive;
    static {
        try {
            Texture[] t={ new Texture("res/WalkingDude/rsz_1robot1.png", 64, 64),
                        new Texture("res/rsz_terrorist.png", 64, 64)};
            enemyAlive= new TextureAnimation(t, 2, true);
           } catch (IOException e) {
            e.printStackTrace();
        }

    }








}



