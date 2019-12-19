/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public abstract class DescBox
{
    protected String name;
    private ArrayList<String> desc_lines;
    private TrueTypeFont font;
    
    private long desc_hover,last_desc_hover;
    
    private boolean display;
    
    public DescBox(String name,String desc,TrueTypeFont font)
    {
        this.name = name;
        this.font = font;
        desc_lines = new ArrayList<String>();
        
        if (font.getWidth(desc) > 300) 
        {
            String[] desc_arr = desc.split(" ");
            String placeholder = "";
            for (int i = 0; i < desc_arr.length; i++) {
                if (font.getWidth(placeholder + " " + desc_arr[i]) < 300) {
                    placeholder += " " + desc_arr[i];
                } else {
                    desc_lines.add(placeholder);
                    placeholder = desc_arr[i];

                }
            }
            desc_lines.add(placeholder);

        } else {
            desc_lines.add(" "+desc);
        }
        
    }
    
    public void renderDesc(Graphics g,Input input)
    {
        g.setColor(Color.decode("#60a3bc"));
        g.fillRect(input.getMouseX(), input.getMouseY(), 320, 29+(desc_lines.size()*29)+5);
        font.drawString(input.getMouseX()+20, input.getMouseY(), name);
        
        for(int i=0;i<desc_lines.size();i++)
        {
            if(i==0)
            {
                font.drawString(input.getMouseX(), input.getMouseY()+29+(i*29), desc_lines.get(i));
            }else
            {
                font.drawString(input.getMouseX()+10, input.getMouseY()+29+(i*29), desc_lines.get(i));
            }
        }
        
        g.setColor(Color.black);
        g.drawRect(input.getMouseX(), input.getMouseY(), 320, 29+(desc_lines.size()*29)+5);
    }
    
    public void tickDesc(boolean hover)
    {
        if (hover)
        {
            if (desc_hover < 500)
            {
                if (last_desc_hover == 0)
                {
                    last_desc_hover = System.currentTimeMillis();
                } else
                {
                    desc_hover += System.currentTimeMillis() - last_desc_hover;
                    last_desc_hover = System.currentTimeMillis();
                }
            } else
            {
                display = true;

            }
        } else
        {
            display = false;
            desc_hover = 0;
            last_desc_hover = 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDesc_lines() {
        return desc_lines;
    }

    public void setDesc_lines(ArrayList<String> desc_lines) {
        this.desc_lines = desc_lines;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    public long getDesc_hover() {
        return desc_hover;
    }

    public void setDesc_hover(long desc_hover) {
        this.desc_hover = desc_hover;
    }

    public long getLast_desc_hover() {
        return last_desc_hover;
    }

    public void setLast_desc_hover(long last_desc_hover) {
        this.last_desc_hover = last_desc_hover;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    
    
    
    
}
