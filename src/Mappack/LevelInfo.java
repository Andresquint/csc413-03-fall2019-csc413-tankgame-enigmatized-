package Mappack;

import Animation.Texture;
import Entity.Enemy;
import Entity.Entity;
import Entity.Powerup;
import Entity.BreakableWall;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelInfo {

    private List<Entity> entityList = new ArrayList<>();
    private List<Entity> actualenemiesList = new ArrayList<>();



    public LevelInfo() throws IOException {

        this.entityList.add(new Enemy(8, 8, new Texture("resources/terrr.png", 64, 64)));
        //Animation.Texture has to be 64
        //128 Works to?
        this.entityList.add(new Enemy(10, 10, new Texture("resources/terrr.png", 64, 64)));
        this.entityList.add(new Powerup(2, 2, new Texture("resources/rsz_powerup.png", 64, 64) ));
        this.entityList.add(new BreakableWall(3.5,3.5,new Texture("resources/BreakableWall.png", 64, 64) ));

        for(Entity possibleEnemy : entityList){
            if(possibleEnemy instanceof Enemy ) this.actualenemiesList.add(possibleEnemy);

        }

    }

    public List<Entity> getEnemies() {
        return entityList;
    }

    public List<Entity> getActualEnemies(){
        return actualenemiesList;
    }

    public Entity getEnemyFromEnemyList(int num) {
        return entityList.get(num);
    }

    public int getEnemiesListSize() {
        return entityList.size();
    }

    public void removeEntity(Entity removing){
        entityList.remove(removing);
    }

    public boolean hasInstanceOFBreakableWall(){
        return entityList.stream().anyMatch(c ->(c instanceof BreakableWall)  );
    }

    public boolean hasInstanceOFBreakableWallThatisAlive(){
        return (entityList.stream().anyMatch(c ->(c instanceof BreakableWall)&& c.health>=0));
    }




}
