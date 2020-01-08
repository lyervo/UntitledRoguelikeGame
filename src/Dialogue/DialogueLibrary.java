/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogue;

import World.World;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Timot
 */
public class DialogueLibrary
{
    
    private HashMap<Integer,Dialogue> dialogs;
    
    public DialogueLibrary(World world)
    {
        dialogs = new HashMap<Integer,Dialogue>();
        initDialogs(world);
    }
    
    public void initDialogs(World world)
    {
       
        
        
        try {
            
            File jsonFile = new File("res/data/dialog/dialog_regular.json");
            Scanner jsonReader;
            jsonReader = new Scanner(jsonFile);
            String jsonString = "";
            while(jsonReader.hasNext())
            {
                jsonString += jsonReader.nextLine();
            }
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(jsonString);
            JSONArray jsonArr = (JSONArray)obj;
            
            for(int i=0;i<jsonArr.size();i++)
            {
                JSONObject jsonObj = (JSONObject)jsonArr.get(i);
                int id = (int)((long)jsonObj.get("id"));
                dialogs.put(id,new Dialogue(jsonObj,world,world.getDialogue()));
            }
            
            
        } catch (FileNotFoundException ex) {
            
        } catch (ParseException e)
        {
           
        }
        
        
            
    }
    
    public Dialogue getDialogById(int id)
    {
        return dialogs.get(id);
    }
    
}
