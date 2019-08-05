# Chemical-Kinetics-Simulator
This is a particle level implementation and simulation of collision theory with an almost ideal gas. The main screen constists of a depiction of the gas particles, a histogram to the bottom left and a graph of concentration over time on the bottom. Lastly, there is a control panel on the right.

## Histogram
This is a histogram of the speeds of the particles. The vertical and horizontal scales can vary depending on the temperature of the system so that the average speed at a given temperature is always at the same x coordinate. The green line is a graph of the maxwell-boltzmann distribution for the calculated temperature of the system.

## Concentration graph
The lines represent the concentration of all the species present over time. The color of the lines correspond to the color of the compounds they represent. The speed at which the graph scrolls can be controlled by varying the "Data Collection Interval" slider.

## Display
The time of the simulation, temperature, energy and total energy are displayed at the top right. Energy refers to the sum of kinetic energy of the particles while total energy refers to kinetic energy + energy stored in bonds.

## Control panel

### Temperature
If "Set temperature" is checked, then the simulation will try to change the temperature of the system to match the chosen temperature. Particles can gain or lose energy when they collide with walls, with the same effect as the container being in contact with a surface at a certain temperature. If "Set temperature" is unchecked, then the walls are adiabatic - heat cannot flow in or out.

### Add/Remove
These buttons add or remove a certain number of particles in the simulation. Set the number you wish to add or remove, and the name of the species with the fields on the left.

### Timestep/iterations


## Editor
