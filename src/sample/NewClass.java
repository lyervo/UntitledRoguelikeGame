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
        int tick = 0;
        long last = System.currentTimeMillis();
        while(System.currentTimeMillis()-last<1000)
        {
            tick++;
        }
        System.out.println(tick);
        
    }
}
