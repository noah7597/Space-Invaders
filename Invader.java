//Program creates Invader object
//Invader
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

public class Invader
{
  private SpaceInvaders game; //instance of spaceinvaders
  boolean visible = true; //determines whether invader can be seen or not
  final int WIDTH = 60; //extra space so entire invader stays on the screen
  
  Rectangle r = new Rectangle(0, 50, 30, 30); //rectangle around invader 
  
  Rectangle bullet; //alien bullet
  boolean shot = false; //whether bullet has been fired
  boolean readyToFire = true; //whether bullet can be fired
  int bx; //x-coordinate of bullet
  int by; //y-coordinate of bullet
  
  int y; //y-coordinate of invader
  int x; //x-coordinate of invader
  int xSpeed = 1; //xSpeed of invader
  int ySpeed = 0; //ySpeed of invader
  int rectWidth = 30; //width of invader
  int rectHeight = 30; //height of invader
  
  boolean turn = false; //whether invader has hit one of the sides
  
  int counter = 1; //counter used for when invader becomes open or closed

  /**
   *   Description: This is the constructor that creates the invader object
   *
   *   @param SpaceInvaders game, instance of SpaceInvaders
   *   @param int x, x-coordinate of invader
   *   @param int y, y-coordinate of invader
   */

  public Invader(SpaceInvaders game, int x, int y)
  {
    this.game = game;
    this.x = x;
    this.y = y;
    timerShoot();
  }
  
  /**
   *   Description: This method controls when the invader fires.
   *
   */
  
  public void timerShoot()
  {
    Random random = new Random();
    int randNum = random.nextInt(10000) + 5000;
    
    Timer t = new Timer();
    t.schedule(new TimerTask()
    {
      @Override
      public void run() //invader fire bullet 5 to 15 seconds
      {
        if(visible)
        {
          by = y + 30;
          bx = x + 10;
          bullet = new Rectangle(bx, by, 3, 8); //bullet is formed
          shot = true; //bullet is fired
        }
      }
    }, 0, randNum);
    
    Timer t2 = new Timer();
    t2.schedule(new TimerTask() //every second the counter increases by one
    {
      @Override
      public void run()
      {
        counter = counter + 1;
      }
    }, 0, 1000);
    
  }
  
  /**
   *   Description: This method controls the movement of the invader
   *
   */
  
  public void move()
  {
    turn = false;
    if (x + xSpeed == 0) //left edge
    {
      turn = true;
    }
    if (x + xSpeed == game.getWidth() - WIDTH) //right edge
    {
      turn = true;
    }
    
    x = x + xSpeed;
  }
  
  /**
   *   Description: This method paints the invader
   *
   *   @param Graphics2D g, graphics that can be used to paint
   */
  
  public void paint(Graphics2D g)
  {
    if(!visible) //invader becomes null
    {
      try
      {
        BufferedImage image = ImageIO.read(new File("InvaderOpen.png"));
        g.drawImage(image, x, y, 0, 0, null);
      }
      catch(Exception e)
      {
        System.out.println("Something went wrong.");
      }
      
      rectWidth = 0;
      rectHeight = 0;
    }
    else
    { 
      if(counter % 2 == 0) //invader is open
      {
        try
        {
          BufferedImage image = ImageIO.read(new File("InvaderOpen.png"));
          g.drawImage(image, x, y, 30, 30, null);
        }    
        catch(Exception e)
        {
          System.out.println("Something went wrong.");
        }
      }
      else //invader is closed (these change every second)
      {
        try
        {
          BufferedImage image = ImageIO.read(new File("InvaderClosed.png"));
          g.drawImage(image, x, y, 30, 30, null);
        }    
        catch(Exception e)
        {
          System.out.println("Something went wrong.");
        }
      }
    }
    
    if(shot)
    {
      g.setColor(Color.GREEN);
      g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
    }
    
  }
  
  /**
   *   Description: This method controls the change in the y-coordinate of the bullet
   *
   */
  
  public void shoot()
  {
    if(shot)
    {
      bullet.y = bullet.y + 15;
    }
  }
  
  /**
   *   Description: This method creates rectangle around invader
   *
   *   
   *   @return Rectangle rectangle around invader used for collisions
   */
  
  public Rectangle getBounds()
  {
    return (new Rectangle(x, y, rectWidth, rectWidth));
  }
  
  /**
   *   Description: This method creates a rectangle around the alien bullet used for collisions
   *
   *   
   *   @return Rectangle, rectangle around bullet
   */
  
  public Rectangle getBulletBounds()
  {
    if(shot)
    {
      return (new Rectangle(bullet.x, bullet.y - 5, 3, 8));
    }
    
    return new Rectangle(0, 0, 0, 0);
  }
  
}