package ViewModel;

import Model.Obstacle;
import Model.Player;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class VMPlayer {
    private Player p1 = null;
    private Canvas canvas = null;
    private boolean success = false;
    private boolean firstHit = false;
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
//        double gravity = 200;
//        System.out.println(dt);
        // When player moving to the right
        if(!isBoundary()){
            if(getBoxCollider().getX()<=0)
                p1.setPosX(p1.velX*dt+1);
            else if(getBoxCollider().getX()>1199)
                p1.setPosX(-p1.velX*dt-1);
            p1.velX = 0;
        }
        if(p1.getPos() == Player.Pos.LEFT){
            p1.setPosX((110)*dt);
            return;
        }
        else if(p1.getPos() == Player.Pos.RIGHT){
            p1.setPosX((110)*dt);
            return;
        }
        if(s == State.RIGHT){
            // Set player velocity to the value of variable called speed
            p1.velX = speed;
            // Then, move the player position based on velocity * delta time
            p1.setPosX(p1.velX * dt);


//            p1.velY = p1.velY + gravity * dt;
//            System.out.println(p1.velX * dt);
//            p1.setPosY(p1.velY * dt);
//            p1.setVelocityX(moveSpeed);
        }
        // When player moving to the left
        else if(s == State.LEFT){
//            p1.setVelocityX(-moveSpeed);
            // Set player velocity to the negative value of variable called speed
            p1.velX = -speed;
            // Then, move the player position based on velocity * delta time
            p1.setPosX(p1.velX * dt);
        }
        // When player isn't moving to the left or right
        else{

            // Player's velocity will be gradually decreased or increased till it reaches Zero velocity

            if(p1.velX>0){
                // When player was moving to the right and there is no keyboard input
                // Player's velocity will be gradually decreased
                p1.velX -= speed * 2 * dt;
                // Reset the player velocity to zero if it's less than zero
                if(p1.velX<=0)
                    p1.velX = 0;
                // Then, move the player's position based on the velocity
                p1.setPosX(p1.velX * dt);
            }
            else if(p1.velX<0){
                // When player was moving to the left and there is no keyboard input
                // Player's velocity will be gradually increased till it reaches zero
                p1.velX += speed * 2 * dt;
                // Then, move the player's position based on the velocity
                p1.setPosX(p1.velX * dt);
            }
            else{
                // Reset player's velocity
                p1.velX = 0;
            }
        }
    }

    public void verticalMove(State s, int jumpForce, double dt){

        // If the current state is jump
        if(p1.getPos() == Player.Pos.TOP){
            p1.setGrounded(true);
            if(s != State.JUMP){
                p1.setPosY(0);
                p1.velY = 0;
                p1.setPosX(100*dt);
            }
            else{
                p1.velY -= jumpForce*2.25;
                // Move player based on velocity * delta time
                p1.setPosY(p1.velY * dt);
                // Make sure the player isn't able to jump
                p1.setJump(false);
                p1.setPos(Player.Pos.BOTTOM);
                p1.setGrounded(false);
            }
            return;
        }

        if(isCollided() && p1.getPos() == Player.Pos.BOTTOM){
            p1.velY = 0;
            p1.setPosY(jumpForce*dt);
            return;
        }
        if(s == State.JUMP){
            double force = jumpForce;
            // Change player velocity, then
            p1.velY -= jumpForce*2.25;
            // Move player based on velocity * delta time
            p1.setPosY(p1.velY * dt);
            // Make sure the player isn't able to jump
            p1.setJump(false);
//            if(p1.velY <= -700){
//                p1.velY = -700.0;
//                p1.setJump(false);
//                return;
//            }
//            if(jumpForce >=0){
//            }

        }
        else{

            if(!isGrounded()){
//                p1.setJump(false);
                // Gravity
                p1.velY += jumpForce * 5 * dt;
                p1.setPosY(p1.velY*dt);
            }
            else if(isCollided()){
                p1.velY = 0;
                p1.setPosY(jumpForce*dt*2);
//            return;
            }
            else{
                // Reset jump and velocity
                p1.setPos(Player.Pos.NONE);
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
        AffineTransform t = new AffineTransform();
        t.scale(1,1);
        t.translate(p1.getX(),p1.getY());


//        g2d.fill(this);
//        g2d.draw(new Line2D.Double(p1.getX()+p1.getWidth()/2,p1.getY()+p1.getHeight(),p1.getX()+p1.getWidth()/2,canvas.getHeight()));
//        g2d.setColor(Color.black);
//        g2d.drawString("Distance to canvas's height : " + Double.toString( (canvas.getHeight()-40) - (p1.getY()+p1.getHeight())),90,60);
//        g2d.setColor(Color.WHITE);
//        g2d.drawString("Vel x = " + Double.toString(p1.getVelX()),(int)getX(),(int)getY() - 60);
//        g2d.drawString("Vel y = " + Double.toString(p1.getVelY()),(int)getX(),(int)getY() - 20);
//        BufferedImage currImg = p1.getImg();
//        g2d.drawImage(currImg.getSubimage(100,74,33,38),t,null);
//
//        g2d.drawLine(p1.getX(),p1.getY(),p1.getX(),this.canvas.getHeight());
    }

    public void checkCollision(VMObstacle obs){
        Obstacle[] blocks = obs.getObstacles();
        boolean trigger = false;
        Rectangle boxCollider = getBoxCollider();
        for(Obstacle data : blocks){
            if(data != null){
                // AABB Collision detection
                trigger = (int)boxCollider.getX() < (int)data.getX() + (int)data.getWidth() &&
                          (int)boxCollider.getX() + (int)boxCollider.getWidth() > (int)data.getX() &&
                          (int)boxCollider.getY() < (int)data.getY() + (int)data.getHeight() &&
                          (int)boxCollider.getY() + (int)boxCollider.getWidth() > (int)data.getY();
    //            System.out.println(trigger);
                p1.setCollided(trigger);
                if(isCollided()){
                    double w = 0.5 * (data.getWidth() + boxCollider.getWidth());
                    double h = 0.5 * (data.getHeight() + boxCollider.getHeight());
                    double dataCx = data.getX() + data.getWidth()/2;
                    double playerCx = boxCollider.getX() + boxCollider.getWidth()/2;
                    double dataCy = data.getY() + data.getHeight()/2;
                    double playerCy = boxCollider.getY() + boxCollider.getHeight()/2;
                    double dx = dataCx - playerCx;
                    double dy = dataCy - playerCy;

                    if (Math.abs(dx) < w && Math.abs(dy) < h)//collision
                    {
                        double wy = w * dy;
                        double hx = h * dx;
                        if (wy >= hx)
                            if (wy >= -hx)//top of block
                            {
                                p1.setPos(Player.Pos.TOP);
                                success = true;
                            }
//                                p1.setPos(Player.Pos.TOP);
                            else//right of block
                                p1.setPos(Player.Pos.RIGHT);
                        else
                            if (wy >= -hx)//left of block
                                p1.setPos(Player.Pos.LEFT);
                            else//bottom of block
                                p1.setPos(Player.Pos.BOTTOM);
                        if(p1.getPos() != Player.Pos.TOP){
                            if(!firstHit){
                                Fail();
                                firstHit = true;
                            }
                        }
                    }
//                    if((int)boxCollider.getY() + (int)boxCollider.getWidth() > (int)data.getY()){
//                        p1.setPos(Player.Pos.TOP);
//                        p1.setGrounded(true);
//                        Succes();
//                    }
//                    if((int)boxCollider.getX() < (int)data.getX() + (int)data.getWidth()){
//                        p1.setPos(Player.Pos.LEFT);
////                        Fail();
//                    }
//                    else if((int)boxCollider.getX() + (int)boxCollider.getWidth() > (int)data.getX()){
//                        p1.setPos(Player.Pos.RIGHT);
////                        Fail();
//                    }
                    return;
                }
            }
        }
//        return trigger;
    }

    public boolean isGrounded(){
        if(p1.getPos() == Player.Pos.TOP){
            p1.setGrounded(true);
            firstHit = false;
        }
        else{
            p1.setGrounded(((p1.getBoxCollider().getY()+p1.getBoxCollider().getHeight()) > canvas.getHeight()-40 ));
            if(((p1.getBoxCollider().getY()+p1.getBoxCollider().getHeight()) > canvas.getHeight()-40 ))
                firstHit =false;
        }
        return p1.isGrounded();
    }

    public boolean isJump(){
        return p1.isJump();
    }

    public boolean isBoundary(){
        return p1.getBoxCollider().getX() > 0 && p1.getBoxCollider().getX() + p1.getBoxCollider().getWidth() < canvas.getWidth();
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
    public Rectangle getBoxCollider(){
        return p1.getBoxCollider();
    }
    public boolean isCollided(){
        return p1.isCollided();
    }
    public int getFail(){
        return p1.getFail();
    }
    public int getSuccess(){
        return p1.getSuccess();
    }
    public void Success(){
        p1.setSuccess(p1.getSuccess()+1);
        p1.setPos(Player.Pos.NONE);
    }
    public void Fail(){
        p1.setFail(p1.getFail()+1);
    }
    public Player.Pos getPos(){
        return p1.getPos();
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setX(double x){
        p1.setX(x);
    }
    public void setY(double y){
        p1.setY(y);
    }

    public void playAnim(String anim){
        p1.playAnim(anim);
    }

    public int animSize(){
        return p1.animSize();
    }
    public void Flip(){
        boolean tmp = p1.isFlip();
        p1.Flip();
        p1.setFlip(!tmp);
    }
    public boolean isFlip(){
        return p1.isFlip();
    }
    public void setRight(boolean right){
        p1.setRight(right);
    }
    public boolean isRight(){
        return p1.isRight();
    }
}
