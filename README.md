# The issue was fixed in vanilla 1.13. There's no reason to use or update this plugin paste 1.12.2

# GhostBuster

This Bukkit plugin prevents a common cause of "ghost blocks" (blocks that a client thinks have been mined yet the server knows are still there). This most often happens when a player is mining blocks very quickly while occasionally falling.

When all of the following conditions are met:
* A player is breaking a block
* That player is not flying (creative flight)
* That player is not standing on solid ground

then this plugin will send an update to the client so that it knows the correct state of the block.
