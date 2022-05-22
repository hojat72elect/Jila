"""
Created by <a href="https://github.com/hojat72elect">Hojat Ghasemi</a>

the [debug] function in here helps us debug
the game's code and see all the debug logs
in the top left corner of the game window.
"""

import pygame

pygame.init()
font = pygame.font.Font(None, 25)


def debug(info, y=10, x=10):
    display_surface = pygame.display.get_surface()
    debug_surf = font.render(str(info), True, 'white')
    debug_rect = debug_surf.get_rect(topleft=(x, y))
    pygame.draw.rect(display_surface, 'Black', debug_rect)
    display_surface.blit(debug_surf, debug_rect)
