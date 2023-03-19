
package hammurabi;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    public int population, bushels, land, landValue, numEnter, price, rations, numStarve, yield, workers, capacity, bushelsPlanted;
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    public void playGame() {

        for(int i = 0; i < 10 ; i++) {
            askHowManyAcresToBuy();
            askHowManyAcresToSell();
        }

        population = 100;
        bushels = 2800;
        land = 1000;
        price = 19;
        landValue = land * price;
        numEnter = 0;
        rations = 20;
        yield = 3;
        workers = 8;
        capacity = 25;

    }

    public void printGame() {
        System.out.println("O great Hammurabi");
        System.out.println("You are in year " + numYears + " of your ten year rule");
        System.out.println("In the previous year " + numStarve + " people starved to death");
        System.out.println("In the previous year " + numEnter + " people have entered the kingdom");
        System.out.println("The population is now " + population);
        System.out.println("We harvested " + land * 3 + "at 3 bushels per acre");
        System.out.println("Rats destroyed " + ratsDestroyed + "leaving" + grain + " bushels in storage");
        System.out.println(" The city owns " + land + " acres of land");
        System.out.println("Land is currently worth 19 bushels per acre");

    }


    boolean buyorsell = true;

    public int askHowManyAcresToBuy(int price, int bushels) {
        this.price = price;
        System.out.println("How much land do you want to purchase:?");
        int acres = scanner.nextInt();

        if (acres == 0) {
            System.out.println("You did not buy any acres of land ");
        } else if (acres * price <= bushels) {
            bushels -= acres * price; //how many grains i have minus acres*price(grains)
            land += acres;
            buyorsell = false;
        } else if (acres * price > bushels) { // we dont have enough money
            System.out.println("You do not have enough bushels");
            buyorsell = false;
        }

        return land;
    }

    public int askHowManyAcresToSell(int land) {
        if (buyorsell = false) {
            buyorsell = true;
            return land;
        }

        this.land = land;
        System.out.println("How many acres do you want to sell?");
        int acres = scanner.nextInt();
        if (land > acres) {
            land -= acres;
            bushels += acres * price;
        } else if (land < acres) {
            System.out.println("You do not have enough land");
        } else if (land == acres) {
            System.out.println("you cannot sell the land you live on noob");
        }
        return land;

    }


    public int askHowMuchGrainToFeedPeople(int bushels) {
        this.bushels = bushels;
        System.out.println("How many bushels do you want to feed people dude");
        int feed = scanner.nextInt();
        if (feed > bushels) {
            feed = bushels;
            bushels = 0;
        } else if (feed < bushels) {
            System.out.println("You have fed your colony" + feed + "bushels");
            bushels -= feed;
        }

        if (feed / rations >= population) {
            System.out.println("Everyone survives, you are a fantastic ruler");
            numStarve = 0;
        } else if (feed / rations < population) {
            numStarve = population - feed / rations;
            population = (feed / rations);

            System.out.println("OMG O MY LORD " + numStarve + " PEOPLE HAVE STARVED, WE NEED A REVOLUTION!!");
            System.out.println("You crappy ruler now you only have a population of " + population);
        }
        return feed;
    }

    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        this.land = acresOwned;
        this.population = population;
        this.bushels = bushels;

        System.out.println("How many acres do you want to plant? Insert amount of bushels : ");
        int acresToPlant = scanner.nextInt();

        if (land > acresToPlant && population > acresToPlant * workers && bushels > acresToPlant * capacity) {

            bushels -= acresToPlant * capacity;
            bushelsPlanted = acresToPlant * capacity;

            return bushelsPlanted;

        } else if (land < acresToPlant && population > acresToPlant * workers && bushels > acresToPlant * capacity) {
            System.out.println("You only have " + land + " acres of land");
            bushels -= land * capacity;
            bushelsPlanted = acresToPlant * capacity;

            return bushelsPlanted;
        } else if (land > acresToPlant && population < acresToPlant * workers && bushels > acresToPlant * capacity) {
            System.out.println("you only have " + population + " people, this aint america. dont overwork them") {
                bushels -= population * workers;
                bushels -= population * workers;
                bushelsPlanted = population * workers;

            } else if (land > acresToPlant && population > acresToPlant * yield && bushels < acresToPlant * capacity) {
                System.out.println("You do not have enough " + bushels + " you broke boy");
                bushelsPlanted = bushels * yield;
                bushels = 0;
                return bushelsPlanted;

            } else {
                System.out.println("Kris, if you see this, I cant believe you made us code this, love Hiep");
                return 0;
            }

        }
    }

    int plagueDeaths(int population) {
        rand.nextInt(20);

        if (3 > rand.nextInt(20)) {
            System.out.println("Omg, someone took a massive poop in the sewers, and now we are all plagued :'(");
            population *= 0.5;

        } else {
            System.out.println("Hooray, healthcare is free this year!!!!");
        }
        return population;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        int immigrants = 0;
        if (numStarve == 0) {
            immigrants = (20 * land + bushels) / (100 * population) + 1;

        }
        return immigrants;
    }

    int harvest(int acres, int bushelsUsedAsSeed) {
        return 0;
    }

    int grainsEatenByRats(int bushels) {
        this.bushels = bushels;


        if (2 > rand.nextInt(5)) {
            System.out.println("THE RATS, THE RATS ATE ALL OF OUR FOOD, OH NO!!! THOSE DANG RODENTS");
            bushels -= (bushels * (rand.nextInt(2) + 1) / 10);
        }
        return bushels;
    }

    int newCostOfLand() {
        landValue = rand.nextInt(7) + 17;
        return landValue;
    }


}