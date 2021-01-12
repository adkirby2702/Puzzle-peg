//Alex Kirby
//kirbya4@winthrop.edu
import java.util.*;
class PuzzlePeg{
    
    private static final int[][] PossibleMoves = 
    {
        //literally a list of all of the possible moves that can be made
        {1,2,4},
        {1,3,6},
        {2,4,7},
        {2,5,9},
        {3,5,8},
        {3,6,10},
        {4,2,1},
        {4,5,6},
        {4,7,11},
        {4,8,13},
        {5,8,12},
        {5,9,14},
        {6,3,1},
        {6,5,4},
        {6,9,13},
        {6,10,15},
        {7,4,2},
        {7,8,9},
        {8,5,3},
        {8,9,10},
        {9,5,2},
        {9,8,7},
        {10,6,3},
        {10,9,8},
        {11,7,4},
        {11,12,13},
        {12,8,5},
        {12,13,14},
        {13,12,11},
        {13,8,4},
        {13,9,6},
        {13,14,15},
        {14,13,12},
        {14,9,5},
        {15,10,6},
        {15,14,13}
    };

    //declare global variables that each function can access and change
    //hold the jumps made
    private static List<String> jumpsMade;

    //hold the boards that come from those jumps
    private static List<char[]> boardsMade;

    private static final char peg = 'p';
    private static final char hole = 'x';//was going to do h for holes, but x looks better when outputting the board

    
    
    private static boolean puzzle( char[] board, int end){
        for (int[] trymove: PossibleMoves ){
            //if a match is found
            if (((((board[trymove[0]])==peg)) &&(board[trymove[1]] == peg) && (board[trymove[2]] == hole))){
                board[trymove[0]] = hole;//change the values of the positions involved in the jumping
                board[trymove[1]] = hole;
                board[trymove[2]] = peg;
                
                char[] copy = board.clone();//clone that board and keep it if the move is successful
                boardsMade.add(copy);
                //function calls itself
                if (puzzle(board,end)){//if it returns true, add that move to the successful move list
                    jumpsMade.add(trymove[0]+"-"+ trymove[2]);
                    return true;
                }
                else{
                    //move is not successful so delete the board
                    if (boardsMade.contains(copy)){
                        boardsMade.remove(copy);
                    }
                    //reset the values of the places in the board
                    board[trymove[0]] = peg;
                    board[trymove[1]] = peg;
                    board[trymove[2]] = hole;
                }
            }
        }
            //count the number of pegs in the board
        int count = 0;
        for (char piece : board){
            if(piece == peg){
                    count ++;
                }
            }
        //if there is only one peg left and there was no ending space,
        if((count == 1)&& (end == -10)){
           return true;
        }
        //if there is only one peg left and there was an ending space 
        else if ((count == 1)&&(board[end]==peg)){
            return true;
        }
        //if there was no solution
        else{
            return false;
        }
    }
    //function to print the board and hopefully make it easy to visualize the moves
    private static String printTheBoard( char[] board){
        StringBuilder triangle = new StringBuilder();
        triangle.append("       "+board[1]+"\n");
        triangle.append("      "+board[2]+ " "+board[3]+"\n");
        triangle.append("     "+board[4]+" "+ board[5]+" "+board[6]+"\n");
        triangle.append("    "+board[7]+" "+board[8]+ " "+board[9]+" "+board[10]+"\n");
        triangle.append("   "+board[11]+" "+board[12]+ " "+board[13]+" "+board[14]+" "+board[15]+"\n");
        return triangle.toString();
    }

    public static void main(String[] args){
        //I initialize the start and end variables, even though I define them later because the debugger really wanted me to because they might not be initialized
        int start=13;
        int end=-10;//set the end to -10 because that is nowhere near what an ending position could be
        //if there are zero arguments, then the program defaults to start at hole 13 and end anywhere
        if(args.length == 0){
            start = 13;
            end = -10;
        }
        //if there is one arguement, the program starts at the hole that is given and ends at any hole
        if (args.length == 1){
            start = Integer.parseInt(args[0]);
            end = -10;//set it to -10 because that is nowhere near what an ending position could be
        }
        //If there are 2 arguments, start at the first arguement and end at the 2nd
        if (args.length == 2){
                start = Integer.parseInt(args[0]);
                end = Integer.parseInt(args[1]);
        }
        //create the initial board of characters
        char[] Pegboard = new char[16];
        Pegboard[0] = ' ';
        //it is more convienent for the information to start at one, so set the array 1 bigger than needed and just dont use the [0] index
        for (int i=1;i < 16; i++){//fill the board
            if (start == i){//leave the starting hole empty
                Pegboard[i] = hole;
            }
            else{
                Pegboard[i] = peg;
            }
        }
        char[] original = Pegboard.clone();
        //list that holds the jumps that are made
        jumpsMade = new ArrayList<>();
        //list that holds the boards that are copied
        boardsMade = new ArrayList<>();
        
        int numofjumps=1;//track the number of jumps

        //if a solution is found
        if (puzzle(Pegboard, end)){
            //print out the first board
            System.out.println("Starting board");
            System.out.println(printTheBoard(original));
            //print out the jumps paired with the boards resulting from the jumps one by one
            //found out, through much confusion and starring at the debugger, that the jumps had to be itterated in reverse order, while the boards in ascending order
            for (int i = 0; (jumpsMade.size()-1)-i>=0; i++){
                System.out.println( "Jump "+numofjumps+": "+ jumpsMade.get((jumpsMade.size()-1)-i));
                System.out.println(printTheBoard(boardsMade.get(i)));
                numofjumps++;
            }
        }
        //the puzzle function returned false so there is no solution
        else{
            System.out.println("No solution was found");
        }

    }
}
