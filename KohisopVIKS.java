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
            } else if (mataUang.equalsIgnoreCase("USD") == true) {
                break;
            } else if (mataUang.equalsIgnoreCase("JPY") == true) {
                break;
            } else if (mataUang.equalsIgnoreCase("MYR") == true) {
                break;
            } else if (mataUang.equalsIgnoreCase("EUR") == true) {
                break;
            } else {
                System.out.println("Mata uang tidak Valid!");
            }
        }

        DetailPajak(kode, jumlah, mataUang);

        sc.close();
    }

    static void Menu() {
        System.out.println("===================================================");
        System.out.println("                DAFTAR MENU KOHISOP");
        System.out.println("===================================================");
        System.out.printf("%-6s %-33s %s\n", "Kode", "Menu Minuman", "Harga (Rp)");
        System.out.println("---------------------------------------------------");

        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%-6s %-33s %d\n", menu[i][0], menu[i][1], hargaMenu[i]);
        }

        System.out.println("===================================================");
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

                String temp = sc.nextLine();

                if (temp.equalsIgnoreCase("s") == true) {
                    inputJumlah = 0;
                } else {
                    if (temp.equals("0") == true) {
                        inputJumlah = 0;
                    } else if (temp.equals("1") == true) {
                        inputJumlah = 1;
                    } else if (temp.equals("2") == true) {
                        inputJumlah = 2;
                    } else if (temp.equals("3") == true) {
                        inputJumlah = 3;
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

            kode[pesananKe] = inputKode;
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
        System.out.println("                STRUK PEMBAYARAN");
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

                System.out.println(kode[i].toUpperCase() + "  " + nama);
                System.out.printf("    %-24s%20s\n", jumlah[i] + " x Rp" + formatRupiah(harga * 1000), "Rp" + formatRupiah(totalJenis * 1000));
                System.out.printf("    %-24s%20s\n", "Pajak " + persen + "%", "Rp" + formatRupiah((int)(nilaiPajak * 1000)));
            }
        }

        System.out.println("------------------------------------------------");
        System.out.printf("%-28s%20s\n", "Total tanpa pajak", "Rp" + formatRupiah((int)(totalTanpaPajak * 1000)));
        System.out.printf("%-28s%20s\n", "Total pajak", "Rp" + formatRupiah((int)Math.round((totalDenganPajak - totalTanpaPajak) * 1000)));
        System.out.printf("%-28s%20s\n", "Total dengan pajak", "Rp" + formatRupiah((int)Math.round(totalDenganPajak * 1000)));
        System.out.println("================================================");
    }

    static String formatRupiah(int angka) {
        String hasil = "";
        int hitung = 0;

        if (angka == 0) {
            return "0";
        }

        while (angka > 0) {
            if (hitung == 3) {
                hasil = "." + hasil;
                hitung = 0;
            }

            hasil = (angka % 10) + hasil;
            angka = angka / 10;
            hitung++;
        }

        return hasil;
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

        System.out.printf("%-28s%20s\n", "Mata uang", mataUang.toUpperCase());
        System.out.printf("%-28s%20s\n", "TOTAL BAYAR", String.format(java.util.Locale.US, "%.2f", totalBayar) + " " + mataUang.toUpperCase());
        System.out.println("================================================");
        System.out.println("                  TERIMA KASIH");
        System.out.println("             SILAKAN DATANG KEMBALI");
        System.out.println("================================================");
    }
}