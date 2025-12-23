from datetime import date
from id_generator import IdGenerator
from comments import Comments

class Video:
    def __init__(self):
        self.video_id = IdGenerator.generate_video_id()
        self.titre_video = ""
        self.author = ""
        self.description = ""
        self.resolution = 0
        self.date_publication = date.today()
        self.nbre_comment = 0
        self.nbre_view = 0
        self.nbre_likes = 0
        self.comment_list = []
    
    @staticmethod
    def create_video(titre, author, description, resolution, video_date):
        video = Video()
        video.titre_video = titre
        video.author = author
        video.description = description
        video.resolution = resolution
        video.date_publication = video_date
        return video
    
    def add_comment(self, comment):
        self.comment_list.append(comment)
        self.nbre_comment += 1
    
    def display_list_comments(self):
        print("\t \t \t***************** LISTE DES COMMENTAIRES DE LA VIDEO **********************************")
        for c in self.comment_list:
            print(f"\t \t.......COMMENTAIRE DE : {c.user_name}.......\n")
            print(f"\t ID : {c.comment_id}")
            print(f"\t Date de Publication : {c.date}")
            print(f"\t Texte : {c.text}")
            print(f"\t Likes : {c.likes}")
    
    def liked_comment(self, comment_id):
        for c in self.comment_list:
            if c.comment_id == comment_id:
                c.likes += 1
                print("Commentaire liké avec succès!")
                return
        print(f"Commentaire introuvable : {comment_id}")
    
    def unliked_comment(self, comment_id):
        for c in self.comment_list:
            if c.comment_id == comment_id:
                c.likes -= 1
                return
        print(f"Commentaire introuvable : {comment_id}")
    
    def get_relevance_score(self):
        score = 0
        score += (self.nbre_likes * 2)
        score += (self.nbre_comment * 5)
        
        for c in self.comment_list:
            score += c.likes
        
        return score
    
    def get_pertinence_status(self):
        score = self.get_relevance_score()
        if score > 100:
            return "Très Pertinente (Incontournable)"
        elif score > 50:
            return "Pertinente (Populaire)"
        elif score > 10:
            return "Intéressante"
        return "Peu de retours (Nouvelle ou niche)"
    
    def serialize(self):
        return f"{self.video_id}|{self.titre_video}|{self.author}|{self.description}|{self.resolution}|{self.nbre_likes}|{self.nbre_view}|{self.nbre_comment}|{self.date_publication.isoformat()}"
    
    @staticmethod
    def deserialize(data):
        parts = data.split('|')
        video = Video()
        video.video_id = parts[0]
        video.titre_video = parts[1]
        video.author = parts[2]
        video.description = parts[3]
        video.resolution = int(parts[4])
        video.nbre_likes = int(parts[5])
        video.nbre_view = int(parts[6])
        video.nbre_comment = int(parts[7])
        video.date_publication = date.fromisoformat(parts[8])
        return video