# Chemical-Kinetics-Simulator
This is a particle level implementation and simulation of collision theory with an almost ideal gas. The main screen constists of a depiction of the gas particles, a histogram to the bottom left and a graph of concentration over time on the bottom. Lastly, there is a control panel on the right.
<br />
<br />
<br />

## Histogram
This is a histogram of the speeds of the particles. The vertical and horizontal scales can vary depending on the temperature of the system so that the average speed at a given temperature is always at the same x coordinate. The green line is a graph of the maxwell-boltzmann distribution for the calculated temperature of the system.

## Concentration graph
The lines represent the concentration of all the species present over time. The color of the lines correspond to the color of the compounds they represent. The speed at which the graph scrolls can be controlled by varying the "Data Collection Interval" slider.

## Display
The time of the simulation, temperature, energy and total energy are displayed at the top right. Energy refers to the sum of kinetic energy of the particles while total energy refers to kinetic energy + energy stored in bonds.
<br />
<br />
<br />

## Control panel

### Temperature
If "Set temperature" is checked, then the simulation will try to change the temperature of the system to match the chosen temperature. Particles can gain or lose energy when they collide with walls, with the same effect as the container being in contact with a surface at a certain temperature. If "Set temperature" is unchecked, then the walls are adiabatic - heat cannot flow in or out.

### Add/Remove
These buttons add or remove a certain number of particles in the simulation. Set the number you wish to add or remove, and the name of the species with the fields on the left.

### Timestep/iterations
Changing the timestep changes the time resolution of the simulation. This slider should be as high as possible without "(reduce timestep)". If the timestep is as high as it can go but the simulation still runs slowly, you can increase the "iterations" slider to make the simulation run faster.

### Data collection interval
This slider sets the speed at which the concentration vs. time graph scrolls past.

### Width/height
These sliders control the width and height of the simulation, in physically accurate lengths.
<br />
<br />
<br />

## Editor
The editor allows you to create compounds and reactions of your own.

### Using the editor
Click "open editor" to open up the interface. The file menu will allow you to save and load files or examples, and to create new files.

### Creating compounds
To create a compound, first click the "add" button and then type in the name of the compound, mass, radius, and color (select a color or use hexadecimal format, eg. ff00ff). The radius should be accurate as it can affect the frequency of collisions with other particles. Currently complex shapes are not possible, only spheres. To change an existing compound, click on its name in the list, modify the parameters and then click the "change" button.

### Creating reactions
The process of creating and modifying reactions is similar to that for compounds. First specify how many reactants and products there are under "Reaction type" and enter the parameters. All probabilities must be between 0 and 1.

### Loading reactions into the simulation
When you are finished creating a system of reactions, click reset in the main screen to load it. This will delete all the particles in the simulation, so you must add more using the Add/Remove buttons.
<br />
<br />
<br />

## Examples
To load an example, click on the name of the example and press "reset".

### NO2/N2O4 Equilibrium
This is the default simulation upon opening the application, showing the reaction of 2NO2 <-> N2O4 (delta H = -60 kJ/mol). You can use it to demonstrate le chatlier's principle. Since dH (delta H) is negative, increasing the temperature will shift equilibrium to the reactants - and this can be seen in the concentration graph.

### Explosion
This is a simulation of an explosion. When the explosive breaks into two parts, it releases a lot of energy which causes more explosives to break down. For a demonstration, set the temperature slider to ~1500K and add a 400 particles of explosives. The temperature will be seen to increase;

### Decomposition of ozone
This simulates the depletion of ozone in the atmosphere catalyzed by CFCs. To demonstrate how CFCs can degrade ozone, first add only equal parts of ozone and diatomic oxygen to the simulation. You will notice that the reaction O3 <-> O + O2 is in equilibrium. THe splitting of O3 when radiation hits it is simulated. Then add some chlorine radicals, which are formed when CFCs break apart in the atmosphere. The concentration of O3 will steadily decrease because of the following reactions:\
Cl + O3 -> ClO + O2\
ClO + O -> Cl + O2\
The chlorine radical is thus regenerated and can continue to break apart ozone.\
<br />
**Notes:**
- The temperature should be set to 2000K
- You may need to increase the size of the simulation
