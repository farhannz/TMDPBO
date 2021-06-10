package View;

import Model.Obstacle;
import Model.Player;
import ViewModel.VMPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import static ViewModel.VMPlayer.State.*;

import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends GameLoop{

    private Player p1;
    private int speed = 500;
    private int jumpForce = 400;
    private int frameCount = 0;
    private double dt = 0.0;
    private double dt2 = 0.0;
    private double fps = 0.0;
    private double updateRate = 4.0; // 4 Update / second;
    private int score = 0;
    private int fail = 0;
    private VMPlayer vmp = null;
    private boolean debugMode = true;
    private Font customFont = null;
    private BufferedImage background = null;

    private Obstacle[][] balok = null;

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public Game(int width, int height, String title) {
        super(width, height, title);
    }

    @Override
    public void Init() {
        p1 = new Player(500,450,50,50);
        this.vmp = new VMPlayer(p1,this.canvas);
        balok = new Obstacle[5][];
        for(int i =0;i<5;++i){
            balok[i] = new Obstacle[21];
            for(int j = 0;j<=20;++j){
                if(j==0){
                    balok[i][j] = new Obstacle(120,500-(i*200),32,32,"Left");
                }
                else if(j!=0 && j<20){
                    balok[i][j] = new Obstacle(120+j*50,500-(i*200),32,32,"Mid");
                }
                else{
                    balok[i][j] = new Obstacle(120+j*50,500-(i*200),32,32,"Right");
                }
            }
        }
    }

    @Override
    public void LoadContent() {
        try{
            background = ImageIO.read(new File("src/main/resources/Desert.png"));
            background = resize(background,this.width,this.height);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
        try {

            customFont = Font.createFont(Font.TRUETYPE_FONT,new File("src/main/resources/monogram.ttf"));
            customFont = customFont.deriveFont(Font.BOLD,20);
        } catch (IOException|FontFormatException e) {
            //Handle exception
        }

    }

    @Override
    public void Update() {

        // FPS
        frameCount++;
        dt+=GameTime.getDeltaTime()/1_000_000_000;
        if(dt > 1.0/updateRate){
            fps = frameCount/dt;
            frameCount = 0;
            dt-=1/updateRate;
        }
        dt2+=GameTime.getDeltaTime()/1_000_000_000;
        if(dt2>1.0/8.0){
            vmp.nextAnim();
            dt2-=1.0/8.0;
        }

        for(int i = 0;i<=20;++i){
            if(balok[0][i].getX() + 1 >= canvas.getWidth())
                balok[0][i].setRect(-10-balok[0][i].getWidth(),balok[0][i].getY(),balok[0][i].getWidth(),balok[0][i].getHeight());
            balok[0][i].setPosX(speed*GameTime.getDeltaTime()/1_000_000_000);
        }
        double deltaTime = GameTime.getDeltaTime()/1_000_000_000;
//        System.out.println(deltaTime);
        // Logic
//        grounded = vmp.isGrounded();
        if(this.keyboard.keyDown(VK_W) && (vmp.isGrounded() || vmp.isJump())){
            vmp.verticalMove(JUMP,jumpForce,deltaTime);
//            }
        }
//        else{
            vmp.verticalMove(DOWN,jumpForce,deltaTime);
//        }
        if(this.keyboard.keyDown(VK_D)){
            vmp.horizontalMove(RIGHT,speed,deltaTime);
        }
        else if(this.keyboard.keyDown(VK_A)){
            vmp.horizontalMove(LEFT,speed,deltaTime);
        }
        else{
            vmp.horizontalMove(NONE,speed,deltaTime);
        }

        if(this.keyboard.keyDownOnce(VK_F10)){
            debugMode = !debugMode;
        }
        // Gravity
//        vmp.move(DOWN,speed,deltaTime);


//        if(p1.getX()+1>=this.window.getWidth()){
//            p1.setRect((-p1.getWidth()),this.getY(),p1.getWidth(),p1.getHeight());
//        }
//        p1.setRect(p1.getX()+speed*(GameTime.getDeltaTime()/1_000_000_000),p1.getY(),p1.getWidth(),p1.getHeight());
    }

    @Override
    public void Render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
//        Font font = new Font(Font.);
        g.drawImage(background,0,0,null);
//        g.setColor(new Color(0xe18339));
//        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        g.setFont(customFont);
        g.setColor(Color.black);
        for(int i =0;i<=20;++i){
            balok[0][i].draw(g);
        }
        g.drawString("FPS : " + Double.toString(Math.round(fps*100.0)/100.0),20,20);
        g.drawString("Success : " + Integer.toString(score),20,40);
        g.drawString("Fail : " + Integer.toString(fail),20,60);
        if(debugMode){
            g.drawString("isGrounded : " + Boolean.toString(vmp.isGrounded()),20,80);
            g.drawString("isJump : " + Boolean.toString(vmp.isJump()),200,80);
            g.drawString("isBoundary : " + Boolean.toString(vmp.isBoundary()),200,100);
            g.setColor(Color.WHITE);
            g.drawString("X : " + Double.toString(Math.round(vmp.getX()*100)/100.0),200,120);
            g.drawString("Y : " + Double.toString(Math.round(vmp.getY()*100)/100.0),200,140);
            g.drawString("Vel x = " + Double.toString(Math.round(vmp.getVelX()*100)/100.0),(int)vmp.getX(),(int)vmp.getY() - 60);
            g.drawString("Vel y = " + Double.toString(Math.round(vmp.getVelY()*100)/100.0),(int)vmp.getX(),(int)vmp.getY() - 20);
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawRect((int)vmp.getX(),(int)vmp.getY(),(int)vmp.getWidth(),(int)vmp.getHeight());
            g2d.draw(new Line2D.Double(vmp.getX()+vmp.getWidth()/2,vmp.getY()+vmp.getHeight(),vmp.getX()+vmp.getWidth()/2,canvas.getHeight()));
            g2d.setColor(Color.black);
            g2d.drawString("Distance to canvas's height : " + Double.toString( (Math.round(canvas.getHeight()-40) - (p1.getY()+p1.getHeight())*100)/100.0),90,60);
        }

//        p1.draw(g);
        vmp.draw(g);
        g.dispose();
        bs.show();
    }
}
