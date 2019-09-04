package CTCI.OOP;

import java.util.Queue;
import java.util.Set;

/**
 * Design a musical jukebox using oop
 * Basic system components:
 * - Jukebox
 * - CD
 * - Song
 * - Artist
 * - Playlist
 * - Display
 *
 * Actions:
 * - Playlist creation(add, delete, shuffle)
 * - CD selector
 * - Song selector
 * - Queuing up a song
 * - Get next song from playlist
 *
 * A user also can be introduced:
 * - Adding
 * - Deleting
 * - Credit information
 *
 * Each of the system components maps roughly to an object
 * and each action translates to a method.
 */
public class JukeBoxProblem {
    public static void main(String[] args) {

    }

    static class Song {
        private String songName;

        String toStr() {
            return songName;
        }
    }

    static class SongSelector {
        private Song currentSong;
        SongSelector(Song s) {
            currentSong = s;
        }

        void getSong(Song s) {
            currentSong = s;
        }

        Song getCurrentSong() {
            return currentSong;
        }
    }

    static class CD {

    }

    static class Playlist {
        private Song song;
        private Queue<Song> queue;

        Playlist(Song song, Queue<Song> queue) {
            super();
            this.song = song;
            this.queue = queue;
        }

        Song getNextSongToPlay() {
            return queue.peek();
        }

        void queueUpSong(Song s) {
            queue.add(s);
        }
    }

    static class CDPlayer {
        private Playlist playlist;
        private CD cd;

        Playlist getPlaylist() {
            return playlist;
        }

        void setPlaylist(Playlist p) {
            this.playlist = p;
        }

        CD getCD() {
            return cd;
        }

        void setCD (CD cd) {
            this.cd = cd;
        }

        CDPlayer(Playlist playlist) {
            this.playlist = playlist;
        }

        CDPlayer(CD cd, Playlist playlist) {
            this.playlist = playlist;
            this.cd = cd;
        }

        CDPlayer (CD cd) {
            this.cd = cd;
        }

        void playSong(Song s) {

        }
    }

    static class JukeBox {
        private CDPlayer cdPlayer;
        private User user;
        private Set<CD> cdCollection;
        private SongSelector ts;

        JukeBox(CDPlayer cdPlayer, User user, Set<CD> cdCollection, SongSelector ts) {
            super();
            this.cdPlayer = cdPlayer;
            this.user = user;
            this.cdCollection = cdCollection;
            this.ts = ts;
        }

        Song getCurrentSong() {
            return ts.getCurrentSong();
        }

        void setUser(User u) {
            this.user = u;
        }

    }

    static class User {
        private String name;
        private long ID;
        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        long getID() {
            return ID;
        }

        void setID(long iD) {
            ID = iD;
        }

        User(String name, long iD) {
            this.name = name;
            this.ID = iD;
        }

        User getUser() {
            return this;
        }

        static User addUser(String name, long iD) {
            return new User(name, iD);
        }
    }

    
}
