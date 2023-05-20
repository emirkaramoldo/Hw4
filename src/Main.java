import java.util.Random;

public class Main {
    public static int bossHealth = 1500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 700, 200, 300, 250, 230};
    public static int[] heroesDamage = {15, 20, 25, 5, 10, 0, 15, 5};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Lucky", "Medic", "Berserk", "Thor"};
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
        thorSlam();
        printBossDamage();
        bossHits();
        heroesHit();
        missLucky();
        berserkBlockDamage();
        golemTakeDamage();
        medicHealing();
        printStatistics();
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
        Random random = new Random();
        int randomHealth = random.nextInt(40 - 10) + 10;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[5] > 0) {
                    heroesHealth[i] += randomHealth;
                    System.out.println("Медик вылечил героя" + randomHealth);
                    break;
            }
            }
        }
    public static void golemTakeDamage() {
        int takeDamage = bossDamage / 5;
        int aliveHeroes = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[3] > 0){
                aliveHeroes ++;
                heroesHealth[i] += takeDamage;
                heroesHealth[3] -= bossDamage - (takeDamage * aliveHeroes);
                if (heroesHealth[4] > 400) {
                    heroesHealth[4] = 400;
                }
            }
            else if (heroesHealth[3] <= 0) {
                heroesHealth[3] = 0;
            }
        }
        System.out.println("Голем поглотил " + (takeDamage * aliveHeroes));
    }

    public static void berserkBlockDamage() {
        int blockTakeDamage = bossDamage / 2;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[6] > 0){
                heroesDamage[6] += blockTakeDamage;
                heroesHealth[6] += blockTakeDamage;
                break;

            }
            else if (heroesHealth[6] <= 0) {
                heroesHealth[6] = 0;
            }
        }
        System.out.println("Берсерк поглотил и прибавил " + blockTakeDamage);
    }

    public static void missLucky() {
        Random random = new Random();
        boolean Luck = true;
            if (heroesHealth[4] > 0) {
                heroesHealth[4] += bossDamage;
        }
    }

    public static void thorSlam() {
        Random random = new Random();
        boolean Slam = random.nextBoolean();
        if (heroesHealth[7] > 0 && Slam==true) {
            bossDamage = 0;
        }
        else{
            bossDamage = 50;
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
    public static void printBossDamage() {
            System.out.println("Урон Босса " + bossDamage);
    }
}
