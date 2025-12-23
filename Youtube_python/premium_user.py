from user import User
from id_generator import IdGenerator
import os

class PremiumUser(User):
    def __init__(self):
        super().__init__()
        self.payment_subscript = None
    
    @staticmethod
    def create_premium_user(name, gender, num_tel, email, password, option):
        premium_user = PremiumUser()
        premium_user.name_user = name
        premium_user.gender = gender
        premium_user.num_tel = num_tel
        premium_user.email = email
        premium_user.password = password
        premium_user.payment_subscript = option
        return premium_user
    
    def subscription_payment(self):
        print("\n--- OPTIONS DE SOUSCRIPTION PREMIUM ---")
        print("1....................OPTION 1 : 2,05$/ mois ...................")
        print("2....................OPTION 2 : 5,57$/ mois ...................")
        print("3....................OPTION 3 : 12,89$/ mois ..................")
        
        try:
            choix = int(input("Quelle option desirez vous souscrire ?.. "))
        except ValueError:
            print("Choix invalide. Veuillez entrer un nombre.")
            return self
        
        option_description = ""
        
        if choix == 1:
            option_description = "OPTION 1 : 2,05$/mois"
            print(f"VOUS SOUSCRIVREZ BIEN À L'{option_description}......... ")
            card_number = input("Inserez le numero de votre carte bancaire :")
            self.payment_subscript = option_description
            print(" PAIEMENT ET OPERATION REUSSIS")
        elif choix == 2:
            option_description = "OPTION 2 : 5,57$/mois"
            print(f"VOUS SOUSCRIVREZ BIEN À L'{option_description}......... ")
            card_number = input("Inserez le numero de votre carte bancaire :")
            self.payment_subscript = option_description
            print(" PAIEMENT ET OPERATION REUSSIS")
        elif choix == 3:
            option_description = "OPTION 3 : 12,89$/mois"
            print(f"VOUS SOUSCRIVREZ BIEN À L'{option_description}......... ")
            card_number = input("Inserez le numero de votre carte bancaire :")
            self.payment_subscript = option_description
            print(" PAIEMENT ET OPERATION REUSSIS")
        else:
            print("Choix d'option invalide. Souscription non effectuée.")
        
        self.save_to_file()
        return self
    
    def display_profile_premium_user(self):
        print("\t \t ***************** INFORMATION DE L'UTILISATEUR **********************\n")
        print(f"ID Utilisateur : {self.user_id}")
        print(f"Nom : {self.name_user}")
        print(f"Genre : {self.gender}")
        print(f"Numero de Telephone : {self.num_tel}")
        print(f"Email : {self.email}")
        print(f"Mot de Passe : {self.password}")
        print(f"Option de souscription: {self.payment_subscript}")
        print(f"Nombre de vidéos créées: {len(self.user_videos)}")
        print(f"Nombre de likes: {len(self.liked_videos)}")
        print(f"Nombre de commentaires: {len(self.user_comments)}")
    
    def save_to_file(self):
        try:
            with open(f"{self.user_id}.txt", 'w', encoding='utf-8') as f:
                # Type Premium
                f.write("USERTYPE:PREMIUM\n")
                f.write(f"USERINFO:{self.user_id}|{self.name_user}|{self.gender}|{self.num_tel}|{self.email}|{self.password}|{self.payment_subscript or ''}\n")
                
                # Vidéos
                f.write(f"VIDEOS:{len(self.user_videos)}\n")
                for video in self.user_videos:
                    f.write(f"{video.serialize()}\n")
                
                # Likes
                f.write(f"LIKES:{len(self.liked_videos)}\n")
                for video_id in self.liked_videos:
                    f.write(f"{video_id}\n")
                
                # Commentaires
                f.write(f"COMMENTS:{len(self.user_comments)}\n")
                for comment in self.user_comments:
                    f.write(f"{comment.serialize()}\n")
            
            print(f"Données utilisateur premium sauvegardées dans {self.user_id}.txt")
        except Exception as e:
            print(f"Erreur lors de la sauvegarde du fichier utilisateur: {e}")
    
    @staticmethod
    def load_premium_from_file(user_id):
        # Import ici pour éviter l'importation circulaire
        from video import Video
        from comments import Comments
        
        filename = f"{user_id}.txt"
        if not os.path.exists(filename):
            return None
        
        try:
            with open(filename, 'r', encoding='utf-8') as f:
                line = f.readline().strip()
                if line != "USERTYPE:PREMIUM":
                    return None
                
                line = f.readline().strip()
                if not line.startswith("USERINFO:"):
                    return None
                
                parts = line[9:].split('|')
                premium_user = PremiumUser()
                premium_user.user_id = parts[0]
                premium_user.name_user = parts[1]
                premium_user.gender = parts[2]
                premium_user.num_tel = int(parts[3])
                premium_user.email = parts[4]
                premium_user.password = parts[5]
                if len(parts) > 6:
                    premium_user.payment_subscript = parts[6]
                
                # Lire vidéos
                line = f.readline().strip()
                if line.startswith("VIDEOS:"):
                    count = int(line[7:])
                    for _ in range(count):
                        line = f.readline().strip()
                        if line:
                            premium_user.user_videos.append(Video.deserialize(line))
                
                # Lire likes
                line = f.readline().strip()
                if line.startswith("LIKES:"):
                    count = int(line[6:])
                    for _ in range(count):
                        line = f.readline().strip()
                        if line:
                            premium_user.liked_videos.append(line)
                
                # Lire commentaires
                line = f.readline().strip()
                if line.startswith("COMMENTS:"):
                    count = int(line[9:])
                    for _ in range(count):
                        line = f.readline().strip()
                        if line:
                            premium_user.user_comments.append(Comments.deserialize(line))
                
                return premium_user
        except Exception as e:
            print(f"Erreur lors du chargement du fichier utilisateur premium: {e}")
            return None
