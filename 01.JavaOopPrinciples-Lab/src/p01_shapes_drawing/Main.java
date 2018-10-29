package p01_shapes_drawing;

import p01_shapes_drawing.contracts.CustomQueue;
import p01_shapes_drawing.contracts.Drawable;
import p01_shapes_drawing.entities.custom_data_structures.CustomQueueImpl;
import p01_shapes_drawing.entities.shapes.Circle;
import p01_shapes_drawing.entities.shapes.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        CustomQueue<Integer> integerQueue = new CustomQueueImpl<>();

        for (int i = 0; i < 3; i++) {
            integerQueue.add(Integer.parseInt(reader.readLine()));
        }
        reader.close();

        Drawable circle = new Circle(integerQueue.pop());
        Drawable rect = new Rectangle(integerQueue.pop(), integerQueue.pop());

        circle.draw();
        rect.draw();
    }
}
