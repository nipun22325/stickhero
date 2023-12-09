# stick-hero
This project is for CSE-201 Advanced Programming Monsoon Semester 2023.
Made by Nipun Kothari(2022325) and Paras Kakran(2022349).

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
HOW TO RUN CODE:
To start the application, run MainGameplay.java.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CONTROLS:

-Hold the right mouse button to extend the stick to next platform.
-Press the alt key to flip the character to restore cherries
-Press the key S to save the game at any point
-Press the key R to restore a previously saved game

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CLASSES:

-MAINGAMEPLAY : This class extends the Application class and implements serializable interface. saveGameState method is used to save the state of the same and restoreGameState is used to restore the game state and continues from where it was stopped. The start method initializes all the stage elements like scene , Pane , background , canvas. collectCherry method is  to collect the cherry that come along the character's path. StartGame method is used to spawn random platforms and to add all the controls like extend the stick , flip character etc. restartGame method is used to restart the game .

-CHARACTER : This class instantiates the character and its attributes like its coordinates , height and widht. Flip method is used to flip the character to collect cherries . Getters and setters are used to get and set the coordinates of the character.

-CHERRY : This class instantiates the cherries , its coordinates , getters and setters are used for its coordinates.

-FINALSCREEN : This class creates the final end screen of the game with appropriate messages like GAme OVer , a restart button is added to restart the game.

-GENERATOR : This class spawns platforms and cherries using spawnrandomcherries and spawnrandomplatforms method with appropriate height width and coordinates.

-PLATFORM: This class instantiates the platform with its coordinates and height and width.

-SOUNDPLAYER : This class is used start and audio clip which works as a background music for the game , it creates playsound method for it.

-STARTSCREEN : This class creates the first ever screen with appropriate texts like the start game and various game instructions.

-STICK : This class instantiates the stick with its coordiates , height and width , rotateangle to rotate the stick , update function is used to update the stick angle.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------


DESIGN PATTERNS :

- The Factory Method Pattern is a creational design pattern that provides an interface for creating instances of a class, 
    but allows subclasses to alter the type of instances that will be created. 
    The Generator class contains methods (spawnRandomCherries and spawnRandomPlatforms) are factory methods.
    They encapsulate the creation logic of objects (cherries and platforms) and return instances of these objects.
     
-Observer is a behavioral design pattern which specifies communication between objects: observable and observers.
        An observable is an object which notifies observers about the changes in its state.
        The AnimationTimer usage in the startGame() method follows the observer pattern implicitly.
        Here the AnimationTimer is the observable and the startGame() method is the observer
        The game loop updates the game state continuously.
-The methods like spawnRandomCherries and spawnRandomPlatforms can be seen as commands.
     They encapsulate a request as an object, allowing for parameterization and queuing of requests.
     
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

