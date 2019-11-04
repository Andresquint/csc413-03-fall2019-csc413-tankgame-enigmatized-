import java.lang.Math.*;




public class Entity {
   // https://www.geeksforgeeks.org/check-line-touches-intersects-circle/

    Camera camera;
    //HealthStats healthStats= new HealthStats();
    public double width;
    // Texture face = new Texture("res/enemy.png", 64);
    public double xpos;	//the x location of the sprite
    protected double dx;	//change in x location
    public double ypos; 	//the y location of the sprite
    protected double dy;	//change in y location
    protected double speed;//higher number means slower speed, 60 is 1 tile per second
    protected double lastPlayerX, lastPlayerY;

    Entity(double xpos, double ypos){
        this.xpos=xpos;
        this.ypos=ypos;
        this.width=0.5;
        this.speed=60;//higher number means slower speed, 60 is 1 tile per second
    }


    Entity(){}














    public void checkCollision(int a, int b, int c,
                               int x, int y, int radius)
    {
        // Finding the distance of line from center.
        double dist = (Math.abs(a * x + b * y + c)) /
                Math.sqrt(a * a + b * b);

        // Checking if the distance is less than,
        // greater than or equal to radius.
        if (radius == dist)
            System.out.println ( "Touch" );
        else if (radius > dist)
            System.out.println( "Intersect") ;
        else
            System.out.println( "Outside") ;
    }



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

//    public int checksRayHitting(RayHitting circletouching){
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


}

interface Arithmetic {

    float rayhitting(float a, float b);
}


interface RayHitting {

    int rayhittingLambda(int a, int b, int c, int xcenterOfthisenemy, int ycenterOfthisenemy, int radius);
}