public class Torpedo {

    private float angle;
    private float[] location;

    public Torpedo(float[] location, float angle){
        new Torpedo(location[0], location[1], angle);
    }

    public Torpedo(float x, float y, float angle){
        location = new float[]{x,y};
        this.angle = angle;
    }
}
