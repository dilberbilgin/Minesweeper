import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int rowNumber, colNumber;

        // Kullanıcıdan matris boyutlarını alın
        do {
            System.out.print("Satir sayisini giriniz: ");
            rowNumber = input.nextInt();
            System.out.print("Sutun sayisini giriniz: ");
            colNumber = input.nextInt();

            if (rowNumber < 2 || colNumber < 2) {
                System.out.println("Satir ve sutun sayisi en az 2 olmalidir. Lutfen tekrar deneyin.");
            }
        } while (rowNumber < 2 || colNumber < 2);

        MineSweeper mineSweeper = new MineSweeper(rowNumber, colNumber);
        mineSweeper.startGame();
    }
}


//public class Main {
//    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        int rowNumber, colNumber;
//
//        while(true){
//            System.out.print("Satir sayisi giriniz: ");
//            rowNumber = input.nextInt();
//            System.out.print("Sutun sayisi giriniz: ");
//            colNumber = input.nextInt();
//
//            if (rowNumber >= 2 && colNumber >= 2){
//                break;
//
//            } else {
//                System.out.println("Matris boyutu 2x2'den kucuk olamaz");
//
//            }
//        }
//        System.out.println("====================================");
//
//        MineSweeper game = new MineSweeper(rowNumber, colNumber);
//        game.run();
//
//    }
//}
