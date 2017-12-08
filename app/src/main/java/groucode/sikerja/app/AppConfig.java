package groucode.sikerja.app;

/**
 * Created by User on 09/11/2017.
 */

public class AppConfig {

    // Server user login url
    public static String URL_LOGIN = "https://eloker-kayongutara.com/raditya/SikerjaAPI/login.php";

    // Server user register url
    public static String URL_REGISTER = "https://eloker-kayongutara.com/raditya/SikerjaAPI/register.php";

    public static String URL_REGISTER_KARTU = "https://eloker-kayongutara.com/raditya/SikerjaAPI/registerKartu.php";

    public static String URL_UPDATE_TOKEN = "https://eloker-kayongutara.com/raditya/SikerjaAPI/updateToken.php";

    public static String URL_GET_LOWONGAN = "https://eloker-kayongutara.com/raditya/SikerjaAPI/getLowongan.php";

    public static String URL_GETDetailLowongan = "https://eloker-kayongutara.com/raditya/SikerjaAPI/getDetailLowongan.php?id=";

    public static String URL_GET_PELATIHAN = "https://eloker-kayongutara.com/raditya/SikerjaAPI/getPelatihan.php";

    public static String URL_GETDetailPelatihan = "https://eloker-kayongutara.com/raditya/SikerjaAPI/getDetailPelatihan.php?id=";


//get lowongan kerja
    public static String[] id;
    public static String[] bataswaktu;
    public static String[] logoperusahaan;
    public static String[] namaperusahaan;
    public static String[] jabatan;
    public static String[] lokasi;
    public static String[] deskripsi;
    public static String[] persyaratan;
    public static String[] caradaftar;
    public static String[] alamatkantor;
    public static String[] website;
    public static String[] jumlahkaryawan;
    public static String[] deskripsiperusahaan;


    //get pelatihan
    public static String[] namapelatihan;
    public static String[] logopelatihan;
    public static String[] bidang;
    public static String[] penyelenggara;
    public static String[] waktu;
    public static String[] tempat;
    public static String[] kuota;
    public static String[] caramendaftar;
    public static String[] biaya;
    public static String[] fasilitas;



    public AppConfig(int i) {
        //getLowongan
        id = new String[i];
        bataswaktu = new String[i];
        logoperusahaan = new String[i];
        namaperusahaan = new String[i];
        jabatan = new String[i];
        lokasi = new String[i];
        deskripsi = new String[i];
        persyaratan = new String[i];
        caradaftar = new String[i];
        alamatkantor = new String[i];
        website = new String[i];
        jumlahkaryawan = new String[i];
        deskripsiperusahaan = new String[i];

        //getPelatihan

        namapelatihan = new String[i];
        logopelatihan = new String[i];
        bidang = new String[i];
        penyelenggara = new String[i];
        waktu = new String[i];
        tempat = new String[i];
        kuota = new String[i];
        caramendaftar = new String[i];
        biaya = new String[i];
        fasilitas = new String[i];


    }

    public static final String TAG_ID = "id";
    public static final String TAG_LOGOPERUSAHAAN = "logoperusahaan";
    public static final String TAG_BATASWAKTU = "bataswaktu";
    public static final String TAG_NAMAPERUSAHAAN = "namaperusahaan";
    public static final String TAG_JABATAN = "jabatan";
    public static final String TAG_LOKASI = "lokasi";
    public static final String TAG_DESKRIPSI = "deskripsi";
    public static final String TAG_PERSYARATAN = "persyaratan";
    public static final String TAG_CARADAFTAR = "caradaftar";
    public static final String TAG_ALAMATKANTOR = "alamatkantor";
    public static final String TAG_WEBSITE = "website";
    public static final String TAG_JUMLAHKARYAWAN = "jumlahkaryawan";
    public static final String TAG_DESKRIPSIPERUSAHAAN = "deskripsiperusahaan";

    public static final String TAG_NAMAPELATIHAN = "namapelatihan";
    public static final String TAG_LOGOPELATIHAN = "logopelatihan";
    public static final String TAG_BIDANG = "bidang";
    public static final String TAG_PENYELENGGARA = "penyelenggara";
    public static final String TAG_WAKTU= "waktu";
    public static final String TAG_TEMPAT = "tempat";
    public static final String TAG_KUOTA = "kuota";
    public static final String TAG_CARAMENDAFTAR = "caramendaftar";
    public static final String TAG_BIAYA = "biaya";
    public static final String TAG_FASILITAS = "fasilitas";


}
