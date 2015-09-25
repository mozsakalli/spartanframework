# Actions #

If forms are the render part of the game, actions are the update part in the render/update cicle. Actions use states of entities and forms as data to be processed and then changed again if needed. Actions are the manipulators of data in the framework.

To create a new action the programmer must extend the BasicGameAction that will represent the basic functionality needed for any form.

To instanciate a new form the programmer must do:
```
ForwardMovementAction fma = new ForwardMovementAction( “action name”, targetEntity );
```
This will create a forward movement action that will work on the targetEntity form position and will inject a movement at every update to that entity. As you can see now, it only takes an entity with a form and this action and anything in the game can now be made to go forward. Its one of those nice things about entities.
Of course, not all is good with this, because I have ommited something, the targetEntity needs two things to work with this action; 1. To have a form; 2. To have a well known gamestate with the speed parameter, if one of these cannot be sustained then the action will be destroyed.
This is a double edge sword, one the one hand it transforms a well typed system (java) into a not so well typed one (spartan), because now you have to know what your entities have as states, because the compiler sure wont known and your game will blow up, on the other hand, any entity that has a speed state may use this action to move without resorting to more code.
`{In 2.0 we are thinkling to change this into a well behave game, perhaps using java tags, so it can be compiled only if the target entity meets the requirements}`

Actions are registered in the action manager like this:
```
ActionManager.getInstance().registerAction(action);
```
And are unregistered like this:
```
ActionManager.getInstance().unregisterAction(action);
```
Only when a action is registered in the action manager will it be called to update, otherwise it wont.

When a action is over, for example a timer action reaches zero, it should be destroyed. In order to do so, the only thing it is needed is that the action returns true at the method isOver.
Upon execution of the action the ActionManager will verify if the action is over and in case it is, it will destroy the action.