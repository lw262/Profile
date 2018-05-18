package game;

public class Question 
{
	private String question;
	private String answer;
	
	public Question()
	{
		question = "";
		answer = "";
	}
	
	public Question(String newQuestion, String newAnswer)
	{
		question = newQuestion;
		answer = newAnswer;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	
	
}
