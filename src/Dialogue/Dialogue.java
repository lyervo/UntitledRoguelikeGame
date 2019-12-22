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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class Dialogue
{
    private int id;
    
    private ArrayList<String> lines;
    private ArrayList<DialogueOption> choices;
    
    private Rectangle bounds;
    
    private boolean display;
    
    private TrueTypeFont font;
    
    
    public Dialogue(JSONObject jsonObj,World world)
    {
        
        long longID = (long)jsonObj.get("id");
        id = (int)longID;
        
        String lineUnparse = (String)jsonObj.get("content");
        
        font = world.getRes().disposableDroidBB;
        lines = new ArrayList<String>();
        parseLines(lineUnparse,world.getContainer().getWidth());
        
        
        int previousHeight = 0;
        
        JSONArray replyArr = (JSONArray)jsonObj.get("replies");
        choices = new ArrayList<DialogueOption>();
        for(int i=replyArr.size()-1;i>=0;i--)
        {
            choices.add(new DialogueOption(i,previousHeight,(JSONObject)replyArr.get(i),world.getContainer(),world.getRes().disposableDroidBB));
            previousHeight += choices.get(choices.size()-1).getHeight();
        }
        
        
    }
    
    public Dialogue(Dialogue dialogue,World world)
    {
        this.id = dialogue.getId();
        this.font = dialogue.getFont();
        this.lines = new ArrayList<String>(dialogue.getLines());
        int previousHeight = 0;
        choices = new ArrayList<DialogueOption>();
        System.out.println("this is a new copy of dialogue "+dialogue.getChoices().size());
        for(int i=0;i<dialogue.getChoices().size();i++)
        {
            if(dialogue.getChoices().get(i).valid(world))
            {
                choices.add(new DialogueOption(i,previousHeight,dialogue.getChoices().get(i),world.getContainer(),world.getRes().disposableDroidBB));
                previousHeight += choices.get(choices.size()-1).getHeight();
            }
        }
    }
    
    public void parseLines(String lineUnparse,int width)
    {
        String[] splitter = lineUnparse.split("\n");
        for(int i=0;i<splitter.length;i++)
        {
            if(font.getWidth(splitter[i])>(width-200))
            {
                String[] subSplitter = splitter[i].split(" ");
                String placeholder = "";
                for(int j=0;j<subSplitter.length;j++)
                {
                    if(font.getWidth(placeholder+" "+subSplitter[j])<width-200)
                    {
                        placeholder += " "+subSplitter[j];
                    }else
                    {
                        lines.add(placeholder);
                        placeholder = subSplitter[j];
                        
                    }
                }
                lines.add(placeholder);
            }else
            {
                lines.add(splitter[i]);
            }
        }
        
    }
    
    public void render(Graphics g)
    {
        g.setFont(font);
        g.setColor(Color.white);
        for(int i=0;i<lines.size();i++)
        {
            g.drawString(lines.get(i), 100, i*font.getHeight()+100);
        }
        for(int i=0;i<choices.size();i++)
        {
            choices.get(i).render(g);
        }
    }

    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        
        
           for(int i=choices.size()-1;i>=0;i--)
            {
                choices.get(i).tick(m, input, world);
            }
            
        
        
    }
    
    public void refresh(World world)
    {
        
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }
    
    
    
    
}
