/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import java.util.Comparator;

/**
 *
 * @author Timot
 */
public class TradeItemNameComparator implements Comparator
{

    @Override
    public int compare(Object o1, Object o2)
    {
        return ((TradeItem)o1).getItem().getInGameName().compareToIgnoreCase(((TradeItem)o2).getItem().getInGameName());
    }
    
    
}


