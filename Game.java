//Lauren Wineinger
//Serious Game Phase III
//Computer Science II
//Dr. DeClue

package game;

import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Game
{
  private static Grid grid; //sets up game grid
  private int userRow; //row which user controlled image is in
  private int msElapsed; //milliseconds since start of game
  private int timesGet; //how many times the user has gotten the desired object
  private int timesAvoid; //how many times the user has hit something they shouldn't have
  private int getCount; //tracks the collisions with get to pull up a question
  long startTime; //creates variable to track time left
  long elapsedTime; //current time
  long elapsedSeconds; // current time in seconds
  long secondsDisplay; // the display for seconds
  static String name;
  private static ArrayList<Question> questionList;
  static ScoreManager sm = new ScoreManager();
  
  public Game()
  {
	intro();
	questionList = new ArrayList<Question>();
	loadQuestionArray("questionList1.txt");
    grid = new Grid(10, 19); 
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    startTime = System.currentTimeMillis();
    updateTitle(); 
    grid.setImage(new Location(userRow, 0), "microphone.png");
  }//Game constructor
  
  public void play()
  {
    while (!isGameOver())
    {
      grid.pause(100);
      if (msElapsed % 200 == 0)
      {
        scrollLeft();
        populateRightEdge();
      }
      handleKeyPress();
      updateTitle();
      msElapsed += 100;
    }
  }//play
  
  public void intro()
  {
	  name = JOptionPane.showInputDialog("What is your name?");
	  JOptionPane.showMessageDialog(null, "Welcome to the Singoff, "+name+"!\nYou have 3 minutes to sing 10 music notes correctly.\n"
			  + "Avoid the haters - they damage your score!\nGood luck!");
  }//intro
  
  public static void loadQuestionArray(String insertFile) //throws FileNotFoundException
	{
	    try 
	    {
	    	Scanner scan1 = new Scanner(new File (insertFile));
	    	while (scan1.hasNextLine())
			{
				String q = (scan1.nextLine());
				String a = (scan1.nextLine());
				questionList.add(new Question(q, a));
			}
	    } 
	    catch (Exception FileNotFoundException) {
	    	System.out.println("Question file not found.");
	    }
		
	}//loadQuestionArray
  
  public void question()
  //TAS: Display question and check for correct answer
  {
	  Object [] options = {'A', 'B', 'C', 'D'};
	  
	  Random qp = new Random ();
	  System.out.println(questionList.size());
	  int qi = qp.nextInt(questionList.size());
	  
	  
	  Question q = questionList.get(qi);
	  
	  int ans = JOptionPane.showOptionDialog(null, q.getQuestion(), "Question!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	  
	  if (ans == (q.getAnswer().charAt(0) - 65))
	  {
		  JOptionPane.showMessageDialog(null, "That's correct!");
		  timesGet++;
	  }
	  else
	  {
		  JOptionPane.showMessageDialog(null, "Oh no! Try again next time.");
	  }
  }//question
  
  public void handleKeyPress()
  {
	  grid.setImage(new Location(userRow, 0), null);
	  int key = grid.checkLastKeyPressed();

	  if ((key == 38) && (userRow != 0))//up arrow
	  {
		  userRow--;
		  handleCollision(new Location(userRow, 0));
	  }
	  else if ((key == 40) && (userRow < grid.getNumRows()-1)) //down arrow
	  {
		  userRow++;
		  handleCollision(new Location(userRow, 0));
	  }
	  grid.setImage(new Location(userRow, 0), "microphone.png");
  }	
  
  public void populateRightEdge()
  {
	  int random = (int) (Math.random()*grid.getNumRows());
	  int image = (int) (Math.random()*2);
	  if (image == 0)
		  grid.setImage(new Location(random, grid.getNumCols()-1), "eightNote.gif");
	  else
		  grid.setImage(new Location(random, grid.getNumCols()-1), "meanFace.jpg");
  }
  
  public void scrollLeft()
  {
	  
	  for (int r = 0; r < grid.getNumRows(); r++)
	  {
		  for (int c = 0; c < grid.getNumCols()-1; c++)
		  {

			  if (grid.getImage(new Location(r, c)) != "microphone.png")
			  {
				  handleCollision(new Location(userRow, 1));
				  grid.setImage(new Location(r, c), grid.getImage(new Location (r, c+1)));
				  grid.setImage(new Location(r, c+1), null);
			  }

		  }//for
	  }//for
  }//scrollLeft
  
  public void handleCollision(Location loc)
  {
	  if (grid.getImage(loc) == null)
		  return;
	  else if (grid.getImage(loc).equals("eightNote.gif"))
	  {
		  getCount++;
		  if (getCount % 3 == 0)
			  question();
	  }
	  else if (grid.getImage(loc).equals("meanFace.jpg"))
		  timesAvoid++;
	  grid.setImage(loc, null);

  }
  
  public int getScore()
  {
    return timesGet - timesAvoid;
  }
  
  public void updateTitle()
  {
    grid.setTitle("Singoff! Score:  " + getScore() + "/10. Time: "
    		+timer()+" seconds left!");
  }
  
  public long timer()
  {
	  elapsedTime = System.currentTimeMillis() - startTime;
	  elapsedSeconds = elapsedTime / 1000;
	  secondsDisplay = 180 - elapsedSeconds;
	  return secondsDisplay;
  }
  
  public boolean isGameOver()
  {
	  if (getScore() == 10)
	  {
		  JOptionPane.showMessageDialog(null, "Congratulations! You won the singing competition!");
		  sm.write(name, getScore());
		  return true;
	  }
	  else if (secondsDisplay <= 0)
	  {
		  JOptionPane.showMessageDialog(null, "The show ended, but you didn't win. You answered "+timesGet+" questions "
				  + "correctly. Your score was "+getScore()+" out of 10.");
		  sm.write(name, getScore());
		  return true;
	  }
	  else
		  return false;
  }//isGameOver
  
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  
  public static void main(String[] args)
  {
	  ArrayList<String> inMain = new ArrayList<String>();
	  
	  inMain.add("Yes");
	  inMain.add("No");
	  Object [] candidates = {inMain.get(0), inMain.get(1)};
	  
	  int ans = 0;
	  
	  String playAgain = "Would you like to sing again?";
	  
	  sm.read();
	  
	  do
	  {
		test();
		ans = JOptionPane.showOptionDialog(null, playAgain, "Title", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, candidates, candidates[0]);
		sm.print();
		grid.close();
	  }while (ans == 0);
  }//main
}//Game