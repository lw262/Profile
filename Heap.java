//Lauren Wineinger
//Phase 3 Serious Game
//Heap class borrowed from CS2 lab

package game;

import javax.swing.JOptionPane;

public class Heap
{
  private final int MAXHEAP =100;  //Arbitrarily picked
  protected HeapEntry [] tree;     //The array to hold the heap entries
  protected int insertPoint;       //Location of the place to put an new entry
  protected int size;

  public Heap()
  //PRE: None
  //POS: Empty heap; insertPoint == 0;
  //TAS: Create a new heap
  {
    tree = new HeapEntry[MAXHEAP];
    insertPoint = 0;
    size = 0;
  }//Heap

  public boolean isEmpty()
  {
    return tree[0] == null;
  }

  public void testPrint()
  //PRE: none
  //POS: none
  //TAS: debugging code.  Not for use in final implementation
  {
    String highScore = "High Scores: \n";
    for (int i = 0; i < insertPoint; i++)
      highScore = highScore + tree[i].getElement() + ": " + tree[i].getPriority() + "\n";
    JOptionPane.showMessageDialog(null, highScore);
  }

  private void swap (int parent, int child)
  //PRE: two or more nodes in heap
  //POS: tree[parent]<exit> == tree[child]<entry>
  //     tree[child]<exit> == tree[parent]<entry>
  //TAS: swap entries at parent and child
  {
    HeapEntry temp = new HeapEntry();
    temp.setElement(tree[parent].getElement());
    temp.setPriority(tree[parent].getPriority());

    tree[parent].setElement(tree[child].getElement());
    tree[parent].setPriority(tree[child].getPriority());

    tree[child].setElement(temp.getElement());
    tree[child].setPriority(temp.getPriority());
  }

  public void insert (Object newElement, int priority)
  //PRE: none
  //POS: tree<exit> == tree<entry> + newElement && tree is heap
  //POS: insertPoint<exit> == insertPoint<entry> + 1
  //TAS: insert newElement into tree heap
  {
    //Create the new HeapEntry
    tree[insertPoint] = new HeapEntry (newElement, priority);
    //Set up local variables
    boolean done = false;
    int parent = (insertPoint-1)/2;  //nifty trick for finding parent's location
    int child = insertPoint;
    insertPoint++;                   //increase to the next insertion location

    while ((parent >= 0) && !done)   //start hunting for correct location
    {
      if (tree[parent].getPriority() < tree[child].getPriority())
      {
        swap (parent,child);
        child = parent;
        parent = (parent -1)/2;
      }
      else
      {
        done = true;
      }

    }//while
  }//insert


  public Object peek ()
  //PRE: none
  //POS: none
  //TAS: return the object at top of heap
  {
    if (tree == null)
      return null;
    else
      return tree[0].getElement();
  }

  public Object remove()
  //PRE: Tree is not empty
  //POS:
  //TAS:
  {
	  HeapEntry h = tree [0]; //the element from the root.

	  if (insertPoint == 1) //there was only the root and it was plucked
		  tree[0] = null;
	  else //possibility for reheapifying exists
	  {
		  //replace the root witht he last node inserted
		  tree [0] = tree [insertPoint-1];
		  tree [insertPoint-1] = null;
		  reHeapify(); //restore the heap property
	  }
	  insertPoint --;
	  return h;
  }//remove

  private int findSwapChild (int left, int right)
  //PRE: !isEmpty()
  //POS: none
  //TAS: Decide which child to swap with parent during reheapify
  {
	  int next = 0;
	  if ((tree[left] == null) && (tree[right] == null))
		  next = insertPoint;
	  else if (tree[left] == null)
		  next = right;
	  else if (tree[right] == null)
		  next = left;
	  else if ((tree[left].getPriority() > tree[right].getPriority()))
		  next = left;
	  else 
		  next = right;
	  return next;
  }//findSwapChild

  private void reHeapify()
  //PRE: tree has at least one parent and one child
  //POS: heap is a heap
  {
	  HeapEntry temp;
	  int node = 0;
	  int left = 1;
	  int right = 2;

	  int next = findSwapChild(left, right);

	  while ((next < insertPoint) && (tree[next].getPriority() > tree[node].getPriority()))
	  {
		  temp = tree[node];
		  tree[node] = tree[next];
		  tree[next] = temp;

		  node = next;
		  left = 2*node+1;
		  right = 2*node+2;
		  next = findSwapChild(left, right);
	  }
  }//reHeapify

}//Heap