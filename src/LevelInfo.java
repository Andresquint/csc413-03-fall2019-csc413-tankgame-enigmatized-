import java.util.ArrayList;
import java.util.List;

public class LevelInfo {

    private List<Enemy> enemiesList = new ArrayList<>();


    public List<Enemy> getEnemies() {
        return enemiesList;
    }

    LevelInfo(){

        this.enemiesList.add(new Enemy(8,8, Texture.stone));
        this.enemiesList.add(new Enemy(10,10, Texture.wood));


    }

}
