package com.app.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.dto.EmailDetails;
import com.app.entity.Customer;
import com.app.entity.Transaction;
import com.app.exception.InvalidDateException;
import com.app.repository.CustomerRepo;
import com.app.repository.TransactionRepo;
import com.app.util.AppUtils;
import com.app.util.MessageUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	private static final String FILE="C:\\SRG bank\\bankstatements\\MyStatement.pdf";
	
	private EmailService emailService;
	
	/**
	 * Retrieve list of transaction within a date range given an Account Number
	 * Generate a PDF file of transaction
	 * Send a file via email
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public List<Transaction> generateStatement(String accountNo,String startDt,String endDt)
			throws FileNotFoundException, DocumentException{
		
		List<Transaction> transactionList=null;
		
		try {
			LocalDate stDt=LocalDate.parse(startDt, DateTimeFormatter.ISO_DATE);
			LocalDate edDt=LocalDate.parse(endDt, DateTimeFormatter.ISO_DATE);
			
			Boolean isExist=customerRepo.existsByAccountNumber(accountNo);
			
			if(isExist) {
				transactionList=transactionRepo.findAll().stream()
						.filter(transaction->transaction.getAccountNumber().equals(accountNo))
						.filter(transaction->transaction.getTransactionDate().isEqual(stDt))
						.filter(transaction->transaction.getTransactionDate().isEqual(edDt)).toList();
				
				
				Customer user=customerRepo.findByAccountNumber(accountNo);
				
				designStatement(transactionList, new LocalDate[] {stDt,edDt},user);
				
				EmailDetails emailDetails=EmailDetails.builder()
						.receipient(user.getEmail())
						.subject("STATEMENT OF ACCOUNT")
						.messageBody("Kindly find your requested account statement attached!")
						.attachments(FILE).build();
				
				emailService.sendEmailWithAttachment(emailDetails);
			}
		} catch (InvalidDateException e) {
			System.out.println(e.getMessage());
		}
		return transactionList;
	}
	
	private void designStatement(List<Transaction> transactonList,LocalDate[] dates,Customer user)throws FileNotFoundException,DocumentException {
		
		Document document=new Document(new Rectangle(PageSize.A4));
		log.info("Setting size of the document");
		OutputStream outputStream=new FileOutputStream(FILE);
		PdfWriter.getInstance(document,outputStream);
		document.open();
		
		PdfPTable bankInfoTable=new PdfPTable(1);
		PdfPCell bankName=new PdfPCell(new Phrase("SRG Bank"));
		bankName.setBorder(0);
		bankName.setBackgroundColor(BaseColor.BLUE);
		bankName.setPadding(20f);
		
		PdfPCell bankAddress=new PdfPCell(new Phrase("Louiswadi, Greenroad, Thane(West), MH, IND"));
		bankAddress.setBorder(0);
		bankInfoTable.addCell(bankName);
		bankInfoTable.addCell(bankAddress);
		
		PdfPTable statementInfoTable=new PdfPTable(2);
		
		PdfPCell startDate=new PdfPCell(new Phrase("Start Date: "+dates[0].format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		startDate.setBorder(0);
		startDate.setPadding(10f);
		
		PdfPCell statement=new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
		statement.setBorder(0);
		statement.setPadding(10f);
		
		PdfPCell endDate=new PdfPCell(new Phrase("End Date: "+dates[1].format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		endDate.setBorder(0);
		endDate.setPadding(10f);
		
		PdfPCell name=new PdfPCell(new Phrase("Customer Name: "+user.getFirstName()+" "+user.getLastName()));
		name.setBorder(0);
		name.setPadding(10f);
		
		PdfPCell space=new PdfPCell();
		space.setBorder(0);
		space.setPadding(10f);
		
		PdfPCell address=new PdfPCell(new Phrase("Customer Address: "+user.getAddress()));
		address.setBorder(0);
		address.setPadding(10f);
		
		statementInfoTable.addCell(startDate);
		statementInfoTable.addCell(statement);
		statementInfoTable.addCell(endDate);
		statementInfoTable.addCell(name);
		statementInfoTable.addCell(space);
		statementInfoTable.addCell(address);
		
		PdfPTable transactionInfoTable=new PdfPTable(4);
		PdfPCell transactionDate=new PdfPCell(new Phrase("DATE"));
		transactionDate.setBorder(0);
		transactionDate.setBackgroundColor(BaseColor.BLUE);
		address.setPadding(10f);
		
		PdfPCell transactionType=new PdfPCell(new Phrase("TRANSACTION TYPE"));
		transactionType.setBorder(0);
		transactionType.setBackgroundColor(BaseColor.BLUE);
		transactionType.setPadding(10f);
		
		PdfPCell transactionAmount=new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
		transactionAmount.setBorder(0);
		transactionAmount.setBackgroundColor(BaseColor.BLUE);
		transactionAmount.setPadding(10f);
		
		PdfPCell transactionStatus=new PdfPCell(new Phrase("STATUS"));
		transactionStatus.setBorder(0);
		transactionStatus.setBackgroundColor(BaseColor.BLUE);
		transactionStatus.setPadding(10f);
		
		transactionInfoTable.addCell(transactionDate);
		transactionInfoTable.addCell(transactionType);
		transactionInfoTable.addCell(transactionAmount);
		transactionInfoTable.addCell(transactionStatus);
		
		transactonList.forEach(transaction->{
			transactionInfoTable.addCell(new Phrase(transaction.getTransactionDate().toString()));
			transactionInfoTable.addCell(new Phrase(transaction.getTransactionType().toString()));
			transactionInfoTable.addCell(new Phrase(transaction.getAmount().toString()));
			transactionInfoTable.addCell(new Phrase(transaction.getStatus().toString()));
		});
		
		document.add(bankInfoTable);
		document.add(statementInfoTable);
		document.add(transactionInfoTable);
		
		document.close();
	}
	
}
