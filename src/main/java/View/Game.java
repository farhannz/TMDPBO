package View;

import Model.Obstacle;
import Model.Player;
import ViewModel.VMHighScore;
import ViewModel.VMPlayer;
import ViewModel.VMObstacle;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import static ViewModel.VMPlayer.State.*;
import ViewModel.VMObstacle.Dir;

import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Game extends GameLoop{

    private Player p1;
    private int speed = 500;
    private int jumpForce = 400;
    private int frameCount = 0;
    private double dt = 0.0;
    private double dt2 = 0.0;
    private double dt3 = 0.0;
    private double fps = 0.0;
    private double updateRate = 4.0; // 4 Update / second;
    private VMPlayer vmp = null;
    private ArrayList<VMObstacle> obstacles = new ArrayList<>();
    private boolean debugMode = false;
    private Font customFont = null;
    private BufferedImage background = null;
    private Clip clip;
    private String username;
    private VMHighScore hs = null;

//    private Obstacle[][] balok = null;

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public Game(int width, int height, String title, String username) {
        super(width, height, title);
        this.username = username;
    }

    @Override
    public void Init() {
        p1 = new Player(500,400,50,50);
        this.vmp = new VMPlayer(p1,this.canvas);
        this.hs = new VMHighScore();
        for(int i =0;i<5;++i){
            obstacles.add(i,new VMObstacle(20,120,500-i*200,32,32));
        }
//        this.obstacle1 = new VMObstacle(20,120,500,32,32);
        try {
            // Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/main/resources/DesertBouncetrimmed.wav"));
            // Get a sound clip resource.

            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
//            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
//        balok = new Obstacle[5][];
//        for(int i =0;i<5;++i){
//            balok[i] = new Obstacle[21];
//            for(int j = 0;j<=20;++j){
//                if(j==0){
//                    balok[i][j] = new Obstacle(120,500-(i*200),32,32,"Left");
//                }
//                else if(j!=0 && j<20){
//                    balok[i][j] = new Obstacle(120+j*50,500-(i*200),32,32,"Mid");
//                }
//                else{
//                    balok[i][j] = new Obstacle(120+j*50,500-(i*200),32,32,"Right");
//                }
//            }
//        }
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
            customFont = customFont.deriveFont(Font.BOLD,24);
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
        // Animation placeholder
        dt2+=GameTime.getDeltaTime()/1_000_000_000;
        if(dt2>1.0/vmp.animSize()){

            vmp.nextAnim();
            dt2-=1.0/vmp.animSize();
        }
        // Determine the movement direction of obstacle blocks
        for(int i =0;i<5;++i){
            if(i%2==0){
                obstacles.get(i).move(Dir.RIGHT);
            }
            else{
                obstacles.get(i).move(Dir.LEFT);
            }
        }
        // Check if player is success on passing the obstacle
        if(vmp.isSuccess()){
//            obstacles.remove(0);
            vmp.setX(500);
            vmp.setY(400);
            vmp.setSuccess(false);
            for(int i = 0;i<4;++i){
                obstacles.set(i+1,new VMObstacle(20,120,500-i*200,32,32));
                obstacles.set(i,obstacles.get(i+1));
            }
            vmp.Success();
        }
//        obstacle1.move(Dir.RIGHT);
        // Obstacle logic placeholder
//        for(int i = 0;i<=(int)obstacle1.getHeight();++i){
//
//        }
        double deltaTime = GameTime.getDeltaTime()/1_000_000_000;

        dt3+=GameTime.getDeltaTime()/1_000_000_000;
        if(dt3>1.0/50.0){
            vmp.checkCollision(obstacles.get(0));
            dt3-=1.0/50.0;
        }
        // Gravity
        vmp.verticalMove(DOWN,jumpForce,deltaTime);
        // Keyboard input logic
        if(this.keyboard.keyDown(VK_W) && (vmp.isGrounded() || vmp.isJump())){
            // Jump when 'W' is pressed\
            vmp.verticalMove(JUMP,jumpForce,deltaTime);
        }

        else if(this.keyboard.keyDown(VK_D) && vmp.isBoundary()){
            // Play running animation
            vmp.playAnim("Run");
            vmp.setRight(false);
            // Move right when 'D' is pressed
            vmp.horizontalMove(RIGHT,speed,deltaTime);
        }
        else if(this.keyboard.keyDown(VK_A) && vmp.isBoundary()){
            // Play running animation
            vmp.playAnim("Run");
            vmp.setRight(true);
//            System.out.println(vmp.isFlip());
            // Move left when 'A' is pressed
            vmp.horizontalMove(LEFT,speed,deltaTime);
        }
        else{
            // Play Idle animation
            vmp.playAnim("Idle");
            // No movement or gradually decreasing player's velocity
            vmp.horizontalMove(NONE,speed,deltaTime);
        }

        // Keyboard input for switching on/off debug mode
        if(this.keyboard.keyDownOnce(VK_F10)){
            debugMode = !debugMode;
        }

        if(this.keyboard.keyDownOnce(VK_Q)){
            hs.Insert(p1.getSuccess(), p1.getFail(), username);
            setRunning(false);
            clip.stop();
        }
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
//        for(int i =0;i<=20;++i){
//            balok[0][i].draw(g);
//        }
        g.drawString("FPS : " + Double.toString(Math.round(fps*100.0)/100.0),20,20);
        g.drawString("Success : " + Integer.toString(vmp.getSuccess()),20,40);
        g.drawString("Fail : " + Integer.toString(vmp.getFail()),20,60);

        // Debug mode
        if(debugMode){
            g.drawString("isGrounded : " + Boolean.toString(vmp.isGrounded()),20,80);
            g.drawString("isJump : " + Boolean.toString(vmp.isJump()),200,80);
            g.drawString("blockCollision : " + Boolean.toString(vmp.isCollided()),450,80);
            g.drawString("isBoundary : " + Boolean.toString(vmp.isBoundary()),200,100);
            g.setColor(Color.WHITE);
            g.drawString("X : " + Double.toString(Math.round(vmp.getBoxCollider().getX()*100)/100.0),200,120);
            g.drawString("Y : " + Double.toString(Math.round(vmp.getBoxCollider().getY()*100)/100.0),200,140);
            g.drawString("Pos = " + vmp.getPos().name(),(int)vmp.getBoxCollider().getX(),(int)vmp.getBoxCollider().getY() - 40);
            g.drawString("Vel x = " + Double.toString(Math.round(vmp.getVelX()*100)/100.0),(int)vmp.getBoxCollider().getX(),(int)vmp.getBoxCollider().getY() - 60);
            g.drawString("Vel y = " + Double.toString(Math.round(vmp.getVelY()*100)/100.0),(int)vmp.getBoxCollider().getX(),(int)vmp.getBoxCollider().getY() - 80);
            g.setColor(Color.green);
//            g.drawRect((int)vmp.getX()+ (int)vmp.getWidth()/2 - 30,(int)vmp.getY()+150,60,(int)vmp.getHeight()-150);
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g;
//            Rectangle playerBoxCollider = vmp.getBoxCollider();
//            System.out.println(vmp.getBoxCollider());

            g2d.draw(vmp.getBoxCollider());
//            g2d.drawRect((int)playerBoxCollider.getX(),(int)playerBoxCollider.getY(),(int)playerBoxCollider.getWidth(),(int)playerBoxCollider.getHeight());
//            g2d.drawRect((int)vmp.getX(),(int)vmp.getY(),(int)vmp.getWidth(),(int)vmp.getHeight());
//            g2d.draw(new Line2D.Double(vmp.getX()+vmp.getWidth()/2,vmp.getY()+vmp.getHeight(),vmp.getX()+vmp.getWidth()/2,canvas.getHeight()));
            g2d.draw(new Line2D.Double(vmp.getBoxCollider().getWidth()/2+vmp.getBoxCollider().getX(),vmp.getBoxCollider().getY()+vmp.getBoxCollider().getHeight(),vmp.getBoxCollider().getWidth()/2+vmp.getBoxCollider().getX(),canvas.getHeight()));
            g2d.setColor(Color.black);
//            g2d.drawString("Distance to canvas's height : " + Double.toString( Math.round(p1.getBoxCollider().getY()) - canvas.getHeight()),90,60);
        }

//        p1.draw(g);
        g.setColor(Color.WHITE);
        g.drawString("Press 'Q' to exit the game!",20,650);
//        obstacle1.draw(g);
        for(VMObstacle data : obstacles)
            data.draw(g);
        vmp.draw(g);
        g.dispose();
        bs.show();
    }
}
