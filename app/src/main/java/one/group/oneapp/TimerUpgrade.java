package one.group.oneapp;

public class TimerUpgrade extends AbstractUpgrade {

    private int cost;
    private int level;
    private String upgradeName;
    private String upgradeDescription;
    private boolean locked;


    TimerUpgrade(int costAmount, String nameOfUpgrade, String descriptionOfUpgrade) {
        cost = costAmount;
        level = 1;
        upgradeName = nameOfUpgrade;
        upgradeDescription = descriptionOfUpgrade;
        locked = true;
    }

    public String buy(Wallet playerWallet){
        if (locked = false)
        {
            int price = getCost();

            if (playerWallet.getMoney() > price)
            {
                playerWallet.setMoney(playerWallet.getMoney() - price);
                ++level;

                return "Upgrade Purchased";
            }
            else
            {
                return "Broke boy";
            }

        }

        return upgradeName + " Upgrade is locked";
    }
}
