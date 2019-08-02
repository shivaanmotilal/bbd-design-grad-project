var mysql = require('mysql');

const config = mysql.createConnection({
    user: 'root',
    password: 'root',
    server: 'localhost', 
    database: 'test_wallet',
    port: 3306
});

config.connect(function(err) {
    if (err) throw err;
    config.query("SELECT * FROM payment_history", function (err, res, fields) {
        if (err) throw err;
        console.log(res);
    });
    console.log("connected");
});
