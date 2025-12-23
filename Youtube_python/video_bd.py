from video import Video

class VideoBd:
    def __init__(self):
        self.video_list = []
    
    def add_video(self, video):
        self.video_list.append(video)
    
    def research_video(self, nom):
        for v in self.video_list:
            if v.titre_video == nom:
                print(f"\t \tID Vidéo : {v.video_id}")
                print(f"\t \tTitre : {v.titre_video}")
                print(f"\t \tAutheur : {v.author}")
                print(f"\t \tDescription : {v.description}")
                print(f"\t \tResolution : {v.resolution}K")
                print(f"\t \tDate de Publication : {v.date_publication}")
                print("\t \t----------------------------------------")
                print("\t \tSTATISTIQUES & PERTINENCE\n")
                print(f"\t \tNombre de Vues : {v.nbre_view}")
                print(f"\t \tNombre de Commentaires : {v.nbre_comment}")
                print(f"\t \tNombre de Likes : {v.nbre_likes}")
                print(f"\t \tScore de Pertinence : {v.get_relevance_score()}")
                print(f"\t \tStatut : {v.get_pertinence_status()}")
                print("\t \t----------------------------------------\n\n")
                v.display_list_comments()
                return
        print("Aucune video disponible pour ce theme.....")
    
    def liked_video(self, titre):
        for v in self.video_list:
            if v.titre_video == titre:
                v.nbre_likes += 1
                print(f"La vidéo '{titre}' a été likée ! Nouveau nombre de likes : {v.nbre_likes}")
                return
        print(f"Vidéo introuvable : {titre}")
    
    def get_video_by_title(self, titre):
        for v in self.video_list:
            if v.titre_video == titre:
                return v
        return None
    
    def get_video_by_id(self, video_id):
        for v in self.video_list:
            if v.video_id == video_id:
                return v
        return None
    
    def display_video(self):
        if not self.video_list:
            print("Aucune vidéo disponible.")
            return
        
        for v in self.video_list:
            print("\n========================================")
            print(f"ID : {v.video_id}")
            print(f"Titre de la Video : {v.titre_video}")
            print(f"Author: {v.author}")
            print(f"Description: {v.description}")
            print(f"Resolution: {v.resolution}K")
            print(f"Date: {v.date_publication}")
            print(f"Nbre comment: {v.nbre_comment}")
            print(f"Nbre vue: {v.nbre_view}")
            print(f"Likes: {v.nbre_likes}")
            print("========================================\n")
    
    def delete_video(self, nom):
        for v in self.video_list:
            if v.titre_video == nom:
                self.video_list.remove(v)
                print(f"Vidéo '{nom}' supprimée avec succès.")
                return
        print(f"Vidéo introuvable : {nom}")