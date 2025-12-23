#include "PremiumUser.h"
#include "IdGenerator.h"
#include <iostream>
#include <fstream>
#include <sstream>

PremiumUser::PremiumUser() : User() {}

PremiumUser::PremiumUser(const std::string& name, const std::string& gender, 
                        int numTel, const std::string& email, const std::string& password) {
    this->userId = IdGenerator::generateUserId();
    this->nameUser = name;
    this->gender = gender;
    this->numTel = numTel;
    this->email = email;
    this->password = password;
}

PremiumUser PremiumUser::createPremiumUser(const std::string& name, const std::string& gender, 
                                          int numTel, const std::string& email, 
                                          const std::string& password, const std::string& option) {
    PremiumUser premiumUser(name, gender, numTel, email, password);
    premiumUser.paymentSubscript = option;
    return premiumUser;
}

void PremiumUser::subscriptionPayment() {
    std::cout << "\n--- OPTIONS DE SOUSCRIPTION PREMIUM ---\n";
    std::cout << "1....................OPTION 1 : 2,05$/ mois .....................\n";
    std::cout << "2....................OPTION 2 : 5,57$/ mois .....................\n";
    std::cout << "3....................OPTION 3 : 12,89$/ mois ....................\n";
    std::cout << "Quelle option desirez vous souscrire ?.. ";
    
    int choix;
    std::cin >> choix;
    std::cin.ignore();
    
    std::string optionDescription;
    std::string cardNumber;
    
    switch (choix) {
        case 1:
            optionDescription = "OPTION 1 : 2,05$/mois";
            std::cout << "VOUS SOUSCRIVREZ BIEN À L'" << optionDescription << "......... \n";
            std::cout << "Inserez le numero de votre carte bancaire :";
            std::getline(std::cin, cardNumber);
            paymentSubscript = optionDescription;
            std::cout << " PAIEMENT ET OPERATION REUSSIS\n";
            break;
        case 2:
            optionDescription = "OPTION 2 : 5,57$/mois";
            std::cout << "VOUS SOUSCRIVREZ BIEN À L'" << optionDescription << "......... \n";
            std::cout << "Inserez le numero de votre carte bancaire :";
            std::getline(std::cin, cardNumber);
            paymentSubscript = optionDescription;
            std::cout << " PAIEMENT ET OPERATION REUSSIS\n";
            break;
        case 3:
            optionDescription = "OPTION 3 : 12,89$/mois";
            std::cout << "VOUS SOUSCRIVREZ BIEN À L'" << optionDescription << "......... \n";
            std::cout << "Inserez le numero de votre carte bancaire :";
            std::getline(std::cin, cardNumber);
            paymentSubscript = optionDescription;
            std::cout << " PAIEMENT ET OPERATION REUSSIS\n";
            break;
        default:
            std::cout << "Choix d'option invalide. Souscription non effectuée.\n";
            break;
    }
    
    saveToFile();
}

void PremiumUser::displayProfilePremiumUser() const {
    std::cout << "\t \t ***************** INFORMATION DE L'UTILISATEUR **********************\n\n";
    std::cout << "ID Utilisateur : " << userId << "\n";
    std::cout << "Nom : " << nameUser << "\n";
    std::cout << "Genre : " << gender << "\n";
    std::cout << "Numero de Telephone : " << numTel << "\n";
    std::cout << "Email : " << email << "\n";
    std::cout << "Mot de Passe : " << password << "\n";
    std::cout << "Option de souscription: " << paymentSubscript << "\n";
    std::cout << "Nombre de vidéos créées: " << userVideos.size() << "\n";
    std::cout << "Nombre de likes: " << likedVideos.size() << "\n";
    std::cout << "Nombre de commentaires: " << userComments.size() << "\n";
}

void PremiumUser::saveToFile() {
    std::ofstream ofs(userId + ".txt");
    if (ofs.is_open()) {
        ofs << "USERTYPE:PREMIUM\n";
        ofs << "USERINFO:" << userId << "|" << nameUser << "|" << gender << "|" 
            << numTel << "|" << email << "|" << password << "|" << paymentSubscript << "\n";
        
        ofs << "VIDEOS:" << userVideos.size() << "\n";
        for (const auto& video : userVideos) {
            ofs << video.serialize() << "\n";
        }
        
        ofs << "LIKES:" << likedVideos.size() << "\n";
        for (const auto& videoId : likedVideos) {
            ofs << videoId << "\n";
        }
        
        ofs << "COMMENTS:" << userComments.size() << "\n";
        for (const auto& comment : userComments) {
            ofs << comment.serialize() << "\n";
        }
        
        ofs.close();
        std::cout << "Données utilisateur premium sauvegardées dans " << userId << ".txt\n";
    }
}

PremiumUser PremiumUser::loadPremiumFromFile(const std::string& userId) {
    PremiumUser user;
    std::ifstream ifs(userId + ".txt");
    
    if (!ifs.is_open()) {
        user.userId = "";
        return user;
    }
    
    std::string line;
    std::getline(ifs, line);
    
    if (line != "USERTYPE:PREMIUM") {
        user.userId = "";
        return user;
    }
    
    std::getline(ifs, line);
    if (line.find("USERINFO:") != 0) {
        user.userId = "";
        return user;
    }
    
    std::istringstream iss(line.substr(9));
    std::string part;
    std::getline(iss, user.userId, '|');
    std::getline(iss, user.nameUser, '|');
    std::getline(iss, user.gender, '|');
    std::getline(iss, part, '|');
    user.numTel = std::stoi(part);
    std::getline(iss, user.email, '|');
    std::getline(iss, user.password, '|');
    std::getline(iss, user.paymentSubscript, '|');
    
    // Lire vidéos
    std::getline(ifs, line);
    if (line.find("VIDEOS:") == 0) {
        int count = std::stoi(line.substr(7));
        for (int i = 0; i < count; i++) {
            std::getline(ifs, line);
            if (!line.empty()) {
                user.userVideos.push_back(Video::deserialize(line));
            }
        }
    }
    
    // Lire likes
    std::getline(ifs, line);
    if (line.find("LIKES:") == 0) {
        int count = std::stoi(line.substr(6));
        for (int i = 0; i < count; i++) {
            std::getline(ifs, line);
            if (!line.empty()) {
                user.likedVideos.push_back(line);
            }
        }
    }
    
    // Lire commentaires
    std::getline(ifs, line);
    if (line.find("COMMENTS:") == 0) {
        int count = std::stoi(line.substr(9));
        for (int i = 0; i < count; i++) {
            std::getline(ifs, line);
            if (!line.empty()) {
                user.userComments.push_back(Comments::deserialize(line));
            }
        }
    }
    
    ifs.close();
    return user;
}
