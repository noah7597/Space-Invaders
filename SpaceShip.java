//Program creates SpaceShip object
//SpaceShip
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

public class SpaceShip
{
  private static final int Y = 330; //constant Y-coordinate of ship
  final int WIDTH = 60; //extra distance so the entire ship stays on the screen
  int x = 200; //x coordinate of ship
  int xSpeed = 0; //x coordinate speed of ship
  
  boolean readyToFire; //whether ship can fire or not
  boolean shot = false; //whether the ship has an active bullet
  
  Rectangle bullet; //bullet
  int bx; //x coordinate of bullet
  int by; //y coordinate of bullet
  
  private SpaceInvaders game; //instance of SpaceInvaders
  
  /**
   *   Description: This method is the contructor
   *
   *   @param SpaceInvaders game, instance of SpaceInvaders
   */
   
  public SpaceShip(SpaceInvaders game)
  {
    this.game = game;
    
    Timer t = new Timer();
    t.schedule(new TimerTask()
    {
      @Override
      public void run() //new thread creates which allows the ship to fire every two seconds
      {
        readyToFire = true;
      }
    }, 0, 2000);
  }
  
  /**
   *   Description: This method controls the changes in the x-coordinate
   *
   */
  
  public void move()
  {
    if (x + xSpeed > 0 && x + xSpeed < game.getWidth() - WIDTH) //as long as ship is on the screen
    {
      x = x + xSpeed;
    }
  }
  
  /**
   *   Description: This method changes the y-coordinate of the bullet when it is fired
   *
   */
  
  public void shoot()
  {
    if(shot)
    {
      bullet.y = bullet.y - 15;
    }
  }
  
  /**
   *   Description: This method creates a rectangle around the bullet fired used for collisions
   *
   */
  
  public Rectangle getBounds()
  {
    if(shot)
    {
      return (new Rectangle(bullet.x, bullet.y + 5, 3, 8));
    }
    
    return new Rectangle(0, 0, 0, 0);
  }
  
  /**
   *   Description: This method creates a rectangle around the ship used for collisions
   *
   */
  
  public Rectangle getShipBounds()
  {
    return (new Rectangle(x, Y + 10, 35, 30));
  }
  
  /**
   *   Description: This method paints the ship
   *
   */
  
  public void paint(Graphics2D g)
  {
    try
    {
      BufferedImage image = ImageIO.read(new File("Spaceship.png"));
      g.drawImage(image, x, Y, 40, 40, null);
    }
    catch(Exception e)
    {
      System.out.println("Something went wrong.");
    }
    if(shot)
    {
      g.setColor(Color.WHITE);
      g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
    }
    
  }
  
  /**
   *   Description: This method call actions when the user releases a key
   *
   *   @param KeyEvent e, user stops pressing key
   */
  
  public void keyReleased(KeyEvent e)
  {
    xSpeed = 0;
    
    if(e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      readyToFire = false;
      if(bullet.y <= 20) //if bullet is 20 or fewer pixels away from the top of the screen
      {
        bullet = new Rectangle(0, 0, 0, 0); //bullet becomes null
        shot = false; //bullet is stopped
        readyToFire = true; //the ship is ready to fire
      }
    }
    
  }
  
  /**
   *   Description: This method performs actions when the user presses a key
   *
   *   @param KeyEvent e, user presses a key
   */
  
  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      xSpeed = -5;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      xSpeed = 5;
    }
    if(e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      
      if(bullet == null)
      {
        readyToFire = true;
      }
      
      //readyToFire = true;
      
      if(readyToFire)
      {
        by = Y - 5;
        bx = x + 17;
        bullet = new Rectangle(bx, by, 3, 8);
        shot = true;
      }
    }
  }
  
}