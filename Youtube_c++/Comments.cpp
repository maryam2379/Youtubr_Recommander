#include "Comments.h"
#include "IdGenerator.h"
#include <iostream>
#include <sstream>
#include <ctime>
#include <iomanip>

Comments::Comments() : likes(0) {
    commentId = IdGenerator::generateCommentId();
    time_t now = time(0);
    tm* ltm = localtime(&now);
    std::ostringstream oss;
    oss << (1900 + ltm->tm_year) << "-" 
        << std::setw(2) << std::setfill('0') << (1 + ltm->tm_mon) << "-" 
        << std::setw(2) << std::setfill('0') << ltm->tm_mday;
    date = oss.str();
}

Comments Comments::createComment(const std::string& name, const std::string& commentDate, const std::string& text) {
    Comments comment;
    comment.date = commentDate;
    comment.userName = name;
    comment.text = text;
    return comment;
}

void Comments::displayComment() const {
    std::cout << "\t \t Commentaire : " << userName << "\n";
    std::cout << "Id du commentaire: " << commentId << "\n";
    std::cout << "Date de publication : " << date << "\n";
    std::cout << "Texte: " << text << "\n";
    std::cout << "Likes : " << likes << "\n";
}

std::string Comments::serialize() const {
    return commentId + "|" + userName + "|" + text + "|" + std::to_string(likes) + "|" + date;
}

Comments Comments::deserialize(const std::string& data) {
    Comments comment;
    std::istringstream iss(data);
    std::string part;
    
    std::getline(iss, comment.commentId, '|');
    std::getline(iss, comment.userName, '|');
    std::getline(iss, comment.text, '|');
    std::getline(iss, part, '|');
    comment.likes = std::stoi(part);
    std::getline(iss, comment.date, '|');
    
    return comment;
}
