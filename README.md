February 2017

A game created for CSC505: Data Structures and Algorithms with Christine Hu and Gregory Zhu.

Concept: Newbould wants to destroy the bears before they kill him. The player moves Newbould using the arrow keys, and presses space to shoot a note from Newbould's trombone, which kills most bears upon contact. 

We used JavaFX to code the game, and Quadtrees for efficient collision detection of projectiles, bears, and Newbould. To implement the Quadtrees, we extended binary trees with each node having four children; thus the screen was split into quarters. When the number of objects in a quarter exceeds a preset number, that quarter divides into quarters. The Quadtree is constantly changing to follow movement and distribution of objects. The two functions required for the Quadtree are insert and retrieve, both of which are recursive. The big O analysis is O(n log n) time to track collisions, which improves upon the O(n^2) to compare each object with each other.
