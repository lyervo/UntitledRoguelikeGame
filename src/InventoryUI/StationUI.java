/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Entity.Furniture;
import Res.Res;
import World.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class StationUI
{
    private String name, desc;
    private int index;
    
    private Image texture;
    
    public StationUI(Furniture furniture,int index,Res res)
    {
        this.name = furniture.getName();
        this.desc = furniture.getDesc();
        this.texture = furniture.getTexture();
        this.index = index;
    }
    
    public void render(Graphics g,Input input)
    {
        texture.draw(16+(35*index),29);
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        
    }
}
