package PGR112exam2022;


public class UserScore extends User {

    private Integer score = 0;

    private Topic topic;

    public UserScore () {
        super();

    }

    public UserScore(String name) {
        super(name);
    }

    public Integer incrementScore () {
        return this.score += 1;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}

