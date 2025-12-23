#include "User.h"
#include "IdGenerator.h"
#include <fstream>
#include <iostream>
#include <sstream>
#include <algorithm>

User::User() : numTel(0) {
    userId = IdGenerator::generateUserId();
}

User User::createUser(const std::string& name, const std::string& gender, 
                     int numTel, const std::string& email, const std::string& password) {
    User user;
    user.nameUser = name;
    user.gender = gender;
    user.numTel = numTel;
    user.email = email;
    user.password = password;
    return user;
}

void User::addVideo(const Video& video) {
    userVideos.push_back(video);
    saveToFile();
}

void User::addLikedVideo(const std::string& videoId) {
    if (std::find(likedVideos.begin(), likedVideos.end(), videoId) == likedVideos.end()) {
        likedVideos.push_back(videoId);
        saveToFile();
    }
}

void User::addComment(const Comments& comment) {
    userComments.push_back(comment);
    saveToFile();
}

void User::saveToFile() {
    std::ofstream ofs(userId + ".txt");
    if (ofs.is_open()) {
        ofs << "USERINFO:" << userId << "|" << nameUser << "|" << gender << "|" 
            << numTel << "|" << email << "|" << password << "\n";
        
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
        std::cout << "Données utilisateur sauvegardées dans " << userId << ".txt\n";
    }
}

User User::loadFromFile(const std::string& userId) {
    User user;
    std::ifstream ifs(userId + ".txt");
    
    if (!ifs.is_open()) {
        user.userId = "";
        return user;
    }
    
    std::string line;
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
    
    // Lire les vidéos
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
    
    // Lire les likes
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
    
    // Lire les commentaires
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

bool User::userFileExists(const std::string& userId) {
    std::ifstream ifs(userId + ".txt");
    return ifs.good();
}

