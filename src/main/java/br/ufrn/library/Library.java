package br.ufrn.library;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.ufrn.library.dto.BookAvailabilityDTO;
import br.ufrn.library.dto.LoanReportDTO;
import br.ufrn.library.model.Loan; 
import br.ufrn.library.model.User; 
import br.ufrn.library.repository.BookRepository;
import br.ufrn.library.repository.LoanRepository;
import br.ufrn.library.repository.UserRepository;
import br.ufrn.library.repository.impl.InMemoryBookRepository;
import br.ufrn.library.repository.impl.InMemoryLoanRepository;
import br.ufrn.library.repository.impl.InMemoryUserRepository;
import br.ufrn.library.service.BookService;
import br.ufrn.library.service.LoanService;
import br.ufrn.library.service.UserService;

public class Library {

    private static final Scanner scanner = new Scanner(System.in);
    private static BookService bookService;
    private static UserService userService;
    private static LoanService loanService;

    public static void main(String[] args) {
        
        setupServices();
        runMenuLoop();
        
        scanner.close();
        System.out.println("Sistema finalizado.");
    }

    private static void setupServices() {
        UserRepository userRepo = new InMemoryUserRepository();
        BookRepository bookRepo = new InMemoryBookRepository();
        LoanRepository loanRepo = new InMemoryLoanRepository();

        userService = new UserService(userRepo);
        bookService = new BookService(bookRepo);
        loanService = new LoanService(loanRepo, bookRepo, userRepo);
    }

    private static void runMenuLoop() {
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                running = dispatchMenuChoice(choice);

            } catch (NumberFormatException e) {
                System.err.println("Erro: Por favor, digite um número válido.");
            }
            
            if (running) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }

    private static boolean dispatchMenuChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    handleRegisterBook();
                    break;
                case 2:
                    handleRegisterUser();
                    break;
                case 3:
                    handleCreateLoan();
                    break;
                case 4:
                    handleReturnLoan();
                    break;
                case 5:
                    handleListBookAvailability();
                    break;
                case 6:
                    handleListAllUsers();
                    break;
                case 7:
                    handleListActiveLoans();
                    break;
                case 8:
                    handleLoanReport();
                    break;
                case 9:
                    handleSeedData();
                    break;
                case 0:
                    return false;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } catch (Exception e) {
            // Captura erros de negócio (ex: BookNotFound, validações)
            System.err.println("\n--- ERRO NA OPERAÇÃO ---\n" + e.getMessage() + "\n");
        }
        return true;
    }

    private static void printMenu() {
        System.out.println("\n--- Sistema de Biblioteca ---");
        System.out.println("1. Cadastrar Livro");
        System.out.println("2. Cadastrar Usuário");
        System.out.println("3. Realizar Empréstimo");
        System.out.println("4. Realizar Devolução");
        System.out.println("5. Listar Livros e Disponibilidade");
        System.out.println("6. Listar Usuários Cadastrados");
        System.out.println("7. Listar Empréstimos Ativos");
        System.out.println("8. Gerar Relatório de Empréstimos");
        System.out.println("9. Carregar Dados");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void handleRegisterBook() {
        System.out.println("\n--- 1. Cadastrar Livro ---");
        System.out.print("Tipo (1-Físico, 2-Digital): ");
        int type = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        if (type == 1) {
            System.out.print("Quantidade de Cópias: ");
            int copies = Integer.parseInt(scanner.nextLine());
            bookService.registerPhysicalBook(title, author, isbn, copies);
            System.out.println("Livro Físico cadastrado com sucesso!");
        } else if (type == 2) {
            bookService.registerDigitalBook(title, author, isbn);
            System.out.println("Livro Digital cadastrado com sucesso!");
        } else {
            System.err.println("Tipo inválido.");
        }
    }

    private static void handleRegisterUser() {
        System.out.println("\n--- 2. Cadastrar Usuário ---");
        System.out.print("ID do Usuário: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        
        userService.registerUser(id, name);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void handleCreateLoan() {
        System.out.println("\n--- 3. Realizar Empréstimo ---");
        System.out.print("ID do Empréstimo: ");
        String loanId = scanner.nextLine();
        System.out.print("ID do Usuário: ");
        String userId = scanner.nextLine();
        System.out.print("ISBN do Livro: ");
        String isbn = scanner.nextLine();
        
        loanService.createLoan(loanId, userId, isbn);
        System.out.println("Empréstimo realizado com sucesso!");
    }

    private static void handleReturnLoan() {
        System.out.println("\n--- 4. Realizar Devolução ---");
        System.out.print("ID do Empréstimo: ");
        String loanId = scanner.nextLine();
        
        loanService.returnLoan(loanId, LocalDate.now());
        System.out.println("Devolução registrada com sucesso!");
    }

    private static void handleListBookAvailability() {
        System.out.println("\n--- 5. Listar Livros e Disponibilidade ---");
        
        List<BookAvailabilityDTO> dtos = bookService.getBookAvailabilityReport();
        if (dtos.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for (BookAvailabilityDTO dto : dtos) {
            System.out.printf("  -> %s (ISBN: %s) [%s] | %s\n",
                    dto.getTitle(),
                    dto.getIsbn(),
                    dto.getType(),
                    dto.getAvailability());
        }
    }
    
    private static void handleListAllUsers() {
        System.out.println("\n--- 7. Listar Usuários Cadastrados ---");
        List<User> users = userService.listAllUsers();

        if (users.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        
        for (User user : users) {
            System.out.printf("  -> ID: %s | Nome: %s | Empréstimos no Histórico: %d\n",
                    user.getId(),
                    user.getName(),
                    user.getLoanHistory().size());
        }
    }

    private static void handleListActiveLoans() {
        System.out.println("\n--- 8. Listar Empréstimos Ativos ---");
        List<Loan> activeLoans = loanService.getAllActiveLoans();

        if (activeLoans.isEmpty()) {
            System.out.println("Nenhum empréstimo ativo no momento.");
            return;
        }

        for (Loan loan : activeLoans) {
            System.out.printf("  -> ID: %s | Data: %s | Usuário: %s | Livro: %s\n",
                    loan.getId(),
                    loan.getLoanDate().toString(),
                    loan.getUser().getName(),
                    loan.getBook().getTitle());
        }
    }

    private static void handleLoanReport() {
        System.out.println("\n--- 6. Relatório de Empréstimos ---");
        LoanReportDTO report = loanService.generateLoanReport();

        System.out.println("Total de Empréstimos no Sistema: " + report.getTotalLoans());
        System.out.println("Empréstimos por Livro (Descendente):");

        if (report.getLoansPerBook().isEmpty()) {
            System.out.println("Nenhum empréstimo foi realizado ainda.");
            return;
        }

        report.getLoansPerBook().forEach((book, count) -> {
            System.out.printf("  -> %s (ISBN: %s): %d empréstimo(s)\n",
                    book.getTitle(),
                    book.getIsbn(),
                    count);
        });
    }

    private static void handleSeedData() {
        System.out.println("\n--- 9. Carregando Dados de Teste ---");
        DataLoader.seed(userService, bookService, loanService);
    }
}