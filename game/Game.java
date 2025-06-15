package game;

import character.*;
import weapon.*;
import java.util.Scanner;
import java.util.Random;

public class Game 
{
    Scanner input = new Scanner(System.in);
    Random rand = new Random();

    private Player[] player;
    private Weapon weapon;
    private boolean isSinglePlayer;

    public Game(Player[] player, Weapon weapon, boolean isSinglePlayer)
    {
        this.player = player;
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

            int currentIndex = rand.nextInt(player.length);

            while (countPlayerActive() > 1) {
                Player currentPlayer = player[currentIndex];

                if (currentPlayer.getHealth() <= 0)
                {
                    currentIndex = (currentIndex + 1) % player.length;
                    continue;
                }

                System.out.println("\nIt's " + currentPlayer.getName() + "'s turn!");
                Thread.sleep(1500);
                System.out.println("-- Status --");
                showStatus(player);
                Thread.sleep(1500);

                Player target = getPlayerActive(currentPlayer);
                if (target == null) 
                {
                    System.out.println("No valid targets remaining.");
                    break;
                }

                if (isSinglePlayer && currentPlayer.getName().equals("Cool Guy")) 
                {
                    System.out.println("\nCool Guy is making its move...");
                    Thread.sleep(1500);
                    weapon.attack(target);
                } 
                else 
                {
                    while (true)
                    {
                        System.out.println("\nYo " + currentPlayer.getName() + ", input a number:");
                        System.out.println("1. Life is all about gamble!!");

                        int inputOption = input.nextInt();
                        if (inputOption == 1)
                        {
                            weapon.attack(target);
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid input, try again.");
                        }
                    }
                }

                if (countPlayerActive() == 1)
                {
                    Player winner = getWinnerPlayer();
                    System.out.println("\nGame Over! " + winner.getName() + " wins!");
                    break;
                }

                currentIndex = (currentIndex + 1) % player.length;
            }
        }
        catch(InterruptedException e)
        {   
            System.out.println("Game stopped!");
        }
    }

    private int countPlayerActive() {
        int count = 0;
        for (Player p : player) {
            if (p.getHealth() > 0) count++;
        }
        return count;
    }

    private Player getWinnerPlayer() {
        for (Player p : player) {
            if (p.getHealth() > 0) return p;
        }
        return null;
    }

    private Player getPlayerActive(Player currentPlayer) {
        for (Player p : player) {
            if (!p.equals(currentPlayer) && p.getHealth() > 0) {
                return p;
            }
        }
        return null;
    }

    private void showStatus(Player[] player)
    {
        int countPlayer = 1;
        for (Player p : player) {
            System.out.println("Player " + countPlayer++);
            System.out.println("+---------------+");
            System.out.printf("| %-4s | %-5s |\n", "Name", "Health");
            System.out.println("+---------------+");
            System.out.printf("| %-5s | %-5d |\n", p.getName(), p.getHealth());
            System.out.println("+---------------+");
        }
    }
}
