import java.util.*;

public class AgentScorer {

    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    private Map<String, Agent> agents = new HashMap<>();

    public void giveRating(String id, int rating) {

        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Agent agent = agents.computeIfAbsent(id, Agent::new);
        agent.addRating(rating);
    }

    public List<Agent> getAverageRatings() {
        return agents.values().stream()
                .sorted(Comparator
                        .comparing(Agent::getAverage)
                        .thenComparing(Agent::getCount )
                        .thenComparing(Agent::getId))
                .toList();
    }

    class Agent {

        private String id;
        private int total;
        private int count;

        Agent(String id) {
            this.id = id;
        }

        void addRating(int rating) {
            total += rating;
            count++;
        }

        double getAverage() {
            return (double) total / count;
        }

        int getCount() {
            return count;
        }

        String getId() {
            return id;
        }

        public String toString() {
            return id + " : " + getAverage();
        }
    }
    public static void main(String[] args) {

        AgentScorer scorer = new AgentScorer();

        scorer.giveRating("Kohli", 4);
        scorer.giveRating("Kohli", 5);

        scorer.giveRating("Sachin", 5);
        scorer.giveRating("Sachin", 5);

        System.out.println(scorer.getAverageRatings());
    }
}
