package game;

import java.util.*;
import java.io.*;

public class ScoreManager 
{
	private ArrayList<String> n = new ArrayList<String>();
	private ArrayList<String> s = new ArrayList<String>();
	private int amount = 0;
	Heap h = new Heap();
	
	public ScoreManager()
	{
		
	}//ScoreManager
	
	public void write(String name, int score)
	{
		h.insert(name, score);
		try
		{
			FileOutputStream fos = new FileOutputStream("highScoreList.txt", true);
			
			PrintWriter pw = new PrintWriter(fos);
			
			pw.println(name);
			pw.println(score);
			
			pw.close();
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("File Not Found");
		}
	}//write
	
	public void read()
	{
		try
		{
			File inputFile = new File ("highScoreList.txt");
			Scanner file = new Scanner(inputFile);
			boolean name = true;
			while (file.hasNext())
			{
				//System.out.println("inside while");
				if (name == true)
				{
					n.add(file.next());
					name = false;
				}//if
				else
				{
					s.add(file.next());
					name = true;
					amount++;
				}//else
			}//while
			
			for (int i = 0; i < amount; i++)
			{
				h.insert(n.get(i), Integer.parseInt(s.get(i)));
			}
		}//try
		catch(IOException e)
		{
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}//catch
	}//read
	
	public void print()
	{
		h.testPrint();
	}//print
}//ScoreManager class
