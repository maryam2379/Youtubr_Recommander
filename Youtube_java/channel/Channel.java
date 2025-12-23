package channel;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import video.Video;
import idGenerator.*;

// ========== Channel.java ==========
public class Channel {
    public String channelId;
    public String nameChannel;
    public String hostName;
    public int followers;
    public int videosNumber;
    public LocalDate dateCreation;
    public List<Video> nbreVideo;

    public Channel() {
        this.channelId = IdGenerator.generateChannelId();
        this.nbreVideo = new ArrayList<Video>();
        this.followers = 0;
        this.videosNumber = 0;
        this.dateCreation = LocalDate.now();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getNameChannel() {
        return nameChannel;
    }

    public void setNameChannel(String nameChannel) {
        this.nameChannel = nameChannel;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getVideosNumber() {
        return videosNumber;
    }

    public void setVideosNumber(int videosNumber) {
        this.videosNumber = videosNumber;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    // Créer une chaîne avec ID auto-généré
    public static Channel createChannel(String name, String host, LocalDate date) {
        Channel channel = new Channel();
        channel.nameChannel = name;
        channel.hostName = host;
        channel.dateCreation = date;
        return channel;
    }

    // Afficher la liste des vidéos de la chaîne
    public void displayListVideo() {
        if (nbreVideo.isEmpty()) {
            System.out.println("Aucune vidéo dans cette chaîne.");
            return;
        }
        
        for (Video v : this.nbreVideo) {
            System.out.println("\n----------------------------------------");
            System.out.println("ID Vidéo: " + v.getVideoId());
            System.out.println("Titre de la Video: " + v.getTitreVideo());
            System.out.println("Author: " + v.getAuthor());
            System.out.println("Description: " + v.getDecription());
            System.out.println("Resolution: " + v.getResolution() + "K");
            System.out.println("Date: " + v.getDatePublication());
            System.out.println("Nbre comment: " + v.getNbreComment());
            System.out.println("Nbre vue: " + v.getNbreView());
            System.out.println("Likes: " + v.getNbreLikes());
            System.out.println("----------------------------------------");
        }
    }
}
