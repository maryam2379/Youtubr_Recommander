package user;
import java.time.LocalDate;
import java.io.Serializable;
import idGenerator.*;

public class Comments implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String commentId;
    public LocalDate date;
    public String userName;
    public String text;
    public int likes;

    public Comments() {
        this.commentId = IdGenerator.generateCommentId();
        this.date = LocalDate.now();
        this.likes = 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Créer un commentaire avec ID auto-généré
    public static Comments createComment(String name, LocalDate date, String text) {
        Comments comments = new Comments();
        comments.date = date;
        comments.userName = name;
        comments.text = text;
        return comments;
    }

    // Afficher un commentaire
    public void displayComment() {
        System.out.println("\t \t Commentaire :" + getUserName() + "\n");
        System.out.println("Id du commentaire:" + getCommentId() + "\nDate de publication :" + getDate() + "\nTexte: " + getText() + "\nLikes :" + getLikes());
    }
    
    // Convertir en chaîne pour sauvegarde
    public String serialize() {
        return commentId + "|" + userName + "|" + text + "|" + likes + "|" + date.toString();
    }
    
    // Créer à partir d'une chaîne
    public static Comments deserialize(String data) {
        String[] parts = data.split("\\|");
        Comments comment = new Comments();
        comment.commentId = parts[0];
        comment.userName = parts[1];
        comment.text = parts[2];
        comment.likes = Integer.parseInt(parts[3]);
        comment.date = LocalDate.parse(parts[4]);
        return comment;
    }
}
