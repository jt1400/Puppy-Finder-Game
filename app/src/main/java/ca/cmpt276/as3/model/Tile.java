package ca.cmpt276.as3.model;

/**
 * Tile class represents a tile (or a cell) inside the game board.
 * Tile class contains information about a tile: whether a tile contains a hidden puppy, whether puppy have been revealed, and if the tile itself has been scanned.
 * This class also stores the scan value of how many hidden puppies are in its row and column.
 */
public class Tile {
    private boolean containPuppy;
    private boolean puppyRevealed;
    private boolean scanned;
    private int scanValue;

    public Tile() {
        containPuppy = false;
        puppyRevealed = false;
        scanned = false;
        scanValue = -1;
    }

    public boolean isContainPuppy() {
        return containPuppy;
    }

    public void setContainPuppy(boolean containPuppy) {
        this.containPuppy = containPuppy;
    }

    public boolean isPuppyRevealed() {
        return puppyRevealed;
    }

    public void setPuppyRevealed(boolean puppyRevealed) {
        this.puppyRevealed = puppyRevealed;
    }

    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    public void decrementScanValue()
    {
        scanValue--;
    }

    public void setScanValue(int num)
    {
        scanValue = num;
    }

    public int getScanValue()
    {
        return scanValue;
    }
}
