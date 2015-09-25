# Form #

The form is a representation of the entity, entities shall only hold forms if they need to be rendered in any way. An entity that acts as a statistical data holder for a game (like the enemies destroyed, weapons used, number of deaths, shots fired, etc) may not need to be rendered directly and only used by other entities as data holders.

If games are divided into render/update cicle, then Forms are the render part of the game.

A form must be a discrete concept, i.e. They only need to read any of its enclosed data (in states) to be able to render themselves. If anyone in the system needs that a specific form renders in a different way, all it needs to do is to change the states associate with the form and wait for the next render cicle, the form in render time must be able to understand what has changed and render it self accordingly.

Every form created by the game programmer must extend the BasicGameForm, this will add basic functionality to the Form, like positional data and collision detection.

To instanciate a ImageGameForm, that is one of the forms in the framework that only showns an Slick2d Image, you must:
```
ImageGameForm form = new ImageGameForm(new Image(“image.jpg”));
```

This will render a image at 0,0 when registered in the render manager.

By looking at the render method:

```
public void render(Graphics graphics) {
	mesh.draw();
}
```

Mesh is the image inside the form, we can see that the image will be drawn at 0,0... This is another good thing in the system, you only need to write the render method your forms with no regards for position, scale and rotation. Positioning will be done by the render manager, using the data in the Positional State.

## Positional state ##
In the BasicGameForm, there is a method that obtains the positional state as an IformPosition, by the getposition method.
This state includes all the normal transformations (translate, rotate, scale), direction of the form (for example a ship may be rotate 30º but is direction may be upwards or whatever you defined the direction), also it holds a center of position so the position of the form (x,y) refers to this center.
The center of position is also used for scaling and rotation.
Finally the Positional state also contains a shape that represents the form as a Slick2d Shape (any convex polygon) that can be used for collision detection.
`{In version 2.0 a new state will appear that will hold not only one shape but several shapes}`


---

## Collision state ##
Another state that every form that needs (but may not have if no collisions are needed) to collide must hold is the collision state, that will hold the type of the object in collisions. This is used by the collision manager to understand what kind of form its colliding, for examples player bullets may be type 1, enemies type 2, enemy bullets type 3. So collisions between 1 and 2 may exist, but 3 and 2 may not collide against each other.


---


Forms must be registered at the render Manager in order to be displayed. The render manger as a set of layers that have a render order. By obtaining the layer, all it needs to do is to add the form to that layer. (see render manager for more details).