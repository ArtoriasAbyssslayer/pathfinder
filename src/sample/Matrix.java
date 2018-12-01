package sample;

public class Matrix {

    double[][] elements;

    public Matrix(int rows, int cols) {
        elements = new double[rows][cols];
    }

    public Vector multiply(Vector vector) {
        Vector ret = new Vector(getRows());
        for (int i = 0; i < ret.getSize(); i++) {
            int sum = 0;
            for (int j = 0; j < getColumns(); j++) {
                sum += vector.get(j) * get(i, j);
            }
            ret.set(i, sum);
        }
        return ret;
    }

    public double get(int i, int j) {
        return elements[i][j];
    }

    public void set(int i, int j, double element) {
        elements[i][j] = element;
    }

    public int getRows() {
        return elements.length;
    }

    public int getColumns() {
        return elements[0].length;
    }
}
