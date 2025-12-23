package channel;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import video.Video;
import idGenerator.*;

public class ChannelBd extends Channel {
    public List<Channel> channelList;

    public ChannelBd() {
        this.channelList = new ArrayList<Channel>();
    }

    public void addChannel(Channel c) {
        this.channelList.add(c);
        System.out.println("Chaîne créée avec l'ID: " + c.getChannelId());
    }

    // Se connecter à sa chaîne
    public void connectionChannel(java.util.Scanner sc) {
        System.out.println("Entrez le nom de votre chaine :");
        String name = sc.nextLine();
        for (Channel c : this.channelList) {
            if (c.hostName.equals(name)) {
                System.out.println("CONNEXION REUSSIE. Bienvenu sur " + c.getNameChannel());
                return;
            }
        }
        System.out.println("Impossible de se connecter a la chaine . veuillez entrer le bon nom ou creer une nouvelle chaine ");
    }

    // Chercher une chaîne
    public void searchChannel(String nom) {
        for (Channel c : this.channelList) {
            if (c.nameChannel.equals(nom)) {
                System.out.println("\n========================================");
                System.out.println("\t \tID Chaîne: " + c.getChannelId());
                System.out.println("\t \tNom de la chaine: " + c.getNameChannel());
                System.out.println("\t \tPropietaire de la chaine: " + c.getHostName());
                System.out.println("\t \tFollowers: " + c.getFollowers());
                System.out.println("\t \tNombre de Video: " + c.getVideosNumber());
                System.out.println("\t \tDate de creation: " + c.getDateCreation());
                System.out.println("========================================\n");
                displayListVideo();
                return;
            }
        }
        System.out.println("Chaîne introuvable: " + nom);
    }

    // Suivre une chaîne
    public void followChannel(String titre) {
        for (Channel c : this.channelList) {
            if (c.getNameChannel().equals(titre)) {
                c.setFollowers(c.getFollowers() + 1);
                System.out.println("La chaine '" + titre + "' a été suivi ! Nouveau nombre de followers : " + c.getFollowers());
                return;
            }
        }
        System.out.println("Chaine introuvable : " + titre);
    }

    // Arrêter de suivre une chaîne
    public void unfollowChannel(String titre) {
        for (Channel c : this.channelList) {
            if (c.getNameChannel().equals(titre)) {
                c.setFollowers(c.getFollowers() - 1);
                System.out.println("La chaine '" + titre + "' a arrete d'etre suivi par un follower ! Nouveau nombre de followers : " + c.getFollowers());
                return;
            }
        }
        System.out.println("Chaine introuvable : " + titre);
    }

    // Poster une nouvelle vidéo dans la chaîne
    public void postedVideo(Video v) {
        this.nbreVideo.add(v);
        this.videosNumber++;
        System.out.println("Vidéo postée avec succès! ID: " + v.getVideoId());
    }

    // Supprimer une vidéo de la chaîne par ID
    public void delateVideo(String id) {
        for (Video v : this.nbreVideo) {
            if (v.videoId.equals(id)) {
                nbreVideo.remove(v);
                this.videosNumber--;
                System.out.println("Vidéo " + id + " supprimée avec succès.");
                return;
            }
        }
        System.out.println("Vidéo introuvable: " + id);
    }

    // Afficher une chaîne
    public void displayChannel() {
        if (channelList.isEmpty()) {
            System.out.println("Aucune chaîne disponible.");
            return;
        }
        
        for (Channel c : this.channelList) {
            System.out.println("\n========================================");
            System.out.println("\t \tID Chaîne: " + c.getChannelId());
            System.out.println("\t \tNom de la chaine: " + c.getNameChannel());
            System.out.println("\t \tPropietaire de la chaine: " + c.getHostName());
            System.out.println("\t \tFollowers: " + c.getFollowers());
            System.out.println("\t \tNombre de Video: " + c.getVideosNumber());
            System.out.println("\t \tDate de creation: " + c.getDateCreation());
            System.out.println("========================================\n");
        }
    }
}
