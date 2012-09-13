package gameCode;

import java.util.*;

public class Deck {
	
	public static final int MAX_SIZE = 52;
	
	private ArrayList <Card> cards;
	
	public Deck (){
		reset();
	}
	
	public void reset (){
		//Create a new list and add 13 cards from each suit
		cards = new ArrayList<Card>();
		addSuit(Suit.spade);
		addSuit(Suit.heart);
		addSuit(Suit.diamond);
		addSuit(Suit.club);
	}
	
	private void addSuit (Suit suit){
		for(int i = 1; i <= 13; i++)
			cards.add(new Card(suit, i));
	}
	
	public boolean isEmpty(){
		return cards.isEmpty();
	}
	
	public int size(){
		return cards.size();
	}
	
	public Card deal(){
		if(isEmpty())
			return null;
		else
			return cards.remove(cards.size() - 1);
	}
	
	public Card[] deal (int number){
		if(number > cards.size())
			return null;
		else{
			Card[] hand = new Card[number];
			for (int i = 0; i < hand.length; i++)
				hand[i] = deal();
			return hand;
		}
	}
	
	public void shuffle (){
		if (cards.size() < MAX_SIZE)
			return;
		Random gen  = new Random();
		//Removes cards from the list and place at random positions throughout the list
		Card[] array = new Card[MAX_SIZE];
		while(cards.size() > 0){
			Card card = cards.remove(cards.size()-1);
			int i = gen.nextInt(MAX_SIZE);
			while(array[i] != null)
				i = gen.nextInt(MAX_SIZE);
			array[i] = card;
		}
		//Transfer the shuffled cards back into the list
		for (Card card : array)
			cards.add(card);
	}
	
	public String toString(){
		String result = "";
		for (Card card : cards)
			result += card + "\n";
		return result;
	}
}