<?php

    function upload() {
        if ( isset($_POST["submit"]) ) {

        if ( isset($_FILES["file"])) {

                 //if there was an error uploading the file
             if ($_FILES["file"]["error"] > 0) {
                 echo "Return Code: " . $_FILES["file"]["error"] . "<br />";

             }
             else {
                      //Print file details
                  //echo "Upload: " . $_FILES["file"]["name"] . "<br />";

                      //if file already exists
                  if (file_exists("upload/" . $_FILES["file"]["name"])) {
                    echo $_FILES["file"]["name"] . " already exists. ";
                  }
                  else {
                    $date = date("U");
                    $storagename = "CSV_$date.csv";
                    move_uploaded_file($_FILES["file"]["tmp_name"], "uploads/" . $storagename);
                    //echo "Stored in: " . "uploads/" . $_FILES["file"]["name"] . "<br />";
                 }
             }
          } else {
                  echo "No file selected <br />";
          }
        }
        return $storagename;
    }

    function open($file=''){
              //get the csv file
            $file = "uploads/$file";

            $handle = fopen($file,"r");

            $header = NULL;
            $data = array();

            while (($row = fgetcsv($handle, 1000,",","'")) !== FALSE){

                $data[] = $row;

            }
            fclose($handle);

            return $data;
    }
?>
