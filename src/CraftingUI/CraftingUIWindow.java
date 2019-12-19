/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import Res.Res;

import UI.UIWindow;

import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class CraftingUIWindow extends UIWindow
{

    private CraftingUI ui;
    private TrueTypeFont font;
    
    public CraftingUIWindow(int x, int y, String name, CraftingUI uiComponent, Res res) {
        super(x, y, name, uiComponent, res);
        this.ui = uiComponent;
        this.font = res.disposableDroidBB;
    }
    

 

    
}
