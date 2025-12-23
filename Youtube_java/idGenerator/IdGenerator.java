package idGenerator;
import java.io.*;

public class IdGenerator {
    private static int userCounter = 1;
    private static int videoCounter = 1;
    private static int commentCounter = 1;
    private static int channelCounter = 1;
    
    private static final String COUNTER_FILE = "counters.dat";
    
    static {
        loadCounters();
    }
    
    public static String generateUserId() {
        String id = String.format("us%03d", userCounter++);
        saveCounters();
        return id;
    }
    
    public static String generateVideoId() {
        String id = String.format("VID%03d", videoCounter++);
        saveCounters();
        return id;
    }
    
    public static String generateCommentId() {
        String id = String.format("COM%03d", commentCounter++);
        saveCounters();
        return id;
    }
    
    public static String generateChannelId() {
        String id = String.format("CH%03d", channelCounter++);
        saveCounters();
        return id;
    }
    
    private static void saveCounters() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COUNTER_FILE))) {
            oos.writeInt(userCounter);
            oos.writeInt(videoCounter);
            oos.writeInt(commentCounter);
            oos.writeInt(channelCounter);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des compteurs: " + e.getMessage());
        }
    }
    
    private static void loadCounters() {
        File file = new File(COUNTER_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COUNTER_FILE))) {
                userCounter = ois.readInt();
                videoCounter = ois.readInt();
                commentCounter = ois.readInt();
                channelCounter = ois.readInt();
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des compteurs: " + e.getMessage());
            }
        }
    }
}
