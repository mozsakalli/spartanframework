# Resource Manager #

The resource Manager acts a a central repository for all the resources of your game. It will load resources (image, sound, animation, text) from an XML file and gather them in memory and associate it with a String Identifier.
At any time in the game anyone may call the resource manager to get a handle for a specific resource without loading it again.

Why use this? Well if you dont every time you want to change an image from your game you must go and change the code and recompile, with this you only have to change the XML file containing the resources. Also its a great way to have all the resources in one place, even text. Imagine you want your game to have multiple languages, you just have different XML files (one for every language) that will be loaded when choosing a language.

XML entries for the resources

Lets looks at how to build the resource XML File. Below is a small resource xml file.

```
<?xml version="1.0" encoding="UTF-8"?>
<resources>
<!-- GAMEPLAY -->
	<!-- Background -->
	<resource type="image" id="BATTLE_BACKGROUND">data\smashtvbg.jpg</resource>
	<!-- HUD -->
	<resource type="image" id="HUD_HEART">data\heart.png</resource>
	<resource type="image" id="HUD_MONEY">data\coin.png</resource>
	<!-- PLAYER -->
	<resource type="image" id="ANGEL">data\angel.png</resource>
	<resource type="animation" id="ROCKET_TRAIL" tw="100" th="50" duration="100">data\rockettrail.png</resource>
	<resource type="animation" id="ELECTRIC_BURST" tw="150" th="150" duration="65">data\electricburst.png</resource>
	<!-- Enemy -->
	<resource type="image" id="ENEMY">data\enemy.png</resource>
	
	<resource type="image" id="RETICLE">data\reticle2.png</resource>
	
	<resource type="image" id="BULLET">data\bullet.png</resource>
</resources>
```
This represents the entire resources for the following video:

<a href='http://www.youtube.com/watch?feature=player_embedded&v=zlcuHUE5X_U' target='_blank'><img src='http://img.youtube.com/vi/zlcuHUE5X_U/0.jpg' width='425' height=344 /></a>

As you can see the resurce manager will load every resource and associate them with its id attribute.

In order to load resources all you need to do is
```
ResourceManager.getInstance().loadResources(inputStream, true);
```
This will instruct the resource manager to read the resource XML file from the inputStream passed, and to use a deferred load (if you dont known what deferred loading is check this). False will load every resource at once, deferred will enable the user to load them on demand. I generally use this in conjuntion with a loading action and a loading slickgamestate to have a loading state that can be called on demand with a set of new resources (think of the loading bar before every level). Look at the tutorial on how to implement a resource loader SlickGameState.


## Sintax Resources ##

In version 1.0 the resource manager will load Images, Animations, Sounds and Text as resources.
The following section will describe each resource syntax in the file.

### Available syntax Types ###

There are 4 available syntax types:

  * image
  * animation
  * sound
  * text

No more are accpeted in the resource type and the loader will issue an error if something else is inserted.

### Images ###

Example:
```
<resource type="image" id="BATTLE_BACKGROUND">data\smashtvbg.jpg</resource>
```

  * type - the type of the resource in this case the image
  * id - the in game identifier associated with this image
  * value - the path of the resource


### Animation ###

Example:
```
<resource type="animation" id="ROCKET_TRAIL" tw="100" th="50" duration="100">data\rockettrail.png</resource>
```

  * type - the type of the resource in this case the animation
  * id - the in game identifier associated with this animation
  * tw - the width of the animation
  * th - the height of the animation
  * duration - the duration of each frame
  * value - the path of the resource


### Sound ###

Example:
```
<resource type="sound" id="BATTLE_START" loop="false">data\start.ogg</resource>
```

  * type - the type of the resource in this case the sound
  * id - the in game identifier associated with this sound
  * loop - if the sound needs to loop (for music)
  * value - the path of the resource


### Text ###

Example:
```
<resource type="text" id="History_lesson">When the portuguese finally set sail, they conquered half the world</resource>
```

  * type - the type of the resource in this case the text
  * id - the in game identifier associated with this text
  * value - Text to show