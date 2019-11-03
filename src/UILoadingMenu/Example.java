package UILoadingMenu;

import javafx.event.ActionEvent;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.*;

public class Example extends JFrame {
    static JTextField textfield1, textfield2, textfield3;
    static Button[] buttons = new Button[2];

    public static void main(String[] args) {
        JButton startGameButton = new JButton("Start Game");
        JButton exitGameButton = new JButton("Exit");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Does this really work?");
            }
        });
        JFrame f = new JFrame("Text Field Examples");
        f.add(startGameButton);
        f.add(exitGameButton);
        JTextField txtItem = new JTextField(10);
        f.getContentPane().setLayout(new FlowLayout());
        textfield1 = new JTextField("Text field 1",10);
        textfield2 = new JTextField("Text field 2",10);
        textfield3 = new JTextField("Text field 3",10);
        f.getContentPane().add(textfield1);
        f.getContentPane().add(textfield2);
        f.getContentPane().add(textfield3);


        f.pack();
        f.setVisible(true);
    }
//    public class MyAction extends MouseAdapter implements ActionListener {
//        public void actionPerformed(ActionEvent ae){
//            //String data = txtItem.getText();
//            if (data.equals(""))
//                JOptionPane.showMessageDialog(null,"Please enter text in the Text Box.");
//            else{
//                model.addElement(data);
//                JOptionPane.showMessageDialog(null,"Item added successfully.");
//               // txtItem.setText("");
//            }
//        }
//    }

}