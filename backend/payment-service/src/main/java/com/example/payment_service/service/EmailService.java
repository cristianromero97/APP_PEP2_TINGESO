package com.example.payment_service.service;

import com.example.payment_service.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PDFService pdfService;

    public void sendPaymentPdf(String to, PaymentEntity payment, String subject) throws MessagingException, IOException {
        //Generate the PDF from the PaymentEntity
        byte[] pdfBytes = pdfService.createInvoicePDF(payment);

        //Create the email message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        //Configure email details
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("Adjunto encontrarás tu comprobante de pago.");

        //Create a resource with the PDF bytes
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        //Add the attachment
        helper.addAttachment("comprobante_pago.pdf", resource);

        //Send the message
        mailSender.send(message);
    }
}
