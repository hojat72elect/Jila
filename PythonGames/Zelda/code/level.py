"""
The [level] class is going to be the central part of the
game because it contains all sprites (player, enemies,
map, ...) and also manages all the interactions between
them efficiently.

Here we're going to have 2 kinds of sprites:
    1- visible_sprites:
        All the sprites that will be drawn on screen. Such as player, enemies, ...
2- obstacle_sprites:
        Anything that can collide with the player. Such as Level's boundaries, ...
"""

import pygame


class Level:
    def __init__(self):
        # get the display surface
        self.display_surface = pygame.display.get_surface()

        # sprite groups setup
        self.visible_sprites = pygame.sprite.Group()
        self.obstacles_sprites = pygame.sprite.Group()

    def run(self):
        # update and draw the level
        pass
