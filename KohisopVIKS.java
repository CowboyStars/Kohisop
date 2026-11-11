
import java.util.Scanner;

public class KohisopVIKS {
    static String[][] menu = {
        {"E1", "Caffe Latte"},
        {"E2", "Cappuccino"},
        {"E3", "Caffe Mocha"},
        {"E4", "Caramel Macchiato"},
        {"E5", "Caffe Americano"},
        {"E6", "Asian Dolce Latte"},
        {"E7", "Double Shots Iced Shaken Espresso"},
        {"B1", "Freshly Brewed Coffee"},
        {"B2", "Vanilla Sweet Cream Cold Brew"},
        {"B3", "Cold Brew"}
    };
    static int[] hargaMenu = {46, 46, 55, 59, 37, 55, 50, 23, 50, 44};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Menu();

        String[] kode = new String[5];
        int[] jumlah = new int[5];

        EntryPesan(kode, jumlah, sc);
        Detail(kode, jumlah);

        String mataUang;

        while (true) {
            System.out.print("\nMasukkan mata uang (IDR, USD, JPY, MYR, EUR): ");
            mataUang = sc.nextLine();

            if (mataUang.equalsIgnoreCase("IDR") == true || mataUang.equalsIgnoreCase("USD") == true ||
                mataUang.equalsIgnoreCase("JPY") == true || mataUang.equalsIgnoreCase("MYR") == true ||
                mataUang.equalsIgnoreCase("EUR") == true) {
                break;
            } else {
                System.out.println("Mata uang tidak valid!");
            }
        }
        DetailPajak(kode, jumlah, mataUang);

        sc.close();
    }

    static void Menu() {
        System.out.println("DAFTAR MENU KOHISOP");
        System.out.printf("%-6s %-38s %-10s\n", "Kode", "Menu Minuman", "Harga (Rp)");

        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%-6s %-38s %d\n", menu[i][0], menu[i][1], hargaMenu[i]);
        }
    }

    static void EntryPesan(String[] kode, int[] jumlah, Scanner sc) {

        int pesananKe = 0;
        while (pesananKe < 5) {
            System.out.print("\nMasukkan kode minuman: ");
            String inputKode = sc.nextLine();

            if (cekKode(inputKode) == false) {
                System.out.println("Kode tidak valid!");
                continue;
            }

            if (sudahDipesan(kode, inputKode, pesananKe) == true) {
                System.out.println("Menu sudah dipesan, pilih yang lain!");
                continue;
            }

            int inputJumlah;
            while (true) {
                System.out.print("Masukkan jumlah (1-3 atau 0 untuk selesai): ");

                if (sc.hasNextInt() == true) {
                    inputJumlah = sc.nextInt();
                    sc.nextLine();
                } else {
                    String temp = sc.nextLine();
                    if (temp.equalsIgnoreCase("s") == true) {
                        inputJumlah = 0;
                    } else {
                        inputJumlah = -1;
                    }
                }

                if (inputJumlah >= 0 && inputJumlah <= 3) {
                    break;
                } else {
                    System.out.println("Jumlah tidak valid! Maksimal 3 porsi.");
                }
            }

            if (inputJumlah == 0) {
                break;
            }

            kode[pesananKe] = inputKode.toUpperCase();
            jumlah[pesananKe] = inputJumlah;

            pesananKe++;

            if (pesananKe < 5) {
                System.out.print("Apakah ada pesanan lain? (y/n): ");
                String lanjut = sc.nextLine();

                if (lanjut.equalsIgnoreCase("n") == true) {
                    break;
                }
            }
        }
    }

    static boolean cekKode(String kode) {
        for (int i = 0; i < menu.length; i++) {
            if (menu[i][0].equalsIgnoreCase(kode) == true) {
                return true;
            }
        }
        return false;
    }

    static boolean sudahDipesan(String[] kode, String inputKode, int batasSekarang) {
        for (int i = 0; i < batasSekarang; i++) {
            if (kode[i] != null) {
                if (kode[i].equalsIgnoreCase(inputKode) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    static int cariIndeks(String kode) {
        for (int i = 0; i < menu.length; i++) {
            if (menu[i][0].equalsIgnoreCase(kode) == true) {
                return i;
            }
        }
        return -1;
    }

    static void Detail(String[] kode, int[] jumlah) {
        int totalTanpaPajak = 0;
        System.out.println("\nDETAIL PESANAN");
        System.out.printf("%-6s %-38s %-8s %-10s\n", "Kode", "Minuman", "Jumlah", "Harga Total");

        for (int i = 0; i < 5; i++) {
            if (jumlah[i] > 0) {
                int idx = cariIndeks(kode[i]);

                String nama = menu[idx][1];
                int harga = hargaMenu[idx];

                int totalJenis = harga * jumlah[i];

                totalTanpaPajak = totalTanpaPajak + totalJenis;

                System.out.printf("%-6s %-38s %-8d Rp%d.000\n", kode[i], nama, jumlah[i], totalJenis);
            }
        }
        System.out.println("Total Keseluruhan (Tanpa Pajak): Rp" + totalTanpaPajak + ".000");
    }

    static double hitungPajak(int harga, int jumlah) {
        int totalHarga = harga * jumlah;
        double totalPajak = 0;

        if (harga < 50) {
            totalPajak = 0;
        } else if (harga <= 55) {
            totalPajak = (totalHarga * 8) / 100;
        } else {
            totalPajak = (totalHarga * 11) / 100;
        }
        return totalPajak;
    }

    static double konversiMataUang(double totalIDR, String mataUang) {
        double hasil = totalIDR;

        if (mataUang.equalsIgnoreCase("USD") == true) {
            hasil = totalIDR / 15;
        } else if (mataUang.equalsIgnoreCase("JPY") == true) {
            hasil = totalIDR * 10;
        } else if (mataUang.equalsIgnoreCase("MYR") == true) {
            hasil = totalIDR / 4;
        } else if (mataUang.equalsIgnoreCase("EUR") == true) {
            hasil = totalIDR / 14;
        } else {
            hasil = totalIDR;
        }
        return hasil;
    }

    static void DetailPajak(String[] kode, int[] jumlah, String mataUang) {
        double totalIDR = 0;

        System.out.println("\nDETAIL PESANAN DAN PAJAK");
        System.out.printf("%-6s %-38s %-8s %-8s %-12s\n", "Kode", "Minuman", "Harga", "Jumlah", "Subtotal");

        for (int i = 0; i < 5; i++) {
            if (jumlah[i] > 0) {
                int idx = cariIndeks(kode[i]);

                String nama = menu[idx][1];
                int harga = hargaMenu[idx];

                int totalJenis = harga * jumlah[i];

                double nilaiPajak = hitungPajak(harga, jumlah[i]);
                double subtotal = totalJenis + nilaiPajak;

                totalIDR = totalIDR + subtotal;

                System.out.printf("%-6s %-38s Rp%-6d %-8d Rp%.2f\n", kode[i], nama, harga, jumlah[i], subtotal);
            }
        }
        double totalBayar = konversiMataUang(totalIDR, mataUang);

        System.out.println("\nRINGKASAN PEMBAYARAN");
        System.out.printf("Total Tagihan (IDR)      : Rp%.2f Ribu\n", totalIDR);
        System.out.println("Mata Uang Pembayaran     : " + mataUang.toUpperCase());
        System.out.printf("Total Yang Harus Dibayar : %.2f %s\n", totalBayar, mataUang.toUpperCase());
    }
}