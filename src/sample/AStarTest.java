package sample;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;


public class AStarTest {

    private static final int MAX_PATH_LENGTH = 100;

    private static final int START_X = 1;
    private static final int START_Y = 1;

    private static final int GOAL_X = 1;
    private static final int GOAL_Y = 6;

    public static void main(String[] args) {

        SimpleMap map = new SimpleMap();

        AStarPathFinder pathFinder = new AStarPathFinder(map, MAX_PATH_LENGTH, false);
        Path path = pathFinder.findPath(null, START_X, START_Y, GOAL_X, GOAL_Y);

        int length = path.getLength();
        System.out.println("Found path of length: " + length + ".");

        for(int i = 0; i < length; i++) {
            System.out.println("Move to: " + path.getX(i) + "," + path.getY(i) + ".");
        }

    }

}

class SimpleMap implements TileBasedMap {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private static final int[][] MAP = {
        {1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,1,1,1,1},
        {1,0,1,1,1,0,1,1,1,1},
        {1,0,1,1,1,0,0,0,1,1},
        {1,0,0,0,1,1,1,0,1,1},
        {1,1,1,0,1,1,1,0,0,0},
        {1,0,1,0,0,0,0,0,1,0},
        {1,0,1,1,1,1,1,1,1,0},
        {1,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,1,1,1,1,0}
    };

    @Override
    public boolean blocked(PathFindingContext ctx, int x, int y) {
        return MAP[y][x] != 0;
    }

    @Override
    public float getCost(PathFindingContext ctx, int x, int y) {
        return 1.0f;
    }

    @Override
    public int getHeightInTiles() {
        return HEIGHT;
    }

    @Override
    public int getWidthInTiles() {
        return WIDTH;
    }

    @Override
    public void pathFinderVisited(int x, int y) {}

}