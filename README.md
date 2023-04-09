# Client Enhancements
A collection of Minecraft modifications to automate repetitive stuff and make your life easier.

## Installation
Grab the latest jar asset from the [release page](https://github.com/Gurkengewuerz/MinecraftClientEnhancements/releases). Drop it into your `mods` folder named your minecraft directory.  
You also need to install [Fabric Loader](https://fabricmc.net/) before this as well as [Fabric API](https://github.com/FabricMC/fabric/releases).

## Usage
The mod has no modules active by default. Bind the respective modules in your keybindings to activate them.  
The GUI also has tabs for configuring the modules accessible unter `Options` -> `Client Enhancements`.

## Included modules
* **Creative Fly**: Creative mode-like flight, even in survival. (default key `g`)
* **Full-Brightness**: Disables darkness/makes everything completely bright. (default key `v`)
* **No Fall**: Disables fall damage in survival. Useful in combination with creative fly. (default key `none`)
* **X-Ray**: Show ores in the ground. (default key `x`)
* **Zoom**: Optifine like smooth zoom without cinematic camera. (default key `none`)
* **Anti-Invisibility**: Show hidden entities. Useful to debug armorstands. (default key `i`)
* **Freecam**: Explore the world around you (default key `none`)

## Thanks
A huge thanks to [@LiveOverflow](https://twitter.com/LiveOverflow) for his inspiration in his [Minecraft series](https://www.youtube.com/watch?v=Ekcseve-mOg&list=PLhixgUqwRTjwvBI-hmbZ2rpkAl4lutnJG&index=2).  
I play Minecraft since end of Alpha and hosted and developed a lot of minecraft server plugins but never developed client side mods.

## Development
### Setup
Download this project source, or `git clone git@github.com:Gurkengewuerz/MinecraftClientEnhancements.git`  
Navigate to the source directory  
Use `gradlew runClient` to launch a test environment.  
To build the project jars, run `gradlew build`  
For detailed IDE-specific instructions please see [fabric wiki page](https://fabricmc.net/wiki/tutorial:setup)
