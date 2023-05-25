import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!

 *  To play this game, create an instance of this class and call the "play"
 *  method.

 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.

 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private static int totalSteps = 10;
    private LifeBar lifeBar;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */

    private void createRooms()
    {
        Room outside, theater, pub, lab, office,underground,refectory,bathroom,deposit;
        Item sabao, goldenDuck,lapis,sapato,papel,lanterna,bola,coracao,balde;


        //create itens for room

        goldenDuck = new GoldenDuck("item raro", 2,"pato");
        sabao = new Item("Lava o corpo",0.5,"sabao");
        sapato = new Item("Bota no pé", 0.4,"sapato");
        lapis = new Item("escreve", 0.4,"lapis");
        papel = new Item(" papel", 0.4,"papel");
        lanterna = new Item("Brilaé", 0.4,"lanterna");
        bola = new Item("Bola", 0.4,"bola");
        coracao = new Coracao("Recebe dois passos", 0.4,"coracao");
        balde = new Item("balde", 0.4,"balde");


        // create the rooms

        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        underground = new Room("in the underground university");
        refectory = new Room("in the refectory university");
        deposit = new Room("in deposit the university");
        bathroom = new Room("in bathroom the university");


        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north",underground);

        underground.setExit("south",outside);
        underground.setExit("west",refectory);
        underground.setExit("east",bathroom);

        bathroom.setExit("south",theater);
        bathroom.setExit("west", underground);

        theater.setExit("west", outside);
        theater.setExit("north",bathroom);
        theater.setExit("south",office);


        refectory.setExit("south",pub);
        refectory.setExit("east",underground);

        pub.setExit("east", outside);
        pub.setExit("south",deposit);
        pub.setExit("north",refectory);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("north", outside);

        deposit.setExit("north",pub);
        deposit.setExit("east",lab);


        office.setExit("west", lab);
        office.setExit("north", theater);

        //create a List of Rooms and List of Items
        Room[] allRooms = {outside, theater, pub, lab, office,underground,refectory,bathroom,deposit};
        Item[] allItems = {sabao, goldenDuck, lapis,sapato,papel,lanterna,bola,coracao,balde};

        List<Item> listItems = new ArrayList<>(Arrays.asList(allItems));

        for(Room sala: allRooms){
            //Creating a random number and random Item from this
            Random gerador = new Random();
            int sizeList = listItems.size();
            int randomNumber;

            randomNumber  = (gerador.nextInt(sizeList));
            Item randomItem = listItems.get(randomNumber);

            sala.addItem(randomItem);
            listItems.remove(randomItem);

        }

        //create a life bar for the player
        lifeBar = new LifeBar();
        lifeBar.setLifebar(totalSteps);

        currentRoom = outside;  // start game outside
    }




    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();


        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println("You have " + getTotalSteps() + " ♥ left. Before the ghost of the university catches up with you ");
        System.out.println(lifeBar.getLifebar());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("check")) {
            System.out.println("The room item is:");
            currentRoom.mostrarItem();
            checaItem(currentRoom);
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
            checaPassos();
            System.out.println("Restam "+ getTotalSteps() + " passos");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    private void checaItem(Room currentRoom) {
        ArrayList<Item> listaItens = currentRoom.getItens();

        for (Item item : listaItens){
            if(item.ehUmCoracao()){
                totalSteps+= 2;
                lifeBar.addHeart();
                lifeBar.addHeart();
                System.out.println("Congratulations you found a Heart. You received two more hearts in your life bar");
                System.out.println(lifeBar.getLifebar());
            }
            else if(item.ehUmPato()){
                System.out.println("The Duck has been found");
                System.out.println("       ___________      ");
                System.out.println("      '._==_==_=_.'     ");
                System.out.println("      .-\\:      /-.    ");
                System.out.println("     | (|:.     |) |    ");
                System.out.println("      '-|:.     |-'     ");
                System.out.println("        \\::.    /      ");
                System.out.println("         '::. .'        ");
                System.out.println("           ) (          ");
                System.out.println("         _.' '._        ");
                System.out.println("        '-------'       ");
                System.exit(0);
            }else{
                System.out.println("The duck is not here");
                System.out.println("  :( ");
                System.out.println(currentRoom.getMediumDescription());
            }
        }
    }


    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            totalSteps--;
            lifeBar.removeHeart();
            if(totalSteps != 0)
                System.out.println(currentRoom.getLongDescription());
                System.out.println(lifeBar.getLifebar());
        }
    }

    public void checaPassos(){
        if (totalSteps == 0){
            System.out.println("The ghost got you.");
            System.out.println("    ___  ");
            System.out.println("  .'   '. ");
            System.out.println(" /  o o  \\");
            System.out.println("|    ^    |");
            System.out.println(" \\  '-'  /");
            System.out.println("  '. ___.' ");
            System.exit(0);
        }
    }

    public static int getTotalSteps() {
        return totalSteps;
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
