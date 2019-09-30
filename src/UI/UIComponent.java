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

    private int x,y,xofs,yofs;
    
    protected boolean drag;
    
    protected UIWindow window;
    
    public UIComponent(int x,int y,int xofs,int yofs,UIWindow window)
    {
        this.x = x;
        this.y = y;
        this.xofs = xofs;
        this.yofs = yofs;
        this.drag = false;
        this.window = window;
    }
    
    public abstract void render(Graphics g,Input input,int x,int y);
    
    public abstract void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y,UIWindow window);

    public abstract void dragRender(Graphics g,Input input);
    
    public abstract int getUIWidth();
    
    public abstract int getUIHeight();
    
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

    public int getXofs() {
        return xofs;
    }

    public void setXofs(int xofs) {
        this.xofs = xofs;
    }

    public int getYofs() {
        return yofs;
    }

    public void setYofs(int yofs) {
        this.yofs = yofs;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public UIWindow getWindow() {
        return window;
    }

    public void setWindow(UIWindow window) {
        this.window = window;
    }
    
    
    
    
}
