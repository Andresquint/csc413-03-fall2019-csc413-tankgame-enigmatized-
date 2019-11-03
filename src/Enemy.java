

public class Enemy extends Entity {

    // coordinages in level position
    double xpos;
    double ypos;

    //Radius
    double width = 0.5;

    // distance to current camera position
    double distanceToCamera;

    double cameraWallDistanceFactor;

    // coordinates transformed by the camera view
    double xTransformed;
    double yTransformed;

    private Texture texture;

    public Enemy(double xPosition, double yPosition, Texture texture) {
        this.xpos = xPosition;
        this.ypos = yPosition;
        this.texture = texture;


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




}