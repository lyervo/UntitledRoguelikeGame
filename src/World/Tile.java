/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Camera.Camera;
import Entity.Pawn;
import java.awt.Point;
import java.awt.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class Tile {

    private boolean solid;
    protected int x, y;
    protected Image texture;
    protected int type;
    private Wall wall;
    private Rectangle bounds;
    private boolean hover;
    private int visit;
    
    public Tile(int x, int y, int type, Image image,int tile_size) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.texture = image;
        if (type >= 50) {
            this.solid = false;
        } else {
            this.solid = true;
        }
        hover = false;
        visit = 0;
        wall = null;
        bounds = new Rectangle(x * tile_size, y * tile_size, tile_size, tile_size);
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,Camera cam)
    {
        if(withinDisplayArea(cam)&&world.getUiDisplay()==0&&world.getZ()==0)
        {
            if(bounds.contains(new Point(input.getMouseX()-cam.getMxofs(),input.getMouseY()-cam.getMyofs()))&&!world.getWm().getCurrentLocalMap().isHoveringTab())
            {
                hover = true;
            }else
            {
                hover = false;
            }

            if(world.getZ()==0)
            {
                if(hover&&m[10]&&visit!=0)
                {
                    if(!solid)
                    {
                        ((Pawn)world.getWm().getCurrentLocalMap().getPawns().get(0)).calcPath(this.x,this.y);
                    }
                }else if(hover&&m[1]&&visit!=0)
                {
                    world.getWm().getCurrentLocalMap().spawnOptionTab(this);
                }
            }
        }
    }
    
    public void resetVisit()
    {
        if(visit == 2)
        {
            visit = 1;
        }
    }

    
    public void render(Camera cam) {
        
        if(withinDisplayArea(cam))
        {
            if(visit == 2&&hover)
            {
                if (wall != null) {
                    wall.getTexture().draw(x * 32+cam.getXofs(), y * 32+cam.getYofs(),Color.lightGray);
                } else {
                    texture.draw(x * 32+cam.getXofs(), y * 32+cam.getYofs(),Color.lightGray);
                }
            }else if(visit == 1&&hover)
            {
                if (wall != null) {
                    wall.getTexture().draw(x * 32+cam.getXofs(), y * 32+cam.getYofs(),Color.lightGray);
                } else {
                    texture.draw(x * 32+cam.getXofs(), y * 32+cam.getYofs(),Color.lightGray);
                }
            }
            else if(visit == 2)
            {
                if (wall != null) {
                    wall.getTexture().draw(x * 32+cam.getXofs(), y * 32+cam.getYofs());
                } else {
                    texture.draw(x * 32+cam.getXofs(), y * 32+cam.getYofs());
                }
            }else if(visit == 1)
            {
                if (wall != null) {
                    wall.getTexture().draw(x * 32+cam.getXofs(), y * 32+cam.getYofs(),Color.gray);
                } else {
                    texture.draw(x * 32+cam.getXofs(), y * 32+cam.getYofs(),Color.gray);
                }

            }
        }
        
        
        
    }
    
    public boolean withinDisplayArea(Camera cam)
    {
        return (x*32+cam.getXofs()>=0&&x*32+cam.getXofs()<cam.getWidth())&&(y*32+cam.getYofs()>=0&&y*32+cam.getYofs()<cam.getHeight());
    }

    public boolean isSolid() {
        if (wall != null) {
            return wall.isSolid() || solid;
        } else {
            return solid;
        }
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isVisit() {
        return visit==2;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }
    
    public int getVisit()
    {
        return visit;
    }

}
