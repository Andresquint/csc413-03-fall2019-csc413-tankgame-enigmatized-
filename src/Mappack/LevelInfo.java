package Mappack;

import Animation.Texture;
import Entity.Enemy;
import Entity.Entity;
import Entity.Powerup;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelInfo {

    private List<Entity> enemiesList = new ArrayList<>();
    private List<Entity> actualenemiesList = new ArrayList<>();


    public LevelInfo() throws IOException {

        this.enemiesList.add(new Enemy(8, 8, new Texture("res/terrr.png", 64, 64)));
        //Animation.Texture has to be 64
        //128 Works to?
        this.enemiesList.add(new Enemy(10, 10, new Texture("res/terrr.png", 64, 64)));
        this.enemiesList.add(new Powerup(2, 2, new Texture("res/rsz_powerup.png", 64, 64) ));

        for(Entity possibleEnemy : enemiesList){
            if(possibleEnemy instanceof Enemy ) this.actualenemiesList.add(possibleEnemy);

        }

    }

    public List<Entity> getEnemies() {
        return enemiesList;
    }

    public List<Entity> getActualEnemies(){
        return actualenemiesList;
    }

    public Entity getEnemyFromEnemyList(int num) {
        return enemiesList.get(num);
    }

    public int getEnemiesListSize() {
        return enemiesList.size();
    }

    public void removeEntity(Entity removing){
        enemiesList.remove(removing);

    }

}
