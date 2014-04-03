package farkanoid;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

/**
 * ei12130 - Eduardo Fernandes
 * ei12164 - José Pinto
 * Thread for the sfx audio.
 */

public class PlayThread implements Runnable {
    private byte[] audioFile;
    private boolean enabled;

    public PlayThread(byte[] input, boolean in) {
        // store parameter for later user
        this.audioFile = input;
        enabled=in;
    }

    public void run() {
        if (enabled) {
            AudioData audioData = new AudioData(audioFile);
            AudioDataStream audioStream = new AudioDataStream(audioData);
            AudioPlayer.player.start(audioStream);
        }
    }
}