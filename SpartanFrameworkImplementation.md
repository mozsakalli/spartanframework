# Spartan Framework Implementation #

The following sections describe the actual implementation of the framework as it is in Version 1.0.
Althought several parts of the framework will be improved in version 2.0, the overall parts of the system wont change.

The following concepts are the meat and potatos of any game, this is what the game programmer will use to build its game. Every these parts, may need to be (and it is encouraged) extended directly by the programmer, creating new actions, new states, new forms, etc.

Beggining with the gamestate that will replace the normal gamestate in slick2d, to several parts of the framwork, like the entities, forms, states, actions we will transverse all the implementation until we arrive at the managers, like the render, action, collision managers.