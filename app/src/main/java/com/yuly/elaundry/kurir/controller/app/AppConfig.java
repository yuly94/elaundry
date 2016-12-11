package com.yuly.elaundry.kurir.controller.app;

public class AppConfig {

	public static final String TAG = "elaundry-apps";

	private static String URL = "http://elaundry.pe.hu/";

	private static String API = "kurir/";

	public static String LINKAPI = URL+API;

	// Server user login url
	public static String URL_LOGIN = LINKAPI+"login/";

	// Server user register url
	public static String URL_REGISTRASI = LINKAPI+"registrasi/";

	// Server user request password reset
	public static String URL_FORGOT = LINKAPI+"password/lupa/";

	// Server user reset password
	public static String URL_RESET = LINKAPI+"password/reset/";

	// Server user change password
	public static String URL_GANTI_PASSWORD = LINKAPI+"password/ganti/";

	public static String URL_ALAMAT = LINKAPI+"profile/update/";

	// Server user change password
	public static String URL_UPDATE_PROFILE = LINKAPI+"profile/update/";

	// Server user register url
	public static String URL_TASK = LINKAPI+"tasks";

	// Server user register url
	public static String URL_MAPS = LINKAPI+"peta/";

	// Server user register url
	public static String URL_PAKET= LINKAPI+"paket/";

	// Server user register url
	public static String URL_PEMESANAN= LINKAPI+"pemesanan/";

	// Server user register url
	public static String URL_PEMESANAN_DETAIL= LINKAPI+"pemesanan/detail/";

	// Server user register url
	public static String URL_PENGAMBILAN_PEMESANAN= LINKAPI+"pemesanan/pengambilan/";

	// Server user register url
	public static String URL_PENGAMBILAN_PEMESANAN_DETAIL= LINKAPI+"pemesanan/pengambilan/detail/";


	// Server user register url
	public static String URL_UPDATE_PEMESANAN= LINKAPI+"pemesanan/updatestatus/";

	// Server user register url
	public static String URL_TEMPAT= LINKAPI+"alamat/";

	// Server user register url
	public static String URL_TRANSAKSI= LINKAPI+"transaksi";

	public static String NAMA_PETA = "indonesia_jawatimur_kediringanjuk";
	public static String URL_PETA = "http://wachid.pe.hu/elaundrymaps/maps/";


}
