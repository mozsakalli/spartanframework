# Entity Manager #

Entity manager is the manager responsible for gathering all the entities in the system. It shall act as a unique repository of entities that can be queried at any time to find any entity.
It will work also as a desctructor for the entity, since calling the remove Entity method will destroy anything related to the entity in game.

`{In version 2.0 of spartan the EntityManager will be used to create and destroy the Entities.}`