/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Camera.Camera;

import Entity.Pawn;
import Entity.Traveler;
import Item.Inventory;
import Res.Res;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;


/**
 *
 * @author Timot
 */
public class WorldMap implements TileBasedMap
{
    //camera when in world map
    private Camera wCam;
    //camera when in local map
    private Camera cam;
    
    private int width,height;
    
    //check if the player is in local map or world map
    private int currentMap;
    
    private ArrayList<LocalMap> maps;
    
    private ArrayList<Traveler> travelers;
    
    private Location[][] locations;

    private GameContainer container;
    
    private Res res;
    private World world;
    
    private Pawn player;
    
    private Inventory playerInventory;
    
    public WorldMap(Res res,World world,GameContainer container)
    {        
        this.res = res;
        cam = new Camera(70,50,container);
        wCam = new Camera(100,100,container);
        this.width = 100;
        this.height = 100;
        this.container = container;
        maps = new ArrayList<LocalMap>();
        travelers = new ArrayList<Traveler>();
        travelers.add(new Traveler(0,0,0,res.human1));
        locations = new Location[10][10];
        currentMap = 1;
        this.world = world;
        playerInventory = new Inventory(null,world.getItemLibrary());
        playerInventory.debugInit(world.getItemLibrary());
        
        initLocations();
        
    }
    
    
    public void initLocations()
    {
        for(int iy=0;iy<10;iy++)
        {
            for(int ix=0;ix<10;ix++)
            {
                locations[iy][ix] = new Location(ix,iy,0,res.basicTile,res,world);
            }
            
        }
    }
    
    
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        
        if(currentMap!=-1)
        {
            //travelers.get(0) will always be the traveler object that the player controls
            locations[travelers.get(0).getY()][travelers.get(0).getX()].tick(k,m, input, world,cam);
            
        }else
        {
            for(Location[] ls:locations)
            {
                for(Location l:ls)
                {
                    l.tick(k,m,input,world,cam);
                }
            }
        }
    }
    
    public void render(Graphics g,boolean animate)
    {
        if(currentMap!=-1)
        {
            
            if(locations[travelers.get(0).getY()][travelers.get(0).getX()].getLocalMap()!=null)
            {
                //travelers.get(0) will always be the traveler object that the player controls
                locations[travelers.get(0).getY()][travelers.get(0).getX()].getLocalMap().render(g,animate);
            }
            
        }else
        {
            for(Location[] ls:locations)
            {
                for(Location l:ls)
                {
                    l.render(wCam,cam);
                }
            }
        }
    }
    
    
    @Override
    public int getWidthInTiles() {
        return width;
    }

    @Override
    public int getHeightInTiles() {
        return height;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
    
    }

    @Override
    public boolean blocked(PathFindingContext context, int tx, int ty) {
        return locations[ty][tx].isSolid();
    }

    @Override
    public float getCost(PathFindingContext context, int tx, int ty) {
        return 1.0f;
    }
    
    public Location getCurrentLocation()
    {
        return locations[travelers.get(0).getY()][travelers.get(0).getX()];
    }
    
    public LocalMap getCurrentLocalMap()
    {
        return locations[travelers.get(0).getY()][travelers.get(0).getX()].getLocalMap();
    }

    public Camera getwCam() {
        return wCam;
    }

    public void setwCam(Camera wCam) {
        this.wCam = wCam;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public ArrayList<LocalMap> getMaps() {
        return maps;
    }

    public void setMaps(ArrayList<LocalMap> maps) {
        this.maps = maps;
    }

    public ArrayList<Traveler> getTravelers() {
        return travelers;
    }

    public void setTravelers(ArrayList<Traveler> travelers) {
        this.travelers = travelers;
    }

    public Location[][] getLocations() {
        return locations;
    }

    public void setLocations(Location[][] locations) {
        this.locations = locations;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public GameContainer getContainer() {
        return container;
    }

    public void setContainer(GameContainer container) {
        this.container = container;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Pawn getPlayer() {
        return player;
    }

    public void setPlayer(Pawn player) {
        this.player = player;
        playerInventory.setOwner(player);
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public void setPlayerInventory(Inventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    
    
    
}
