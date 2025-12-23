from datetime import date
from id_generator import IdGenerator

class Comments:
    def __init__(self):
        self.comment_id = IdGenerator.generate_comment_id()
        self.date = date.today()
        self.user_name = ""
        self.text = ""
        self.likes = 0

    @staticmethod
    def create_comment(name, date_pub, text):
        comment = Comments()
        comment.date = date_pub
        comment.user_name = name
        comment.text = text
        return comment

    def display_comment(self):
        print(f"\t \t Commentaire : {self.user_name}\n")
        print(f"Id: {self.comment_id}\nDate: {self.date}\nTexte: {self.text}\nLikes: {self.likes}")

    def serialize(self):
        return f"{self.comment_id}|{self.user_name}|{self.text}|{self.likes}|{self.date.isoformat()}"

    @staticmethod
    def deserialize(data):
        parts = data.split("|")
        comment = Comments()
        comment.comment_id = parts[0]
        comment.user_name = parts[1]
        comment.text = parts[2]
        comment.likes = int(parts[3])
        comment.date = date.fromisoformat(parts[4])
        return comment
