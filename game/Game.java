package game;

import character.*;
import weapon.*;
import java.util.Scanner;
import java.util.Random;


public class Game 
{
    Scanner input = new Scanner(System.in);
    Random rand = new Random();

    private Player player1;
    private character.Character player2; // Bisa player atau zombie
    private Weapon weapon;
    private boolean isSinglePlayer;
    private boolean isZombieMode;
    private int shotsRemaining;
    private static final int MAX_SHOTS = 4;

    public Game(Player player1, character.Character player2, Weapon weapon, boolean isSinglePlayer, boolean isZombieMode)
    {
        this.player1 = player1;
        this.player2 = player2;
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
            for(int i = 0; i < 3; i++)
            {
                System.out.println(i + "...");
                Thread.sleep(1000);
            }

            System.out.println("randomizing first turn...");
            Thread.sleep(2500);

            boolean player1Turn = rand.nextBoolean();

            if (isZombieMode) {
                playZombieMode();
            } else {
                playNormalMode(player1Turn);
            }
        }
        catch(InterruptedException e)
        {   
            System.out.println("Game stopped!");
        }
    }

    private void playZombieMode() throws InterruptedException {
        while(player1.getHealth() > 0 && shotsRemaining > 0)
        {
            System.out.println("It's " + player1.getName() + "'s turn!");
            Thread.sleep(1500);
            System.out.println("-- Status --");
            showZombieStatus();
            System.out.println("\nShots remaining: " + shotsRemaining);
            Thread.sleep(1500);

            while(true)
            {
                System.out.println("\n Yo " + player1.getName() + " input a number");
                System.out.println("1. Life is all about gamble!!");

                int playerInput = input.nextInt();

                if(playerInput == 1)
                {
                    if (weapon instanceof BlankRevolver) {
                        survivor.setHealth(0);
                        weapon.attack(survivor);  // Player mati
                        System.out.println("You gave up on your live...");
                    } else {
                        weapon.attack(player2);  // Normal attack on zombie
                    }
                    break;
                }
                else
                {
                    System.out.println("invalid input, just throw your life on the table " + player1.getName() + " my guy");
                }
            }

            shotsRemaining--;
            
            // Cek kalau zombie udah sampai ke player
            if (((Zombie)player2).isCloseEnough()) {
                ((Zombie)player2).attackPlayer(player1);
                System.out.println("\nGame Over! The zombie got you!");
                break;
            }

            if (player1.getHealth() <= 0) {
                System.out.println("\nGame Over! You died!");
                break;
            }

            if (((Zombie)player2).getHealth() <= 0) {
                System.out.println("Humanity is saved!");
                break;
            }

            // Ammo habis
            if (shotsRemaining == 0 && !((Zombie)player2).isCloseEnough()) {
                System.out.println("\nGame Over! You've got no ammo left! Just die already");
                break;
            }
            
            ((Zombie)player2).stepCloser();

        }

    }

    private void playNormalMode(boolean player1Turn) throws InterruptedException {
        while(player1.getHealth() > 0 && ((Player)player2).getHealth() > 0)
        {
            Player playerNow;
            Player otherPlayer;

            if(player1Turn == true)
            {
                playerNow = player1;
                otherPlayer = (Player)player2;
            }
            else
            {
                playerNow = (Player)player2;
                otherPlayer = player1;
            }
            
            System.out.println("It's " + playerNow.getName() + "'s turn!");
            Thread.sleep(1500);
            System.out.println("-- Status --");
            showStatus(player1, (Player)player2);
            Thread.sleep(1500);

            // Kalo single player, AI otomatis attack
            if (isSinglePlayer && playerNow.getName().equals("Cool Guy")) 
            {
                System.out.println("\nCool Guy is making its move...");
                Thread.sleep(1500);
                if (weapon instanceof BlankRevolver) {
                    weapon.attack(playerNow);  // AI dies if using blank revolver
                } else {
                    weapon.attack(otherPlayer);  // Normal attack
                }
            } 
            else {
                while(true)
                {
                    System.out.println("\n Yo " + playerNow.getName() + " input a number");
                    System.out.println("1. Life is all about gamble!!");

                    int playerInput = input.nextInt();

                    if(playerInput == 1)
                    {
                        if (weapon instanceof BlankRevolver) {
                            weapon.attack(playerNow);  // Player dies if using blank revolver
                        } else {
                            weapon.attack(otherPlayer);  // Normal attack
                        }
                        break;
                    }
                    else
                    {
                        System.out.println("invalid input, just throw your life on the table " + playerNow.getName() + " my guy");
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
            else if(aliveCount >= 2){
                System.out.println("No one is going to get hurt today!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }

    private void showStatus(Player p1, Player p2)
    {
        System.out.println("Player 1");
        System.out.println("+---------------+");
        System.out.printf("| %-4s | %-5s |\n", "Name", "Health");
        System.out.println("+---------------+");
        System.out.printf("| %-5s | %-5d |\n", p1.getName(), p1.getHealth());
        System.out.println("+---------------+");

        System.out.println("\nPlayer 2");
        System.out.println("+---------------+");
        System.out.printf("| %-4s | %-5s |\n", "Name", "Health");
        System.out.println("+---------------+");
        System.out.printf("| %-5s | %-5d |\n", p2.getName(), p2.getHealth());
        System.out.println("+---------------+");
    }

    private void showZombieStatus()
    {
        System.out.println("Survivor");
        System.out.println("+---------------+");
        System.out.printf("| %-4s | %-5s |\n", "Name", "Health");
        System.out.println("+---------------+");
        System.out.printf("| %-5s | %-5d |\n", player1.getName(), player1.getHealth());
        System.out.println("+---------------+");

        System.out.println("\nZombie");
        System.out.println("+---------------+");
        System.out.printf("| %-4s | %-5s |\n", "Name", "Steps");
        System.out.println("+---------------+");
        System.out.printf("| %-5s | %-5d |\n", player2.getName(), ((Zombie)player2).getSteps());
        System.out.println("+---------------+");
    }
}
