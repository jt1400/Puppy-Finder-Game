package ca.cmpt276.as3.model;

public class Tile {
    private boolean containPuppy;
    private boolean puppyRevealed;
    private boolean scanned;

    public Tile() {
        containPuppy = false;
        puppyRevealed = false;
        scanned = false;
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
}
