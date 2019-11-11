package one.group.oneapp;

public abstract class Item {
    String name;
    int cost;
    int count;
    public Item(String name, int cost){
        this.name = name;
        this.cost = cost;
        this.count = 0;
    }
    public final void sell(Wallet wallet, int maxSell, int multiplier){
        int amountToSell = (this.cost > maxSell)?maxSell:this.count;
        wallet.addMoney(Math.round(this.count*amountToSell*multiplier));
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
