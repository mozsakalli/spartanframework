# The Game Space Object #

_Taken from "Object-Oriented Game Design" by Britt L. Hannah._

The Game Space Object holds or contains other objects within itself. It represents a set or sets of game entities. In most computer games the game tokens interact with each other in some fashion, and are generally sorted into collections. However, in most computer game implementations the collections are created arbitrarily as needed. This system is designed with the realization that the game entities will always need to interact, and therefore requires a standard way to organize collections of game tokens. In this way, the sets could be made reusable if necessary.

There are two types of sets that are generally instantiated from the Game Space class. The Virtual Space object contains Game Form objects that are a virtual representation of an entity’s physical form. The set labelled as virtual space is a subset of logical space. The set shown contains a car, a plane, a flower and a boat, and these things simply represent what the Game Entity object looks like. This is of importance to keep in mind as the objects in virtual space are merely a representation of an entity’s physical form, and are not the entity itself. Other aspects of an Entity object are sorted into logical space, which will be explained later. The Game Space class is derived from an abstract base class that enforces minimum functionality on every set. Essentially that minimum functionality is the ability to add and remove entities, but could be extended to include things like insert for ordered sets or sort. The primary reason for separating entities into the two types of sets is optimization. Virtual space is slightly different from logical space as a virtual space is also capable of being rendered to a display system, and therefore contains only objects meant to be rendered. In fact, the interface to the core services of this game development framework expects to utilize virtual space sets for rendering. And, it is capable of using any such set, and even switching between virtual space sets at runtime.

The set logical space is where the work gets done. One could say that virtual space contains the virtual physical form of an entity, while logical space contains the entity’s virtual soul. This is the space where the work is done to make all the entity interactions occur. There can be as many types of interactions as there are entities to interact. An example might be a player’s avatar shooting a bad guy with his or her rail gun. When this interaction event occurs something must happen in the code to acknowledge the event, and this set is essentially where that acknowledgement takes place. It is noteworthy to mention that the objects in this set are optimized in a way that eliminates inter-object conditional branching. The are no large switch/case structures being continually checked in the interaction system. No big if/else chains exist in the interaction code. There is minor conditional branching within an object itself, but no program today can be implemented without using some conditional branching. Outside of that, the important thing to keep in mind is that the game space represents sets of interacting entities.