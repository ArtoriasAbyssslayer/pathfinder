package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Barbas {
    private static final int HIDDEN_UNITS = 3;
    private double x, y;
    private double directionAngle;
    private Line lineLeft, lineRight, lineCenter;
    private static final double lineLength = 50;
    private static final double angle = Math.PI / 4;
    public static final double SPEED = 0.3;

    public Matrix hidden, output;
    private Board board;

    public Barbas(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.directionAngle = 0;
        this.lineCenter = new Line(x, y, x + lineLength, y);
        this.lineLeft = new Line(x, y, x + lineLength * Math.cos(angle), y + lineLength * Math.sin(angle));
        this.lineRight = new Line(x, y, x + lineLength * Math.cos(-angle), y + lineLength * Math.sin(-angle));

        hidden = new Matrix(3, HIDDEN_UNITS);
        output = new Matrix(HIDDEN_UNITS, 1);
    }

    public Vector check() {
        Vector ret = new Vector(3);
        boolean collided = false;
        if (lineCenter.getXend() - lineCenter.getXstart() > 0) {
            for (double i = lineCenter.getXstart(); i < lineCenter.getXend(); i += 0.3) {
                if (lineCenter.getY(i) < 0 || board.get(getBoardX(i), getBoardY(lineCenter.getY(i))) == 0) {
                    ret.set(0, (i - lineCenter.getXstart()) / (lineCenter.getXend() - lineCenter.getXstart()));
                    collided = true;
                    break;
                }
            }
        } else {
            for (double i = lineCenter.getXstart(); i > lineCenter.getXend(); i -= 0.3) {
                if (lineCenter.getY(i) < 0 || board.get(getBoardX(i), getBoardY(lineCenter.getY(i))) == 0) {
                    ret.set(0, (i - lineCenter.getXstart()) / (lineCenter.getXend() - lineCenter.getXstart()));
                    collided = true;
                    break;
                }
            }
        }
        if (!collided) ret.set(0, 2);

        collided = false;
        if (lineLeft.getXend() - lineLeft.getXstart() > 0) {
            for (double i = lineLeft.getXstart(); i < lineLeft.getXend(); i += 0.3) {
                if (lineLeft.getY(i) < 0 || board.get(getBoardX(i), getBoardY(lineLeft.getY(i))) == 0) {
                    ret.set(1, (i - lineLeft.getXstart()) / (lineLeft.getXend() - lineLeft.getXstart()));
                    collided = true;
                    break;
                }
            }
        } else {
            for (double i = lineLeft.getXstart(); i > lineLeft.getXend(); i -= 0.3) {
                if (lineLeft.getY(i) < 0 || board.get(getBoardX(i), getBoardY(lineLeft.getY(i))) == 0) {
                    ret.set(1, (i - lineLeft.getXstart()) / (lineLeft.getXend() - lineLeft.getXstart()));
                    collided = true;
                    break;
                }
            }
        }
        if (!collided) ret.set(1, 2);

        collided = false;
        if (lineRight.getXend() - lineRight.getXstart() > 0) {
            for (double i = lineRight.getXstart(); i < lineRight.getXend(); i += 0.3) {
                if (lineRight.getY(i) < 0 || board.get(getBoardX(i), getBoardY(lineRight.getY(i))) == 0) {
                    ret.set(2, (i - lineRight.getXstart()) / (lineRight.getXend() - lineRight.getXstart()));
                    collided = true;
                    break;
                }
            }
        } else {
            for (double i = lineRight.getXstart(); i > lineRight.getXend(); i -= 0.3) {
                if (lineRight.getY(i) < 0 || board.get(getBoardX(i), getBoardY(lineRight.getY(i))) == 0) {
                    ret.set(2, (i - lineRight.getXstart()) / (lineRight.getXend() - lineRight.getXstart()));
                    collided = true;
                    break;
                }
            }
        }
        if (!collided) ret.set(2, 2);
        return ret;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Paint.valueOf("pink"));
        gc.fillOval(x - 5, y - 5, 10, 10);
        lineLeft.draw(gc);
        lineCenter.draw(gc);
        lineRight.draw(gc);
    }

    public void calculateAngle(Vector input) {
        directionAngle = output.multiply(hidden.multiply(input)).get(0);
    }

    public void update() {
        Vector vector = check();
        directionAngle += getDangle(vector);

        x += SPEED * Math.cos(directionAngle);
        y += SPEED * Math.sin(directionAngle);

        lineCenter = new Line(x, y, x + lineLength * Math.cos(directionAngle), y + lineLength * Math.sin(directionAngle));
        lineLeft = new Line(x, y, x + lineLength * Math.cos(angle + directionAngle), y + lineLength * Math.sin(angle + directionAngle));
        lineRight = new Line(x, y, x + lineLength * Math.cos(-angle + directionAngle), y + lineLength * Math.sin(-angle + directionAngle));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        lineCenter = new Line(x, y, x + lineLength * Math.cos(directionAngle), y + lineLength * Math.sin(directionAngle));
        lineLeft = new Line(x, y, x + lineLength * Math.cos(angle + directionAngle), y + lineLength * Math.sin(angle + directionAngle));
        lineRight = new Line(x, y, x + lineLength * Math.cos(-angle + directionAngle), y + lineLength * Math.sin(-angle + directionAngle));
    }

    public double getDangle(Vector senses) {
        double max = senses.get(0);
        int angle = 0;
        if (max < senses.get(1)) {
            max = senses.get(1);
            angle = 1;
        }
        if (max < senses.get(2)) {
            max = senses.get(2);
            angle = 2;
        }

        switch (angle) {
            case 0:
                return 0;
            case 1:
                return 0.3;
            case 2:
                return -0.3;
            default:
                System.out.println("Error in turning barbas");
                throw new RuntimeException("fuck");
        }
    }

    public int getBoardX() {
        return (int) (x * board.getColumns() / Main.WIDTH);
    }

    public int getBoardX(double x) {
        return (int) (x * board.getColumns() / Main.WIDTH);
    }

    public int getBoardY() {
        return (int) (y * board.getRows() / Main.HEIGHT);
    }

    public int getBoardY(double y) {
        return (int) (y * board.getRows() / Main.HEIGHT);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        lineCenter = new Line(x, y, x + lineLength * Math.cos(directionAngle), y + lineLength * Math.sin(directionAngle));
        lineLeft = new Line(x, y, x + lineLength * Math.cos(angle + directionAngle), y + lineLength * Math.sin(angle + directionAngle));
        lineRight = new Line(x, y, x + lineLength * Math.cos(-angle + directionAngle), y + lineLength * Math.sin(-angle + directionAngle));
    }

    public void setDirectionAngle(double directionAngle) {
        this.directionAngle = directionAngle;
        lineCenter = new Line(x, y, x + lineLength * Math.cos(directionAngle), y + lineLength * Math.sin(directionAngle));
        lineLeft = new Line(x, y, x + lineLength * Math.cos(angle + directionAngle), y + lineLength * Math.sin(angle + directionAngle));
        lineRight = new Line(x, y, x + lineLength * Math.cos(-angle + directionAngle), y + lineLength * Math.sin(-angle + directionAngle));
    }
}
