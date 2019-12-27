/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameConsole;

import Entity.Entity;
import Entity.Pawn;
import Entity.Task;
import Item.Item;
import Item.ItemPile;
import World.World;
import java.util.ArrayList;
import javafx.util.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 * 
 * PC M@ster race?
 * 
 */
public class GameConsole
{
    private World world;
    private ArrayList<String> lines;
    private ConsoleTextField textfield;
    private GameContainer container;
    private TrueTypeFont font;
    
    private int width,height,maxLines,scrollIndex,maxDisplayLines;
    
    public GameConsole(World world)
    {
        this.world = world;
        lines = new ArrayList<String>();
        textfield = new ConsoleTextField(world.getContainer(),world.getRes().disposableDroidBB40f,0,world.getContainer().getHeight()-40,world.getContainer().getWidth(),64,this);
        container = world.getContainer();
        font = world.getRes().disposableDroidBB40f;
        height = world.getContainer().getHeight();
        width = world.getContainer().getWidth();
        maxLines = 100;
        maxDisplayLines = (height-40)/font.getHeight();
    }
    
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        if (m[16])
        {

            scrollUp();
        } else if (m[17])
        {

            scrollDown();
        }
    }
    
    public void scrollDown()
    {
        if(lines.size()-scrollIndex<=maxDisplayLines)
        {
            return;
        }
        scrollIndex++;
           
    }
    
    public void scrollUp()
    {
     
        if(lines.size()<=maxDisplayLines||scrollIndex==0)
        {
            return;
        }
        
        
        scrollIndex--;
            
    }
    
    
    public void render(Graphics g)
    {
        Color b = Color.black;
        b.a = 0.5f;
        g.setColor(b);
        g.fillRect(0, 0, width, height-40);
        g.setColor(Color.white);
        g.setFont(font);
        
        if(!lines.isEmpty())
        {
            int loopI;
            if(lines.size()<maxDisplayLines)
            {
                loopI = lines.size();
            }else
            {
                loopI = scrollIndex+maxDisplayLines;
            }
            for(int i=scrollIndex;i<loopI;i++)
            {
                g.drawString(lines.get(i), 20,height-((i+2)*40-(scrollIndex*font.getHeight())));
            }
        }
        g.setColor(Color.decode("#757161"));
        textfield.render(container, g);
        
    }
    
    public void startState()
    {
        textfield.setFocus(true);
    }
    
    
    public void runCommand(String commandLine)
    {
        addLine(commandLine);
        String[] token = commandLine.split(" ");
        switch(token[0])
        {
            case "help":
                break;
            case "clear":
                lines.clear();
                break;
            case "spawnItem":
                spawnItem(token);
                break;
            case "entityInfo":
                getEntityInfo(token);
                break;
            case "giveTask":
                giveTask(token);
                break;
            case "removeEntity":
                break;
            case "quit":
                System.exit(0);
                break;
            default:
                addLine("Unknown command line");
                break;
        }
        
    }
    
    
    public void addLine(String line)
    {
        lines.add(0,line);
        if(lines.size()>maxLines)
        {
            lines.remove(lines.size()-1);
        }
    }
    
    public void giveTask(String[] token)
    {
        if(token.length<4)
        {
            addLine("Invalid number of parameters");
            return;
        }
       
        Task task = generateTask(token);
        
        switch(token[1])
        {
            case "mouse":
                Pair<Integer,Integer> coords = getCoordinatesByMousePos();
                ArrayList<Pawn> ps = world.getWm().getCurrentLocalMap().getPawnsAt(coords.getKey(), coords.getValue());
                
                for(Pawn p:ps)
                {
                    p.setTask(task);
                }
                break;
            default:
                addLine("Invalid parameter 1");
                return;
        }
        
        
    }
    
    public Task generateTask(String[] token)
    {
        Task task = new Task(0,0,0,0,"nothing");
        int id = 0;
        int index = 0;
        Entity target;
        if(token[2].equals("nothing"))
        {
            return task;
        }
        else if(token[2].equals("follow"))
        {
            if (token[3].equals("player"))
            {
                id = world.getWm().getCurrentLocalMap().getPlayer().getId();
                target = world.getWm().getCurrentLocalMap().getPawnById(id);
                if (target == null)
                {
                    addLine("Invalid parameter 3, no entity with id " + id + " exists");
                    return null;
                }
                task = new Task(target.getX(), target.getY(), id, 0, "follow_target");
                task.setTarget(target);
            } else
            {
                try
                {
                    id = Integer.parseInt(token[3]);
                    target = world.getWm().getCurrentLocalMap().getPawnById(id);
                    if(target == null)
                    {
                        addLine("Invalid parameter 3, no entity with id "+id+" exists");
                        return null;
                    }
                    task = new Task(target.getX(),target.getY(),id,0,"follow_target");
                    task.setTarget(target);
                } catch (NumberFormatException e)
                {
                    addLine("Invalid parameter 3");
                    return null;
                }
            }
            
            
        }else if(token[2].equals("grab"))
        {
            try
            {
                id = Integer.parseInt(token[3]);
                target = world.getWm().getCurrentLocalMap().getItemPileById(id);
                if (target == null)
                {
                    addLine("Invalid parameter 3, no entity with id " + id + " exists");
                    return null;
                }
                task = new Task(target.getX(), target.getY(), id, 0, "grab_item");
                task.setTarget(target);
            } catch (NumberFormatException e)
            {
                addLine("Invalid parameter 3");
                return null;
            }
            target = world.getWm().getCurrentLocalMap().getItemPileById(id);
            task.setTarget(target);
            String info = "";
            for(int i=4;i<token.length;i++)
            {
                if(i==4)
                {
                    info = token[i];
                }else
                {
                    info+=" "+token[i];
                }
            }
            task.setInfo(info);
        }else if(token[2].equals("search"))
        {
            String searchName = "";
            int searchAmount = 0;
            for(int i=3;i<token.length;i++)
            {
                if(i==token.length-1)
                {
                    try
                    {
                        searchAmount = Integer.parseInt(token[i]);
                    }catch(NumberFormatException e)
                    {
                        if(i==3)
                        {
                            searchName = token[i];
                        }else
                        {
                            searchName += " "+token[i];
                        }
                        searchAmount = 1;
                    }
                }else
                {
                    if(i==3)
                    {
                        searchName = token[i];
                    }else
                    {
                        searchName += " "+token[i];
                    }
                }
            }
                    
            task.setIndex(searchAmount);
            task = new Task(0,0,0,0,"search_item");
            task.setInfo(searchName);
        }else if(token[2].equals("craft"))
        {
            task = new Task(0,0,0,0,"craft");
            String recipeName = "";
            for(int i=3;i<token.length;i++)
            {
                if(i==3)
                {
                    recipeName = token[i];
                }else
                {
                    recipeName += " "+token[i];
                }
            }
            task.setInfo(recipeName);
        }
        
        return task;
    }
    
    public void getEntityInfo(String[] token)
    {
        
        if(token.length == 1)
        {
            return;
        }
        
        if(token.length<2)
        {
            addLine("Invalid number of paramters");
            return;
        }
        
        switch(token[1])
        {
            case "mouse":
                
                Pair<Integer,Integer> coords = getCoordinatesByMousePos();
                ArrayList<Pawn> pawns = world.getWm().getCurrentLocalMap().getPawnsAt(coords.getKey(), coords.getValue());
                ItemPile ip = world.getWm().getCurrentLocalMap().getItemPileAt(coords.getKey(), coords.getValue());
                if(pawns.isEmpty()&&ip == null)
                {
                    addLine("No entity at "+coords.getKey()+","+coords.getValue());
                    return;
                }
                if(ip!=null)
                {
                    addLine("id"+ip.getId()+"   name:Item Pile");
                    addLine("| Items:");
                    for (Item i : ip.getItems())
                    {
                        addLine(getItemInfo(i));
                    }
                }
                for(Pawn p:pawns)
                {
                    addLine("id"+p.getId()+"   name:"+p.getName());
                    
                    addLine("Current task:  "+p.getTask().getType()+"  target:"+p.getTask().getId()+"   index:"+p.getTask().getIndex()+"   info:"+p.getTask().getInfo());
                    addLine("| Items:");
                    if(p.isControl())
                    {
                        if(world.getWm().getPlayerInventory().getItems().isEmpty())
                        {
                            addLine("| No items");
                        }
                        for(Item i:world.getWm().getPlayerInventory().getItems())
                        {
                            
                            addLine(getItemInfo(i));
                        }
                        
                    }else
                    {
                        if(p.getInventory().getItems().isEmpty())
                        {
                            addLine("| No items");
                        }   
                        for(Item i:p.getInventory().getItems())
                        {
                            addLine(getItemInfo(i));
                        }
                    }
                }
                
                break;
            default:
                addLine("Invalid parameter 1");
                break;
        }
        
        
        
    }
    
    public String getItemInfo(Item i)
    {
        String info = "| "+i.getTrueName()+" stacks:"+i.getStack();
        if(i.getDurability()>1)
        {
            info += " durability/freshness:"+i.getDurability();
        }
        return info;
    }
    
    public void spawnItem(String[] token)
    {
        
        if(token.length == 1)
        {
            
            
            
            return;
        }
        
        
        if(token.length<3)
        {
            addLine("Invalid number of parameters");
            return;
        }
        int amount = 0;
        int x = 0;
        int y = 0;
        int id = 0;
        String itemName = "";
        Item item;
        switch(token[1].toLowerCase())
        {
            case "player":
                if(token.length<3)
                {
                    addLine("Invalid number of parameters");
                    return;
                }
                
                for(int i=2;i<token.length;i++)
                {
                    if(i == token.length-1)
                    {
                        try
                        {
                            amount = Integer.parseInt(token[i]);
                        }catch(NumberFormatException e)
                        {
                            amount = 1;
                            itemName += " "+token[i];
                        }
                    }else
                    {
                        if(i == 2)
                        {
                            itemName = token[i];
                        }else
                        {
                            itemName += " "+token[i];
                        }
                    }
                }
                item = world.getItemLibrary().getItemByName(itemName);
                if(item == null)
                {
                    addLine("Invalid parameter 2, item does not exists");
                    return;
                }
                if(item.isStackable())
                {
                    item.setStack(amount);
                    world.getWm().getPlayerInventory().addItem(item);
                }else
                {
                    for(int i=0;i<amount;i++)
                    {
                        world.getWm().getPlayerInventory().addItem(item);
                    }
                }
                break;
                
            case "coordinate":
                if(token.length<5)
                {
                    addLine("Invalid number of parameters");
                    return;
                }else
                {
                    try
                    {
                        x = Integer.parseInt(token[2]);
                        y = Integer.parseInt(token[3]);
                    }catch(NumberFormatException e)
                    {
                        addLine("Invalid parameter 2 or/and 3");
                    }
                }
                
                for(int i=4;i<token.length;i++)
                {
                    if(i == token.length-1)
                    {
                        try
                        {
                            amount = Integer.parseInt(token[i]);
                        }catch(NumberFormatException e)
                        {
                            amount = 1;
                            itemName += " "+token[i];
                        }
                    }else
                    {
                        if(i == 4)
                        {
                            itemName = token[i];
                        }else
                        {
                            itemName += " "+token[i];
                        }
                    }
                }
                item = world.getItemLibrary().getItemByName(itemName);
                if(item == null)
                {
                    addLine("Invalid parameter 4, item does not exists");
                    return;
                }
                if(item.isStackable())
                {
                    item.setStack(amount);
                    world.getWm().getCurrentLocalMap().spawnItemAt(x, y, item);
                }else
                {
                    for(int i=0;i<amount;i++)
                    {
                        world.getWm().getCurrentLocalMap().spawnItemAt(x, y, item);
                    }
                }
                break;
                
            case "mouse":
                Pair<Integer,Integer> coords = getCoordinatesByMousePos();
                for(int i=2;i<token.length;i++)
                {
                    if(i == token.length-1)
                    {
                        try
                        {
                            amount = Integer.parseInt(token[i]);
                        }catch(NumberFormatException e)
                        {
                            amount = 1;
                            if(i == 2)
                            {
                                itemName = token[i];
                            }else
                            {
                                itemName += " "+token[i];
                            }
                        }
                    }else
                    {
                        if(i == 2)
                        {
                            itemName = token[i];
                        }else
                        {
                            itemName += " "+token[i];
                        }
                    }
                }
                
                item = world.getItemLibrary().getItemByName(itemName);
                if(item == null)
                {
                    addLine(itemName+" "+itemName.length());
                    addLine("Invalid parameter 2, item does not exists");
                    return;
                }
                if(item.isStackable())
                {
                    item.setStack(amount);
                    world.getWm().getCurrentLocalMap().spawnItemAt(coords.getKey(), coords.getValue(), item);
                }else
                {
                    for(int i=0;i<amount;i++)
                    {
                        world.getWm().getCurrentLocalMap().spawnItemAt(coords.getKey(), coords.getValue(), item);
                    }
                }
                break;
                
            case "here":
                x = world.getWm().getPlayer().getX();
                y = world.getWm().getPlayer().getY();
                
                for(int i=2;i<token.length;i++)
                {
                    if(i == token.length-1)
                    {
                        try
                        {
                            amount = Integer.parseInt(token[i]);
                        }catch(NumberFormatException e)
                        {
                            amount = 1;
                            if(i == 2)
                            {
                                itemName = token[i];
                            }else
                            {
                                itemName += " "+token[i];
                            }
                        }
                    }else
                    {
                        if(i == 2)
                        {
                            itemName = token[i];
                        }else
                        {
                            itemName += " "+token[i];
                        }
                    }
                }
                
                item = world.getItemLibrary().getItemByName(itemName);
                if(item == null)
                {
                    addLine(itemName+" "+itemName.length());
                    addLine("Invalid parameter 2, item does not exists");
                    return;
                }
                if(item.isStackable())
                {
                    item.setStack(amount);
                    world.getWm().getCurrentLocalMap().spawnItemAt(x, y, item);
                }else
                {
                    for(int i=0;i<amount;i++)
                    {
                        world.getWm().getCurrentLocalMap().spawnItemAt(x, y, item);
                    }
                }
                break;
                
            case "entity":
                if(token.length<3)
                {
                    addLine("Invalid number of parameters");
                    return;
                }
                
                try
                {
                    id = Integer.parseInt(token[2]);
                }catch(NumberFormatException e)
                {
                    addLine("Invalid parameter 2");
                    return;
                }
                
                Pawn p = world.getWm().getCurrentLocalMap().getPawnById(id);
                
                if(p == null)
                {
                    addLine("Invalid parameter 2, entity with id "+id+" does not exists");
                    return;
                }
                
                
                for(int i=3;i<token.length;i++)
                {
                    if(i == token.length-1)
                    {
                        try
                        {
                            amount = Integer.parseInt(token[i]);
                        }catch(NumberFormatException e)
                        {
                            amount = 1;
                            if(i==3)
                            {
                                itemName = token[i];
                            }else
                            {
                                itemName += " "+token[i];
                            }
                        }
                    }else
                    {
                        if(i == 3)
                        {
                            itemName = token[i];
                        }else
                        {
                            itemName += " "+token[i];
                        }
                    }
                }
                item = world.getItemLibrary().getItemByName(itemName);
                if(item == null)
                {
                    addLine("Invalid parameter 3, item does not exists");
                    return;
                }
                if(item.isStackable())
                {
                    item.setStack(amount);
                    p.getInventory().addItem(item);
                }else
                {
                    for(int i=0;i<amount;i++)
                    {
                        p.getInventory().addItem(item);
                    }
                }
                break;
                
            default:
                addLine("Invalid parameter 1");
                return;
                
        }
        
    }
    
    public Pair<Integer,Integer> getCoordinatesByMousePos()
    {
        Pair<Integer,Integer> result;
        
        
        int mouseX = world.getInput().getMouseX();
        int mouseY = world.getInput().getMouseY();
        
        int tileSize = (int)(world.getWm().getCurrentLocalMap().getCam().getTile_size());

        
        int xofs = world.getWm().getCurrentLocalMap().getCam().getXofs();
        int yofs = world.getWm().getCurrentLocalMap().getCam().getYofs();
        
        int x = (mouseX-xofs)/tileSize;
        int y = (mouseY-yofs)/tileSize;
        
        
        
        result = new Pair(x,y);
        
        
        return result;
        
    }

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public ArrayList<String> getLines()
    {
        return lines;
    }

    public void setLines(ArrayList<String> lines)
    {
        this.lines = lines;
    }

    public ConsoleTextField getTextfield()
    {
        return textfield;
    }

    public void setTextfield(ConsoleTextField textfield)
    {
        this.textfield = textfield;
    }
    
    
    
    
    
}


