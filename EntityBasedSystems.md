# Entities #

An entity system is an approach to game development that assumes that every object in your game is an entity, your avatar is an entity, a bullet is an entity, a backpack with inventory is an entity and so on.

Entities are nothing more that a generalization of anything that may appear on your game, that can be or not interacted by the player.

Since the explanation is a bit vague it's probably better to just explain all the components that make the entity system.

**The Game Entity Object:** This object represents a game token. It can be anything within game limits, as far as the imagination of the designer stretches.

**The Game Action Object:** The actions make the game happen. This object is like an event in-game, it shapes the game rules/mechanics. Some examples of actions may be walk, run, jump, shoot, climb rope, etc.

**The Game Form Object:** This object represents the form of visual appearance of an entity within the game. If an avatar is an entity, its form defines the way the player sees it. The entity representing a ship may have all the data regarding the ship, but the form has all the data needed to be showned as a ship.

**The Game State Object:** This object is used to track the data of an entity or its form. State objects can also modify the way an action functions. State objects of an Entity object represent data that is not relevant to most game mechanics, such as a name, while State objects of the Form object are typically used exclusively for game mechanics.

**The Game Space Object:** These are simply collections (or sets) of the other objects in the system. The reasons why one would want collections of the various other game objects is diverse and the objects are generally sorted into collections for optimization purposes, which includes rendering, and A.I. updates. Conceptually, this object represents the world of the game, and it is where the effects of the all actions are applied.