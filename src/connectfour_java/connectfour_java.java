package connectfour_java;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class connectfour_java {
	
	//we define characters for players R for red Y for yellow
	private static final char[] PLAYERS = {'R', 'Y'};
	
	//we define our board
	private static int width, height;
	
	//grid for the board
	private final char[][] grid;
	
	//store last move made by player
	private int lastCol = -1, lastTop = -1;
	
	public connectfour_java(int w, int h) {
		width = w;
		height = h;
		grid = new char[h][];
		
		//init the grid will blank cell
		for(int i = 0; i < h; i++) {
			Arrays.fill(grid[i] = new char[w], '.');
		}
	}
	
	public String toString() {
		return IntStream.range(0, width).mapToObj(Integer::toString).collect(Collectors.joining()) + "\n" +
				Arrays.stream(grid).map(String::new).collect(Collectors.joining("\n"));
	}
	
	// get string representation of the horizontal containing the last play of the user
	public String horizontal() {
		return new String(grid[lastTop]);
	}
	
	//get string representation o fthe vertical containing the last play of the user 
	public String vertical(){
		StringBuilder sb = new StringBuilder(height);
		
		for(int h = 0; h <height; h++) {
			sb.append(grid[h][lastCol]);
		}
		
		return sb.toString();
	}
	
	//get string representation of the "/" diagonal containing the last play of the user
	public String slashDiagonal() {
		StringBuilder sb = new StringBuilder(height);
		
		for(int h = 0; h < height; h++) {
			int w = lastCol + lastTop - h;
			
			if( 0 <= w && w < width) {
				sb.append(grid[h][w]);
			}
		}
		
		return sb.toString();
	}
	
	//get string representation of the "\" diagonal containing the last play of the user
	public String backslashDiagonal() {
		StringBuilder sb = new StringBuilder(height);
		
		for(int h = 0; h < height; h++) {
			int w = lastCol - lastTop + h;
			
			if( 0 <= w && w < width) {
				sb.append(grid[h][w]);
			}
		}
		
		return sb.toString();
	}
	
	//static method checking if a substring is in str
	public static boolean contains(String str, String substring) {
		return str.indexOf(substring) >= 0;
	}
	
	//check if last play is win
	public boolean isWinningPlay() {
		if(lastCol == -1) {
			System.err.println("No move has been made yet");
		}
		
		char sym = grid[lastTop][lastCol];
		//winning streak with the last play symbol
		String streak = String.format("%c%c%c%c", sym,sym,sym,sym);
		
		//check if streak is in row,m col, diagonal or backslash diagonal
		return contains(horizontal(), streak) || contains(vertical(), streak) 
				|| contains(slashDiagonal(), streak) || contains(backslashDiagonal(), streak);
	}
	
	//prompts the user for a column, repeating until a valid choice is made
	public void chooseAndDrop(char symbol, Scanner input) {
		do {
			System.out.println("\nPlayer " + symbol + " turn: ");
			int col = input.nextInt();
			
			//check if column is ok
			if(!(0 <= col && col <width)) {
				System.out.println("Column must be between 0 and " + (width - 1));
				continue;
			}
			
			//place the symbol to the first available row in the asked column
			for(int h = height - 1; h >= 0; h--) {
				if(grid[h][col] == '.') {
					grid[lastTop = h][lastCol = col] = symbol;
					return;
				}
			}
			
			//if column is full ==> we need to ask for a new input
			System.out.println("Column " + col + " is full.");
		} while(true);
	}
	
	public static void main(String[] args) {
		//we assemble all the pieces of the puzzle for building our Connect Four Game
		try (Scanner input = new Scanner(System.in)){
			//we define some variables for our game like dimensions and nb max of mover
			int height = 6; int width = 8; int moves = height * width;
			
			//we create the Connect Four instance
			connectfour_java board = new connectfour_java(width, height);
			
			//we explain users how to enter their choices
			System.out.println("Use 0-" + (width - 1) + " to choose a column");
			//we display initial board
			System.out.println(board);
			
			//we iterate until max nb moves be reached
			for(int player = 0; moves --> 0; player = 1 -player) {
				//simple trick to change player turn at each iteration
				
				//symbol for current player
				char symbol = PLAYERS[player];
				
				//ask user to choose a column
				board.chooseAndDrop(symbol, input);
				
				//display the board
				System.out.println(board);
				
				// check if a palyer won. If not, continue, otherwise display a message
				if(board.isWinningPlay()) {
					System.out.println("\nPlayer " + symbol + " wins!");
					return;
				}
			}
		}
	}
}
