#include "Video.h"
#include "IdGenerator.h"
#include <iostream>
#include <sstream>
#include <ctime>
#include <iomanip>

Video::Video() : resolution(0), nbreComment(0), nbreView(0), nbreLikes(0) {
    videoId = IdGenerator::generateVideoId();
    time_t now = time(0);
    tm* ltm = localtime(&now);
    std::ostringstream oss;
    oss << (1900 + ltm->tm_year) << "-" 
        << std::setw(2) << std::setfill('0') << (1 + ltm->tm_mon) << "-" 
        << std::setw(2) << std::setfill('0') << ltm->tm_mday;
    datePublication = oss.str();
}

Video Video::createVideo(const std::string& titre, const std::string& author, 
                        const std::string& desc, int res, const std::string& date) {
    Video video;
    video.titreVideo = titre;
    video.author = author;
    video.description = desc;
    video.resolution = res;
    video.datePublication = date;
    return video;
}

void Video::addComment(const Comments& c) {
    commentList.push_back(c);
    nbreComment++;
}

void Video::displayListComments() const {
    std::cout << "\t \t \t***************** LISTE DES COMMENTAIRES DE LA VIDEO **********************************\n";
    for (const auto& c : commentList) {
        std::cout << "\t \t.......COMMENTAIRE DE : " << c.userName << ".......\n\n";
        std::cout << "\t ID : " << c.commentId << "\n";
        std::cout << "\t Date de Publication : " << c.date << "\n";
        std::cout << "\t Texte : " << c.text << "\n";
        std::cout << "\t Likes : " << c.likes << "\n";
    }
}

void Video::likedComment(const std::string& id) {
    for (auto& c : commentList) {
        if (c.commentId == id) {
            c.likes++;
            std::cout << "Commentaire liké avec succès!\n";
            return;
        }
    }
    std::cout << "Commentaire introuvable : " << id << "\n";
}

void Video::unlikedComment(const std::string& id) {
    for (auto& c : commentList) {
        if (c.commentId == id) {
            c.likes--;
            return;
        }
    }
    std::cout << "Commentaire introuvable : " << id << "\n";
}

double Video::getRelevanceScore() const {
    double score = 0;
    score += (nbreLikes * 2);
    score += (nbreComment * 5);
    
    for (const auto& c : commentList) {
        score += c.likes;
    }
    
    return score;
}

std::string Video::getPertinenceStatus() const {
    double score = getRelevanceScore();
    if (score > 100) return "Très Pertinente (Incontournable)";
    if (score > 50) return "Pertinente (Populaire)";
    if (score > 10) return "Intéressante";
    return "Peu de retours (Nouvelle ou niche)";
}

std::string Video::serialize() const {
    return videoId + "|" + titreVideo + "|" + author + "|" + description + "|" + 
           std::to_string(resolution) + "|" + std::to_string(nbreLikes) + "|" + 
           std::to_string(nbreView) + "|" + std::to_string(nbreComment) + "|" + datePublication;
}

Video Video::deserialize(const std::string& data) {
    Video video;
    std::istringstream iss(data);
    std::string part;
    
    std::getline(iss, video.videoId, '|');
    std::getline(iss, video.titreVideo, '|');
    std::getline(iss, video.author, '|');
    std::getline(iss, video.description, '|');
    
    std::getline(iss, part, '|');
    video.resolution = std::stoi(part);
    
    std::getline(iss, part, '|');
    video.nbreLikes = std::stoi(part);
    
    std::getline(iss, part, '|');
    video.nbreView = std::stoi(part);
    
    std::getline(iss, part, '|');
    video.nbreComment = std::stoi(part);
    
    std::getline(iss, video.datePublication, '|');
    
    return video;
}

