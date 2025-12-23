#include "UserBd.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <iomanip>

const std::string UserBd::USER_INDEX_FILE = "user_index.txt";

UserBd::UserBd() {
    loadUserIndex();
}

UserBd::~UserBd() {
    for (auto user : userList) {
        delete user;
    }
}

void UserBd::addUser(User* user) {
    userList.push_back(user);
    user->saveToFile();
    saveUserIndex();
}

void UserBd::addPremiumUser(PremiumUser* premiumUser) {
    userList.push_back(premiumUser);
    premiumUser->saveToFile();
    saveUserIndex();
}

void UserBd::logOut(const std::string& email) {
    for (auto it = userList.begin(); it != userList.end(); ++it) {
        if ((*it)->email == email) {
            userList.erase(it);
            return;
        }
    }
}

void UserBd::displayUser() const {
    std::cout << "\t \t \t************************************************************************************\n";
    std::cout << "\t \t \t*                               LISTE DES UTILISATEURS                             *\n";
    std::cout << "\t \t \t************************************************************************************\n";
    for (const auto& user : userList) {
        std::cout << "\t \tID : " << user->userId << "\n";
        std::cout << "\t \tNom : " << user->nameUser << "\n";
        std::cout << "\t \tgenre : " << user->gender << "\n";
        std::cout << "\t \tNumero de Telephone : " << user->numTel << "\n";
        std::cout << "\t \tEmail : " << user->email << "\n";
        std::cout << "\t \tMot de Passe : " << user->password << "\n";
    }
}

User* UserBd::logIn() {
    std::string mail, password;
    std::cout << "Veuillez entrer l'adresse mail du compte :";
    std::getline(std::cin, mail);
    std::cout << "veuillez entrer le mot de passe du compte :";
    std::getline(std::cin, password);
    
    // Chercher dans la liste en mémoire
    for (auto& user : userList) {
        if (user->email == mail && user->password == password) {
            std::cout << "\n\tCONNEXION REUSSIE. Bienvenue, " << user->nameUser << "!\n";
            handlePremiumLogin(user);
            return user;
        }
    }
    
    // Chercher dans les fichiers
    User* foundUser = searchUserInFiles(mail, password);
    if (foundUser != nullptr) {
        std::cout << "\n\tCONNEXION REUSSIE. Bienvenue, " << foundUser->nameUser << "!\n";
        userList.push_back(foundUser);
        handlePremiumLogin(foundUser);
        return foundUser;
    }
    
    std::cout << "Aucun compte ne correspond à cet email ou mot de passe. Veuillez vous inscrire !\n";
    return nullptr;
}

User* UserBd::searchUserInFiles(const std::string& email, const std::string& password) {
    // Recherche dans les fichiers us001.txt à us999.txt
    for (int i = 1; i < 1000; i++) {
        std::ostringstream oss;
        oss << "us" << std::setw(3) << std::setfill('0') << i;
        std::string userId = oss.str();
        
        if (!User::userFileExists(userId)) continue;
        
        // Essayer premium d'abord
        PremiumUser premiumUser = PremiumUser::loadPremiumFromFile(userId);
        if (!premiumUser.userId.empty() && premiumUser.email == email && premiumUser.password == password) {
            return new PremiumUser(premiumUser);
        }
        
        // Sinon utilisateur normal
        User user = User::loadFromFile(userId);
        if (!user.userId.empty() && user.email == email && user.password == password) {
            return new User(user);
        }
    }
    
    return nullptr;
}

void UserBd::handlePremiumLogin(User* user) {
    PremiumUser* premiumUser = dynamic_cast<PremiumUser*>(user);
    if (premiumUser != nullptr) {
        if (premiumUser->paymentSubscript.empty()) {
            std::cout << "\n*** COMPTE PREMIUM : Votre souscription doit être activée. ***\n";
            premiumUser->subscriptionPayment();
        } else {
            std::cout << "Statut Premium actuel : " << premiumUser->paymentSubscript << "\n";
        }
    }
}

void UserBd::displayProfileUser(const std::string& email) {
    User* user = nullptr;
    
    // Chercher en mémoire
    for (auto& u : userList) {
        if (u->email == email) {
            user = u;
            break;
        }
    }
    
    // Chercher dans les fichiers
    if (user == nullptr) {
        user = findUserByEmail(email);
    }
    
    if (user == nullptr) {
        std::cout << "\n  Aucun utilisateur trouvé avec cet email.\n";
        return;
    }
    
    // Affichage du profil
    std::cout << "\n";
    std::cout << "\t \t╔═══════════════════════════════════════════════════════════╗\n";
    std::cout << "\t \t║                      PROFIL DE L'UTILISATEUR              ║\n";
    std::cout << "\t \t╚═══════════════════════════════════════════════════════════╝\n";
    std::cout << "\n";
    
    PremiumUser* premiumUser = dynamic_cast<PremiumUser*>(user);
    if (premiumUser != nullptr) {
        std::cout << "\t \t TYPE DE COMPTE      : PREMIUM\n";
        std::cout << "\t \t───────────────────────────────────────────────────────────────\n";
        std::cout << "\t \t ID Utilisateur      : " << premiumUser->userId << "\n";
        std::cout << "\t \t Nom                 : " << premiumUser->nameUser << "\n";
        std::cout << "\t \t  Genre               : " << premiumUser->gender << "\n";
        std::cout << "\t \t Téléphone           : " << premiumUser->numTel << "\n";
        std::cout << "\t \t  Email               : " << premiumUser->email << "\n";
        std::cout << "\t \t Mot de Passe        : " << std::string(premiumUser->password.length(), '*') << "\n";
        std::cout << "\t \t Souscription        : " << (premiumUser->paymentSubscript.empty() ? "Non activée" : premiumUser->paymentSubscript) << "\n";
        std::cout << "\t \t Vidéos créées       : " << premiumUser->userVideos.size() << "\n";
        std::cout << "\t \t Vidéos likées       : " << premiumUser->likedVideos.size() << "\n";
        std::cout << "\t \t Commentaires        : " << premiumUser->userComments.size() << "\n";
        std::cout << "\t \t───────────────────────────────────────────────────────────────\n";
    } else {
        std::cout << "\t \t TYPE DE COMPTE      : STANDARD\n";
        std::cout << "\t \t───────────────────────────────────────────────────────────────\n";
        std::cout << "\t \t ID Utilisateur      : " << user->userId << "\n";
        std::cout << "\t \t Nom                 : " << user->nameUser << "\n";
        std::cout << "\t \t  Genre               : " << user->gender << "\n";
        std::cout << "\t \t Téléphone           : " << user->numTel << "\n";
        std::cout << "\t \t  Email               : " << user->email << "\n";
        std::cout << "\t \t Mot de Passe        : " << std::string(user->password.length(), '*') << "\n";
        std::cout << "\t \t Vidéos créées       : " << user->userVideos.size() << "\n";
        std::cout << "\t \t Vidéos likées       : " << user->likedVideos.size() << "\n";
        std::cout << "\t \t Commentaires        : " << user->userComments.size() << "\n";
        std::cout << "\t \t───────────────────────────────────────────────────────────────\n";
    }
    std::cout << "\n";
}

User* UserBd::findUserByEmail(const std::string& email) {
    for (int i = 1; i < 1000; i++) {
        std::ostringstream oss;
        oss << "us" << std::setw(3) << std::setfill('0') << i;
        std::string userId = oss.str();
        
        if (!User::userFileExists(userId)) continue;
        
        PremiumUser premiumUser = PremiumUser::loadPremiumFromFile(userId);
        if (!premiumUser.userId.empty() && premiumUser.email == email) {
            return new PremiumUser(premiumUser);
        }
        
        User user = User::loadFromFile(userId);
        if (!user.userId.empty() && user.email == email) {
            return new User(user);
        }
    }
    
    return nullptr;
}

void UserBd::saveUserIndex() {
    std::ofstream ofs(USER_INDEX_FILE);
    if (ofs.is_open()) {
        for (const auto& user : userList) {
            ofs << user->userId << "|" << user->email << "\n";
        }
        ofs.close();
    }
}

void UserBd::loadUserIndex() {
    std::ifstream ifs(USER_INDEX_FILE);
    if (!ifs.is_open()) return;
    
    std::string line;
    while (std::getline(ifs, line)) {
        std::istringstream iss(line);
        std::string userId;
        std::getline(iss, userId, '|');
        
        PremiumUser premiumUser = PremiumUser::loadPremiumFromFile(userId);
        if (!premiumUser.userId.empty()) {
            userList.push_back(new PremiumUser(premiumUser));
        } else {
            User user = User::loadFromFile(userId);
            if (!user.userId.empty()) {
                userList.push_back(new User(user));
            }
        }
    }
    
    ifs.close();
}
