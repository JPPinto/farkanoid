/*
 * Copyright (c) 2011, Martin Cameron
 * All rights reserved.
 * Modifications by Eduardo Fernandes
 */

package ibxm;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class IBXMBackgroundPlayer {
    private static final int SAMPLE_RATE = 48000;

    private IBXM ibxm;
    private volatile boolean playing;
    private Thread playThread;
    private boolean ready;

    public IBXMBackgroundPlayer(String fileName) {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            int next = in.read();
            while (next > -1) {
                bos.write(next);
                next = in.read();
            }
            bos.flush();
            byte[] result = bos.toByteArray();

            ready = true;
            Module module = new Module(result);
            ibxm = new IBXM(module, SAMPLE_RATE);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public boolean getStatus(){
        return ready;
    }

    public boolean playSong() {
        if (ready)
            play();
        return ready;
    }

    public void pauseSong() {
        if (ready)
            stop();
    }

    private synchronized void loadModule(String path) throws IOException {
        Path modulePath = Paths.get(path);
        byte[] moduleData = Files.readAllBytes(modulePath);

        ready = true;
        Module module = new Module(moduleData);
        ibxm = new IBXM(module, SAMPLE_RATE);
    }

    private synchronized void loadModule(Path modulePath) throws IOException {
        byte[] moduleData = Files.readAllBytes(modulePath);

        ready = true;
        Module module = new Module(moduleData);
        ibxm = new IBXM(module, SAMPLE_RATE);
    }

    private synchronized void play() {
        if (ibxm != null) {
            playing = true;
            playThread = new Thread(new Runnable() {
                public void run() {
                    int[] mixBuf = new int[ibxm.getMixBufferLength()];
                    byte[] outBuf = new byte[mixBuf.length * 4];
                    AudioFormat audioFormat = null;
                    SourceDataLine audioLine = null;
                    try {
                        audioFormat = new AudioFormat(SAMPLE_RATE, 16, 2, true, true);
                        audioLine = AudioSystem.getSourceDataLine(audioFormat);
                        audioLine.open();
                        audioLine.start();
                        while (playing) {
                            int count = getAudio(mixBuf);
                            int outIdx = 0;
                            for (int mixIdx = 0, mixEnd = count * 2; mixIdx < mixEnd; mixIdx++) {
                                int ampl = mixBuf[mixIdx];
                                if (ampl > 32767) ampl = 32767;
                                if (ampl < -32768) ampl = -32768;
                                outBuf[outIdx++] = (byte) (ampl >> 8);
                                outBuf[outIdx++] = (byte) ampl;
                            }
                            audioLine.write(outBuf, 0, outIdx);
                        }
                        audioLine.drain();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        if (audioLine != null && audioLine.isOpen()) audioLine.close();
                    }
                }
            });
            playThread.start();
        }
    }

    private synchronized void stop() {
        playing = false;
        try {
            if (playThread != null) playThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private synchronized int getAudio(int[] mixBuf) {
        return ibxm.getAudio(mixBuf);
    }

}
