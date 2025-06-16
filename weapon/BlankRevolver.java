package weapon;

import character.Character;

public class BlankRevolver extends Weapon {
    public BlankRevolver(String name) {
        super(name, 0);
    }

    @Override
    public void attack(Character target) {
        System.out.println("You try to shoot but... the gun is empty! It was a blank revolver!");
        System.out.println("Just die already...");
        target.setHealth(0);
    }
} 