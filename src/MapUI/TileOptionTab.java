/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapUI;

import Entity.Furniture;
import Entity.Pawn;
import Entity.Task;
import UI.OptionTab;
import Item.Item;
import Item.ItemPile;
import Res.Res;
import UI.Option;
import World.LocalMap;
import World.Tile;
import java.awt.Rectangle;
import java.util.ArrayList;
import javafx.util.Pair;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class TileOptionTab extends OptionTab
{
    private Tile t;
    
    private Pawn p;
    
    private ItemPile ip;
    
    private Furniture furniture;
    
    private LocalMap lm;
    
    public TileOptionTab(int x, int y, GameContainer container, LocalMap lm, TrueTypeFont font,Res res,Tile t) {
        super(x, y, lm, container,font,res);
        this.t = t;
        p = lm.getPawnAt(t.getX(),t.getY());
        ip = lm.getItemPileAt(t.getX(), t.getY());
        furniture = lm.furnitureAt(t.getX(), t.getY());
        this.lm = lm;
        initOptions();
        initOptionTab();
    }
    

    @Override
    public void runOption(LocalMap lm)
    {
        Task task;
        switch (options.get(hoveringIndex).getActionType())
        {
            case 0:
                lm.getPlayer().walkTo(t.getX(),t.getY());
                break;
            case 1:
                if(p!=null)
                {
                    lm.getWm().getWorld().getAncestor().addText(p.getName()+" is standing there.");
                }
                if(ip==null)
                {
                    lm.getWm().getWorld().getAncestor().addText("There is nothing on the ground.");
                }else
                {
                    if(ip.getItems().size()==1)
                    {
                        lm.getWm().getWorld().getAncestor().addText("There is a "+ip.getItems().get(0).getInGameName()+" on the ground.");
                
                    }else
                    {
                        lm.getWm().getWorld().getAncestor().addText("There is a "+ip.getItems().get(0).getInGameName()+" and other "+(ip.getItems().size()-1)+" items on the ground.");
                
                    }
                }
                break;
            case 2:
                int id = ip.getId();
                int itemIndex = hoveringIndex;
                if (t.isVisit()) {
                    itemIndex--;
                }
                if (!t.isSolid()) {
                    itemIndex--;
                }
                lm.getPlayer().grabItemAt(t.getX(), t.getY(), id, itemIndex,ip.getItems().get(itemIndex).getTrueName(),-1);
                
                break;
            case 3:
                if(furniture.withinDistance(1, lm.getPlayer()))
                {
                    lm.getWorld().spawnFurnitureInventoryWindow(furniture, 100, 100);
                }
                break;
            case 5:
                lm.getPlayer().addTask(new Task(0,0,options.get(hoveringIndex).getId(),0,"talk_to_target"));
                break;
            case 6:
                task = new Task(t.getX(),t.getY(),t.getPlant().getId(),options.get(hoveringIndex).getId(),"harvest_plant");
                task.setTarget(t.getPlant());
                task.setInfo(t.getPlant().getCurrentName());
                lm.getPlayer().addTask(task);
                break;
            case 7:
                task = new Task(t.getX(),t.getY(),0,0,"plant_seed");
                task.setInfo(options.get(hoveringIndex).getInfo());
                lm.getPlayer().addTask(task);
                break;
                
        }
    }

    @Override
    public void initOptions()
    {
        optionBounds = new ArrayList<Rectangle>();
        options = new ArrayList<Option>();
        if(!t.isSolid())
        {
            options.add(new Option("Walk to",0));
        }
        ArrayList<Item> items = lm.getItemsAt(t.getX(), t.getY());
        if(t.getVisit()==2)
        {
            options.add(new Option("Look",1));
            if(items!=null)
            {
                for(Item i:items)
                {
                    options.add(new Option("Take "+i.getInGameName(),2));
                }
            }
        }
        
        if(lm.getPawnAt(t.getX(), t.getY())!=null)
        {
            ArrayList<Pawn> pawns = lm.getPawnsAt(t.getX(),t.getY());
            for(Pawn p:pawns)
            {
                if(!p.isControl())
                {
                    options.add(new Option("Talk to "+p.getName(),5));
                    options.get(options.size()-1).setId(p.getId());
                }
            }
        }
        
        
        if(furniture!=null)
        {
            if(furniture.isFuelable()&&furniture.withinDistance(1, lm.getPlayer()))
            {
                options.add(new Option("Interact",3));
            }
        }
        
        if(t.getPlant()!=null)
        {
            for(Integer i:t.getPlant().getCurrentTools())
            {
                Option o;
                switch(i)
                {
                    case 100:
                        o = new Option("Use hands",6);
                        o.setId(i);
                        options.add(o);
                        break;
                    case 101:
                        o = new Option("Use axe",6);
                        o.setId(i);
                        options.add(o);
                        break;
                    case 102:
                        o = new Option("Use pickaxe",6);
                        o.setId(i);
                        options.add(o);
                        break;
                    case 103:
                        o = new Option("Use hammer",6);
                        o.setId(i);
                        options.add(o);
                        break;
                    case 104:
                        o = new Option("Use hoe",6);
                        o.setId(i);
                        options.add(o);
                        break;
                }
            }
        }
        
        if(!t.isSolid()&&t.getPlant()==null)
        {
            ArrayList<Item> seeds = lm.getWm().getPlayerInventory().getItemsOfType(14);
            if(!seeds.isEmpty())
            {

                for(Item s:seeds)
                {
                    if(s.getProperties().contains(t.getType()))
                    {
                        Option seedOption = new Option("Plant "+s.getTrueName(),7);
                        seedOption.setInfo(s.getTrueName());
                        options.add(seedOption);
                    }
                }
            }
        }
        
        setLongestIndex();
    }
    
}
