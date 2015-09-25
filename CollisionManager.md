# Collision Manager #

The collision manager is very simple `{version 2.0 will enhance it}`, it checks every register shape against every registered shape in the game. To help things a bit, every form that registers itself for collision has a collision type, and a set of collision hadlers may be resgistered between collision types.

Example:

Lets say you have 4 types of entities:


  1. The player avatar
  1. Bullets fired by player
  1. Enemy
  1. Bullets fired by the enemy

Each collision type corresponds to the number of the entity above.

All the entities are registered as soon as they are created by using the method:
```
CollisionManager.getInstance().registerForm(playerForm);
```
So now the collision manager knows whom to collide, but not how process that collision outcome, enter the Handlers.

For each type pair a collision handler must be registered by means of the method:
```
CollisionManager.getInstance().registerCollisionHandler(handler);
```
handler is a implementation of the interface : `ICollisionHandler`.

The handler contains two types, the types for that collision {version 2.0 will enable several types at the same collision handler}. Given that we need the following handlers:

Collision between 2 and 3
Collision between 1 and 4
Collision between 1 and 3

The handlers are implemented by the programmer and are unique for each game (most of the times at least).