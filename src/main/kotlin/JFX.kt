package moynes.spacesim

import kotlinx.coroutines.experimental.*

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import moynes.spacesim.sim.*

fun main(args: Array<String>){
  Application.launch(JFX::class.java, *args)
}

val SCREEN_WIDTH = 1200.0
val SCREEN_HEIGHT = 600.0
var rect1 = Rectangle(100.0,100.0,Color.RED);
var rect2 = Rectangle(100.0,100.0,Color.BLUE);
val shipX = 70.0
val shipY = 70.0
var ship1 = Ship(position=Vector2d(shipX,shipY), acceleration=Vector2d(oneG,0.0))
var ship2 = Ship(position=Vector2d(shipX,shipY+200), acceleration=Vector2d(oneG*2,0.0))

public class JFX : Application() {
  var counter = 0


  override fun start(stage: Stage) {
    stage.setTitle("Hello World")

    var grid = GridPane()
    grid.setHgap(0.0)
    grid.setVgap(0.0)

    var root = StackPane()
    root.getChildren().add(grid)
    //stage.setScene(Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK))
    var lineGrid = LineGrid()
    stage.setScene(Scene(lineGrid, Color.BLACK))
    stage.show()

    rect1.relocate(shipX,shipY)
    rect2.relocate(shipX,shipY+200)
    lineGrid.getChildren().add(rect1)
    lineGrid.getChildren().add(rect2)

    launch {
        gameLoop()
    }
  }
}

suspend fun gameLoop(){
  var elapsedTime = 0.0 //in seconds
  while(true){
        //println("Elapsed: $elapsedTime(s), Velocity: ${ship1.velocity}(m/s) Position: ${ship1.position}")
        rect1.relocate(ship1.position.x, ship1.position.y)
        rect2.relocate(ship2.position.x, ship2.position.y)
        delay(100)
        elapsedTime = elapsedTime + 0.1
        ship1.calc(0.1)
        ship2.calc(0.1)
    }
}

public class LineGrid : Pane{
  val canvas = Canvas()
  val dashLength = 5.0
  val horizontalX = 3.0

  constructor() {
    getChildren().add(canvas)
  }

/*
  init {
    rectangle {
        fill = Color.BLUE
        width = 300.0
        height = 150.0
        arcWidth = 20.0
        arcHeight = 20.0
    }
  }
*/

  override protected fun layoutChildren(){
    val top = snappedTopInset().toInt()
    val right = snappedRightInset().toInt()
    val bottom = snappedBottomInset().toInt()
    val left = snappedLeftInset().toInt()
    val w = getWidth() - left - right
    val h = getHeight() - top - bottom

    canvas.setLayoutX(left.toDouble())
    canvas.setLayoutY(top.toDouble())

    if (w != canvas.getWidth() || h != canvas.getHeight()){
      canvas.setWidth(w)
      canvas.setHeight(h)
      val g = canvas.getGraphicsContext2D()
      g.clearRect(0.toDouble(), 0.toDouble(), w, h)

      //vertical lines
      g.setStroke(Color.DIMGRAY)
      g.setLineDashes(dashLength)
      for(i in 0..getWidth().toInt() step 30){
        g.moveTo(i.toDouble(), 0.toDouble())
        g.lineTo(i.toDouble(), getHeight() - (getHeight()%30))
        g.stroke()
      }

      //horizontal lines
      g.setLineDashes(dashLength)
      for(i in 0..getHeight().toInt() step 30){
        g.moveTo(horizontalX, i.toDouble())
        g.lineTo(getWidth() - (getWidth()%30), i.toDouble())
        g.stroke()
      }

    }
  }
}
