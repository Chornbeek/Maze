/*
 * 	Maze 1:
  	*******@@@****X
	@****@@@@@@@***
	@*************1
 	*@@@@@@@*******
	1*******@@@@@@@
 	****@**********
 	****@****@*****
 	****@****@*****
 	****@****@*****
 	****@****@*****
 	*********@*****

  	Maze 2:
   	2***@8**X@*****
	****@@@@@@@@@@@
	*3**@3**4@****5
	@@@@@@@@@@@@@@@
	****@***6@*****
	****@5***@****7
	6**7@***4@**8**
	@@@@@@@@@@@@@@@
	***1@****@****1
	****@****@*****
	****@****@2****
 	
 	The digits such as ‘1’, ‘2’, ‘3’, etc… represent magical squares. These squares will
teleport you to other matching digit in the maze. For example, the ‘1’ in the above figure,
if walked on, will transport you to the other ‘1’. It will NOT transport you to the different
digit spots.
 	
 */

public class MagicMaze {
    public char[][] maze;     // 2D array to store the maze
    public String mazeName;  // Name of the maze (e.g., "maze1")
    public int numRows;      // Number of rows in the maze
    public int numCols;      // Number of columns in the maze
    Map<Character, int[]> numberMap = new HashMap<>();
    

    // Constructor to initialize the MagicMaze object
    public MagicMaze(String mazeName, int rows, int cols) throws IOException {
        numRows = rows;
        numCols = cols;
        maze = new char[numRows][numCols];
        
        
        readMazeFromFile(mazeName);
        createNumberMap();
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

        // you can read the maze data and fill the maze array as before
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
    
    
    private void createNumberMap() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char currentChar = maze[i][j];
                if (Character.isDigit(currentChar)) {
                    // Convert the digit to its integer value
                    int digit = Character.getNumericValue(currentChar);

                    // Check if the digit is already in the map
                    if (numberMap.containsKey(currentChar)) {
                        // Found two identical numbers; print their coordinates
                        int[] coordinates = numberMap.get(currentChar);
                        System.out.println("Identical numbers " + currentChar + " found at coordinates (" + coordinates[0] + ", " + coordinates[1] + ") and (" + i + ", " + j + ")");
                        
                    } else {
                        // Store the coordinates of the current digit in the map
                        numberMap.put(currentChar, new int[]{i, j});
                    }
            }
        }
    }
    }

    // Recursive backtracking function to explore maze paths
    public boolean solveMazeR(char[][] maze, int x, int y,  char[][] sol) {
    	//System.out.println("Current Position: (" + x + ", " + y + ")");
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
            
            if (Character.isDigit(maze[x][y])) {
                char num = maze[x][y];
                if (numberMap.containsKey(num)) {
                    int[] teleport = numberMap.get(num);
                    int newX = teleport[0];
                    int newY = teleport[1];
                    System.out.println("Teleporting from (" + x + ", " + y + ") to (" + newX + ", " + newY + ")");
                    x = newX;
                    y = newY;
                    
                }
            }
            
            
            
            
            // mark x, y as part of solution path
            sol[x][y] = '.';
            

           
            if (x + 1 < numRows && solveMazeR(maze, x + 1, y, sol))
                return true;

            
            if (y + 1 < numCols && solveMazeR(maze, x, y + 1, sol))
                return true;

            
            if (x - 1 >= 0 && solveMazeR(maze, x - 1, y, sol))
                return true;

            
            if (y - 1 >= 0 && solveMazeR(maze, x, y - 1, sol))
                return true;

           
            sol[x][y] = ' ';
            return false;
        }
        
        return false;
    }
    

    // Helper method to check if a position is valid and within bounds
    boolean isSafe( char[][] maze, int x, int y)
	{
		// if (x, y outside maze) return false
		return (x >= 0 && x < numRows && y >= 0 && y < numCols && maze[x][y] != '@');  //Maybe - 1
	}

    
    public static void main(String[] args) throws IOException {
    	 MagicMaze maze = new MagicMaze("maze2.txt", 11, 15);
    	 
         try {
             maze.readMazeFromFile("maze2.txt");
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







