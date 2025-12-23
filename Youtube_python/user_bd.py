import os
import glob
from user import User
from premium_user import PremiumUser

class UserBd:
    def __init__(self):
        self.user_list = []
        self.user_index_file = "user_index.txt"
        self.load_user_index()
    
    def add_user(self, user):
        self.user_list.append(user)
        user.save_to_file()
        self.save_user_index()
    
    def add_premium_user(self, premium_user):
        self.user_list.append(premium_user)
        premium_user.save_to_file()
        self.save_user_index()
    
    def log_out(self, email):
        for user in self.user_list:
            if user.email == email:
                self.user_list.remove(user)
                return
    
    def display_user(self):
        print("\t \t \t************************************************************************************")
        print("\t \t \t*                               LISTE DES UTILISATEURS                             *")
        print("\t \t \t************************************************************************************")
        for user in self.user_list:
            print(f"\t \tID : {user.user_id}")
            print(f"\t \tNom : {user.name_user}")
            print(f"\t \tgenre : {user.gender}")
            print(f"\t \tNumero de Telephone : {user.num_tel}")
            print(f"\t \tEmail : {user.email}")
            print(f"\t \tMot de Passe : {user.password}")
    
    def log_in(self):
        mail = input("Veuillez entrer l'adresse mail du compte :").strip()
        mot_de_passe = input("veuillez entrer le mot de passe du compte :").strip()
        
        # Chercher dans la liste en mémoire
        for user in self.user_list:
            if user.email == mail and user.password == mot_de_passe:
                print(f"\n\tCONNEXION REUSSIE. Bienvenue, {user.name_user}!")
                self.handle_premium_login(user)
                return user
        
        # Chercher dans les fichiers
        found_user = self.search_user_in_files(mail, mot_de_passe)
        if found_user:
            print(f"\n\tCONNEXION REUSSIE. Bienvenue, {found_user.name_user}!")
            self.user_list.append(found_user)
            self.handle_premium_login(found_user)
            return found_user
        
        print("Aucun compte ne correspond à cet email ou mot de passe. Veuillez vous inscrire !")
        return None
    
    def search_user_in_files(self, email, password):
        user_files = glob.glob("us[0-9][0-9][0-9].txt")
        
        for file in user_files:
            user_id = file.replace(".txt", "")
            
            # Essayer premium d'abord
            premium_user = PremiumUser.load_premium_from_file(user_id)
            if premium_user and premium_user.email == email and premium_user.password == password:
                return premium_user
            
            # Sinon utilisateur normal
            user = User.load_from_file(user_id)
            if user and user.email == email and user.password == password:
                return user
        
        return None
    
    def handle_premium_login(self, user):
        if isinstance(user, PremiumUser):
            if not user.payment_subscript:
                print("\n*** COMPTE PREMIUM : Votre souscription doit être activée. ***")
                user.subscription_payment()
            else:
                print(f"Statut Premium actuel : {user.payment_subscript}")
    
    def display_profile_user(self, email):
        user = None
        
        # Chercher en mémoire
        for u in self.user_list:
            if u.email == email:
                user = u
                break
        
        # Chercher dans les fichiers
        if not user:
            user = self.find_user_by_email(email)
        
        if not user:
            print("\n  Aucun utilisateur trouvé avec cet email.")
            return
        
        # Affichage du profil
        print("\n")
        print("\t \t╔═══════════════════════════════════════════════════════════╗")
        print("\t \t║                      PROFIL DE L'UTILISATEUR              ║")
        print("\t \t╚═══════════════════════════════════════════════════════════╝")
        print()
        
        if isinstance(user, PremiumUser):
            print("\t \t TYPE DE COMPTE      : PREMIUM")
            print("\t \t───────────────────────────────────────────────────────────────")
            print(f"\t \t ID Utilisateur      : {user.user_id}")
            print(f"\t \t Nom                 : {user.name_user}")
            print(f"\t \t  Genre               : {user.gender}")
            print(f"\t \t Téléphone           : {user.num_tel}")
            print(f"\t \t  Email               : {user.email}")
            print(f"\t \t Mot de Passe        : {'*' * len(user.password)}")
            print(f"\t \t Souscription        : {user.payment_subscript or 'Non activée'}")
            print(f"\t \t Vidéos créées       : {len(user.user_videos)}")
            print(f"\t \t Vidéos likées       : {len(user.liked_videos)}")
            print(f"\t \t Commentaires        : {len(user.user_comments)}")
            print("\t \t───────────────────────────────────────────────────────────────")
        else:
            print("\t \t TYPE DE COMPTE      : STANDARD")
            print("\t \t───────────────────────────────────────────────────────────────")
            print(f"\t \t ID Utilisateur      : {user.user_id}")
            print(f"\t \t Nom                 : {user.name_user}")
            print(f"\t \t  Genre               : {user.gender}")
            print(f"\t \t Téléphone           : {user.num_tel}")
            print(f"\t \t  Email               : {user.email}")
            print(f"\t \t Mot de Passe        : {'*' * len(user.password)}")
            print(f"\t \t Vidéos créées       : {len(user.user_videos)}")
            print(f"\t \t Vidéos likées       : {len(user.liked_videos)}")
            print(f"\t \t Commentaires        : {len(user.user_comments)}")
            print("\t \t───────────────────────────────────────────────────────────────")
        print()
    
    def find_user_by_email(self, email):
        user_files = glob.glob("us[0-9][0-9][0-9].txt")
        
        for file in user_files:
            user_id = file.replace(".txt", "")
            
            premium_user = PremiumUser.load_premium_from_file(user_id)
            if premium_user and premium_user.email == email:
                return premium_user
            
            user = User.load_from_file(user_id)
            if user and user.email == email:
                return user
        
        return None
    
    def save_user_index(self):
        try:
            with open(self.user_index_file, 'w', encoding='utf-8') as f:
                for user in self.user_list:
                    f.write(f"{user.user_id}|{user.email}\n")
        except Exception as e:
            print(f"Erreur lors de la sauvegarde de l'index: {e}")
    
    def load_user_index(self):
        if not os.path.exists(self.user_index_file):
            return
        
        try:
            with open(self.user_index_file, 'r', encoding='utf-8') as f:
                for line in f:
                    parts = line.strip().split('|')
                    user_id = parts[0]
                    
                    premium_user = PremiumUser.load_premium_from_file(user_id)
                    if premium_user:
                        self.user_list.append(premium_user)
                    else:
                        user = User.load_from_file(user_id)
                        if user:
                            self.user_list.append(user)
        except Exception as e:
            print(f"Erreur lors du chargement de l'index: {e}")

