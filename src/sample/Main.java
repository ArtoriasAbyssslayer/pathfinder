package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    private Barbas barbas;
    private int wins = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        double directionAngle = 0;
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));

        Board board = new Board(100);
        board.fillBoard();
        barbas = new Barbas(0, HEIGHT / 2, board);


        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();
        System.out.println(board);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                if (board.get((int) barbas.getX() * board.getColumns() / WIDTH, (int) barbas.getY() * board.getRows() / HEIGHT) == 0) {
                    reset();
                }
                if (board.get(barbas.getBoardX(), barbas.getBoardY()) == 2) {
                    wins++;
                    reset();
                }

                // background image clears canvas
                //gc.fillRect(25, 25, 100, 100);
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                for (int i = 0; i < board.getColumns(); i++) {
                    for (int j = 0; j < board.getRows(); j++) {
                        if (board.get(i, j) == 0) {
                            gc.setFill(Paint.valueOf("blue"));
                        } else {
                            gc.setFill(Paint.valueOf("green"));
                        }
                        gc.fillRect(i * WIDTH / board.getColumns() , HEIGHT / board.getRows() * j, WIDTH / board.getColumns(), HEIGHT / board.getRows());
                    }
                }
                barbas.draw(gc);
                gc.fillText("wins " + wins, 10,10);
                barbas.update();
            }
        }.start();

        primaryStage.show();
    }

    private void reset() {
        barbas.setX(0);
        barbas.setY(HEIGHT / 2);
        barbas.setDirectionAngle(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
