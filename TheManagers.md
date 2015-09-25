# The Managers #

The Managers are a simple way of performing some transversal functionality, like rendering to the screen, collision detection, action update etc.

There are several manager within Spartan (and more comming at every iteration) like

  * **ResourceManager** Acts a a central repository for all the resources of your game
  * **RenderManager** Used to render all the game forms
  * **ActionManager** Used to update all the actions of the game
  * **EntityManager** Works as a central repository for all entities within the game
  * **CollisionManager** Works the collisions of the system
  * **GameStateManager** This is the manager of managers, that selects the appropriate set of managers for each gamestate

Every manager (except the Resource Manager) is only global within its SlickGameState, and changing the SlickGameState will result in changing the set of managers. This is done to preserve the gamestate full state, so it is possible to jump to another SlickGameState  and upon return to the initial state all managers shall continue from where it stopped.

`{Version 2.0 will add the InputManager and the LevelBuilderManager}`