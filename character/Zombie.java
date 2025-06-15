package character;

public class Zombie extends Character {
    private int steps;
    private static final int MAX_STEPS = 4;

    public Zombie() {
        super("Zombie", 1);
        this.steps = 0;
    }

    public void stepCloser() {
        steps++;
        System.out.println("The zombie takes a step closer... (" + steps + "/" + MAX_STEPS + " steps)");
    }

    public boolean isCloseEnough() {
        return steps >= MAX_STEPS;
    }

    public void attackPlayer(Player player) {
        System.out.println("The zombie has reached you! It's too late...");
        player.setHealth(0);
    }

    public int getSteps() {
        return steps;
    }
} 