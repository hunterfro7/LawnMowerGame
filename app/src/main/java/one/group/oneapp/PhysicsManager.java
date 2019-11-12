package one.group.oneapp;

public class PhysicsManager {
    public static final int minX = 0;
    public static final int minY = 300;
    public static final int maxX = 1050;
    public static final int maxY = 1750;
    public static boolean collides(Collidable o1, Collidable o2){
        return (o1.getX() + o1.getWidth() > o2.getX() && o1.getX() < o2.getX()+o2.getWidth() &&
                o1.getY() + o1.getHeight() > o2.getY() && o1.getY() < o2.getY()+o2.getHeight());
    }
    public static boolean isValidPosition(Collidable o1){
        return (o1.getX() >= minX &&
                o1.getY() >= minY &&
                o1.getX() + o1.getWidth() <= maxX &&
                o1.getY() + o1.getHeight() <= maxY
        );
    }
}
