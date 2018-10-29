package p01_shapes_drawing.entities.shapes;

import p01_shapes_drawing.contracts.Drawable;

public class Rectangle implements Drawable {
    private static final String INVALID_PARAMETER_VALUE = "%s should be a positive number!";

    private Integer width;
    private Integer height;

    public Rectangle(Integer width, Integer height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void draw() { //copy pasted this code
        for (int i = 0; i < this.height; i++) {
            System.out.print("*");
            for (int j = 1; j < this.width; j++) {
                System.out.print(" ");
                if (i == 0 || i == (this.height - 1)){
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print(" ");
            System.out.print("*");
            System.out.print("\n");
        }
    }

    private void setWidth(Integer width) {
        if (width <= 0) {
            throw new IllegalArgumentException(String.format(INVALID_PARAMETER_VALUE, "Width"));
        } else {
            this.width = width;
        }
    }

    private void setHeight(Integer height) {
        if (height <= 0) {
            throw new IllegalArgumentException(String.format(INVALID_PARAMETER_VALUE, "Length"));
        } else {
            this.height = height;
        }
    }

}
