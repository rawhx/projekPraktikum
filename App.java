import character.*;
import weapon.*;
import game.*;

import java.util.Random;
import java.util.Scanner;;

public class App 
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Russian Roulette V 1.0");
        System.out.println("1. Single Player (vs AI)");
        System.out.println("2. vs Player");
        System.out.println("3. Zombie Survival Mode");
        System.out.print("Enter your choice: ");

        int respon = input.nextInt();
        input.nextLine();
        
        if(respon == 1)
        {
            System.out.print("Who should we call you ? ");
            String nama = input.nextLine();
            
            Player bot = new Player("Cool Guy", 5);
            Player p = new Player(nama, 5);

            Weapon wp = inputWeapon(input);
            
            Game game = new Game(p, bot, wp, true, false);
            game.gameStart();    
        }
        else if(respon == 2)
        {
            System.out.print("Player 1: ");
            String namaP1 = input.nextLine();
            Player p1 = new Player(namaP1, 5);

            System.out.print("Player 2: ");
            String namaP2 = input.nextLine();
            Player p2 = new Player(namaP2, 5);

            Weapon wp = inputWeapon(input);
            
            Game game = new Game(p1, p2, wp, false, false);
            game.gameStart();
        }
        else if(respon == 3)
        {
            System.out.print("Enter your name, survivor: ");
            String nama = input.nextLine();
            Player survivor = new Player(nama, 5);
            Zombie zombie = new Zombie();
            
            Weapon wp = inputWeapon(input);

            Game game = new Game(survivor, zombie, wp, false, false);
            game.gameStart();
        }
    }

    private static Weapon inputWeapon(Scanner input) 
    {
        System.out.println("Choose your weapon: ");
        System.out.println("1. Revolver");
        System.out.println("2. Double Barrel");
        Weapon currWeapon;

        while (true) {
            int weaponChoice = input.nextInt();
            input.nextLine();

            if (weaponChoice == 1) {
                Random rand = new Random();
                boolean luck = rand.nextBoolean();
                if (luck) {
                    currWeapon = new Revolver("Anaconda", 1);
                    break;
                } else {
                    currWeapon = new BlankRevolver("Blank Revolver");
                    break;
                }
            }
            else if (weaponChoice == 2) {
                currWeapon = new DoubleBarrel("Double Barrel", 2);
                break;
            }
            else {
                System.out.println("Invalid input!");
                continue;
            }
        }

        return currWeapon;

    }
}
