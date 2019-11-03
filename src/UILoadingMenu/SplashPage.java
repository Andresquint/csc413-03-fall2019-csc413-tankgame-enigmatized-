package UILoadingMenu;

import com.sun.glass.ui.Pixels;
import javafx.scene.control.ProgressBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;


public class SplashPage extends JWindow {

    private BorderLayout borderLayout;
    private JLabel imgLabel;
    private JPanel end;
    private FlowLayout endFlow;
    private JProgressBar bar;
    private ImageIcon imageIcon ;
    //JLabel contentPane = new JLabel();


    public SplashPage()  {


        this.imageIcon = new ImageIcon();
        this.borderLayout = new BorderLayout();
        this.imgLabel = new JLabel();
        this.end = new JPanel();
        this.bar = new JProgressBar();
        this.endFlow = new FlowLayout();
        this.imageIcon = new ImageIcon();
        bar.setStringPainted(true);
        try {
            init();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    private void init() throws Exception{
        imgLabel.setIcon(imageIcon);
        getContentPane().setLayout(borderLayout);
        end.setLayout(endFlow);
        end.setBackground(Color.BLACK);
        getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(new File("./res/cs1.png")))));
       getContentPane().add(end, BorderLayout.SOUTH);
        end.add(bar, null);
        pack();
    }

    public void setMaxProgress(int maxProgress){
        bar.setMaximum(maxProgress);

    }

    public void setProgress(int progress){
        float percentage = ((float)progress/(float)bar.getMaximum() *100);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                bar.setValue(progress);
                bar.setString("Loading: " + percentage+ "%");
            }
        });


    }
    public ImageIcon iconLoader(String path) {
        // https://stackoverflow.com/questions/25635636/eclipse-exported-runnable-jar-not-showing-images
        URL resource = Iconz.class.getResource(path);
        return new ImageIcon(resource);
    }


}
