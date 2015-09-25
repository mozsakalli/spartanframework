# Action Manager #

Where an update is, the action manager follows.

This is what will make your game actually do something. The action manager runs all the actions. Actually that's just about it. Whenever you create an action, just register it into the action manager this way:

```
ActionManager.getInstance().registerAction(action);
```
And unregistered this way:
```
ActionManager.getInstance().unregisterAction(action);
```
Most of the times there is no need to unregister the action since when the action is over the ActionManager will remove it.