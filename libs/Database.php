<?php
class Database extends PDO {
	public function __construct($DB_TYPE, $DB_HOST, $DB_NAME, $DB_USER, $DB_PASS) {
		 parent::__construct($DB_TYPE.':host='.$DB_HOST.';dbname='.$DB_NAME, $DB_USER, $DB_PASS);
		 parent::setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		 parent::setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
	}
	
	
	/**
	 * select
	 * @param string $sql An SQL string
	 * @param array $array Paramters to bind
	 * @param constant $fetchMode A PDO Fetch mode
	 * @return mixed
	 */
	public function select($sql, $array = array(), $fetchMode = PDO::FETCH_ASSOC)
	{
		try {
		$sth = $this->prepare($sql);
		foreach ($array as $key => $value) {
			$sth->bindValue("$key", $value);
		}
		
		$sth->execute();
		return $sth->fetchAll($fetchMode);
		
		} catch(PDOException $e) {
			throw $e;
		}
	}
	
	/**
	 * select
	 * @param string $sql An SQL string
	 * @param array $array Paramters to bind
	 * @param constant $fetchMode A PDO Fetch mode
	 * @return mixed
	 */
	public function selectSingle($sql, $array = array(), $fetchMode = PDO::FETCH_ASSOC)
	{
		try {
			$sth = $this->prepare($sql);
			foreach ($array as $key => $value) {
				$sth->bindValue("$key", $value);
			}
	
			$sth->execute();
			return $sth->fetch($fetchMode);
	
		} catch(PDOException $e) {
			throw $e;
		}
	}
 
	/**
	 * @param string $table nama tabel yang akan dimasuki
	 * @param string $data data merupakan dalam bentuk array
	 */
	
	public function insert($table, $data) {
		
		
		ksort($data);
		
		$fieldNames = implode('`, `', array_keys($data));
		$fieldValues = ':' . implode(', :', array_keys($data));
		
		$sth = $this->prepare("INSERT INTO $table (`$fieldNames`) VALUES ($fieldValues)");
		
		foreach ($data as $key => $value) {
			$sth->bindValue(":$key", $value);
		}
		
		$sth->execute();
		
		/* ksort($data);
		
		$fieldNames = implode(', ', array_key($data));
		$fieldValues = ':' . implode(', :', array_keys($data));
		
		echo "INSERT INTO $table
				('$fieldNames') VALUES ($fieldValues)
				";
		
		die;
		$sth = $this->prepare("INSERT INTO $table
				('$fieldNames') VALUES ($fieldValues)
				");
		 
		
		foreach ($data as $key => $value){
			$sth->bindValue(":$key", $value);
		}
		$sth->execute(); */
	}
	
	/**
	 * @param unknown $table
	 * @param unknown $data
	 * @param unknown $where
	 */
	
	public function update($table,$data,$where) {
		
		ksort($data);
		
		$fieldDetails = NULL;
		foreach($data as $key=> $value) {
			$fieldDetails .= "`$key`=:$key,";
		}
		$fieldDetails = rtrim($fieldDetails, ',');
		
		$sth = $this->prepare("UPDATE $table SET $fieldDetails WHERE $where");
		
		foreach ($data as $key => $value) {
			$sth->bindValue(":$key", $value);
		}
		
		$sth->execute();
		
		/* ksort($data);
		$fieldDetails =null;
		foreach ($data as $key=>$value){
			$fieldDetails .= "'$key=:$key,";
		}
		
		$fieldDetails = rtrim($fieldDetails, ',');
		//echo $fieldDetails;
		 
		// UPDATE table SET item1 = a, item2 = b WHERE something = c;
		
		$sth = $this->prepare("UPDATE $table SET
				$fieldDetails WHERE $where
				");
		
		foreach ($data as $key => $value){
			$sth->bindValue(":$key", $value);
		}
		$sth->execute(); */
	}
	
	
	/**
	 * delete
	 * 
	 * @param string $table
	 * @param string $where
	 * @param integer $limit
	 * @return integer Affected Rows
	 */
	public function delete($table, $where, $limit = 1)
        {
          return $this->exec("DELETE FROM $table WHERE $where LIMIT $limit");
        
        // echo "DELETE FROM $table WHERE $where LIMIT $limit";
        
        // die;
        }
	
}
	

