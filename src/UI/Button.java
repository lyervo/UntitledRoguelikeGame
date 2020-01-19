/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Dialogue.DialogueOption;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import org.json.simple.JSONObject;
import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public abstract class Button
{
    protected int x,y;
    protected int width,height;
    
    protected Color border,fill,hoverFill;
    
    
    protected Rectangle bounds;
    
    protected String text;
    
    protected boolean hover;
    
    protected TrueTypeFont font;
    
    protected Image texture;
    
    protected boolean display;
    
    
    //constructor for dialog option
    public Button(int index,int previousHeight,JSONObject jsonObj,GameContainer container,TrueTypeFont font)
    {
        this.x = 0;
        this.y = container.getHeight()-previousHeight-font.getHeight();
        this.height = font.getHeight();
        this.width = container.getWidth();
        this.bounds = new Rectangle(x,y,width,height);
        this.text = (String)jsonObj.get("reply");
        this.hover = false;
        this.fill = Color.green;
        this.border = Color.black;
        this.hoverFill = Color.gray;
        this.font = font;
        this.display = true;
    }
    
    public Button(int index,int previousHeight,String text,GameContainer container,TrueTypeFont font)
    {
        this.x = 0;
        this.y = container.getHeight()-previousHeight-font.getHeight();
        this.height = font.getHeight();
        this.width = container.getWidth();
        this.bounds = new Rectangle(x,y,width,height);
        this.text = text;
        this.hover = false;
        this.fill = Color.green;
        this.border = Color.black;
        this.hoverFill = Color.gray;
        this.font = font;
        this.display = true;
    }
    
    public Button(int x,int y,int width,int height,String text,Color border,Color fill,Color hoverFill,TrueTypeFont font)
    {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.bounds = new Rectangle(x,y,width,height);
        this.text = text;
        this.hover = false;
        this.fill = fill;
        this.border = border;
        this.hoverFill = hoverFill;
        this.font = font;
        this.display = true;
    }
    
    public Button(int x,int y,Image texture)
    {
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.texture = texture;
        bounds = new Rectangle(x,y,width,height);
        this.display = true;
    }
    
    public Button(int x,int y,int xofs,int yofs,Image texture)
    {
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.texture = texture;
        bounds = new Rectangle(x,y,width,height);
        this.display = true;
    }
    
    public abstract void onClick(boolean[] m,World world);
    
    public void tick(boolean[] m,Input input,World world)
    {
        if(display)
        {
            if(texture == null)
            {
                if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
                {
                    hover = true;
                }else
                {
                    hover = false;
                }
            }else
            {

                if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
                {
                    if(texture.getColor(input.getAbsoluteMouseX()-x, input.getMouseY()-y).a == 0)
                    {
                        hover = false;
                    }else
                    {
                        hover = true;
                    }
                }else
                {
                    hover = false;
                }
            }
        
            if(hover&&m[0])
            {
                onClick(m,world);
            }
        }
        
    }
    
    public void tick(boolean[] m,Input input,World world,int x,int y,int z)
    {
        if(display)
        {
            if(texture == null)
            {
                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y))&&world.getZ()==z)
                {
                    hover = true;
                    
                }else
                {
                    hover = false;
                }
            }else
            {

                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y))&&world.getZ()==z)
                {
                    if(texture.getColor(input.getAbsoluteMouseX()-this.x-x, input.getMouseY()-this.y-y).a == 0)
                    {
                        hover = false;
                    }else
                    {
                        hover = true;
                    }
                }else
                {
                    hover = false;
                }
            }
        
            if(hover&&m[10])
            {
                onClick(m,world);
            }
        }
        
    }
    
    public void render(Graphics g)
    {
        if(display)
        {
            if(texture == null)
            {
                if(!hover)
                {
                    g.setColor(fill);
                }else
                {
                    g.setColor(hoverFill);
                }
                g.fillRect(x, y, width, height);
                g.setColor(border);
                g.drawRect(x, y, width,height);
                font.drawString(x+(width-font.getWidth(text))/2, y+(height-font.getHeight())/2, text);

            }else
            {
                if(hover)
                {
                    texture.draw(x, y);
                }else
                {
                    texture.draw(x, y,Color.gray);
                }
            }
        }
    }
    
    public void render(Graphics g,int x,int y)
    {
        if(display)
        {
            if(texture == null)
            {
                if(!hover)
                {
                    g.setColor(fill);
                }else
                {
                    g.setColor(hoverFill);
                }
                g.fillRect(this.x+x, this.y+y, width, height);
                g.setColor(border);
                g.drawRect(this.x+x, this.y+y, width,height);
                font.drawString(this.x+x+(width-font.getWidth(text))/2, this.y+y+(height-font.getHeight())/2, text);

            }else
            {
                if(hover)
                {
                    texture.draw(this.x+x, this.y+y);
                }else
                {
                    texture.draw(this.x+x, this.y+y,Color.gray);
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        bounds.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        bounds.y = y;
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

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public Color getFill() {
        return fill;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }

    public Color getHoverFill() {
        return hoverFill;
    }

    public void setHoverFill(Color hoverFill) {
        this.hoverFill = hoverFill;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    
    
    
}
