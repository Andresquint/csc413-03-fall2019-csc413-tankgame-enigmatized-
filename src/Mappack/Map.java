package Mappack;

public class Map {


    public int [][] map;

    public Map(int[][] map){
        this.map=map;
    }

    //Todo
    //Load map

    public boolean collision(int x, int y) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return false;
        }
        return map[x][y]!=0;
    }



}
