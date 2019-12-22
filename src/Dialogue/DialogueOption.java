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
import javafx.util.Pair;
import org.json.simple.JSONArray;
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
    private ArrayList<Pair<String,Integer>> condition;
    private boolean valid;

    
    public DialogueOption(int index,int previousHeight,JSONObject jsonObj,GameContainer container,TrueTypeFont font)
    {
        super(index,previousHeight,jsonObj,container,font);
        choice = (int)((long)jsonObj.get("next"));
        
        condition = new ArrayList<Pair<String,Integer>>();
        JSONArray conditionArr = (JSONArray)jsonObj.get("condition");
        if(conditionArr!=null)
        {
            for(int i=0;i<conditionArr.size();i++)
            {
                JSONObject conditionObj = (JSONObject)conditionArr.get(i);
                condition.add(new Pair((String)conditionObj.get("type"),Integer.parseInt((String)conditionObj.get("value"))));
            }
        }
        
        effects = new ArrayList<Effect>();
        JSONArray effectArr = (JSONArray)jsonObj.get("effect");
        if(effectArr!=null)
        {
            for(int i=0;i<effectArr.size();i++)
            {
                JSONObject effectObj = (JSONObject)effectArr.get(i);
                effects.add(new Effect(effectObj));
            }
        }
        
        
    }
    
    public DialogueOption(int index,int previousHeight,DialogueOption dialogueOption,GameContainer container,TrueTypeFont font)
    {
        super(index,previousHeight,dialogueOption.getText(),container,font);
        choice = dialogueOption.getChoice();
        
        
        condition = new ArrayList<Pair<String,Integer>>(dialogueOption.getCondition());
        
        
        effects = new ArrayList<Effect>(dialogueOption.getEffects());
        
        
        
    }

    

    @Override
    public void onClick(boolean[] m, World world)
    {
        for(int i=0;i<effects.size();i++)
        {
            if(effects.get(i).getType().startsWith("spawn_item"))
            {
                String[] splitter = effects.get(i).getType().split("%");
                System.out.println(splitter[1]+" add to inventory");
                world.getWm().getPlayerInventory().addItem(world.getItemLibrary().getItemByTrueName(splitter[1]));
            }
        }
        world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
        world.getDialogue(). switchDialog(choice,world);
        Arrays.fill(m, false);
    }
    
    public boolean valid(World world)
    {
        if(condition.isEmpty())
        {
            return true;
        }
        String[] splitter;
        for(int i=0;i<condition.size();i++)
        {
            if(condition.get(i).getKey().startsWith("no_item"))
            {
                splitter = condition.get(i).getKey().split("%");
                if(world.getWm().getPlayerInventory().getItemCount(splitter[1])>=condition.get(i).getValue())
                {
                    System.out.println(world.getWm().getPlayerInventory().getItemCount(splitter[1])+">="+condition.get(i).getValue());
                    return false;
                }
            }else if(condition.get(i).getKey().startsWith("got_item"))
            {
                splitter = condition.get(i).getKey().split("%");
                if(world.getWm().getPlayerInventory().getItemCount(splitter[1])<condition.get(i).getValue())
                {
                    return false;
                }
            }
        }
        return true;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
    }

    public ArrayList<Pair<String, Integer>> getCondition() {
        return condition;
    }

    public void setCondition(ArrayList<Pair<String, Integer>> condition) {
        this.condition = condition;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    
    
}
