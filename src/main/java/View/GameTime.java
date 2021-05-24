package View;

public class GameTime {
    public static double DeltaTime;
    public static double Elapsed;

    public static double getElapsed() {
        return Elapsed;
    }

    public static double getDeltaTime() {
        return DeltaTime;
    }

    public static void setElapsed(double elapsed) {
        Elapsed = elapsed;
    }

    public static void setDeltaTime(double deltaTime) {
        DeltaTime = deltaTime;
    }
}
