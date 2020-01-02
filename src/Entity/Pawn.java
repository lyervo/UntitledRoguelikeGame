/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Camera.Camera;
import Culture.SubFaction;
import Entity.Plant.Plant;
import Item.Inventory;
import Item.Item;
import Item.ItemLibrary;
import Item.ItemPile;
import World.LocalMap;
import World.Tile;
import World.World;
import World.Zone;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import rlforj.los.IFovAlgorithm;


/**
 *
 * @author Timot
 */
public class Pawn extends Entity
{
    private String name;
    private boolean control;
    private Path path;
    private int step;
    
    private FOVBoard fovBoard;
    
    
    private ArrayList<Task> tasks;
    private AStarPathFinder pf;
    private IFovAlgorithm fov;
    
    private long current;
    
    private Inventory inventory;
    
    private Meter hp;
    
    private ArrayList<Status> status;
    

    private ArrayList<String> recipes;
    
    private ArrayList<SubFaction> subFactions;
    
    
    
    public Pawn(int id, int x, int y,Image texture,IFovAlgorithm fov,String name,ItemLibrary itemLibrary)
    {
        super(id, x, y,texture);
        hp = new Meter("HP",100,50,0);
        this.fov = fov;
        this.control = false;
        step = 1;
        tasks = new ArrayList<Task>();
        tasks.add(new Task(0,0,0,0,"nothing"));
        inventory = new Inventory(this,itemLibrary);
        this.name = name;
        current = System.currentTimeMillis();
        status = new ArrayList<Status>();
        subFactions = new ArrayList<SubFaction>();
        
    }
    
    public void initPawn(LocalMap lm,AStarPathFinder pf)
    {
        this.pf = pf;
        fovBoard = new FOVBoard(lm.getTiles());
        if(control)
        {
            fov.visitFieldOfView(lm, x, y, 7);
        }
    }
    
    
    public void pawnAction(Input input,World world)
    {
        if (world.isMoved())
        {
            fovBoard.resetVision(x, y, 7);
            fov.visitFieldOfView(fovBoard, x, y, 7);
            
            inventory.getCrafting().getNearbyStations(world.getWm().getCurrentLocalMap());
            
            
            for(SubFaction sf:subFactions)
            {
                for(Zone z:sf.getZones())
                {
                    if(z.getId() == world.getWm().getCurrentLocalMap().getID())
                    {
                        for(Pawn p:world.getWm().getCurrentLocalMap().getPawns())
                        {
                            if(p.getId()!=this.id)
                            {
                                if(z.isWithinZone(p)&&!isSameFaction(sf,p.getSubFactions()))
                                {
                                    if(!tasks.get(0).getType().equals("follow_target"))
                                    {

                                        Task t = new Task(p.getX(),p.getY(),p.getId(),0,"follow_target");
                                        t.setTarget(p);
                                        addTask(t);
                                        sortTask();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        int taskResult = 0;

        if(tasks.get(0).getType().equals("follow_target"))
        {
            pawnFollowLogic(world);
        }else if(tasks.get(0).getType().equals("grab_item"))
        {
            pawnGrabItemLogic(world);
        }else if(tasks.get(0).getType().equals("craft"))
        {
            pawnCraftingLogic(world);
        }else if(tasks.get(0).getType().equals("search_item"))
        {
            pawnSearchItemLogic(world);
        }else if(tasks.get(0).getType().equals("harvest_plant"))
        {
            pawnHarvestPlantLogic(world);
        }else if(tasks.get(0).getType().equals("plant_seed"))
        {
            pawnPlantSeedLogic(world);
        }
        
        
        
        
        
        
        
    }
    
    
    
    
    public void pawnFollowLogic(World world)
    {
        if (path == null&&distanceBetween(tasks.get(0).getTarget())>=1)
        {
            step = 1;
            path = pf.findPath(null, x, y, tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
        }else if(tasks.get(0).getX()!=tasks.get(0).getTarget().getX()||tasks.get(0).getTarget().getY()!=tasks.get(0).getY())
        {
            path = null;
            step = 1;
            path = pf.findPath(null, x, y, tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
        }
        if(world.isMoved()&&path!=null)
        {
            
            if(distanceBetween(tasks.get(0).getTarget())<=2)
            {
                path = null;
                step = 1;
            }else
            {
                doPath(world.getWm().getCurrentLocalMap());
            }
        }
        
        
        
        
    }
    
    public void pawnCraftingLogic(World world)
    {
        if(inventory.getCrafting().getTargetRecipe()==null)
        {
            inventory.getCrafting().setCraftingTarget(tasks.get(0).getInfo());
            
        }

        if(!inventory.getCrafting().checkCraftingRecipe(inventory.getCrafting().getTargetRecipe()))
        {
            
        }
        
        if (world.isMoved())
        {
  
            inventory.getCrafting().craft();
            if (inventory.getCrafting().finishedCrafting())
            {
                tasks.get(0).clearTask();
                path = null;
                
            }

           

        }
    }

    @Override
    public void tick(boolean[] k, boolean[] m,Input input, World world)
    {
        if(control&&!world.isMoved()&&!world.getDialogue().isDisplay())
        {
            
            playerAction(k,m,input,world);
            
        }else
        {
            pawnAction(input,world);
        }
        
        if(world.isMoved())
        {
            
            processStatus(k,m,input,world);
            if(control)
            {
                world.getWm().getPlayerInventory().tick(k, m, input, world);
            }
            
        }
        
    }
    
    public void playerAction(boolean[] k,boolean[] m,Input input,World world)
    {
        if(k[255])
        {
            //k[255] will be true whenever a key is pressed
            tasks.get(0).clearTask();
            path = null;
            step = 1;
            current = System.currentTimeMillis();
            playerKeyboardControl(k, input, world);
                
        }
        
        
        if (tasks.get(0).getType().equals("grab_item"))
        {
            playerGrabItemLogic(k, m, input, world);
        }else if (tasks.get(0).getType().equals("craft"))
        {
            playerCraftingLogic(k, m, input, world);
        }else if (tasks.get(0).getType().equals("talk_to_target"))
        {
            playerMoveToTalkLogic(k, m, input, world);
        }else if (tasks.get(0).getType().equals("walk_to"))
        {
            playerWalkLogic(k, m, input, world);
        }else if(tasks.get(0).getType().equals("harvest_plant"))
        {
            playerHarvestPlantLogic(k,m,input,world);
        }else if(tasks.get(0).getType().equals("plant_seed"))
        {
            playerPlantSeedLogic(k,m,input,world);
        }

        if (world.isMoved())
        {

            
            int ix = x-8;
            int iy = y-8;
            int mx = x+9;
            int my = y+9;
            
            if(iy<0)
            {
                iy = 0;
            }
            if(ix<0)
            {
                ix = 0;
            }
            
            
            if(my>world.getWm().getCurrentLocalMap().getHeight())
            {
                my = world.getWm().getCurrentLocalMap().getHeight();
            }
            if(mx>world.getWm().getCurrentLocalMap().getWidth())
            {
                mx = world.getWm().getCurrentLocalMap().getWidth();
            }
            
            
            
            
            for (int i=iy;i<my;i++)
            {
                for (int j=ix;j<mx;j++)
                {
                    world.getWm().getCurrentLocalMap().getTiles()[i][j].resetVisit();
                }
            }
            fov.visitFieldOfView(world.getWm().getCurrentLocalMap(), x, y, 7);

        }
    }
    
    public void processStatus(boolean[] k,boolean[] m,Input input,World world)
    {
        for(int i=0;i<status.size();i++)
        {
            if(status.get(i).getEffect().getDuration()>0)
            {
                status.get(i).tick(world, world.getWm().getCurrentLocalMap(),this);
            }
            if(status.get(i).getEffect().getDuration()==0)
            {
                status.remove(i);
            }
        }
    }
    
    
    
    public void walkTo(int x,int y)
    {
        Task task = new Task(x,y,0,0,"walk_to");
        addTask(task);
    }
    
    public void grabItemAt(int x,int y,int id,int index,String itemName)
    {
        Task task = new Task(x,y,id,index,"grab_item");
        task.setInfo(itemName);
        addTask(task);
        
    }
    
    public void sortTask()
    {
        tasks.sort(new TaskComparator());
    }
    
    public void addTask(Task task)
    {
        if(control)
        {
            tasks.clear();
            tasks.add(task);
        }else
        {
            tasks.add(0,task);
            sortTask();
        }
    }
    
    
    public void checkVision()
    {
        
    }
    
    public void calcPath(int x,int y)
    {
        if(path==null)
        {
            step = 1;
            path = pf.findPath(null,this.x,this.y,x,y);
            if(path==null)
            {
                return;
            }
            
        }
            
    }
    
    
    @Override
    public void render(Camera cam,LocalMap map,boolean animate)
    {
        if(map.getTiles()[y][x].isVisit())
        {
            texture.draw(x*cam.getTile_size()+cam.getXofs(),y*cam.getTile_size()+cam.getYofs(),cam.getTile_size(),cam.getTile_size());
        }
        
    }
    
    
    public void doPath(LocalMap lm)
    {
        if(step<path.getLength())
        {
            if(path.getX(step)>x&&path.getY(step)>y)
            {
                move(1,1,lm);
            }else if(path.getX(step)>x&&path.getY(step)<y)
            {
                move(1,-1,lm);
            }else if(path.getX(step)<x&&path.getY(step)>y)
            {
                move(-1,1,lm);
            }else if(path.getX(step)<x&&path.getY(step)<y)
            {
                move(-1,-1,lm);
            }else if(path.getX(step)>x&&path.getY(step)==y)
            {
                move(1,0,lm);
            }else if(path.getX(step)<x&&path.getY(step)==y)
            {
                move(-1,0,lm);
            }else if(path.getX(step)==x&&path.getY(step)>y)
            {
                move(0,1,lm);
            }else if(path.getX(step)==x&&path.getY(step)<y)
            {
                move(0,-1,lm);
              
            }
            step++;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }


    public void playerKeyboardControl(boolean[] k,Input input,World world)
    {
        if (k[Input.KEY_A]&&!world.getWm().getCurrentLocalMap().isObstacle(x-1, y)) {
            move(-1, 0,world.getWm().getCurrentLocalMap());
            world.moved();
        } else if (k[Input.KEY_D]&&!world.getWm().getCurrentLocalMap().isObstacle(x+1, y)) {
            move(1, 0,world.getWm().getCurrentLocalMap());
            world.moved();
        } else if (k[Input.KEY_W]&&!world.getWm().getCurrentLocalMap().isObstacle(x, y-1)) {
            move(0, -1,world.getWm().getCurrentLocalMap());
            world.moved();
        } else if (k[Input.KEY_S]&&!world.getWm().getCurrentLocalMap().isObstacle(x, y+1)) {
            move(0, 1,world.getWm().getCurrentLocalMap());
            world.moved();
        }else if(k[Input.KEY_Q]&&!world.getWm().getCurrentLocalMap().isObstacle(x-1, y-1))
        {
            move(-1,-1,world.getWm().getCurrentLocalMap());
            world.moved();
        }else if(k[Input.KEY_E]&&!world.getWm().getCurrentLocalMap().isObstacle(x+1, y-1))
        {
            move(+1,-1,world.getWm().getCurrentLocalMap());
            world.moved();
        }else if(k[Input.KEY_Z]&&!world.getWm().getCurrentLocalMap().isObstacle(x-1, y+1))
        {
            move(-1,+1,world.getWm().getCurrentLocalMap());
            world.moved();
        }else if(k[Input.KEY_C]&&!world.getWm().getCurrentLocalMap().isObstacle(x+1, y+1))
        {
            move(+1,+1,world.getWm().getCurrentLocalMap());
            world.moved();
        }else if(k[Input.KEY_SPACE])
        {
            world.moved();
        }
    }
    
    public void playerWalkLogic(boolean[] k,boolean[] m,Input input,World world)
    {
        if(path == null)
        {
            calcPath(tasks.get(0).getX(),tasks.get(0).getY());
        }
        if (world.getWm().getPlayerInventory().getCrafting().isCrafting())
        {
            world.getWm().getPlayerInventory().getCrafting().clearCraftingTarget();
            tasks.get(0).clearTask();
            path = null;
        }
        if (System.currentTimeMillis() - current >= 250)
        {
            current = System.currentTimeMillis();
            doPath(world.getWm().getCurrentLocalMap());
            world.moved();
            if (step == path.getLength()) {
                path = null;
                tasks.get(0).clearTask();
                step = 1;
            }

        }
    }
    
    public void playerGrabItemLogic(boolean[] k,boolean[] m,Input input,World world)
    {
        if(tasks.get(0).getTarget() == null)
        {
            tasks.get(0).setTarget(world.getWm().getCurrentLocalMap().getItemPileById(tasks.get(0).getId()));
        }
        
        if(!tasks.get(0).getTarget().hasItem(tasks.get(0).getInfo()))
        {
            tasks.get(0).clearTask();
            path = null;
            return;
        }
        
        if(distanceBetween(tasks.get(0).getTarget()) == 0)
        {
            if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y) != null)
            {

                
                    if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getId() == tasks.get(0).getId())
                    {

                        world.getAncestor().addText(world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getItems().get(tasks.get(0).getIndex()).getName() + " added to inventory.");

                        world.getWm().getCurrentLocalMap().getItemPileAt(x, y).takeFrom(world.getWm().getPlayerInventory(), tasks.get(0).getInfo(), world.getWm().getCurrentLocalMap(), -1);
        //                            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                        tasks.get(0).clearTask();
                        path = null;
                        world.moved();
                    }

            }
        }else
        {
            if (path == null)
            {
                calcPath(tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
            }
            
            if(System.currentTimeMillis() - current >= 250)
            {
                current = System.currentTimeMillis();
                doPath(world.getWm().getCurrentLocalMap());
                world.moved();

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                

            }
        }
    }
    
    public void playerHarvestPlantLogic(boolean[] k,boolean[] m,Input input,World world)
    {

        Tile tile = world.getWm().getCurrentLocalMap().getTiles()[tasks.get(0).getY()][tasks.get(0).getX()];
        
        if(tile.getPlant()==null)
        {
            tasks.get(0).clearTask();
            path = null;
            step = 1;
            return;
        }
        
        if(!tile.getPlant().getCurrentName().equals(tasks.get(0).getInfo()))
        {
            tasks.get(0).clearTask();
            path = null;
            step = 1;
            return;
        }
        
        if(tasks.get(0).getIndex()!=100&&(!world.getWm().getPlayerInventory().hasItemType(tasks.get(0).getIndex())&&!world.getWm().getPlayerInventory().getEquipment().getMainHandSlot().equipmentIsType(tasks.get(0).getIndex())))
        {
            world.getAncestor().addText("You do not have the tools to perform this tasks.get(0).");
            tasks.get(0).clearTask();
            path = null;
            step = 1;
            return;
        }
        
        if(distanceBetween(tasks.get(0).getTarget()) == 1)
        {
            if(world.getWm().getPlayerInventory().getEquipment().getMainHandSlot().getItem() != null)
            {
                if(!world.getWm().getPlayerInventory().getEquipment().getMainHandSlot().getItem().getProperties().contains(tasks.get(0).getIndex()))
                {
                    world.getWm().getPlayerInventory().getEquipment().equipEquipmentWithProperty(tasks.get(0).getIndex());
                    world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                    world.getEquipment_ui().refreshUI();
                }
            }else
            {
                world.getWm().getPlayerInventory().getEquipment().equipEquipmentWithProperty(tasks.get(0).getIndex());
                world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                world.getEquipment_ui().refreshUI();
            }
            
            if(System.currentTimeMillis() - current >= 250)
            {
                current = System.currentTimeMillis();
                
                world.getAncestor().addText("You used "+world.getWm().getPlayerInventory().getEquipment().getMainHandSlot().getItem().getName()
                                            +" on "+ tile.getPlant().getCurrentName());
                tile.getPlant().harvest(world.getWm().getPlayerInventory().getEquipment().getMainHandSlot().getItem(), this, world);
                
                
                world.moved();

                
                

            }
            
            
        }else
        {
            if (path == null)
            {
                if(((Plant)tasks.get(0).getTarget()).getCurrentSolid())
                {
                    boolean foundPath = false;
                    for(int i=-1;i<=1;i++)
                    {
                        for(int j=-1;j<=1;j++)
                        {
                            int jy = j+tasks.get(0).getTarget().getY();
                            int ix = i+tasks.get(0).getTarget().getX();
                            if(jy>=0&&jy<world.getWm().getCurrentLocalMap().getHeight()-1
                            &&ix>=0&&ix<world.getWm().getCurrentLocalMap().getWidth()-1)
                            {
                                if(!world.getWm().getCurrentLocalMap().getTiles()[jy][ix].isSolid())
                                {
                                    calcPath(ix,jy);
                                    foundPath = true;
                                }
                            }
                        }
                        
                    }
                    if(!foundPath)
                    {
                        world.getAncestor().addText("No path can reach the target");
                        tasks.get(0).clearTask();
                        path = null;
                        step = 1;
                    }
                }else
                {
                    calcPath(tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
                }
            }
            
            if(System.currentTimeMillis() - current >= 250)
            {
                current = System.currentTimeMillis();
                doPath(world.getWm().getCurrentLocalMap());
                world.moved();

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                

            }
        }
    }
    
    
    public void playerPlantSeedLogic(boolean[] k,boolean[] m,Input input,World world)
    {

        Tile tile = world.getWm().getCurrentLocalMap().getTiles()[tasks.get(0).getY()][tasks.get(0).getX()];
        Item seed = world.getWm().getPlayerInventory().getItemByName(tasks.get(0).getInfo());
        if(tile.getPlant()!=null||tile.isSolid()||!seed.getProperties().contains(tile.getType()))
        {
            tasks.get(0).clearTask();
            path = null;
            step = 1;
            return;
        }
        
        if(distanceBetween(tasks.get(0).getX(),tasks.get(0).getY()) <= 1)
        {
            if(System.currentTimeMillis() - current >= 250)
            {
                current = System.currentTimeMillis();

                

            
                
                if(seed == null)
                {
                    world.getAncestor().addText("You do not have the item for this tasks.get(0).");

                }else
                {
                    tile.setPlant(world.getWm().getPlayerInventory().getItemByName(tasks.get(0).getInfo()),world);
                    world.getWm().getPlayerInventory().removeItem(seed);
                    world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                }

                tasks.get(0).clearTask();
                path = null;
                step = 1;
                world.moved();
                return;
            }
        }else
        {
            if (path == null)
            {
                
                calcPath(tasks.get(0).getX(), tasks.get(0).getY());
                
            }
            
            if(System.currentTimeMillis() - current >= 250)
            {
                current = System.currentTimeMillis();
                doPath(world.getWm().getCurrentLocalMap());
                world.moved();

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                

            }
        }
    }
    
    public void pawnPlantSeedLogic(World world)
    {

        Tile tile = world.getWm().getCurrentLocalMap().getTiles()[tasks.get(0).getY()][tasks.get(0).getX()];
        
        Item seed = inventory.getItemByName(tasks.get(0).getInfo());
        
        if(seed == null)
        {
            tasks.remove(0);
            path = null;
            step = 1;
            System.out.println(tasks.get(0).getInfo());
            return;
        }
        
        if(tile.getPlant()!=null||tile.isSolid()||!seed.getProperties().contains(tile.getType()))
        {
            tasks.remove(0);
            path = null;
            step = 1;
            return;
        }
        
        if(distanceBetween(tasks.get(0).getX(),tasks.get(0).getY()) <= 1)
        {
            if(world.isMoved())
            {
                
                if(seed == null)
                {


                }else
                {
                    tile.setPlant(inventory.getItemByName(tasks.get(0).getInfo()),world);
                    inventory.removeItem(seed);

                }

                tasks.remove(0);
                path = null;
                step = 1;
                return;
            }
            
        }else
        {
            if (path == null)
            {
                
                calcPath(tasks.get(0).getX(), tasks.get(0).getY());
                
            }
            
            if(world.isMoved())
            {
                
                doPath(world.getWm().getCurrentLocalMap());
                

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                

            }
        }
    }
    
    
    public void pawnHarvestPlantLogic(World world)
    {

        Tile tile  = world.getWm().getCurrentLocalMap().getTiles()[tasks.get(0).getY()][tasks.get(0).getX()];
        
        if(tile.getPlant()==null)
        {
            tasks.remove(0);
            path = null;
            step = 1;
            return;
        }
        
        if(!tile.getPlant().getCurrentName().equals(tasks.get(0).getInfo()))
        {
            tasks.get(0).clearTask();
            path = null;
            step = 1;
            return;
        }
        
        if(tasks.get(0).getIndex()!=100&&(!inventory.hasItemType(tasks.get(0).getIndex())&&!inventory.getEquipment().getMainHandSlot().equipmentIsType(tasks.get(0).getIndex())))
        {
            
            tasks.get(0).clearTask();
            path = null;
            step = 1;
            return;
        }
        
        if(distanceBetween(tasks.get(0).getTarget()) == 1)
        {
            if(inventory.getEquipment().getMainHandSlot().getItem() != null)
            {
                if(inventory.getEquipment().getMainHandSlot().getItem().getProperties().contains(tasks.get(0).getIndex()))
                {
                    inventory.getEquipment().equipEquipmentWithProperty(tasks.get(0).getIndex());
                    
                }
            }else
            {
                inventory.getEquipment().equipEquipmentWithProperty(tasks.get(0).getIndex());
               
            }
            
            if(world.isMoved())
            { 
                
                tile.getPlant().harvest(inventory.getEquipment().getMainHandSlot().getItem(), this, world);
                
                

                
                

            }
            
            
        }else
        {
            if (path == null)
            {
                if(((Plant)tasks.get(0).getTarget()).getCurrentSolid())
                {
                    boolean foundPath = false;
                    for(int i=-1;i<=1;i++)
                    {
                        for(int j=-1;j<=1;j++)
                        {
                            int jy = j+tasks.get(0).getTarget().getY();
                            int ix = i+tasks.get(0).getTarget().getX();
                            if(jy>=0&&jy<world.getWm().getCurrentLocalMap().getHeight()-1
                            &&ix>=0&&ix<world.getWm().getCurrentLocalMap().getWidth()-1)
                            {
                                if(!world.getWm().getCurrentLocalMap().getTiles()[jy][ix].isSolid())
                                {
                                    calcPath(ix,jy);
                                    foundPath = true;
                                }
                            }
                        }
                        
                    }
                    if(!foundPath)
                    {
                        
                        tasks.get(0).clearTask();
                        path = null;
                        step = 1;
                    }
                }else
                {
                    calcPath(tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
                }
            }
            
            if(world.isMoved())
            {
                
                doPath(world.getWm().getCurrentLocalMap());
               

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                

            }
        }
    }
    
    public void pawnGrabItemLogic(World world)
    {
        if(tasks.get(0).getTarget() == null)
        {
            tasks.get(0).setTarget(world.getWm().getCurrentLocalMap().getItemPileById(tasks.get(0).getId()));
        }
        
        if(!tasks.get(0).getTarget().hasItem(tasks.get(0).getInfo()))
        {
            tasks.get(0).clearTask();
            path = null;
            return;
        }
        
        if(distanceBetween(tasks.get(0).getTarget()) == 0)
        {
            if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y) != null)
            {

                
                    if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getId() == tasks.get(0).getId())
                    {


                        world.getWm().getCurrentLocalMap().getItemPileAt(x, y).takeFrom(inventory, tasks.get(0).getInfo(), world.getWm().getCurrentLocalMap(), -1);
        //                            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                        tasks.get(0).clearTask();
                        path = null;
                        world.moved();
                    }

            }
        }else
        {
            if (path == null)
            {
                calcPath(tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
            }
            
            if(world.isMoved())
            {
                
                doPath(world.getWm().getCurrentLocalMap());
                

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                

            }
        }
    }
    
    public void pawnSearchItemLogic(World world)
    {
        if(tasks.get(0).getTarget() == null)
        {
            ArrayList<ItemPile> ips = world.getWm().getCurrentLocalMap().getItemPilesWithItemByName(tasks.get(0).getInfo());
            if(ips.isEmpty())
            {
                tasks.get(0).clearTask();
            }
            int itemPileID = getClosestItemPile(ips);
            
            tasks.get(0).setId(itemPileID);
            tasks.get(0).setTarget(world.getWm().getCurrentLocalMap().getItemPileById(tasks.get(0).getId()));
            
        }
        
        if(!tasks.get(0).getTarget().hasItem(tasks.get(0).getInfo()))
        {
            tasks.get(0).clearTask();
            path = null;
            return;
        }
        
        if(distanceBetween(tasks.get(0).getTarget()) == 0)
        {
            if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y) != null)
            {

                
                    if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getId() == tasks.get(0).getId())
                    {


                        world.getWm().getCurrentLocalMap().getItemPileAt(x, y).takeFrom(inventory, tasks.get(0).getInfo(), world.getWm().getCurrentLocalMap(), -1);
        //                            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                        tasks.get(0).clearTask();
                        path = null;
                        world.moved();
                    }

            }
        }else
        {
            if (path == null)
            {
                calcPath(tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
            }
            
            if(world.isMoved())
            {
                
                doPath(world.getWm().getCurrentLocalMap());
                

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                

            }
        }
    }
    
    public void playerCraftingLogic(boolean[] k,boolean[] m,Input input,World world)
    {
        if (k[255] || (m[19] && world.getZ() != world.getCraftingWindow().getZ())) {
            tasks.get(0).clearTask();
            path = null;
            world.getWm().getPlayerInventory().getCrafting().clearCraftingTarget();
        }

        if (System.currentTimeMillis() - current >= 250) {
            current = System.currentTimeMillis();
            world.getWm().getPlayerInventory().getCrafting().craft();
            if (world.getWm().getPlayerInventory().getCrafting().finishedCrafting()) {
                tasks.get(0).clearTask();
                path = null;
                world.getCrafting_ui().refreshUI(world.getWm().getCurrentLocalMap());
                world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
            }

            world.moved();

        }
    }
    
    public void playerMoveToTalkLogic(boolean[] k,boolean[] m,Input input,World world)
    {
        if(tasks.get(0).getTarget() == null)
        {
            tasks.get(0).setTarget(world.getWm().getCurrentLocalMap().getPawnById(tasks.get(0).getId()));
        }
        if(distanceBetween(tasks.get(0).getTarget()) <= 1)
        {
            world.getDialogue().switchDialog(6, world);
            world.getDialogue().display();
            tasks.get(0).clearTask();
            path = null;
        }else
        {
            if (path == null)
            {
                calcPath(tasks.get(0).getTarget().getX(), tasks.get(0).getTarget().getY());
            }
            
            if(System.currentTimeMillis() - current >= 250)
            {
                current = System.currentTimeMillis();
                doPath(world.getWm().getCurrentLocalMap());
                world.moved();

                if(step == path.getLength())
                {
                    path = null;
                    step = 1;
                }
                path = null;

            }

        }
    }
    
    public boolean isSameFaction(SubFaction sf,ArrayList<SubFaction> sfs)
    {
        if(sfs.isEmpty())
        {
            return false;
        }
        for(SubFaction s:sfs)
        {
            if(s.getSubFactionName().equals(sf))
            {
                return true;
            }
        }
        return false;
    }
    
    public void removeTask()
    {
        if(tasks.size()>1)
        {
            tasks.remove(0);
        }
    }
    
    public Task getCurrentTask()
    {
        return tasks.get(0);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public FOVBoard getFovBoard() {
        return fovBoard;
    }

    public void setFovBoard(FOVBoard fovBoard) {
        this.fovBoard = fovBoard;
    }

    



    public IFovAlgorithm getFov() {
        return fov;
    }

    public void setFov(IFovAlgorithm fov) {
        this.fov = fov;
    }

    public AStarPathFinder getPf() {
        return pf;
    }

    public void setPf(AStarPathFinder pf) {
        this.pf = pf;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Meter getHp() {
        return hp;
    }

    public void setHp(Meter hp) {
        this.hp = hp;
    }

    public ArrayList<Status> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<Status> status) {
        this.status = status;
    }

    @Override
    public boolean hasItem(String name) {
        return true;
    }

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
    }

    public ArrayList<String> getRecipes()
    {
        return recipes;
    }

    public void setRecipes(ArrayList<String> recipes)
    {
        this.recipes = recipes;
    }

    public ArrayList<SubFaction> getSubFactions()
    {
        return subFactions;
    }

    public void setSubFactions(ArrayList<SubFaction> subFactions)
    {
        this.subFactions = subFactions;
    }

    
    
    
    
}
