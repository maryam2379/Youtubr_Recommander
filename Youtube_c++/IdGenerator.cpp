#include "IdGenerator.h"
#include <sstream>
#include <iomanip>

int IdGenerator::userCounter = 1;
int IdGenerator::videoCounter = 1;
int IdGenerator::commentCounter = 1;
int IdGenerator::channelCounter = 1;
const std::string IdGenerator::COUNTER_FILE = "counters.dat";

void IdGenerator::initialize() {
    loadCounters();
}

void IdGenerator::saveCounters() {
    std::ofstream ofs(COUNTER_FILE, std::ios::binary);
    if (ofs.is_open()) {
        ofs.write(reinterpret_cast<char*>(&userCounter), sizeof(int));
        ofs.write(reinterpret_cast<char*>(&videoCounter), sizeof(int));
        ofs.write(reinterpret_cast<char*>(&commentCounter), sizeof(int));
        ofs.write(reinterpret_cast<char*>(&channelCounter), sizeof(int));
        ofs.close();
    }
}

void IdGenerator::loadCounters() {
    std::ifstream ifs(COUNTER_FILE, std::ios::binary);
    if (ifs.is_open()) {
        ifs.read(reinterpret_cast<char*>(&userCounter), sizeof(int));
        ifs.read(reinterpret_cast<char*>(&videoCounter), sizeof(int));
        ifs.read(reinterpret_cast<char*>(&commentCounter), sizeof(int));
        ifs.read(reinterpret_cast<char*>(&channelCounter), sizeof(int));
        ifs.close();
    }
}

std::string IdGenerator::generateUserId() {
    std::ostringstream oss;
    oss << "us" << std::setw(3) << std::setfill('0') << userCounter++;
    saveCounters();
    return oss.str();
}

std::string IdGenerator::generateVideoId() {
    std::ostringstream oss;
    oss << "VID" << std::setw(3) << std::setfill('0') << videoCounter++;
    saveCounters();
    return oss.str();
}

std::string IdGenerator::generateCommentId() {
    std::ostringstream oss;
    oss << "COM" << std::setw(3) << std::setfill('0') << commentCounter++;
    saveCounters();
    return oss.str();
}

std::string IdGenerator::generateChannelId() {
    std::ostringstream oss;
    oss << "CH" << std::setw(3) << std::setfill('0') << channelCounter++;
    saveCounters();
    return oss.str();
}

