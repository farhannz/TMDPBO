import View.Game;
import View.GameLoop;
import View.Window;

public class Main {
    public static void main(String[] args){
//        Window window = new Window(1280,720,"Test Game");
        GameLoop game = new Game(1280,720,"Test Game");
        game.Run();
    }
}
