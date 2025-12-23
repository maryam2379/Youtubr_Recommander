import os
from id_generator import IdGenerator

class User:
    def __init__(self):
        self.user_id = IdGenerator.generate_user_id()
        self.name_user = ""
        self.gender = ""
        self.num_tel = 0
        self.password = ""
        self.email = ""
        self.user_videos = []
        self.liked_videos = []
        self.user_comments = []
    
    @staticmethod
    def create_user(name, gender, num_tel, email, password):
        user = User()
        user.name_user = name
        user.gender = gender
        user.num_tel = num_tel
        user.email = email
        user.password = password
        return user
    
    def add_video(self, video):
        self.user_videos.append(video)
        self.save_to_file()
    
    def add_liked_video(self, video_id):
        if video_id not in self.liked_videos:
            self.liked_videos.append(video_id)
            self.save_to_file()
    
    def add_comment(self, comment):
        self.user_comments.append(comment)
        self.save_to_file()
    
    def save_to_file(self):
        try:
            with open(f"{self.user_id}.txt", 'w', encoding='utf-8') as f:
                # Informations de base
                f.write(f"USERINFO:{self.user_id}|{self.name_user}|{self.gender}|{self.num_tel}|{self.email}|{self.password}\n")
                
                # Vidéos créées
                f.write(f"VIDEOS:{len(self.user_videos)}\n")
                for video in self.user_videos:
                    f.write(f"{video.serialize()}\n")
                
                # Vidéos likées
                f.write(f"LIKES:{len(self.liked_videos)}\n")
                for video_id in self.liked_videos:
                    f.write(f"{video_id}\n")
                
                # Commentaires
                f.write(f"COMMENTS:{len(self.user_comments)}\n")
                for comment in self.user_comments:
                    f.write(f"{comment.serialize()}\n")
            
            print(f"Données utilisateur sauvegardées dans {self.user_id}.txt")
        except Exception as e:
            print(f"Erreur lors de la sauvegarde du fichier utilisateur: {e}")
    
    @staticmethod
    def load_from_file(user_id):
        # Import ici pour éviter l'importation circulaire
        from video import Video
        from comments import Comments
        
        filename = f"{user_id}.txt"
        if not os.path.exists(filename):
            return None
        
        try:
            with open(filename, 'r', encoding='utf-8') as f:
                line = f.readline().strip()
                if not line.startswith("USERINFO:"):
                    return None
                
                parts = line[9:].split('|')
                if len(parts) < 6:
                    return None
                
                user = User()
                user.user_id = parts[0].strip()
                user.name_user = parts[1].strip()
                user.gender = parts[2].strip()
                user.num_tel = int(parts[3].strip())
                user.email = parts[4].strip()
                user.password = parts[5].strip()
                
                # Lire les vidéos
                line = f.readline().strip()
                if line.startswith("VIDEOS:"):
                    count = int(line[7:])
                    for _ in range(count):
                        line = f.readline().strip()
                        if line:
                            user.user_videos.append(Video.deserialize(line))
                
                # Lire les likes
                line = f.readline().strip()
                if line.startswith("LIKES:"):
                    count = int(line[6:])
                    for _ in range(count):
                        line = f.readline().strip()
                        if line:
                            user.liked_videos.append(line)
                
                # Lire les commentaires
                line = f.readline().strip()
                if line.startswith("COMMENTS:"):
                    count = int(line[9:])
                    for _ in range(count):
                        line = f.readline().strip()
                        if line:
                            user.user_comments.append(Comments.deserialize(line))
                
                return user
        except Exception as e:
            print(f"Erreur lors du chargement du fichier utilisateur: {e}")
            return None
    
    @staticmethod
    def user_file_exists(user_id):
        return os.path.exists(f"{user_id}.txt")