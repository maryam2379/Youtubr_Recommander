from channel import Channel
from video import Video

class ChannelBd(Channel):
    def __init__(self):
        super().__init__()
        self.channel_list = []
    
    def add_channel(self, channel):
        self.channel_list.append(channel)
        print(f"Chaîne créée avec l'ID: {channel.channel_id}")
    
    def connection_channel(self):
        name = input("Entrez le nom de votre chaine :")
        for channel in self.channel_list:
            if channel.host_name == name:
                print(f"CONNEXION REUSSIE. Bienvenu sur {channel.name_channel}")
                return
        print("Impossible de se connecter a la chaine. veuillez entrer le bon nom ou creer une nouvelle chaine")
    
    def search_channel(self, nom):
        for channel in self.channel_list:
            if channel.name_channel == nom:
                print("\n========================================")
                print(f"\t \tID Chaîne: {channel.channel_id}")
                print(f"\t \tNom de la chaine: {channel.name_channel}")
                print(f"\t \tPropietaire de la chaine: {channel.host_name}")
                print(f"\t \tFollowers: {channel.followers}")
                print(f"\t \tNombre de Video: {channel.videos_number}")
                print(f"\t \tDate de creation: {channel.date_creation}")
                print("========================================\n")
                self.display_list_video()
                return
        print(f"Chaîne introuvable: {nom}")
    
    def follow_channel(self, titre):
        for channel in self.channel_list:
            if channel.name_channel == titre:
                channel.followers += 1
                print(f"La chaine '{titre}' a été suivi ! Nouveau nombre de followers : {channel.followers}")
                return
        print(f"Chaine introuvable : {titre}")
    
    def unfollow_channel(self, titre):
        for channel in self.channel_list:
            if channel.name_channel == titre:
                channel.followers -= 1
                print(f"La chaine '{titre}' a arrete d'etre suivi par un follower ! Nouveau nombre de followers : {channel.followers}")
                return
        print(f"Chaine introuvable : {titre}")
    
    def posted_video(self, video):
        self.nbre_video.append(video)
        self.videos_number += 1
        print(f"Vidéo postée avec succès! ID: {video.video_id}")
    
    def delete_video(self, video_id):
        for video in self.nbre_video:
            if video.video_id == video_id:
                self.nbre_video.remove(video)
                self.videos_number -= 1
                print(f"Vidéo {video_id} supprimée avec succès.")
                return
        print(f"Vidéo introuvable: {video_id}")
    
    def display_channel(self):
        if not self.channel_list:
            print("Aucune chaîne disponible.")
            return
        
        for channel in self.channel_list:
            print("\n========================================")
            print(f"\t \tID Chaîne: {channel.channel_id}")
            print(f"\t \tNom de la chaine: {channel.name_channel}")
            print(f"\t \tPropietaire de la chaine: {channel.host_name}")
            print(f"\t \tFollowers: {channel.followers}")
            print(f"\t \tNombre de Video: {channel.videos_number}")
            print(f"\t \tDate de creation: {channel.date_creation}")
            print("========================================\n")


