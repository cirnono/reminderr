public class Task {
    private String name;
    private int score;
    private boolean done;

    Task(String name, int score){
        this.name = name;
        this.score = score;
        this.done = false;
    }

    public String getName(){
        return this.name;
    }
    public int getScore(){
        return this.score;
    }
}
