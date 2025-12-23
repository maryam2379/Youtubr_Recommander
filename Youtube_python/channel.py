from datetime import date
from id_generator import IdGenerator
from video import Video

class Channel:
    def __init__(self):
        self.channel_id = IdGenerator.generate_channel_id()
        self.name_channel = ""
        self.host_name = ""
        self.followers = 0
        self.videos_number = 0
        self.date_creation = date.today()
        self.nbre_video = []
    
    @staticmethod
    def create_channel(name, host, creation_date):
        channel = Channel()
        channel.name_channel = name
        channel.host_name = host
        channel.date_creation = creation_date
        return channel
    
    def display_list_video(self):
        if not self.nbre_video:
            print("Aucune vidéo dans cette chaîne.")
            return
        
        for v in self.nbre_video:
            print("\n----------------------------------------")
            print(f"ID Vidéo: {v.video_id}")
            print(f"Titre de la Video: {v.titre_video}")
            print(f"Author: {v.author}")
            print(f"Description: {v.description}")
            print(f"Resolution: {v.resolution}K")
            print(f"Date: {v.date_publication}")
            print(f"Nbre comment: {v.nbre_comment}")
            print(f"Nbre vue: {v.nbre_view}")
            print(f"Likes: {v.nbre_likes}")
            print("----------------------------------------")
