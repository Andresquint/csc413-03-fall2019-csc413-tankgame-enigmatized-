import java.util.ArrayList;
import java.util.List;

public class LevelInfo {

    private List<Enemy> enemiesList = new ArrayList<>();

    LevelInfo() {

        this.enemiesList.add(new Enemy(8, 8, Texture.stone));
        this.enemiesList.add(new Enemy(10, 10, Texture.wood));

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
