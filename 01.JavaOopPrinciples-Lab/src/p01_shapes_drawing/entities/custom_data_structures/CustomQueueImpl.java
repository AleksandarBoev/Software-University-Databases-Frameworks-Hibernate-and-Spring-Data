package p01_shapes_drawing.entities.custom_data_structures;

import p01_shapes_drawing.contracts.CustomQueue;

import java.util.ArrayList;
import java.util.List;

public class CustomQueueImpl<T> implements CustomQueue<T> {
    private List<T> dataContainer;

    public CustomQueueImpl() {
        this.dataContainer = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        this.dataContainer.add(element);
    }

    @Override
    public T pop() {
        if (!this.dataContainer.isEmpty()) {
            return this.dataContainer.remove(0);
        } else {
            return null; //or throw exception
        }
    }
}
