<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../css/fontawesome-free-6.0.0-web/css/all.css">
    <link rel="stylesheet" href="../css/main.css">
    <!-- <script src="js/bootstrap.bundle.js"></script> -->
    <script src="../css/bootstrap/js/bootstrap.bundle.js"></script>
    <title>Payment History</title>
</head>

<body>

<!-- Header -->
<c:import url="components/incl/header.jsp"/>

    <!-- Container -->
    <div class="container">
        <!-- Card: Payment History Card -->
        <div class="card">
            <!-- Card Header -->
            <div class="card-header">
                <i class="fas fa-credit-card me-2"></i> Payment History
            </div>
            <!-- End Of Card Header -->
            <div class="card-body">
                <c:if test="${requestScope.payment_history != null}">
                    <!-- Payment History Table -->
                    <table class="table text-center table-striped">
                        <tr>
                            <th>Record Number</th>
                            <th>Beneficiary</th>
                            <th>Beneficiary Account Number</th>
                            <th>Amount</th>
                            <th>Status</th>
                            <th>Reference</th>
                            <th>Reason Code</th>
                            <th>Created at</th>
                        </tr>
                        <c:forEach items="${requestScope.payment_history}" var="transactionHistory">
                            <tr>
                                <th>${transactionHistory.payment_id}</th>
                                <th>${transactionHistory.beneficiary}</th>
                                <th>${transactionHistory.beneficiary_acc_no}</th>
                                <th>${transactionHistory.amount}</th>
                                <th>${transactionHistory.status}</th>
                                <th>${transactionHistory.reference_no}</th>
                                <th>${transactionHistory.reason_code}</th>
                                <th>${transactionHistory.created_at}</th>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>
        <!-- End Of Card: Payment History Card -->
    </div>
    <!-- End of Container -->

<!-- Footer -->
<c:import url="components/incl/footer.jsp"/>