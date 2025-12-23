#ifndef USER_H
#define USER_H

#include <string>
#include <vector>
#include "Video.h"
#include "Comments.h"

class User {
public:
    std::string userId;
    std::string nameUser;
    std::string gender;
    int numTel;
    std::string password;
    std::string email;
    std::vector<Video> userVideos;
    std::vector<std::string> likedVideos;
    std::vector<Comments> userComments;
    
    User();
    virtual ~User() {}
    
    static User createUser(const std::string& name, const std::string& gender, 
                          int numTel, const std::string& email, const std::string& password);
    
    void addVideo(const Video& video);
    void addLikedVideo(const std::string& videoId);
    void addComment(const Comments& comment);
    virtual void saveToFile();
    static User loadFromFile(const std::string& userId);
    static bool userFileExists(const std::string& userId);
    
    // Getters
    std::string getUserId() const { return userId; }
    std::string getNameUser() const { return nameUser; }
    std::string getGender() const { return gender; }
    int getNumTel() const { return numTel; }
    std::string getPassword() const { return password; }
    std::string getEmail() const { return email; }
};

#endif