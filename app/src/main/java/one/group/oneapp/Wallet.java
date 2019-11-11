package one.group.oneapp;

public class Wallet {
    private  int money;
    public Wallet(){
        this.money = 0;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void setMoney(int money) {this.money = money;}
}
