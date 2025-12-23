package user;
import java.io.*;
import java.util.*;
import video.Video;
import idGenerator.*;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String userId;
    public String nameUser;
    public String gender;
    public int numTel;
    public String password;
    public String email;
    
    // Données utilisateur stockées
    public List<Video> userVideos;
    public List<String> likedVideos;
    public List<Comments> userComments;

    public User() {
        this.userId = IdGenerator.generateUserId();
        this.userVideos = new ArrayList<>();
        this.likedVideos = new ArrayList<>();
        this.userComments = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Fonction qui permet de créer un nouvel utilisateur
    public static User createUser(String nameUser, String gender, int numTel, String email, String password) {
        User user = new User();
        user.nameUser = nameUser;
        user.gender = gender;
        user.numTel = numTel;
        user.email = email;
        user.password = password;
        return user;
    }
    
    // Ajouter une vidéo créée par l'utilisateur
    public void addVideo(Video video) {
        this.userVideos.add(video);
        saveToFile();
    }
    
    // Ajouter un like
    public void addLikedVideo(String videoId) {
        if (!this.likedVideos.contains(videoId)) {
            this.likedVideos.add(videoId);
            saveToFile();
        }
    }
    
    // Ajouter un commentaire
    public void addComment(Comments comment) {
        this.userComments.add(comment);
        saveToFile();
    }
    
    // Sauvegarder les données de l'utilisateur dans un fichier
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(userId + ".txt"))) {
            // Informations de base
            writer.println("USERINFO:" + userId + "|" + nameUser + "|" + gender + "|" + 
                          numTel + "|" + email + "|" + password);
            
            // Vidéos créées
            writer.println("VIDEOS:" + userVideos.size());
            for (Video video : userVideos) {
                writer.println(video.serialize());
            }
            
            // Vidéos likées
            writer.println("LIKES:" + likedVideos.size());
            for (String videoId : likedVideos) {
                writer.println(videoId);
            }
            
            // Commentaires
            writer.println("COMMENTS:" + userComments.size());
            for (Comments comment : userComments) {
                writer.println(comment.serialize());
            }
            
            System.out.println("Données utilisateur sauvegardées dans " + userId + ".txt");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du fichier utilisateur: " + e.getMessage());
        }
    }
    
    // Charger les données de l'utilisateur depuis un fichier
    public static User loadFromFile(String userId) {
       File file = new File(userId + ".txt");
    if (!file.exists()) return null;

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line = reader.readLine();
        if (line == null || !line.startsWith("USERINFO:")) return null;

        String[] parts = line.substring(9).split("\\|");
        // Vérifier qu'on a bien tous les champs (6 champs attendus)
        if (parts.length < 6) return null;

        User user = new User();
        user.userId = parts[0].trim();
        user.nameUser = parts[1].trim();
        user.gender = parts[2].trim();
        user.numTel = Integer.parseInt(parts[3].trim());
        user.email = parts[4].trim();
        user.password = parts[5].trim();
            
            // Lire les vidéos
            if ((line = reader.readLine()) != null && line.startsWith("VIDEOS:")) {
                int count = Integer.parseInt(line.substring(7));
                for (int i = 0; i < count; i++) {
                    line = reader.readLine();
                    if (line != null) {
                        user.userVideos.add(Video.deserialize(line));
                    }
                }
            }
            
            // Lire les likes
            if ((line = reader.readLine()) != null && line.startsWith("LIKES:")) {
                int count = Integer.parseInt(line.substring(6));
                for (int i = 0; i < count; i++) {
                    line = reader.readLine();
                    if (line != null) {
                        user.likedVideos.add(line);
                    }
                }
            }
            
            // Lire les commentaires
            if ((line = reader.readLine()) != null && line.startsWith("COMMENTS:")) {
                int count = Integer.parseInt(line.substring(9));
                for (int i = 0; i < count; i++) {
                    line = reader.readLine();
                    if (line != null) {
                        user.userComments.add(Comments.deserialize(line));
                    }
                }
            }
            
            return user;
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier utilisateur: " + e.getMessage());
            return null;
        }
    }
    
    // Vérifier si un fichier utilisateur existe
    public static boolean userFileExists(String userId) {
        return new File(userId + ".txt").exists();
    }
}
