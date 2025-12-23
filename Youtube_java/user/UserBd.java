package user;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class UserBd extends User {
    private static final long serialVersionUID = 1L;
    
    public List<User> userList;
    private static final String USER_INDEX_FILE = "user_index.txt";

    public UserBd() {
        this.userList = new ArrayList<User>();
        loadUserIndex();
    }

    // Ajouter un utilisateur dans la base de données
    public void addUser(User u) {
        this.userList.add(u);
        u.saveToFile();
        saveUserIndex();
    }

    // Ajouter un utilisateur premium
    public void addPremiumUser(PremiumUser p) {
        this.userList.add(p);
        p.saveToFile();
        saveUserIndex();
    }

    // Retirer un utilisateur de la base de données
    public void logOut(String email) {
        for (User u : this.userList) {
            if (u.email.equals(email)) {
                userList.remove(u);
                return;
            }
        }
    }

    // Affichage du mot de passe
    public String getMaskedPassword() {
        return "*".repeat(getPassword().length());
    }

    // Afficher un utilisateur
    public void displayUser() {
        System.out.println("\t \t \t************************************************************************************");
        System.out.println("\t \t \t*                               LISTE DES UTILISATEURS                                    *");
        System.out.println("\t \t \t************************************************************************************");
        for (User u : this.userList) {
            System.out.println("\t \tID : " + u.getUserId());
            System.out.println("\t \tNom : " + u.getNameUser());
            System.out.println("\t \tgenre :" + u.getGender());
            System.out.println("\t \tNumero de Telephone : " + u.getNumTel());
            System.out.println("\t \tEmail : " + u.getEmail());
            System.out.println("\t \tMot de Passe : " + u.getPassword());
        }
    }

    // Connecter un utilisateur à son compte en cherchant dans les fichiers
    public User logIn(Scanner sc) {
        System.out.print("Veuillez entrer l'adresse mail du compte :");
        String mail = sc.nextLine().trim();
        System.out.print("veuillez entrer le mot de passe du compte :");
        String motDePasse = sc.nextLine().trim();

        // D'abord chercher dans la liste chargée en mémoire
        for (User u : this.userList) {
            if (u.email.equals(mail) && u.password.equals(motDePasse)) {
                System.out.println("\n\tCONNEXION REUSSIE. Bienvenue, " + u.getNameUser() + "!");
                handlePremiumLogin(u, sc);
                return u;
            }
        }

        // Si pas trouvé en mémoire, chercher dans tous les fichiers utilisateurs
        User foundUser = searchUserInFiles(mail, motDePasse);
        if (foundUser != null) {
            System.out.println("\n\tCONNEXION REUSSIE. Bienvenue, " + foundUser.getNameUser() + "!");
            userList.add(foundUser); // Ajouter à la liste en mémoire
            handlePremiumLogin(foundUser, sc);
            return foundUser;
        }

        System.out.println("Aucun compte ne correspond à cet email ou mot de passe. Veuillez vous inscrire !");
        return null;
    }

    // Chercher un utilisateur dans tous les fichiers
    private User searchUserInFiles(String email, String password) {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.matches("us\\d{3}\\.txt"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                String userId = file.getName().replace(".txt", "");
                
                // Essayer de charger comme utilisateur premium d'abord
                PremiumUser premiumUser = PremiumUser.loadPremiumFromFile(userId);
                if (premiumUser != null && premiumUser.email.equals(email) && premiumUser.password.equals(password)) {
                    return premiumUser;
                }
                
                // Sinon charger comme utilisateur normal
                User user = User.loadFromFile(userId);
                if (user != null && user.email.equals(email) && user.password.equals(password)) {
                    return user;
                }
                System.out.println("Fichier testé : " + file.getName() + " | Email chargé : " + (user != null ? user.getEmail() : "null"));
            }
        }
        return null;
    }

    // Gérer la connexion premium
    private void handlePremiumLogin(User u, Scanner sc) {
        if (u instanceof PremiumUser) {
            PremiumUser premiumUser = (PremiumUser) u;
            if (premiumUser.getPaymentSubcript() == null || premiumUser.getPaymentSubcript().isEmpty()) {
                System.out.println("\n*** COMPTE PREMIUM : Votre souscription doit être activée. ***");
                premiumUser.subscriptionPayment(sc);
            } else {
                System.out.println("Statut Premium actuel : " + premiumUser.getPaymentSubcript());
            }
        }
    }

    // Afficher le profil User
    public void displayProfileUser(String email) {
        User user = null;

        // Chercher dans la liste en mémoire
        for (User u : this.userList) {
            if (u.email.equals(email)) {
                user = u;
                break;
            }
        }

        // Si pas trouvé, chercher dans les fichiers
        if (user == null) {
            user = findUserByEmail(email);
        }

        if (user == null) {
            System.out.println("\n  Aucun utilisateur trouvé avec cet email.");
            return;
        }

        // Affichage du profil
        System.out.println("\n");
        System.out.println("\t \t╔═════════════════════════════════════════════════════════════╗");
        System.out.println("\t \t║                      PROFIL DE L'UTILISATEUR                ║");
        System.out.println("\t \t╚═════════════════════════════════════════════════════════════╝");
        System.out.println();

        // Vérifier si c'est un utilisateur Premium
        if (user instanceof PremiumUser) {
            PremiumUser premiumUser = (PremiumUser) user;
            System.out.println("\t \t TYPE DE COMPTE      : PREMIUM");
            System.out.println("\t \t─────────────────────────────────────────────────────────────────");
            System.out.println("\t \t ID Utilisateur      : " + premiumUser.getUserId());
            System.out.println("\t \t Nom                 : " + premiumUser.getNameUser());
            System.out.println("\t \t  Genre               : " + premiumUser.getGender());
            System.out.println("\t \t Téléphone           : " + premiumUser.getNumTel());
            System.out.println("\t \t  Email               : " + premiumUser.getEmail());
            System.out.println("\t \t Mot de Passe        : " + "*".repeat(premiumUser.getPassword().length()));
            System.out.println("\t \t Souscription        : " + (premiumUser.getPaymentSubcript() != null ? premiumUser.getPaymentSubcript() : "Non activée"));
            System.out.println("\t \t Vidéos créées       : " + premiumUser.userVideos.size());
            System.out.println("\t \t Vidéos likées       : " + premiumUser.likedVideos.size());
            System.out.println("\t \t Commentaires        : " + premiumUser.userComments.size());
            System.out.println("\t \t─────────────────────────────────────────────────────────────────");
        } else {
            System.out.println("\t \t TYPE DE COMPTE      : STANDARD");
            System.out.println("\t \t─────────────────────────────────────────────────────────────────");
            System.out.println("\t \t ID Utilisateur      : " + user.getUserId());
            System.out.println("\t \t Nom                 : " + user.getNameUser());
            System.out.println("\t \t  Genre               : " + user.getGender());
            System.out.println("\t \t Téléphone           : " + user.getNumTel());
            System.out.println("\t \t  Email               : " + user.getEmail());
            System.out.println("\t \t Mot de Passe        : " + "*".repeat(user.getPassword().length()));
            System.out.println("\t \t Vidéos créées       : " + user.userVideos.size());
            System.out.println("\t \t Vidéos likées       : " + user.likedVideos.size());
            System.out.println("\t \t Commentaires        : " + user.userComments.size());
            System.out.println("\t \t─────────────────────────────────────────────────────────────────");
        }
        System.out.println();
    }

    // Trouver un utilisateur par email dans les fichiers
    private User findUserByEmail(String email) {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.matches("us\\d{3}\\.txt"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                String userId = file.getName().replace(".txt", "");
                
                // Essayer premium d'abord
                PremiumUser premiumUser = PremiumUser.loadPremiumFromFile(userId);
                if (premiumUser != null && premiumUser.email.equals(email)) {
                    return premiumUser;
                }
                
                // Sinon utilisateur normal
                User user = User.loadFromFile(userId);
                if (user != null && user.email.equals(email)) {
                    return user;
                }
            }
        }
        return null;
    }

    // Sauvegarder l'index des utilisateurs
    private void saveUserIndex() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_INDEX_FILE))) {
            for (User u : userList) {
                writer.println(u.getUserId() + "|" + u.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de l'index: " + e.getMessage());
        }
    }

    // Charger l'index des utilisateurs
    private void loadUserIndex() {
        File indexFile = new File(USER_INDEX_FILE);
        if (!indexFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_INDEX_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String userId = parts[0];
                
                // Charger l'utilisateur depuis son fichier
                PremiumUser premiumUser = PremiumUser.loadPremiumFromFile(userId);
                if (premiumUser != null) {
                    userList.add(premiumUser);
                } else {
                    User user = User.loadFromFile(userId);
                    if (user != null) {
                        userList.add(user);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'index: " + e.getMessage());
        }
    }
}
