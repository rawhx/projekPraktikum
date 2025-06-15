import character.*;
import weapon.*;
import game.*;
import java.util.Scanner;;

public class App 
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Russian Roulette V 1.0");
        System.out.println("1. Single Player");
        System.out.println("2. vs Player");
        int respon = input.nextInt();
        input.nextLine();
        
        if(respon == 1)
        {
            System.out.print("Who should we call you ? ");
            String nama = input.nextLine();
            
            Player bot = new Player("Cool Guy", 5);
            Player p = new Player(nama, 5);
            
            Revolver revolver = new Revolver("Anaconda", 1);

            Game game = new Game(p, bot, revolver);
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

            Revolver revolver = new Revolver("Anaconda", 1);

            Game game = new Game(p1, p2, revolver);
            game.gameStart();
        }
        else
        {
            System.out.println("Invalid input!");
        }
    }
}