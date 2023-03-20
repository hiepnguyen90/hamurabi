
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
Random rand = new Random();
Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {

        //declaration & initialization of all the variables.
        int year = 1;
        int population = 100;
        int bushels = 2800;
        int land = 1000;
        int landValue = 19;
        int plague = 0;
        int starved = 0;
        int immigrants = 0;
        int bushelsEaten = 0;
        int harvest = 2800;


        System.out.println("========================================================================================================\n" +
                        "Congratulations, you are the newest ruler of ancient Sumer, elected for a ten year term of office.\n" +
                        "Your duties are to dispense food, direct farming, and buy and sell land as needed to support your people. \n" +
                        "Watch out for rat infestiations and the plague! Grain is the general currency, measured in bushels. \n" +
                        "The following will help you in your decisions:\n" +
                        "\n" +
                        "Each person needs at least 20 bushels of grain per year to survive\n" +
                        "Each person can farm at most 10 acres of land\n" +
                        "It takes 2 bushels of grain to farm an acre of land\n" +
                        "The market price for land fluctuates yearly\n" +
                        "Rule wisely and you will be showered with appreciation at the end of your term.\n" +
                        "Rule poorly and you will be kicked out of office!\n"+
                        "========================================================================================================\n" +
                        "\n" +
                        "Press any key to proceed");

        while (true) {
            scan.nextLine();
            break;
        }

        for(year = 1; year <= 10; year++) {
            System.out.println(printSummary(year,plague, starved,immigrants,population,harvest,bushelsEaten,land,landValue));

            int bought = askHowManyAcresToBuy(landValue, bushels);
            land += bought;
            bushels -= bought * landValue;

            if(bought == 0){
                System.out.println("=========================================================================");
                int sold = askHowManyAcresToSell(land);
                land -= sold;
                bushels += sold * landValue;
            }
            System.out.println("========================================================================="); //Divider
            System.out.println("You now have " + bushels + " bushels left.");

            int feed = askHowMuchGrainToFeedPeople(bushels);
            bushels -= feed;

            System.out.println("========================================================================="); //Divider


            int plant = askHowManyAcresToPlant(land, population, bushels);
            bushels -= plant * 2;

            plague = plagueDeaths(population);
            population -= plague;

            starved = starvationDeaths(population, feed);
            population -= starved;

            if(uprising(population, starved)){
                System.out.println("=========================================================================");
                System.out.println("Yo bro, your population starved, you stink at this job");
                break;
            }
            if(starved == 0) {
                System.out.println("=========================================================================");
                immigrants = immigrants(population, land, bushels);
                population += immigrants;
            }

            harvest = harvest(plant);
            bushels += harvest;

            bushelsEaten = grainEatenByRats(bushels);
            bushels -= bushelsEaten;

            landValue = newCostOfLand();


        }
    }


    String printSummary(int year, int plague, int starved, int immigrants, int population, int bushels, int bushelsEaten, int land, int landValue){
        String output = "";
        output += "O great Hammurabi!\n";
        output += "You are in year " + year + " of your ten year rule. \n";
        output += "In the previous year " + plague + " people died from the plague. \n";
        output += "In the previous year " + starved + " people starved to death. \n";
        output += "In the previous year " + immigrants + " people entered the kingdom. \n";
        output += "The population is now " + population + "\n";
        output += "We harvested " + bushels + " bushels. \n";
        output += "Rats destroyed " + bushelsEaten + " bushels, leaving " + bushels + " bushels in storage. \n";
        output += "The city owns " + land + " acres of land. \n";
        output += "Land is currently worth " + landValue + " bushels per acre.\n";

        return output;
    }

    int askHowManyAcresToBuy(int price, int bushels){
        int acres = getNumber("How many acres of land would you like to buy Sire?");
        if(acres * price > bushels) {
            System.out.print("Sorry Sire, you cannot buy that much, for you are poor.");
            acres = 0;
        } else if (bushels > price * acres && acres > 0) {
            System.out.println("Nice doing business with you Sire\n" +
                    "you have purchased " + acres + " acres of land");
        } else {
            System.out.println("You did not buy anything");
            acres = 0;
        }

        return acres;
    }


    int askHowManyAcresToSell(int acresOwned) {
        int acres = getNumber("How many acres do you wish to sell? ");
        if (acres > acresOwned) {
            System.out.println("You don't that many acres to sell!");
            return 0;
        } else if (acres < acresOwned && acres > 0) { //If you have more acres owned than want to sell and you want to sell more than 0
            System.out.println("You sold " + acres + " acres of land!");
            return acres;
        } else {
            System.out.println("You didn't sell any land this year.");
        }
        return 0;

    }
    int askHowMuchGrainToFeedPeople(int bushels){
        int feed = getNumber("Sire, the people are hungry, how much grain do we have to feed them?");
         if(bushels >= feed && feed > 0){
             System.out.println("Sire you have fed your people " + feed + " many of bushels\n");
         } else if (feed > bushels){
             System.out.println("Sire you have fed your people all of your bushels\n");
                feed = bushels;
         } else {
             System.out.println("Sire you did not feed your people, they are going HUNGRY :'(\n");
             feed = 0;
         }
         return feed;
         };

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        int plant = 0;
        int workers = (int) (population * 1);

        while(true){
            plant=getNumber("\nYou have:\n"
                    + "    " + acresOwned + " acres of land\n"
                    + "    " + workers + " workers available\n"
                    + "    " + bushels + " bushels in storage\n"
                    + "How much do you want to plant this season?\n"
                    + "Each acre needs "+ 2 + " bushels and each worker can work on " + 10 + " acres.\n"
                    );


            if(plant>acresOwned){
                System.out.println("Sorry Sire, we do not have enough land\n");
            }
            if(plant>population*10){
                System.out.println("Sorry Sire, we do not have enough people\n");
            }
            if(plant>bushels*2){
                System.out.println("Sorry Sire, we do not have eough bushels\n");
            }
            if(plant < 0) {
                System.out.println("Sir you cannot plant negative crops, WTF");
            }
            if(plant <= population*10 && plant <= acresOwned && plant<= bushels*2 && plant >= 0){
                System.out.println("Sire, we have planted " + plant + " crops, may we have a nice harvest!\n");
                return plant;
            }
        }

    }

    int plagueDeaths(int population){
            int deaths = 0;
                rand.nextInt(20);
            if (3 > rand.nextInt(20)) {
                deaths = (int) (population * 0.5);
            }
            return deaths;
            }

    int starvationDeaths(int population, int bushelsFedToPeople){
        int starve = 0;
        int survivors = bushelsFedToPeople/20;
        if(population > bushelsFedToPeople / 20){
            starve = population - survivors;
        }
        return starve;
        }

    boolean uprising(int population, int howManyPeopleStarved){
        if(howManyPeopleStarved > (int) (population * .45)){
            return true;
        }
            return false;
        }


    int immigrants(int population, int acresOwned, int grainInStorage){
        int immigrants = (int) ((double) (20 * acresOwned + grainInStorage) / (100 * population) + 1);
        return immigrants;
    }

    int harvest(int acres){
        int harvest = acres * (rand.nextInt(6) + 1);
        return harvest;
        }

    int grainEatenByRats(int bushels){
        int bushelsEaten = 0;
        if (2 > rand.nextInt(5)) {
            bushelsEaten = (int) ((double) bushels * (rand.nextInt(20) + 10) / 100);
        }
        return bushelsEaten;
        }

    int newCostOfLand(){
        int landValue = rand.nextInt(7) + 17;
        return landValue;
        }

    int getNumber(String message) {
        while (true) {
            System.err.println(message);
            try {
                return scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scan.next() + "\" isn't a number!\n");
            }
        }
    }


















}
//    public int population, bushels, land, landValue, numEnter, price, rations, numStarve, yield, workers, capacity, bushelsPlanted;
//    Random rand = new Random();
//    Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) {
//        new Hammurabi().playGame();
//    }
//
//    public void playGame() {
//
//        for(int i = 0; i < 10 ; i++) {
//            askHowManyAcresToBuy();
//            askHowManyAcresToSell();
//        }
//
//        population = 100;
//        bushels = 2800;
//        land = 1000;
//        price = 19;
//        landValue = land * price;
//        numEnter = 0;
//        rations = 20;
//        yield = 3;
//        workers = 8;
//        capacity = 0;
//
//    }
//
//    public void printGame() {
//        System.out.println("O great Hammurabi");
//        System.out.println("You are in year " + i + " of your ten year rule");
//        System.out.println("In the previous year " + numStarve + " people starved to death");
//        System.out.println("In the previous year " + numEnter + " people have entered the kingdom");
//        System.out.println("The population is now " + population);
//        System.out.println("We harvested " + land * 3 + "at 3 bushels per acre");
//        System.out.println("Rats destroyed " + ratsDestroyed + "leaving" + bushels + " bushels in storage");
//        System.out.println(" The city owns " + land + " acres of land");
//        System.out.println("Land is currently worth 19 bushels per acre");
//
//    }
//
//
//    boolean buyorsell = true;
//
//    public int askHowManyAcresToBuy(int price, int bushels) {
//        this.price = price;
//        System.out.println("How much land do you want to purchase:?");
//        int acres = scanner.nextInt();
//
//        if (acres == 0) {
//            System.out.println("You did not buy any acres of land ");
//        } else if (acres * price <= bushels) {
//            bushels -= acres * price; //how many grains i have minus acres*price(grains)
//            land += acres;
//            buyorsell = false;
//        } else if (acres * price > bushels) { // we dont have enough money
//            System.out.println("You do not have enough bushels");
//            buyorsell = false;
//        }
//
//        return land;
//    }
//
//    public int askHowManyAcresToSell(int land) {
//        if (buyorsell = false) {
//            buyorsell = true;
//            return land;
//        }
//
//        this.land = land;
//        System.out.println("How many acres do you want to sell?");
//        int acres = scanner.nextInt();
//        if (land > acres) {
//            land -= acres;
//            bushels += acres * price;
//        } else if (land < acres) {
//            System.out.println("You do not have enough land");
//        } else if (land == acres) {
//            System.out.println("you cannot sell the land you live on noob");
//        }
//        return land;
//
//    }
//
//
//    public int askHowMuchGrainToFeedPeople(int bushels) {
//        this.bushels = bushels;
//        System.out.println("How many bushels do you want to feed people dude");
//        int feed = scanner.nextInt();
//        if (feed > bushels) {
//            feed = bushels;
//            bushels = 0;
//        } else if (feed < bushels) {
//            System.out.println("You have fed your colony" + feed + "bushels");
//            bushels -= feed;
//        }
//
//        if (feed / rations >= population) {
//            System.out.println("Everyone survives, you are a fantastic ruler");
//            numStarve = 0;
//        } else if (feed / rations < population) {
//            numStarve = population - feed / rations;
//            population = (feed / rations);
//
//            System.out.println("OMG O MY LORD " + numStarve + " PEOPLE HAVE STARVED, WE NEED A REVOLUTION!!");
//            System.out.println("You crappy ruler now you only have a population of " + population);
//        }
//        return feed;
//    }
//
//    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
//        this.land = acresOwned;
//        this.population = population;
//        this.bushels = bushels;
//
//
//        System.out.println("How many acres do you want to plant? Insert amount of bushels : ");
//        int acresToPlant = scanner.nextInt();
//
//        if (land > acresToPlant && population > acresToPlant * workers && bushels > acresToPlant * capacity) {
//
//            bushels -= acresToPlant * capacity;
//            bushelsPlanted = acresToPlant * capacity;
//
//            return bushelsPlanted;
//
//        } else if (land < acresToPlant && population > acresToPlant * workers && bushels > acresToPlant * capacity) {
//            System.out.println("You only have " + land + " acres of land");
//            bushels -= land * capacity;
//            bushelsPlanted = acresToPlant * capacity;
//
//            return bushelsPlanted;
//        } else if (land > acresToPlant && population < acresToPlant * workers && bushels > acresToPlant * capacity) {
//            System.out.println("you only have " + population + " people, this aint america. dont overwork them");
//
//            bushels -= population * workers;
//            bushels -= population * workers;
//            bushelsPlanted = population * workers;
//            return bushels;
//
//        } else if (land > acresToPlant && population > acresToPlant * yield && bushels < acresToPlant * capacity) {
//            System.out.println("You do not have enough " + bushels + " you broke boy");
//            bushelsPlanted = bushels * yield;
//            bushels = 0;
//            return bushelsPlanted;
//        } else {
//            System.out.println("Kris, if you see this, I cant believe you made us code this, love Hiep");
//            return 0;
//        }
//    }
//
//
//
//
//
//    int plagueDeaths(int population) {
//        rand.nextInt(20);
//
//        if (3 > rand.nextInt(20)) {
//            System.out.println("Omg, someone took a massive poop in the sewers, and now we are all plagued :'(");
//            population *= 0.5;
//
//        } else {
//            System.out.println("Hooray, healthcare is free this year!!!!");
//        }
//        return population;
//    }
//
//    int immigrants(int population, int acresOwned, int grainInStorage) {
//        int immigrants = 0;
//        if (numStarve == 0) {
//            immigrants = (20 * land + bushels) / (100 * population) + 1;
//
//        }
//        return immigrants;
//    }
//
//    int harvest(int acres, int bushelsUsedAsSeed) {
//        this.land = acres;
//        this.bushels = bushelsUsedAsSeed;
//        rand.nextInt(6)+1;
//
//
//        return
//    }
//
//    int grainsEatenByRats(int bushels) {
//        this.bushels = bushels;
//
//
//        if (2 > rand.nextInt(5)) {
//            System.out.println("THE RATS, THE RATS ATE ALL OF OUR FOOD, OH NO!!! THOSE DANG RODENTS");
//            bushels -= (bushels * (rand.nextInt(2) + 1) / 10);
//        }
//        return bushels;
//    }
//
//    int newCostOfLand() {
//        landValue = rand.nextInt(7) + 17;
//        return landValue;
//    }
//
//
//}
