<?php

class ArticleModel{

    public static function addArticle($data){

        $app = \Slim\Slim::getInstance();

        $sql = "INSERT INTO article (art_id, acc_id, art_title, art_body, art_created_at, art_updated_at)
        VALUES (null, :acc_id, :art_title, :art_body, NOW(), NOW());";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'acc_id'=>$data['acc_id'],
            'art_title'=>$data['art_title'],
            'art_body'=>$data['art_body']
        ));

        if($stmt->errorCode()!=0){

            return false;
        }

        return $app->db->lastInsertId();
    }

    public static function patchArticle($artId, $data){

        $app = \Slim\Slim::getInstance();

        $sql = "UPDATE article SET art_title=:art_title, art_body=:art_body, art_updated_at=NOW() WHERE art_id=:art_id";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'art_title'=>$data['art_title'],
            'art_body'=>$data['art_body'],
            'art_id'=>$artId
        ));

        if($stmt->errorCode()!=0){

            return false;
        }

        return true;
    }

    public static function getArticle($artId){

        $app = \Slim\Slim::getInstance();

        $sql = "SELECT * FROM article WHERE art_id=:id";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('id'=>$artId));

        return $stmt->fetch();
    }

    public static function deleteArticle($artId){

        $app = \Slim\Slim::getInstance();

        $sql = "DELETE FROM article WHERE art_id=:art_id";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('art_id'=>$artId));

        if($stmt->errorCode()!=0){

            return false;
        }

        return true;
    }

    public static function listArticles(){

        $app = \Slim\Slim::getInstance();

        $sql = "SELECT * FROM article ORDER BY art_created_at ASC";

        $stmt = $app->db->prepare($sql);
        $stmt->execute();

        if($stmt->errorCode()!=0){

            return false;
        }

        return $stmt->fetchAll();
    }
}
