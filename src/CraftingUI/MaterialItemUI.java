/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import InventoryUI.ItemUI;
import Item.Item;
import Res.Res;
import World.World;
import java.awt.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class MaterialItemUI extends ItemUI
{

    private MaterialUI ui;
    
    public MaterialItemUI(Item item, int index, Res res,int column,MaterialUI ui)
    {
        super(item, index, res);
 
    }
    
    @Override
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y)
    {
        if(!world.isDrag())
        {


        }
    }
    
    @Override
    public void render(Graphics g,Input input,int x,int y)
    {
        if(!drag)
        {
           
            item.getTexture().draw(x,y,48,48);
            
        }       
    }


    
}
