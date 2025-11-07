package br.ufrn.library;

// 1. Importa os Modelos (se necessário para criar instâncias de teste)
import br.ufrn.library.model.User;
import br.ufrn.library.repository.UserRepository;
import br.ufrn.library.repository.impl.InMemoryUserRepository;
import br.ufrn.library.service.UserService;

public class Library {

    public static void main(String[] args) {
        
        // --- FASE 1: CONFIGURAÇÃO E INJEÇÃO DE DEPENDÊNCIA ---
        // Aqui é "Em alguma classe Main ou de Configuração"
        
        System.out.println("Iniciando o sistema da biblioteca...");

        // 1. Cria os "Trabalhadores" (Repositórios - Implementações)
        // Cada um de vocês instancia o "trabalhador" que criou.
        UserRepository userRepo = new InMemoryUserRepository();
        //BookRepository bookRepo = new InMemoryBookRepository(); // Feito pelo Colega 1
        //LoanRepository loanRepo = new InMemoryLoanRepository(); // Feito pelo Colega 2

        // 2. Cria os "Cérebros" (Serviços)
        // E injeta os "trabalhadores" (via construtor) neles.
        // Note que os serviços só conhecem as INTERFACES.
        
        // O SEU serviço é criado
        UserService userService = new UserService(userRepo);
        
        // O serviço pra livros é criado
        //BookService bookService = new BookService(bookRepo);
        
        // O serviço pra empréstimos é criado (depende de todos)
        //LoanService loanService = new LoanService(loanRepo, userRepo, bookRepo);

        System.out.println("Sistema pronto. Serviços configurados.");
        
        
        // --- FASE 2: USO DA APLICAÇÃO (A UI) ---
        // A partir deste ponto, SÓ usamos as variáveis de SERVIÇO
        // (userService, bookService, loanService)
        // NUNCA mais usamos os "Repos" diretamente.
        
        System.out.println("\n--- Exemplo de Uso (UI de Console) ---");

        // --- Como seu colega usará o que você fez ---
        // Ele vai simplesmente chamar métodos no 'userService'.
        
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

        // --- Aqui o Colega 2 usaria o LoanService ---
        // (Exemplo de como ele usaria a sua parte e a do outro colega)
        /*
        try {
            // (O Colega 1 cadastraria um livro primeiro)
            // bookService.registerNewBook(...)
        
            System.out.println("Realizando empréstimo...");
            // O loanService usa o 'userService' e 'bookService' por baixo dos panos
            loanService.performLoan("12345", "978-0321125217");
            
        } catch (Exception e) {
             System.err.println("ERRO no Empréstimo: " + e.getMessage());
        }
        */
    }
}