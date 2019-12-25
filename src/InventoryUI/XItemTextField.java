/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Res.Res;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Timot
 */
public class XItemTextField extends TextField
{
    
    private int state;
    private String itemName;
    private XItemTextFieldConfirmButton confirmButton;
    private XItemTextFieldExitButton exitButton;
    
    private TrueTypeFont font;
    
    private Rectangle bounds;
    private boolean hover;
    
    private World world;
    
    public XItemTextField(GUIContext container, Font font, int x, int y, int width, int height,World world,Res res)
    {
        super(container, font, x, y, width, height);
        setBackgroundColor(Color.decode("#757161"));
        setBorderColor(Color.decode("#757161"));
        this.font = res.disposableDroidBB;
        confirmButton = new XItemTextFieldConfirmButton(x-(((res.disposableDroidBB.getWidth("Confirm")+20)-width)/2),y+res.disposableDroidBB.getHeight()+5,res.disposableDroidBB.getWidth("Confirm")+20,res.disposableDroidBB.getHeight()+10,"Confirm",Color.black,Color.decode("#757161"),Color.decode("#666355"),res.disposableDroidBB);
        exitButton = new XItemTextFieldExitButton(x-80,y-50,res.disposableDroidBB.getWidth("X")+10,res.disposableDroidBB.getWidth("X")+10,"X",Color.black,Color.decode("#757161"),Color.decode("#666355"),res.disposableDroidBB);
        bounds = new Rectangle(x,y,width,height);
        this.world = world;
    }
    
    @Override
    public void keyReleased(int key,char c)
    {
        if(key == Input.KEY_ENTER)
        {
            world.getxItemTextField().processInput(world, world.getInventory_ui());
        }
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,InventoryUI inventoryUI)
    {
        
        
        setText(getText().replaceAll("[^0-9]", ""));
        
        if(getText().length()>=10)
        {
            setText(getText().substring(0, 9));
        }
        
        if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
        {
            hover = true;
        }else
        {
            hover = false;
        }
        
//        if(m[10]&&!hover&&world.isxItemTextFieldActive())
//        {
//            world.deactivateXItemTextField();
//            setText("");
//            return;
//        }
        
        exitButton.tick(m, input, world);
        confirmButton.tick(m, input, world);
        
        if(k[Input.KEY_ESCAPE])
        {
            world.deactivateXItemTextField();
            setText("");
        }else if(k[Input.KEY_ENTER])
        {
            processInput(world,inventoryUI);
            world.moved();
        }
        
    }
    
    public void processInput(World world,InventoryUI inventoryUI)
    {
        if(getText()!="")
        {
            if(state<=2||state==5)
            {
                world.getWm().getPlayerInventory().dropItem(world.getWm().getCurrentLocalMap().getPlayer().getX(),world.getWm().getCurrentLocalMap().getPlayer().getY(), itemName, world.getWm().getCurrentLocalMap(), Integer.parseInt(getText()));
            }else if(state==4)
            {
                world.getWm().getCurrentLocalMap().getItemPileAt(world.getWm().getCurrentLocalMap().getPlayer().getX(), world.getWm().getCurrentLocalMap().getPlayer().getY()).takeFrom(world.getWm().getPlayerInventory(), itemName, world.getWm().getCurrentLocalMap(), Integer.parseInt(getText()));
            }
        }
        inventoryUI.refreshInventoryUI(world.getWm().getCurrentLocalMap());
        world.deactivateXItemTextField();
        setText("");
    }
    
    public void renderBackground(Graphics g)
    {
        g.setColor(Color.decode("#4d494d"));
        g.fillRect(x-80, y-50, 160+getWidth(), 120+getHeight());
        g.setColor(Color.white);
        font.drawString(x-((160+getWidth()-font.getWidth("Enter amount"))/2)-10, y-40, "Enter amount");
        exitButton.render(g);
        confirmButton.render(g);
        g.setColor(Color.black);
        g.drawRect(x-80, y-50, 160+getWidth(), 120+getHeight());
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public XItemTextFieldConfirmButton getConfirmButton() {
        return confirmButton;
    }

    public void setConfirmButton(XItemTextFieldConfirmButton confirmButton) {
        this.confirmButton = confirmButton;
    }

    public XItemTextFieldExitButton getExitButton() {
        return exitButton;
    }

    public void setExitButton(XItemTextFieldExitButton exitButton) {
        this.exitButton = exitButton;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }
    
    
    
}