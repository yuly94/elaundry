<?php
class Hash
{
	/**
	 * @param String $algo the algoritm (md5, sha1, whirtpool etc)
	 * @param String $data The data to encode
	 * @param String $salt The salt (Harus sama)
	 * @return String The hashed / salted data
	 */
	 
	public static function create($algo, $data, $salt)
	{
		$context = hash_init($algo,HASH_HMAC, $salt);
		hash_update($context, $data);
			
		return hash_final($context);
			
	}
}