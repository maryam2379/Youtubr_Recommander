import pickle
import os

class IdGenerator:
    _user_counter = 1
    _video_counter = 1
    _comment_counter = 1
    _channel_counter = 1
    _counter_file = "counters.dat"
    
    @classmethod
    def _load_counters(cls):
        if os.path.exists(cls._counter_file):
            try:
                with open(cls._counter_file, 'rb') as f:
                    data = pickle.load(f)
                    cls._user_counter = data.get('user', 1)
                    cls._video_counter = data.get('video', 1)
                    cls._comment_counter = data.get('comment', 1)
                    cls._channel_counter = data.get('channel', 1)
            except Exception as e:
                print(f"Erreur lors du chargement des compteurs: {e}")
    
    @classmethod
    def _save_counters(cls):
        try:
            with open(cls._counter_file, 'wb') as f:
                data = {
                    'user': cls._user_counter,
                    'video': cls._video_counter,
                    'comment': cls._comment_counter,
                    'channel': cls._channel_counter
                }
                pickle.dump(data, f)
        except Exception as e:
            print(f"Erreur lors de la sauvegarde des compteurs: {e}")
    
    @classmethod
    def generate_user_id(cls):
        user_id = f"us{cls._user_counter:03d}"
        cls._user_counter += 1
        cls._save_counters()
        return user_id
    
    @classmethod
    def generate_video_id(cls):
        video_id = f"VID{cls._video_counter:03d}"
        cls._video_counter += 1
        cls._save_counters()
        return video_id
    
    @classmethod
    def generate_comment_id(cls):
        comment_id = f"COM{cls._comment_counter:03d}"
        cls._comment_counter += 1
        cls._save_counters()
        return comment_id
    
    @classmethod
    def generate_channel_id(cls):
        channel_id = f"CH{cls._channel_counter:03d}"
        cls._channel_counter += 1
        cls._save_counters()
        return channel_id

# Initialiser les compteurs au chargement du module
IdGenerator._load_counters()