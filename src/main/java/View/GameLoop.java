package View;

import View.EventHandler.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public abstract class GameLoop extends JPanel implements Runnable {
    private boolean running = false;
    protected int width,height;
    protected String title;
    KeyInput keyboard = new KeyInput();
    Window window = null;
    Canvas canvas = new Canvas();


    public GameLoop(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
        this.window = new Window(this.width,this.height,this.title);
        this.canvas.setPreferredSize(new Dimension(this.width,this.height));
        add(canvas);
        this.window.add(this);
        setBackground(new Color(0xe763b54));
    }

    @Override
    public void run(){
        Init();
        this.addKeyListener(this.keyboard);
        canvas.addKeyListener(this.keyboard);
        setFocusable(true);
        LoadContent();
        running = true;
        GameTime.setElapsed(System.nanoTime());
        while(running){
            // Start polling for keyboard input
            this.keyboard.poll();
            // Calculating delta Time
            GameTime.setDeltaTime(System.nanoTime() - GameTime.getElapsed());
            GameTime.setElapsed(System.nanoTime());
            Update();
            Render();
        }
    }

    // Abstract classes yang harus diimplementasikan
    public abstract void Init();
    public abstract void LoadContent();
    public abstract void Update();
    public abstract void Render();


}
