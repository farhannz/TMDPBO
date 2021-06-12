package ViewModel;
import Model.Obstacle;
import View.GameTime;


import java.awt.*;


public class VMObstacle {
    public enum Dir{
        LEFT,
        STOP,
        RIGHT
    };
    public Obstacle obs[] = null;
    private double x,y,width,height,size;
    public VMObstacle(double size, double x, double y, double width, double height){
        this.obs = new Obstacle[(int)size];
        int min = 1;
        int max = (int)size-1;
        for(int i =0;i<size;++i){
            if(i ==0)obs[i] = new Obstacle(x,y,width,height,"Left");
            else if(i >0 && i<size-1){
                obs[i] = new Obstacle(x+i*60,y,width,height,"Middle");
            }
            else obs[i] = new Obstacle(x+i*60,y,width,height,"Right");
        }
        int rand = (int)Math.floor(Math.random()*(size-5-min+1)+min);
        min = 5;
        max = 7;
        int maxHoles = (int)Math.floor(Math.random()*(max-min+1)+min);
        for(int i =rand;i<rand+maxHoles && i < size;++i){
            obs[i] = null;
        }
        // 100 - 132
        // 132 - 164
        // 164 - 196
        this.x = x;
        this.y = y;
        this.size = size;
        this.width =width;
        this.height = height;

    }
    public void draw(Graphics g){
        for(int i = 0;i<size;++i){
            if(obs[i] != null)obs[i].draw(g);
        }
    }

    public void move(Dir dir){
        for(int i =0;i<size;++i){
            if(obs[i] != null) obs[i].move(dir,100, GameTime.getDeltaTime()/1_000_000_000);
        }
    }
    public Obstacle[] getObstacles(){
        return obs;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getSize() {
        return size;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setSize(int size) {
        this.size = size;
    }
}
