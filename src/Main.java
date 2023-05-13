import java.util.Random;

public class Main {
    public static int bossHealth = 1500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {470, 460, 450, 600, 800, 400};
    public static int[] heroesDamage = {10, 15, 20, 0, 15, 20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        missLuckky();
        heroesHit();
        printStatistics();
        golemTakeDamage();
        medicHealing();
        printHeath();
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicHealing() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                if (heroesHealth[3] < 100) {
                    heroesHealth[3] = heroesHealth[3] - bossDamage;
                }
                else {
                    Random random = new Random();
                    int randomHealth = random.nextInt(40-10) + 10;
                    heroesHealth[(int)Math.floor(Math.random() * heroesHealth.length)]  += randomHealth;
                    System.out.println("Медик вылечил героя" + randomHealth);
                    break;
                }
                }
            else if (heroesHealth[3] < 0){
                heroesHealth[3] = 0;
                heroesHealth[i] -= bossDamage;
            }
            }
        }
    public static void golemTakeDamage() {
        int takeDamage = bossDamage / 5;
        int aliveHeroes = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[4] > 0){
                aliveHeroes ++;
                heroesHealth[i] -= bossDamage - takeDamage;
                heroesHealth[4] -= bossDamage - (takeDamage * aliveHeroes);

            }
            else if (heroesHealth[4] <= 0) {
                heroesHealth[4] = 0;
            }
        }
        System.out.println("Голем поглотил" + (takeDamage * aliveHeroes));
    }

    public static void missLuckky() {
//        Random random = new Random();
        boolean Luck = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[5] > 0) {
                heroesHealth[5] += 50;
                break;
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
      /*  if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " -------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " +
                bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
    public static void printHeath() {
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(" health: " + heroesHealth[i]);
        }
    }
}
