package one.group.oneapp;

import java.io.Serializable;

public abstract class Item  implements Serializable {
    String name;
    int cost;
    int count;
    public Item(String name, int cost){
        this.name = name;
        this.cost = cost;
        this.count = 0;
    }
    public final void sell(Wallet wallet, int maxSell, int multiplier){
        int amountToSell = (this.count > maxSell)?maxSell:this.count;
        wallet.addMoney(Math.round(this.cost*amountToSell*multiplier));
        this.count -= amountToSell;
    }

    public int getCount() {
        return count;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
    public void increaseCount(int amount){
        count += amount;
    }
}
