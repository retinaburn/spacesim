package moynes.spacesim.sim

import kotlinx.coroutines.experimental.*

val oneG = 9.8 //m/s2
val acceleration = oneG

fun main(args: Array<String>){
	var positionV = Vector2d(0.0,0.0)
	var accelerationV  = Vector2d(acceleration, 0.0)
	var ship1 = Ship(position = positionV, acceleration = accelerationV)

	println("Acceleration: ${ship1.acceleration}(m/s2) - Give er....")
	for (elapsedTime in 0..10){
		ship1.calc(1)
		println("Elapsed: $elapsedTime(s), Velocity: ${ship1.velocity}(m/s) Position: ${ship1.position}")
	}

	ship1.acceleration = Vector2d(0.0, 0.0)
	println()
	println("Acceleration: ${ship1.acceleration}(m/s2) - On the float...")
	for (elapsedTime in 0..10){
		ship1.calc(1)
		println("Elapsed: $elapsedTime(s), Velocity: ${ship1.velocity}(m/s) Position: ${ship1.position}")
	}

	//add a little sideways momentun
	ship1.acceleration = Vector2d(0.0, 5.0)
	println()
	println("Acceleration: ${ship1.acceleration}(m/s2) - dodge")
	for (elapsedTime in 0..10){
		ship1.calc(1)
		println("Elapsed: $elapsedTime(s), Velocity: ${ship1.velocity}(m/s) Position: ${ship1.position}")
	}


	//flip and burn
	ship1.acceleration = Vector2d(-acceleration, 0.0)
	println()
	println("Acceleration: ${ship1.acceleration}(m/s2) - Flip and burn...")
	for (elapsedTime in 0..10){
		ship1.calc(1)
		println("Elapsed: $elapsedTime(s), Velocity: ${ship1.velocity}(m/s) Position: ${ship1.position}")
	}
}

data class Vector2d(val x: Double, val y: Double)

data class Ship(var position: Vector2d, var acceleration: Vector2d, var velocity: Vector2d = Vector2d(0.0,0.0)){

  fun calc(changeInTime: Int){
    val newX = velocity.x + acceleration.x * changeInTime
    val newY = velocity.y +  acceleration.y * changeInTime
    velocity = Vector2d(newX, newY)
    calcPosition(changeInTime)
  }

  fun calc(changeInTime: Double){
    val newX = velocity.x + acceleration.x * changeInTime
    val newY = velocity.y +  acceleration.y * changeInTime
    velocity = Vector2d(newX, newY)
    calcPosition(changeInTime)
  }

  private fun calcPosition(changeInTime: Int): Vector2d{
    val newX = position.x + velocity.x * changeInTime
    val newY = position.y + velocity.y * changeInTime
    position = Vector2d(newX, newY)
    return position
  }

  private fun calcPosition(changeInTime: Double): Vector2d{
    val newX = position.x + velocity.x * changeInTime
    val newY = position.y + velocity.y * changeInTime
    position = Vector2d(newX, newY)
    return position
  }
}
