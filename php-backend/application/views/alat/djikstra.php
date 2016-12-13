<?php

/* 
 * Copyright (C) 2016 Yuly Nurhidayati <elaundry.pe.hu>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

?>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
        <style>
            body{
                background-color: #B2C2C8;
                font-family:sans-serif;
            }
            h1{
                color: #999;
                text-align: center;
            }
            form{
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h1>Algoritmo de dijkstra</h1>
        <form action="script.php" method="post" enctype="multipart/form-data">
            <input type="hidden" name="MAX_FILE_SIZE" value="30000" />
            Envie seu arquivo CSV: <input type="file" name="file" id="file" />
            <input type="submit" name="submit" value="Enviar CSV!" />
        </form>
    </body>
</html>