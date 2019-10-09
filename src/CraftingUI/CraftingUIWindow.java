/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import Res.Res;
import UI.UIComponent;
import UI.UIWindow;
import World.World;
import java.awt.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class CraftingUIWindow extends UIWindow
{

    private CraftingUI ui;
    private TrueTypeFont font;
    
    public CraftingUIWindow(int x, int y, String name, CraftingUI uiComponent, Res res) {
        super(x, y, name, uiComponent, res);
        this.ui = uiComponent;
        this.font = res.disposableDroidBB;
    }
    
    @Override
    public void render(Graphics g,Input input)
    {
        if(display&&!drag)
        {
            g.setColor(Color.blue);
            g.drawRect(x, y-32, dragBounds.width, dragBounds.height);
            g.setColor(Color.yellow);
            g.drawRect(x, y, width, height);
            ui.getTexture().draw(x,y);
            ui.render(g, input, x, y);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(name, x+32, y-37);
            closeWindowButton.render(g,x, y);
            
        }
    }
    
    @Override
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

            closeWindowButton.tick(m, input, world,x,y);
            
            if((dragHover||hover)&&m[10]&&world.getZ()==z)
            {
                world.moveWindowToTop(this);
            }
            
            if(world.getZ()<=z&&(hover||dragHover))
            {
                world.setZ(z);
                world.setHoveringWindow(true);
            }
            

            if(dragHover&&input.isMouseButtonDown(0)&&!drag&&!world.isDrag()&&world.getZ()==z)
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
                ui.tick(k, m, input, world,x,y,this);
            }
        }
    }

    @Override
    public void itemUICheckDrop(boolean[] k, boolean[] m, Input input, World world)
    {
       
    }
    
    @Override
    public void dragRender(Graphics g,Input input)
    {
        if(display)
        {
            g.setColor(Color.blue);
            g.drawRect(input.getMouseX()-xofs, input.getMouseY()-yofs, dragBounds.width, dragBounds.height);
            g.setColor(Color.yellow);
            g.drawRect(input.getMouseX()-xofs, input.getMouseY()-yofs+32, width, height);
            ui.getTexture().draw(input.getMouseX()-xofs, input.getMouseY()-yofs+32);
            ui.render(g, input, input.getMouseX()-xofs, input.getMouseY()-yofs+32);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(name, input.getMouseX()-xofs+32, input.getMouseY()-yofs-5);
            closeWindowButton.render(g,input.getMouseX()-xofs, input.getMouseY()-yofs+32);
        }
    }
    
}
