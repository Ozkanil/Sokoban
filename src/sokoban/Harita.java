package sokoban;

public class Harita {

    private int adamX, adamY;
    private int hedefX, hedefY;
    private int kutuX, kutuY;
    static int adimSayisi = 0;
    static int adimSayisiKosu = 0;

    public static final int ZEMIN = 1;
    public static final int DUVAR = 2;
    public static final int ADAM = 3;
    public static final int KUTU = 5;
    public static final int HEDEF = 6;
    public static final int KOSU = 7;
    public static final int HEDEFTASIMA = 8;
    static boolean adamKosabilir = false;
    static boolean hedefiItebilir = false;

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

    public String toString() {

        String s = "";

        for (int[] ints : matris) {
            for (int x = 0; x < matris[0].length; x++) {
                int hucre = ints[x];

                switch (hucre) {
                    case ZEMIN:
                        s += new Zemin().getSymbol();
                        System.out.println(" ");
                        break;
                    case DUVAR:
                        s += new Duvar().getSymbol();
                        System.out.println(" ");
                        break;
                    case ADAM:
                        s += new Oyuncu().getSymbol();
                        System.out.println(" ");
                        break;
                    case KUTU:
                        s += new Kutu().getSymbol();
                        System.out.println(" ");
                        break;
                    case HEDEF:
                        s += new Hedef().getSymbol();
                        break;
                    case KOSU:
                        s += new Kosu().getSymbol();
                        break;
                    case HEDEFTASIMA:
                        s += new Hedeftasima().getSymbol();
                        break;
                }
            }
            s += "\n";
        }
        return s;
    }

    public boolean adaminYeriniGuncelle(int x, int y)
    {

        boolean basarili = false;

        //Hedef taşıma özelliğini kazanınca yeni özelliği ekle. Oyuncu hedef taşıyabilir
        if (matris[y][x]==HEDEFTASIMA)
        {
            hedefiItebilir = true;
        }

        if (hedefiItebilir&& matris[y][x] == HEDEF && hedefIcinGecerliAdimmi(x, y) && adamIcinGecerliAdimmi(x, y, hedefiItebilir))
        {
            hedefinYeriniGuncelle(x, y);
        }
        //10 adım sonunda özelliği kaldır
        if (hedefiItebilir)
        {
            adimSayisi++; // adimSayisi say
            if (adimSayisi == 10)
            {
                hedefiItebilir = false;
            }
        }

        //Oyuncunun adımlarının koşullarını belirle. İlk koşulda oyuncu hedefi itebilir
        if (adamIcinGecerliAdimmi(x, y, hedefiItebilir)) {

            kutununYeriniGuncelle(x, y); //adamdan önce kutunun yerini güncelle

            //Başka özelliği olmayan oyuncu koşu özelliğini kazanınca oyuncu koşabilir
            if (matris[3][1]== KOSU && !hedefiItebilir)
            {
                adamKosabilir = true;
            }
            if (adamKosabilir && kosuIcinGecerliAdimmi(x, y))
            {
                matris[adamY][adamX] = ZEMIN; // şu an ki bulunduğu yeri boşluk olarak belirle
                matris[y+ (y-adamY)][x+(x-adamX)] = ADAM; // haritada adamın yerini değiştir
                adamX = x+(x-adamX);
                adamY = y+ (y-adamY);
                basarili = true;

                //10 adım sonunca özelliği kaldır
                adimSayisiKosu++;
                if (adimSayisiKosu == 10)
                {
                    adamKosabilir = false;
                }
            //Oyuncunun hedefi itemediği durum
            }
            else
            {
            matris[adamY][adamX] = ZEMIN; // şu an ki bulunduğu yeri boşluk olarak belirle
            matris[y][x] = ADAM; // haritada adamın yerini değiştir
            adamX = x;
            adamY = y;

            // Adamın koşabilme özelliğini alması ve hedefi itme özelliği olmaması halinde koşu özelliği kazanması
            //if (matris[y][x] == KOSU && !hedefiItebilir) {adamKosabilir =true; }

            basarili = true;}
            ozellikEkle();
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
            if (matris[hedefY][hedefX] == ZEMIN)
            {
                matris[hedefY][hedefX] = HEDEF;
            }
        }

        if (kutuIcinGecerliAdimmi(kutununYeniYeriX, kutununYeniYeriY)) {
            if (matris[kutununYeniYeriY][kutununYeniYeriX] == HEDEF) {
                //kutunun hedeften tekrar uzaklaştırılması halinde hedefi haritada yeniden göstermek için yerini sakla
                hedefX = kutununYeniYeriX;
                hedefY = kutununYeniYeriY;
            }
            kutuX = kutununYeniYeriX;
            kutuY = kutununYeniYeriY;

            matris[kutuY][kutuX] = ZEMIN; // kutunun yerini boşluk olarak belirle
            matris[kutununYeniYeriY][kutununYeniYeriX] = KUTU; // haritada kutunun yerini değiştir
        }
    }
    //Oyuncu hedefi itme özelliğine  sahip olunca hedefin yerini güncelleyecek blok
    public void hedefinYeriniGuncelle(int x, int y) {
        int hedefinYeniYeriX =0;
        int hedefinYeniYeriY = 0;
        int hucre = matris[y][x];
        if (hucre == HEDEF) {
            hedefinYeniYeriX = x + (x - adamX);
            hedefinYeniYeriY = y + (y - adamY);
        }

        //Hedefin hangi koşullarda itilebileceğini belirle
        if (hedefIcinGecerliAdimmi(hedefinYeniYeriX, hedefinYeniYeriY)) {

            hedefX = hedefinYeniYeriX;
            hedefY = hedefinYeniYeriY;

            matris[hedefY][hedefX] = ZEMIN; // hedefin yerini boşluk olarak belirle
            matris[hedefinYeniYeriY][hedefinYeniYeriX] = HEDEF; // haritada hedefin yerini değiştir
        }
    }

    //Bir sonraki adımın adam için geçerli bir adım olup olmadığını kontrol et
    private boolean adamIcinGecerliAdimmi(int x, int y, boolean gucuVar) {
       //Eğer oyuncu bir özellik kazandıysa hareket koşullarını belirle
        if (gucuVar)
        {
        int newPositionX = adamX + (x - adamX) + (x - adamX);
        int newPositionY = adamY + (y - adamY) + (y - adamY);

        return matris[y][x] == HEDEFTASIMA || (matris[y][x] == HEDEF && matris[newPositionY][newPositionX] != DUVAR) || matris[y][x] == KOSU || matris[y][x] == ZEMIN || (matris[y][x] == KUTU && matris[newPositionY][newPositionX] != KUTU && matris[newPositionY][newPositionX] != DUVAR);
        }
        //Oyuncu hiçbir özelliğe sahip olmazsa hareket koşullarını belirle
        else
        {
           int newPositionX = adamX + (x - adamX) + (x - adamX);
           int newPositionY = adamY + (y - adamY) + (y - adamY);

           return matris[y][x] == HEDEFTASIMA || matris[y][x] == KOSU || matris[y][x] == ZEMIN || matris[y][x] == KUTU && matris[newPositionY][newPositionX] != KUTU && matris[newPositionY][newPositionX] != DUVAR && matris[y][x] != DUVAR && matris[y][x] != HEDEF;
       }
    }

    //Bir sonraki adımın koşu için geçerli bir adım olup olmadığını kontrol et
    private boolean kosuIcinGecerliAdimmi(int x, int y) {

        int newPositionX = adamX + (x - adamX) + (x - adamX);
        int newPositionY = adamY + (y - adamY) + (y - adamY);

        return matris[y][x] == ZEMIN || matris[y+ (y - adamY)][x + (x - adamX)] == ZEMIN  ||matris[y][x] == KUTU && matris[newPositionY][newPositionX] != KUTU && matris[newPositionY][newPositionX] != DUVAR;
    }

    //Bir sonraki adımın kutu için geçerli bir adım olup olmadığını kontrol et
    private boolean kutuIcinGecerliAdimmi(int x, int y) {

        return matris[y][x] == ZEMIN || matris[y][x] == HEDEF;
    }

    //İtilebildiği senaryoda bir sonraki adımın hedef için geçerli bir adım olup olmadığını kontrol et
    private boolean hedefIcinGecerliAdimmi(int x, int y) {

        return matris[y][x] == ZEMIN || matris[y][x] != DUVAR;
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

    //Haritaya koşabilme ve hedefi itebilme özelliklerini ekle
    protected void ozellikEkle() {

       if (Oyun.adım ==4) {
           matris[3][1] = KOSU;
       }
        if (Oyun.adım ==9) {
            matris[3][1] = ZEMIN;
            matris[6][6] = HEDEFTASIMA;
        }
        if (Oyun.adım ==19) {
            matris[6][6] = ZEMIN;
        }
            }
        }



