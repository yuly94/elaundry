package com.yuly.elaundry.app;

import com.android.volley.toolbox.StringRequest;

public class AppConfig {

	private static String URL = "http://yuly.besaba.com";

	private static String API = "/elaundry/v1/index.php/";

	public static String LINKAPI = URL+API;

	// Server user login url
	public static String URL_LOGIN = LINKAPI+"login";

	// Server user register url
	public static String URL_REGISTER = LINKAPI+"register";

	// Server user request password reset
	public static String URL_FORGOT = LINKAPI+"resetpassreq";

	// Server user reset password
	public static String URL_RESET = LINKAPI+"resetpass";

	// Server user change password
	public static String URL_CHANGEPASS = LINKAPI+"changepass";

	// Server user change password
	public static String URL_CHANGEPROFILE = LINKAPI+"updateProfile";

	// Server user register url
	public static String URL_TASK = LINKAPI+"tasks";

	// Server user register url
	public static String URL_MAPS = LINKAPI+"maps";

	// Server user register url
	public static String URL_PAKET= LINKAPI+"paket";

	// Server user register url
	public static String URL_PESANAN= LINKAPI+"pesanan";

	// Server user register url
	public static String URL_TEMPAT= LINKAPI+"tempat";

	// Server user register url
	public static String URL_TRANSAKSI= LINKAPI+"transaksi";
}
