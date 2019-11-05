package Mappack;

import Animation.Texture;
import Entity.Enemy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelInfo {

    private List<Enemy> enemiesList = new ArrayList<>();

    public LevelInfo() throws IOException {

        this.enemiesList.add(new Enemy(8, 8, new Texture("res/terrr.png", 64, 64)));
        //Animation.Texture has to be 64
        //128 Works to?
        this.enemiesList.add(new Enemy(10, 10, new Texture("res/terrr.png", 64, 64)));


    }

    public List<Enemy> getEnemies() {
        return enemiesList;
    }

    public Enemy getEnemyFromEnemyList(int num) {
        return enemiesList.get(num);
    }

    public int getEnemiesListSize() {
        return enemiesList.size();
    }

}
