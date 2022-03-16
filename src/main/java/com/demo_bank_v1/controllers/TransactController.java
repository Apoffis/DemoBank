package com.demo_bank_v1.controllers;

import com.demo_bank_v1.models.User;
import com.demo_bank_v1.repository.AccountRepository;
import com.demo_bank_v1.repository.PaymentRepository;
import com.demo_bank_v1.repository.TransactRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/transact")
public class TransactController {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final TransactRepository transactRepository;

    private User user;
    private Double currentBalance;
    private Double newBalance;

    LocalDateTime currentDateTime;

    TransactController(AccountRepository accountRepository, PaymentRepository paymentRepository, TransactRepository transactRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
        this.transactRepository = transactRepository;
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("deposit_amount") String depositAmount,
                          @RequestParam("account_id") String accountId,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {

        // TODO: 3/14/2022 CHECK FOR EMPTY STRING:
        if (depositAmount.isEmpty() || accountId.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Deposit Amount or Account Depositing to Cannot Be Empty!");
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 GET LOGGED IN USERS:
        user = (User) session.getAttribute("user");

        // TODO: 3/14/2022 GET CURRENT ACCOUNT BALANCE:
        Integer acc_id = Integer.parseInt(accountId);

        Double depositAmountValue = Double.parseDouble(depositAmount);

        // TODO: 3/14/2022 CHECK IF DEPOSIT ACCOUNT IS 0 (ZERO):
        if (depositAmountValue == 0) {
            redirectAttributes.addFlashAttribute("error", "Deposit Amount Cannot Be Of 0 (Zero) Value!");
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 UPDATE BALANCE:
        currentBalance = accountRepository.getAccountBalance(user.getUser_id(), acc_id);
        newBalance = currentBalance + depositAmountValue;

        accountRepository.changeAccountBalance(newBalance, acc_id);

        // log transact successful
        currentDateTime = LocalDateTime.now();
        transactRepository.logTransaction(acc_id, "deposit",
                depositAmountValue, "Online", "success",
                "Deposit Transaction Successfully", currentDateTime);

        redirectAttributes.addFlashAttribute("success", "Amount Deposited Successfully!");
        return "redirect:/app/dashboard";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("transfer_from") String transferFrom,
                           @RequestParam("transfer_to") String transferTo,
                           @RequestParam("transfer_amount") String transferAmount,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        // Init Error Message
        String errorMessage;

        // TODO: 3/14/2022 CHECK FOR EMPTY FIELDS:
        if (transferFrom.isEmpty() || transferTo.isEmpty() || transferAmount.isEmpty()) {
            errorMessage = "The Account Transferring From an To along with the amount cannot be empty!";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 CONVERT VARIABLES:
        Integer transferFromId = Integer.parseInt(transferFrom);
        Integer transferToId = Integer.parseInt(transferTo);
        Double transferAmountValue = Double.parseDouble(transferAmount);

        // TODO: 3/14/2022 CHECK IF TRANSFERRING INTO THE SAME ACCOUNT:
        if (transferFromId == transferToId) {
            errorMessage = "Cannot Transfer Into The Same Account, Please Select The Appropriate Account To Perform Transfer!";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 CHECK FOR 0 (ZERO):
        if (transferAmountValue == 0) {
            errorMessage = "Cannot Transfer An Amount of 0 (Zero) Value, Please Enter a Value Greater Than 0 (Zero)!";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 GET LOGGED IN USER:
        user = (User) session.getAttribute("user");

        // TODO: 3/14/2022 GET CURRENT BALANCE:
        Double currentBalanceOfAccountTransferringFrom = accountRepository.getAccountBalance(user.getUser_id(), transferFromId);

        // TODO: 3/14/2022 CHECK IF TRANSFER AMOUNT IS MORE THEN CURRENT BALANCE:
        if (currentBalance < transferAmountValue) {
            errorMessage = "You Have Insufficient Funds to Perform this Transfer!";
            currentDateTime = LocalDateTime.now();
            transactRepository.logTransaction(transferFromId, "Transfer", transferAmountValue, "Online", "failed", "Insufficient Founds", currentDateTime);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        Double currentBalanceOfAccountTransferringTo = accountRepository.getAccountBalance(user.getUser_id(), transferToId);        // Change The Balance Of The Account Transferring To:

        // TODO: 3/14/2022 SET NEW BALANCE:
        Double newBalanceOfAccountTransferringFrom = currentBalanceOfAccountTransferringFrom - transferAmountValue;
        Double newBalanceOfAccountTransferringTo = currentBalanceOfAccountTransferringTo + transferAmountValue;

        // Change The Balance Of The Account Transferring From:
        accountRepository.changeAccountBalance(newBalanceOfAccountTransferringFrom, transferFromId);

        // Change The Balance Of The Account Transferring To:
        accountRepository.changeAccountBalance(newBalanceOfAccountTransferringTo, transferToId);

        currentDateTime = LocalDateTime.now();
        transactRepository.logTransaction(transferFromId, "Transfer", transferAmountValue, "Online", "success", "Transaction Transfer Successful", currentDateTime);

        redirectAttributes.addFlashAttribute("success", "Amount Transferred Successfully!");
        return "redirect:/app/dashboard";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("withdrawal_amount") String withdrawalAmount,
                           @RequestParam("account_id") String accountId,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        String errorMessage;

        // TODO: 3/14/2022 CHECK FOR EMPTY VALUE:
        if (withdrawalAmount.isEmpty() || accountId.isEmpty()) {
            errorMessage = "Withdrawal Amount And Account Withdrawing form Cannot Be Empty!";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 CONVERT VARIABLES:
        Double withdrawal_amount = Double.parseDouble(withdrawalAmount);
        Integer account_id = Integer.parseInt(accountId);

        // TODO: 3/14/2022 CHECK FOR 0 (ZERO) VALUES:
        if(withdrawal_amount == 0) {
            errorMessage = "Withdrawal An Amount Cannot Be of 0 (Zero) Value, Please Enter a Value Greater Than 0 (Zero)!";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 GET LOGGED USER:
        user = (User) session.getAttribute("user");

        // TODO: 3/14/2022  GET CURRENT BALANCE:
        currentBalance = accountRepository.getAccountBalance(user.getUser_id(), account_id);

        // TODO: 3/14/2022 CHECK IF WITHDRAWAL AMOUNT IS MORE THEN CURRENT BALANCE:
        if (currentBalance < withdrawal_amount) {
            errorMessage = "You Have Insufficient Funds to Perform this Withdrawal!";
            currentDateTime = LocalDateTime.now();
            transactRepository.logTransaction(account_id, "Withdrawal", withdrawal_amount, "Online", "failed", "Insufficient Founds", currentDateTime);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 SET NEW BALANCE:
        newBalance = currentBalance - withdrawal_amount;

        // TODO: 3/14/2022 UPDATE ACCOUNT BALANCE:
        accountRepository.changeAccountBalance(newBalance, account_id);

        currentDateTime = LocalDateTime.now();
        transactRepository.logTransaction(account_id, "Withdrawal", withdrawal_amount, "Online", "success", "Withdrawal Founds", currentDateTime);
        
        redirectAttributes.addFlashAttribute("success", "Withdrawal Successfully!");
        return "redirect:/app/dashboard";
    }

    @PostMapping("/payment")
    String payment(@RequestParam("beneficiary") String beneficiary,
                   @RequestParam("account_number") String account_number,
                   @RequestParam("account_id") String account_id,
                   @RequestParam("reference") String reference,
                   @RequestParam("payment_amount") String payment_amount,
                   HttpSession session,
                   RedirectAttributes redirectAttributes) {

        String errorMessage;

        // TODO: 3/14/2022 CHECK FOR EMPTY VALUES:
        if (beneficiary.isEmpty() || account_number.isEmpty() || account_id.isEmpty() || payment_amount.isEmpty()) {
            errorMessage = "Beneficiary, Account Number, Account Paying Form And Payment Amount Cannot Be Empty!";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 CONVERT VARIABLES:
        Integer accountId = Integer.parseInt(account_id);
        Double paymentAmount = Double.parseDouble(payment_amount);

        // TODO: 3/14/2022 CHECK FOR 0 (ZERO) VALUES:
        if (paymentAmount == 0) {
            errorMessage = "Payment Amount Cannot Be of 0 (Zero) Value, Please Enter a Value Greater Than 0 (Zero)!";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 GET LOGGED INT USER:
        user = (User) session.getAttribute("user");

        // TODO: 3/14/2022 GET CURRENT BALANCE:
        currentBalance = accountRepository.getAccountBalance(user.getUser_id(), accountId);

        // TODO: 3/14/2022 CHECK IF PAYMENT AMOUNT IS MORE THEN CURRENT BALANCE:
        if (currentBalance < paymentAmount) {
            currentDateTime = LocalDateTime.now();
            errorMessage = "You Have Insufficient Funds to Perform this Payment!";
            String reasonCode = "Could not Processed Payment due to Insufficient funds!";
            paymentRepository.makePayment(accountId, beneficiary, account_number, paymentAmount, reference, "failed", reasonCode, currentDateTime);
            transactRepository.logTransaction(accountId, "Payment", paymentAmount, "Online", "failed", "Insufficient Founds", currentDateTime);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/app/dashboard";
        }

        // TODO: 3/14/2022 SET NEW BALANCE FOR PAYING FROM:
        newBalance = currentBalance - paymentAmount;

        String reasonCode = "Payment Processed Successfully!";

        // TODO: 3/14/2022 MAKE PAYMENT:
        currentDateTime = LocalDateTime.now();
        paymentRepository.makePayment(accountId, beneficiary, account_number, paymentAmount, reference, "success", reasonCode, currentDateTime);

        // TODO: 3/14/2022 UPDATE ACCOUNT PAYING FROM:
        accountRepository.changeAccountBalance(newBalance, accountId);

        currentDateTime = LocalDateTime.now();
        transactRepository.logTransaction(accountId, "Payment", paymentAmount, "Online", "success", "Insufficient Founds", currentDateTime);

        redirectAttributes.addFlashAttribute("success", reasonCode);
        return "redirect:/app/dashboard";
    }

}
