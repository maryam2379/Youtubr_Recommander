#ifndef IDGENERATOR_H
#define IDGENERATOR_H

#include <string>
#include <fstream>

class IdGenerator {
private:
    static int userCounter;
    static int videoCounter;
    static int commentCounter;
    static int channelCounter;
    static const std::string COUNTER_FILE;
    
    static void saveCounters();
    static void loadCounters();
    
public:
    static void initialize();
    static std::string generateUserId();
    static std::string generateVideoId();
    static std::string generateCommentId();
    static std::string generateChannelId();
};

#endif
