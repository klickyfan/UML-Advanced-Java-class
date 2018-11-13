<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">

        <title>Stock Quote Application: Quotes Requested</title>

        <style>
            body {
                padding: 4em;
                background: #e5e5e5;
                font: 13px/1.4 Geneva, 'Lucida Sans', 'Lucida Grande', 'Lucida Sans Unicode', Verdana, sans-serif;
            }
        </style>

    </head>

    <body>

        <h1>Stock Quote Application: Quotes Requested</h1>

            <c:choose>

                <c:when test="${!empty sessionScope.errorMessage}"  >
                    <div class="errorMessage">Sorry, I was unable to get the quotes you requested. <%=session.getAttribute("errorMessage")%></div>
                </c:when>

                <c:otherwise>
                    <jsp:useBean id="stockQuoteList" type="java.util.ArrayList"  scope="session">
                        <c:set target='${stockQuoteList}'  value='${sessionScope.get("stockQuoteList")}'/>
                    </jsp:useBean>
                    <table class="pure-table">
                        <thead>
                        <tr>
                            <th>Stock</th>
                            <th>Date Time</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${stockQuoteList}" var="quote">
                                <tr>
                                    <td>${quote.getStockSymbol()}</td><td>${quote.getDateRecordedAsString()}</td><td>${quote.getStockPrice()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>

            </c:choose>

        <br>

        <h2><a href="/stockQuoteForm.jsp">Return to form.</a></h2>

    </body>

</html>
