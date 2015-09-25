# Game States #

A state is a think of beauty, its all and nothing, it has all the data of an entity and can be reused in different entities.

All the states in the Spartan framework must extends the AbstractGameState class. This class adds some common and expected functionality to all the states.
To create a new state all it needs to be done is to do:

```
PositionalGameState positionalGS = PositionalGameState(“Identifier”);
```

The identifier of the GameState is a String Identifier that will uniquely define the gamestate in an Entity or form and can be used to obtain the correct gamestate to anyone that holds the entity or the form.

From this point on, states can be anything, for example the Positional GameStates holds a position data. This game state is used in all the forms to hold its position within the game world. It could be used for a entity as well in order to gather a position of a item in a map representing the game world, or a position of an item in a radar for example. In here lies the beauty of these kinds of systems, everything may be reused from scratch.

Like it was said before states can be and hold anything, like:
  * Positional information
  * Collision detection information
  * Lives and health information for an avatar
  * Resource levels for a RTS game
  * Unit attack/defense information for RTS game
  * Platform information (like Ice, lava, water) for a platform game
  * Explosion time, damage type for a grenade in any game

All you can think as data for an entity/form must be inserted as a state.

Since the forms must be discrete, i.e. Renderable by themselves only, states are also needed within forms. So if an Entity may have a state to hold the explosion time (in seconds) and damage type, its form may have a state that represents the timer as a fuse that can be read by the form and rendered accordingly.