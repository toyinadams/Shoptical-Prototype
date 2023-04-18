package myStore;

import java.awt.*;

/**
 * The myStore.myStore.CustomColor class models a colour.
 * Each instance has specific colour represented as a string, Color object, and integer.
 *
 * @author  Toyin Adams
 * @version 1.0
 */
public class CustomColor {
    private String asString;
    private Color asColor;

    /**
     * Constructs a myStore.CustomColor represented as a specified String, Color instance, and integer.
     *
     * @param asString  String, name of color
     * @param asColor   Color object corresponding to color
     */
    public CustomColor(String asString, Color asColor) {
        this.asString = asString;
        this.asColor = asColor;
    }

    /**
     * Returns the name of the myStore.CustomColor.
     *
     * @return String, name of color
     */
    public String getAsString() {
        return asString;
    }

    /**
     * Returns the Color object corresponding to the myStore.CustomColor.
     *
     * @return Color object corresponding to color
     */
    public Color getAsColor() {
        return asColor;
    }
}
