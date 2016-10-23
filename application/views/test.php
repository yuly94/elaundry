<!DOCTYPE html>
<html>
<head>
<title>Skeleton Application</title>
<link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body>
<h1>API v1.0 Test</h1>
<hr />
<form id="api_test_form" style="padding: 10px 0 20px 0;" onsubmit="return false;">
    <div class="form-group">
        <label>Secret Key:</label>
        <div>
            <input id="api_secret_key" type="text" name="secret" value="<?=$secret;?>" />
        </div>
    </div>
    <button type="submit">Run</button>
</form>

<div id="test_result_target"></div>
<script src="/lib/jquery.min.js"></script>
<script src="/js/test.js"></script>
</body>
</html>
