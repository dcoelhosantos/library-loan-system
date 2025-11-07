package br.ufrn.library;

// 1. Importa os Modelos
import br.ufrn.library.model.Book;
import br.ufrn.library.model.User;

// 2. Importa as Exceções
import br.ufrn.library.exception.BookNotFoundException;

// 3. Importa os Repositórios (Interfaces e Implementações)
import br.ufrn.library.repository.BookRepository;
import br.ufrn.library.repository.UserRepository;
import br.ufrn.library.repository.impl.InMemoryBookRepository;
import br.ufrn.library.repository.impl.InMemoryUserRepository;

// 4. Importa os Serviços
import br.ufrn.library.service.BookService;
import br.ufrn.library.service.UserService;

public class Library {

    public static void main(String[] args) {
        
        // --- FASE 1: CONFIGURAÇÃO E INJEÇÃO DE DEPENDÊNCIA ---
        System.out.println("Iniciando o sistema da biblioteca...");

        // 1. Cria os "Trabalhadores" (Repositórios - Implementações)
        UserRepository userRepo = new InMemoryUserRepository();
        BookRepository bookRepo = new InMemoryBookRepository(); // <-- ATUALIZADO
        //LoanRepository loanRepo = new InMemoryLoanRepository(); // Feito pelo Colega 2

        // 2. Cria os "Cérebros" (Serviços)
        UserService userService = new UserService(userRepo);
        BookService bookService = new BookService(bookRepo); // <-- ATUALIZADO
        
        // O serviço pra empréstimos é criado (depende de todos)
        //LoanService loanService = new LoanService(loanRepo, userRepo, bookRepo);

        System.out.println("Sistema pronto. Serviços configurados.");
        
        
        // --- FASE 2: USO DA APLICAÇÃO (A UI) ---
        
        // --- Teste do UserService (código original) ---
        System.out.println("\n--- Testando UserService ---");
        try {
            System.out.println("Cadastrando usuário Joadson...");
            userService.registerUser("12345", "Joadson");
            
            System.out.println("Cadastrando usuário Paulo...");
            userService.registerUser("67890", "Paulo");

            System.out.println("Usuários cadastrados:");
            System.out.println(userService.listAllUsers());
            
            System.out.println("Buscando usuário Joadson:");
            User joadson = userService.findUserById("12345");
            System.out.println("Encontrado: " + joadson.getName());

        } catch (IllegalArgumentException e) {
            System.err.println("ERRO: " + e.getMessage());
        }

        // --- ATUALIZADO: Teste do BookService ---
        System.out.println("\n--- Testando BookService ---");
        try {
            // 1. Cadastro
            System.out.println("Cadastrando Livro Físico (ISBN 978-1)...");
            bookService.registerPhysicalBook("O Senhor dos Anéis", "J.R.R. Tolkien", "978-1", 5);

            System.out.println("Cadastrando Livro Digital (ISBN 123-456)...");
            bookService.registerDigitalBook("Duna", "Frank Herbert", "123-456");
            System.out.println("Livros cadastrados!");

            // 2. Listagem
            System.out.println("Listando todos os livros:");
            for (Book book : bookService.listAllBooks()) {
                System.out.println("  -> " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")");
            }

            // 3. Atualização
            System.out.println("Atualizando 'Duna' para 'Duna (Ed. Especial)'...");
            bookService.updateDigitalBook("123-456", "Duna (Ed. Especial)", "Frank Herbert");
            Book duna = bookService.findBookByIsbn("123-456");
            System.out.println("Busca após atualização: " + duna.getTitle());

            // 4. Teste de Erro (Duplicado)
            System.out.println("Tentando cadastrar ISBN 978-1 de novo (deve falhar)...");
            bookService.registerPhysicalBook("Outro Livro", "Outro Autor", "978-1", 1);

        } catch (BookNotFoundException | IllegalArgumentException e) {
            // Pega erros de validação (como o duplicado)
            System.err.println("ERRO (Esperado no teste de duplicado): " + e.getMessage());
        }

        // 5. Teste de Erro (Não Encontrado)
        try {
            System.out.println("Tentando buscar ISBN '999' (deve falhar)...");
            bookService.findBookByIsbn("999");
        } catch (BookNotFoundException e) {
            System.err.println("ERRO (Esperado no teste de não encontrado): " + e.getMessage());
        }

        // --- Aqui o Colega 2 usaria o LoanService ---
        /*
        try {
            System.out.println("Realizando empréstimo...");
            // O loanService usa o 'userService' e 'bookService' por baixo dos panos
            loanService.performLoan("12345", "978-1"); // Emprestando O Senhor dos Anéis para Joadson
            
        } catch (Exception e) {
             System.err.println("ERRO no Empréstimo: " + e.getMessage());
        }
        */
    }
}