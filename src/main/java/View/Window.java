package View;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    protected int width,height;
    protected String title;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
        this.setPreferredSize(new Dimension(width,height));
        setTitle(title);
//        add(this);
        pack();
        setBackground(Color.black);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
