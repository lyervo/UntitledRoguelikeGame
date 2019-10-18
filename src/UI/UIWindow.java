/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import FurnitureUI.FurnitureUIWindow;
import Res.Res;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public abstract class UIWindow 
{
    
    protected int x,y,z,width,height;
    protected String name;
    
    protected Rectangle bounds,dragBounds;
    
    private UIComponent uiComponent;

    protected boolean drag,pin,hover,dragHover,display;
    
    protected int xofs,yofs;
    
    
    
    protected ArrayList<Button> windowButtons;
    
    private TrueTypeFont font;
   
    
    public UIWindow(int x,int y,String name,UIComponent uiComponent,Res res)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = uiComponent.getUIWidth();
        this.height = uiComponent.getUIHeight();
        this.uiComponent = uiComponent;
        
        this.bounds = new Rectangle(x,y,width,height);
        this.dragBounds = new Rectangle(x,y-32,uiComponent.getUIWidth(),32);
        
        this.drag = false;
        this.hover = false;
        this.dragHover = false;
        this.z = -1;
        this.display = false;
        
        this.windowButtons = new ArrayList<Button>();
        this.windowButtons.add(new CloseWindowButton((width-32),-32,res.close_icon,this));
        this.windowButtons.add(new PinWindowButton((width-64),-32,this,res.pin_icon,res.unpin_icon));

        this.pin = false;
        this.font = res.disposableDroidBB40f;
        
    }
    
    
    public void itemUICheckDrop(boolean[] k,boolean[] m,Input input,World world)
    {
        uiComponent.checkDrop(k, m, input, world);
    }
    
    public void render(Graphics g,Input input)
    {
        if(display&&!drag)
        {
            g.setColor(Color.blue);
            g.drawRect(x, y-32, dragBounds.width, dragBounds.height);
            g.setColor(Color.yellow);
            g.drawRect(x, y, width, height);
            uiComponent.render(g, input, x, y);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(name, x+32, y-37);
            for(Button b:windowButtons)
            {
                b.render(g, x, y);
            }
            
        }
    }
    
    public void renderDesc(Graphics g,Input input)
    {
        uiComponent.renderDesc(g, input);
    }
    
    public void dragRender(Graphics g,Input input)
    {
        if(display)
        {
            g.setColor(Color.blue);
            g.drawRect(input.getMouseX()-xofs, input.getMouseY()-yofs, dragBounds.width, dragBounds.height);
            g.setColor(Color.yellow);
            g.drawRect(input.getMouseX()-xofs, input.getMouseY()-yofs+32, width, height);
            uiComponent.render(g, input, input.getMouseX()-xofs, input.getMouseY()-yofs+32);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(name, input.getMouseX()-xofs+32, input.getMouseY()-yofs-5);
            for(Button b: windowButtons)
            {
                b.render(g,input.getMouseX()-xofs,input.getMouseY()-yofs+32);
            }
        }
       
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
 
        if(display)
        {
            if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
            {

                hover = true;
                
            }else
            {
                hover = false;
            }
            
            


            if(dragBounds.contains(new Point(input.getMouseX(),input.getMouseY())))
            {
                dragHover = true;

            }else
            {
                dragHover = false;
            }
            

            
            
            if((dragHover||hover)&&m[10]&&world.getZ()==z)
            {
                world.moveWindowToTop(this);
            }
            
            if(world.getZ()<=z&&(hover||dragHover))
            {
                world.setZ(z);
                world.setHoveringWindow(true);
            }

            if(!drag)
            {
                for(Button b:windowButtons)
                {
                    b.tick(m, input, world,x,y,z);
                }
            }
            
            
            

            if(dragHover&&input.isMouseButtonDown(0)&&!drag&&!world.isDrag()&&world.getZ()==z&&!pin)
            {
                xofs = input.getMouseX()-dragBounds.x;
                yofs = input.getMouseY()-dragBounds.y;
                drag = true;
                world.setDrag(true);
            }

            if(m[0]&&drag&&world.isDrag())
            {

                drag = false;
                setWindowLocation(input,world.getContainer());
                world.setDrag(false);
            }

            if(!drag)
            {
                uiComponent.tick(k, m, input, world,x,y,this);
            }
        }
        
        
    }
    
    
    public void setWindowLocation(Input input,GameContainer container)
    {
        if(input.getMouseX()-xofs<0)
        {
            this.x = 0;
            this.bounds.x = 0;
            this.dragBounds.x = 0;
        }else if(input.getMouseX()-xofs>container.getWidth()-width)
        {
            this.x = container.getWidth()-width;
            this.bounds.x = container.getWidth()-width;
            this.dragBounds.x = container.getWidth()-width;
        }else
        {
            this.x = input.getMouseX()-xofs;
            this.bounds.x = input.getMouseX()-xofs;
            this.dragBounds.x = input.getMouseX()-xofs;
        }
        
        if(input.getMouseY()-yofs<0)
        {
            this.y = 32;
            this.bounds.y = 32;
            this.dragBounds.y = 0;
        }else if(input.getMouseY()-yofs>container.getHeight()-64-height)
        {
            this.y = container.getHeight()-height-64;
            this.bounds.y = container.getHeight()-height-64;
            this.dragBounds.y = container.getHeight()-height-32-64;
        }else
        {
            this.y = input.getMouseY()-yofs+32;
            this.bounds.y = input.getMouseY()-yofs+32;
            this.dragBounds.y = input.getMouseY()-yofs;
        }
        
        uiComponent.setX(x);
        uiComponent.setY(y);
            
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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getDragBounds() {
        return dragBounds;
    }

    public void setDragBounds(Rectangle dragBounds) {
        this.dragBounds = dragBounds;
    }

    public UIComponent getUiComponent() {
        return uiComponent;
    }

    public void setUiComponent(UIComponent uiComponent) {
        this.uiComponent = uiComponent;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isDragHover() {
        return dragHover;
    }

    public void setDragHover(boolean dragHover) {
        this.dragHover = dragHover;
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

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    public void setDisplay()
    {
        this.display = !display;
        this.drag = false;
        this.dragHover = false;
        for(Button b:windowButtons)
        {
            b.setHover(false);
        }
    }
    
    public void setPin()
    {
        this.pin = !pin;
        this.drag = false;
        this.dragHover = false;
        for(Button b:windowButtons)
        {
            b.setHover(false);
        }
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public ArrayList<Button> getWindowButtons() {
        return windowButtons;
    }

    public void setWindowButtons(ArrayList<Button> windowButtons) {
        this.windowButtons = windowButtons;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }
    
    
    
    
}
