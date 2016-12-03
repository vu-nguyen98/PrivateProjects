import java.util.Scanner;
public class SomeCode {

	//These variables are flexible and can be used by all methods
	static int row = 0;
	static int column = 0;
	static int playerTurn = 1;
	static int gameMode = 0;
	static int botwins = 0;
	static int playerwins = 0;
	static int botties = 0;
	static int Xwins = 0;
	static int Owins = 0;
	static int XOties = 0;

	public static void main(String[] args) {
		//Import scanner
		Scanner input = new Scanner(System.in);
		
		//These two booleans are important to warp the player around the game interface
		//I really wish I can somehow make it more simple, though
		boolean menu = true;
		boolean gameInterface = true;
		
		//The main game interface loop
		do {
		//The menu loop
		while (menu) {
			//Disabling main game interface loop just makes it work well
			//I don't want to know what happens if I do otherwise
			gameInterface = false;
			
			//Sweet Tic Tac Toe graphics
			System.out.println("==============================================");
			System.out.println("___ _ ____    ___ ____ ____    ___ ____ ____ ");
			System.out.println(" |  | |    __  |  |__| |    __  |  |  | |___");
			System.out.println(" |  | |___     |  |  | |___     |  |__| |___ ");
			System.out.println("==============================================");
			System.out.println();
			
			//Menu options and such
			System.out.println("Welcome to Tic Tac Toe! What would be your selection?");
			System.out.println("(1) Play against a computer (no A.I.)");
			System.out.println("(2) Play against a human (on same computer)");
			System.out.println("(3) Check out the stats");
			System.out.println("(4) Exit the game");
			System.out.print("Your selection: ");

			//Receive the selection from the user
			int menuSelection = input.nextInt();
			
			//Some fun switch statements, depending on what the user picked as options
			switch (menuSelection) {
			//If the selection was 1, initiate a bot game
			case 1: System.out.println("Let's start a bot game!");
			System.out.println("Please note that the bot has no intelligence whatsoever.");
			gameMode = 1;
			menu = false;
			break;
			
			//If the selection was 2, initiate a multiplayer game
			case 2: System.out.println("Let's play a multiplayer game!");
			System.out.println("Please note that you need a friend to play this with");
			gameMode = 2;
			menu = false;
			break;	
			
			//If the selection was 3, initiate the stats and such
			//Erases when the program closes, need a way to integrate save files
			//.txt file use, maybe?
			case 3: System.out.println();
			System.out.println("Let's check the stats");
			System.out.println("HUMAN VS. COMPUTER GAMES");
			System.out.println("Number of computer wins: " + botwins);
			System.out.println("Number of player wins: " + playerwins);
			System.out.println("Number of tie games: " + botties);
			System.out.println();
			System.out.println("HUMAN VS. HUMAN GAMES");
			System.out.println("Number of player X wins: " + Xwins);
			System.out.println("Number of player O wins: " + Owins);
			System.out.println("Number of tie games: " + XOties);
			System.out.println("Press enter to return to the main menu");
			pressEnterToContinue();
			break;
			
			//If the selection was 4, simply exit the game
			case 4: System.out.println("Okay. The game will exit...");
					System.exit(0);	
					break;
					
			//If the user inputs anything but 1,2,3,4, oh no, nuke itself
			default: System.out.println("The program will now nuke itself in rage");
					 System.out.println("Because you're unable to follow instructions");
					 System.exit(0);
					 break;
					
			}
		}

		//Creating 2D array to contain the tic tac toe board
		String[][] board = createBoard(3,3);

		//As long as this is true, the game goes on
		boolean gameOn = true;
		while (gameOn) {
			//display the board
			displayBoard(board);

			//A boolean to determine if the space is empty or not
			boolean emptyspace;
			String option;

			//A do..while loop
			do {
				
				//If playing with bot, and the bot's turn, make it appear so that it's thinking...
				if (gameMode == 1 && playerTurn == 2) {
					System.out.println("The computer will try and make its choice...");
				}
				//2 custom methods to select the row and column
				row = rowSelection();
				column = columnSelection();

				//Assign the row and column selections to the board
				String position = board[row][column];

				//If the position contains nothing, it is empty space
				if (position.equals("|   |")) {
					emptyspace = true;
				}
				
				//If not empty tell user to insert it again
				else {
					emptyspace = false;
					if (gameMode == 2 || (gameMode == 1 && playerTurn==1)) {
					System.out.println("That position has been chosen. Try again."); }
				}
			} while (emptyspace==false);


			//If everything is good and clear then assign the selections to the board
			boardInsertion(board);

			//Runs every single loop to check for winners, if there is winner
			if (checkWinner(board)) {
				//Display the winning board
				displayBoard(board);
				
				//String to determine what player it was.
				String player;
				
				//Switch statement evaluating what game mode it is
				switch (gameMode) {
				//If multiplayer game mode (gameMode 2)
				case 2: 
						//If playerTurn is 1, it is X player, else it's O player
						//Also adds wins according to the player
						if (playerTurn == 1) {
							player = "X";
							Xwins++;
						}
						else {
							player = "O";
							Owins++;
						}
						afterGameInterface(gameOn, gameInterface);
						//Interface to determine who was the winner
						System.out.println("Player " + player + " has won.");
						
						//Asking if the user wants to play the game again
						System.out.print("Do you want to play again? y/n: ");
						
						//Records choice into string
						option = input.next();
						
						//Boolean depending on whether or not it was y, ignoring case
						gameOn = (option.equalsIgnoreCase("y"));
						
						//If they don't want to play ask if they want to return to menu
						if (gameOn == false) {
							System.out.println("Do you want to return to the main menu? y/n");
							
							//Use string to contain option again
							option = input.next();
							
							//Like above
							gameInterface = (option.equalsIgnoreCase("y"));
							}
						break;
				case 1: if (playerTurn==1) {
							System.out.println("Congratulations, you have won!");
							playerwins++;
						}
						else {
							System.out.println("The computer won!");
							botwins++;
						}
						System.out.print("Do you want to play again? y/n: ");
						option = input.next();
						gameOn = (option.equalsIgnoreCase("y"));
						if (gameOn == false) {
							System.out.println("Do you want to return to the main menu? y/n");
							option = input.next();
							gameInterface = (option.equalsIgnoreCase("y"));
							}
				}
				//initiate board again
				board = createBoard(3, 3);
			}

			//checking draw (comments are same as checking winners)
			if (checkDraw(board)) {
				displayBoard(board);
				System.out.println("Tie game!");
				if (gameMode == 1) botties++;
				else XOties++;
				System.out.print("Do you want to play again? y/n: ");
				option = input.next();
				gameOn = (option.equalsIgnoreCase("y"));
				if (gameOn == false) {
					System.out.println("Do you want to return to the main menu? y/n");
					option = input.next();
					gameInterface = (option.equalsIgnoreCase("y"));
					}
				board = createBoard(3, 3);
			}

			//alternate turns
			if (playerTurn == 1) playerTurn = 2;
			else playerTurn = 1;
		}
		//boohoo
		System.out.println("Game over!");
		if (gameInterface==true) {
			System.out.println("Returning back to the menu...");
			gameOn = false;
			menu = true;
		}

		} while (gameInterface = true);
	}


	private static void afterGameInterface(boolean gameOn, boolean gameInterface) {
		// TODO Auto-generated method stub
		
	}


	private static void boardInsertion(String[][] board) {
		//insert depending on player
		String selection;
		if (playerTurn == 1) {
			selection = "| X |";
		}
		else selection = "| O |";

		board[row][column] = selection;

	}

	private static int columnSelection() {
		//custom method to select column
		Scanner input = new Scanner(System.in);
		int column = 0;
		boolean validSelection = false;

		while (validSelection == false) {
			switch (gameMode) {
			case 2: if (playerTurn==1) {
						System.out.print("Enter the column for player X: ");
					}
					else {
						System.out.print("Enter the column for player O: ");
					}
					column = input.nextInt();
					if (column >=0 && column <=2) validSelection = true;
					else System.out.println("Invalid input!");
		
					break;
			case 1: if (playerTurn==1) {
						System.out.print("Please enter the column you wish to choose: ");
						column = input.nextInt();
						if (column >=0 && column <=2) validSelection = true;
						else System.out.println("Invalid input!");
					}
					else {
						column = (int) (Math.random() * (3 - 0) + 0);
						validSelection = true;
					}
					break;
			}
		}
		return column;
	}

	private static int rowSelection() {
		//custom method to select row
		Scanner input = new Scanner(System.in);
		int row = 0;
		boolean validSelection = false;

		while (validSelection == false) {
			switch (gameMode) {
			case 2: if (playerTurn==1) {
						System.out.print("Enter the row for player X: ");
					}
					else {
						System.out.print("Enter the row for player O: ");
					}
					row = input.nextInt();
					if (row >=0 && row <=2) validSelection = true;
					else System.out.println("Invalid input!");
					break;
			case 1: if (playerTurn==1) {
						System.out.print("Please enter the row you wish to choose: ");
						row = input.nextInt();
						if (row >=0 && row <=2) validSelection = true;
						else System.out.println("Invalid input!");
					}
					else {
						row = (int) (Math.random() * (3 - 0) + 0);
						validSelection = true;
					}
					break;
			}
		}
		return row;
	}

	private static void displayBoard(String[][] board) {
		//print the board for users to see
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}

	}

	private static String[][] createBoard(int x, int y) {
		//initialize board
		String[][] board = new String[x][y];

		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board[i].length; j++) {

				board[i][j] = "|   |";
			}
		}
		return board;
	}

	private static boolean checkWinner(String[][] board) {
		//check winner
		String token;
		if (playerTurn == 1) {
			token = "| X |";
		}
		else token = "| O |";
		return (checkColumn(board, token) || checkRow(board,token) || checkDiagonal(board, token));

	}

	private static boolean checkRow(String[][] board, String token) {
		//sweep by row
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == token) count++;
			}
			if (count == 3) return true;
			else count = 0;
		}

		return false;

	}

	private static boolean checkColumn(String[][] board, String token) {
		//sweep by column
		int count = 0;
		for (int j = 0; j < board[0].length; j++) {
			for (int i = 0; i < board.length; i++) {
				if (board[i][j] == token) count++;
			}
			if (count == 3) return true;
			else count = 0;
		}

		return false;
	}

	private static boolean checkDiagonal(String[][] board, String token) {
		//sweep by diagonal, from top left
		int count = 0;
		int x = 0;
		int y = 0;
		while (x < board.length && y < board.length) {

			if (board[y][x] == token) count++;
			if (count == 3) return true;
			x++;
			y++;
		}
		//rest
		count = 0;
		//sweep by diagonal, from top right
		x = 0;
		y = board.length - 1;
		while (x < board.length && y >= 0) {

			if (board[y][x] == token) count++;
			if (count == 3) return true;
			y--;
			x++;
		}


		return false;

	}
	
	private static boolean checkDraw(String[][] board) {
		int count = 0;
		//count + 1 if board is occupied by either x or o
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board[i].length; j++) {
				if (board[i][j] == "| X |" || board[i][j] == "| O |") {
					count++;
				}
			}
		}
		if (count==9) return true;
		else return false;
	}
	
	private static void pressEnterToContinue() { 
		try
		{
			System.in.read();
		}  
		catch(Exception e)
		{}  
	}
}