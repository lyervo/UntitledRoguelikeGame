/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Camera.Camera;
import Item.Inventory;
import Item.ItemLibrary;
import Item.ItemPile;
import World.LocalMap;
import World.Tile;
import World.World;
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
    
    
    private Task task;
    private AStarPathFinder pf;
    private IFovAlgorithm fov;
    
    private long current;
    
    private Inventory inventory;
    
    private Meter hp;
    
    private ArrayList<Status> status;
    

    private ArrayList<String> recipes;
    
    
    
    public Pawn(int id, int x, int y,Image texture,IFovAlgorithm fov,String name,ItemLibrary itemLibrary)
    {
        super(id, x, y,texture);
        hp = new Meter("HP",100,50,0);
        this.fov = fov;
        this.control = false;
        step = 1;
        task = new Task(0,0,0,0,"nothing");
        inventory = new Inventory(this,itemLibrary);
        this.name = name;
        current = System.currentTimeMillis();
        status = new ArrayList<Status>();
        
        
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
            

        }

        if(task.getType().equals("follow_target"))
        {
            pawnFollowLogic(world);
        }else if(task.getType().equals("grab_item"))
        {
            pawnGrabItemLogic(world);
        }else if(task.getType().equals("craft"))
        {
            pawnCraftingLogic(world);
        }else if(task.getType().equals("search_item"))
        {
            pawnSearchItemLogic(world);
        }
        
        
    }
    
    
    
    
    public void pawnFollowLogic(World world)
    {
        if (path == null&&distanceBetween(task.getTarget())>=1)
        {
            step = 1;
            path = pf.findPath(null, x, y, task.getTarget().getX(), task.getTarget().getY());
        }else if(task.getX()!=task.getTarget().getX()||task.getTarget().getY()!=task.getY())
        {
            path = null;
            step = 1;
            path = pf.findPath(null, x, y, task.getTarget().getX(), task.getTarget().getY());
        }
        if(world.isMoved()&&path!=null)
        {
            
            if(distanceBetween(task.getTarget())<=2)
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
            inventory.getCrafting().setCraftingTarget(task.getInfo());
            
        }

        if (world.isMoved())
        {
  
            inventory.getCrafting().craft();
            if (inventory.getCrafting().finishedCrafting())
            {
                task.clearTask();
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
            task.clearTask();
            path = null;
            step = 1;
            current = System.currentTimeMillis();
            playerKeyboardControl(k, input, world);
                
        }
        
        if (task.getType().equals("grab_item"))
        {
            playerGrabItemLogic(k, m, input, world);
        }else if (task.getType().equals("craft"))
        {
            playerCraftingLogic(k, m, input, world);
        }else if (task.getType().equals("talk_to_target"))
        {
            playerMoveToTalkLogic(k, m, input, world);
        }else if (task.getType().equals("walk_to"))
        {
            playerWalkLogic(k, m, input, world);
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
        task = new Task(x,y,0,0,"walk_to");
    }
    
    public void grabItemAt(int x,int y,int id,int index,String itemName)
    {
        task = new Task(x,y,id,index,"grab_item");
        task.setInfo(itemName);
        
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
            calcPath(task.getX(),task.getY());
        }
        if (world.getWm().getPlayerInventory().getCrafting().isCrafting())
        {
            world.getWm().getPlayerInventory().getCrafting().clearCraftingTarget();
            task.clearTask();
            path = null;
        }
        if (System.currentTimeMillis() - current >= 250)
        {
            current = System.currentTimeMillis();
            doPath(world.getWm().getCurrentLocalMap());
            world.moved();
            if (step == path.getLength()) {
                path = null;
                task.clearTask();
                step = 1;
            }

        }
    }
    
    public void playerGrabItemLogic(boolean[] k,boolean[] m,Input input,World world)
    {
        if(task.getTarget() == null)
        {
            task.setTarget(world.getWm().getCurrentLocalMap().getItemPileById(task.getId()));
        }
        
        if(!task.getTarget().hasItem(task.getInfo()))
        {
            task.clearTask();
            path = null;
            return;
        }
        
        if(distanceBetween(task.getTarget()) == 0)
        {
            if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y) != null)
            {

                
                    if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getId() == task.getId())
                    {

                        world.getAncestor().addText(world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getItems().get(task.getIndex()).getName() + " added to inventory.");

                        world.getWm().getCurrentLocalMap().getItemPileAt(x, y).takeFrom(world.getWm().getPlayerInventory(), task.getInfo(), world.getWm().getCurrentLocalMap(), -1);
        //                            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                        task.clearTask();
                        path = null;
                        world.moved();
                    }

            }
        }else
        {
            if (path == null)
            {
                calcPath(task.getTarget().getX(), task.getTarget().getY());
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
    
    public void pawnGrabItemLogic(World world)
    {
        if(task.getTarget() == null)
        {
            task.setTarget(world.getWm().getCurrentLocalMap().getItemPileById(task.getId()));
        }
        
        if(!task.getTarget().hasItem(task.getInfo()))
        {
            task.clearTask();
            path = null;
            return;
        }
        
        if(distanceBetween(task.getTarget()) == 0)
        {
            if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y) != null)
            {

                
                    if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getId() == task.getId())
                    {


                        world.getWm().getCurrentLocalMap().getItemPileAt(x, y).takeFrom(inventory, task.getInfo(), world.getWm().getCurrentLocalMap(), -1);
        //                            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                        task.clearTask();
                        path = null;
                        world.moved();
                    }

            }
        }else
        {
            if (path == null)
            {
                calcPath(task.getTarget().getX(), task.getTarget().getY());
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
        if(task.getTarget() == null)
        {
            ArrayList<ItemPile> ips = world.getWm().getCurrentLocalMap().getItemPilesWithItemByName(task.getInfo());
            if(ips.isEmpty())
            {
                task.clearTask();
            }
            int itemPileID = getClosestItemPile(ips);
            
            task.setId(itemPileID);
            task.setTarget(world.getWm().getCurrentLocalMap().getItemPileById(task.getId()));
            
        }
        
        if(!task.getTarget().hasItem(task.getInfo()))
        {
            task.clearTask();
            path = null;
            return;
        }
        
        if(distanceBetween(task.getTarget()) == 0)
        {
            if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y) != null)
            {

                
                    if (world.getWm().getCurrentLocalMap().getItemPileAt(x, y).getId() == task.getId())
                    {


                        world.getWm().getCurrentLocalMap().getItemPileAt(x, y).takeFrom(inventory, task.getInfo(), world.getWm().getCurrentLocalMap(), -1);
        //                            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                        task.clearTask();
                        path = null;
                        world.moved();
                    }

            }
        }else
        {
            if (path == null)
            {
                calcPath(task.getTarget().getX(), task.getTarget().getY());
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
            task.clearTask();
            path = null;
            world.getWm().getPlayerInventory().getCrafting().clearCraftingTarget();
        }

        if (System.currentTimeMillis() - current >= 250) {
            current = System.currentTimeMillis();
            world.getWm().getPlayerInventory().getCrafting().craft();
            if (world.getWm().getPlayerInventory().getCrafting().finishedCrafting()) {
                task.clearTask();
                path = null;
                world.getCrafting_ui().refreshUI(world.getWm().getCurrentLocalMap());
                world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
            }

            world.moved();

        }
    }
    
    public void playerMoveToTalkLogic(boolean[] k,boolean[] m,Input input,World world)
    {
        if(task.getTarget() == null)
        {
            task.setTarget(world.getWm().getCurrentLocalMap().getPawnById(task.getId()));
        }
        if(distanceBetween(task.getTarget()) <= 1)
        {
            world.getDialogue().switchDialog(6, world);
            world.getDialogue().display();
            task.clearTask();
            path = null;
        }else
        {
            if (path == null)
            {
                calcPath(task.getTarget().getX(), task.getTarget().getY());
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

    

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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

    
    
    
    
}
