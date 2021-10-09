package ca.cmpt276.as3.model;

public class GameOption {
    private int numRow;
    private int numCol;
    private int numPuppy;
    private static GameOption instance;

    private GameOption(){
        numRow = 4;
        numCol = 6;
        numPuppy = 6;
    }

    public static GameOption getInstance(){
        if (instance == null){
            instance = new GameOption();
        }
        return instance;
    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getNumPuppy() {
        return numPuppy;
    }

    public void setNumPuppy(int numPuppy) {
        this.numPuppy = numPuppy;
    }
}
