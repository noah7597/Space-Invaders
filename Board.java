//Program creates board for invaders and defenses
//Board
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

public class Board
{
  private SpaceInvaders game; //instance of SpaceInvaders
  int x = 0; //x-coordinate of invader
  int y = 50; //y-coordinate of invader
  Invader[] board = new Invader[15]; //array storing the invaders
  int invadersPlaced = 0; //counter for invaders placed into the array
  
  Defense[][] defenseBoard = new Defense[4][9]; //2d array for storing defense squares
  int xDefense = 50; //x-coordinate of defense
  int yDefense = 300; //y-coordinate of defense
  
  /**
   *   Description: This the constructor for board.
   *
   *   @param SpaceInvaders game an instance of SpaceInvaders
   */
  
  public Board(SpaceInvaders game)
  {
    this.game = game;
  }
  
  /**
   *   Description: This method creates the invaders in board
   *
   */
  
  public void makeInvaders()
  {
    for(int i = 0; i < board.length; i++)
    {
      if(invadersPlaced < 5)
      {
        board[i] = new Invader(game, x * 40, y); //40 pixels between each invader
        invadersPlaced = invadersPlaced + 1;
        x = x + 1;
      }
        
      else if(invadersPlaced >= 5 && invadersPlaced < 10)
      {
        if(x == 5)
        {
          x = 0;
        }
          
        y = 80;
        board[i] = new Invader(game, x * 40, y);
        invadersPlaced = invadersPlaced + 1;
        x = x + 1;
      }
       
      else if(invadersPlaced >= 10 && invadersPlaced < 15)
      {
        if(invadersPlaced == 10)
        {
          x = 0;
        }
          
        y = 110;
        board[i] = new Invader(game, x * 40, y);
        invadersPlaced = invadersPlaced + 1;
        x = x + 1;
      }
        
    }
  }
  
  /**
   *   Description: This is the method that creates the defense squares in defense board
   */
  
  public void makeDefenses()
  {
    for(int i = 0; i < defenseBoard.length; i++)
    {
      for(int j = 0; j < defenseBoard[i].length; j++)
      {
        if(j == 0)
        {
          defenseBoard[i][j] = new Defense(game, xDefense, yDefense);
        }
      
        if(j == 1)
        {
          defenseBoard[i][j] = new Defense(game, xDefense, yDefense - 13);
        }
      
        if(j == 2)
        {
          defenseBoard[i][j] = new Defense(game, xDefense + 12, yDefense - 13);
        }
      
        if(j == 3)
        {
          defenseBoard[i][j] = new Defense(game, xDefense + 25, yDefense - 13);
        }
      
        if(j == 4)
        {
          defenseBoard[i][j] = new Defense(game, xDefense + 38, yDefense - 13);
        }
      
        if(j == 5)
        {
          defenseBoard[i][j] = new Defense(game, xDefense + 38, yDefense);
        }
      
        if(j == 6)
        {
          defenseBoard[i][j] = new Defense(game, xDefense + 12, yDefense - 26);
        }
      
        if(j == 7)
        {
          defenseBoard[i][j] = new Defense(game, xDefense + 25, yDefense - 26);
        }
      
        if(j == 8)
        {
          defenseBoard[i][j] = new Defense(game, xDefense + 19, yDefense - 39);
        }
      }
      
      xDefense = xDefense + 130;
    }
    
  }
  
 
}
