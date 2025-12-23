#ifndef CHANNEL_H
#define CHANNEL_H

#include <string>
#include <vector>
#include "Video.h"

class Channel {
public:
    std::string channelId;
    std::string nameChannel;
    std::string hostName;
    int followers;
    int videosNumber;
    std::string dateCreation;
    std::vector<Video> nbreVideo;
    
    Channel();
    static Channel createChannel(const std::string& name, const std::string& host, 
                                const std::string& date);
    void displayListVideo() const;
    
    // Getters
    std::string getChannelId() const { return channelId; }
    std::string getNameChannel() const { return nameChannel; }
    std::string getHostName() const { return hostName; }
    int getFollowers() const { return followers; }
    int getVideosNumber() const { return videosNumber; }
    std::string getDateCreation() const { return dateCreation; }
    
    // Setters
    void setFollowers(int f) { followers = f; }
};

#endif
