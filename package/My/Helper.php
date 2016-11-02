<?php

namespace My;

class Helper{

    public static function old_response($success, $data=array(), $message='', $status=0, $contentType='application/json'){

        if($contentType=='application/json'){

            $app = \Slim\Slim::getInstance();
            $app->response->headers->set('Content-Type', 'application/json');
            echo json_encode(array('success'=>$success, 'data'=>$data, 'message'=>$message, 'status'=>$status));
        }
    }
    
    public static function response(
            $success, 
            $data=array(), 
            $message='', 
            $status=0, 
            $contentType='application/json'){

        if($contentType=='application/json'){

            $app = \Slim\Slim::getInstance();
            $app->response->headers->set(
                    'Content-Type',
                    'application/json');
            echo json_encode(array(
                'success'=>$success, 
                'data'=>$data, 'message'=>$message,
                'status'=>$status));
        }
    }
    
    /**
 * Echoing json response to client
 * @param String $status_code Http response code
 * @param Int $response Json response
 */
function echoRespnse($status_code, $response) {
    $app = \Slim\Slim::getInstance();
    // Http response code
    $app->status($status_code);

    // setting response content type to json
    $app->contentType("application/json; charset=utf-8");

    echo json_encode($response);
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
