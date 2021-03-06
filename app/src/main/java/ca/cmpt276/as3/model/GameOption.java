package ca.cmpt276.as3.model;

import com.google.gson.Gson;

/**
 * GameOption is a singleton class that determines the number of rows, columns, and puppies a game will have.
 * GameOption class stores these configuration values (rows, columns, puppies) for the game board, and when an instance of GameOption is passed onto the Game constructor, a game will be constructed based on these value.
 * This class also stores number of games played and the highest score for each configuration.
 */
public class GameOption {
    public static final int BOARD_SIZE_4X6 = 0;
    public static final int BOARD_SIZE_5X10 = 1;
    public static final int BOARD_SIZE_6X15 = 2;
    public static final int NUM_PUPPIES_6 = 0;
    public static final int NUM_PUPPIES_10 = 1;
    public static final int NUM_PUPPIES_15 = 2;
    public static final int NUM_PUPPIES_20 = 3;
    private int numRow;
    private int numCol;
    private int numPuppy;
    private static GameOption instance;
    private int[][] highScores;
    private int[][] times_game_played;
    private final int[] config;

    private GameOption(){
        numRow = 4;
        numCol = 6;
        numPuppy = 6;

        highScores = new int[3][4];
        times_game_played = new int[3][4];
        resetScores();

        config = new int[2];
        setConfig();
    }

    public String convertHighScoresToJson()
    {
        Gson gson = new Gson();
        return gson.toJson(highScores);
    }

    public String convertTimesGamePlayedToJson()
    {
        Gson gson = new Gson();
        return gson.toJson(times_game_played);
    }

    public void convertHighScoresFromJson(String highScoresJson)
    {
        if(highScoresJson != null)
        {
            Gson gson = new Gson();
            highScores = gson.fromJson(highScoresJson, int[][].class);
        }
    }

    public void convertTimesPlayedFromJson(String timesPlayedJson)
    {
        if(timesPlayedJson != null) {
            Gson gson = new Gson();
            times_game_played = gson.fromJson(timesPlayedJson, int[][].class);
        }
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

    public int getNumCol() {
        return numCol;
    }

    public int getNumPuppy() {
        return numPuppy;
    }

    public int getHighScore()
    {
        return highScores[config[0]][config[1]];
    }

    public int getTimesPlayed()
    {
        return times_game_played[config[0]][config[1]];
    }

    public void incrementTimesGamePlayed()
    {
        times_game_played[config[0]][config[1]]++;
    }

    public boolean isNewHighScore(int newScore)
    {
        return newScore < highScores[config[0]][config[1]];
    }

    public void resetScores()
    {
        for(int row=0; row < 3; row++)
        {
            for(int col=0; col < 4; col++)
            {
                highScores[row][col] = 91;
            }
        }

        for(int row=0; row < 3; row++)
        {
            for(int col=0; col < 4; col++)
            {
                times_game_played[row][col] = 0;
            }
        }
    }

    public void setNumPuppy(int numPuppy) {
        this.numPuppy = numPuppy;
        setConfig();
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
        setConfig();
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
        setConfig();
    }

    public void setHighScore(int newScore) {
        if(newScore < highScores[config[0]][config[1]]) {
            highScores[config[0]][config[1]] = newScore;
        }
    }

    private void setConfig() {
        switch (numRow){
            case 4:
                config[0] = BOARD_SIZE_4X6;
                break;
            case 5:
                config[0] = BOARD_SIZE_5X10;
                break;
            case 6:
                config[0] = BOARD_SIZE_6X15;
                break;

        }
        switch (numPuppy){
            case 6:
                config[1] = NUM_PUPPIES_6;
                break;
            case 10:
                config[1] = NUM_PUPPIES_10;
                break;
            case 15:
                config[1] = NUM_PUPPIES_15;
                break;
            case 20:
                config[1] = NUM_PUPPIES_20;
                break;

        }
    }
}
