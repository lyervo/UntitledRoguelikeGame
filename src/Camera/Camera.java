/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camera;

import Entity.Pawn;
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
    
    public Camera(int w,int h,GameContainer gc)
    {
        this.h = h;
        this.w = w;
        this.width = gc.getWidth();
        
        
        this.height = gc.getHeight()-64;
        
        xofs = 0;
        yofs = 0;
        
        this.tile_size = (int)(32);
        
    }
    
    
    
    
    
    
    public void tick()
    {
        
        if(w<=(width/32))
        {
            xofs = (width-(w*32))/2;
        }else
        {
            if(target!=null)
            {
                if(target.getX()<=(width/64))
                {
                    xofs = 0;
                    mxofs = (int)(xofs);
                }else if(target.getX()>(width/64)&&target.getX()<=w-(width/64))
                {
                    xofs = -(target.getX()-(width/64))*32;
                    mxofs = (int)(xofs);
                }else
                {
                    xofs = -(w-(width/32))*32;
                    mxofs = (int)(xofs);
                }
            }
            
            
        }
        
        if(h<=(height/32))
        {
            yofs = (height-(h*32))/2;
        }else
        {
            if(target!=null)
            {
                if(target.getY()<=(height/64))
                {
                    yofs = 0;
                    myofs = (int)(yofs);
                }else if(target.getY()>(height/64)&&target.getY()<=h-(height/64))
                {
                    yofs = -(target.getY()-(height/64))*32;
                    myofs = (int)(yofs);
                }else
                {
                    yofs = -(h-(height/32))*32;
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
    
    
    
    
    
    
    
    
}
