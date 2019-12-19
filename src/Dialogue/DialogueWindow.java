/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogue;

import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class DialogueWindow
{
    private ArrayList<String> lines;
    private ArrayList<DialogueOption> choices;
    
    private Rectangle bounds;
    
    private boolean display;
    
    private Dialogue dialogue;
    private DialogueLibrary dialogLibrary;
    
    public DialogueWindow(DialogueLibrary dialogLibrary,World world)
    {
        bounds = new Rectangle(0,0,world.getContainer().getWidth(),world.getContainer().getHeight());
        display = false;
        choices = new ArrayList<DialogueOption>();
        lines = new ArrayList<String>();
        this.dialogLibrary = dialogLibrary;
        dialogue = dialogLibrary.getDialogById(1);
    }
    
    public void render(Graphics g)
    {
        if(dialogue!=null)
        {
            if(display)
            {
                g.setColor(Color.yellow);
                g.setColor(Color.decode("#4d4949"));
                g.fillRect(0, 0, bounds.width, bounds.height);
                dialogue.render(g);
                
            }

            for(int i=choices.size()-1;i>=0;i--)
            {
                choices.get(i).render(g);
            }
        }
    }
    
    
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        if(dialogue!=null&&display)
        {
            
            if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
            {
                world.setZ(100);
                world.setHoveringWindow(true);
            }else
            {
                world.setZ(0);
            }
            dialogue.tick(k, m, input, world);
            
                
            
        }
    }
    
    public void switchDialog(int id,World world)
    {
        if(id<=-1)
        {
            dialogue = null;
            world.setHoveringWindow(false);
            display = false;
            
            return;
        }
        
        dialogue = dialogLibrary.getDialogById(id);
        
       
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    public ArrayList<DialogueOption> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<DialogueOption> choices) {
        this.choices = choices;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
}
