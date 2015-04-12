package com.quizwiz;

/**
 * Created by Mitesh on 4/5/2015.
 */
public class Game {
    private String player1;
    private String player2;
    private String winner;

    public Game() {}
    public Game(String player1)
    {
        this.player1 = player1;
        this.player2 = null;
        this.winner  = null;
    }

    public Game(String player1,String player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.winner  = null;
    }

    public String getPlayer1() {
        return player1;
    }
    public String getPlayer2() {
        return player2;
    }
    public String getWinner() {
        return winner;
    }

}
