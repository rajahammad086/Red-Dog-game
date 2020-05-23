// Raja Hammad Mehmood.
import java.util.Scanner;

/**
 * The casino game Red Dog, for multiple players.
 */

public class RedDogArray {
    /**
     *This function removes the player at a position
     * @param1 is the array in which we have to remove a player
     * @param2 is the number of players in that array
     * @param3 is the position to be removed in the array
     * @return is the number of players removed
     */
    public static int removePlayer ( RedDogPlayer[] players,int numplayers, int removeposition) {
        if ( removeposition < 0 || removeposition >= numplayers ) {
            throw new IllegalArgumentException("index out of range; got "+
                                               removeposition);
        }

        for ( int hole = removeposition ; hole < numplayers-1 ; hole++ ) {
            // shift one tile over
            players[hole] = players[hole+1];
        }


        return 1;

    }

    /**
     *This function removes the player by name
     * @param1 is the array in which we have to remove a player
     * @param2 is the number of players in that array
     * @param3 is the name to be removed in the array
     * @return is the number of players removed
     */
    public static int removePlayerByName ( RedDogPlayer[] players,int numplayers, String removename) {
        int position=-1;
        for ( int i = 0 ; i < numplayers ; i++ ) {
            if ((players[i].getName()).equals(removename)) {
                position=i;
                break;
            }

        }
        if (position!=-1) {
            removePlayer(players,numplayers,position);
            return 1;
        } else {
            return 0;
        }
    }

    /**
     *This function removes the player who have no money left
     * @param1 is the array in which we have to remove players
     * @param2 is the number of players in that array
     * @return is the number of players removed
     */
    public static int removeBankruptPlayers ( RedDogPlayer[] players,int numplayers) {
        int number=0;
        for ( int i = 0 ; i < numplayers ; i++ ) {
            if (players[i].getMoney()==0) {
                removePlayer(players,numplayers,i);
                i=i-1;
                number++;
            }

        }
        return number;
    }

    /**
    *This subroutine grows the function (twice its capacity)
    * @param1 is the array which we have to increase
    */
    public static RedDogPlayer[] grow(RedDogPlayer[] players) {
        RedDogPlayer[] newplayers = new RedDogPlayer[players.length*2];
        for ( int j = 0 ; j < players.length ; j++ ) {
            newplayers[j] = players[j];
        }
        players = newplayers;
        return players;
    }

    /**
    *This function adds the player to the array and gives it the first slot in the array
    * @param1 is the array in which we have to add players
    * @param2 is the number of players in that array
    * @param3 is the name of players
    * @param2 is the initial amount of money
    * @return is the number of players added
    */
    public static int addPlayer(RedDogPlayer[] players, int numplayers, String name, int money) {
        RedDogPlayer[] newplayers = new RedDogPlayer[players.length];
        for ( int j = 0 ; j < players.length ; j++ ) {
            newplayers[j] = players[j];
        }
        for(int i=0; i<numplayers; i++) {
            players[i+1]=newplayers[i];
        }
        RedDogPlayer add=new RedDogPlayer(name,money);
        players[0]=add;
        return 1;
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
    public static void getBets ( RedDogPlayer[] players, int numplayers, Scanner scanner ) {
        for ( int ctr = 0 ; ctr < numplayers ; ctr++ ) {
            System.out.println(players[ctr].getName() + ", you have $"
                               + players[ctr].getMoney());

            int bet; // the player's bet
            for ( ; true ; ) {
                System.out.print(players[ctr].getName() + ", make a bet: ");
                bet = scanner.nextInt();
                scanner.nextLine();
                if ( bet <= 0 || bet > players[ctr].getMoney() ) {
                    System.out.println("  bet must be between 0 and "
                                       + players[ctr].getMoney());
                } else {
                    break;
                }
            }
            players[ctr].raiseBet(bet);
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
    public static void raiseBets ( RedDogPlayer[] players, int numplayers, Scanner scanner ) {
        for ( int ctr = 0 ; ctr < numplayers ; ctr++ ) {
            System.out.println(players[ctr].getName() + ", your bet is $"
                               + players[ctr].getBet() + " and you have $"
                               + players[ctr].getMoney());
            int raise; // the player's raise
            for ( ; true ; ) {
                System.out.print(players[ctr].getName()
                                 + ", raise your bet: (0 if you don't want to raise) ");
                raise = scanner.nextInt(); // amount bet is raised
                scanner.nextLine();
                if ( raise < 0 ) {
                    System.out.println("  raise must be non-negative");
                } else if ( raise > players[ctr].getBet()
                            || raise > players[ctr].getMoney() - players[ctr].getBet() ) {
                    System.out.println("  raise is too large");
                } else {
                    break;
                }
            }
            players[ctr].raiseBet(raise);
        }
    }

    /**
     * Play one round of Red Dog.
     *
     * @param players
     *          the players in the game
     * @param scanner
     *          scanner for reading user input
     */
    public static void play ( RedDogPlayer[] players, int numplayers, Scanner scanner ) {
        System.out.println();

        // get the players' bets
        getBets(players,numplayers,scanner);
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
                for ( int ctr = 0 ; ctr < numplayers ; ctr++ ) {
                    int payoff = 11 * players[ctr].getBet();
                    System.out.println(players[ctr].getName() + ", you win $" + payoff);
                    players[ctr].payoff(payoff);
                }
            } else {
                System.out.println("third card is different - no payoff");
                for ( int ctr = 0 ; ctr < numplayers ; ctr++ ) {
                    players[ctr].payoff(0); // reset bets
                }
            }

        } else if ( card1.getValue() - card2.getValue() == 1
                    || card2.getValue() - card1.getValue() == 1 ) { // adjacent
            // values
            // no one gains or loses money
            System.out.println("cards are adjacent - no payoff");
            for ( int ctr = 0 ; ctr < numplayers ; ctr++ ) {
                players[ctr].payoff(0); // reset bets
            }

        } else { // values differ by more than 1
            // allow the player to raise her bet
            raiseBets(players,numplayers,scanner);
            System.out.println();

            // deal a third card
            Card card3 = deck.dealCard(); // third card dealt
            System.out.println("dealer deals " + card3);

            // payoff if the third card's value is between the other two,
            // otherwise everyone loses their bets
            if ( isBetween(card1,card2,card3) ) {
                System.out.println("everyone wins!");
                for ( int ctr = 0 ; ctr < numplayers ; ctr++ ) {
                    int payoff =
                        determinePayoff(computeSpread(card1,card2),players[ctr].getBet());
                    System.out.println(players[ctr].getName() + ", you win $" + payoff);
                    players[ctr].payoff(payoff);
                }
            } else {
                System.out.println("everyone loses");
                for ( int ctr = 0 ; ctr < numplayers ; ctr++ ) {
                    players[ctr].loseBet();
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
        int numplayers=initplayers;

        // create the initial set of players
        RedDogPlayer[] players = new RedDogPlayer[initplayers];
        for ( int ctr = 0 ; ctr < initplayers ; ctr++ ) {
            String name; // current player's name
            System.out.print("enter player " + (ctr + 1) + "'s name: ");
            name = scanner.nextLine();
            players[ctr] = new RedDogPlayer(name,100);
        }

        // play the game
        for ( ; true ; ) {
            play(players,numplayers,scanner);
            // removes bankrupt players.
            numplayers=numplayers-removeBankruptPlayers(players,numplayers);
            // removing players as user enters their names
            while(true) {
                System.out.println("Enter the name of the player you want to remove. To stop enter '-'");
                String removename= scanner.nextLine();
                if(removename.equals("-")) {
                    break;
                }
                numplayers=numplayers-removePlayerByName (players,numplayers,removename);
            }
            //adding player
            while(true) {
                System.out.println("Enter the name of the player you want to add. To stop enter '-'");
                String addname= scanner.nextLine();
                if(addname.equals("-")) {
                    break;
                }
                if(numplayers==players.length) {
                    grow(players);
                }
                addPlayer(players,numplayers,addname,100);
                numplayers++;
            }
            //checking if no players left
            if(numplayers==0) {
                System.out.println("No players left");
                break;
            }
        }
    }
}
