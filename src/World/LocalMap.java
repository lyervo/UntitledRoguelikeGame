/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Camera.Camera;
import Entity.Entity;
import Entity.Furniture;
import Entity.Pawn;
import Item.Item;
import Item.ItemLibrary;
import Item.ItemPile;
import UI.OptionTab;
import Res.Res;
import MapUI.TileOptionTab;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ILosBoard;
import rlforj.los.PrecisePermissive;




/**
 *
 * @author Timot
 */
public class LocalMap implements TileBasedMap, ILosBoard
{
    
    private int id;
    private int width,height;
    
    public AStarPathFinder pf;
    
    private Tile[][] tiles;
    
    private Res res;
    
    private ArrayList<Pawn> pawns;
    private ArrayList<ItemPile> itemPiles;
    
    private IFovAlgorithm fov;
    private Camera cam;
    private World world;
    
    private OptionTab optionTab;
   
    private Input input;
    private boolean hoveringTab;
    
    private WorldMap wm;
    
    private GameContainer container;
    
    private ItemLibrary itemLibrary;
    
    private ArrayList<Furniture> furnitures;
    
    public LocalMap(int id,int width,int height,Res res,World world,GameContainer container,ItemLibrary itemLibrary)
    {
        this.wm = world.getWm();
        this.container = container;
        this.res = res;
        this.width = width;
        this.height = height;
        this.id = id;
        this.input = world.getInput();
        this.world = world;
        tiles = new Tile[height][width];
        System.out.println("width"+width+" height"+height);
        cam = new Camera(width,height,container);
        this.itemLibrary = itemLibrary;
        
        furnitures = new ArrayList<Furniture>();
        
        furnitures.add(new Furniture(0,2,2,world.getEntityLibrary().getFurnitureTemplateByName("Workbench")));
        furnitures.add(new Furniture(1,2,4,world.getEntityLibrary().getFurnitureTemplateByName("Camp Fire"),true));
        
        
        generateTiles();
        hoveringTab = false;
        fov = new PrecisePermissive();
       
        
        itemPiles = new ArrayList<ItemPile>();
        
        
        itemPiles.add(new ItemPile(2,0,5,itemLibrary.getItemByTrueName("Healing Potion")));
        itemPiles.add(new ItemPile(3,4,0,itemLibrary.getItemByTrueName("Poison")));
        itemPiles.add(new ItemPile(4,3,0,itemLibrary.getItemByTrueName("Healing Potion")));
        
        
        
        pawns = new ArrayList<Pawn>();
        
        pawns.add(new Pawn(0,3,5,res.human1,fov,"Eve",world.getItemLibrary()));
        
        world.getWm().setPlayer(pawns.get(0));
        
        
        ((Pawn)pawns.get(0)).setControl(true);
//        pawns.add(new Pawn(1,7,7,res.human2,fov));
        
        pf = new AStarPathFinder(this,100,true);
        
        pawns.add(new Pawn(2,2,2,res.human2,fov,"Adam",world.getItemLibrary()));
        
       
        optionTab = null;
        
        for(Entity p:pawns)
        {
            ((Pawn)p).initPawn(this, pf);
        }
        cam.setTarget((Pawn)pawns.get(0));
        
        
    }
    
    
    
    public void generateTiles()
    {
        for(int iy=0;iy<height;iy++)
        {
            for(int ix=0;ix<width;ix++)
            {
                tiles[iy][ix] = new Tile(ix,iy,51,res.basicTile,cam.getTile_size());
                if(ix==4&&iy>=3&&iy<=10)
                {
                    tiles[iy][ix].setWall(new Wall(ix,iy,0,res.basicWall));
                }
            }
        }
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        
        
        if(optionTab!=null)
        {
            hoveringTab = true;
            optionTab.tick(k, m,input,this);
        }
        
        
        for(Entity e:pawns)
        {
            e.tick(k, m,input, world);
        }
        
        for(Furniture f:furnitures)
        {
            f.tick(k, m, input, world);
        }

        cam.tick(k,m,world);
        for (ItemPile ip : itemPiles)
        {
            ip.tick(k, m,input, world);
        }
        for (Tile[] ts : tiles)
        {
            for (Tile t : ts)
            {
                t.tick(k, m, input, world, cam);
            }
        }

        if(m[0]&&optionTab!=null)
        {
            optionTab.checkClick(m, this);
            optionTab = null;
            hoveringTab = false;
        }
        else if(k[255])
        {
            optionTab = null;
            hoveringTab = false;
        }
        
       
       
        
       
       
       
       
       
       
    }
    
    public void render(Graphics g,boolean animate)
    {
        
        
        for(Tile[] tys:tiles)
        {
            for(Tile txs:tys)
            {
                txs.render(cam);
            }
        }
        for(ItemPile ip:itemPiles)
        {
            ip.render(cam, this, animate);
        }
        
        for(Furniture f:furnitures)
        {
            f.render(cam, this, animate);
        }
        
        for(Entity e:pawns)
        {
            e.render(cam,this, animate);
        }
        if(optionTab!=null)
        {
            optionTab.render(g);
        }
    }
    
    public Entity entityAt(int x,int y)
    {
        for(Entity e: pawns)
        {
            if(e.getX()==x&&e.getY()==y)
            {
                return e;
            }
        }
        return null;
    
    }
    
    public ArrayList<Item> getItemsAt(int x,int y)
    {
        for(ItemPile ip:itemPiles)
        {
            if(ip.getX()==x&&ip.getY()==y)
            {
                return ip.getItems();
            }
        }
        return null;
    }
    
    public ItemPile getItemPileAt(int x,int y)
    {
        for(ItemPile ip:itemPiles)
        {
            if(ip.getX()==x&&ip.getY()==y)
            {
                
                return ip;
            }
        }
        
        return null;
    }
    
    public Pawn getPawnAt(int x,int y)
    {
        for(Pawn p:pawns)
        {
            if(p.getX()==x&&p.getY()==y)
            {
                return p;
            }
        }
        return null;
    }
    
    public void spawnOptionTab(Tile t)
    {
        optionTab = new TileOptionTab(input.getMouseX(),input.getMouseY(),container,this,res.disposableDroidBB,res,t);
    }

    @Override
    public int getWidthInTiles()
    {
        return width;
    }

    @Override
    public int getHeightInTiles()
    {
        return height;
    }

    @Override
    public void pathFinderVisited(int x, int y) 
    {
    
    }

    @Override
    public boolean blocked(PathFindingContext context, int tx, int ty)
    {
        return tiles[ty][tx].isSolid();
    }

    @Override
    public float getCost(PathFindingContext context, int tx, int ty) 
    {
        return 1.0f;
    }
    
    public int getID()
    {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public AStarPathFinder getPf() {
        return pf;
    }

    public void setPf(AStarPathFinder pf) {
        this.pf = pf;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(ArrayList<Pawn> pawns) {
        this.pawns = pawns;
    }

    public Furniture furnitureAt(int x,int y)
    {
        for(Furniture f:furnitures)
        {
            if(f.getX()==x&&f.getY()==y)
            {
                return f;
            }
        }
        return null;
    }
    
    public ArrayList<Furniture> furnituresAround(int x,int y)
    {
        ArrayList<Furniture> f = new ArrayList<Furniture>();
        for(Furniture fu:furnitures)
        {
            if(((fu.getX()-x)>=-1&&(fu.getX()-x)<=1)&&((fu.getY()-y)>=-1&&(fu.getY()-y)<=1))
            {
                if(fu.isFuelable()&&fu.getFuel()>=2)
                {
                    f.add(fu);
                }else if(!fu.isFuelable())
                {
                    f.add(fu);
                }
            }
        }
        
        return f;
        
    }
    
    
    @Override
    public boolean contains(int x, int y) {
        
        if(x>=0&&x<=width-1&&y>=0&&y<=height-1)
        {
            return true;
        }else
        {
            return false;
        }
    }

    @Override
    public boolean isObstacle(int x, int y) {
        try
        {
            return tiles[y][x].isSolid();
        }catch(IndexOutOfBoundsException e)
        {
            return true;
        }
    }

    @Override
    public void visit(int x, int y)
    {
        tiles[y][x].setVisit(2);
    }

    public ArrayList<ItemPile> getItemPiles() {
        return itemPiles;
    }

    public void setItemPiles(ArrayList<ItemPile> itemPiles) {
        this.itemPiles = itemPiles;
    }

    public IFovAlgorithm getFov() {
        return fov;
    }

    public void setFov(IFovAlgorithm fov) {
        this.fov = fov;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public OptionTab getOptionTab() {
        return optionTab;
    }

    public void setOptionTab(OptionTab optionTab) {
        this.optionTab = optionTab;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public boolean isHoveringTab() {
        return hoveringTab;
    }

    public void setHoveringTab(boolean hoveringTab) {
        this.hoveringTab = hoveringTab;
    }
    
    
    public Pawn getPlayer()
    {
        for(Entity p: pawns)
        {
            if(((Pawn)p).isControl())
            {
                return (Pawn)p;
            }
        }
        return null;
    }

    public WorldMap getWm() {
        return wm;
    }

    public void setWm(WorldMap wm) {
        this.wm = wm;
    }

    public GameContainer getContainer() {
        return container;
    }

    public void setContainer(GameContainer container) {
        this.container = container;
    }

    public ItemLibrary getItemLibrary() {
        return itemLibrary;
    }

    public void setItemLibrary(ItemLibrary itemLibrary) {
        this.itemLibrary = itemLibrary;
    }

    public ArrayList<Furniture> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(ArrayList<Furniture> furnitures) {
        this.furnitures = furnitures;
    }
    
   
    
    
    
    
}
