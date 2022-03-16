// Get Transaction Type:
const transactType = document.querySelector("#transact-type");

// Get Transaction Forms:
const paymentCard = document.querySelector(".payment-card");
const transferCard = document.querySelector(".transfer-card");
const depositCard = document.querySelector(".deposit-card");
const withdrawalCard = document.querySelector(".withdrawal-card");

// Check For Transaction Type Event Listener
transactType.addEventListener("change", () => {

    // Check For Transaction Type and Display Form:
    switch (transactType.value) {
        case "peyment":
            paymentCard.style.display = "block";
            transferCard.style.display = "none";
            depositCard.style.display = "none";
            withdrawalCard.style.display = "none";
        break;
        case "transfer":
            transferCard.style.display = "block";
            paymentCard.style.display = "none";
            depositCard.style.display = "none";
            withdrawalCard.style.display = "none";
        break;
        case "deposit":
            depositCard.style.display = "block";
            paymentCard.style.display = "none";
            transferCard.style.display = "none";
            withdrawalCard.style.display = "none";
        break;
        case "withdrawal":
            withdrawalCard.style.display = "block";
            paymentCard.style.display = "none";
            transferCard.style.display = "none";
            depositCard.style.display = "none";
        break;
    }

    // End Of Check For Transaction Type and Display Form:
});
// End Of Check For Transaction Type Event Listener