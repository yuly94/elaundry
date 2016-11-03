<?php

use \FB;
use \My\Helper;

$app->get('/home/aktifasi/:id/', function ($id) use ($app) {

    $article = ArticleModel::getArticle($id);

    if(empty($article)){

        return Helper::response(false, array(), 'Article not found', 404);
    }

    return Helper::response(true, $article);
});
