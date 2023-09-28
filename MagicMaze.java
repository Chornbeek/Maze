import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/*
 * 

 STARTING BOTTOM LEFT CORNER, X IS GOAL
  	*******@@@****X
	@****@@@@@@@***
	@*************1
 	*@@@@@@@*******
	1******@@@@@@@@
 	****@**********
 	****@****@*****
 	****@****@*****
 	****@****@*****
 	****@****@*****
 	*********@*****
 	
 	The digits such as ‘1’, ‘2’, ‘3’, etc… represent magical squares. These squares will
teleport you to other matching digit in the maze. For example, the ‘1’ in the above figure,
if walked on, will transport you to the other ‘1’. It will NOT transport you to the different
digit spots.
 	
 */


public class MagicMaze {
    public char[][] maze;     // 2D array to store the maze
    public String mazeName;  // Name of the maze (e.g., "maze1")
    public static int numRows;      // Number of rows in the maze
    public static int numCols;      // Number of columns in the maze
    
    public static final char WALL = '@';
    public static final char EXIT = 'X';
    public static final char VISITED = '.';
    public static final char PATH = '*';
    
  

    // Constructor to initialize the MagicMaze object
    public MagicMaze(String mazeName, int rows, int cols) throws IOException {
        numRows = rows;
        numCols = cols;
        maze = new char[numRows][numCols];
        
        
        
        readMazeFromFile(mazeName);
    }
    
    

    // Method to read the maze from a text file
    public void readMazeFromFile(String txt) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(txt));
        int rows = 0;
        int cols = 0;

        String line;
        while ((line = reader.readLine()) != null) {
            rows++;
            if (cols == 0) {
                // Assuming all lines have the same number of characters (columns)
                cols = line.length();
            }
        }
        
        
        reader.close();

        numRows = rows;
        numCols = cols;
        maze = new char[numRows][numCols];

        // Now, you can read the maze data and fill the maze array as before
        reader = new BufferedReader(new FileReader(txt));
        for (int i = 0; i < numRows; i++) {
            line = reader.readLine();
            for (int j = 0; j < numCols; j++) {
                maze[i][j] = line.charAt(j);
            }
        }
        reader.close();
    }
    
    void printSolution(char sol[][])
    {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++)
                System.out.print( " " + sol[i][j] + " ");
            System.out.println();
        }
    }
    
    

    // Method to solve the maze using backtracking
    public boolean solveMagicMaze() {
    	char sol[][] = new char[numRows][numCols];
    	
    	
    	if (solveMazeR(maze, numRows - 1, 0, sol) == false) {
			System.out.print("Solution doesn't exist");
			return false;
		}
		printSolution(sol);
		return true;
        
    }
    

    // Recursive backtracking function to explore maze paths
    public boolean solveMazeR(char[][] maze, int x, int y,  char[][] sol) {
    	
    	// if (x, y is goal) return true
        if (maze[x][y] == 'X') {
            sol[x][y] = '.';
            return true;
        }

        // Check if maze[x][y] is valid
        if (isSafe(maze, x, y) == true) {
            // Check if the current block is already part of solution path.
            if (sol[x][y] == '.')
                return false;
            
            

            // mark x, y as part of solution path
            sol[x][y] = '.';

            /* Move forward in x direction */
            if (x + 1 < numRows && solveMazeR(maze, x + 1, y, sol))
                return true;

            /* If moving in x direction doesn't give solution then
               Move down in y direction */
            if (y + 1 < numCols && solveMazeR(maze, x, y + 1, sol))
                return true;

            /* If moving in y direction doesn't give solution then
               Move backward in x direction */
            if (x - 1 >= 0 && solveMazeR(maze, x - 1, y, sol))
                return true;

            /* If moving in x direction backward doesn't give solution then
               Move upward in y direction */
            if (y - 1 >= 0 && solveMazeR(maze, x, y - 1, sol))
                return true;

            /* If none of the above movements works then
               BACKTRACK: unmark x,y as part of solution path */
            sol[x][y] = ' ';
            return false;
        }

        return false;
    }
    

    // Helper method to check if a position is valid and within bounds
    boolean isSafe( char[][] maze, int x, int y)
	{
		// if (x, y outside maze) return false
		return (x >= 0 && x < numRows && y >= 0 && y < numCols && maze[x][y] == '*');  //Maybe - 1
	}

    // Main method (for testing purposes)
    public static void main(String[] args) throws IOException {
    	 MagicMaze maze = new MagicMaze("maze1.txt", numRows, numCols);
         try {
             maze.readMazeFromFile("maze1.txt");
             boolean solved = maze.solveMagicMaze();
             if (solved) {
                 System.out.println("Maze solved!");
             } else {
                 System.out.println("Maze could not be solved.");
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    }


   








