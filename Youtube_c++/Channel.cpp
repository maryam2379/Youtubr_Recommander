#include "Channel.h"
#include "IdGenerator.h"
#include <iostream>
#include <ctime>
#include <sstream>
#include <iomanip>

Channel::Channel() : followers(0), videosNumber(0) {
    channelId = IdGenerator::generateChannelId();
    time_t now = time(0);
    tm* ltm = localtime(&now);
    std::ostringstream oss;
    oss << (1900 + ltm->tm_year) << "-" 
        << std::setw(2) << std::setfill('0') << (1 + ltm->tm_mon) << "-" 
        << std::setw(2) << std::setfill('0') << ltm->tm_mday;
    dateCreation = oss.str();
}

Channel Channel::createChannel(const std::string& name, const std::string& host, 
                              const std::string& date) {
    Channel channel;
    channel.nameChannel = name;
    channel.hostName = host;
    channel.dateCreation = date;
    return channel;
}

void Channel::displayListVideo() const {
    if (nbreVideo.empty()) {
        std::cout << "Aucune vidéo dans cette chaîne.\n";
        return;
    }
    
    for (const auto& v : nbreVideo) {
        std::cout << "\n----------------------------------------\n";
        std::cout << "ID Vidéo: " << v.videoId << "\n";
        std::cout << "Titre de la Video: " << v.titreVideo << "\n";
        std::cout << "Author: " << v.author << "\n";
        std::cout << "Description: " << v.description << "\n";
        std::cout << "Resolution: " << v.resolution << "K\n";
        std::cout << "Date: " << v.datePublication << "\n";
        std::cout << "Nbre comment: " << v.nbreComment << "\n";
        std::cout << "Nbre vue: " << v.nbreView << "\n";
        std::cout << "Likes: " << v.nbreLikes << "\n";
        std::cout << "----------------------------------------\n";
    }
}
