import java.time.LocalDate;
import java.util.Scanner;
import user.*;
import video.*;
import idGenerator.*;
import channel.*;

public class youtube {
    public static void menu() {
        System.out.println("\t \t \t @#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$");
        System.out.println("\t \t \t @#$                                                                                 @#$");
        System.out.println("\t \t \t @#$                         ~~~ YOUTUBE RECOMMANDER ~~~                             @#$");
        System.out.println("\t \t \t @#$                                                                                 @#$");
        System.out.println("\t \t \t @#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$");
        System.out.println("\n");
        System.out.println("\t......................... BIENVENUE SUR LE YOUTUBE RECOMMANDER.!!!!!!!!! .....................");
        System.out.println("\n");
        System.out.println("\t \t \t************************************************************************************");
        System.out.println("\t \t \t*                      MENU DU YOUTUBE RECOMMANDER                                 *");
        System.out.println("\t \t \t************************************************************************************");
        System.out.println("\n");
        System.out.println("\t1............................................  S'inscrire .....................................");
        System.out.println("\t2..........................................  Se Connecter .....................................");
        System.out.println("\t3........................................  Se deconnecter .....................................");
        System.out.println("\t0.............................................. QUITTER  ......................................");
    }

    // Menu après l'inscription et la connexion
    public static void menuInscription() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n\n");
        System.out.println("1................................ Poster une video...................................");
        System.out.println("2.............................   Rechercher une video ...............................");
        System.out.println("3................................. Supprimer une video ..............................");
        System.out.println("4................................ Creer une chaine ..................................");
        System.out.println("5............................. Rechercher une chaine.................................");
        System.out.println("6............................... suivre une chaine ..................................");
        System.out.println("7............................. Arreter de suivre une chaine .........................");
        System.out.println("8.......................... Poster une video dans la chaine .........................");
        System.out.println("9.......................... Supprimer une video de la chaine ........................");
        System.out.println("10.................................. Retour au menu principal .......................");
        System.out.println("11.................................. Afficher mon profil ............................");
        System.out.println("0............................. Fermer le Youtubre Recommander .......................");
    }

    public static void menuPayment() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n\n");
        System.out.println("1........................ S'incrire comme un utilisateur............................");
        System.out.println("2................... S'incrire comme un utilisateur premium ........................");
        System.out.println("0................................. Retour  .........................................");
    }

    public static void main(String[] args) {
        Video video = new Video();
        UserBd userBd = new UserBd();
        VideoBd videoBd = new VideoBd();
        ChannelBd channelBd = new ChannelBd();
        PremiumUser premiumUser = new PremiumUser();
        User currentUser = null;

        do {
            menu();
            Scanner sc = new Scanner(System.in);
            System.out.print("veuiller entrer une reponse : ");
            int choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1: // S'inscrire
                    menuPayment();
                    System.out.print("veuiller entrer une reponse : ");
                    int inscripChoix = sc.nextInt();
                    sc.nextLine();
                    
                    switch (inscripChoix) {
                        case 1:
                            System.out.print("Entrer votre Nom complet: ");
                            String name = sc.nextLine().trim();
                            System.out.print("Entrer votre genre : ");
                            String gender = sc.nextLine();
                            System.out.print("Entrer votre numero de telephone : ");
                            int numTel = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Entrer votre adresse mail : ");
                            String email = sc.nextLine().trim();
                            System.out.print("Entrer votre mot de passe : ");
                            String password = sc.nextLine().trim();
                            
                            User newUser = User.createUser(name, gender, numTel, email, password);
                            userBd.addUser(newUser);
                            System.out.println("Inscription réussie! Votre ID est: " + newUser.getUserId());
                            currentUser = newUser;
                            break;
                            
                        case 2:
                            System.out.print("Entrer votre Nom complet: ");
                            String na = sc.nextLine().trim();
                            System.out.print("Entrer votre genre : ");
                            String genre = sc.nextLine();
                            System.out.print("Entrer votre numero de telephone : ");
                            int num = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Entrer votre adresse mail : ");
                            String em = sc.nextLine().trim();
                            System.out.print("Entrer votre mot de passe : ");
                            String pass = sc.nextLine().trim();
                            String option = null;
                            
                            PremiumUser newPremiumUser = PremiumUser.createPremiumUser(na, genre, num, em, pass, option);
                            newPremiumUser.subscriptionPayment(sc);
                            userBd.addPremiumUser(newPremiumUser);
                            System.out.println("Inscription Premium réussie! Votre ID est: " + newPremiumUser.getUserId());
                            currentUser = newPremiumUser;
                            break;
                            
                        case 0:
                            System.out.println("Retour au menu principal.....");
                            break;
                            
                        default:
                            System.out.println("Choix invalide. Veuillez réessayer.");
                            break;
                    }

                    if (currentUser != null) {
                        int reponse;
                        do {
                            menuInscription();
                            System.out.print("veuillez entrer un choix :");
                            if (sc.hasNextInt()) {
                                reponse = sc.nextInt();
                                sc.nextLine();
                            } else {
                                System.out.println("Choix invalide. Veuillez entrer un numéro.");
                                sc.nextLine();
                                reponse = -1;
                                continue;
                            }

                            switch (reponse) {
                                case 1: // Poster une vidéo
                                    System.out.print("Entrer votre nom : ");
                                    String author = sc.nextLine();
                                    System.out.print("Entrer le titre de la video :");
                                    String titre = sc.nextLine();
                                    System.out.print("Entrer la description de la video : ");
                                    String description = sc.nextLine();
                                    System.out.print("Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : ");
                                    int resolution = sc.nextInt();
                                    sc.nextLine();
                                    LocalDate date = LocalDate.now();
                                    
                                    Video newVideo = Video.createVideo(titre, author, description, resolution, date);
                                    videoBd.addVideo(newVideo);
                                    currentUser.addVideo(newVideo);
                                    
                                    System.out.println("Vidéo créée avec l'ID: " + newVideo.getVideoId());
                                    System.out.println("\n");
                                    videoBd.displayVideo();
                                    
                                    System.out.print("\n souhaitez vous liker ?(O/N)...");
                                    String rep = sc.nextLine().toUpperCase();
                                    if (rep.equals("O")) {
                                        videoBd.likedVideo(titre);
                                        currentUser.addLikedVideo(newVideo.getVideoId());
                                        System.out.println("\n");
                                        videoBd.displayVideo();
                                    }
                                    
                                    System.out.println("\n Souhaitez-vous laisser un commentaire ? (O/N)...");
                                    String re = sc.nextLine().toUpperCase();
                                    if (re.equals("O")) {
                                        System.out.print("Entrez votre nom:");
                                        String nme = sc.nextLine();
                                        LocalDate te = LocalDate.now();
                                        System.out.print("Entrez votre commentaire :");
                                        String cm = sc.nextLine();
                                        
                                        Comments newComment = Comments.createComment(nme, te, cm);
                                        video.addComment(newComment);
                                        currentUser.addComment(newComment);
                                        
                                        System.out.println("Commentaire créé avec l'ID: " + newComment.getCommentId());
                                        System.out.println("\n");
                                        video.displayListComments();
                                        
                                        System.out.println("Souhaitez vous liker un commentaire ?(O/N)...");
                                        String v = sc.nextLine().toUpperCase();
                                        if (v.equals("O")) {
                                            System.out.print("Entrez l'id :");
                                            String iden = sc.nextLine();
                                            video.likedComment(iden);
                                        }
                                    }
                            
                                    break;

                                case 2: // Rechercher une vidéo
                                    System.out.print(" Entrer le titre de la video que vous desirez rechercher :");
                                    String titreRecherche = sc.nextLine();
                                    videoBd.researchVideo(titreRecherche);
                                    
                                    System.out.print("\n Souhaitez-vous liker cette video ?(O/N)");
                                    String ch = sc.nextLine().toUpperCase();
                                    if (ch.equals("O")) {
                                        videoBd.likedVideo(titreRecherche);
                                        Video foundVideo = videoBd.getVideoByTitle(titreRecherche);
                                        if (foundVideo != null) {
                                            currentUser.addLikedVideo(foundVideo.getVideoId());
                                        }
                                        videoBd.displayVideo();
                                    }
                                    
                                    System.out.println("\n Souhaitez-vous laisser un commentaire ? (O/N)...");
                                    String z = sc.nextLine().toUpperCase();
                                    if (z.equals("O")) {
                                        System.out.print("Entrez votre nom:");
                                        String nme = sc.nextLine();
                                        LocalDate te = LocalDate.now();
                                        System.out.print("Entrez votre commentaire :");
                                        String cm = sc.nextLine();
                                        
                                        Comments newComment = Comments.createComment(nme, te, cm);
                                        video.addComment(newComment);
                                        currentUser.addComment(newComment);
                                        
                                        System.out.println("Commentaire créé avec l'ID: " + newComment.getCommentId());
                                        System.out.println("\n");
                                        video.displayListComments();
                                    }
                                    
                                    System.out.println("Souhaitez vous liker un commentaire ?(O/N)...");
                                    String vv = sc.nextLine().toUpperCase();
                                    if (vv.equals("O")) {
                                        System.out.print("Entrez l'id :");
                                        String identi = sc.nextLine();
                                        video.likedComment(identi);
                                    }
                                    break;

                                case 3: // Supprimer une vidéo
                                    System.out.print("entrez le nom de la video a supprimer :");
                                    String n = sc.nextLine();
                                    videoBd.delateVideo(n);
                                    System.out.println("OPERATION REUSSIE AVEC SUCCES....");
                                    break;

                                case 4: // Créer une chaîne
                                    System.out.print("Entrer votre nom :");
                                    String nom = sc.nextLine();
                                    System.out.print("Entrez le nom de votre chaine :");
                                    String nomChaine = sc.nextLine();
                                    LocalDate dateCreation = LocalDate.now();
                                    
                                    Channel newChannel = Channel.createChannel(nom, nomChaine, dateCreation);
                                    channelBd.addChannel(newChannel);
                                    System.out.println("BIENVENUE DANS LA CHAINE " + nomChaine);
                                    break;

                                case 5: // Rechercher une chaîne
                                    System.out.print("Entrez le nom de la chaine :");
                                    String nameChannel = sc.nextLine();
                                    channelBd.searchChannel(nameChannel);
                                    break;

                                case 6: // Suivre une chaîne
                                    System.out.print("Entrez le nom de la chaine :");
                                    String nChannel = sc.nextLine();
                                    channelBd.searchChannel(nChannel);
                                    System.out.print("\nSouhaitez-vous suivre cette chaine ?(O/N)");
                                    String c = sc.nextLine().toUpperCase();
                                    if (c.equals("O")) {
                                        channelBd.followChannel(nChannel);
                                    }
                                    break;

                                case 7: // Arrêter de suivre une chaîne
                                    System.out.print("Entrez le nom de la chaine :");
                                    String nCh = sc.nextLine();
                                    channelBd.searchChannel(nCh);
                                    System.out.print("\nSouhaitez-vous arreter de suivre cette chaine ?(O/N)");
                                    String ct = sc.nextLine().toUpperCase();
                                    if (ct.equals("O")) {
                                        channelBd.unfollowChannel(nCh);
                                    }
                                    break;

                                case 8: // Poster une vidéo dans la chaîne
                                    System.out.print("Entrer votre nom : ");
                                    String a = sc.nextLine();
                                    System.out.print("Entrer le titre de la video :");
                                    String t = sc.nextLine();
                                    System.out.print("Entrer la description de la video : ");
                                    String d = sc.nextLine();
                                    System.out.print("Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : ");
                                    int r = sc.nextInt();
                                    sc.nextLine();
                                    LocalDate dt = LocalDate.now();
                                    
                                    Video channelVideo = Video.createVideo(t, a, d, r, dt);
                                    channelBd.postedVideo(channelVideo);
                                    currentUser.addVideo(channelVideo);
                                    break;

                                case 9: // Supprimer une vidéo de la chaîne
                                    System.out.print("Entrer l'ID de la vidéo : ");
                                    String idi = sc.nextLine();
                                    channelBd.delateVideo(idi);
                                    System.out.println("OPERATION REUSSIE AVEC SUCCES....");
                                    break;

                                case 10: // Retour au menu principal
                                    System.out.println("Retour au menu principal...");
                                    break;

                                case 11: // Afficher mon profil
                                    if (currentUser instanceof PremiumUser) {
                                        ((PremiumUser) currentUser).displayProfilePremiumUser((PremiumUser) currentUser);
                                    } else {
                                        userBd.displayProfileUser(currentUser.getEmail());
                                    }
                                    break;

                                case 0: // Fermer
                                    System.out.println("Merci pour votre fidelite............");
                                    System.exit(0);

                                default:
                                    System.out.println("Choix invalide. Veuillez réessayer.");
                                    break;
                            }
                        } while (reponse != 10 && reponse != 0);
                    }
                    break;

                case 2: // Se connecter
                    User loggedUser = userBd.logIn(sc);

                    if (loggedUser == null) {
                        System.out.println("\n CONNEXION ÉCHOUÉE ! Retour au menu principal...\n");
                        break;
                    }

                    currentUser = loggedUser;

                    int choice;
                    do {
                        menuInscription();
                        System.out.print("veuillez entrer un choix :");
                        if (sc.hasNextInt()) {
                            choice = sc.nextInt();
                            sc.nextLine();
                        } else {
                            System.out.println("Choix invalide. Veuillez entrer un numéro.");
                            sc.nextLine();
                            choice = -1;
                            continue;
                        }

                        switch (choice) {
                            case 1: // Poster une vidéo
                                System.out.print("Entrer votre nom : ");
                                String author = sc.nextLine();
                                System.out.print("Entrer le titre de la video :");
                                String titre = sc.nextLine();
                                System.out.print("Entrer la description de la video : ");
                                String description = sc.nextLine();
                                System.out.print("Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : ");
                                int resolution = sc.nextInt();
                                sc.nextLine();
                                LocalDate dateVid = LocalDate.now();
                                
                                Video newVideo = Video.createVideo(titre, author, description, resolution, dateVid);
                                videoBd.addVideo(newVideo);
                                currentUser.addVideo(newVideo);
                                
                                System.out.println("Vidéo créée avec l'ID: " + newVideo.getVideoId());
                                videoBd.displayVideo();
                                
                                System.out.print(" souhaitez vous liker ?(O/N)...");
                                String rep = sc.nextLine().toUpperCase();
                                if (rep.equals("O")) {
                                    videoBd.likedVideo(titre);
                                    currentUser.addLikedVideo(newVideo.getVideoId());
                                    System.out.println("\n");
                                    videoBd.displayVideo();
                                }
                                
                                System.out.println("\n Souhaitez-vous laisser un commentaire ? (O/N)...");
                                String re = sc.nextLine().toUpperCase();
                                if (re.equals("O")) {
                                    System.out.print("Entrez votre nom:");
                                    String nme = sc.nextLine();
                                    LocalDate te = LocalDate.now();
                                    System.out.print("Entrez votre commentaire :");
                                    String cm = sc.nextLine();
                                    
                                    Comments newComment = Comments.createComment(nme, te, cm);
                                    video.addComment(newComment);
                                    currentUser.addComment(newComment);
                                    
                                    System.out.println("Commentaire créé avec l'ID: " + newComment.getCommentId());
                                    System.out.println("\n");
                                    video.displayListComments();
                                }
                                
                                System.out.println("Souhaitez vous liker un commentaire ?(O/N)...");
                                String v = sc.nextLine().toUpperCase();
                                if (v.equals("O")) {
                                    System.out.print("Entrez l'id :");
                                    String identi = sc.nextLine();
                                    video.likedComment(identi);
                                }
                                break;

                            case 2: // Rechercher une vidéo
                                System.out.print(" Entrer le titre de la video que vous desirez rechercher :");
                                String titreRecherche = sc.nextLine();
                                videoBd.researchVideo(titreRecherche);
                                
                                System.out.print("\n Souhaitez-vous liker cette video ?(O/N)...");
                                String ch = sc.nextLine().toUpperCase();
                                if (ch.equals("O")) {
                                    videoBd.likedVideo(titreRecherche);
                                    Video foundVideo = videoBd.getVideoByTitle(titreRecherche);
                                    if (foundVideo != null) {
                                        currentUser.addLikedVideo(foundVideo.getVideoId());
                                    }
                                    System.out.println("\n");
                                    videoBd.displayVideo();
                                }
                                
                                System.out.println(" \nSouhaitez-vous laisser un commentaire ? (O/N)...");
                                String o = sc.nextLine().toUpperCase();
                                if (o.equals("O")) {
                                    System.out.print("Entrez votre nom:");
                                    String nme = sc.nextLine();
                                    LocalDate te = LocalDate.now();
                                    System.out.print("Entrez votre commentaire :");
                                    String cm = sc.nextLine();
                                    
                                    Comments newComment = Comments.createComment(nme, te, cm);
                                    video.addComment(newComment);
                                    currentUser.addComment(newComment);
                                    
                                    System.out.println("Commentaire créé avec l'ID: " + newComment.getCommentId());
                                    System.out.println("\n");
                                    video.displayListComments();
                                }
                                
                                System.out.println("Souhaitez vous liker un commentaire ?(O/N)...");
                                String w = sc.nextLine().toUpperCase();
                                if (w.equals("O")) {
                                    System.out.print("Entrez l'id :");
                                    String identi = sc.nextLine();
                                    video.likedComment(identi);
                                }
                                break;

                            case 3: // Supprimer une vidéo
                                System.out.print("entrez le nom de la video a supprimer :");
                                String n = sc.nextLine();
                                videoBd.delateVideo(n);
                                System.out.println("OPERATION REUSSIE AVEC SUCCES....");
                                break;

                            case 4: // Créer une chaîne
                                System.out.print("Entrer votre nom :");
                                String nom = sc.nextLine();
                                System.out.print("Entrez le nom de votre chaine :");
                                String nomChaine = sc.nextLine();
                                LocalDate dateCreation = LocalDate.now();
                                
                                Channel newChannel = Channel.createChannel(nom, nomChaine, dateCreation);
                                channelBd.addChannel(newChannel);
                                System.out.println("BIENVENUE DANS LA CHAINE " + nomChaine);
                                break;

                            case 5: // Rechercher une chaîne
                                System.out.print("Entrez le nom de la chaine :");
                                String nameChannel = sc.nextLine();
                                channelBd.searchChannel(nameChannel);
                                break;

                            case 6: // Suivre une chaîne
                                System.out.print("Entrez le nom de la chaine :");
                                String nChannel = sc.nextLine();
                                channelBd.searchChannel(nChannel);
                                System.out.print("\nSouhaitez-vous suivre cette chaine ?(O/N)");
                                String c = sc.nextLine().toUpperCase();
                                if (c.equals("O")) {
                                    channelBd.followChannel(nChannel);
                                }
                                break;

                            case 7: // Arrêter de suivre une chaîne
                                System.out.print("Entrez le nom de la chaine :");
                                String nCh = sc.nextLine();
                                channelBd.searchChannel(nCh);
                                System.out.print("\nSouhaitez-vous arreter de suivre cette chaine ?(O/N)");
                                String ct = sc.nextLine().toUpperCase();
                                if (ct.equals("O")) {
                                    channelBd.unfollowChannel(nCh);
                                }
                                break;

                            case 8: // Poster une vidéo dans la chaîne
                                System.out.print("Entrer votre nom : ");
                                String a = sc.nextLine();
                                System.out.print("Entrer le titre de la video :");
                                String t = sc.nextLine();
                                System.out.print("Entrer la description de la video : ");
                                String d = sc.nextLine();
                                System.out.print("Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : ");
                                int r = sc.nextInt();
                                sc.nextLine();
                                LocalDate dt = LocalDate.now();
                                
                                Video channelVideo = Video.createVideo(t, a, d, r, dt);
                                channelBd.postedVideo(channelVideo);
                                currentUser.addVideo(channelVideo);
                                break;

                            case 9: // Supprimer une vidéo de la chaîne
                                System.out.print("Entrer l'ID de la vidéo : ");
                                String idi = sc.nextLine();
                                channelBd.delateVideo(idi);
                                System.out.println("OPERATION REUSSIE AVEC SUCCES....");
                                break;

                            case 10: // Retour au menu principal
                                System.out.println("Retour au menu principal...");
                                break;

                            case 11: // Afficher mon profil
                                userBd.displayProfileUser(currentUser.getEmail());
                                break;

                            case 0: // Fermer
                                System.out.println("\nMerci pour votre fidelite.*YOUTUBE RECOMMANDER* vous dit au Revoir!!!!!............");
                                System.exit(0);

                            default:
                                System.out.println("Choix invalide. Veuillez réessayer.");
                                break;
                        }
                    } while (choice != 10 && choice != 0);
                    break;

                case 3: // Se déconnecter
                    System.out.println("Entrez l'email du compte:");
                    String e = sc.nextLine();
                    userBd.logOut(e);
                    currentUser = null;
                    System.out.println("Vous êtes déconnecté.");
                    break;

                case 0: // Quitter
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
        } while (true);
    }
}
