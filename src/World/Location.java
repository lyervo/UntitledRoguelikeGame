/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Camera.Camera;
import Res.Res;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class Location
{
    private int x,y,type;
    private boolean solid;
    private Image texture;
    
    private int lastVisit;
    
    private Res res;
    
    private boolean hasMap;

    private LocalMap lm;
    
    private World world;
    
    
    
    public Location(int x,int y,int type,Image texture,Res res,World world)
    {
        
        this.res = res;
        lastVisit = 0;
        if(type>=50)
        {
            solid = false;
        }else
        {
            solid = true;
        }
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.hasMap = false;
        this.world = world;
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,Camera cam)
    {
        if(hasMap)
        {
            
            lm.tick(k, m,input, world);
        }else
        {
           
            lm = new LocalMap(1,70,40,res,world,world.getContainer(),world.getItemLibrary());
            hasMap = true;
        }
    }
    
    public void render(Camera wCam,Camera cam)
    {
        
        texture.draw(x*32,y*32);
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public int getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(int lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public boolean isHasMap() {
        return hasMap;
    }

    public void setHasMap(boolean hasMap) {
        this.hasMap = hasMap;
    }
    
    public LocalMap getLocalMap()
    {
        return this.lm;
    }
    
    
    
}
