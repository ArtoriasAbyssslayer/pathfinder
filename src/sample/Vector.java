package sample;

import java.util.Arrays;

public class Vector {

    double[] elements;

    public Vector(int size) {
        elements = new double[size];
    }


    @Override
    public String toString() {
        return "Vector{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }

    public double get(int i) {
        return elements[i];
    }

    public void set(int i, double element) {
        elements[i] = element;
    }

    public int getSize() {
        return elements.length;
    }
}
