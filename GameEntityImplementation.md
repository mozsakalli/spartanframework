# Game Entity #

The entity implementation in spartan manager is the GameEntity class. To instanciate a new entity all it needs to be done is to do the following

```
GameEntity entity = new GameEntity(“Player 1”);
```

A lot of stuff happens in this simple sentence, the EntityManager is envolve inderectly in the creation of this entity, and this instance is saved within the EntityManager itself.
The name “Player 1” shall represent a unique name in the system for this entity. If another entity is instanciated with this name an error occurs.

If any part of the system needs to find the “Player 1” entity, all it needs to do is to obtain an instance of the EntityManager and call the getEntity(“Player 1”) method.

Even thougth there is a tendency to think that Entities should have children (and indeed Spartan still has methods for adding children within an entity), this is considered to be depracated and will dissapear in version 2.0.
The reason for this is that and entity is looked at a whole, so it can never have the concept of children.

Example: Let's think about a person entity, its easy to think that a person is composed by an arm entity, a hand entity, a torso entity, a head entity, etc... This is wrong (in my opinion and implementation) since the concept is a PERSON entity so all of the above entities are in reality just small parts of only ONE entity that is a person. The form of the person entity should be able to render all the different parts of the person and in every of its poses.