package Tables;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import Helpers.score;

public class HighScoresTable {

    private static final String path = "resources/";
    private static final String name = "highscores.txt";
    private static ArrayList<score> scores;
    private static int lowestScore;

    public static void init(){
        scores = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path+name));
            lowestScore = 0;
            String currentLine;

            while((currentLine = br.readLine()) != null){
                //Check formatting
                if(!currentLine.contains(":"))
                    throw new IOException();
                //Split
                String[] splitLine = currentLine.split(":");
                //Validate
                if(splitLine.length != 2)
                    throw new IOException();
                //Check for first score
                //Populate
                int cScore = Integer.parseInt(splitLine[1]);
                scores.add(new score(splitLine[0],cScore));
                if(cScore < lowestScore || scores.size() == 1)
                    lowestScore = cScore;
            }
        }catch(FileNotFoundException e){
            System.out.println("[HighScoresTable-init] File Not Found: (" + name + ")");
        }catch(IOException e){
            System.out.println("[HighScoresTable-init] Error Reading File: (" + name + ")");
        }
        if(scores.isEmpty()){
            scores.add(new score("replace",0));
        }
    }
    public static boolean isHighScore(int checkScore){
        return (checkScore > lowestScore);
    }
    public static void insertNewHighScore(String insertName, int insertScore){
        try{
            //Clear File
            FileChannel.open(Paths.get(path+name), StandardOpenOption.WRITE).truncate(0).close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(path+name));
            boolean written = false;
            for(score cScore : scores){
                if(!written && cScore.getScore() == lowestScore){
                    System.out.println("writen to file");
                    written = true;
                    bw.write(insertName + ":" + insertScore);
                    bw.newLine();
                }else{
                    bw.write(cScore.getName() + ":" + cScore.getScore());
                    bw.newLine();
                }
            }
            bw.close();
        }catch(IOException e){
            System.out.println("[HighScoresTable->writeNewScores] could not write scores to file");
        }
    }
    public static String getHighScores(){
        String highScores = "";
        for(score cScore : scores){
            highScores += cScore.getName() + " | " + cScore.getScore() + "\n";
        }
        return highScores;
    }
}
