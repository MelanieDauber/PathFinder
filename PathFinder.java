     /*
      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
      CODE WRITTEN BY OTHER STUDENTS. Melanie Dauber
      */

	/**
	 * Starter code for the Maze path finder problem.
	 */

package PathFinder.java;


import java.io.*;
import java.util.Scanner;
import java.util.ArrayDeque;

/*
 * Recursive class to represent a position in a path
 */
class Position{
	public int i;     //row
	public int j;     //column
	public char val;  //1, 0, or 'X'
	
	// reference to the previous position (parent) that leads to this position on a path
	Position parent;
	
	Position(int x, int y, char v){
		i=x; j = y; val=v;
	}
	
	Position(int x, int y, char v, Position p){
		i=x; j = y; val=v;
		parent=p;
	}
	
}


public class PathFinder {
	
	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.err.println("***Usage: java PathFinder maze_file");
			System.exit(-1);
		}
		
		char [][] maze;
		maze = readMaze(args[0]);
		printMaze(maze);
		Position [] path = stackSearch(maze);
		System.out.println("stackSearch Solution:");
		printPath(path);
		printMaze(maze);
		
		char [][] maze2 = readMaze(args[0]);
		path = queueSearch(maze2);
		System.out.println("queueSearch Solution:");
		printPath(path);
		printMaze(maze2);
	}
	
	
	public static Position [] stackSearch(char [] [] maze){
		// todo: your path finding algorithm here using the stack to manage search list
		// your algorithm should mark the path in the maze, and return array of Position 
		// objects coressponding to path, or null if no path found
		
		//creates a search list for positions yet to explore (INCLUDING 0,0)
		ArrayDeque<Position> RDirection = new ArrayDeque<Position>();
		Position[] backwards = new Position[10000];
		Position X = new Position(0, 0, '*');
		RDirection.push(X);
		
		
		//while the list is not empty
		while(!RDirection.isEmpty()) {
			
			//remove the next position from the search list
			Position Y = RDirection.pop();
			maze [Y.i][Y.j] = '*';
			
			
				//if at the exit position [n-1, n-1], a path is found!
				if (Y.i == maze.length - 1 && Y.j == maze.length - 1) {
					int end = 0;
					Position temp = Y.parent;
					maze[Y.i][Y.j] = 'X';
					backwards[end] = Y;
					
					while (temp != null){
						end++;
						maze[temp.i][temp.j] = 'X';
						backwards[end] = temp;
						temp = temp.parent;
					}
					maze[0][0] = 'X';
					return backwards;
				}
				
				
				if (Y.i < maze.length - 1 && maze[Y.i + 1][Y.j] == '0'){
					Position x0 = new Position(Y.i + 1, Y.j, '*', Y);
					RDirection.push(x0);
				}
				if (Y.j < maze.length - 1 && maze[Y.i][Y.j + 1] == '0'){
					Position y0 = new Position(Y.i, Y.j + 1, '*', Y);
					RDirection.push(y0);
				}
				if (Y.i > 0 && maze[Y.i - 1][Y.j] == '0'){
					Position x1 = new Position(Y.i - 1, Y.j, '*', Y);
					RDirection.push(x1);
				}
				if (Y.j > 0 && maze[Y.i][Y.j - 1] == '0'){
					Position y1 = new Position(Y.i, Y.j - 1, '*', Y);
					RDirection.push(y1);
				}
				
		}
			
		return null;
	}
	
	public static Position [] queueSearch(char [] [] maze){
		// todo: your path finding algorithm here using the queue to manage search list
		// your algorithm should mark the path in the maze, and return array of Position 
		// objects coressponding to path, or null if no path found
		
		//creates a search list for positions yet to explore (INCLUDING 0,0)
		ArrayDeque<Position> RDirection = new ArrayDeque<Position>();
		Position[] backwards = new Position[10000];
		Position X = new Position(0, 0, '*');
		RDirection.addLast(X);
		
		
		
		//while the list is not empty
		while(!RDirection.isEmpty()) {
			
			
			//remove the next position from the search list
			Position Y = RDirection.removeFirst();
			maze [Y.i][Y.j] = '*';
			
			
				//if at the exit position [n-1, n-1], a path is found!
				if (Y.i == maze.length - 1 && Y.j == maze.length - 1) {
					int i = 0;
					Position temp = Y.parent;
					maze[Y.i][Y.j] = 'X';
					backwards[i] = Y;
					
					
					while (temp != null){
						i++;
						maze[temp.i][temp.j] = 'X';
						backwards[i] = temp;
						temp = temp.parent;
					}
					maze[0][0] = 'X';
					return backwards;
				}
				
				
				if (Y.i < maze.length - 1 && maze[Y.i + 1][Y.j] == '0'){
					Position x0 = new Position(Y.i + 1, Y.j, '*', Y);
					RDirection.addLast(x0);
				}
				if (Y.j < maze.length - 1 && maze[Y.i][Y.j + 1] == '0'){
					Position y0 = new Position(Y.i, Y.j + 1, '*', Y);
					RDirection.addLast(y0);
				}
				if (Y.i > 0 && maze[Y.i - 1][Y.j] == '0'){
					Position x1 = new Position(Y.i - 1, Y.j, '*', Y);
					RDirection.addLast(x1);
				}
				if (Y.j > 0 && maze[Y.i][Y.j - 1] == '0'){
					Position y1 = new Position(Y.i, Y.j - 1, '*', Y);
					RDirection.addLast(y1);
				}
		}
		return null;
	}
	
	public static void printPath(Position [] path){
		// todo: print the path to the stdout
		if( path != null){
			System.out.println("Correct path: ");

		
			for( int X = path.length - 1; X >= 0; X--){
				if( path[X] != null){
					System.out.print("[" + path[X].i + "," + path[X].j + "]");
				}
			}	System.out.println();
		}else {
			System.out.println("No valid path.");
		}
	}
	
	/**
	 * Reads maze file in format:
	 * N  -- size of maze
	 * 0 1 0 1 0 1 -- space-separated 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static char [][] readMaze(String filename) throws IOException{
		char [][] maze;
		Scanner scanner;
		try{
			scanner = new Scanner(new FileInputStream(filename));
		}
		catch(IOException ex){
			System.err.println("*** Invalid filename: " + filename);
			return null;
		}
		
		int N = scanner.nextInt();
		scanner.nextLine();
		maze = new char[N][N];
		int i=0;
		while(i < N && scanner.hasNext()){
			String line =  scanner.nextLine();
			String [] tokens = line.split("\\s+");
			int j = 0;
			for (; j< tokens.length; j++){
				maze[i][j] = tokens[j].charAt(0);
			}
			if(j!=N){
				System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
				return null;
			}
			i++;
		}
		if(i!=N){
			System.err.println("*** Invalid file: has wrong number of rows: " + i);
			return null;
		}
		return maze;
	}
	
	public static void printMaze(char[][] maze){
		
		if(maze==null || maze[0] == null){
			System.err.println("*** Invalid maze array");
			return;
		}
		
		for(int i=0; i< maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				System.out.print(maze[i][j] + " ");	
			}
			System.out.println();
		}
		
		System.out.println();
	}

}

