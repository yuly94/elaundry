$(function(){

    $('#api_test_form').on('submit', function(){

        var artId = null;
        var secret = $('#api_secret_key').val();

        $('#test_result_target').html('<label>Test Results:</label>');

        addArticleTest(secret, function(article){

            artId = article?article.art_id:0;

            getArticleTest(artId, function(){

                patchArticleTest(artId, secret, function(){

                    deleteArticleTest(artId, secret, function(){

                        listArticlesTest(function(){

                            finishCallback();
                        });
                    });
                });
            });
        });

        return false;
    });
});

function log(msg, color) {

    if(color==undefined){

        color = 'black';
    }

    console.log("%c" + msg, "color:" + color + ";font-weight:bold;");

    $('#test_result_target').append($('<div style="color: ' + color + ';">'+msg+'</div>'))
}

function finishCallback(){

    log('-----------------------------------------------------------------');
    log('Test Finished!', 'darkred');
}

function addArticleTest(secret, callback){

    log('-----------------------------------------------------------------');
    log('Create article:');

    $.ajax({
        type: 'POST',
        url: '/elaundry/public/api-v1.0/article/add/',
        data: {art_title:"Lorem Ipsum Article Title", art_body:"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", secret:secret},
        success: function(result, textStatus, jqXHR){

            if(result.success){

                log('Ok: id = ' + result.data.art_id, 'green');
            }
            else{

                log('Error: ' + result.message, 'red');
            }

            if(callback!=undefined && typeof callback == 'function'){

                callback(result.data);
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            log('Error: ' + textStatus, 'red');
        }
    });
}

function getArticleTest(artId, callback){

    log('-----------------------------------------------------------------');
    log('Get article [' + artId + ']:');

    $.ajax({
        type: 'GET',
        url: '/elaundry/public/api-v1.0/article/get/' + artId + '/',
        success: function(result, textStatus, jqXHR){

            if(result.success){

                log('Ok: ' + result.data.art_title, 'green');
            }
            else{

                log('Error: ' + result.message, 'red');
            }

            if(callback!=undefined && typeof callback == 'function'){

                callback(result.data);
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            log('Error: ' + textStatus, 'red');
        }
    });
}

function patchArticleTest(artId, secret, callback){

    log('-----------------------------------------------------------------');
    log('Patch article [' + artId + ']:');

    $.ajax({
        type: 'POST',
        url: '/elaundry/public/api-v1.0/article/patch/',
        data: {art_id:artId, art_title:"Patched Article Title", art_body:"Patched Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", secret:secret},
        success: function(result, textStatus, jqXHR){

            if(result.success){

                log('Ok ', 'green');
            }
            else{

                log('Error: ' + result.message, 'red');
            }

            if(callback!=undefined && typeof callback == 'function'){

                callback(result.data);
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            log('Error: ' + textStatus, 'red');
        }
    });
}

function deleteArticleTest(artId, secret, callback){

    log('-----------------------------------------------------------------');
    log('Delete article [' + artId + ']:');

    $.ajax({
        type: 'POST',
        url: '/elaundry/public/api-v1.0/article/delete/',
        data: {art_id:artId, secret:secret},
        success: function(result, textStatus, jqXHR){

            if(result.success){

                log('Ok: article ' + artId + ' deleted', 'green');
            }
            else{

                log('Error: ' + result.message, 'red');
            }

            if(callback!=undefined && typeof callback == 'function'){

                callback(result.data);
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            log('Error: ' + textStatus, 'red');
        }
    });
}

function listArticlesTest(callback){

    log('-----------------------------------------------------------------');
    log('List articles:');

    $.ajax({
        type: 'GET',
        url: '/elaundry/public/api-v1.0/article/list/',
        success: function(result, textStatus, jqXHR){

            if(result.success){

                log('Ok: ' + result.data.length + ' article(s)', 'green');
            }
            else{

                log('Error: ' + result.message, 'red');
            }

            if(callback!=undefined && typeof callback == 'function'){

                callback(result.data);
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            log('Error: ' + textStatus, 'red');
        }
    });
}
