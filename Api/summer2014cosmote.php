<?php
// Connecting, selecting database
$dbconn = pg_connect("host=localhost dbname=dbgpstelco user=postgres password=111111")
    or die('Could not connect: ' . pg_last_error());

// Performing SQL query
$query = "select ST_X(geom) as long,ST_Y(geom) as lat,sigstrength from gpstelco where TO_CHAR(TO_TIMESTAMP(tmsmtp / 1000), 'DD/MM/YYYY HH24:MI:SS') > '24/07/2014' and TO_CHAR(TO_TIMESTAMP(tmsmtp / 1000), 'DD/MM/YYYY HH24:MI:SS') < '30/08/2014' and carrier = 'COSMOT' order by random() limit 200 --group by model";
//$query1 = "select * from gpstelco limit 1000";
$result = pg_query($query) or die('Query failed: ' . pg_last_error());

// Printing results in Json

while($line = pg_fetch_array($result, null, PGSQL_ASSOC))
        $output[]=$line;


//print(json_encode($output));
$str2 = '{ "coordinates":';
print($str2); 
print(json_encode($output)); 
$str1 = '}';
print($str1);
// Free resultset
pg_free_result($result);

// Closing connection
pg_close($dbconn);
?>
