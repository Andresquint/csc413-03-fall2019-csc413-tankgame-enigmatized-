package UILoadingMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Iconz {

public static ImageIcon loadingPageImage= new ImageIcon("./res/cs.png", "yes");
    private InputStream fis = null;
    private BufferedInputStream bis = null;





    protected BufferedImage imageLoader(String path) {
        try {
            fis = getClass().getClassLoader().getResourceAsStream(path);
            bis = new BufferedInputStream(fis);
            return ImageIO.read(bis);
        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);// exit with 1 to accept errors
        }

        return null;
    }

    public ImageIcon iconLoader(String path) {
        // https://stackoverflow.com/questions/25635636/eclipse-exported-runnable-jar-not-showing-images
        URL resource = Iconz.class.getResource(path);
        return new ImageIcon(resource);
    }


}
