/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentUI;

import Res.Res;

import UI.UIWindow;

import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class EquipmentUIWindow extends UIWindow
{
    
    private EquipmentUI equipmentUI;
    private TrueTypeFont font;
    
    public EquipmentUIWindow(int x, int y, String name, EquipmentUI uiComponent, Res res)
    {
        super(x, y, name, uiComponent, res);
        this.equipmentUI = uiComponent;
        this.font = res.disposableDroidBB40f;
    }
    
    
}
