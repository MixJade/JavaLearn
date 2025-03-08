package jade.utils;

import jade.consts.MusicEnum;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface MusicPlayer {
    ExecutorService executor = Executors.newFixedThreadPool(3);

    static void music(MusicEnum musicEnum) {
        executor.submit(musicEnum);
    }
}