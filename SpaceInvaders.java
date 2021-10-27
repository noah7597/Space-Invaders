//Program coordinates classes and methods for space invaders
//SpaceInvaders
//5-17-21
//947 lines of code

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SpaceInvaders extends JPanel
{
  SpaceShip ship = new SpaceShip(this); //creates instance of user controlled ship
  Board field = new Board(this); //creates instance of board
  BonusShip bonus = new BonusShip(this); //creates instance of the bonus ship
  Color transparent = new Color(0,0,0,0); //transparent color
  
  int score = 0;
  int round = 1;
  
  /**
   *   Description: This the constructor that creates the SpaceInvaders object.
   *
   *   
   */
  
  public SpaceInvaders()
  {
    addKeyListener(new KeyListener()
    {
      @Override
      public void keyTyped(KeyEvent e){}

      @Override
      public void keyReleased(KeyEvent e)
      {
        ship.keyReleased(e);
      }

      @Override
      public void keyPressed(KeyEvent e)
      {
        ship.keyPressed(e);
      }
    });
    setFocusable(true); //focues the spaceInvaders object
    
    field.makeInvaders(); //creates invaders
    field.makeDefenses(); //creates defenses
  }
  
  /**
   *   Description: This method coordinates the movement of all objects
   *
   */
   
  private void move()
  {
    ship.move(); //user controlled ship movement
    bonus.move(); //bonus ship movement
    for(int i = 0; i < field.board.length; i++)
    {
      field.board[i].move();
      if(field.board[i].turn) //movement of all invaders is reversed if one hits the edge
      {
        for(int j = 0; j < field.board.length; j++)
        {
          field.board[j].xSpeed = field.board[j].xSpeed * -1;
          field.board[j].y = field.board[j].y + 30;
        }
      }
    }
  }
  
  /**
   *   Description: This method paints all of the objects.
   *
   */
  
  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    setBackground(Color.black);
        
    ship.paint(g2d); //paint ship
    g2d.setColor(Color.BLACK);
    g2d.draw(ship.getShipBounds()); //draw the rectangle used for collisons
    ship.shoot();
    g2d.draw(ship.getBounds()); //bullet rectangle used for collsion
    
    bonus.paint(g2d); //paint bonus ship
    g2d.draw(bonus.getBounds()); //the rectangle used for the collisons
    
    for(int i = 0; i < field.board.length; i++) //paints all of the invaders
    {
      field.board[i].paint(g2d);
      field.board[i].shoot();
      g2d.setColor(Color.BLACK);
      g2d.draw(field.board[i].getBounds()); //invader rectangle used for collsion
      g2d.draw(field.board[i].getBulletBounds()); //invader bullet rectangle used for collision
    }
    
    
    for(int i = 0; i < field.defenseBoard.length; i++) //paints all of the defenses. Defense = 1 square
    {
      for(int j = 0; j < field.defenseBoard[i].length; j++)
      {
        field.defenseBoard[i][j].paint(g2d);
        g2d.setColor(transparent);
        g2d.draw(field.defenseBoard[i][j].getBounds()); //draw rectangle used for collisions
      }
    }
    
    if(userWins() == 1) //determines if the user has destroyed all of the invaders on the board, if so, variables set to default
    {
      field.x = 0;
      field.y = 50;
      field.invadersPlaced = 0;
      field.makeInvaders();
      
      round = round + 1;
    }
    
    g2d.setColor(Color.WHITE);
    g2d.drawString("Score: " + score, 260, 20);
    g2d.drawString("Round: " + round, 50, 20);
  }
  
  /**
   *   Description: This method performs actions when the alien is hit by a bullet from the ship
   *
   */
  
  public void alienHit()
  {
    Rectangle bullet = ship.getBounds(); //rectangle around bullet used for collisions
    
    for(int i = 0; i < field.board.length; i++)
    {
      Rectangle alien = field.board[i].getBounds(); //rectangle around alien used for collisions
      
      if(bullet.intersects(alien))
      {        
        ship.shot = false; //bullet stopped
        ship.readyToFire = true; //ship ready to fire
        field.board[i].visible = false; //alien invisible
        score = score + 10;
      }
    }
  }
  
  /**
   *   Description: This method performs actions when the ship is hit by the alien
   *
   */
  
  public void shipHit()
  {
    Rectangle spaceShip = ship.getShipBounds(); //rectangle around ship used for collisions
    
    for(int i = 0; i < field.board.length; i++)
    {
      Rectangle alienBullet = field.board[i].getBulletBounds(); //rectangle around alien used for collisions
    
      if(spaceShip.intersects(alienBullet))
      {
        JOptionPane.showMessageDialog(this, "Final Score: " + score, "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
      }
    }
  }
  
  /**
   *   Description: This method performs actions when the alien collides with the ship
   *
   */
  
  public void alienHitsShip()
  {
    Rectangle spaceShip = ship.getShipBounds(); //rectangle around ship used for collisions
    
    for(int i = 0; i < field.board.length; i++)
    {
      Rectangle alien = field.board[i].getBounds(); //rectangle around alien used for collisions
    
      if(spaceShip.intersects(alien))
      {
        JOptionPane.showMessageDialog(this, "Final Score: " + score, "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
      }
    }
  }
  
  /**
   *   Description: This method performs actions when the defense is hit by the ship
   *
   */
  
  public void defenseHitByShip() throws InterruptedException
  {
    Rectangle bullet = ship.getBounds(); //rectangle around ship bullet used for collisions
    
    for(int i = 0; i < field.defenseBoard.length; i++)
    {
      for(int j = 0; j < field.defenseBoard[i].length; j++)
      {
        Rectangle defense = field.defenseBoard[i][j].getBounds(); //rectangle around each defense square used for collisions
        
        if(bullet.intersects(defense))
        {
          ship.shot = false; //bullet stopped
          ship.readyToFire = true; //ship ready to fire
        
          //following if statements adjust defense square damage
          if(field.defenseBoard[i][j].noHits)
          {
            field.defenseBoard[i][j].noHits = false;
            field.defenseBoard[i][j].firstHit = true;
          }
        
          else if(field.defenseBoard[i][j].firstHit)
          {
            field.defenseBoard[i][j].firstHit = false;
            field.defenseBoard[i][j].secondHit = true;
          }
        
          else if(field.defenseBoard[i][j].secondHit)
          {
            field.defenseBoard[i][j].secondHit = false;
            field.defenseBoard[i][j].thirdHit = true;
          }
        
          else if(field.defenseBoard[i][j].thirdHit)
          {
            field.defenseBoard[i][j].thirdHit = false;
            field.defenseBoard[i][j].fourthHit = true;
          }
        
          else if(field.defenseBoard[i][j].thirdHit)
          {
            field.defenseBoard[i][j].thirdHit = false;
            field.defenseBoard[i][j].fourthHit = true;
          }
        } 
      }
    }
    
  } 
  
  /**
   *   Description: This method performs actions when the defense is hit by an alien bullet
   *
   */
  
  public void defenseHitByAlien()
  {
    
    for(int i = 0; i < field.defenseBoard.length; i++)
    {
      for(int j = 0; j < field.defenseBoard[i].length; j++)
      {
        Rectangle defense = field.defenseBoard[i][j].getBounds(); //rectangle around defense used for collisions
        
        for(int k = 0; k < field.board.length; k++)
        {
          Rectangle alienBullet = field.board[k].getBulletBounds(); //rectangle around alien bullet used for collisions
    
          if(defense.intersects(alienBullet))
          {
            field.board[k].shot = false; //alien bullet stops
            
            //following if statements adjust defense square damage
            if(defense.intersects(alienBullet) && field.defenseBoard[i][j].noHits)
            {
              field.defenseBoard[i][j].noHits = false;
              field.defenseBoard[i][j].firstHit = true;
            }
        
            else if(defense.intersects(alienBullet) && field.defenseBoard[i][j].firstHit)
            {              
              field.defenseBoard[i][j].firstHit = false;
              field.defenseBoard[i][j].secondHit = true;
            }
        
            else if(defense.intersects(alienBullet) && field.defenseBoard[i][j].secondHit)
            {              
              field.defenseBoard[i][j].secondHit = false;
              field.defenseBoard[i][j].thirdHit = true;
            }
        
            else if(defense.intersects(alienBullet) && field.defenseBoard[i][j].thirdHit)
            {
              field.defenseBoard[i][j].thirdHit = false;
              field.defenseBoard[i][j].fourthHit = true;
            }
        
          }
        }
      }
    }
    
  }
  
  /**
   *   Description: This method performs actions when an alien bullet and a ship bullet collide
   *
   */
  
  public void bulletHitsBullet()
  {
    Rectangle bullet = ship.getBounds(); //rectangle around ship bullet used for collisions
    
    for(int k = 0; k < field.board.length; k++)
    {
      Rectangle alienBullet = field.board[k].getBulletBounds(); //rectangle around alien bullet used for collisions
    
      if(bullet.intersects(alienBullet))
      {
        ship.shot = false; //ship bullet stopped
        ship.readyToFire = true; //ship ready to fire
        
        field.board[k].shot = false; //alien bullet stopped
      } 
    }
    
  }
  
  /**
   *   Description: This method performs actions when the ship bullet hits the bonus ship
   *
   */
  
  public void shipHitsBonus()
  {
    Rectangle bullet = ship.getBounds(); //rectangle around ship bullet used for collisons
    Rectangle bonusShip = bonus.getBounds(); //rectangle around bonuship used for collisions
    
    if(bullet.intersects(bonusShip))
    {
      ship.shot = false; //ship bullet stopped
      ship.readyToFire = true; //ship ready to fire
      score = score + 50;
      bonus.visible = false; //bonus ship disappears
    }
  }
  
  /**
   *   Description: This method determines if the user has destroyed all aliens.
   *
   *   
   *   @return returns 1 if all aliens have been destroyed, and 0 otherwise
   */
  
  public int userWins()
  {
    for(int i = 0; i < field.board.length; i++)
    {
      if(field.board[i].visible)
      {
        return 0;
      }
    }
    
    return 1;
  }
  
  /**
   *   Description: This the main method where all future methods are called.
   *
   */
  
  public static void main(String[] args) throws InterruptedException
  {
    JFrame frame = new JFrame("Space Invaders");
    SpaceInvaders game = new SpaceInvaders();
    frame.add(game);
    frame.setSize(600, 400);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    while (true)
    {
      game.move();
      game.defenseHitByShip();
      game.defenseHitByAlien();
      game.shipHitsBonus();
      game.repaint();
      Thread.sleep(15);
      game.alienHit();
      game.shipHit();
      game.alienHitsShip();
      game.bulletHitsBullet();
      Thread.sleep(15);
    }
  }
}