<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Stock Quote Application</title>

    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">

    <style>
        [type="date"] {
            background:#fff url(https://cdn1.iconfinder.com/data/icons/cc_mono_icon_set/blacks/16x16/calendar_2.png)  97% 50% no-repeat ;
        }
        [type="date"]::-webkit-inner-spin-button {
            display: none;
        }
        [type="date"]::-webkit-calendar-picker-indicator {
            opacity: 0;
        }
        body {
            padding: 4em;
            background: #e5e5e5;
            font: 13px/1.4 Geneva, 'Lucida Sans', 'Lucida Grande', 'Lucida Sans Unicode', Verdana, sans-serif;
        }
        label {
            display: block;
        }
        input[type="date"],input[type="text"]{
            width: 190px;
        }
    </style>

</head>

    <body>

        <h1>Stock Quote Application: Home</h1>

        <form action="servlets/StockSearch/" method="post" class="pure-form">

            <div class="pure-control-group">
                <label for="symbol">
                    Symbol
                </label>
                <input id="symbol" name="symbol" type="text" value="AAPL"/>
            </div>

            <br>

            <div class="pure-control-group">
                <label for="from">
                    From
                </label>
                <input id="from" name="from" type="date" value="2018-10-08"/>
            </div>

            <br>

            <div class="pure-control-group">
                <label for="until">
                    Until
                </label>
                <input id="until" name="until"  type="date" value="2018-10-12"/>
            </div>

            <br>

            <div class="pure-control-group">

                <label class="label-head">
                    On what interval?
                </label>

                <label for="daily-radio">
                    <input id="daily-radio" name="interval" type="radio" value="daily" checked />
                    daily
                </label>

                <label for="weekly-radio">
                    <input id="weekly-radio" name="interval" type="radio" value="weekly" />
                    weekly
                </label>

                <label for="monthly-radio">
                    <input id="monthly-radio" name="interval" type="radio" value="monthly" />
                    monthly
                </label>

            </div>

            <br>

            <div class="pure-control-group">

                <button class="pure-button pure-button-primary" name="submit" type="submit">
                    Submit
                </button>

            </div>

        </form>

    </body>

</html>
