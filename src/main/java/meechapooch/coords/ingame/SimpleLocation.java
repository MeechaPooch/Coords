package meechapooch.coords.ingame;

public class SimpleLocation {
    double x;
    double y;
    double z;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getBlockX() { return (int) getX(); }
    public int getBlockY() { return (int) getY(); }

    public SimpleLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getBlockZ() { return (int) getZ(); }
}
