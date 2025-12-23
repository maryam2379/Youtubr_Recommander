#ifndef VIDEO_H
#define VIDEO_H

#include <string>
#include <vector>
#include "Comments.h"

class Video {
public:
    std::string videoId;
    std::string titreVideo;
    std::string author;
    std::string description;
    int resolution;
    std::string datePublication;
    int nbreComment;
    int nbreView;
    int nbreLikes;
    std::vector<Comments> commentList;
    
    Video();
    static Video createVideo(const std::string& titre, const std::string& author, 
                            const std::string& desc, int res, const std::string& date);
    
    void addComment(const Comments& c);
    void displayListComments() const;
    void likedComment(const std::string& id);
    void unlikedComment(const std::string& id);
    double getRelevanceScore() const;
    std::string getPertinenceStatus() const;
    std::string serialize() const;
    static Video deserialize(const std::string& data);
    
    // Getters
    std::string getVideoId() const { return videoId; }
    std::string getTitreVideo() const { return titreVideo; }
    std::string getAuthor() const { return author; }
    std::string getDescription() const { return description; }
    int getResolution() const { return resolution; }
    std::string getDatePublication() const { return datePublication; }
    int getNbreComment() const { return nbreComment; }
    int getNbreView() const { return nbreView; }
    int getNbreLikes() const { return nbreLikes; }
    
    // Setters
    void setNbreLikes(int likes) { nbreLikes = likes; }
    void setNbreView(int views) { nbreView = views; }
};

#endif