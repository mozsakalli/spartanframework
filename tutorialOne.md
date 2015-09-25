# Tutorial One : Simple Plane #

Since Im on a three hour roll writimng the documentation, for today I'll add the simple plane tutorial. This is just a small example, that will show how :

  1. to set up a game
  1. to create some entities
  1. to create layers in the render manager
  1. to create actions

First of all lets create a simple game structure, the game will launch a single game state with all the functionality, so the initial game class is simple:

```
package com.foxhole.spartan.examples.topdownplane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.exception.SpartanException;

public class TopDownPlaneGame extends StateBasedGame {

	public TopDownPlaneGame() {
		super("Example 1 : Plane Tutorial");
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		try {
			addState(new BattleZoneState());
		} catch (SpartanException e) {
			throw new SlickException("Could not create BattleZoneState", e);
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new TopDownPlaneGame());
		
		app.setDisplayMode(800, 600, false);
		
		app.start();
	}
}
```

Remind yourself that BattleZoneState does not yet exist. Lets make it...

```
package com.foxhole.spartan.examples.topdownplane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.movement.ForwardMovementAction;
import com.foxhole.tools.spartan.actions.movement.RotationalMovementAction;
import com.foxhole.tools.spartan.entity.GameEntity;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.forms.ImageGameForm;
import com.foxhole.tools.spartan.gamestate.SpartanGameState;
import com.foxhole.tools.spartan.managers.action.ActionManager;
import com.foxhole.tools.spartan.managers.render.RenderManager;
import com.foxhole.tools.spartan.spaces.VirtualGameSpace;
import com.foxhole.tools.spartan.states.PropertiesGameState;

public class BattleZoneState extends SpartanGameState {

	public BattleZoneState() throws SpartanException {
		super();
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		
	}
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		
		
	}
}
```

This is the simple structure for our small example, now if you run it, you should have a small black window showing in your monitor with the name “Example 1 : Plane Tutorial”

Lets analise this for a bit, as yu can see there is no render nor update methods. This is because they are implemented in the SpartanGameState from where you're game state derives. All you need to do is add content to the render and update manager.

## Adding forms ##

Creating the virtual game spaces to render

After the super.enter(container, game);

add the following code:
```
// create the virtualspace in which to render
VirtualGameSpace background = RenderManager.getInstance().addNewVirtualGameSpace("background");
VirtualGameSpace action = RenderManager.getInstance().addNewVirtualGameSpace("action");
```

This will add us two layers in the render manager.

Adding the background image

after the last code we'll add:
```
		try {
			IGameEntityObject backgroundEntity = new GameEntity("background");
			ImageGameForm backgroundForm = new ImageGameForm(new Image("testdata/background.jpg"));
			backgroundEntity.setForm(backgroundForm);
			
			background.addForm(backgroundForm);
		} catch (SpartanException e) {
			throw new SlickException("Could not create background entity", e);
		}
```

As can be seen in the code above, an entity named “background” is created, then a form for it is also created by using the ImageGameForm (a basic image form from spartan framework), we set the entity form to this one and add it to the background layer in the render manager.

Creating the plane

Again,

```
		// create the plane
		IGameEntityObject planeEntity  = null;
		try {
			planeEntity  = new GameEntity("plane");
			
			ImageGameForm planeForm = new ImageGameForm(new Image("testdata/plane.png"));
			
			planeEntity.setForm(planeForm);
			
			action.addForm(planeForm);
			
			planeForm.getPosition().setCenterPosition(61, 60);
			
			planeForm.getPosition().setPosition(container.getWidth()/2+planeForm.getPosition().getCenterposX(), 
					container.getHeight()/2+planeForm.getPosition().getCenterposY());
			planeForm.getPosition().setDirection(0, -1);
			
			((PropertiesGameState)planeEntity.getState(PropertiesGameState.class.getCanonicalName())).addFloat("ROTATION", 0.5f);
		} catch (SpartanException e) {
			throw new SlickException("Could not create plane entity", e);
		}

```

This one is a bit different, after all we did for the background, we also set the center of the entity to (61,60) and set a new position as well as direction for the plane.

We also obtain a propertiesgamestate (automaticaly inserted in every entity upon creation) and add a variable called ROTATION with the value 0.5f, this will be used in the rotation action later on.

{This is wrong, the correct way would be to have a plane settings state and read from it}

## Add the actions ##

After the last piece of code add the following, this will add two actions, one that will rotate the plane te other that will make it go forward.

```
//actions
		try {
			RotationalMovementAction raction = new RotationalMovementAction(RotationalMovementAction.class.getCanonicalName(), planeEntity);
			ActionManager.getInstance().registerAction(raction);
			
			ForwardMovementAction faction = new ForwardMovementAction(ForwardMovementAction.class.getCanonicalName(), planeEntity, 0.3f);
			ActionManager.getInstance().registerAction(faction);
			
		} catch (SpartanException e) {
			throw new SlickException("Could not start actions", e);
		}
```

These two classes are located inside the SpartanUtils library, and I urge you to look at them a bit and understand that they are just a part of the update cycle.

The scope of these actions are out of this tutorial that was made to show how to set up a game using spartan.