package groucode.sikerja.app;

/**
 * Created by User on 09/11/2017.
 */

public class AppConfig {

    // Server user login url
    public static String URL_LOGIN = "https://trywahyudinata.web.id/raditya/SikerjaAPI/login.php";

    // Server user register url
    public static String URL_REGISTER = "https://trywahyudinata.web.id/raditya/SikerjaAPI/register.php";

    public static String URL_UPDATE_TOKEN = "https://trywahyudinata.web.id/raditya/SikerjaAPI/updateToken.php";

    public static String URL_GET_LOWONGAN = "https://trywahyudinata.web.id/raditya/SikerjaAPI/getLowongan.php";


//get lowongan kerja
    public static String[] bataswaktu;
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

    public AppConfig(int i) {
        //getPengumuman
        bataswaktu = new String[i];
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

    }

    public static final String TAG_ID = "id";
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

}
