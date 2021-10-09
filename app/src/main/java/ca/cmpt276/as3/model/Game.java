package ca.cmpt276.as3.model;

import java.util.Random;

public class Game {
    private final int NUM_ROWS;
    private final int NUM_COLS;
    private final int NUM_PUPPIES;

    private int numPuppiesFound;
    private int numOfScans;
    private Tile tiles[][];

    public Game(GameOption options)
    {
        this.NUM_ROWS = options.getNumRow();
        this.NUM_COLS = options.getNumCol();
        this.NUM_PUPPIES = options.getNumPuppy();
        numPuppiesFound = 0;
        numOfScans = 0;
        initializeTilesMatrix();
        generateRandomPuppiesLocation();
    }

    public Game(int NUM_ROWS, int NUM_COLS, int NUM_PUPPIES) {
        this.NUM_ROWS = NUM_ROWS;
        this.NUM_COLS = NUM_COLS;
        this.NUM_PUPPIES = NUM_PUPPIES;
        numPuppiesFound = 0;
        numOfScans = 0;
        initializeTilesMatrix();
        generateRandomPuppiesLocation();
    }

    private void initializeTilesMatrix(){
        tiles = new Tile[NUM_ROWS][NUM_COLS];
        for(int row=0; row < NUM_ROWS; row++)
        {
            for(int col=0; col < NUM_COLS; col++)
            {
                tiles[row][col] = new Tile();
            }
        }
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

    public boolean checkForHiddenPuppy(int row, int col){
        if(tiles[row][col].isContainPuppy() && !tiles[row][col].isPuppyRevealed()){
            numPuppiesFound++;
            tiles[row][col].setPuppyRevealed(true);
            return true;
        }
        return false;
    }

    public boolean isTileScanned(int row, int col)
    {
        return tiles[row][col].isScanned();
    }

    public int scanTile(int row, int col){
        numOfScans++;
        tiles[row][col].setScanned(true);

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

    public int getNumPuppiesFound()
    {
        return numPuppiesFound;
    }

    public int getNumOfScans()
    {
        return numOfScans;
    }
}
