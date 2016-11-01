<?php

namespace MyConn;

class Helper{

    public static function response($success, $data=array(), 
            $message='', 
            $status=0, 
            $contentType='application/json'){

        if($contentType=='application/json'){

            $app = \Slim\Slim::getInstance();
            $app->response->headers->set('Content-Type', 'application/json');
            echo json_encode(array('success'=>$success, 
                'data'=>$data, 'message'=>$message, 'status'=>$status));
        }
    }

    public static function checkSecret(){

        $app = \Slim\Slim::getInstance();

        if(!$app->request->isPost()){

            self::response(false, null, 'Bad request, expected POST method', 400);
            return false;
        }

        $secret = $app->request->post('secret');

        if(empty($secret) || !is_string($secret)){

            self::response(false, null, 'Bad request, secret key required', 400);
            return false;
        }

        $account = \AccountModel::getAccountByAPISecretKey($secret);

        if(empty($account)){

            self::response(false, null, 'Bad request, wrong secret key', 400);
            return false;
        }

        return $account;
    }
}
