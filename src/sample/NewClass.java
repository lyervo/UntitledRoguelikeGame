/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author Timot
 */
public class NewClass 
{
    public static void main(String[] args)
    {
        
        int id;     //ambiguous, prone to confusion for people who read it
        int item_id;//the standard way
        int itemId; //the standard way for camel text
        int itemID; //I feel uncomfortable looking at it but it is not wrong
        int ID_item;//this gives me anxiety
        int item1d; //get out of my office
        
        id = 0;
        item_id = 0;
        itemId = 0;
        itemID = 0;
        ID_item = 0;
        item1d = 0;
        int a = id +item_id +itemId +itemID +ID_item+item1d;
        int zack;
    }
}
