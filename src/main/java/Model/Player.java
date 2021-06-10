package Model;

import View.GameTime;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends Rectangle2D.Double implements GameObject {
    private BufferedImage img = null;
    private BufferedImage currImage = null;
    public double velX,velY;
    public boolean jump = true, grounded = true;
    private int indexAnim = 0;
    private Animation[] anim = new Animation[8];
    public Player(int x, int y, int width, int height) {
        super(x,y,width,height);
        try
        {
            img = ImageIO.read(new File("src/main/resources/wind_SpriteSheet_224x112.png"));

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        int a = 224;
        anim[0] = new Animation(a *0,0,224,112);
        anim[1] = new Animation(a *1,0,224,112);
        anim[2] = new Animation(a *2,0,224,112);
        anim[3] = new Animation(a *3,0,224,112);
        anim[4] = new Animation(a *4,0,224,112);
        anim[5] = new Animation(a *5,0,224,112);
        anim[6] = new Animation(a *6,0,224,112);
        anim[7] = new Animation(a *7,0,224,112);
        currImage = resize(img.getSubimage(anim[indexAnim%8].x,anim[indexAnim%8].y,anim[indexAnim%8].w,anim[indexAnim%8].h),anim[indexAnim%8].w*2,anim[indexAnim%8].h*2);
        this.height = currImage.getHeight();
        this.width = currImage.getWidth();
        this.velX = 0;
        this.velY = 0;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void setPosX(double pos){
        this.setRect(this.getX()+pos,this.getY(),this.width,this.height);
    }
    public void setPosY(double pos){

        this.setRect(this.getX(),this.getY()+pos,this.width,this.height);
    }


    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform t = new AffineTransform();
        t.translate(x,y);
        t.scale(1,1);
        g2d.drawImage(currImage,t,null);
//        g2d.dispose();
//        g2d.setColor(Color.red);
//        g2d.fill(this);
    }
    public BufferedImage getImg(){
        return currImage;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }
    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public void nextImage(){
        indexAnim = (indexAnim + 1)%8;
        currImage = resize(img.getSubimage(anim[indexAnim%8].x,anim[indexAnim%8].y,anim[indexAnim%8].w,anim[indexAnim%8].h),anim[indexAnim%8].w*2,anim[indexAnim%8].h*2);
    }
}
