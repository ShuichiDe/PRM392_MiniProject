package com.example.prm392_miniproject;

public class MoneyManager {
    private int playerMoney;

    public MoneyManager(int initialMoney) {
        this.playerMoney = initialMoney;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void addMoney(int amount) {
        this.playerMoney += amount;
    }

    public void subtractMoney(int amount) {
        this.playerMoney -= amount;
    }

    public boolean isGameOver() {
        return playerMoney <= 0;
    }
}
