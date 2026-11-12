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

            if (mataUang.equalsIgnoreCase("IDR") == true) {
                break;
            } else if(mataUang.equalsIgnoreCase("USD") == true) {
                break;
            } else if(mataUang.equalsIgnoreCase("JPY") == true) {
                break;
            } else if(mataUang.equalsIgnoreCase("MYR") == true) {
                break;
            } else if(mataUang.equalsIgnoreCase("EUR") == true) {
                break;
            } else {
                System.out.println("Mata uang tidak Valid!");
            }
        }
        DetailPajak(kode, jumlah, mataUang);

        sc.close();
    }

    static void Menu() {
        System.out.println("------------------DAFTAR MENU KOHISOP------------------");
        System.out.printf("%-6s %-36s %-10s\n", "Kode", "Menu Minuman", "Harga (Rp)");

        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%-6s %-40s %d\n", menu[i][0], menu[i][1], hargaMenu[i]);
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
                System.out.print("Masukkan jumlah (1-3 atau 0/s untuk selesai): ");

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
                System.out.println("Pesanan dibatalkan!");
                continue;
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
        double totalTanpaPajak = 0;
        double totalDenganPajak = 0;

        System.out.println("\n================================================");
        System.out.println("                    KOHISOP");
        System.out.println("                STRUK PEMESANAN");
        System.out.println("================================================");

        for (int i = 0; i < 5; i++) {
            if (jumlah[i] > 0) {
                int idx = cariIndeks(kode[i]);

                String nama = menu[idx][1];
                int harga = hargaMenu[idx];

                int totalJenis = harga * jumlah[i];
                double nilaiPajak = hitungPajak(harga, jumlah[i]);

                int persen = 0;
                if (harga < 50) {
                    persen = 0;
                } else if (harga <= 55) {
                    persen = 8;
                } else {
                    persen = 11;
                }

                totalTanpaPajak = totalTanpaPajak + totalJenis;
                totalDenganPajak = totalDenganPajak + totalJenis + nilaiPajak;

                String kiri1 = "    " + jumlah[i] + " x Rp" + String.format("%.2f", (double) harga);
                String kanan1 = "Rp" + String.format("%.2f", (double) totalJenis);
                String kiri2 = "    Pajak " + persen + "%";
                String kanan2 = "Rp" + String.format("%.2f", nilaiPajak);

                System.out.println(kode[i] + "   " + nama);
                System.out.printf("%-28s%20s\n", kiri1, kanan1);
                System.out.printf("%-28s%20s\n", kiri2, kanan2);
            }
        }

        System.out.println("------------------------------------------------");
        System.out.printf("%-28s%20s\n", "Total Tanpa Pajak", "Rp" + String.format("%.2f", totalTanpaPajak));
        System.out.printf("%-28s%20s\n", "Total Dengan Pajak", "Rp" + String.format("%.2f", totalDenganPajak));
        System.out.println("------------------------------------------------");
    }

    static double hitungPajak(int harga, int jumlah) {
        int totalHarga = harga * jumlah;
        double totalPajak = 0;

        if (harga < 50) {
            totalPajak = 0;
        } else if (harga <= 55) {
            totalPajak = (totalHarga * 8) / 100.0;
        } else {
            totalPajak = (totalHarga * 11) / 100.0;
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

        for (int i = 0; i < 5; i++) {
            if (jumlah[i] > 0) {
                int idx = cariIndeks(kode[i]);

                int harga = hargaMenu[idx];

                int totalJenis = harga * jumlah[i];

                double nilaiPajak = hitungPajak(harga, jumlah[i]);
                double subtotal = totalJenis + nilaiPajak;

                totalIDR = totalIDR + subtotal;
            }
        }

        double totalBayar = konversiMataUang(totalIDR, mataUang);

        System.out.printf("%-28s%20s\n", "Mata Uang", mataUang.toUpperCase());
        System.out.printf("%-28s%20s\n", "Total Bayar", String.format("%.2f", totalBayar) + " " + mataUang.toUpperCase());
        System.out.println("================================================");
        System.out.println("                  TERIMA KASIH");
        System.out.println("================================================");
    }
}