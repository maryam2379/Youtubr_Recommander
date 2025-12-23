#ifndef CHANNELBD_H
#define CHANNELBD_H

#include <vector>
#include "Channel.h"

class ChannelBd : public Channel {
private:
    std::vector<Channel> channelList;
    
public:
    ChannelBd();
    void addChannel(const Channel& c);
    void connectionChannel();
    void searchChannel(const std::string& nom);
    void followChannel(const std::string& titre);
    void unfollowChannel(const std::string& titre);
    void postedVideo(const Video& v);
    void deleteVideo(const std::string& id);
    void displayChannel() const;
};

#endif
