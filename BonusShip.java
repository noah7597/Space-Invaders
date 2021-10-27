//Program creates BonusShip object
//BonusShip
//5-17-21

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class BonusShip
{
  final int WIDTH = 60; //extra space so that bonus ship stays on screen
  private SpaceInvaders game; //instance of SpaceInvader
  int y = 25; //y-coordinate
  int x = -100; //x-coordinate
  int xSpeed = 3; //xSpeed
  int ySpeed = 0; //ySpeed
  boolean visible = true; //whether bonus ship is visible
  int rectWidth = 40; //width of bonus ship
  int rectHeight = 20; //height of bonus ship

  /**
   *   Description: This method is the constructor
   *
   *   @param SpaceInvaders game, an instance of SpaceInvaders
   */

  public BonusShip(SpaceInvaders game)
  {
    this.game = game;
    
    Random random = new Random();
    int randNum = random.nextInt(20000) + 20000;
    
    Timer t = new Timer(); //bonus ship set to visible and has its x-coordinate reset
    t.schedule(new TimerTask()
    {
      @Override
      public void run()
      {
        visible = true;
        x = -100;
      }
    }, 0, randNum);
    
  }
  
  /**
   *   Description: This method paints the bonus ship
   *
   *   @param Graphics2D g, allows for the ship to be painted
   */
  
  public void paint(Graphics2D g)
  {
    if(visible) //paints bonus ship if visible, else the bonus ship is null
    {
      try
      {
        BufferedImage image = ImageIO.read(new File("Bonus.png"));
        g.drawImage(image, x, y, rectWidth, rectHeight, null);
      }
      catch(Exception e)
      {
        System.out.println("Something went wrong.");
      }
      
      rectWidth = 40;
      rectHeight = 20;
    }
    else
    {
      try
      {
        BufferedImage image = ImageIO.read(new File("Bonus.png"));
        g.drawImage(image, 0, 0, 0, 0, null);
      }
      catch(Exception e)
      {
        System.out.println("Something went wrong.");
      }
      rectWidth = 0;
      rectHeight = 0;
    }
  }
  
  /**
   *   Description: This method controls the movement for the bonus ship
   *
   */
  
  public void move()
  {
    x = x + xSpeed;
  }
  
  /**
   *   Description: This method create a rectangle that is used for collisons
   *
   *   
   *   @return Rectangle rectangle around bonus ship
   */
  
  public Rectangle getBounds()
  {
    return (new Rectangle(x, y, rectWidth, rectWidth));
  }
}