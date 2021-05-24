package Model;

import java.awt.*;

public class Player extends Rectangle {

    public Player(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public void setVelocityX(int speed){
        this.setLocation((int)this.getX()+speed,(int)this.getY());
    }
    public void setVelocityY(int speed){

        this.setLocation((int)this.getX(),(int)this.getY()+speed);
    }

    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)this.getWidth(),
                (int)this.getHeight());
    }
}
