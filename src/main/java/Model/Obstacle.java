package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static ViewModel.VMObstacle.Dir;
public class Obstacle extends Rectangle2D.Double implements GameObject{

    private BufferedImage sprite = null;

    public Obstacle(double x, double y, double w, double h,String block){
        super(x,y,w,h);
        if(w !=0 && h != 0){

            try{
                switch(block){
                    case "Right":
                        sprite = ImageIO.read(new File("src/main/resources/rightBlock.png"));
                        break;
                    case "Left":
                        sprite = ImageIO.read(new File("src/main/resources/leftBlock.png"));
                        break;
                    default:
                        sprite = ImageIO.read(new File("src/main/resources/midBlock.png"));
                        break;
                }
                sprite = resize(sprite,(int)w*2,(int)h*2);
            }
            catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    @Override
    public void setPosX(double pos){
        this.setRect(this.getX()+pos,this.getY(),this.width,this.height);
    }
    @Override
    public void setPosY(double pos){

        this.setRect(this.getX(),this.getY()+pos,this.width,this.height);
    }

    public void move (Dir dir,double speed, double dt){
        if(dir == Dir.LEFT){
            if(this.getX() >= -10)
                setPosX(-speed*dt);
            else
                setRect(1280,getY(),getWidth(),getHeight());
        }
        else if(dir == Dir.RIGHT){
            if(this.getX() < 1280)
                setPosX(speed*dt);
            else
                setRect(-10-getWidth(),getY(),getWidth(),getHeight());
        }
        else if(dir == Dir.STOP){
            setPosX(0);
        }
    }
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sprite,(int)x,(int)y,null);
//        g2d.dispose();
    }
}
