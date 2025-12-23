#ifndef VIDEOBD_H
#define VIDEOBD_H

#include <vector>
#include "Video.h"

class VideoBd {
private:
    std::vector<Video> videoList;
    
public:
    VideoBd();
    void addVideo(const Video& v);
    void researchVideo(const std::string& nom);
    void likedVideo(const std::string& titre);
    Video* getVideoByTitle(const std::string& titre);
    Video* getVideoById(const std::string& videoId);
    void displayVideo() const;
    void deleteVideo(const std::string& nom);
};

#endif
