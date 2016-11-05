<?php

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of DbHandlerTask
 *
 * @author root
 */
class DbHandlerTasks {
    //put your code here
            private $conn;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }
    /* ------------- `tasks` table method ------------------ */

    /**
     * Creating new task
     * @param String $user_id user id to whom task belongs to
     * @param String $task task text
     */
    public function createTask($user_id, $task) {
        $stmt = $this->conn->prepare("INSERT INTO tasks(task) VALUES(?)");
        $stmt->bind_param("s", $task);
        $result = $stmt->execute();
        $stmt->close();

        if ($result) {
            // task row created
            // now assign the task to user
            $new_task_id = $this->conn->insert_id;
            $res = $this->createUserTask($user_id, $new_task_id);
            if ($res) {
                // task created successfully
                return $new_task_id;
            } else {
                // task failed to create
                return NULL;
            }
        } else {
            // task failed to create
            return NULL;
        }
    }

    
    /**
     * Creating new map
     * @param String $user_id user id to whom task belongs to
     * @param String $task task text
     */
    public function createMap($unique_id, $name,$address,$city,$province, $latitude,$longitude) {
        $stmt = $this->conn->prepare("INSERT INTO maps (unique_id,name,address,city,province,latitude,longitude) VALUES(?,?,?,?,?,?,?)");
        $stmt->bind_param("sssssdd", $unique_id, $name,$address,$city, $province,$latitude,$longitude);
        $result = $stmt->execute();
        $stmt->close();

        if ($result) {
            // task row created
            // now assign the task to user
             $maps_id = $this->conn->insert_id;
                return $maps_id;
            
        } else {
            // task failed to create
            return NULL;
        }
    }

    
    /**
     * Fetching single task
     * @param String $task_id id of the task
     */
    public function getTask($task_id, $user_id) {
        $stmt = $this->conn->prepare("SELECT t.id, t.task, t.status, t.created_at from tasks t, user_tasks ut WHERE t.id != ? AND ut.task_id = t.id AND ut.user_id = ?");
        $stmt->bind_param("ii", $task_id, $user_id);
        if ($stmt->execute()) {
            $res = array();
            $stmt->bind_result($id, $task, $status, $created_at);
            // TODO
            // $task = $stmt->get_result()->fetch_assoc();
            $stmt->fetch();
            $res["id"] = $id;
            $res["task"] = $task;
            $res["status"] = $status;
           $res["created_at"] = $created_at;
            $stmt->close();
            return $res;
        } else {
            return NULL;
        }
    }

    /**
     * Fetching all user tasks
     * @param String $user_id id of the user
     */
    public function getAllUserTasks($user_id) {
        $stmt = $this->conn->prepare("SELECT t.* FROM tasks t, user_tasks ut WHERE t.id = ut.task_id AND ut.user_id = ?");
        $stmt->bind_param("s", $user_id);
        $stmt->execute();
        if ($stmt->execute()) {
        $items = $res = array();
    
        $stmt->bind_result($id, $task, $status, $created_at);

        // fetch values
        while ($stmt->fetch()) {
        $res["id"] = $id;
        $res["task"] = $task;
        $res["status"] = $status;
        $res["created_at"] = $created_at;

        $items[] = $res;
    }

    $stmt->close();

    return $items;
} else {
    return NULL;
}
    }
    
        /**
     * Fetching all user maps
     * @param String $user_id id of the user
     */
    public function getAllMaps($maps_status) {
        $stmt = $this->conn->prepare("SELECT maps_id, name, address, city, province, latitude, longitude  FROM maps WHERE maps_status = ?");
        $stmt->bind_param("i", $maps_status);
        $stmt->execute();
        if ($stmt->execute()) { $items = $res = array();
    
        $stmt->bind_result($maps_id, $name, $address, $city, $province, $latitude, $longitude );
        // fetch values
        while ($stmt->fetch()) {
        $res["maps_id"] = $maps_id;
        $res["name"] = $name;
        $res["address"] = $address;
        $res["city"] = $city;
        $res["province"] = $province;
        $res["latitude"] = $latitude;
        $res["longitude"] = $longitude;
        $items[] = $res;
    }

    $stmt->close();
    return $items;
} else {
    return NULL;
}
    }

    
                /**
     * Fetching all user tasks
     * @param String $user_id id of the user
     */
    public function getAllTempat($konsumen_id) {
        $stmt = $this->conn->prepare("SELECT tempat_id, konsumen_id, tempat_nama, tempat_alamat, tempat_kota, tempat_provinsi, tempat_latitude, tempat_longitude, tempat_created, tempat_updated FROM tempat WHERE konsumen_id = ?");
        $stmt->bind_param("s", $konsumen_id);
        $stmt->execute();
        if ($stmt->execute()) { $items = $res = array();
    
        $stmt->bind_result($tempat_id, $konsumen_id, $tempat_nama, $tempat_alamat, $tempat_kota, $tempat_provinsi, $tempat_latitude, $tempat_longitude, $tempat_created, $tempat_updated );
        // fetch values
        while ($stmt->fetch()) {
        $res["tempat_id"] = $tempat_id;
        $res["konsumen_id"] = $konsumen_id;
        $res["tempat_nama"] = $tempat_nama;
        $res["tempat_alamat"] = $tempat_alamat;
        $res["tempat_kota"] = $tempat_kota;
        $res["tempat_provinsi"] = $tempat_provinsi;
        $res["tempat_latitude"] = $tempat_latitude;
        $res["tempat_longitude"] = $tempat_longitude;
        $res["tempat_created"] = $tempat_created;
        $res["tempat_updated"] = $tempat_updated;
        $items[] = $res;
    }

    $stmt->close();
    return $items;
} else {
    return NULL;
}
    }
    
    
    
     
     /**
     * Fetching all transaksi
     * @param String $konsumen_id id of the user
     */
    public function getAllTransaksi($konsumen_id) {
        $stmt = $this->conn->prepare("SELECT transaksi_id, konsumen_id, pemesanan_id, kurir_pengambil_id,kurir_pengantar_id, tempat_id,   pembayaran_id,tanggal_transaksi  FROM transaksi WHERE konsumen_id = ?");
        $stmt->bind_param("s", $konsumen_id);
        $stmt->execute();
        if ($stmt->execute()) { $items = $res = array();
    
        $stmt->bind_result($transaksi_id, $konsumen_id, $pemesanan_id, $kurir_pengambil_id, $kurir_pengantar_id, $tempat_id, $pembayaran_id,$tanggal_transaksi );
        // fetch values
        while ($stmt->fetch()) {
        $res["transaksi_id"] = $transaksi_id;
        $res["konsumen_id"] = $konsumen_id;
        $res["pemesanan_id"] = $pemesanan_id;
        $res["kurir_pengambil_id"] = $kurir_pengambil_id;
        $res["kurir_pengantar_id"] = $kurir_pengantar_id;
        $res["tempat_id"] = $tempat_id;
        $res["pembayaran_id"] = $pembayaran_id;
        $res["tanggal_transaksi"] = $tanggal_transaksi;
    
        $items[] = $res;  }

    $stmt->close();
    return $items;
} else {
    return NULL;
} }
    
       
    
     /**
     * Fetching all pesanan
     * @param String $user_id id of the user
     */
    public function getAllPesanan($harga_status) {
        $stmt = $this->conn->prepare("SELECT pesanan_id, konsumen_id, kurir_pengambil_id, kurir_pengantar_id, tempat_id, paket_id, tanggal_pemesanan, tanggal_pengantaran, status_cucian, status_pengantaran FROM pesanan WHERE konsumen_id = ?");
        $stmt->bind_param("i", $harga_status);
        $stmt->execute();
        if ($stmt->execute()) { $items = $res = array();
    
        $stmt->bind_result($pesanan_id, $konsumen_id, $kurir_pengambil_id, $kurir_pengantar_id, $lokasi_id, $paket_id, $tanggal_pemesanan, $tanggal_pengantaran, $status_cucian, $status_pengantaran );
        // fetch values
        while ($stmt->fetch()) {
        $res["pesanan_id"] = $pesanan_id;
        $res["konsumen_id"] = $konsumen_id;
        $res["kurir_pengambil_id"] = $kurir_pengambil_id;
        $res["kurir_pengantar_id"] = $kurir_pengantar_id;
        $res["lokasi_id"] = $lokasi_id;
        $res["paket_id"] = $paket_id;
        $res["tanggal_pemesanan"] = $tanggal_pemesanan;
        $res["tanggal_pengantaran"] = $tanggal_pengantaran;
        $res["status_cucian"] = $status_cucian;
        $res["status_pengantaran"] = $status_pengantaran;
        $items[] = $res;
    }

    $stmt->close();
    return $items;
} else {
    return NULL;
}
    }
    
    
    
    
            /**
     * Fetching all user tasks
     * @param String $user_id id of the user
     */
    public function getAllPaket($paket_status) {
        $stmt = $this->conn->prepare("SELECT paket_id, paket_nama, paket_harga, paket_keterangan, paket_status FROM paket WHERE paket_status = ?");
        $stmt->bind_param("i", $paket_status);
        $stmt->execute();
        if ($stmt->execute()) { $items = $res = array();
    
        $stmt->bind_result($paket_id, $paket_nama, $paket_harga, $paket_keterangan, $paket_status );
        // fetch values
        while ($stmt->fetch()) {
        $res["paket_id"] = $paket_id;
        $res["paket_nama"] = $paket_nama;
        $res["paket_harga"] = $paket_harga;
        $res["paket_keterangan"] = $paket_keterangan;
        $res["paket_status"] = $paket_status;
        $items[] = $res;
    }

    $stmt->close();
    return $items;
} else {
    return NULL;
}
    }
    
    
    /**
     * Updating task
     * @param String $task_id id of the task
     * @param String $task task text
     * @param String $status task status
     */
    public function updateTask($user_id, $task_id, $task, $status) {
        $stmt = $this->conn->prepare("UPDATE tasks t, user_tasks ut set t.task = ?, t.status = ? WHERE t.id = ? AND t.id = ut.task_id AND ut.user_id = ?");
        $stmt->bind_param("siii", $task, $status, $task_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }

    /**
     * Deleting a task
     * @param String $task_id id of the task to delete
     */
    public function deleteTask($user_id, $task_id) {
        $stmt = $this->conn->prepare("DELETE t FROM tasks t, user_tasks ut WHERE t.id = ? AND ut.task_id = t.id AND ut.user_id = ?");
        $stmt->bind_param("ii", $task_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }

    /* ------------- `user_tasks` table method ------------------ */

    /**
     * Function to assign a task to user
     * @param String $user_id id of the user
     * @param String $task_id id of the task
     */
    public function createUserTask($user_id, $task_id) {
        $stmt = $this->conn->prepare("INSERT INTO user_tasks(user_id, task_id) values(?, ?)");
        $stmt->bind_param("ii", $user_id, $task_id);
        $result = $stmt->execute();

        if (false === $result) {
            die('execute() failed: ' . htmlspecialchars($stmt->error));
        }
        $stmt->close();
        return $result;
    }

}

    
 

