/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camera;

import Entity.Pawn;
import World.World;
import org.newdawn.slick.GameContainer;

/**
 *
 * @author Timot
 */
public class Camera 
{
    private int width;
    private int height;
    private int w,h;
    
    private int xofs,yofs;
    private int mxofs,myofs;
    
    private Pawn target;
    private int tile_size;
    private double scale;
    
    private double zoom;
    
    public Camera(int w,int h,GameContainer gc)
    {
        this.h = h;
        this.w = w;
        this.width = gc.getWidth();
        
        
        this.height = gc.getHeight()-64;
        
        xofs = 0;
        yofs = 0;
        
        zoom = 1;
        
        this.tile_size = 32;
        
    }
    
    public void zoomIn()
    {
        if(zoom<2)
        {
            zoom+=0.5;
            tile_size=(int)(32*zoom);
        }
    }
    
    public void zoomOut()
    {
        if(zoom>0.5)
        {
            zoom -= 0.5;
            tile_size=(int)(32*zoom);
        }
    }
    
    
    
    
    public void tick(boolean[] k,boolean[] m,World world)
    {
        
        if(world.getZ()==0)
        {
            if(m[16])
            {
                zoomIn();
            }else if(m[17])
            {
                zoomOut();
            }
        }
        
     
        
        if(w<=(width/tile_size))
        {
         
            xofs = (width-(w*tile_size))/2;
        }else
        {
            if(target!=null)
            {
                if(target.getX()<=(width/(tile_size*2)))
                {
                    xofs = 0;
                    mxofs = (int)(xofs);
                }else if(target.getX()<=w-(width/(tile_size*2)))
                {
                    xofs = -(target.getX()-(width/(tile_size*2)))*tile_size;
                    mxofs = (int)(xofs);
                }else
                {
                 
                    xofs = -(w-(width/tile_size))*tile_size;
                    mxofs = (int)(xofs);
                }
            }
            
            
        }
   
        
        if(h<=(height/tile_size))
        {
            yofs = (height-(h*tile_size))/2;
        }else
        {
            if(target!=null)
            {
                if(target.getY()<=(height/(tile_size*2)))
                {
                    yofs = 0;
                    myofs = (int)(yofs);
                }else if(target.getY()<=h-(height/(tile_size*2)))
                {
                    yofs = -(target.getY()-(height/(tile_size*2)))*tile_size;
                    myofs = (int)(yofs);
                }else
                {
                    yofs = -(h-(height/tile_size))*tile_size;
                    myofs = (int)(yofs);
                }
            }
        }
        
        
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getXofs() {
        return xofs;
    }

    public void setXofs(int xofs) {
        this.xofs = xofs;
    }

    public int getYofs() {
        return yofs;
    }

    public void setYofs(int yofs) {
        this.yofs = yofs;
    }

    public Pawn getTarget() {
        return target;
    }

    public void setTarget(Pawn target) {
        this.target = target;
    }

    public int getTile_size() {
        return tile_size;
    }

    public void setTile_size(int tile_size) {
        this.tile_size = tile_size;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getMxofs() {
        return mxofs;
    }

    public void setMxofs(int mxofs) {
        this.mxofs = mxofs;
    }

    public int getMyofs() {
        return myofs;
    }

    public void setMyofs(int myofs) {
        this.myofs = myofs;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    
    
    
    
    
    
    
    
}
