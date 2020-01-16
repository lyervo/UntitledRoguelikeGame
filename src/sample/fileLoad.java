/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Timot
 */
public class fileLoad
{
    public static void main(String[]args)
    {
        
        
        
        try (Stream<Path> walk = Files.walk(Paths.get("res/texture"))) {

		List<String> result = walk.filter(Files::isDirectory)
				.map(x -> x.toString()).collect(Collectors.toList());

		for(String s:result)
                {
                    getFiles(s);
                }
                
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    
    public static void getFiles(String folderName)
    {
        System.out.println(folderName);
        try (Stream<Path> walk = Files.walk(Paths.get(folderName))) {

		List<String> result = walk.map(x -> x.toString())
				.filter(f -> f.endsWith(".png")).collect(Collectors.toList());

		for(String s:result)
                {
                        
                        String imageName = s.replace(folderName, "");
                        imageName = s.replace(".png", "");
                        System.out.println(imageName);
                    
                }

	} catch (IOException e) {
		e.printStackTrace();
	}
    }
}
