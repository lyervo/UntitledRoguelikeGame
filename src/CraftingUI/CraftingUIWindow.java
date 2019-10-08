/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import Res.Res;
import UI.UIComponent;
import UI.UIWindow;
import World.World;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class CraftingUIWindow extends UIWindow
{

    private CraftingUI ui;
    
    public CraftingUIWindow(int x, int y, String name, UIComponent uiComponent, Res res) {
        super(x, y, name, uiComponent, res);
    }

    @Override
    public void itemUICheckDrop(boolean[] k, boolean[] m, Input input, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
