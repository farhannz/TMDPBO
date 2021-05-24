package View;

import Model.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends GameLoop{

    Player p1;
    public Game(int width, int height, String title) {
        super(width, height, title);
    }

    @Override
    public void Init() {

        p1 = new Player(0,0,50,50);
    }

    @Override
    public void LoadContent() {

    }

    @Override
    public void Update() {
        if((int)p1.getX()+1>=this.window.getWidth()){
            p1.setLocation(0,this.getY());
        }
        p1.setLocation(((int)p1.getX()+1),(int)p1.getY());

    }

    @Override
    public void Render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

        g.setColor(Color.white);
        double current = GameTime.getDeltaTime();
        g.drawString("Fps : "+Double.toString(fps/GameTime.getDeltaTime()),30,30);
        fps = 0;
        g.setColor(Color.red);
        g.fillRect(
                (int)p1.getX(),
                (int)p1.getY(),
                (int)p1.getWidth(),
                (int)p1.getHeight());
        g.dispose();
        bs.show();
    }
}
