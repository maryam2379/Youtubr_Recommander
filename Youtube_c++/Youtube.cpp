#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include <sstream>
#include <iomanip>
#include "IdGenerator.h"
#include "User.h"
#include "PremiumUser.h"
#include "UserBd.h"
#include "Video.h"
#include "VideoBd.h"
#include "Channel.h"
#include "ChannelBd.h"
#include "Comments.h"

void menu() {
    std::cout << "\t \t \t @#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$\n";
    std::cout << "\t \t \t @#$                                                                                 @#$\n";
    std::cout << "\t \t \t @#$                         ~~~ YOUTUBE RECOMMANDER ~~~                             @#$\n";
    std::cout << "\t \t \t @#$                                                                                 @#$\n";
    std::cout << "\t \t \t @#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$\n";
    std::cout << "\n";
    std::cout << "\t......................... BIENVENUE SUR LE YOUTUBE RECOMMANDER.!!!!!!!!! ...................\n";
    std::cout << "\n";
    std::cout << "\t \t \t************************************************************************************\n";
    std::cout << "\t \t \t*                      MENU DU YOUTUBE RECOMMANDER                                 *\n";
    std::cout << "\t \t \t************************************************************************************\n";
    std::cout << "\n";
    std::cout << "\t1............................................  S'inscrire .....................................\n";
    std::cout << "\t2..........................................  Se Connecter .....................................\n";
    std::cout << "\t3........................................  Se deconnecter .....................................\n";
    std::cout << "\t0.............................................. QUITTER  ......................................\n";
}

void menuInscription() {
    #ifdef _WIN32
        system("cls");
    #else
        system("clear");
    #endif
    
    std::cout << "\n\n\n\n";
    std::cout << "1................................ Poster une video...................................\n";
    std::cout << "2.............................   Rechercher une video ...............................\n";
    std::cout << "3................................. Supprimer une video ..............................\n";
    std::cout << "4................................ Creer une chaine ..................................\n";
    std::cout << "5............................. Rechercher une chaine.................................\n";
    std::cout << "6............................... suivre une chaine ..................................\n";
    std::cout << "7............................. Arreter de suivre une chaine .........................\n";
    std::cout << "8.......................... Poster une video dans la chaine .........................\n";
    std::cout << "9.......................... Supprimer une video de la chaine ........................\n";
    std::cout << "10.................................. Retour au menu principal .......................\n";
    std::cout << "11.................................. Afficher mon profil ............................\n";
    std::cout << "0............................. Fermer le Youtube Recommander ........................\n";
}

void menuPayment() {
    #ifdef _WIN32
        system("cls");
    #else
        system("clear");
    #endif
    
    std::cout << "\n\n\n\n";
    std::cout << "1........................ S'incrire comme un utilisateur............................\n";
    std::cout << "2................... S'incrire comme un utilisateur premium ........................\n";
    std::cout << "0................................. Retour  .........................................\n";
}

std::string getCurrentDate() {
    time_t now = time(0);
    tm* ltm = localtime(&now);
    std::ostringstream oss;
    oss << (1900 + ltm->tm_year) << "-" 
        << std::setw(2) << std::setfill('0') << (1 + ltm->tm_mon) << "-" 
        << std::setw(2) << std::setfill('0') << ltm->tm_mday;
    return oss.str();
}

void handleUserMenu(User* currentUser, Video& video, VideoBd& videoBd, ChannelBd& channelBd, UserBd& userBd);

int main() {
    IdGenerator::initialize();
    
    Video video;
    UserBd userBd;
    VideoBd videoBd;
    ChannelBd channelBd;
    User* currentUser = nullptr;
    
    while (true) {
        menu();
        std::cout << "veuiller entrer une reponse : ";
        int choix;
        std::cin >> choix;
        std::cin.ignore();
        
        if (choix == 1) {  // S'inscrire
            menuPayment();
            std::cout << "veuiller entrer une reponse : ";
            int inscripChoix;
            std::cin >> inscripChoix;
            std::cin.ignore();
            
            if (inscripChoix == 1) {  // Utilisateur standard
                std::string name, gender, email, password;
                int numTel;
                
                std::cout << "Entrer votre Nom complet: ";
                std::getline(std::cin, name);
                std::cout << "Entrer votre genre : ";
                std::getline(std::cin, gender);
                std::cout << "Entrer votre numero de telephone : ";
                std::cin >> numTel;
                std::cin.ignore();
                std::cout << "Entrer votre adresse mail : ";
                std::getline(std::cin, email);
                std::cout << "Entrer votre mot de passe : ";
                std::getline(std::cin, password);
                
                User* newUser = new User(User::createUser(name, gender, numTel, email, password));
                userBd.addUser(newUser);
                std::cout << "Inscription réussie! Votre ID est: " << newUser->getUserId() << "\n";
                currentUser = newUser;
                
            } else if (inscripChoix == 2) {  // Utilisateur premium
                std::string name, gender, email, password;
                int numTel;
                
                std::cout << "Entrer votre Nom complet: ";
                std::getline(std::cin, name);
                std::cout << "Entrer votre genre : ";
                std::getline(std::cin, gender);
                std::cout << "Entrer votre numero de telephone : ";
                std::cin >> numTel;
                std::cin.ignore();
                std::cout << "Entrer votre adresse mail : ";
                std::getline(std::cin, email);
                std::cout << "Entrer votre mot de passe : ";
                std::getline(std::cin, password);
                
                PremiumUser* newPremiumUser = new PremiumUser(PremiumUser::createPremiumUser(name, gender, numTel, email, password, ""));
                newPremiumUser->subscriptionPayment();
                userBd.addPremiumUser(newPremiumUser);
                std::cout << "Inscription Premium réussie! Votre ID est: " << newPremiumUser->getUserId() << "\n";
                currentUser = newPremiumUser;
                
            } else if (inscripChoix == 0) {
                std::cout << "Retour au menu principal.....\n";
                continue;
            }
            
            // Menu après inscription
            if (currentUser != nullptr) {
                handleUserMenu(currentUser, video, videoBd, channelBd, userBd);
            }
            
        } else if (choix == 2) {  // Se connecter
            User* loggedUser = userBd.logIn();
            
            if (loggedUser == nullptr) {
                std::cout << "\n CONNEXION ÉCHOUÉE ! Retour au menu principal...\n\n";
                continue;
            }
            
            currentUser = loggedUser;
            handleUserMenu(currentUser, video, videoBd, channelBd, userBd);
            
        } else if (choix == 3) {  // Se déconnecter
            std::string email;
            std::cout << "Entrez l'email du compte: ";
            std::getline(std::cin, email);
            userBd.logOut(email);
            currentUser = nullptr;
            std::cout << "Vous êtes déconnecté.\n";
            
        } else if (choix == 0) {  // Quitter
            std::cout << "Merci pour votre visite!\n";
            break;
            
        } else {
            std::cout << "Choix invalide. Veuillez réessayer.\n";
        }
    }
    
    return 0;
}

void handleUserMenu(User* currentUser, Video& video, VideoBd& videoBd, ChannelBd& channelBd, UserBd& userBd) {
    int reponse;
    do {
        menuInscription();
        std::cout << "veuillez entrer un choix :";
        std::cin >> reponse;
        std::cin.ignore();
        
        if (reponse == 1) {  // Poster une vidéo
            std::string author, titre, description;
            int resolution;
            
            std::cout << "Entrer votre nom : ";
            std::getline(std::cin, author);
            std::cout << "Entrer le titre de la video :";
            std::getline(std::cin, titre);
            std::cout << "Entrer la description de la video : ";
            std::getline(std::cin, description);
            std::cout << "Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : ";
            std::cin >> resolution;
            std::cin.ignore();
            
            Video newVideo = Video::createVideo(titre, author, description, resolution, getCurrentDate());
            videoBd.addVideo(newVideo);
            currentUser->addVideo(newVideo);
            
            std::cout << "Vidéo créée avec l'ID: " << newVideo.getVideoId() << "\n\n";
            videoBd.displayVideo();
            
            std::string rep;
            std::cout << "\n souhaitez vous liker ?(O/N)...";
            std::getline(std::cin, rep);
            if (rep == "O" || rep == "o") {
                videoBd.likedVideo(titre);
                currentUser->addLikedVideo(newVideo.getVideoId());
                std::cout << "\n";
                videoBd.displayVideo();
            }
            
            std::string re;
            std::cout << "\n Souhaitez-vous laisser un commentaire ? (O/N)...";
            std::getline(std::cin, re);
            if (re == "O" || re == "o") {
                std::string nme, cm;
                std::cout << "Entrez votre nom:";
                std::getline(std::cin, nme);
                std::cout << "Entrez votre commentaire :";
                std::getline(std::cin, cm);
                
                Comments newComment = Comments::createComment(nme, getCurrentDate(), cm);
                video.addComment(newComment);
                currentUser->addComment(newComment);
                
                std::cout << "Commentaire créé avec l'ID: " << newComment.getCommentId() << "\n\n";
                video.displayListComments();
                
                std::string v;
                std::cout << "Souhaitez vous liker un commentaire ?(O/N)...";
                std::getline(std::cin, v);
                if (v == "O" || v == "o") {
                    std::string iden;
                    std::cout << "Entrez l'id :";
                    std::getline(std::cin, iden);
                    video.likedComment(iden);
                }
            }
            
        } else if (reponse == 2) {  // Rechercher une vidéo
            std::string titreRecherche;
            std::cout << " Entrer le titre de la video que vous desirez rechercher :";
            std::getline(std::cin, titreRecherche);
            videoBd.researchVideo(titreRecherche);
            
            std::string ch;
            std::cout << "\n Souhaitez-vous liker cette video ?(O/N)";
            std::getline(std::cin, ch);
            if (ch == "O" || ch == "o") {
                videoBd.likedVideo(titreRecherche);
                Video* foundVideo = videoBd.getVideoByTitle(titreRecherche);
                if (foundVideo != nullptr) {
                    currentUser->addLikedVideo(foundVideo->getVideoId());
                }
                videoBd.displayVideo();
            }
            
        } else if (reponse == 3) {  // Supprimer une vidéo
            std::string n;
            std::cout << "entrez le nom de la video a supprimer :";
            std::getline(std::cin, n);
            videoBd.deleteVideo(n);
            std::cout << "OPERATION REUSSIE AVEC SUCCES....\n";
            
        } else if (reponse == 4) {  // Créer une chaîne
            std::string nom, nomChaine;
            std::cout << "Entrer votre nom :";
            std::getline(std::cin, nom);
            std::cout << "Entrez le nom de votre chaine :";
            std::getline(std::cin, nomChaine);
            
            Channel newChannel = Channel::createChannel(nom, nomChaine, getCurrentDate());
            channelBd.addChannel(newChannel);
            std::cout << "BIENVENUE DANS LA CHAINE " << nomChaine << "\n";
            
        } else if (reponse == 5) {  // Rechercher une chaîne
            std::string nameChannel;
            std::cout << "Entrez le nom de la chaine :";
            std::getline(std::cin, nameChannel);
            channelBd.searchChannel(nameChannel);
            
        } else if (reponse == 6) {  // Suivre une chaîne
            std::string nChannel;
            std::cout << "Entrez le nom de la chaine :";
            std::getline(std::cin, nChannel);
            channelBd.searchChannel(nChannel);
            
            std::string c;
            std::cout << "\nSouhaitez-vous suivre cette chaine ?(O/N)";
            std::getline(std::cin, c);
            if (c == "O" || c == "o") {
                channelBd.followChannel(nChannel);
            }
            
        } else if (reponse == 7) {  // Arrêter de suivre une chaîne
            std::string nCh;
            std::cout << "Entrez le nom de la chaine :";
            std::getline(std::cin, nCh);
            channelBd.searchChannel(nCh);
            
            std::string ct;
            std::cout << "\nSouhaitez-vous arreter de suivre cette chaine ?(O/N)";
            std::getline(std::cin, ct);
            if (ct == "O" || ct == "o") {
                channelBd.unfollowChannel(nCh);
            }
            
        } else if (reponse == 8) {  // Poster une vidéo dans la chaîne
            std::string a, t, d;
            int r;
            
            std::cout << "Entrer votre nom : ";
            std::getline(std::cin, a);
            std::cout << "Entrer le titre de la video :";
            std::getline(std::cin, t);
            std::cout << "Entrer la description de la video : ";
            std::getline(std::cin, d);
            std::cout << "Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : ";
            std::cin >> r;
            std::cin.ignore();
            
            Video channelVideo = Video::createVideo(t, a, d, r, getCurrentDate());
            channelBd.postedVideo(channelVideo);
            currentUser->addVideo(channelVideo);
            
        } else if (reponse == 9) {  // Supprimer une vidéo de la chaîne
            std::string idi;
            std::cout << "Entrer l'ID de la vidéo : ";
            std::getline(std::cin, idi);
            channelBd.deleteVideo(idi);
            std::cout << "OPERATION REUSSIE AVEC SUCCES....\n";
            
        } else if (reponse == 10) {  // Retour au menu principal
            std::cout << "Retour au menu principal...\n";
            
        } else if (reponse == 11) {  // Afficher mon profil
            PremiumUser* premiumUser = dynamic_cast<PremiumUser*>(currentUser);
            if (premiumUser != nullptr) {
                premiumUser->displayProfilePremiumUser();
            } else {
                userBd.displayProfileUser(currentUser->getEmail());
            }
            
        } else if (reponse == 0) {  // Fermer
            std::cout << "Merci pour votre fidelite............\n";
            exit(0);
            
        } else {
            std::cout << "Choix invalide. Veuillez réessayer.\n";
        }
        
    } while (reponse != 10 && reponse != 0);
}

/*
COMPILATION AVEC GCC:
=====================

Pour compiler ce projet avec gcc/g++, utilisez les commandes suivantes:

1. Compiler tous les fichiers .cpp:
   g++ -c IdGenerator.cpp Comments.cpp Video.cpp VideoBd.cpp User.cpp PremiumUser.cpp UserBd.cpp Channel.cpp ChannelBd.cpp youtube.cpp

2. Lier tous les fichiers objets:
   g++ IdGenerator.o Comments.o Video.o VideoBd.o User.o PremiumUser.o UserBd.o Channel.o ChannelBd.o youtube.o -o youtube

3. Ou en une seule commande:
   g++ IdGenerator.cpp Comments.cpp Video.cpp VideoBd.cpp User.cpp PremiumUser.cpp UserBd.cpp Channel.cpp ChannelBd.cpp youtube.cpp -o youtube

4. Exécuter le programme:
   ./youtube

STRUCTURE DES FICHIERS:
=======================
IdGenerator.h / IdGenerator.cpp
Comments.h / Comments.cpp
Video.h / Video.cpp
VideoBd.h / VideoBd.cpp
User.h / User.cpp
PremiumUser.h / PremiumUser.cpp
UserBd.h / UserBd.cpp
Channel.h / Channel.cpp
ChannelBd.h / ChannelBd.cpp
youtube.cpp (main)

*/
