<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stockQuoteList" type="java.util.ArrayList"  scope="session">
    <c:set target='${stockQuoteList}'  value='${sessionScope.get("stockQuoteList")}'/>
</jsp:useBean>

<html>

    <head>

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

            <c:when test="${stockQuoteList}.size() == 0" >
                Sorry, unable to get stock quotes!
            </c:when>

            <c:otherwise>
                <c:forEach items="${stockQuoteList}" var="quote">
                    <tr>
                        <td>${quote.toString()}</td><br>
                    </tr>
                </c:forEach>
            </c:otherwise>

        </c:choose>

    </body>

</html>
