"""
Created by <a href="https://github.com/hojat72elect">Hojat Ghasemi</a>
"""

import pygame
import sys

from code.debug import debug
from code.level import Level
from code.settings import *


class Game:

    # initiating the game (the [screen] and the [clock])
    def __init__(self):
        pygame.init()
        self.screen = pygame.display.set_mode((WIDTH, HEIGHT))
        pygame.display.set_caption('Zelda')
        self.clock = pygame.time.Clock()

        self.level = Level()

    def run(self):
        while True:
            # the event loop which checks for recent events in game's world
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    sys.exit()

            self.screen.fill('black')
            self.level.run()
            debug("hello from debug")
            pygame.display.update()
            self.clock.tick(FPS)


if __name__ == '__main__':
    game = Game()
    game.run()
