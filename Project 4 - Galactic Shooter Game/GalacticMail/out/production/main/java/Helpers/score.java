package Helpers;

public class score {

    private String name;
    private int score;

    public score(String id, int score){
        this.name = id;
        this.score = score;
    }
    public String getName(){
        return this.name;
    }
    public int getScore(){
        return this.score;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setScore(int score){
        this.score = score;
    }
}
