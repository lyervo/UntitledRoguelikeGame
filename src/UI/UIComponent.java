/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import World.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public abstract class UIComponent
{

    protected int x,y;
    
    protected boolean drag;
    
    public UIComponent(int x,int y)
    {
        this.x = x;
        this.y = y;
        this.drag = false;
    }
    
    public abstract void checkDrop(boolean[] k,boolean[] m,Input input,World world);
    
    public abstract void render(Graphics g,Input input,int x,int y);
    
    public abstract void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y,UIWindow window);

    public abstract void dragRender(Graphics g,Input input);
    
    public abstract int getUIWidth();
    
    public abstract int getUIHeight();
    
    public abstract void renderDesc(Graphics g,Input input);
    
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

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    
    
    
}
