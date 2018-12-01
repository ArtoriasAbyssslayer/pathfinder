package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Line {
    private double xstart, ystart, xend, yend;

    public Line(double xstart, double ystart, double xend, double yend) {
        this.xstart = xstart;
        this.ystart = ystart;
        this.xend = xend;
        this.yend = yend;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Paint.valueOf("black"));
        gc.strokeLine(xstart, ystart, xend, yend);
    }

    @Override
    public String toString() {
        return "Line{" +
                "xstart=" + xstart +
                ", ystart=" + ystart +
                ", xend=" + xend +
                ", yend=" + yend +
                '}';
    }

    public double getY(double x) {
        return ystart + ((yend - ystart) / (xend - xstart)) * (x - xstart);
    }

    public double getXstart() {
        return xstart;
    }

    public double getYstart() {
        return ystart;
    }

    public double getXend() {
        return xend;
    }

    public double getYend() {
        return yend;
    }
}
