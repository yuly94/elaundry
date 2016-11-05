<?php

namespace My;

class LogWriterModel {

   	/**
	 * @var resource
	 */
	protected $resource;

	/**
	 * @var array
	 */
	protected $settings;

	/**
	 * Converts Slim log level to Monolog log level
	 * @var array
	 */
	protected $log_level = array(
		\Slim\Log::EMERGENCY => \Monolog\Logger::EMERGENCY,
		\Slim\Log::ALERT => \Monolog\Logger::ALERT,
		\Slim\Log::CRITICAL => \Monolog\Logger::CRITICAL,
		\Slim\Log::ERROR => \Monolog\Logger::ERROR,
		\Slim\Log::WARN => \Monolog\Logger::WARNING,
		\Slim\Log::NOTICE => \Monolog\Logger::NOTICE,
		\Slim\Log::INFO => \Monolog\Logger::INFO,
		\Slim\Log::DEBUG => \Monolog\Logger::DEBUG,
	);

	/**
	 * Constructor
	 *
	 * Prepare this log writer. Available settings are:
	 *
	 * name:
	 * (string) The name for this Monolog logger
	 *
	 * handlers:
	 * (array) Array of initialized monolog handlers - eg StreamHandler
	 *
	 * processors:
	 * (array) Array of monolog processors - anonymous functions
	 *
	 * @param   array $settings
	 * @param bool $merge
	 * @return  void
	 */
	public function __construct($settings = array(), $merge = true)
	{
		//Merge user settings
	        if ($merge) {
	            $this->settings = array_merge(array(
	                'name' => 'SlimMonoLogger',
	                'handlers' => array(),
	                'processors' => array(),
	            ), $settings);
	        } else {
	            $this->settings = $settings;
	        }
	}

	/**
	 * Write to log
	 *
	 * @param   mixed $object
	 * @param   int   $level
	 * @return  void
	 */
	public function write($object, $level)
	{
		// Don't bother typesetting $object, Monolog will do this for us
		$this->get_resource()->addRecord(
			$this->get_log_level($level, \Monolog\Logger::WARNING),
			$object
		);
	}

	/**
	 * Converts Slim log level to Monolog log level
	 *
	 * @param  int $slim_log_level   Slim log level we're converting from
	 * @param  int $default_level    Monolog log level to use if $slim_log_level not found
	 * @return int                   Monolog log level
	 */
	protected function get_log_level( $slim_log_level, $default_monolog_log_level )
	{
		return isset($this->log_level[$slim_log_level]) ?
			$this->log_level[$slim_log_level] :
			$default_monolog_log_level;
	}
	
	/**
	* Returns the Logger resource
	*
	* @return [\Monolog\Logger] the Logger instance
	*/
	public function get_resource()
	{
		if ( !$this->resource )
		{
			// create a log channel
			$this->resource = new \Monolog\Logger($this->settings['name']);
			foreach ( $this->settings['handlers'] as $handler )
				$this->resource->pushHandler($handler);
			foreach ( $this->settings['processors'] as $processor )
				$this->resource->pushProcessor($processor);
		}		
		return $this->resource;
	}
    
    
}

