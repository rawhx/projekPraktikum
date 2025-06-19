package game;

import character.*;
import character.Character;
import weapon.*;
import java.util.Scanner;
import java.util.Random;


public class Game 
{
    Scanner input = new Scanner(System.in);
    Random rand = new Random();

    private Character[] characters;
    private Weapon weapon;
    private boolean isSinglePlayer;
    private boolean isZombieMode;
    private int shotsRemaining;
    private static final int MAX_SHOTS = 4;

    public Game(Character[] characters, Weapon weapon, boolean isSinglePlayer, boolean isZombieMode)
    {
        this.characters = characters;
        this.weapon = weapon;
        this.isSinglePlayer = isSinglePlayer;
        this.isZombieMode = isZombieMode;
        if (isZombieMode) {
            this.shotsRemaining = MAX_SHOTS;
        }
    }

    public void gameStart()
    {
        System.out.println("The Game will start in");

        try
        {
            for(int i = 1; i <= 3; i++)
            {
                System.out.println(i + "...");
                Thread.sleep(1000);
            }

            System.out.println("randomizing first turn...");
            Thread.sleep(2500);

            if (isZombieMode) {
                playZombieMode();
            } else {
                playNormalMode();
            }
        }
        catch(InterruptedException e)
        {   
            System.out.println("Game stopped!");
        }
    }

    private void playZombieMode() throws InterruptedException {
        Player survivor = null;
        Zombie zombie = null;

        for (Character c : characters) {
            if (c instanceof Zombie) {
                zombie = (Zombie) c;
            } else if (c instanceof Player) {
                survivor = (Player) c;
            }
        }

        if (zombie == null || survivor == null) {
            System.out.println("Invalid character setup: must have 1 survivor and 1 zombie.");
            return;
        }

        while (shotsRemaining > 0) {
            if (survivor.getHealth() <= 0) {
                System.out.println("\nGame Over! You died!");
                break;
            }

            System.out.println("It's your turn, " + survivor.getName() + "!");
            Thread.sleep(1500);
            System.out.println("-- Status --");
            showZombieStatus();
            System.out.println("\nShots remaining: " + shotsRemaining);
            Thread.sleep(1500);

            while (true) {
                System.out.println("\nYo " + survivor.getName() + ", input a number:");
                System.out.println("1. Life is all about gamble!!");

                int playerInput = input.nextInt();

                if (playerInput == 1) {
                    if (weapon instanceof BlankRevolver) {
                        survivor.setHealth(0);
                        weapon.attack(survivor);  // Player mati
                        System.out.println("You gave up on your live...");
                    } else {
                        weapon.attack(zombie);    // Tembak zombie
                    }
                    break;
                } else {
                    System.out.println("Invalid input, just throw your life on the table " + survivor.getName() + ", my guy");
                }
            }

            shotsRemaining--;

            if (zombie.getHealth() <= 0) {
                System.out.println("\nHumanity is saved!");
                break;
            }

            if (zombie.isCloseEnough()) {
                zombie.attackPlayer(survivor);
                System.out.println("\nGame Over! The zombie got you!");
                break;
            }

            if (shotsRemaining == 0 && !zombie.isCloseEnough()) {
                System.out.println("\nGame Over! You've got no ammo left! Just die already");
                break;
            }

            zombie.stepCloser();
        }
    }

    private void playNormalMode() throws InterruptedException {
        int currentPlayerIndex = new Random().nextInt(characters.length);

        while (true) {
            Character playerNow = characters[currentPlayerIndex];

            // Skip kalau player sudah mati
            if (playerNow.getHealth() <= 0) {
                currentPlayerIndex = (currentPlayerIndex + 1) % characters.length;
                continue;
            }

            System.out.println("It's " + playerNow.getName() + "'s turn!");
            Thread.sleep(1500);
            System.out.println("-- Status --");
            showStatus();
            Thread.sleep(1500);

            if (isSinglePlayer && playerNow.getName().equals("Cool Guy")) {
                System.out.println("\nCool Guy is making its move...");
                Thread.sleep(1500);
                weapon.attack(playerNow);
            } else {
                while (true) {
                    System.out.println("\nYo " + playerNow.getName() + ", input a number");
                    System.out.println("1. Life is all about gamble!!");

                    int playerInput = input.nextInt();

                    if (playerInput == 1) {
                        if(weapon instanceof BlankRevolver)
                        {
                            weapon.attack(playerNow);
                            System.out.println("No one is going to get hurt today!");
                            System.exit(0);
                        }
                        else
                        {
                            weapon.attack(playerNow);
                            break;
                        }
                    } else {
                        System.out.println("Invalid input, just throw your life on the table " + playerNow.getName() + ", my guy");
                    }
                }
            }

            // Cek jumlah pemain yang masih hidup
            int aliveCount = 0;
            Character lastAlive = null;
            for (Character c : characters) {
                if (c.getHealth() > 0) {
                    aliveCount++;
                    lastAlive = c;
                }
            }

            if (aliveCount == 1) {
                System.out.println("\nGame Over! " + lastAlive.getName() + " wins!");
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % characters.length;
        }
    }

    private void showStatus() {
        System.out.println("+-------------------------+");
        System.out.printf("| %-10s | %-10s |\n", "Name", "Health");
        System.out.println("+-------------------------+");

        for (Character c : characters) {
            System.out.printf("| %-10s | %-10d |\n", c.getName(), c.getHealth());
        }

        System.out.println("+-------------------------+");
    }

    private void showZombieStatus() {
        System.out.println("Survivor");
        System.out.println("+-------------------------+");
        System.out.printf("| %-10s | %-10s |\n", "Name", "Health");
        System.out.println("+-------------------------+");

        for (Character c : characters) {
            if (!(c instanceof Zombie)) {
                System.out.printf("| %-10s | %-10d |\n", c.getName(), c.getHealth());
            }
        }

        System.out.println("+-------------------------+");

        System.out.println("\nZombie");
        System.out.println("+-------------------------+");
        System.out.printf("| %-10s | %-10s |\n", "Name", "Steps");
        System.out.println("+-------------------------+");

        for (Character c : characters) {
            if (c instanceof Zombie) {
                System.out.printf("| %-10s | %-10d |\n", c.getName(), ((Zombie) c).getSteps());
            }
        }

        System.out.println("+-------------------------+");
    }
}
