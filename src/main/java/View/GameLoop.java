package View;

import View.EventHandler.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public abstract class GameLoop extends JPanel {
    private boolean running = false;
    protected int width,height;
    protected String title;
    KeyInput keyboard = new KeyInput();
    Window window = null;
    Canvas canvas = new Canvas();
    int fps = 0;
    public GameLoop(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
        this.window = new Window(this.width,this.height,this.title);
        this.canvas.setPreferredSize(new Dimension(this.width,this.height));
        add(canvas);
        this.window.add(this);
        setBackground(Color.black);
//        pack();
    }

    public void Run(){
        Init();
        this.addKeyListener(keyboard);
        LoadContent();
        running = true;
//        this.add(window);

        while(running){
            fps++;
            GameTime.setDeltaTime((float)System.nanoTime()-GameTime.getElapsed());
            GameTime.setElapsed((float)System.nanoTime());
            System.out.println(TimeUnit.SECONDS.convert(System.nanoTime(),TimeUnit.NANOSECONDS));
            Thread t = new Thread(this::Update);
            t.start();
            Render();
//            System.out.println(System.nanoTime() + " " + GameTime.getElapsed());
        }
    }
    public abstract void Init();
    public abstract void LoadContent();
    public abstract void Update();
    public abstract void Render();


}
