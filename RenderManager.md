# Render Manager #

This is by far the most used manager, after all you can have zero actions (nothing changes ingame) but dont want to display a black screen (zero forms).

Using the render manager is really easy, you just create the render layers, like a sheets of transparent paper on top of each other and then add forms to those layers. This is a simple way of adding stuff to render without the need to known what objects render first. Just add them to the correct layer. For example, HUD displays go onto a layer, game action gost to another, the background of the game goes to another.

To add a new layer to the render manager:
```
VirtualGameSpace space = RenderManager.getInstance().addNewVirtualGameSpace("_##Loader##_");
```
From this point on, the layer can be obtained by using the method:
```
VirtualGameSpace space = RenderManager.getInstance().getVirtualSpace("_##Loader##_");
```
The virtual game space is just an array of forms gathered in a single and connected virtual space, in this case a render layer.

To add forms to a virtual space just use the method:
```
space.addForm(form);
```
To remove forms just use the method:
```
space.removeForm(form);
```
Since in 2d Games the renderManager actually is the camera view, a X,Y position of the camera is available for anyone to change.
```
RenderManager.getInstance().translationX;
RenderManager.getInstance().translationY;
```
These two will directly control the position of the camera.