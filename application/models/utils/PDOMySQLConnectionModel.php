<?php


class PDOMySQLConnectionModel{

    public static function newInstance(\Slim\Slim $app) {

        try {
            $config = $app->config('connection');
            $instance = new \PDO(
                "mysql:host={$config['mysql']['host']};dbname={$config['mysql']['database']}",
                $config['mysql']['user'],
                $config['mysql']['password'],
                $config['mysql']['options']
            );

            if(!empty($config['mysql']['execute'])){

                foreach($config['mysql']['execute'] as $sql){

                    $stmt = $instance->prepare($sql);
                    $stmt->execute();
                }
            }
        }
        catch (\PDOException $p) {

            $app->log->error('DB Connection Error '.$p);
            return $app->halt(500, $app->view()->fetch('error/db-error.php'));

            
  
        }

        return $instance;
    }
}
