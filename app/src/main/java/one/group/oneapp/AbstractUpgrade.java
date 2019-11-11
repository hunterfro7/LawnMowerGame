package one.group.oneapp;

public abstract class AbstractUpgrade
{
    private int cost;
    private int level;
    private String upgradeName;
    private String upgradeDescription;
    private boolean locked;


    AbstractUpgrade(int costAmount, String nameOfUpgrade, String descriptionOfUpgrade) {
        cost = costAmount;
        level = 1;
        upgradeName = nameOfUpgrade;
        upgradeDescription = descriptionOfUpgrade;
        locked = true;
    }
    AbstractUpgrade(){

    }


    public int getCost()
    {
        return Math.round(cost * (level++) * (level++) + (20 * cost));
    }

//    public String getLockedText()  //Do we really need this method????
//    {
//        return ("Upgrade is locked!");
//    }

    public int getLevel()
    {
        return level;
    }

    public String buy(Wallet playerWallet)
    {
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
