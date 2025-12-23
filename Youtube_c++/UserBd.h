#ifndef USERBD_H
#define USERBD_H

#include <vector>
#include <string>
#include "User.h"
#include "PremiumUser.h"

class UserBd {
private:
    std::vector<User*> userList;
    static const std::string USER_INDEX_FILE;
    
    void saveUserIndex();
    void loadUserIndex();
    User* searchUserInFiles(const std::string& email, const std::string& password);
    void handlePremiumLogin(User* user);
    User* findUserByEmail(const std::string& email);
    
public:
    UserBd();
    ~UserBd();
    
    void addUser(User* user);
    void addPremiumUser(PremiumUser* premiumUser);
    void logOut(const std::string& email);
    void displayUser() const;
    User* logIn();
    void displayProfileUser(const std::string& email);
};

#endif
