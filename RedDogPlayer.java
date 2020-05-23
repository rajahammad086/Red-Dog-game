/**
 * A player in the game of Red Dog.
 * 
 * @author Stina Bridgeman
 */

public class RedDogPlayer {

  private String name_; // player's name

  private int money_; // player's money on hand

  private int bet_; // player's current bet (0 if none)

  /**
   * Create a player with the specified name and initial amount of money.
   * 
   * @param name
   *          player's name
   * @param money
   *          initial money on hand (money >= 0)
   */
  public RedDogPlayer ( String name, int money ) {
    name_ = name;
    money_ = money;
  }

  /**
   * Get the player's current bet.
   * 
   * @return the player's bet
   */
  public int getBet () {
    return bet_;
  }

  /**
   * Raise the player's current bet.
   * 
   * @param raise
   *          the amount to raise the bet (0 <= raise <= player's money on 
   *          hand - current bet)
   */
  public void raiseBet ( int raise ) {
    bet_ += raise;
  }

  /**
   * Get the player's money on hand.
   * 
   * @return the money on hand
   */
  public int getMoney () {
    return money_;
  }

  /**
   * Get the player's name
   * 
   * @return the player's name
   */
  public String getName () {
    return name_;
  }

  /**
   * Payoff - add the specified amount to the player's money and set the current
   * bet to 0.
   * 
   * @param amount
   *          payoff amount (amount >= 0)
   */
  public void payoff ( int amount ) {
    money_ += amount;
    bet_ = 0;
  }

  /**
   * Loss - subtract the current bet from the player's money and set the current
   * bet to 0.
   */
  public void loseBet () {
    money_ -= bet_;
    bet_ = 0;
  }
}
