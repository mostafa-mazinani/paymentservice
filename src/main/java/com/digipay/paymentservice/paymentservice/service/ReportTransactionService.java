package com.digipay.paymentservice.paymentservice.service;

import com.digipay.paymentservice.paymentservice.model.Card;
import com.digipay.paymentservice.paymentservice.model.PaymentProcessorResponse;
import com.digipay.paymentservice.paymentservice.model.PaymentTransaction;
import com.digipay.paymentservice.paymentservice.repository.ReportTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportTransactionService implements IReportTransactionService {

    private final ReportTransactionRepository reportTransactionRepository;
    private final ICardService cardService;
    private final MemberService memberService;

    public Map<PaymentProcessorResponse.PaymentResponseStatus, List<PaymentTransaction>> getReport(
            Long memberNumber,
            String cardNumber,
            Integer from, Integer to) {

        memberService.findByMemberNumber(memberNumber);
        Card memberCards = cardService.getCardByNumberAndMemberNumber(cardNumber, memberNumber);
        List<PaymentTransaction> paymentTransactionList =
                reportTransactionRepository.findByCardAndTransactionDateBetween(memberCards, from, to)
                                           .orElseThrow(() -> new NoSuchElementException("Member with Number : "
                                                                                                    + memberNumber +
                                                                                                    " Haven't any Transaction on Card with Number : " + cardNumber));
        return paymentTransactionList.stream()
                                     .collect(Collectors.groupingBy(PaymentTransaction::getResult));
    }
}
