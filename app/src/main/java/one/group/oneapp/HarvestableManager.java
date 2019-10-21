package one.group.oneapp;

import java.util.ArrayList;

public class HarvestableManager {
    private ArrayList<Collidable> plants = new ArrayList<Collidable>();
    public HarvestableManager(){
        plants.add(new Grass(400,400));
        plants.add(new Grass(300,300));
        plants.add(new Grass(400,700));
        plants.add(new Grass(200,1200));
    }

    public void removePlant(Collidable plant){
        plants.remove(plant);
    }

    public ArrayList<Collidable> getPlants() {
        return (ArrayList<Collidable>) plants.clone();
    }
}
