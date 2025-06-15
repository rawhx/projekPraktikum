package weapon;

import java.util.Random;
import character.Character;

public class DoubleBarrel extends Weapon {
    private boolean[] barrels;
    private int currentBarrel;
    private boolean isLoaded;

    public DoubleBarrel(String name, int damage) {
        super(name, damage);
        barrels = new boolean[2];
        loadBarrels();
    }

    private void loadBarrels() {
        Random rand = new Random();
        int bulletPosition = rand.nextInt(2);
        for(int i = 0; i < 2; i++) {
            barrels[i] = (i == bulletPosition);
        }
        currentBarrel = 0;
        isLoaded = true;
    }

    @Override
    public void attack(Character target) {
        if (!isLoaded) {
            System.out.println("The gun needs to be reloaded!");
            System.out.println("Reloading...");
            loadBarrels();
            try {
                Thread.sleep(1000);
                System.out.println("Gun is reloaded!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        boolean result = barrels[currentBarrel];
        currentBarrel = (currentBarrel + 1) % 2;
        
        if (currentBarrel == 0) {
            isLoaded = false;
        }

        if (result) {
            int targetHealth = target.getHealth();
            target.setHealth(targetHealth - getDamage());
            System.out.println(target.getName() + " got shot! Health: " + target.getHealth());
        } else {
            System.out.println(target.getName() + " is safe for now.");
        }
    }
} 