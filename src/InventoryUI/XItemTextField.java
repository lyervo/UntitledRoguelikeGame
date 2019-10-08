/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Res.Res;
import World.World;
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
    
    private int state,index;
    private XItemTextFieldConfirmButton confirmButton;
    private XItemTextFieldExitButton exitButton;
    
    private TrueTypeFont font;
    
    
    public XItemTextField(GUIContext container, Font font, int x, int y, int width, int height,Res res)
    {
        super(container, font, x, y, width, height);
        setBackgroundColor(Color.decode("#757161"));
        setBorderColor(Color.decode("#757161"));
        this.font = res.disposableDroidBB;
        confirmButton = new XItemTextFieldConfirmButton(x-(((res.disposableDroidBB.getWidth("Confirm")+20)-width)/2),y+res.disposableDroidBB.getHeight()+5,res.disposableDroidBB.getWidth("Confirm")+20,res.disposableDroidBB.getHeight()+10,"Confirm",Color.black,Color.decode("#757161"),Color.decode("#666355"),res.disposableDroidBB);
        exitButton = new XItemTextFieldExitButton(x-80,y-50,res.disposableDroidBB.getWidth("X")+10,res.disposableDroidBB.getWidth("X")+10,"X",Color.black,Color.decode("#757161"),Color.decode("#666355"),res.disposableDroidBB);
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,InventoryUI inventoryUI)
    {
        
        setText(getText().replaceAll("[^0-9]", ""));
        
        if(getText().length()>=10)
        {
            setText(getText().substring(0, 9));
        }
        
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
                world.getWm().getPlayerInventory().dropItem(world.getWm().getCurrentLocalMap().getPlayer().getX(),world.getWm().getCurrentLocalMap().getPlayer().getY(), index, world.getWm().getCurrentLocalMap(), Integer.parseInt(getText()));
            }else if(state==4)
            {
                world.getWm().getCurrentLocalMap().getItemPileAt(world.getWm().getCurrentLocalMap().getPlayer().getX(), world.getWm().getCurrentLocalMap().getPlayer().getY()).takeFrom(world.getWm().getPlayerInventory(), index, world.getWm().getCurrentLocalMap(), Integer.parseInt(getText()));
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
    
    
    
}