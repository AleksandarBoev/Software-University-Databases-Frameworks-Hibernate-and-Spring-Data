package p01_shapes_drawing.entities.shapes;

import p01_shapes_drawing.contracts.Drawable;

public class Circle implements Drawable {
    private static final String INVALID_RADIUS_MESSAGE = "Radius should be a positive number!";

    private Integer radius;

    public Circle(Integer radius) {
        this.setRadius(radius);
    }

    private void setRadius(Integer radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException(INVALID_RADIUS_MESSAGE);
        } else {
            this.radius = radius;
        }
    }

    @Override
    public void draw() { //copy pasted this code
        double r_in = this.radius - 0.4;
        double r_out = this.radius + 0.4;

        for (double y = this.radius; y >= -this.radius; --y) {
            for (double x = -this.radius; x < r_out; x += 0.5) {
                double value = x * x + y * y;
                if (value >= r_in * r_in && value <= r_out * r_out) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }
}
