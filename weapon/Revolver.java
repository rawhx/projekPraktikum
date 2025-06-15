package weapon;

import java.util.Random;
import character.Character;

public class Revolver extends Weapon 
{
    private boolean[] chambers;
    private int currentChamber;

    public Revolver(String name, int damage) 
    {
        super(name, damage);

        chambers = new boolean[6];
        spinChamber();
    }

    public void spinChamber()
    {
        Random rand = new Random();

        // peluru ada di slot berapa
        int bulletPosition = rand.nextInt(6);
        for(int i = 0; i < 6; i++)
        {
            // isi peluru ke chamber[i] jika [i] == hasil random int
            chambers[i] = (i == bulletPosition);
        }
        currentChamber = 0;
    }

    public boolean shootCondition()
    {
        boolean result = chambers[currentChamber];
        // current + 1 untuk muter ke chamber selanjutnya
        // mod 6 biar pas chamber ke-6 nanti dia hasilnya 0, jadinya balik lagi ke slot 0
        currentChamber = (currentChamber + 1) % 6;

        return result;
    }

    @Override
    public void attack(Character target) 
    {
        spinChamber();
        boolean result = shootCondition();

        if(result == true)
        {
            int targetHealth = target.getHealth();
            target.setHealth(targetHealth - getDamage());
            System.out.println(target.getName() + " is unlucky! Health: " + target.getHealth());
        }
        else
        {
            System.out.println(target.getName() + " is safe for now.");
        }
    }
}
