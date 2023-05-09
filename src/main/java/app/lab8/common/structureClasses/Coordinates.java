/**
 * The Coordinates class represents the coordinates of a ticket point.
 * It provides methods to access and modify the x and y coordinates.
 */

package app.lab8.common.structureClasses;

import java.io.Serializable;
import java.util.Objects;


public class Coordinates implements Serializable, Comparable<Coordinates> {
    /**
     * The x coordinate of the point.
     */

    private float x;

    /**
     * The y coordinate of the point.
     */

    private int y;

    public float getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Sets the x coordinate of the point.
     *
     * @param x the x coordinate of the point
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate of the point.
     *
     * @param y the y coordinate of the point
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate object:" + "\n" +
                "x coordinate: " + this.x + '\n' +
                "y coordinate: " + this.y;
    }

    @Override
    public int compareTo(Coordinates o) {
        int res = Float.compare(this.x, o.x);
        if (res == 0) {
            res = Integer.compare(this.y, o.y);
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates other = (Coordinates) o;
        return Float.compare(this.x, other.getX()) == 0 &&
                this.y == other.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
