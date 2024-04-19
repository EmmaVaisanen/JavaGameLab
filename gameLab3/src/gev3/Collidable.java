package gev3;

// Interface för kollisionslogiken
public interface Collidable {
    boolean intersect(Collidable other);
    void collisionHandling(Collidable other);
}
