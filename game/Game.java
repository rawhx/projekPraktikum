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
    private Player player2;
    private Weapon weapon;
    private boolean isSinglePlayer;

    public Game(Player player1, Player player2, Weapon weapon, boolean isSinglePlayer)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.weapon = weapon;
        this.isSinglePlayer = isSinglePlayer;
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

            while(player1.getHealth() > 0 && player2.getHealth() > 0)
            {
                Player playerNow;
                Player otherPlayer;

                if(player1Turn == true)
                {
                    playerNow = player1;
                    otherPlayer = player2;
                }
                else
                {
                    playerNow = player2;
                    otherPlayer = player1;
                }
                
                System.out.println("It's " + playerNow.getName() + "'s turn!");
                Thread.sleep(1500);
                System.out.println("-- Status --");
                showStatus(player1, player2);
                Thread.sleep(1500);

                // Kalo single player, AI otomatis attack
                if (isSinglePlayer && playerNow.getName().equals("Cool Guy")) 
                {
                    System.out.println("\nCool Guy is making its move...");
                    Thread.sleep(1500);
                    weapon.attack(otherPlayer);
                } 
                else {
                    while(true)
                    {
                        System.out.println("\n Yo " + playerNow.getName() + " input a number");
                        System.out.println("1. Life is all about gamble!!");

                        int playerInput = input.nextInt();

                        if(playerInput == 1)
                        {
                            weapon.attack(otherPlayer);
                            break;
                        }
                        else
                        {
                            System.out.println("invalid input, just throw your life on the table " + playerNow.getName() + " my guy");
                        }
                    }
                }

                // Kalau salah satu player mati, game over
                if (otherPlayer.getHealth() <= 0) {
                    System.out.println("\nGame Over! " + playerNow.getName() + " wins!");
                    break;
                }

                player1Turn = !player1Turn;
            }
        }
        catch(InterruptedException e)
        {   
            System.out.println("Game stopped!");
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
}
