

public class Enemy extends Entity {

    // coordinages in level position
    double xpos;
    double ypos;
    //movement change
    double moveDx;
    double moveDy;


    double health;

    //Radius
    double width = 0.5;

    // distance to current camera position
    double distanceToCamera;

    double cameraWallDistanceFactor;

    // coordinates transformed by the camera view
    double xTransformed;
    double yTransformed;

    private Texture texture;

    //distance?
    double distance=0;
    double centerX;

    public Enemy(double xPosition, double yPosition, Texture texture) {
        this.xpos = xPosition;
        this.ypos = yPosition;
        this.texture = texture;
        this.health=100;



    }


    public double getDistFromLine(double x1, double y1, double x2, double y2) {

        double A = xpos - x1;
        double B = ypos- y1;
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

        double dx = xpos - xx;
        double dy = ypos - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }


    public double getSideFromLine(double x1, double y1, double x2, double y2) {

        double d = (xpos - x1) * (y2 - y1) - (ypos - y1) * (x2 - x1);
        return d;//Actual value worthless, only concerned with d being negative or postive Postive=Right side of sprite
    }


    public Texture getTexture() {
        return texture;
    }

    protected void persue() {
        System.out.println("1/60 in Java is "+ 1/60); moveDx=1; moveDy=1;
        System.out.println("1/60 in Java double is "+ (double)1/(double)60);
        if (Game.camera.xPos > this.xpos) { System.out.println("Line 88 MoveDx:" + moveDx);
            moveDx = Math.round(1 / speed);
            System.out.println("Line 90 MoveDx:" + moveDx);
        }else if (Game.camera.xPos < this.xpos) {
            moveDx = - Math.round((1 / speed));
            System.out.println("Line 93 MoveDx:" + moveDx);
        }else
            moveDx = 0;
        if (Game.camera.yPos > this.ypos) {
            moveDy = Math.round(1 / speed);
            System.out.println("Line 98 MoveDY:" +moveDy);
        }else if (Game.camera.yPos < this.ypos) {
            moveDy = -Math.round(1 / speed);
            System.out.println("Line 101 MoveDy:" +moveDy);
        }else
            moveDy = 0;
        lastPlayerX = Game.camera.xPos;
        lastPlayerY = Game.camera.yPos;
        //persuePath();

    }


    protected void persuePath(){

        if(lastPlayerX>this.xpos)
            moveDx=1/speed;
        else if(lastPlayerX<this.xpos)
            moveDx=-1/speed;
        else
            moveDx=0;
        if(lastPlayerY>this.ypos)
            moveDy=1/speed;
        else if(lastPlayerY<this.ypos)
            moveDy=-1/speed;
        else
            moveDy=0;

    }

    public void updateBehavior() {

        persue();

    }

    public void updatePlayer(int[][] map) {
        //xpos++;
        System.out.println("xpos:" +xpos);
        System.out.println("ypos:" +ypos);
        System.out.println("dx: " +dx);
        //Oh the problem with is dx
        System.out.println("xpos: "+ xpos+"moveDx"+moveDx+"="+" "+(xpos+moveDx));
        System.out.println("ypos: "+ ypos+"moveDy"+moveDy+"="+" "+(ypos+Math.round(moveDy)));
        System.out.println( );
        if(alive()){
            try{
            if(!collision( xpos+moveDx, ypos)){
                xpos+=moveDx;
            }
            }catch (Exception e){
                System.out.println("#1No bueno map out of bounds error");

                }
            try{
            if(!collision(xpos, ypos+moveDy)){
                ypos+=moveDy;
            }
            }catch (Exception e){
                System.out.println("#2No bueno map out of bounds error");

            }
        }else{
            moveDx = 0;
            moveDy = 0;
        }
    }

    public boolean alive() {
        boolean alive = true;
        if(health<=0)
            alive = false;
        return alive;
    }

    protected boolean collision(double x, double y){
        System.out.println("X:"+ x);
        System.out.println("Y:"+ y);
        System.out.println("X:(int)"+ (int)x);
        System.out.println("Y:(int)"+(int) y);
        System.out.println("X int math round)"+(int) Math.round(x));
        System.out.println("Y int math round)"+(int) Math.round(y));
        x=(int) Math.round(x);
        y=(int) Math.round(y);

        if( (Game.map[(int) x][(int) y] > 0)  ){

            return true;
        }else{
            return false;
        }
    }

    public void damaged(int damageTaken) {
        health-=damageTaken;
    }


}