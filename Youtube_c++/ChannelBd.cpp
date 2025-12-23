#include "ChannelBd.h"
#include <iostream>

ChannelBd::ChannelBd() : Channel() {}

void ChannelBd::addChannel(const Channel& c) {
    channelList.push_back(c);
    std::cout << "Chaîne créée avec l'ID: " << c.channelId << "\n";
}

void ChannelBd::connectionChannel() {
    std::string name;
    std::cout << "Entrez le nom de votre chaine :\n";
    std::getline(std::cin, name);
    
    for (const auto& c : channelList) {
        if (c.hostName == name) {
            std::cout << "CONNEXION REUSSIE. Bienvenu sur " << c.nameChannel << "\n";
            return;
        }
    }
    std::cout << "Impossible de se connecter a la chaine. veuillez entrer le bon nom ou creer une nouvelle chaine\n";
}

void ChannelBd::searchChannel(const std::string& nom) {
    for (const auto& c : channelList) {
        if (c.nameChannel == nom) {
            std::cout << "\n========================================\n";
            std::cout << "\t \tID Chaîne: " << c.channelId << "\n";
            std::cout << "\t \tNom de la chaine: " << c.nameChannel << "\n";
            std::cout << "\t \tPropietaire de la chaine: " << c.hostName << "\n";
            std::cout << "\t \tFollowers: " << c.followers << "\n";
            std::cout << "\t \tNombre de Video: " << c.videosNumber << "\n";
            std::cout << "\t \tDate de creation: " << c.dateCreation << "\n";
            std::cout << "========================================\n\n";
            displayListVideo();
            return;
        }
    }
    std::cout << "Chaîne introuvable: " << nom << "\n";
}

void ChannelBd::followChannel(const std::string& titre) {
    for (auto& c : channelList) {
        if (c.nameChannel == titre) {
            c.followers++;
            std::cout << "La chaine '" << titre << "' a été suivi ! Nouveau nombre de followers : " << c.followers << "\n";
            return;
        }
    }
    std::cout << "Chaine introuvable : " << titre << "\n";
}

void ChannelBd::unfollowChannel(const std::string& titre) {
    for (auto& c : channelList) {
        if (c.nameChannel == titre) {
            c.followers--;
            std::cout << "La chaine '" << titre << "' a arrete d'etre suivi par un follower ! Nouveau nombre de followers : " << c.followers << "\n";
            return;
        }
    }
    std::cout << "Chaine introuvable : " << titre << "\n";
}

void ChannelBd::postedVideo(const Video& v) {
    nbreVideo.push_back(v);
    videosNumber++;
    std::cout << "Vidéo postée avec succès! ID: " << v.videoId << "\n";
}

void ChannelBd::deleteVideo(const std::string& id) {
    for (auto it = nbreVideo.begin(); it != nbreVideo.end(); ++it) {
        if (it->videoId == id) {
            nbreVideo.erase(it);
            videosNumber--;
            std::cout << "Vidéo " << id << " supprimée avec succès.\n";
            return;
        }
    }
    std::cout << "Vidéo introuvable: " << id << "\n";
}

void ChannelBd::displayChannel() const {
    if (channelList.empty()) {
        std::cout << "Aucune chaîne disponible.\n";
        return;
    }
    
    for (const auto& c : channelList) {
        std::cout << "\n========================================\n";
        std::cout << "\t \tID Chaîne: " << c.channelId << "\n";
        std::cout << "\t \tNom de la chaine: " << c.nameChannel << "\n";
        std::cout << "\t \tPropietaire de la chaine: " << c.hostName << "\n";
        std::cout << "\t \tFollowers: " << c.followers << "\n";
        std::cout << "\t \tNombre de Video: " << c.videosNumber << "\n";
        std::cout << "\t \tDate de creation: " << c.dateCreation << "\n";
        std::cout << "========================================\n\n";
    }
}
