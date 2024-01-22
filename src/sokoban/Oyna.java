package sokoban;

import java.io.IOException;

public class Oyna {

    public static void main(String[] args) throws IOException {

        int[][] harita = ConfigReader.getInstance("src/map/Map0.file").readConfig();

        Oyun oyun = new Oyun();
        oyun.yukle(harita);
        oyun.baslat();


}}


