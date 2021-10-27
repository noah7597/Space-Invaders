//Program creates Defense object
//Defense
//5-17-21

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class Defense
{
  private SpaceInvaders game; //instance of SpaceInvaders
  private int x; // = 50
  private int y; // = 300
  
  int rectWidth = 11; //width of rectangle around defense square
  int rectHeight = 11; //height of rectangle around defense square
  
  boolean noHits = true; //if defense square has not been hit yet
  boolean firstHit = false; //if defense square has been hit once
  boolean secondHit = false; //if defense square has been hit twice
  boolean thirdHit = false; //if defense square has been hit three times
  boolean fourthHit = false; //if defense square has been hit four times
  
  /**
   *   Description: Creates defense object
   *
   *   @param SpaceInvaders game, instance of SpaceInvaders
   *   @param int x, x-coordinate of defense square
   *   @param int y, y-coordinate of defense square   
   *
   */
  
  public Defense(SpaceInvaders game, int x, int y)
  {
    this.game = game;
    this.x = x;
    this.y = y;
  }
  
  /**
   *   Description: This method paints the square
   *
   *   @param Graphics2D g, graphics used to paint
   */
  
  public void paint(Graphics2D g)
  {
    try //image of defense square changes based on how many times it has been hit
    {
      if(noHits)
      {
        BufferedImage block1 = ImageIO.read(new File("defense1.png")); //bottom left
        g.drawImage(block1, x, y, 13, 13, null);
      }
      
      if(firstHit)
      {
        BufferedImage block1 = ImageIO.read(new File("defense2.png")); //bottom left
        g.drawImage(block1, x, y, 13, 13, null);
      }
      
      if(secondHit)
      {
        BufferedImage block1 = ImageIO.read(new File("defense3.png")); //bottom left
        g.drawImage(block1, x, y, 13, 13, null);
      }
      
      if(thirdHit)
      {
        BufferedImage block1 = ImageIO.read(new File("defense4.png")); //bottom left
        g.drawImage(block1, x, y, 13, 13, null);
      }
      
      if(fourthHit)
      {
        BufferedImage block1 = ImageIO.read(new File("defense1.png")); //bottom left
        g.drawImage(block1, 0, 0, 0, 0, null);
        
        rectWidth = 0;
        rectHeight = 0;
      }
    }
    catch(Exception e)
    {
      System.out.println("Something went wrong.");
    }
  }
  
  /**
   *   Description: This method creates a rectangle around each defense square for collisions
   *
   *   
   *   @return Rectangle rectangle around defense square
   */
  
  public Rectangle getBounds()
  {
    return (new Rectangle(x, y, rectWidth, rectHeight));
  }
} 