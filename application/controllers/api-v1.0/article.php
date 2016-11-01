<?php

use \FB;
use \MyConn\Helper;

$app->get('/api-v1.0/article/list/', function () use ($app) {

    $articles = ArticleModel::listArticles();

    if(!is_array($articles)){

        return Helper::response(false, array(), 'Application error', 500);
    }

    return Helper::response(true, $articles);
});

$app->get('/api-v1.0/article/get/:id/', function ($id) use ($app) {

    $article = ArticleModel::getArticle($id);

    if(empty($article)){

        return Helper::response(false, array(), 'Article not found', 404);
    }

    return Helper::response(true, $article);
});

$app->post('/api-v1.0/article/add/', function () use ($app) {

    if(!$account = Helper::checkSecret()){
        
        return;
    }

    $artId = ArticleModel::addArticle(array(
        'acc_id'=>$account['acc_id'],
        'art_title'=>$app->request->post('art_title', ''),
        'art_body'=>$app->request->post('art_body', '')
    ));

    if(empty($artId)){

        return Helper::response(false, array(), 'Application error', 500);
    }

    return Helper::response(true, array('art_id'=>$artId));
});

$app->post('/api-v1.0/article/patch/', function () use ($app) {

    if(!$account = Helper::checkSecret()){

        return;
    }

    $artId = $app->request->post('art_id');

    if(empty($artId)){

        return Helper::response(false, array(), 'Bad request, art_id required', 400);
    }

    $article = ArticleModel::getArticle($artId);

    if(empty($article)){

        return Helper::response(false, array(), 'Article not found', 404);
    }

    if($article['acc_id']!=$account['acc_id']){

        return Helper::response(false, array(), 'Forbidden, article belongs to different account', 403);
    }

    $patched = ArticleModel::patchArticle($article['acc_id'], array(
        'art_title'=>$app->request->post('art_title', ''),
        'art_body'=>$app->request->post('art_body', '')
    ));

    if(!$patched){

        return Helper::response(false, array(), 'Application error', 500);
    }

    return Helper::response(true);
});

$app->post('/api-v1.0/article/delete/', function () use ($app) {

    if(!$account = Helper::checkSecret()){

        return;
    }

    $artId = $app->request->post('art_id');

    if(empty($artId)){

        return Helper::response(false, array(), 'Bad request, art_id required', 400);
    }

    $article = ArticleModel::getArticle($artId);

    if(empty($article)){

        return Helper::response(false, array(), 'Article not found', 404);
    }

    if($article['acc_id']!=$account['acc_id']){

        return Helper::response(false, array(), 'Forbidden, article belongs to different account', 403);
    }
    
    $deleted = ArticleModel::deleteArticle($article['art_id']);
    
    if(!$deleted){
        
        return Helper::response(false, array(), 'Application error', 500);
    }

    FB::log($deleted);
    
    return Helper::response(true);
});
