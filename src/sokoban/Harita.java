package sokoban;

public class Harita {

    private int adamX, adamY;
    private int hedefX, hedefY;
    private int kutuX, kutuY;

    public static final int ZEMIN = 1;
    public static final int DUVAR = 2;
    public static final int ADAM = 3;
    public static final int KUTU = 5;
    public static final int HEDEF = 6;

    protected int[][] matris;

    public Harita(int[][] grid) {
        adamX = adamY = hedefX = hedefY = 0;
        kutuX = kutuY = 0;
        yukle(grid);
    }

    public void yukle(int[][] grid) {

        matris = new int[grid.length][grid[0].length];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                int hucre = grid[y][x];
                if (hucre == ADAM) {
                    adamX = x;
                    adamY = y;
                }
                if (hucre == KUTU) {
                    kutuX = x;
                    kutuY = y;
                }
                matris[y][x] = grid[y][x];
            }
        }
    }

    public int getAdamX() {
        return adamX;
    }

    public int getAdamY() {
        return adamY;
    }

    public int getKutuY() {
        return kutuY;
    }

    public String toString() {

        String s = "";

        for (int[] ints : matris) {
            for (int x = 0; x < matris[0].length; x++) {
                int hucre = ints[x];

                switch (hucre) {
                    case ZEMIN:
                        s += " ";
                        System.out.println(" ");
                        break;
                    case DUVAR:
                        s += "*";
                        System.out.println(" ");
                        break;
                    case ADAM:
                        s += "�";
                        System.out.println(" ");
                        break;
                    case KUTU:
                        s += "K";
                        System.out.println(" ");
                        break;
                    case HEDEF:
                        s += "H";
                        break;
                }
            }
            s += "\n";
        }

        return s;

    }

    public boolean adaminYeriniGuncelle(int x, int y) {

        boolean basarili = false;

        if (adamIcinGecerliAdimmi(x, y)) {

            kutununYeriniGuncelle(x, y); //adamdan önce kutunun yerini güncelle

            matris[adamY][adamX] = ZEMIN; // şu an ki bulunduğu yeri boşluk olarak belirle
            matris[y][x] = ADAM; // haritada adamın yerini değiştir
            adamX = x;
            adamY = y;
            basarili = true;
        }
        return basarili;
    }

    public void kutununYeriniGuncelle(int x, int y) {
        int kutununYeniYeriX =0;
        int kutununYeniYeriY = 0;
        int hucre = matris[y][x];
        if (hucre == KUTU) {
            kutununYeniYeriX = x + (x - adamX);
            kutununYeniYeriY = y + (y - adamY);
            //Kutu hedefe yerleştirildikten sonra tekrar uzaklaştırılırsa hedefi haritada tekrar göster
            if (matris[hedefY][hedefX] == ZEMIN) {

                matris[hedefY][hedefX] = HEDEF;
            }
        }

        if (kutuIcinGecerliAdimmi(kutununYeniYeriX, kutununYeniYeriY)) {
            if (matris[kutununYeniYeriY][kutununYeniYeriX] == HEDEF) {
                hedefX = kutununYeniYeriX; //kutunun hedeften tekrar uzaklaştırılması halinde hedefi haritada yeniden göstermek için yerini sakla
                hedefY = kutununYeniYeriY;
            }
            kutuX = kutununYeniYeriX;
            kutuY = kutununYeniYeriY;

            matris[kutuY][kutuX] = ZEMIN; // kutunun yerini boşluk olarak belirle
            matris[kutununYeniYeriY][kutununYeniYeriX] = KUTU; // haritada kutunun yerini değiştir
        }
    }
    //Bir sonraki adımın adam için geçerli bir adım olup olmadığını kontrol et
    private boolean adamIcinGecerliAdimmi(int x, int y) {

        int newPositionX = adamX + (x - adamX) + (x - adamX);
        int newPositionY = adamY + (y - adamY) + (y - adamY);

        return matris[y][x] == ZEMIN || matris[y][x] == KUTU && matris[newPositionY][newPositionX] != KUTU && matris[newPositionY][newPositionX] != DUVAR;
    }

    //Bir sonraki adımın kutu için geçerli bir adım olup olmadığını kontrol et
    private boolean kutuIcinGecerliAdimmi(int x, int y) {

        return matris[y][x] == ZEMIN || matris[y][x] == HEDEF;
    }

    //Her adımdan sonra haritada tüm kutuların yerine yerleştirilip yerleştirilmediğini kontrol et
    protected boolean bittiMi() {
        boolean oyunBitti = false;
        int sayac =0;

        for (int[] ints : matris) {
            for (int x = 0; x < matris[0].length; x++) {

                if (ints[x] == HEDEF) {
                    sayac++;
                    System.out.println(sayac);
                }
            }
        }
        if (sayac == 0){
            oyunBitti = true;
            }   return oyunBitti;
    }
}

