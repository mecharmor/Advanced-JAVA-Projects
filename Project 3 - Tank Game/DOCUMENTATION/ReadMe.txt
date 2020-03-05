This project was developed using: intelliJ IDEA 2018.2.3
Java Version: jdk-10.0.2
The working directory for intelliJ is: \csc413-tankgame-mecharmor\TankGame
The working directory for the compiled jar is: \csc413-tankgame-mecharmor\jar

To Run The Game:
	-Navigate to this relative path inside the GitHub repo: \csc413-tankgame-mecharmor\jar
	-Launch the main.jar to run the game


Power Up: Red '+' block gives the tank an extra life. 
Note: Tanks can shoot the power up block and destroy it.
Note: Destructible Walls are marked with a 'D'
Note: Indestructible Walls are marked with an 'I'
Note: If you collide with an object in reverse it will glitch the tank. Need to fix this. (This happens because I invoke the moveBackwards() method during a collision with a wall [located in the handleCollision of Tank.java])

CONTROLS:

Left Tank:
	Move Forwards: W
	Move Backwards: S
	Rotate Right: D
	Rotate Left: A
	Shoot: E
Right Tank:
	Move Forwards: Up Arrow
	Move Backwards: Down Arrow
	Rotate Right: Right Arrow
	Rotate Left: Left Arrow
	Shoot: Enter Key
