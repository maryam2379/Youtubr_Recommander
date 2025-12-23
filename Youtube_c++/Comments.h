#ifndef COMMENTS_H
#define COMMENTS_H

#include <string>
#include <ctime>

class Comments {
public:
    std::string commentId;
    std::string date;
    std::string userName;
    std::string text;
    int likes;
    
    Comments();
    static Comments createComment(const std::string& name, const std::string& commentDate, const std::string& text);
    void displayComment() const;
    std::string serialize() const;
    static Comments deserialize(const std::string& data);
    
    std::string getCommentId() const { return commentId; }
    std::string getDate() const { return date; }
    std::string getUserName() const { return userName; }
    std::string getText() const { return text; }
    int getLikes() const { return likes; }
    void setLikes(int l) { likes = l; }
};

#endif
