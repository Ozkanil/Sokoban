package sokoban;
import java.util.Scanner;

public class Oyun {

    private Harita harita;
    public static final int YUKARI = 1;
    public static final int ASAGI = 2;
    public static final int SAGA = 3;
    public static final int SOLA = 4;
    public static final int HATALI = 0;

    private int adım;

    public Oyun(){
        harita = null;
        adım = 0;
    }

    public Oyun(int[][] matris){
        adım = 0;
        yukle(matris);
    }

    public void yukle(int[][] matris){

        harita = new Harita(matris);
    }

    public void baslat(){
        do {
            sahneyiYazdir();
            int yon = kullanicidanYonBilgisiAl();
            adamiHareketEttir(yon);
        } while (!harita.bittiMi());
        System.out.println("Tebrikler 'Seviye 1' " + adım + " adımda tamamlandı.");
    }

    private void adamiHareketEttir(int yon) {

        switch(yon){

            case YUKARI:

                if(harita.adaminYeriniGuncelle(harita.getAdamX(), harita.getAdamY()-1))
                    adım++;

                break;

            case SOLA:

                if(harita.adaminYeriniGuncelle(harita.getAdamX()-1, harita.getAdamY()))
                    adım++;

                break;

            case ASAGI:

                if(harita.adaminYeriniGuncelle(harita.getAdamX(), harita.getAdamY()+1))
                    adım++;

                break;

            case SAGA:

                if(harita.adaminYeriniGuncelle(harita.getAdamX()+1, harita.getAdamY()))
                    adım++;

                break;
        }
    }

    private int kullanicidanYonBilgisiAl() {

        Scanner konsol = new Scanner(System.in);

        System.out.print("Yön Tuşları (w = yukarı, s = aşağı, a = sol, d = sağ), seçiniz: ");
        String secimStr = konsol.next();

        char secim = secimStr.charAt(0);
        int yon = HATALI;

        switch(secim){
            case 'w':
            case 'W':
                yon = YUKARI;
                break;

            case 'a':
            case 'A':
                yon = SOLA;
                break;

            case 's':
            case 'S':
                yon = ASAGI;
                break;

            case 'd':
            case 'D':
                yon = SAGA;
                break;

        }
        return yon;
    }

    public void sahneyiYazdir(){
        System.out.println("-------------------------\n");

        System.out.println(harita);

        System.out.println("\n Toplam Adım: "+ adım);
        System.out.println("\n-------------------------\n");
    }
}
