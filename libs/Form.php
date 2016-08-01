<?php

/**
 * 
 * @author yuly
 * @2016
 * Based on tutorial from Jream.com
 *	- POST to PHP
 *  - Sanitize
 *  - Validate
 *  - Return Data
 *  - Write to Database
 *  
 */

require 'Form/Val.php';

class Form {
	
	/** @var array $_currentItem : Secara langsung memposting data yang di posting*/
	private $_currentItem = null;
	
	/** @var array $_postData : Menyimpan data yang di posting*/
	private $_postData = array();
	
	/** @var object $_val : Falidator Object*/
	private $_val = array();
	
	/** @var array $_error Holds the current forms errors */
	private $_error = array();
	
	
	/**
	 * The constructor
	 * __ construct - Instantiate the validator class
	 */
	public function __construct() 
	{
		$this->_val = new Val();
	}
	
	/**
	 * post : untuk intuk menjalankan fungsi post
	 * 
	 * @param string $field - Form HTML yang akan di posting
	 */
	public function post($field)
	{
		$this->_postData[$field] = $_POST[$field];
		$this->_currentItem = $field;
		
		return $this;
	}
	
	/**
	 * fetsh => Return the posted data
	 * 
	 * @param string $fieldName
	 */
	public function fetch($fieldName = false)
	{
		if ($fieldName) 
		{
			if (isset($this->_postData[$fieldName]))
			return $this->_postData[$fieldName];
			
			else
			return false;
		} 
		else 
		{
			return $this->_postData;
		}
		
	}
	
	/**
	 *  Val - This is to validate
	 *  
	 *  @param string $typeOfValidator : Method from the from/val
	 *  @param string $arg A property to validate against
	 */
	public function val($typeOfValidator, $arg = null)
	{
		if ($arg == null)
		$error = $this->_val->{$typeOfValidator}($this->_postData[$this->_currentItem]);
		else
		$error = $this->_val->{$typeOfValidator}($this->_postData[$this->_currentItem], $arg);
		
		if ($error)
		$this->_error[$this->_currentItem] = $error;
		
		return $this;
	}
	
	
	/**
	 * submit - Handles the form and throws an exception upon error
	 * Menghandle form dan melemparkan exception jika ternadi kesalahan
	 * 
	 * @throws Exception
	 * @return boolean
	 */
	public function submit()
	{
		if (empty($this->_error)) 
		{
			return true;
		} 
		else 
		{
			$str = '';
			foreach ($this->_error as $key => $value)
			{
				$str .= $key . ' => ' . $value . "\n";
			}
			throw new Exception($str);
		}
	}

	
}