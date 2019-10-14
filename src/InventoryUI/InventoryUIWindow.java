/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Res.Res;
import UI.UIWindow;
import World.World;
import java.awt.Rectangle;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class InventoryUIWindow extends UIWindow
{
    
    private InventoryUI inventoryUI;
    private TrueTypeFont font;
    
    private InventoryWindowSizeButton windowSizeButton;
    
    private boolean mode;
    
    public InventoryUIWindow(int x, int y,String name,InventoryUI inventoryUI,TrueTypeFont font,Res res) 
    {
        super(x, y, name, inventoryUI,res);
        this.inventoryUI = inventoryUI;
        this.font = font;
        this.mode = false;
        windowButtons.add(new InventoryWindowSizeButton((width-96),-32,res.minimize,res.maximize));
    }
    
    
    
    
    
    

    public void setMode(World world)
    {
        mode = !mode;
        inventoryUI.setMode(world);
        this.width = inventoryUI.getUIWidth();
        this.height = inventoryUI.getUIHeight();
        
        this.bounds = new Rectangle(x,y,width,height);
        this.dragBounds = new Rectangle(x,y-32,inventoryUI.getUIWidth(),32);
    }

    public InventoryUI getInventoryUI() {
        return inventoryUI;
    }

    public void setInventoryUI(InventoryUI inventoryUI) {
        this.inventoryUI = inventoryUI;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }
    
    
    
}
