package Tables;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AudioTable {

    private static HashMap<String, AudioInputStream> audioTable;
    private static ArrayList<Clip> playingContinuously;

    private static final String path = "resources/sounds/";

    public static void init(){
        audioTable = new HashMap<>();
        playingContinuously = new ArrayList<>();
        try{
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for(File file : listOfFiles){
                String name = file.getName();
                audioTable.put(name.substring(0,name.lastIndexOf('.')), AudioSystem.getAudioInputStream(new File(path + name)));
            }
        }catch(UnsupportedAudioFileException e){
            System.out.println("[AudioTable->init] Unsupported audio file type" + e.getMessage());
        }catch(IOException e){
            System.out.println("[AudioTable->init] Could not read Audio" + e.getMessage());
        }
    }
    public static void playContinuously(String key){
        if(audioTable.containsKey(key)){
            try{
                Clip clip = AudioSystem.getClip();
                clip.open(audioTable.get(key));
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                //Save reference
                playingContinuously.add(clip);
            }catch(LineUnavailableException e){
                System.out.println("[AudioTable->playContinuously] Error gettingClip");
            }catch(IOException e){
                System.out.println("[AudioTable->playContinuously] Fatal Error Playing Audio");
            }
        }else{
            System.out.println("Invalid Audio Request");
        }
    }
    public static void stopALLContinuous(){
        for(Clip clip : playingContinuously) {
            clip.stop();
        }
        playingContinuously.clear();
    }
    public static void playOnce(String key){
        if(audioTable.containsKey(key)){
            (new Thread(new Runnable(){
                @Override
                public void run() {
                    long audioLength;
                    try {
                        if (!Thread.currentThread().isInterrupted()) {
                            //Load Audio
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioTable.get(key));
                            //Length of Audio
                            audioLength = clip.getMicrosecondLength()/1000;
                            //Play
                            clip.start();

                            //Terminate thread after audioLength milliseconds
                            (new Timer()).schedule(new TimerTask() {
                                public void run(){
                                    Thread.currentThread().interrupt();
                                }
                            }, audioLength);
                        }
                    }catch(LineUnavailableException e){
                        System.out.println("[Anonymous Thread] Clip could not be opened");
                        Thread.currentThread().interrupt();
                    }catch(IOException e){
                        System.out.println("[Anonymous Thread] Fatal Error Occurred" + e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }
            })).start();
        }else{
            System.out.println("Invalid Audio Request");
        }
    }
}
