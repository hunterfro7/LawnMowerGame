package one.group.oneapp;

public abstract class upgradeStats
{
    public int getCost()
    {
        return -1;
    }

    public String getLockedText()
    {
        return ("Upgrade is locked!");
    }

    public int getLevel()
    {
        return -1;
    }

    public boolean buy()
    {
        return false;
    }
}
