<?php

namespace Libs;

class Libs{

    public static function autoload($className){

        $baseDir = __DIR__.DIRECTORY_SEPARATOR;

        $className = ltrim($className, '\\');
        $fileName  = $baseDir;

        if ($lastNsPos = strripos($className, '\\')) {
            $namespace = substr($className, 0, $lastNsPos);
            $className = substr($className, $lastNsPos + 1);
            $fileName  .= str_replace('\\', DIRECTORY_SEPARATOR, $namespace) . DIRECTORY_SEPARATOR;
        }
        $fileName .= str_replace('_', DIRECTORY_SEPARATOR, $className) . '.php';

        if (file_exists($fileName)) {
            require_once $fileName;
        }
    }

    public static function registerAutoloader(){
        spl_autoload_register(__NAMESPACE__ . "\\Package::autoload");
    }
}
