<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Restore password</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#call').click(function ()
            {
                $.ajax({
                    type: "post",
                    url: "testme", //this is my servlet
                    data: "input=" +$('#ip').val()+"&output="+$('#op').val(),
                    success: function(msg){
                            $('#output').append(msg);
                    }
                });
            });

        });

    </script>
</head>
<body>
<div style="text-align: center">
    <form name="restore">
        <label for="login">Login:</label>
        <input name="login" size="30"/>
        <br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" size="30"/>
        <br>${message}
        <br><br>
        <input type="button" value="Restore" id="call">
    </form>
</div>
</body>
</html>
