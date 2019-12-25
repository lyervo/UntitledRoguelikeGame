/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameConsole;

import Item.Item;
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
    
    private int width,height,maxLines;
    
    public GameConsole(World world)
    {
        this.world = world;
        lines = new ArrayList<String>();
        textfield = new ConsoleTextField(world.getContainer(),world.getRes().disposableDroidBB40f,0,world.getContainer().getHeight()-40,world.getContainer().getWidth(),64,this);
        container = world.getContainer();
        font = world.getRes().disposableDroidBB40f;
        height = world.getContainer().getHeight();
        width = world.getContainer().getWidth();
        maxLines = ((height - 40)/font.getHeight())+1;
    }
    
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        
    }
    
    
    public void render(Graphics g)
    {
        Color b = Color.black;
        b.a = 0.5f;
        g.setColor(b);
        g.fillRect(0, 0, width, height-40);
        g.setColor(Color.white);
        g.setFont(font);
        for(int i=0;i<lines.size();i++)
        {
            g.drawString(lines.get(i), 20,height-((i+2)*40));
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
            case "spawnItem":
                spawnItem(token);
                break;
            case "pawnInfo":
                getPawnInfo(token);
                break;
            case "removeEntity":
                break;
            case "quitGame":
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
    
    public void getPawnInfo(String[] token)
    {
        
        if(token.length == 1)
        {
            
        }
        
        if(token.length<3)
        {
            addLine("Invalid number of paramters");
        }
        
        
        
    }
    
    public void spawnItem(String[] token)
    {
        
        if(token.length == 1)
        {
            
            addLine("Parameters for spawnItem, (value) paramters are optional");
            addLine("spawnItem plyaer item_name (amount) - spawns item in player inventory");
            addLine("spawnItem coordinate x_coordinate y_coordinate item_name (amount) - spawns item in a coordinate");
            
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
        String itemName = "";
        Item item;
        switch(token[1])
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
                item = world.getItemLibrary().getItemByTrueName(itemName);
                if(item == null)
                {
                    addLine("Invalid parameter 2, item does not exists");
                    return;
                }
                if(item.isStackable())
                {
                    item.setStack(amount);
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
                item = world.getItemLibrary().getItemByTrueName(itemName);
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
                
                item = world.getItemLibrary().getItemByTrueName(itemName);
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
                
            case "currentPosition":
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
                
                item = world.getItemLibrary().getItemByTrueName(itemName);
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
        
        int tileSize = world.getCam().getTile_size();
        
        int xofs = world.getCam().getXofs();
        int yofs = world.getCam().getYofs();
        
        int x = (mouseX+xofs)/tileSize;
        int y = (mouseY+yofs)/tileSize;
        
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


