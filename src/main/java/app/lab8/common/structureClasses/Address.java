/**
 * The Address class represents an address that consists of a street name.
 * It provides methods to access and modify the street name.
 */
package app.lab8.common.structureClasses;


import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    /**
     * The street name of the address.
     */

    private String street;

    /**
     * Returns the street name of the address.
     *
     * @return the street name of the address
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street name of the address.
     *
     * @param street the street name of the address
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns a string representation of the Address object.
     *
     * @return a string representation of the Address object
     */
    @Override
    public String toString() {
        return "Address object: " + "\n" + "street : " + street + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Address)) return false;
        Address other = (Address) o;
        return Objects.equals(this.street, other.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.street);
    }

}
