imnport java.util.ArrayList;

public class Hand {
    private int sum;
    private int aceCount;
    private ArrayList<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
        this.sum = 0;
        this.aceCount = 0;
    }

    public void addCard(Card card) {
        this.card.add(card);
        this.sum += card.getValue();

        if(card.isAce) {
            this.aceCount++;
        }

        ForAces();
    }

    private void ForAces() {
        while(this.sum > 21 && this.aceCount > 0) {
            this.sum -= 10;
            this.aceCount--;
        }
    }

    public boolean Bust() {
        return this.sum > 21;
    }

    public ArrayList<card> getCards() {
        return new ArrayList<>(this.card);
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }
}