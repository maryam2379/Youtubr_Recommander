package video;
import java.util.*;

public class VideoBd extends Video {
    private static final long serialVersionUID = 1L;
    
    public List<Video> videoList;

    public VideoBd() {
        this.videoList = new ArrayList<Video>();
    }

    // Fonction pour ajouter une vidéo à la base de données
    public void addVideo(Video v) {
        this.videoList.add(v);
    }

    // Rechercher une vidéo et afficher la vidéo
    public void researchVideo(String nom) {
        for (Video v : this.videoList) {
            if (v.titreVideo.equals(nom)) {
                System.out.println("\t \tID Vidéo : " + v.getVideoId());
                System.out.println("\t \tTitre : " + v.getTitreVideo());
                System.out.println("\t \tAutheur :" + v.getAuthor());
                System.out.println("\t \tDescription : " + v.getDecription());
                System.out.println("\t \tResolution : " + v.getResolution() + "K");
                System.out.println("\t \tDate de Publication : " + v.getDatePublication());
                System.out.println("\t \t----------------------------------------");
                System.out.println("\t \tSTATISTIQUES & PERTINENCE\n");
                System.out.println("\t \tNombre de Vues : " + v.getNbreView());
                System.out.println("\t \tNombre de Commentaires : " + v.getNbreComment());
                System.out.println("\t \tNombre de Likes : " + v.getNbreLikes());
                // Intégration de l'analyse de pertinence
                System.out.println("\t \tScore de Pertinence : " + v.getRelevanceScore());
                System.out.println("\t \tStatut : " + v.getPertinenceStatus());
                System.out.println("\t \t----------------------------------------\n\n");
                displayListComments();
                
                return;
            }
        }
        System.out.println("Aucune video disponible pour ce theme..... ");
    }

    // Liker une vidéo
    public void likedVideo(String titre) {
        for (Video v : this.videoList) {
            if (v.getTitreVideo().equals(titre)) {
                v.setNbreLikes(v.getNbreLikes() + 1);
                System.out.println("La vidéo '" + titre + "' a été likée ! Nouveau nombre de likes : " + v.getNbreLikes());
                return;
            }
        }
        System.out.println("Vidéo introuvable : " + titre);
    }
    
    // Obtenir une vidéo par titre
    public Video getVideoByTitle(String titre) {
        for (Video v : this.videoList) {
            if (v.getTitreVideo().equals(titre)) {
                return v;
            }
        }
        return null;
    }
    
    // Obtenir une vidéo par ID
    public Video getVideoById(String videoId) {
        for (Video v : this.videoList) {
            if (v.getVideoId().equals(videoId)) {
                return v;
            }
        }
        return null;
    }

    // Fonction pour afficher une vidéo
    public void displayVideo() {
        if (videoList.isEmpty()) {
            System.out.println("Aucune vidéo disponible.");
            return;
        }
        
        for (Video v : this.videoList) {
            System.out.println("\n========================================");
            System.out.println("ID : " + v.getVideoId());
            System.out.println("Titre de la Video : " + v.getTitreVideo());
            System.out.println("Author: " + v.getAuthor());
            System.out.println("Description: " + v.getDecription());
            System.out.println("Resolution: " + v.getResolution() + "K");
            System.out.println("Date: " + v.getDatePublication());
            System.out.println("Nbre comment: " + v.getNbreComment());
            System.out.println("Nbre vue: " + v.getNbreView());
            System.out.println("Likes: " + v.getNbreLikes());
            System.out.println("========================================\n");
        }
    }

    // Supprimer une vidéo de la base de données
    public void delateVideo(String nom) {
        for (Video v : this.videoList) {
            if (v.titreVideo.equals(nom)) {
                videoList.remove(v);
                System.out.println("Vidéo '" + nom + "' supprimée avec succès.");
                return;
            }
        }
        System.out.println("Vidéo introuvable : " + nom);
    }
}
