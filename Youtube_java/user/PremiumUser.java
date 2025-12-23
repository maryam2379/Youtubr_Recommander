package user;
import java.util.*;
import java.io.*;
import video.Video;
import user.User;
import idGenerator.*;

public class PremiumUser extends User {
    private static final long serialVersionUID = 1L;
    
    public String paymentSubcript;

    public PremiumUser() {
        super();
    }

    public PremiumUser(String name, String gender, int numtel, String email, String password) {
        super();
        this.userId = IdGenerator.generateUserId();
        this.nameUser = name;
        this.gender = gender;
        this.numTel = numtel;
        this.email = email;
        this.password = password;
    }

    public String getPaymentSubcript() {
        return paymentSubcript;
    }

    public void setPaymentSubcript(String paymentSubcript) {
        this.paymentSubcript = paymentSubcript;
    }

    public static PremiumUser createPremiumUser(String name, String gender, int numtel, String email, String password, String option) {
        PremiumUser premiumUser = new PremiumUser(name, gender, numtel, email, password);
        premiumUser.paymentSubcript = option;
        return premiumUser;
    }

    // Souscrire à une option
    public PremiumUser subscriptionPayment(Scanner sc) {
        System.out.println("\n--- OPTIONS DE SOUSCRIPTION PREMIUM ---");
        System.out.println("1....................OPTION 1 : 2,05$/ mois .....................");
        System.out.println("2....................OPTION 2 : 5,57$/ mois .....................");
        System.out.println("3....................OPTION 3 : 12,89$/ mois ....................");
        System.out.print("Quelle option desirez vos souscrire ?..");

        if (!sc.hasNextInt()) {
            System.out.println("Choix invalide. Veuillez entrer un nombre.");
            sc.nextLine();
            return this;
        }

        int choix = sc.nextInt();
        sc.nextLine();

        String optionDescription = "";

        switch (choix) {
            case 1:
                optionDescription = "OPTION 1 : 2,05$/mois";
                System.out.println("VOUS SOUSCRIVREZ BIEN À L'" + optionDescription + "......... ");
                System.out.print("Inserez le numero de votre carte bancaire :");
                String cardNumber = sc.nextLine();
                this.setPaymentSubcript(optionDescription);
                System.out.println(" PAIEMENT ET OPERATION REUSSIS");
                break;
            case 2:
                optionDescription = "OPTION 2 : 5,57$/mois";
                System.out.println("VOUS SOUSCRIVREZ BIEN À L'" + optionDescription + "......... ");
                String cardNber = sc.nextLine();
                this.setPaymentSubcript(optionDescription);
                System.out.println(" PAIEMENT ET OPERATION REUSSIS");
                break;
            case 3:
                optionDescription = "OPTION 3 : 12,89$/mois";
                System.out.println("VOUS SOUSCRIVREZ BIEN À L'" + optionDescription + "......... ");
                String cardNum = sc.nextLine();
                this.setPaymentSubcript(optionDescription);
                System.out.println(" PAIEMENT ET OPERATION REUSSIS");
                break;
            default:
                System.out.println("Choix d'option invalide. Souscription non effectuée.");
                break;
        }
        
        saveToFile(); // Sauvegarder après souscription
        return this;
    }

    // Profil du premium user
    public void displayProfilePremiumUser(PremiumUser p) {
        System.out.println("\t \t ***************** INFORMATION DE L'UTILISATEUR **********************\n");
        System.out.println("ID Utilisateur :" + p.getUserId());
        System.out.println("Nom :" + p.getNameUser());
        System.out.println("Genre :" + p.getGender());
        System.out.println("Numero de Telephone :" + p.getNumTel());
        System.out.println("Email :" + p.getEmail());
        System.out.println("Mot de Passe :" + p.getPassword());
        System.out.println("Option de souscription:" + p.getPaymentSubcript());
        System.out.println("Nombre de vidéos créées: " + p.userVideos.size());
        System.out.println("Nombre de likes: " + p.likedVideos.size());
        System.out.println("Nombre de commentaires: " + p.userComments.size());
    }
    
    @Override
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(userId + ".txt"))) {
            // Informations de base avec type Premium
            writer.println("USERTYPE:PREMIUM");
            writer.println("USERINFO:" + userId + "|" + nameUser + "|" + gender + "|" + 
                          numTel + "|" + email + "|" + password + "|" + 
                          (paymentSubcript != null ? paymentSubcript : ""));
            
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
            
            System.out.println("Données utilisateur premium sauvegardées dans " + userId + ".txt");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du fichier utilisateur: " + e.getMessage());
        }
    }
    
    public static PremiumUser loadPremiumFromFile(String userId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(userId + ".txt"))) {
            PremiumUser user = new PremiumUser();
            String line;
            
            // Vérifier le type
            if ((line = reader.readLine()) != null && !line.equals("USERTYPE:PREMIUM")) {
                return null;
            }
            
            // Lire les informations de base
            if ((line = reader.readLine()) != null && line.startsWith("USERINFO:")) {
                String[] parts = line.substring(9).split("\\|");
                user.userId = parts[0];
                user.nameUser = parts[1];
                user.gender = parts[2];
                user.numTel = Integer.parseInt(parts[3]);
                user.email = parts[4];
                user.password = parts[5];
                if (parts.length > 6) {
                    user.paymentSubcript = parts[6];
                }
            }
            
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
            System.err.println("Erreur lors du chargement du fichier utilisateur premium: " + e.getMessage());
            return null;
        }
    }
}
