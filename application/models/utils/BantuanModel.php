<?php


class BantuanModel{
    
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
    
    
/**
 * Verifying required params posted or not
 */
function verifyRequiredParams($required_fields) {
    $error = false;
    $error_fields = "";
    $request_params = array();
    $request_params = $_REQUEST;
    // Handling PUT request params
    if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
        $app = \Slim\Slim::getInstance();
        parse_str($app->request()->getBody(), $request_params);
    }
    foreach ($required_fields as $field) {
        if (!isset($request_params[$field]) || strlen(trim($request_params[$field])) <= 0) {
            $error = true;
            $error_fields .= $field . ', ';
        }
    }

    if ($error) {
        // Required field(s) are missing or empty
        // echo error json and stop the app
        $response = array();
        $app = \Slim\Slim::getInstance();
        $response["error"] = true;
        $response["message"] = 'Required field(s) ' . substr($error_fields, 0, -2) . ' is missing or empty';
        BantuanModel::echoRespnse(400, $response);
        $app->stop();
    }
}


/**
 * Adding Middle Layer to authenticate every request
 * Checking if the request has valid api key in the 'Authorization' header
 */
function authenticate(\Slim\Route $route) {
    // Getting request headers
    $headers = apache_request_headers();
    $response = array();
    $app = \Slim\Slim::getInstance();

    // Verifying Authorization Header
    if (isset($headers['Authorization'])) {
        $db = new DbHandlerAuth();

        // get the api key
        $api_key = $headers['Authorization'];
        // validating api key
        if (!$db->isValidApiKey($api_key)) {
            // api key is not present in users table
            $response["error"] = true;
            $response["message"] = "Access Denied. Invalid Api key";
            Helper::echoRespnse(401, $response);
            $app->stop();
        } else {
            global $id;
            global $konsumen_id;
	    global $api_email;
            

            // get user primary key id
            $api_email = $db->getKonsumenEmail($api_key);

            // get user primary key id
            $konsumen_id = $db->getKonsumenUnikId($api_key);

            // get user primary key id
            $id = $db->getKonsumenId($api_key);
            

         
			}
		} else {
        // api key is missing in header
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        Helper::echoRespnse(401, $response);
        $app->stop();
    }
}


}
