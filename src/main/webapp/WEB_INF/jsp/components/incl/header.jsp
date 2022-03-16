<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Main PageHeader -->
<header class="main-page-header mb-3 bg-primary">
    <!-- Container -->
    <div class="container d-flex align-items-center">
        <!-- Compony Name -->
        <div class="compony-name">
            Easy-Way Bank
        </div>
        <!-- End Of Compony Name -->

        <!-- Navigation -->
        <nav class="navigation">
            <li><a href="/app/dashboard">Dashboard</a></li>
            <li><a href="/app/payment_history">Payment History</a></li>
            <li><a href="/app/transact_history">Transaction History</a></li>
        </nav>
        <!-- End Of Navigation -->

        <!-- Dispay Name -->
        <div class="display-name ms-auto text-white">
            <i class="fa fa-circle text-success me-2"></i> Welcome: <span>${user.first_name} ${user.last_name}</span>
        </div>
        <!-- End Of Dispay Name -->

        <!-- Log Out Button -->
        <a href="/logout" class="btn btn-sm text-white ms-2">
            <i class="fa fa-sign-out" aria-hidden="true"></i> Sign Out
        </a>
        <!-- End Of Log Out Button -->
    </div>
    <!-- End Of Container -->
</header>
<!-- End Of Main PageHeader -->