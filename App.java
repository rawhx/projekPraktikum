import character.*;
import character.Character;
import weapon.*;
import game.*;

import java.util.Random;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) 
    {
        int MAXPLAYER = 5;
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

            Character[] characters = new Character[] { p, bot };

            Weapon wp = inputWeapon(input);

            Game game = new Game(characters, wp, true, false);
            game.gameStart();
        }
        else if(respon == 2)
        {
            int countPlayer = 0;
           while (true) {
                System.out.print("Masukkan jumlah pemain (min 2, max " + MAXPLAYER + "): ");
                countPlayer = input.nextInt();
                input.nextLine(); // bersihkan newline

                if (countPlayer >= 2 && countPlayer <= MAXPLAYER) {
                    break;
                } else {
                    System.out.println("Jumlah pemain tidak valid!");
                }
            }

            Character[] characters = new Character[countPlayer];
            
            for (int i = 0; i < countPlayer; i++) {
                System.out.print("Player " + (i + 1) + ": ");
                String nama = input.nextLine();
                characters[i] = new Player(nama, 5);
            }
           
            Weapon wp = inputWeapon(input);
            
            Game game = new Game(characters, wp, false, false);
            game.gameStart();
        }
        else if(respon == 3)
        {
            System.out.print("Enter your name, survivor: ");
            String nama = input.nextLine();
            Player survivor = new Player(nama, 5);
            Zombie zombie = new Zombie();

            Character[] characters = new Character[] { survivor, zombie };

            Weapon wp = inputWeapon(input);

            Game game = new Game(characters, wp, false, true); // mode zombie
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
