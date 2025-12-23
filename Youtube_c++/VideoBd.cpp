#include "VideoBd.h"
#include <iostream>

VideoBd::VideoBd() {}

void VideoBd::addVideo(const Video& v) {
    videoList.push_back(v);
}

void VideoBd::researchVideo(const std::string& nom) {
    for (const auto& v : videoList) {
        if (v.titreVideo == nom) {
            std::cout << "\t \tID Vidéo : " << v.videoId << "\n";
            std::cout << "\t \tTitre : " << v.titreVideo << "\n";
            std::cout << "\t \tAutheur : " << v.author << "\n";
            std::cout << "\t \tDescription : " << v.description << "\n";
            std::cout << "\t \tResolution : " << v.resolution << "K\n";
            std::cout << "\t \tDate de Publication : " << v.datePublication << "\n";
            std::cout << "\t \t----------------------------------------\n";
            std::cout << "\t \tSTATISTIQUES & PERTINENCE\n\n";
            std::cout << "\t \tNombre de Vues : " << v.nbreView << "\n";
            std::cout << "\t \tNombre de Commentaires : " << v.nbreComment << "\n";
            std::cout << "\t \tNombre de Likes : " << v.nbreLikes << "\n";
            std::cout << "\t \tScore de Pertinence : " << v.getRelevanceScore() << "\n";
            std::cout << "\t \tStatut : " << v.getPertinenceStatus() << "\n";
            std::cout << "\t \t----------------------------------------\n\n";
            v.displayListComments();
            return;
        }
    }
    std::cout << "Aucune video disponible pour ce theme.....\n";
}

void VideoBd::likedVideo(const std::string& titre) {
    for (auto& v : videoList) {
        if (v.titreVideo == titre) {
            v.nbreLikes++;
            std::cout << "La vidéo '" << titre << "' a été likée ! Nouveau nombre de likes : " << v.nbreLikes << "\n";
            return;
        }
    }
    std::cout << "Vidéo introuvable : " << titre << "\n";
}

Video* VideoBd::getVideoByTitle(const std::string& titre) {
    for (auto& v : videoList) {
        if (v.titreVideo == titre) {
            return &v;
        }
    }
    return nullptr;
}

Video* VideoBd::getVideoById(const std::string& videoId) {
    for (auto& v : videoList) {
        if (v.videoId == videoId) {
            return &v;
        }
    }
    return nullptr;
}

void VideoBd::displayVideo() const {
    if (videoList.empty()) {
        std::cout << "Aucune vidéo disponible.\n";
        return;
    }
    
    for (const auto& v : videoList) {
        std::cout << "\n========================================\n";
        std::cout << "ID : " << v.videoId << "\n";
        std::cout << "Titre de la Video : " << v.titreVideo << "\n";
        std::cout << "Author: " << v.author << "\n";
        std::cout << "Description: " << v.description << "\n";
        std::cout << "Resolution: " << v.resolution << "K\n";
        std::cout << "Date: " << v.datePublication << "\n";
        std::cout << "Nbre comment: " << v.nbreComment << "\n";
        std::cout << "Nbre vue: " << v.nbreView << "\n";
        std::cout << "Likes: " << v.nbreLikes << "\n";
        std::cout << "========================================\n";
    }
}

void VideoBd::deleteVideo(const std::string& nom) {
    for (auto it = videoList.begin(); it != videoList.end(); ++it) {
        if (it->titreVideo == nom) {
            videoList.erase(it);
            std::cout << "Vidéo '" << nom << "' supprimée avec succès.\n";
            return;
        }
    }
    std::cout << "Vidéo introuvable : " << nom << "\n";
}