package UILoadingMenu.Trashextra;

import UILoadingMenu.SplashPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SplashPageDriver {

    private SplashPage screen;

    public SplashPageDriver() {

        screen = new SplashPage();
        screen.setLocationRelativeTo(null);
        screen.setMaxProgress(1000);
        screen.setVisible(true);

        for (int i=0; i<=1000; i++){
            for (int j=0; j<=50000; j++){
                String t = "ewf"+(i+j);

            }
            screen.setProgress(i);
        }
        screen.setVisible(false);
    }
}


//TODO
//Delete this test class after finished
//Or re-name it and add it to a test_ui folder
//But that is probably a stupid idea
//Just get rid of or save for another time.
 class Test {

    public static void main(String[] args) {

        JFrame f = new JFrame();
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("./res/cs1.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JMenuBar jmb = new JMenuBar();

        JMenu jmFile = new JMenu("File");
        JMenuItem jmiOpen = new JMenuItem("Open");
        JMenuItem jmiClose = new JMenuItem("Close");
        JMenuItem jmiSave = new JMenuItem("Save");
        JMenuItem jmiExit = new JMenuItem("Exit");
        jmFile.add(jmiOpen);
        jmFile.add(jmiClose);
        jmFile.add(jmiSave);
        jmFile.addSeparator();
        jmFile.add(jmiExit);
        jmb.add(jmFile);
        f.setJMenuBar(jmb);
        JButton startGameButton = new JButton("Start Game.Game");
        JButton exitGameButton = new JButton("Exit");
        f.add(startGameButton);
        f.add(exitGameButton);

        f.pack();
        f.setVisible(true);
    }

}