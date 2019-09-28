/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Camera.Camera;
import World.LocalMap;
import World.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;

/**
 *
 * @author Timot
 */
public abstract class Entity
{
    protected int x,y;
    protected Image texture;
    protected SpriteSheet sprites;
    protected boolean autoAnimate;
    protected int id;
    protected int direction;
    protected int animatingIndex;
    protected int animatingTimer;
    
    
    public Entity(int id,int x,int y,Image texture)
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.texture = texture;
        this.direction = 0;
    }
    
    public Entity(int id,int x,int y,SpriteSheet sprites,boolean autoAnimate)
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.sprites = sprites;
        this.autoAnimate = autoAnimate;
        this.direction = 0;
    }
    
    public abstract void tick(boolean[] k,boolean[] m,Input input,World world);
    
    public void render(Camera cam,LocalMap map,boolean animate)
    {
        if(sprites == null)
        {
            texture.draw(x*32+cam.getXofs(),y*32+cam.getYofs());
        }else
        {
            if(autoAnimate)
            {
                if(animate)
                {
                    animatingIndex++;
                    if(animatingIndex>=3)
                    {
                        animatingIndex = 0;
                    }
                    
                    
                   
                }
                sprites.getSprite(animatingIndex, 0).draw(x*32+cam.getXofs(),y*32+cam.getYofs());
                
                
            }
        }
    }
    
    public void move(int x,int y,LocalMap lm)
    {
        this.x += x;
        this.y += y;
        
    }
    
    public void setPos(int x,int y)
    {
        this.x = x;
        this.y = y;
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

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpriteSheet getSprites() {
        return sprites;
    }

    public void setSprites(SpriteSheet sprites) {
        this.sprites = sprites;
    }

    public boolean isAutoAnimate() {
        return autoAnimate;
    }

    public void setAutoAnimate(boolean autoAnimate) {
        this.autoAnimate = autoAnimate;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getAnimatingIndex() {
        return animatingIndex;
    }

    public void setAnimatingIndex(int animatingIndex) {
        this.animatingIndex = animatingIndex;
    }

    public int getAnimatingTimer() {
        return animatingTimer;
    }

    public void setAnimatingTimer(int animatingTimer) {
        this.animatingTimer = animatingTimer;
    }

    

    
}
