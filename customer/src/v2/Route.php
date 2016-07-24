<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// User id from db - Global Variable
$user_id = NULL;

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
            echoRespnse(401, $response);
            $app->stop();
        } else {
            global $id;
            global $konsumen_id;
            
            // get user primary key id
            $id = $db->getKonsumenId($api_key);
            
            // get user primary key id
            $konsumen_id = $db->getKonsumenUnikId($api_key);
            
            
        }
    } else {
        // api key is missing in header
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        echoRespnse(400, $response);
        $app->stop();
    }
}

/**
 * ----------- METHODS WITHOUT AUTHENTICATION ---------------------------------
 */
/**
 * User Registration
 * url - /register
 * method - POST
 * params - name, email, password
 */
$app->post('/register', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('nama', 'alamat','nohp','email', 'password'));

            $response = array();

            // reading post params
            $nama = $app->request->post('nama');
            $alamat = $app->request->post('alamat');
            $nohp = $app->request->post('nohp');
            $email = $app->request->post('email');
            $password = $app->request->post('password');

            // validating email address
            validateEmail($email);

            $db = new DbHandlerAuth();
            $res = $db->createUser($nama, $alamat, $nohp, $email, $password);

            if ($res == USER_CREATED_SUCCESSFULLY) {

                $user = $db->getUserByEmail($email);
                 // use is found
                    $response["error"] = FALSE;
                    $response["uid"] = $user["uid"];
                    $response["user"]["nama"] = $user["nama"];
                    $response["user"]["alamat"] = $user["alamat"];
                    $response["user"]["nohp"] = $user["nohp"];
                    $response["user"]["email"] = $user["email"];
                    $response["user"]["api_key"] = $user["api_key"];
                    $response["user"]["status"] = $user["status"];
                    $response["user"]["created_at"] = $user["created_at"];
                    $response["user"]["updated_at"] = $user["updated_at"];
                
            } else if ($res == USER_CREATE_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registereing";
            } else if ($res == USER_ALREADY_EXISTED) {
                $response["error"] = true;
                $response["message"] = "Sorry, this email already existed";
            } 
            // echo json response
            echoRespnse(201, $response);
        });

/**
 * User Login
 * url - /login
 * method - POST
 * params - email, password
 */
$app->post('/login', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('email', 'password'));

            // reading post params
            $email = $app->request()->post('email');
            $password = $app->request()->post('password');
            $response = array();

            $db = new DbHandlerAuth();
            
            // validating email address
            validateEmail($email);
            	
            // check brute force
            if ( $db->checkbrute($email) =="6"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp exceed, please try again in 60 menit future';
       
			}
			
			else {
            

            // check for correct email and password
            if ($db->checkLogin($email, $password)) {

                // get the user by email
                $user = $db->getUserByEmail($email);

                if ($user != NULL) {
                    // use is found
                    $response["error"] = FALSE;
                    $response["uid"] = $user["uid"];
                    $response["user"]["nama"] = $user["nama"];
					$response["user"]["alamat"] = $user["alamat"];
					$response["user"]["nohp"] = $user["nohp"];
                    $response["user"]["email"] = $user["email"];
                    $response["user"]["api_key"] = $user["api_key"];
                    $response["user"]["status"] = $user["status"];
                    $response["user"]["created_at"] = $user["created_at"];
                    $response["user"]["updated_at"] = $user["updated_at"];
        
                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "An error occurred. Please try again";
                }
            } else {
                // user credentials are wrong
				if ($db->loginFailed($email))
				{		
			// check brute force
            if ( $db->checkAttemp($email) =="6"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp Exceed, please try again in 60 menit future';
             
			} else // check brute force
            if ( $db->checkAttemp($email) =="5"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 1, please be carefull';
             
			} else // check brute force
            if ( $db->checkAttemp($email) =="4"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 2, please try again';
             
			} else // check brute force
            if ( $db->checkAttemp($email) =="3"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 3, please try again';
             
			} else  // check brute force
            if ( $db->checkAttemp($email) =="2"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 4, please try again';
             
			} else // check brute force
            if ( $db->checkAttemp($email) =="1"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 5, please try again';
			}			
				} else {
				$response['error'] = true;
                $response['message'] = 'Login failed. Incorrect credentials';	
					
				};
        
            }    
			}			
             
              echoRespnse(200, $response);
        });

/*
 * ------------------------ METHODS WITH AUTHENTICATION ------------------------
 */

/**
 * Listing all tasks of particual user
 * method GET
 * url /tasks          
 */
$app->get('/tasks', 'authenticate', function() {
            global $user_id;
            $response = array();
            $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllUserTasks($user_id);
  
  
            if ($result != NULL) {
            $items = array();

            foreach ($result as $rez) {
            $response["error"] = false;
            $response["task"]["id"] = $rez["id"];
            $response["task"]["task"] = $rez["task"];
            $response["task"]["status"] = $rez["status"];
             $response["task"]["createdAt"] = $rez["created_at"];

        $items[] = $response;
    }

        echoRespnse(200, $items);
    }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }
    });

/**
 * Listing all maps of particual user
 * method GET
 * url /tasks          
 */
$app->get('/maps', 'authenticate', function() {
           
            $maps_status = "1";
            $response = array();
            $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllMaps($maps_status);
  
  
            if ($result != NULL) {
            $items = array();
            $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["maps"]["maps_id"] = $rez["maps_id"];
            $response["maps"]["name"] = $rez["name"];
            $response["maps"]["address"] = $rez["address"];
            $response["maps"]["city"] = $rez["city"];
            $response["maps"]["province"] = $rez["province"];
            $response["maps"]["latitude"] = $rez["latitude"];
            $response["maps"]["longitude"] = $rez["longitude"];
            
            $items[] = $response;
    }

        echoRespnse(200, $items);
    }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }
    });


    
    
    /**
 * Listing all tempat of particual user
 * method GET
 * url /tasks          
 */
$app->get('/tempat', 'authenticate', function() {
           
            global $konsumen_id;
            $response = array();
            $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllTempat($konsumen_id);
  
  
            if ($result != NULL) {
            $items = array();
            $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["tempat"]["id"] = $rez["tempat_id"];
            $response["tempat"]["konsumen_id"] = $rez["konsumen_id"];
            $response["tempat"]["nama"] = $rez["tempat_nama"];
            $response["tempat"]["alamat"] = $rez["tempat_alamat"];
            $response["tempat"]["kota"] = $rez["tempat_kota"];
            $response["tempat"]["provinsi"] = $rez["tempat_provinsi"];
            $response["tempat"]["latitude"] = $rez["tempat_latitude"];
            $response["tempat"]["longitude"] = $rez["tempat_longitude"];
            $response["tempat"]["created"] = $rez["tempat_created"];
            $response["tempat"]["updated"] = $rez["tempat_updated"];
            
            $items[] = $response;
    }

        echoRespnse(200, $items);
    }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }
    });

        
 
    
    
    /**
 * Listing all pesanan of particual user
 * method GET
 * url /tasks          
 */
$app->get('/transaksi', 'authenticate', function() {
           
            global $konsumen_id; 
            $response = array();  
            $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllTransaksi($konsumen_id);
            
            if ($result != NULL) {  $items = array(); $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["transaksi"]["transaksi_id"] = $rez["transaksi_id"];
            $response["transaksi"]["konsumen_id"] = $rez["konsumen_id"];
            $response["transaksi"]["pemesanan_id"] = $rez["pemesanan_id"];
            $response["transaksi"]["kurir_pengambil_id"] = $rez["kurir_pengambil_id"];
            $response["transaksi"]["kurir_pengantar_id"] = $rez["kurir_pengantar_id"];
            $response["transaksi"]["tempat_id"] = $rez["tempat_id"]; 
            $response["transaksi"]["pembayaran_id"] = $rez["pembayaran_id"];
            $response["transaksi"]["tanggal_transaksi"] = $rez["tanggal_transaksi"];           
            $items[] = $response;    
            }

      
            
        echoRespnse(200, $items);}       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }
    });

    
        
    
    
    /**
 * Listing all pesanan of particual user
 * method GET
 * url /tasks          
 */
$app->get('/pesanan', 'authenticate', function() {
           
            global $konsumen_id; $response = array();  $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllPesanan($konsumen_id);
  
  
            if ($result != NULL) { $items = array(); $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["pesanan"]["pesanan_id"] = $rez["pesanan_id"];
            $response["pesanan"]["konsumen_id"] = $rez["konsumen_id"];
            $response["pesanan"]["kurir_pengambil_id"] = $rez["kurir_pengambil_id"];
            $response["pesanan"]["kurir_pengantar_id"] = $rez["kurir_pengantar_id"];
            $response["pesanan"]["lokasi_id"] = $rez["lokasi_id"];
            $response["pesanan"]["paket_id"] = $rez["paket_id"];
            $response["pesanan"]["tanggal_pemesanan"] = $rez["tanggal_pemesanan"];
            $response["pesanan"]["tanggal_pengantaran"] = $rez["tanggal_pengantaran"];
            $response["pesanan"]["status_cucian"] = $rez["status_cucian"];
            $response["pesanan"]["status_pengantaran"] = $rez["status_pengantaran"];
            
            $items[] = $response;    }

        echoRespnse(200, $items);  }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }    });

    
    
    
    /**
 * Listing all maps of particual user
 * method GET
 * url /tasks          
 */
$app->get('/paket', 'authenticate', function() {
           
            $paket_status = "1";
            $response = array();
            $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllPaket($paket_status);
  
  
            if ($result != NULL) {
            $items = array();
            $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["paket"]["id"] = $rez["paket_id"];
            $response["paket"]["nama"] = $rez["paket_nama"];
            $response["paket"]["harga"] = $rez["paket_harga"];
            $response["paket"]["keterangan"] = $rez["paket_keterangan"];
            $response["paket"]["status"] = $rez["paket_status"];
        
            
            $items[] = $response;
    }

        echoRespnse(200, $items);
    }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }
    });


    
    
    
/**
 * Listing single task of particual user
 * method GET
 * url /tasks/:id
 * Will return 404 if the task doesn't belongs to user
 */
$app->get('/tasks/:id', 'authenticate', function($task_id) {
            global $user_id;
            $response = array();
            $dbt = new DbHandlerTasks();

            // fetch task
            $result = $dbt->getTask($task_id, $user_id);

            if ($result != NULL) {
                $response["error"] = false;
                $response["id"] = $result["id"];
                $response["task"] = $result["task"];
                $response["status"] = $result["status"];
                $response["createdAt"] = $result["created_at"];
                echoRespnse(200, $response);
            } else {
                $response["error"] = true;
                $response["message"] = "The requested resource doesn't exists";
                echoRespnse(404, $response);
            }
        });

/**
 * Creating new task in db
 * method POST
 * params - name
 * url - /tasks/
 */
        
$app->post('/tasks', 'authenticate', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('task'));

            $response = array();
            $task = $app->request->post('task');

            global $user_id;
            $dbt = new DbHandlerTasks();

            // creating new task
            $task_id = $dbt->createTask($user_id, $task);

            if ($task_id != NULL) {
                $response["error"] = false;
                $response["message"] = "Task created successfully";
                $response["task_id"] = $task_id;
                echoRespnse(201, $response);
            } else {
                $response["error"] = true;
                $response["message"] = "Failed to create task. Please try again";
                echoRespnse(200, $response);
            }            
        });
        
               
$app->post('/map', 'authenticate', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('name','address','city','province','latitude','longitude'));

            $response = array();
            $name = $app->request->post('name');
            $address = $app->request->post('address');
            $city = $app->request->post('city');
            $province = $app->request->post('province');
            $latitude = $app->request->post('latitude');
            $longitude = $app->request->post('longitude');

            global $unique_id;
            $dbt = new DbHandlerTasks();

            // creating new task
            $maps_id = $dbt->createMap($unique_id, $name,$address,$city,$province, $latitude,$longitude);

            if ($maps_id != NULL) {
                $response["error"] = false;
                $response["message"] = "map created successfully";
                $response["maps_id"] = $maps_id;
                echoRespnse(201, $response);
            } else {
                $response["error"] = true;
                $response["message"] = "Failed to create map. Please try again";
                echoRespnse(200, $response);
            }            
        });


/**
 * Updating existing task
 * method PUT
 * params task, status
 * url - /tasks/:id
 */
$app->put('/tasks/:id', 'authenticate', function($task_id) use($app) {
            // check for required params
            verifyRequiredParams(array('task', 'status'));

            global $user_id;            
            $task = $app->request->put('task');
            $status = $app->request->put('status');

            $db = new DbHandler();
            $response = array();

            // updating task
            $result = $db->updateTask($user_id, $task_id, $task, $status);
            if ($result) {
                // task updated successfully
                $response["error"] = false;
                $response["message"] = "Task updated successfully";
            } else {
                // task failed to update
                $response["error"] = true;
                $response["message"] = "Task failed to update. Please try again!";
            }
            echoRespnse(200, $response);
        });

/**
 * Deleting task. Users can delete only their tasks
 * method DELETE
 * url /tasks
 */
$app->delete('/tasks/:id', 'authenticate', function($task_id) use($app) {
            global $user_id;

            $db = new DbHandler();
            $response = array();
            $result = $db->deleteTask($user_id, $task_id);
            if ($result) {
                // task deleted successfully
                $response["error"] = false;
                $response["message"] = "Task deleted succesfully";
            } else {
                // task failed to delete
                $response["error"] = true;
                $response["message"] = "Task failed to delete. Please try again!";
            }
            echoRespnse(200, $response);
        });

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
        echoRespnse(400, $response);
        $app->stop();
    }
}

/**
 * Validating email address
 */
function validateEmail($email) {
    $app = \Slim\Slim::getInstance();
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $response["error"] = true;
        $response["message"] = 'Email address is not valid';
        echoRespnse(400, $response);
        $app->stop();
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
    $app->contentType('application/json');

    echo json_encode($response);
}
