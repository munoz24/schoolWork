import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class MineSweapPart extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 760;
	private static final int WIDTH = 760;
	private static final int ROWS = 16;
	private static final int COLS = 16;
	private static final int MINES = 16;
	private static int minesLeft = MineSweapPart.MINES;
	private static int actualMinesLeft = MineSweapPart.MINES;
	private static final String FLAGGED = "@";
	private static final String MINE = "M";
	// visual indication of an exposed MyJButton
	private static final Color expColor = Color.lightGray;
	// colors used when displaying the getStateStr() String
	private static final Color colorMap[] = {Color.lightGray, Color.blue, Color.green, Color.cyan, Color.yellow,
			Color.orange, Color.pink, Color.magenta, Color.red, Color.red};
	private boolean running = true;
	// holds the "number of mines in perimeter" value for each MyJButton
	private int[][] sGrid = new int[ROWS][COLS];
	public MineSweapPart()
	{
		this.setTitle("MineSweap " +
				MineSweapPart.minesLeft +" Mines left");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLayout(new GridLayout(ROWS, COLS, 0, 0));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.createContents();
		// place MINES number of mines in sGrid and adjust all of the "mines in perimeter" values
		this.setMines();
		this.setVisible(true);
	}
	public void createContents()
	{
		for (int br = 0; br < ROWS; ++br)
		{
			for (int bc = 0; bc < COLS; ++bc)
			{
				// set sGrid[br][bc] entry to 0 - no mines in itís perimeter
				sGrid[br][bc] = 0;
				// create a MyJButton that will be at location (br, bc) in the GridLayout
				MyJButton but = new MyJButton("", br, bc);
				// register the event handler with this MyJbutton
				but.addActionListener(new MyListener());
				// add the MyJButton to the GridLayout collection
				this.add(but);
			}
		}
	}

	// nested private class
	private class MyListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if ( running )
			{
				// used to detrmine if ctrl or alt key was pressed at the time of mouse action
				int mod = event.getModifiers();
				MyJButton jb = (MyJButton)event.getSource();
				// is the MyJbutton that the mouse action occurred in flagged
				boolean flagged = jb.getText().equals(MineSweapPart.FLAGGED);
				// is the MyJbutton that the mouse action occurred in already exposed
				boolean exposed = jb.getBackground().equals(expColor);
				// flag a cell : ctrl + left click
				if ( !flagged && !exposed && (mod & ActionEvent.CTRL_MASK) != 0 )
				{
					jb.setText(MineSweapPart.FLAGGED);
					--MineSweapPart.minesLeft;
					// if the MyJbutton that the mouse action occurred in is a mine
					if ( sGrid[jb.row][jb.col] == 9 )
					{
						MineSweapPart.actualMinesLeft -- ;


					}
					setTitle("MineSweap " +
							MineSweapPart.minesLeft + " Mines left");
				}
				// un-flag a cell : alt + left click
				else if ( flagged && !exposed && (mod & ActionEvent.ALT_MASK) != 0 )
				{
					jb.setText("");
					++MineSweapPart.minesLeft;
					// if the MyJbutton that the mouse action occurred in is a mine
					if ( sGrid[jb.row][jb.col] == 9 )
					{
						++MineSweapPart.actualMinesLeft;
					}
					setTitle("MineSweap " +
							MineSweapPart.minesLeft +" Mines left");
				}
				// expose a cell : left click
				else if ( !flagged && !exposed)
				{
					exposeCell(jb);
				}
				if(actualMinesLeft == 0 && minesLeft==0)
				{
					setTitle("You Win");

					running = false;
				}
			}
		}
		public void exposeCell(MyJButton jb)
		{
			if ( !running )
				return;
			// expose this MyJButton
			jb.setBackground(expColor);
			jb.setForeground(colorMap[sGrid[jb.row][jb.col]]);
			jb.setText(getStateStr(jb.row, jb.col));
			// if the MyJButton that was just exposed is a mine
			if ( sGrid[jb.row][jb.col] == 9 )
			{


				//here goes the expose everything when player loses
				
				
				running = false;
				setTitle("You Lose");
				return;
			}
			// if the MyJButton that was just exposed has no mines in its perimeter
			if ( sGrid[jb.row][jb.col] == 0 )
			{

				// Need nested for loop to go through the entire array to look at your piremeter
				for(int x = -1; x<2; x++){
					for(int y = -1; y<2; y++){
						// Check that x && y are not == 0
						// if the for loops point to an inbound point
						if(!(x == 0 && y == 0) &&
								(jb.row+x >= 0) && (jb.row+x < MineSweapPart.ROWS) &&
								(jb.col+y >= 0) && (jb.col+y < MineSweapPart.COLS)){

							int index = ((jb.row+x)*MineSweapPart.ROWS) + (jb.col+y);
							MyJButton newButton = (MyJButton)(jb.getParent().getComponent(index));
							// if the new button is not flagged and not exposed
							if (!newButton.getText().equals(MineSweapPart.FLAGGED)&&
									!newButton.getBackground().equals(expColor)){
								exposeCell(newButton);
							}
						}

					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new MineSweapPart();
	}

	// ************************************************************************************************
	// place MINES number of mines in sGrid and adjust all of the
	// "mines in perimeter" values
	private void setMines() 
	{
		int mineCount =0;

		while(mineCount < MINES){
			int randR = (int) (Math.random()*ROWS);
			int randC = (int) (Math.random()*COLS);
			if((sGrid[randR][randC]!=9)) {
				sGrid[randR][randC]=9;
				for(int rm = -1; rm <2; rm++){
					for(int cm = -1; cm <2; cm++){
						if(!((rm ==0) && (cm ==0)) &&
								((randR+ rm < ROWS )&& ( randR +rm>=0)) &&
								((randC + cm>=0)&& (randC + cm< COLS)))
						{
							if(sGrid[randR+rm][randC+cm]!=9){
								sGrid[randR+rm][randC+cm]++;

							}
						}
					}
				}
				mineCount++;
			}
		}
	}

	private String getStateStr(int row, int col) {
		// no mines in this MyJbuttonís perimeter
		if (this.sGrid[row][col] == 0)
			return "";
		// 1 to 8 mines in this MyJButtonís perimeter
		else if (this.sGrid[row][col] > 0 && this.sGrid[row][col] < 9)
			return "" + this.sGrid[row][col];
		// this MyJButton in a mine
		else
			return MineSweapPart.MINE;
	}
}

// nested private class



