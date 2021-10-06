package ca.cmpt276.as3.model;

import java.util.Random;

public class Game {
    private final int NUM_ROWS;
    private final int NUM_COLS;
    private final int NUM_PUPPIES;

    private int numPuppiesFound;
    private Tile tiles[][];

    public Game(int NUM_ROWS, int NUM_COLS, int NUM_PUPPIES) {
        this.NUM_ROWS = NUM_ROWS;
        this.NUM_COLS = NUM_COLS;
        this.NUM_PUPPIES = NUM_PUPPIES;
        numPuppiesFound = 0;
        tiles = new Tile[NUM_ROWS][NUM_COLS];
        generateRandomPuppiesLocation();
    }

    private void generateRandomPuppiesLocation() {
        Random rand = new Random();
        for(int i=1; i <= NUM_PUPPIES; i++) {
            //place puppy into a tile
            int randomRow = rand.nextInt(NUM_ROWS);
            int randomCol = rand.nextInt(NUM_COLS);
            //if there is already a puppy in the tile, loop once more
            if(tiles[randomRow][randomCol].isContainPuppy()){
                i--;
            }
            else {
                tiles[randomRow][randomCol].setContainPuppy(true);
            }
        }
    }

    public boolean checkForPuppy(int row, int col){
        if(tiles[row][col].isContainPuppy() && !tiles[row][col].isPuppyRevealed()){
            numPuppiesFound++;
            return true;
        }
        return false;
    }

    public int scanTile(int row, int col){
        int counter = 0;

        // count the puppy in the row
        for (int i = 0; i < NUM_COLS; i++){
            if(tiles[row][i].isContainPuppy() && !tiles[row][i].isPuppyRevealed()){
                counter++;
            }
        }
        // count the puppy in the column
        for (int i = 0; i < NUM_ROWS; i++){
            if(tiles[i][col].isContainPuppy() && !tiles[i][col].isPuppyRevealed()){
                counter++;
            }
        }
        return counter;
    }

}
