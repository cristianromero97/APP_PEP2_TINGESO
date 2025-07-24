package com.example.payment_service.service;

import com.example.payment_service.entity.PaymentEntity;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PDFService {

    public byte[] createInvoicePDF(PaymentEntity payment) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //Create a PDFWriter and PDFDocument
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);

        //Create the Document object to add content
        Document document = new Document(pdfDocument);

        //Add content to the document
        document.add(new Paragraph("Factura de Pago"));
        document.add(new Paragraph("ID del Pago: " + payment.getId()));
        document.add(new Paragraph("Monto Total: " + payment.getTotalAmount()));
        document.add(new Paragraph("Descuento Aplicado: " + payment.getDiscountApplied()));
        document.add(new Paragraph("IVA: " + payment.getIva()));
        document.add(new Paragraph("Monto Final: " + payment.getFinalAmount()));
        document.add(new Paragraph("Estado: " + payment.getState()));

        //Close document
        document.close();

        //Return the bytes of the PDF
        return byteArrayOutputStream.toByteArray();
    }
}
