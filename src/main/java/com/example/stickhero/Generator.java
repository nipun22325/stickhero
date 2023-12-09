package com.example.stickhero;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.example.stickhero.MainGameplay.translateX;

public class Generator {
    static List<int[]> endings = new ArrayList<>();
    static List<Platform> platforms = new ArrayList<>();
    static List<Cherry> cherries = new ArrayList<>();
    Random random = new Random();

    /*The Factory Method Pattern is a creational design pattern that provides an interface for creating instances of a class,
    but allows subclasses to alter the type of instances that will be created.
    The Generator class contains methods (spawnRandomCherries and spawnRandomPlatforms) are factory methods.
    They encapsulate the creation logic of objects (cherries and platforms) and return instances of these objects.
     */

    /*The methods like spawnRandomCherries and spawnRandomPlatforms can be seen as commands.
     They encapsulate a request as an object, allowing for parameterization and queuing of requests.
     */

    public List<Cherry> spawnRandomCherries() {
        for (int i = 0; i < platforms.size() - 1; i++) {
            Platform currentPlatform = platforms.get(i);
            Platform nextPlatform = platforms.get(i + 1);
            if (random.nextInt(100) < 50) {
                double max = nextPlatform.getX() - 21;
                double min = currentPlatform.getX() + currentPlatform.getWidth();
                double cherryX = random.nextDouble(max - min) + min;
                double cherryY = MainGameplay.HEIGHT + 10 - currentPlatform.getHeight(); // Just above the platform
                cherries.add(new Cherry(cherryX, cherryY));
            }

        }
        return cherries;
    }

    public List<Platform> spawnRandomPlatforms(int count) {
        int x = (int) translateX;
        for (int i = 0; i < count; i++) {
            int platformWidth = random.nextInt(100) + 50;
            platforms.add(new Platform(x, platformWidth, 100));
            endings.add(new int[]{x, x + platformWidth});
            x += platformWidth + random.nextInt(150) + 50;
        }
        return platforms;
    }

    public void collectCherry(Cherry cherry) {
        MainGameplay.cherriesCollected++;
        Iterator<Cherry> iterator = cherries.iterator();
        while (iterator.hasNext()) {
            Cherry currentCherry = iterator.next();
            if (currentCherry == cherry) {
                iterator.remove();
                break;
            }
        }
        MainGameplay.score += 2;
    }

    public void updatePlatformEnds() {
        Generator.endings.clear();
        for (Platform platform : platforms) {
            int startX = platform.getX_int();
            int endX = startX + (int) platform.getWidth();
            Generator.endings.add(new int[]{startX, endX});
        }
    }

    public void updateEntities(Character character, List<Stick> sticks) {
        character.setX(character.getX() - translateX);
        for (Platform platform : platforms) {
            platform.setX(platform.getX() - translateX);
        }
        for (Stick stick : sticks) {
            stick.setX(stick.getX() - translateX);
        }
        for (Cherry cherry : cherries) {
            cherry.setX(cherry.getX() - translateX);
        }
        this.updatePlatformEnds();
    }
}