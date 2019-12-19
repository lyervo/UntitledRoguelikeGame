/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogue;

import Item.Effect;
import UI.Button;
import World.World;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.JSONObject;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class DialogueOption extends Button
{

    private int choice;
    private ArrayList<Effect> effects;
    private ArrayList<String> condition;
    private boolean valid;

    
    public DialogueOption(int index,int previousHeight,JSONObject jsonObj,GameContainer container,TrueTypeFont font)
    {
        super(index,previousHeight,jsonObj,container,font);
        choice = (int)((long)jsonObj.get("next"));
        ArrayList<String> condition = new ArrayList<String>();
        ArrayList<Effect> effects = new ArrayList<Effect>();
        
    }

    

    @Override
    public void onClick(boolean[] m, World world)
    {
        world.getDialogue(). switchDialog(choice,world);
        Arrays.fill(m, false);
    }
    
    public boolean valid()
    {
        return true;
    }
    
    
    
}
