package com.roshambo.Service;

/*
1. Arraylist of players
2. Picks

*/
enum Picks {
    ROCK,
    PAPER,
    SCISSORS
}

public class Roshambo {
    public String picker(int p) {
        Picks myPick =Picks.ROCK;
        switch (p) {
            case 1:
                myPick = Picks.PAPER;
                break;
            case 2:
                myPick = Picks.SCISSORS;
                break;
            default:
                if (p!=0)
                    System.out.println("Please enter valid number");
        }
        return myPick.name();
    }
}
