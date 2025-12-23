import os
from datetime import date
from user import User
from premium_user import PremiumUser
from user_bd import UserBd
from video import Video
from video_bd import VideoBd
from channel import Channel
from channel_bd import ChannelBd
from comments import Comments

def menu():
    print("\t \t \t @#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$")
    print("\t \t \t @#$                                                                                 @#$")
    print("\t \t \t @#$                         ~~~ YOUTUBE RECOMMANDER ~~~                             @#$")
    print("\t \t \t @#$                                                                                 @#$")
    print("\t \t \t @#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$@#$")
    print("\n")
    print("\t......................... BIENVENUE SUR LE YOUTUBE RECOMMANDER.!!!!!!!!! ...................")
    print("\n")
    print("\t \t \t************************************************************************************")
    print("\t \t \t*                      MENU DU YOUTUBE RECOMMANDER                                 *")
    print("\t \t \t************************************************************************************")
    print("\n")
    print("\t1............................................  S'inscrire .....................................")
    print("\t2..........................................  Se Connecter .....................................")
    print("\t3........................................  Se deconnecter .....................................")
    print("\t0.............................................. QUITTER  ......................................")

def menu_inscription():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("\n\n\n\n")
    print("1................................ Poster une video...................................")
    print("2.............................   Rechercher une video ...............................")
    print("3................................. Supprimer une video ..............................")
    print("4................................ Creer une chaine ..................................")
    print("5............................. Rechercher une chaine.................................")
    print("6............................... suivre une chaine ..................................")
    print("7............................. Arreter de suivre une chaine .........................")
    print("8.......................... Poster une video dans la chaine .........................")
    print("9.......................... Supprimer une video de la chaine ........................")
    print("10.................................. Retour au menu principal .......................")
    print("11.................................. Afficher mon profil ............................")
    print("0............................. Fermer le Youtube Recommander ........................")

def menu_payment():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("\n\n\n\n")
    print("1........................ S'incrire comme un utilisateur............................")
    print("2................... S'incrire comme un utilisateur premium ........................")
    print("0................................. Retour  ...........................................")

def main():
    video = Video()
    user_bd = UserBd()
    video_bd = VideoBd()
    channel_bd = ChannelBd()
    current_user = None
    
    while True:
        menu()
        try:
            choix = int(input("veuiller entrer une reponse : "))
        except ValueError:
            print("Choix invalide.")
            continue
        
        if choix == 1:  # S'inscrire
            menu_payment()
            try:
                inscrip_choix = int(input("veuiller entrer une reponse : "))
            except ValueError:
                print("Choix invalide.")
                continue
            
            if inscrip_choix == 1:  # Utilisateur standard
                name = input("Entrer votre Nom complet: ").strip()
                gender = input("Entrer votre genre : ")
                num_tel = int(input("Entrer votre numero de telephone : "))
                email = input("Entrer votre adresse mail : ").strip()
                password = input("Entrer votre mot de passe : ").strip()
                
                new_user = User.create_user(name, gender, num_tel, email, password)
                user_bd.add_user(new_user)
                print(f"Inscription réussie! Votre ID est: {new_user.user_id}")
                current_user = new_user
            
            elif inscrip_choix == 2:  # Utilisateur premium
                name = input("Entrer votre Nom complet: ").strip()
                gender = input("Entrer votre genre : ")
                num_tel = int(input("Entrer votre numero de telephone : "))
                email = input("Entrer votre adresse mail : ").strip()
                password = input("Entrer votre mot de passe : ").strip()
                
                new_premium_user = PremiumUser.create_premium_user(name, gender, num_tel, email, password, None)
                new_premium_user.subscription_payment()
                user_bd.add_premium_user(new_premium_user)
                print(f"Inscription Premium réussie! Votre ID est: {new_premium_user.user_id}")
                current_user = new_premium_user
            
            elif inscrip_choix == 0:
                print("Retour au menu principal.....")
                continue
            
            # Menu après inscription
            if current_user:
                handle_user_menu(current_user, video, video_bd, channel_bd, user_bd)
        
        elif choix == 2:  # Se connecter
            logged_user = user_bd.log_in()
            if not logged_user:
                print("\n CONNEXION ÉCHOUÉE ! Retour au menu principal...\n")
                continue
            
            current_user = logged_user
            handle_user_menu(current_user, video, video_bd, channel_bd, user_bd)
        
        elif choix == 3:  # Se déconnecter
            email = input("Entrez l'email du compte: ")
            user_bd.log_out(email)
            current_user = None
            print("Vous êtes déconnecté.")
        
        elif choix == 0:  # Quitter
            print("Merci pour votre visite!")
            break
        
        else:
            print("Choix invalide. Veuillez réessayer.")

def handle_user_menu(current_user, video, video_bd, channel_bd, user_bd):
    while True:
        menu_inscription()
        try:
            reponse = int(input("veuillez entrer un choix :"))
        except ValueError:
            print("Choix invalide. Veuillez entrer un numéro.")
            continue
        
        if reponse == 1:  # Poster une vidéo
            author = input("Entrer votre nom : ")
            titre = input("Entrer le titre de la video :")
            description = input("Entrer la description de la video : ")
            resolution = int(input("Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : "))
            
            new_video = Video.create_video(titre, author, description, resolution, date.today())
            video_bd.add_video(new_video)
            current_user.add_video(new_video)
            
            print(f"Vidéo créée avec l'ID: {new_video.video_id}")
            print("\n")
            video_bd.display_video()
            
            rep = input("\n souhaitez vous liker ?(O/N)...").upper()
            if rep == 'O':
                video_bd.liked_video(titre)
                current_user.add_liked_video(new_video.video_id)
                print("\n")
                video_bd.display_video()
            
            re = input("\n Souhaitez-vous laisser un commentaire ? (O/N)...").upper()
            if re == 'O':
                nme = input("Entrez votre nom:")
                cm = input("Entrez votre commentaire :")
                
                new_comment = Comments.create_comment(nme, date.today(), cm)
                video.add_comment(new_comment)
                current_user.add_comment(new_comment)
                
                print(f"Commentaire créé avec l'ID: {new_comment.comment_id}")
                print("\n")
                video.display_list_comments()
                
                v = input("Souhaitez vous liker un commentaire ?(O/N)...").upper()
                if v == 'O':
                    iden = input("Entrez l'id :")
                    video.liked_comment(iden)
        
        elif reponse == 2:  # Rechercher une vidéo
            titre_recherche = input(" Entrer le titre de la video que vous desirez rechercher :")
            video_bd.research_video(titre_recherche)
            
            ch = input("\n Souhaitez-vous liker cette video ?(O/N)").upper()
            if ch == 'O':
                video_bd.liked_video(titre_recherche)
                found_video = video_bd.get_video_by_title(titre_recherche)
                if found_video:
                    current_user.add_liked_video(found_video.video_id)
                video_bd.display_video()
            
            z = input("\n Souhaitez-vous laisser un commentaire ? (O/N)...").upper()
            if z == 'O':
                nme = input("Entrez votre nom:")
                cm = input("Entrez votre commentaire :")
                
                new_comment = Comments.create_comment(nme, date.today(), cm)
                video.add_comment(new_comment)
                current_user.add_comment(new_comment)
                
                print(f"Commentaire créé avec l'ID: {new_comment.comment_id}")
                print("\n")
                video.display_list_comments()
            
            vv = input("Souhaitez vous liker un commentaire ?(O/N)...").upper()
            if vv == 'O':
                identi = input("Entrez l'id :")
                video.liked_comment(identi)
        
        elif reponse == 3:  # Supprimer une vidéo
            n = input("entrez le nom de la video a supprimer :")
            video_bd.delete_video(n)
            print("OPERATION REUSSIE AVEC SUCCES....")
        
        elif reponse == 4:  # Créer une chaîne
            nom = input("Entrer votre nom :")
            nom_chaine = input("Entrez le nom de votre chaine :")
            
            new_channel = Channel.create_channel(nom, nom_chaine, date.today())
            channel_bd.add_channel(new_channel)
            print(f"BIENVENUE DANS LA CHAINE {nom_chaine}")
        
        elif reponse == 5:  # Rechercher une chaîne
            name_channel = input("Entrez le nom de la chaine :")
            channel_bd.search_channel(name_channel)
        
        elif reponse == 6:  # Suivre une chaîne
            n_channel = input("Entrez le nom de la chaine :")
            channel_bd.search_channel(n_channel)
            c = input("\nSouhaitez-vous suivre cette chaine ?(O/N)").upper()
            if c == 'O':
                channel_bd.follow_channel(n_channel)
        
        elif reponse == 7:  # Arrêter de suivre une chaîne
            n_ch = input("Entrez le nom de la chaine :")
            channel_bd.search_channel(n_ch)
            ct = input("\nSouhaitez-vous arreter de suivre cette chaine ?(O/N)").upper()
            if ct == 'O':
                channel_bd.unfollow_channel(n_ch)
        
        elif reponse == 8:  # Poster une vidéo dans la chaîne
            a = input("Entrer votre nom : ")
            t = input("Entrer le titre de la video :")
            d = input("Entrer la description de la video : ")
            r = int(input("Entrer la resolution desiree de votre video (ex: 720, 1080, 4, 8) : "))
            
            channel_video = Video.create_video(t, a, d, r, date.today())
            channel_bd.posted_video(channel_video)
            current_user.add_video(channel_video)
        
        elif reponse == 9:  # Supprimer une vidéo de la chaîne
            idi = input("Entrer l'ID de la vidéo : ")
            channel_bd.delete_video(idi)
            print("OPERATION REUSSIE AVEC SUCCES....")
        
        elif reponse == 10:  # Retour au menu principal
            print("Retour au menu principal...")
            break
        
        elif reponse == 11:  # Afficher mon profil
            if isinstance(current_user, PremiumUser):
                current_user.display_profile_premium_user()
            else:
                user_bd.display_profile_user(current_user.email)
        
        elif reponse == 0:  # Fermer
            print("Merci pour votre fidelite............")
            exit()
        
        else:
            print("Choix invalide. Veuillez réessayer.")

if __name__ == "__main__":
    main()