package com.yuly.elaundry.app;

public class AppConfig {

	private static String URL = "http://yuly.besaba.com";
	// Server user login url
	public static String URL_LOGIN = URL+"/elaundry/v1/index.php/login";

	// Server user register url
	public static String URL_REGISTER = URL+"/elaundry/v1/index.php/register";

	// Server user request password reset
	public static String URL_FORGOT = URL+"/elaundry/v1/index.php/resetpassreq";

	// Server user reset password
	public static String URL_RESET = URL+"/elaundry/v1/index.php/resetpass";

	// Server user change password
	public static String URL_CHANGEPASS = URL+"/elaundry/v1/index.php/changepass";

	// Server user register url
	public static String URL_TASK = URL+"/elaundry/v1/index.php/tasks";

	// Server user register url
	public static String URL_MAPS = URL+"/elaundry/v1/index.php/maps";

	// Server user register url
	public static String URL_PAKET= URL+"/elaundry/v1/index.php/paket";

	// Server user register url
	public static String URL_PESANAN= URL+"/elaundry/v1/index.php/pesanan";

	// Server user register url
	public static String URL_TEMPAT= URL+"/elaundry/v1/index.php/tempat";

	// Server user register url
	public static String URL_TRANSAKSI= URL+"/elaundry/v1/index.php/transaksi";
}
