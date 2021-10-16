package ca.cmpt276.as3.model;

import com.google.gson.Gson;

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
    private int[] config;

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

    public static GameOption getInstance(){
        if (instance == null){
            instance = new GameOption();
        }
        return instance;
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

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
        setConfig();
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
        setConfig();
    }

    public int getNumPuppy() {
        return numPuppy;
    }

    public void setNumPuppy(int numPuppy) {
        this.numPuppy = numPuppy;
        setConfig();
    }

    public void setHighScore(int newScore) {
        times_game_played[config[0]][config[1]]++;

        if(newScore < highScores[config[0]][config[1]]) {
            highScores[config[0]][config[1]] = newScore;
        }
    }

    public boolean isNewHighScore(int newScore)
    {
        return newScore < highScores[config[0]][config[1]];
    }

    public int getHighScore()
    {
        return highScores[config[0]][config[1]];
    }

    public int getTimesPlayed()
    {
        return times_game_played[config[0]][config[1]];
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
}
