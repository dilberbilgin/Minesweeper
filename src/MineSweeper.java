import java.util.Random;
import java.util.Scanner;

/**
 * MineSweeper sınıfı, Mayın Tarlası oyununu uygulayan bir Java sınıfıdır.
 * Oyun, kullanıcı tarafından belirlenen satır ve sütun sayılarına göre oluşturulan
 * bir tahtada oynanır. Mayınların rastgele yerleştirildiği ve kullanıcının
 * seçtiği noktalarda mayın olup olmadığını ve çevresindeki mayınları saydığı
 * bir oyundur.
 */
public class MineSweeper {
    private int rowNumber;           // Tahtanın satır sayısı
    private int colNumber;           // Tahtanın sütun sayısı
    private boolean[][] mineLocations;   // Mayınların yerini tutan boolean matris
    private String[][] mineBoard;        // Mayınların gösterildiği tahta
    private String[][] playerBoard;      // Oyuncunun gördüğü tahta
    private boolean isGameInProgress;    // Oyunun devam edip etmediğini belirten bayrak
    private int mineNumber;              // Tahtadaki toplam mayın sayısı

    /**
     * MineSweeper sınıfının constructor'ı. Belirtilen satır ve sütun sayısına göre
     * yeni bir Mayın Tarlası oyunu oluşturur.
     * rowNumber Oyun tahtasının satır sayısı
     * colNumber Oyun tahtasının sütun sayısı
     */
    public MineSweeper(int rowNumber, int colNumber) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.mineLocations = new boolean[rowNumber][colNumber];
        this.mineBoard = new String[rowNumber][colNumber];
        this.playerBoard = new String[rowNumber][colNumber];
        this.isGameInProgress = false;
    }

    /**
     * Oyunu başlatmak için gereken mayınları yerleştirir ve tahtaları hazırlar.
     * Mayınları rastgele yerleştirir ve oyuncu tahtasını "-" sembolleriyle doldurur.
     * Mayınların konumunu gösteren bir tahtayı ekrana yazdırır.
     */
    public void setUpMines() {
        // Mayınları ve boş hücreleri gösteren tahtaları başlat
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                mineBoard[i][j] = "-";
            }
        }
        // Belirlenen mayın sayısı kadar rastgele mayınları yerleştir
        int mineCount = 0;
        mineNumber = rowNumber * colNumber / 4;
        Random random = new Random();

        while (mineCount < mineNumber) {
            int randRow = random.nextInt(rowNumber);
            int randCol = random.nextInt(colNumber);

            if (isValidCell(randRow, randCol) && (!mineLocations[randRow][randCol])) {
                mineLocations[randRow][randCol] = true;
                mineBoard[randRow][randCol] = "*";
                mineCount++;
            }
        }
        // Oyuncu tahtasını "-" sembolleriyle doldur
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                playerBoard[i][j] = "-";
            }
        }
        // Mayınların konumunu gösteren tahtayı ekrana yazdır
        System.out.println("Mayınların Konumu");
        printBoard(mineBoard);
        System.out.println("================================");
        System.out.println("Mayin tarlasin oyununa hosgeldiniz");
    }

    /**
     * Verilen bir tahtayı ekrana yazdıran metod.
     * field Yazdırılacak tahta matrisi
     */
    public void printBoard(String[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Kullanıcıdan satır ve sütun numaralarını alarak bir dizi olarak döndüren metod.
     * return kullanıcının girdiği satır ve sütun numaralarını içeren dizi
     */
    public int[] getUserInput(){
        Scanner input = new Scanner(System.in);
        int[] coordinates = new int[2];
        System.out.println("Satir numarasi giriniz: ");
        coordinates[0] = input.nextInt();
        System.out.println(("Sutun numarasi giriniz: "));
        coordinates[1] = input.nextInt();
        return coordinates;
    }

    /**
     * Verilen satır ve sütun numarasının geçerli olup olmadığını kontrol eden metod.
     * row Kontrol edilecek satır numarası
     * col Kontrol edilecek sütun numarası
     * return Geçerli bir hücre ise true, değilse false
     */
    public boolean isValidCell(int row, int col){
        return row >= 0 && row < rowNumber && col >= 0 && col < colNumber;
    }

    /**
     * Verilen satır ve sütun numarasında bir mayın olup olmadığını kontrol eden metod.
     * selectedRow Kontrol edilecek satır numarası
     * selectedCol Kontrol edilecek sütun numarası
     * return Belirtilen hücrede mayın varsa true, yoksa false
     */
    public boolean isMine(int selectedRow, int selectedCol){
        return mineLocations[selectedRow][selectedCol];
    }

    /**
     * BIRINCI YONTEM
     * Seçilen hücrenin etrafındaki mayınları sayan metod.
     * selectedRow Seçilen hücrenin satır indeksi
     * selectedCol Seçilen hücrenin sütun indeksi
     * return Etrafındaki mayınların sayısı
     */
    public int countSurroundingMines(int selectedRow, int selectedCol) {
        int surroundingMineCount = 0;

        // -1, 0, 1 değerleri ile tüm komşu hücreleri döngü içinde kontrol et
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Kendi hücresini kontrol etmeyecek (i = 0 ve j = 0 durumu hariç)
                if (i != 0 || j != 0) {
                    int newRow = selectedRow + i;
                    int newCol = selectedCol + j;

                    // Geçerli bir hücre ve bu hücrede mayın varsa sayacı artır
                    if (isValidCell(newRow, newCol) && isMine(newRow, newCol)) {
                        surroundingMineCount++;
                    }
                }
            }
        }
        return surroundingMineCount;
    }


//    /**
//     * IKINCI YONTEM
//     * Verilen satır ve sütun numarasındaki hücrenin etrafındaki mayın sayısını hesaplayan metod.
//     * row Hesaplanacak hücrenin satır numarası
//     * col Hesaplanacak hücrenin sütun numarası
//     * return Etrafındaki mayın sayısı
//     */
//    public int countSurroundingMines(int row, int col) {
//        int mineCount = 0;
//        int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
//        int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};
//
//        for (int i = 0; i < 8; i++) {
//            int newRow = row + dRow[i];
//            int newCol = col + dCol[i];
//
//            if (isValidCell(newRow, newCol) && mineLocations[newRow][newCol]) {
//                mineCount++;
//            }
//        }
//        return mineCount;
//    }

    /**
     * Kullanıcının seçtiği hücreyi işleyen metod.
     * selectedRow Seçilen hücrenin satır numarası
     * selectedCol Seçilen hücrenin sütun numarası
     */
    public void processSelection(int selectedRow, int selectedCol) {
        if (isMine(selectedRow, selectedCol)) {
            isGameInProgress = false; // Oyunu bitir
            System.out.println("Game Over!");
            printBoard(mineBoard); // Mayınları göster
        } else {
            // Seçilen hücredeki mayın sayısını hesapla ve oyuncu tahtasına yaz
            int surroundingMineCount = countSurroundingMines(selectedRow, selectedCol);
            playerBoard[selectedRow][selectedCol] = String.valueOf(surroundingMineCount);
            printBoard(playerBoard);

            // Oyunun kazanılıp kazanılmadığını kontrol et
            if (isGameWon()) {
                isGameInProgress = false; // Oyunu bitir
                System.out.println("Tebrikler! Oyunu Kazandiniz!");
            }
        }
    }

    /**
     * Oyunun kazanılıp kazanılmadığını kontrol eden metod.
     * return Oyun kazanıldıysa true, oyun devam ediyorsa false
     */
    public boolean isGameWon() {
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                // Eğer oyuncu tahtasında henüz açılmamış bir hücre ve bu hücrede mayın yoksa oyun devam ediyor demektir
                if (playerBoard[i][j].equals("-") && !mineLocations[i][j]) {
                    return false;
                }
            }
        }
        return true; // Tüm açılmamış hücrelerde mayın yoksa oyun kazanılmıştır
    }

    /**
     * Oyunu başlatan metod. Mayınları yerleştirir, oyuncu tahtasını hazırlar ve oyunu başlatır.
     */
    public void startGame() {
        setUpMines(); // Mayınları yerleştir ve tahtayı hazırla
        printBoard(playerBoard); // Başlangıçta oyuncu tahtasını göster
        isGameInProgress = true; // Oyunun devam ettiğini belirten bayrakk

        // Oyun devam ederken kullanıcıdan hücre seçimlerini al ve işle
        while (isGameInProgress) {
            int[] coordinates = getUserInput();
            int selectedRow = coordinates[0];
            int selectedCol = coordinates[1];

            if (isValidCell(selectedRow, selectedCol)) {
                processSelection(selectedRow, selectedCol);
            } else {
                System.out.println("Geçersiz bir hücre seçtiniz! Lütfen tekrar deneyin.");
            }
        }
    }
}
