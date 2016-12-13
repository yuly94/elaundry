package com.yuly.elaundry.konsumen.app;

public class AppConfig {

	private static String URL = "http://elaundry.pe.hu/";

	private static String API = "konsumen/";

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

	//public static String URL_ALAMAT = LINKAPI+"profile/update/";

	// Server user change password
	public static String URL_CHANGEPROFILE = LINKAPI+"profile/update/";

	// Server user register url
	public static String URL_TASK = LINKAPI+"tasks";

	// Server user register url
	public static String URL_MAPS = LINKAPI+"peta/";

	// Server user register url
	public static String URL_PAKET= LINKAPI+"laundry/paket/";

	// Server user register url
	public static String URL_PEMESANAN= LINKAPI+"pemesanan/";

	// Server user register url
	public static String URL_TAMBAH_PEMESANAN= LINKAPI+"pemesanan/baru/";

	// Server user register url
	public static String URL_PEMESANAN_DETAIL= LINKAPI+"pemesanan/detail/";


	// Server user register url
	public static String URL_UPDATE_PEMESANAN= LINKAPI+"pemesanan/detail/";

	// Server user register url
	public static String URL_ALAMAT= LINKAPI+"alamat/";

	// Server user register url
	public static String URL_TEMPAT= LINKAPI+"alamat/";

	// Server user register url
	public static String URL_TRANSAKSI= LINKAPI+"transaksi/";
}