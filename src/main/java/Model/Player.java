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
import java.util.ArrayList;

public class Player extends Rectangle2D.Double implements GameObject {
    private BufferedImage img = null;
    private BufferedImage currImage = null;
    public double velX,velY;
    private boolean jump = true, grounded = true;
    private int indexAnim = 0;
    private boolean collided = false;
    private int success = 0,fail = 0;
    private ArrayList<Animation> anim = new ArrayList<>();
    private ArrayList<Boolean> flipped = new ArrayList<>();
    private String currentAnim;
    private boolean flip = false;
    private boolean right = true;
//    g.drawRect((int)vmp.getX()+ (int)vmp.getWidth()/2 - 30,(int)vmp.getY()+150,60,(int)vmp.getHeight()-150);
    private Rectangle boxCollider = null;
    public enum Pos{
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        NONE
    };
    private Pos pos= Pos.NONE;
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
        anim.clear();
        anim.add(new Animation(a *0,0,224,112));
        anim.add(new Animation(a *1,0,224,112));
        anim.add(new Animation(a *2,0,224,112));
        anim.add(new Animation(a *3,0,224,112));
        anim.add(new Animation(a *4,0,224,112));
        anim.add(new Animation(a *5,0,224,112));
        anim.add(new Animation(a *6,0,224,112));
        anim.add(new Animation(a *7,0,224,112));
        currImage = resize(img.getSubimage(anim.get(indexAnim).x,anim.get(indexAnim).y,anim.get(indexAnim).w,anim.get(indexAnim).h),anim.get(indexAnim).w*2,anim.get(indexAnim).h*2);
        this.height = currImage.getHeight();
        this.width = currImage.getWidth();
        this.boxCollider = new Rectangle((int)getX()+ (int)getWidth()/2 - 30,(int)getY()+150,60,(int)getHeight()-150);
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
        boxCollider.setRect((int)getX()+ (int)getWidth()/2 - 30 + pos,(int)getY()+150,60,(int)getHeight()-150);
    }
    public void setPosY(double pos){
        this.setRect(this.getX(),this.getY()+pos,this.width,this.height);
        boxCollider.setRect((int)getX()+ (int)getWidth()/2 - 30 + pos,(int)getY()+150+pos,60,(int)getHeight()-150);
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
        indexAnim = (indexAnim + 1)% anim.size();
        currImage = resize(img.getSubimage(anim.get(indexAnim).x,anim.get(indexAnim).y,anim.get(indexAnim).w,anim.get(indexAnim).h),anim.get(indexAnim).w*2,anim.get(indexAnim).h*2);
        if(flipped.get(indexAnim) != right){
            Flip();
            flipped.set(indexAnim,!flipped.get(indexAnim));
        }
    }

    public Rectangle getBoxCollider() {
        return boxCollider;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public Pos getPos() {
        return pos;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return success;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getFail() {
        return fail;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    public void playAnim(String animate){
        currentAnim = animate;
        int a = 224;
        switch (currentAnim){
            case "Idle":
                anim.clear();
                flipped.clear();
                anim.add(new Animation(a *0,0,224,112));
                anim.add(new Animation(a *1,0,224,112));
                anim.add(new Animation(a *2,0,224,112));
                anim.add(new Animation(a *3,0,224,112));
                anim.add(new Animation(a *4,0,224,112));
                anim.add(new Animation(a *5,0,224,112));
                anim.add(new Animation(a *6,0,224,112));
                anim.add(new Animation(a *7,0,224,112));
                for(int i =0;i<anim.size();++i){
                    flipped.add(false);
                }
                break;
            case "Run":
                anim.clear();
                anim.add(new Animation(a *0,112,224,112));
                anim.add(new Animation(a *1,112,224,112));
                anim.add(new Animation(a *2,112,224,112));
                anim.add(new Animation(a *3,112,224,112));
                anim.add(new Animation(a *4,112,224,112));
                anim.add(new Animation(a *5,112,224,112));
                anim.add(new Animation(a *6,112,224,112));
                anim.add(new Animation(a *7,112,224,112));
                for(int i =0;i<anim.size();++i){
                    flipped.add(false);
                }
                break;
        }
    }
    public int animSize(){
        return anim.size();
    }

    public void Flip(){
        AffineTransform tx;
        tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-currImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        currImage = op.filter(currImage, null);
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isFlip() {
        return flip;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
