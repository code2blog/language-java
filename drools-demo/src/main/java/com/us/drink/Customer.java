package com.us.drink;

/**
 * Created by yangyibo on 16/12/13.
 *
 * Drinking customers
 */
public class Customer {
    private String name;
    private int money;
    private int drinkSum;
    private int blankCup;
    private int cap;

    public Customer(String name, int money) {
        super();
        this.name = name;
        this.money = money;
        this.drinkSum = 0;
        this.blankCup = 0;
        this.cap = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getDrinkSum() {
        return drinkSum;
    }

    public void setDrinkSum(int drinkSum) {
        this.drinkSum = drinkSum;
    }

    public int getBlankCup() {
        return blankCup;
    }

    public void setBlankCup(int blankCup) {
        this.blankCup = blankCup;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    @Override
    public String toString() {

        return  this.name + " is left with "
                +this.money + " Yuan, after drinking "+this.drinkSum+" bottle/s of wine, leaving him/her with "
                +this.getBlankCup()+" empty bottle/s and "
                +this.getCap()+" lid/s";
    }
}
