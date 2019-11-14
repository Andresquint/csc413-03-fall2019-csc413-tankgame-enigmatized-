package Entity;
import Animation.Texture;
import Animation.TextureAnimation;
import RayCasting.MathAssist;

import java.io.IOException;


public class Entity implements Comparable<Entity> {
   // https://www.geeksforgeeks.org/check-line-touches-intersects-circle/

   // Camera camera;
    //HealthStats healthStats= new HealthStats();
    public double width;
    // Animation.Texture face = new Animation.Texture("res/enemy.png", 64);
    public double xPos;	//the x location of the sprite
    public double yPos; 	//the y location of the sprite
    protected Texture texture;
    public double distance=0;
    public double centerX;
    public double health=100;

    Texture[] stateTextures;
    TextureAnimation textureAnimation1;


   // double centerX;



    public Entity(double xpos, double ypos, Texture texture){
        this.xPos =xpos;
        this.yPos =ypos;
        this.width=0.5;
        this.texture=texture;

    }

    public Entity() {

    }


    public void updateBehavior(double delta){

    }

    public void damaged(int damageTaken) throws IOException {

        System.out.print("Entity.Enemy Health before: "+ this.health);
        this.health-=damageTaken;
        //this.health2.damaged(damageTaken);
        System.out.print("Entity.Enemy Health After Hit: "+ this.health);
        if(health<0) {
            texture=Texture.wood;
//            textureChange();
//            currentAnimation=dyingAnimation;
//            animation();
        }//What should the enemy do? Die?
    }


/*          Gets Health of Entity
            Primarily used in Game Class
            For rendering health bars of enemies
*/

    public double getHealth() {
        double result = ((double)health)/100;
        if(result <= 0){
            result = 0.01;//Returns 1 instead of zero because the way enemy health bar rendering is
        }               //An Out of Bounds error gets thrown if zero
        return result;
    }










//    public void checkCollision(int a, int b, int c,
//                               int x, int y, int radius)
//    {
//        // Finding the distance of line from center.
//        double dist = (Math.abs(a * x + b * y + c)) /
//                Math.sqrt(a * a + b * b);
//
//        // Checking if the distance is less than,
//        // greater than or equal to radius.
//        if (radius == dist)
//            System.out.println ( "Touch" );
//        else if (radius > dist)
//            System.out.println( "Intersect") ;
//        else
//            System.out.println( "Outside") ;
//    }



//    public int enemy(double x1, double y1, double x2, double y2){
//        double XdistanceFromEnemy=x1-xpos;
//        double ydistanceFromEnemy=y1-ypos;
//
//
//        //Somme form check if
//        //hits X
//        //andhits y
//        //
//        double widththiscircle = width* Math.sin(90);
//
//
//
//
//    }


    Arithmetic rayhitting = (float thisEnemyX, float thisEnemyY) -> (thisEnemyX + thisEnemyY);

    RayHitting rayhittingLambda = (int a, int b, int c, int x, int y, int radius) ->(int) ((Math.abs(a * x + b * y + c)) /  Math.sqrt(a * a + b * b));

//    public int checksRayHitting(Entity.RayHitting circletouching){
//        if (circletouching.rayhittingLambda( a, b,  c, xpos,  ypos, width) > width){
//            System.out.println("Touch");
//            return 1;
//              }
//        else if (radius > dist) {
//            System.out.println("Intersect");
//            return (circletouching.rayhittingLambda(int x1, int y1, int x2, int y2, int y, int width);
//
//        }
//        else
//            System.out.println( "Outside") ;
//    }

    public boolean alive() {
       //boolean alive = true;
//        if(health<=0)
//            alive = false;
        //return alive;
        return health>0;
    }

    public double getDistFromLine(double x1, double y1, double x2, double y2) {

        double A = xPos - x1;
        double B = yPos - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = -1;
        if (len_sq != 0) //in case of 0 length line
            param = dot / len_sq;

        double xx, yy;

        if (param < 0) {
            xx = x1;
            yy = y1;
        }
        else if (param > 1) {
            xx = x2;
            yy = y2;
        }
        else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }

        double dx = xPos - xx;
        double dy = yPos - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }


    public double getSideFromLine(double x1, double y1, double x2, double y2) {

        double d = (xPos - x1) * (y2 - y1) - (yPos - y1) * (x2 - x1);
        return d;//Actual value worthless, only concerned with d being negative or postive Postive=Right side of sprite
    }


    public Texture getTexture() {
        return texture;
    }

    @Override
    public int compareTo(Entity o) {
        if (distance > o.distance) {
            return 1;
        } else return -1;
    }

    protected void checkBullet(double xpos, double ypos, int damage, boolean playerBullet) {

        return;
    }

    public boolean collides(double xPos, double yPos) {
        return MathAssist.distanceBetweenPoints(xPos, yPos, this.xPos, this.yPos)<this.width*2;
    }

    public int getPixel(int u, int v){
        return texture.getPixel(u,v);
    }

}



interface Arithmetic {

    float rayhitting(float a, float b);
}


interface RayHitting {

    int rayhittingLambda(int a, int b, int c, int xcenterOfthisenemy, int ycenterOfthisenemy, int radius);
}