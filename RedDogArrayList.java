//Raja Hammad Mehmood.
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The casino game Red Dog, for multiple players using ArrayList.
 */

public class RedDogArrayList {

    /**
     *This function removes the player by name
     * @param1 is the arraylist of type RedDogPlayer
     * @param2 is the name to be removed in the array
     */
    public static void removePlayerByName ( ArrayList<RedDogPlayer> players, String removename) {
        for ( int i = 0 ; i < players.size() ; i++ ) {
            if (((players.get(i)).getName()).equals(removename)) {
                players.remove(i);
                break;
            }

        }

    }

    /**
     *This function removes the player who have no money left
     * @param1 is the arraylist of type RedDogPlayer
     */
    public static void removeBankruptPlayers ( ArrayList<RedDogPlayer> players) {
        for ( int i = 0 ; i < players.size() ; i++ ) {
            if ((players.get(i)).getMoney()==0) {
                players.remove(i);
                i=i-1;
            }

        }

    }



    /**
     * Compute the point spread between two cards. Precondition: card1.getValue()
     * != card2.getValue()
     *
     * @param card1
     *          the first card
     * @param card2
     *          the second card
     * @return the point spread between the cards
     */
    public static int computeSpread ( Card card1, Card card2 ) {
        if ( card1.getValue() == card2.getValue() ) {
            throw new IllegalArgumentException("card values must be different; got "
                                               + card1 + " " + card2);
        }

        if ( card1.getValue() < card2.getValue() ) {
            return card2.getValue() - card1.getValue() - 1;
        } else {
            return card1.getValue() - card2.getValue() - 1;
        }
    }

    /**
     * Determine the payoff for a given spread and bet.
     *
     * @param spread
     *          the point spread (spread >= 1)
     * @param bet
     *          the player's bet
     * @return the payoff
     */
    public static int determinePayoff ( int spread, int bet ) {
        if ( spread < 1 ) {
            throw new IllegalArgumentException("spread must be >= 1; got " + spread);
        }

        if ( spread == 1 ) {
            return 5 * bet;
        } else if ( spread == 2 ) {
            return 4 * bet;
        } else if ( spread == 3 ) {
            return 2 * bet;
        } else {
            return bet;
        }
    }

    /**
     * Determine if the third card's value is between the other two.
     *
     * @param card1
     *          the first card
     * @param card2
     *          the second card
     * @param card3
     *          the third card
     * @return true if the third card's value is between the other two, false
     *         otherwise
     */
    public static boolean isBetween ( Card card1, Card card2, Card card3 ) {
        if ( card3.getValue() < card1.getValue()
                && card3.getValue() > card2.getValue() ) {
            return true;
        } else if ( card3.getValue() < card2.getValue()
                    && card3.getValue() > card1.getValue() ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prompt the players for their bets.
     *
     * @param players
     *          the players
     * @param scanner
     *          scanner for reading user input
     */
    public static void getBets ( ArrayList<RedDogPlayer> players, Scanner scanner ) {
        for ( int ctr = 0 ; ctr < players.size() ; ctr++ ) {
            System.out.println((players.get(ctr)).getName() + ", you have $"
                               + (players.get(ctr)).getMoney());

            int bet; // the player's bet
            for ( ; true ; ) {
                System.out.print((players.get(ctr)).getName() + ", make a bet: ");
                bet = scanner.nextInt();
                scanner.nextLine();
                if ( bet <= 0 || bet > (players.get(ctr)).getMoney() ) {
                    System.out.println("  bet must be between 0 and "
                                       + (players.get(ctr)).getMoney());
                } else {
                    break;
                }
            }
            (players.get(ctr)).raiseBet(bet);
        }
    }

    /**
     * Prompt the players to raise their bets.
     *
     * @param players
     *          the players, with bets already made
     * @param scanner
     *          scanner for reading user input
     */
    public static void raiseBets ( ArrayList<RedDogPlayer> players, Scanner scanner ) {
        for ( int ctr = 0 ; ctr < players.size() ; ctr++ ) {
            System.out.println((players.get(ctr)).getName() + ", your bet is $"
                               + (players.get(ctr)).getBet() + " and you have $"
                               + (players.get(ctr)).getMoney());
            int raise; // the player's raise
            for ( ; true ; ) {
                System.out.print((players.get(ctr)).getName()
                                 + ", raise your bet: (0 if you don't want to raise) ");
                raise = scanner.nextInt(); // amount bet is raised
                scanner.nextLine();
                if ( raise < 0 ) {
                    System.out.println("  raise must be non-negative");
                } else if ( raise > (players.get(ctr)).getBet()
                            || raise > (players.get(ctr)).getMoney() - (players.get(ctr)).getBet() ) {
                    System.out.println("  raise is too large");
                } else {
                    break;
                }
            }
            (players.get(ctr)).raiseBet(raise);
        }
    }

    /**
     * Play one round of Red Dog
     *
     * @param players
     *          the players in the game
     * @param scanner
     *          scanner for reading user input
     */
    public static void play ( ArrayList<RedDogPlayer> players, Scanner scanner ) {
        System.out.println();

        // get the players' bets
        getBets(players,scanner);
        System.out.println();

        // dealer shuffles the deck
        Deck deck = new Deck(); // the deck
        deck.shuffle();

        // dealer deals two cards
        Card card1 = deck.dealCard(); // first card dealt
        Card card2 = deck.dealCard(); // second card dealt
        System.out.println("dealer deals " + card1 + " and " + card2);
        System.out.println();

        // determine payoff --
        // payoff depends on whether the two cards dealt have the same value,
        // have adjacent values, or have more widely separated values
        if ( card1.getValue() == card2.getValue() ) { // same value
            // deal a third card
            Card card3 = deck.dealCard(); // third card dealt
            System.out.println("dealer deals " + card3);

            // payoff if the third card has the same value as the other two,
            // otherwise no one gains or loses money
            if ( card3.getValue() == card1.getValue() ) {
                for ( int ctr = 0 ; ctr < players.size() ; ctr++ ) {
                    int payoff = 11 * (players.get(ctr)).getBet();
                    System.out.println((players.get(ctr)).getName() + ", you win $" + payoff);
                    (players.get(ctr)).payoff(payoff);
                }
            } else {
                System.out.println("third card is different - no payoff");
                for ( int ctr = 0 ; ctr < players.size() ; ctr++ ) {
                    (players.get(ctr)).payoff(0); // reset bets
                }
            }

        } else if ( card1.getValue() - card2.getValue() == 1
                    || card2.getValue() - card1.getValue() == 1 ) { // adjacent
            // values
            // no one gains or loses money
            System.out.println("cards are adjacent - no payoff");
            for ( int ctr = 0 ; ctr < players.size() ; ctr++ ) {
                (players.get(ctr)).payoff(0); // reset bets
            }

        } else { // values differ by more than 1
            // allow the player to raise her bet
            raiseBets(players,scanner);
            System.out.println();

            // deal a third card
            Card card3 = deck.dealCard(); // third card dealt
            System.out.println("dealer deals " + card3);

            // payoff if the third card's value is between the other two,
            // otherwise everyone loses their bets
            if ( isBetween(card1,card2,card3) ) {
                System.out.println("everyone wins!");
                for ( int ctr = 0 ; ctr < players.size() ; ctr++ ) {
                    int payoff =
                        determinePayoff(computeSpread(card1,card2),(players.get(ctr)).getBet());
                    System.out.println((players.get(ctr)).getName() + ", you win $" + payoff);
                    (players.get(ctr)).payoff(payoff);
                }
            } else {
                System.out.println("everyone loses");
                for ( int ctr = 0 ; ctr < players.size() ; ctr++ ) {
                    (players.get(ctr)).loseBet();
                }
            }
        }
    }

    public static void main ( String[] args ) {

        Scanner scanner = new Scanner(System.in);

        // get the initial number of players
        int initplayers; // initial number of players
        System.out.print("how many players?  ");
        for ( ; true ; ) {
            initplayers = scanner.nextInt();
            scanner.nextLine();
            if ( initplayers < 1 ) {
                System.out.println("  must be at least one player");
            } else {
                break;
            }
        }

        // create the initial set of players
        ArrayList<RedDogPlayer> players = new ArrayList<RedDogPlayer>();
        for ( int ctr = 0 ; ctr < initplayers ; ctr++ ) {
            String name; // current player's name
            System.out.print("enter player " + (ctr + 1) + "'s name: ");
            name = scanner.nextLine();
            RedDogPlayer member= new RedDogPlayer(name,100);
            players.add(member);
        }

        // play the game
        for ( ; true ; ) {
            play(players,scanner);
            removeBankruptPlayers(players);
            // removing players as user enters their names
            while(true) {
                System.out.println("Enter the name of the player you want to remove. To stop enter '-'");
                String removename= scanner.nextLine();
                if(removename.equals("-")) {
                    break;
                }
                removePlayerByName (players,removename);
            }
            //adding player
            while(true) {
                System.out.println("Enter the name of the player you want to add. To stop enter '-'");
                String addname= scanner.nextLine();
                if(addname.equals("-")) {
                    break;
                }
                RedDogPlayer add=new RedDogPlayer(addname,100);
                players.add(add);

            }

            //checking if no players left
            if(players.size()==0) {
                System.out.println("No players left");
                break;
            }
        }
    }
}
