package video;
import java.time.LocalDate;
import java.util.*;
import java.io.Serializable;
import user.Comments;
import idGenerator.*;

public class Video extends Comments implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String titreVideo;
    public String videoId;
    public String author;
    public String decription;
    public int resolution;
    public LocalDate datePublication;
    public int nbreComment;
    public int nbreView;
    public int nbreLikes;
    public List<Comments> commentList;

    public Video() {
        super();
        this.videoId = IdGenerator.generateVideoId();
        this.datePublication = LocalDate.now();
        this.commentList = new ArrayList<>();
        this.nbreComment = 0;
        this.nbreView = 0;
        this.nbreLikes = 0;
    }

    public String getTitreVideo() {
        return titreVideo;
    }

    public void setTitreVideo(String titreVideo) {
        this.titreVideo = titreVideo;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public int getNbreComment() {
        return nbreComment;
    }

    public void setNbreComment(int nbreComment) {
        this.nbreComment = nbreComment;
    }

    public int getNbreView() {
        return nbreView;
    }

    public void setNbreView(int nbreView) {
        this.nbreView = nbreView;
    }

    public int getNbreLikes() {
        return nbreLikes;
    }

    public void setNbreLikes(int nbreLikes) {
        this.nbreLikes = nbreLikes;
    }

    // Création d'une nouvelle vidéo avec ID auto-généré
    public static Video createVideo(String titre, String author, String description, int resolution, LocalDate date) {
        Video video = new Video();
        video.titreVideo = titre;
        video.author = author;
        video.decription = description;
        video.resolution = resolution;
        video.datePublication = date;
        return video;
    }

    // Ajouter un commentaire à la liste des commentaires
    public void addComment(Comments c) {
        this.commentList.add(c);
        this.nbreComment++;
    }

    // Afficher la liste des commentaires d'une vidéo
    public void displayListComments() {
        System.out.println("\t \t \t***************** LISTE DES COMMENTAIRES DE LA VIDEO **********************************");
        for (Comments c : this.commentList) {
            System.out.println("\t \t.......COMMENTAIRE DE : " + c.getUserName() + ".......\n");
            System.out.println("\t ID :" + c.getCommentId());
            System.out.println("\t Date de Publication : " + c.getDate());
            System.out.println("\t Texte : " + c.getText());
            System.out.println("\t Likes : " + c.getLikes());
        }
    }

    // Liker un commentaire
    public void likedComment(String id) {
        for (Comments c : this.commentList) {
            if (c.getCommentId().equals(id)) {
                c.setLikes(c.getLikes() + 1);
                System.out.println("Commentaire liké avec succès!");
                return;
            }
        }
        System.out.println("Commentaire introuvable : " + id);
    }

    // Retirer un like sur un commentaire
    public void unlikedComment(String id) {
        for (Comments c : this.commentList) {
            if (c.getCommentId().equals(id)) {
                c.setLikes(c.getLikes() - 1);
                return;
            }
        }
        System.out.println("Commentaire introuvable : " + id);
    }
    
    // Convertir en chaîne pour sauvegarde
    public String serialize() {
        return videoId + "|" + titreVideo + "|" + author + "|" + decription + "|" + 
               resolution + "|" + nbreLikes + "|" + nbreView + "|" + nbreComment + "|" + datePublication.toString();
    }
    
    // Créer à partir d'une chaîne
    public static Video deserialize(String data) {
        String[] parts = data.split("\\|");
        Video video = new Video();
        video.videoId = parts[0];
        video.titreVideo = parts[1];
        video.author = parts[2];
        video.decription = parts[3];
        video.resolution = Integer.parseInt(parts[4]);
        video.nbreLikes = Integer.parseInt(parts[5]);
        video.nbreView = Integer.parseInt(parts[6]);
        video.nbreComment = Integer.parseInt(parts[7]);
        video.datePublication = LocalDate.parse(parts[8]);
        return video;
    }
    // À ajouter dans Video.java

/**
 * Analyse la pertinence de la vidéo.
 * Score = (Likes Vidéo * 2) + (Nombre de Commentaires * 5) + (Somme des likes des commentaires)
 */
public double getRelevanceScore() {
    double score = 0;
    
    // Poids pour l'engagement direct
    score += (this.nbreLikes * 2);
    score += (this.nbreComment * 5);
    
    // Analyse de la pertinence via les likes des commentaires
    if (this.commentList != null) {
        for (Comments c : this.commentList) {
            score += c.getLikes();
        }
    }
    
    return score;
}

public String getPertinenceStatus() {
    double score = getRelevanceScore();
    if (score > 100) return "Très Pertinente (Incontournable)";
    if (score > 50) return "Pertinente (Populaire)";
    if (score > 10) return "Intéressante";
    return "Peu de retours (Nouvelle ou niche)";
}
}
