//Angel Grajeda-Cervantes (lines 1-55)
//Andreas Hitt            (lines 56-110) 
//Jared Lee               (lines 111-end)
//1/21/25
//Assignment to update blackjack, 

//This code imports the Random class, which is used to generate random numbers.
import java.util.Random;
//This code imports the Scanner class, which is used to get user input.
import java.util.Scanner;
//(project line 11-14 and 20-24)
  import java.util.HashMap;
  import java.util.LinkedList;
  import java.util.Queue;

public class BlackJack {

    

    // (Project line 11-14 and 20-24))Angel Grajeda-Cervantes: Data structures for player profiles, hand history, and waiting list.
    static HashMap<String, PlayerProfile> profiles = new HashMap<>();
    static Queue<String> waitingPlayers = new LinkedList<>();
    static LinkedList<String> playerHandHistory = new LinkedList<>();
    static LinkedList<String> dealerHandHistory = new LinkedList<>();

    // (Project line 26-61)Angel Grajeda-Cervantes: Player Profile Management (HashMap)
public static PlayerProfile getOrCreateProfile(String name) {
    PlayerProfile profile = profiles.get(name);
    if (profile == null) {
        profile = new PlayerProfile(name);
        profiles.put(name, profile);
        System.out.println("New profile created for " + name);
    } else {
        System.out.println("Welcome back, " + name + "!");
    }
    return profile;
}

// (Project)Angel Grajeda-Cervantes: Waiting List Management (Queue)
public static void addToWaitingList(java.util.Scanner scanner) {
    System.out.print("Enter your name to join the waiting list: ");
    String name = scanner.nextLine();
    waitingPlayers.add(name);
    System.out.println(name + " added to the waiting list.");
}

public static String getNextPlayer() {
    return waitingPlayers.poll();
}

// Angel Grajeda-Cervantes: Hand History Tracking (LinkedList)
public static void addCardToHand(LinkedList<String> hand, String card) {
    hand.add(card);
}

public static void showHandHistory() {
    System.out.println("Player's hand: " + playerHandHistory);
    System.out.println("Dealer's hand: " + dealerHandHistory);
    playerHandHistory.clear();
    dealerHandHistory.clear();
}

    //constants - cannot change theur values
    //Static - I can use these in every function without having to pass them in
    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    private static final int[] DECK = new int[52];
    private static int currentCardIndex = 0;

    // Andreas: Added variables to store each persons' wins and print them out at the end of the match 
    public static int playerWins, dealerWins;
//(project line 73-101)
   public static void main(String[] args) {
    java.util.Scanner scanner = new java.util.Scanner(System.in);

    // Add player to waiting list
    addToWaitingList(scanner);

    // Get next player from queue
    String playerName = getNextPlayer();
    PlayerProfile profile = getOrCreateProfile(playerName);

    // Minimal round: deal two cards to player and dealer, track in hand history
    playerHandHistory.clear();
    dealerHandHistory.clear();

    // Example: just use random cards for demonstration
    addCardToHand(playerHandHistory, "Ace of Spades");
    addCardToHand(playerHandHistory, "7 of Hearts");
    addCardToHand(dealerHandHistory, "10 of Clubs");
    addCardToHand(dealerHandHistory, "6 of Diamonds");

    // Show hand history
    showHandHistory();

    // Update profile stats (simulate a win)
    profile.wins++;
    profile.chips += 10;
    System.out.println("Stats for " + playerName + ": Wins: " + profile.wins + ", Losses: " + profile.losses + ", Chips: " + profile.chips);
}
    //initializing the deck integers from 0-51
    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }
    //Loads the array with randomized integers
    private static void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < DECK.length; i++) {
            //Swapping two integers within the array, creates temp variable to hold the value while swapping
            int index = random.nextInt(DECK.length);
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }
        //iterates through the array to print the new randomized numbers 
        //the two print functions below use println so the output is very readable as a player
        //maybe too readable, players can cheat by seeing the later cards in the deck, should be commented out before use
        /*
        System.out.println("printed deck");
        for (int i = 0; i < DECK.length; i++) {
            System.out.println(DECK[i] + " ");
        }
        */
    }
    //assigns the player's first two cards, the array has each value randomized then the mod and division operators are used on each array position
    //to find the card's number/royalty and suit, with the results being printed and then returned by the method 
    private static int dealInitialPlayerCards() {
        int card1 = dealCard();
        int card2 = dealCard();
        System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4] + " and "
                + RANKS[card2] + " of " + SUITS[card2 / 13]);
        return cardValue(card1) + cardValue(card2);
    }
    //Similar method to dealInitialPlayerCards() but the dealer only is dealt one card
    private static int dealInitialDealerCards() {
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4]);
        return cardValue(card1);
    }
    //the 'main' part of the game, inputs are the players input + the total of their current deck
    //allows player to decide 
    private static int playerTurn(Scanner scanner, int playerTotal) {
        //while (true) loop continues until break; which depends on player action 
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to stand?");
            //detect and operate on player action using scanner and if/else 
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                //Testing only
                //System.out.println("new card index is " + newCard);
                System.out.println("You drew a " + RANKS[newCard] + " of " + SUITS[DECK[currentCardIndex] % 4]);
                if (playerTotal > 21) {
                    break;
                    //when player total is greater than 21, does not immediately notify of loss but will exit this loop, with next method deciding the outcome of the game.
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }
     //the code represents dealers turn who will automatically add a new card to their hand if their hand is <17
     private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            int newCard = dealCard();
            dealerTotal += cardValue(newCard);
        }
        //at most, the highest value added will be 10        
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }

        
    // the winner has three potential results: player wins tie, and dealer wins
    private static boolean determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            playerWins++;
            System.out.println("You win!");
            System.out.println("You: " + playerWins);
            System.out.println("Dealer: " + dealerWins);
            return askToPlayAgain();
                
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
            return askToPlayAgain();
        } else {
            dealerWins++;
            System.out.println("Dealer wins!");
            System.out.println("You: " + playerWins);
            System.out.println("Dealer: " + dealerWins);
            return askToPlayAgain();
        }
    }
    
    //check if the player wants to play again
    public static boolean askToPlayAgain(){
        System.out.println("Play again? Yes/No ");

        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        //scanner.close();
        
        if (userResponse.equalsIgnoreCase("yes")){
            //main(new String[0]);
            return true;
        } else if (userResponse.equalsIgnoreCase("no")) {
            System.out.println("Thanks for playing! Goodbye.");
            return false;
        } else {
            System.out.println("Invalid response. Please type \"yes\" or \"no\".");
            askToPlayAgain();
        }
        return true;
    }


    private static int dealCard() {
        return DECK[currentCardIndex++] % 13; //index value is incremented of original value everytime method is called
    }
    
        //the cardValue method returns the card 
    private static int cardValue(int card) { //"card" is the value returned from dealCard method
        return card < 9 ? + 2 :  10; // the card returned is going to be __+2 if less than nine, otherwise will be 10
    }
 // this method is not used throughout main    
    int linearSearch(int[] numbers, int key) {
        int i = 0;
        for (i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i; //returns original array value or null value
            }
        }
        return -1; // not found
    }
}