package br.ufrn.library.dto;

import java.util.Map;
import br.ufrn.library.model.Book;

public class LoanReportDTO {

    private final long totalLoans;
    private final Map<Book, Long> loansPerBook;

    public LoanReportDTO(long totalLoans, Map<Book, Long> loansPerBook) {
        this.totalLoans = totalLoans;
        this.loansPerBook = loansPerBook;
    }

    public long getTotalLoans() { return totalLoans; }

    public Map<Book, Long> getLoansPerBook() { return loansPerBook; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Loan Report ---\n");
        sb.append("Total Loans in System: ").append(totalLoans).append("\n");
        sb.append("Loans per Book (Descending):\n");
        
        loansPerBook.forEach((book, count) -> {
            sb.append("  -> ")
              .append(book.getTitle())
              .append(" (ISBN: ").append(book.getIsbn()).append("): ")
              .append(count).append(" loans\n");
        });
        
        return sb.toString();
    }
}