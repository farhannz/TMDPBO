package ViewModel;

import Model.Player;
import View.GameTime;
import ViewModel.AnimationManager.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class VMPlayer {
    private Player p1 = null;
    private Canvas canvas = null;
    private boolean grounded = false;
    public enum State{
        JUMP,
        RIGHT,
        LEFT,
        DOWN,
        NONE
    }

    public VMPlayer(Player player, Canvas canvas){
        this.p1 = player;
        this.canvas = canvas;
    }

    public void horizontalMove(State s, int speed, double dt)
    {
        double gravity = 200;
//        System.out.println(dt);
        if(s == State.RIGHT){
            p1.velX = speed;
//            p1.velY = p1.velY + gravity * dt;
            System.out.println(p1.velX * dt);
            p1.setPosX(p1.velX * dt);
//            p1.setPosY(p1.velY * dt);
//            p1.setVelocityX(moveSpeed);
        }
        else if(s == State.LEFT){
//            p1.setVelocityX(-moveSpeed);
            p1.velX = -speed;
            p1.setPosX(p1.velX * dt);
        }
        else{
            if(p1.velX>0){
                p1.velX -= speed * 2 * dt;
                if(p1.velX<=0)
                    p1.velX = 0;
                p1.setPosX(p1.velX * dt);
            }
            else if(p1.velX<0){
                p1.velX += speed * 2 * dt;
                p1.setPosX(p1.velX * dt);
            }
            else{
                p1.velX = 0;
            }
        }
    }

    public void verticalMove(State s, int jumpForce, double dt){
        if(s == State.JUMP){
            double force = jumpForce;
            p1.velY -= jumpForce*2.25;
            p1.setPosY(p1.velY * dt);
//            if(p1.velY <= -700){
//                p1.velY = -700.0;
//                p1.setJump(false);
//                return;
//            }
//            if(jumpForce >=0){
//            }

            p1.setJump(false);
        }
        else{
            // Gravity
            if(!isGrounded()){
//                p1.setJump(false);
                p1.velY += jumpForce * 5 * dt;
                p1.setPosY(p1.velY*dt);
            }
            else{
                p1.setJump(true);
                p1.velY = 0;
            }
//            }
        }
    }

    public void draw(Graphics g){
        p1.draw(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
//        g2d.fill(this);
//        g2d.draw(new Line2D.Double(p1.getX()+p1.getWidth()/2,p1.getY()+p1.getHeight(),p1.getX()+p1.getWidth()/2,canvas.getHeight()));
//        g2d.setColor(Color.black);
//        g2d.drawString("Distance to canvas's height : " + Double.toString( (canvas.getHeight()-40) - (p1.getY()+p1.getHeight())),90,60);
        AffineTransform t = new AffineTransform();
        t.scale(1,1);
        t.translate(p1.getX(),p1.getY());
//        g2d.setColor(Color.WHITE);
//        g2d.drawString("Vel x = " + Double.toString(p1.getVelX()),(int)getX(),(int)getY() - 60);
//        g2d.drawString("Vel y = " + Double.toString(p1.getVelY()),(int)getX(),(int)getY() - 20);
//        BufferedImage currImg = p1.getImg();
//        g2d.drawImage(currImg.getSubimage(100,74,33,38),t,null);
//
//        g2d.drawLine(p1.getX(),p1.getY(),p1.getX(),this.canvas.getHeight());
    }

    public boolean isGrounded(){
        return (canvas.getHeight()-40) - (p1.getY()+p1.getHeight()) <= 3 ;
    }

    public boolean isJump(){
        return p1.isJump();
    }

    public boolean isBoundary(){
        return (canvas.getWidth()-(p1.getX()+p1.getHeight()) <= 2 ||(p1.getX()+p1.getHeight()) >=2 );
    }


    public double getY(){
        return p1.getY();
    }
    public double getX(){
        return p1.getX();
    }
    public double getHeight(){
        return p1.getHeight();
    }
    public double getWidth(){
        return p1.getWidth();
    }
    public double getVelX(){
        return p1.getVelX();
    }
    public double getVelY(){
        return p1.getVelY();
    }

    public void nextAnim(){
        p1.nextImage();
    }
}
