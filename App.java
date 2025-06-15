import character.*;
import weapon.*;
import game.*;
import java.util.Scanner;;

public class App 
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        int countPlayer = 1, maxPlayer = 5;

        System.out.println("Russian Roulette V 1.0");
        System.out.println("1. Single Player");
        System.out.println("2. vs Player");
        System.out.print("Enter your choice: ");
        int respon = input.nextInt();
        input.nextLine();
        
        if(respon == 1)
        {
            Player[] player = new Player[2];
            System.out.print("Who should we call you ? ");
            String nama = input.nextLine();
            
            player[0] = new Player("Cool Guy", 5); //bot 
            player[1] = new Player(nama, 5);

            Weapon currWeapon = inputWeapon(input);

            Game game = new Game(new Player[]{player[0], player[1]}, currWeapon, true);
            game.gameStart();
        }
        else if(respon == 2)
        {
            int banyakPlayer;

            System.out.print("Enter number of players (min 2 - max 5): ");
            do {
                banyakPlayer = input.nextInt();
                input.nextLine();
                if (banyakPlayer < 2 || banyakPlayer > maxPlayer) System.out.print("Invalid input. Enter again (2-5): ");
            } while (banyakPlayer < 2 || banyakPlayer > maxPlayer);

            Player[] player = new Player[banyakPlayer];

            for (int i = 0; i < banyakPlayer; i++) {
                System.out.printf("Player %d: ", countPlayer++);
                String nama = input.nextLine();
                player[i] = new Player(nama, 5);
            }

            Weapon currWeapon = inputWeapon(input);
            
            Game game = new Game(player, currWeapon, false);
            game.gameStart();
        }
        else
        {
            System.out.println("Invalid input!");
        }
    }

    private static Weapon inputWeapon(Scanner input) 
    {
        Weapon currWeapon;
        System.out.println("Make a deal! Which weapon will you bring for the duel: ");
        System.out.println("1. Revolver");
        System.out.println("2. Double Barrel");
        while (true) {
            int weaponChoice = input.nextInt();
            input.nextLine();
            if (weaponChoice == 1) {
                currWeapon = new Revolver("Anaconda", 1);
                break;
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