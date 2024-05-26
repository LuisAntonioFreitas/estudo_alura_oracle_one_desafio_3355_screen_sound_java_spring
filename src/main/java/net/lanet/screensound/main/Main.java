package net.lanet.screensound.main;

import net.lanet.screensound.configs.ApplicationProperties;
import net.lanet.screensound.entity.Artista;
import net.lanet.screensound.entity.Musica;
import net.lanet.screensound.enums.SimNao;
import net.lanet.screensound.enums.TipoArtista;
import net.lanet.screensound.repository.IArtistaRepository;
import net.lanet.screensound.service.QueryGeminiAI;
import net.lanet.screensound.service.QueryOpenAI;
import net.lanet.screensound.utils.ValidEnum;
import net.lanet.screensound.utils.ValidNumber;
import net.lanet.screensound.utils.ValidString;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final IArtistaRepository repository;
    private final ApplicationProperties applicationProperties;

    Scanner scanner = new Scanner(System.in);

    public Main(IArtistaRepository repository,
                ApplicationProperties applicationProperties) {
        this.repository = repository;
        this.applicationProperties = applicationProperties;
    }

    public void viewMenu() {

        final String OPTIONS_MENU = """
                ******************************************
                *        S C R E E N    S O U N D        *
                ******************************************
                *  1  |  Cadastrar artistas              *
                *  2  |  Cadastrar músicas               *
                *  3  |  Listar artistas                 *
                *  4  |  Listar músicas                  *
                *  5  |  Buscar músicas por artista      *
                *  6  |  Pesquisar dados sobre artistas  *
                *        utilizando o Gemini IA Google   *
                *  7  |  Pesquisar dados sobre artistas  *
                *        utilizando o ChatGPT OpenAI     *
                *  8  |  Sair                            *
                ******************************************
                Escolha um número válido nas opções:""";
        final int OPTIONS_LIMIT = 8;

        Artista artista;
        int optionMenu = 0;
        while (optionMenu != OPTIONS_LIMIT) {
            System.out.println(OPTIONS_MENU);

            optionMenu = ValidNumber.getValidInteger(scanner, OPTIONS_LIMIT);

            switch (optionMenu) {
                case 1:
                    // Cadastrar artistas
                    cadastrarArtistas();
                    break;
                case 2:
                    // Cadastrar músicas
                    artista = buscarArtista();
                    cadastrarMusicas(artista);
                    break;
                case 3:
                    // Listar artistas
                    listarArtistas();
                    break;
                case 4:
                    // Listar músicas
                    listarMusicas();
                    break;
                case 5:
                    // Buscar músicas por artista
                    artista = buscarArtista();
                    buscarMusicasPorArtista(artista);
                    break;
                case 6:
                    // Pesquisar dados sobre artistas
                    // utilizando o Gemini AI Google
                    artista = buscarArtista();
                    perquisarDadosSobreArtistasGemini(artista);
                    break;
                case 7:
                    // Pesquisar dados sobre artistas
                    // utilizando o ChatGPT OpenAI
                    artista = buscarArtista();
                    perquisarDadosSobreArtistasOpenAI(artista);
                    break;
                case 8:
                    // Sair;
                    System.out.println("Aplicação finalizada!");
                    break;
                default:
                    // Opção inválida
                    System.out.println("Opção inválida!");
                    break;
            }

        }

    }

    private Artista buscarArtista() {
        System.out.println("Informe o nome de um artista cadastrado:");
        Optional<List<Artista>> artista = null;
        boolean isValid = false;
        while (!isValid) {
            String nome = ValidString.getValidString(scanner, 100);
            artista = repository.findByNomeContainingIgnoreCase(nome);
            try {
                artista.stream()
                        .filter(list -> !list.isEmpty())
                        .findFirst()
                        .get().get(0);
                if (artista.isPresent()) {
                    isValid = true;
                } else {
                    System.out.println("Artista não encontrado!");
                }
            } catch (Exception ignored) {
                System.out.println("Artista não encontrado!");
            }
        }
        System.out.println("[Artista]: " + artista.get().get(0).getNome()
                + " (" + artista.get().get(0).getTipo() + ")" );
        return artista.get().get(0);
    }

    private void cadastrarArtistas() {
        boolean cadastrarMais = true;
        while (cadastrarMais) {
            System.out.println("Informe o nome do artista:");
            String nome = ValidString.getValidString(scanner, 100);
            System.out.println("Informe o tipo do artista (solo, dupla ou banda):");
            TipoArtista tipo = ValidEnum.getValidEnum(scanner, TipoArtista.class);

            Artista artista = new Artista(nome, tipo);
            repository.save(artista);

            System.out.println("Cadastrar novo artista (Sim/Não):");
            SimNao simNao = ValidEnum.getValidEnumSimNao(scanner);
            if (String.valueOf(simNao).equalsIgnoreCase("nao")) {
                cadastrarMais = false;
            }
        }
    }

    private void cadastrarMusicas(Artista artista) {
        boolean cadastrarMais = true;
        while (cadastrarMais) {
            System.out.println("Informe o título da música:");
            String titulo = ValidString.getValidString(scanner, 100);

            Musica musica = new Musica(titulo, artista);
            artista.addMusica(musica);

            System.out.println("Cadastrar nova música (Sim/Não):");
            SimNao simNao = ValidEnum.getValidEnumSimNao(scanner);
            if (String.valueOf(simNao).equalsIgnoreCase("nao")) {
                cadastrarMais = false;
                repository.save(artista);
            }
        }
    }

    private void listarArtistas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(artista ->
                System.out.println("[Artista]: " + artista.getNome()
                        + " (" + artista.getTipo() + ")"));
    }

    private void listarMusicas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(artista ->
                System.out.println("[Artista]: " + artista.getNome()
                        + " (" + artista.getTipo() + ")"
                        + " # " + artista.getMusicas()));
    }

    private void buscarMusicasPorArtista(Artista artista) {
        artista.getMusicas().forEach(musica ->
                System.out.println("[Música]: " + musica.getTitulo()));
    }

    private void perquisarDadosSobreArtistasGemini(Artista artista) {
        QueryGeminiAI queryGeminiAI = new QueryGeminiAI();
        String result = queryGeminiAI.getInformation(artista.getNome(), applicationProperties);
        if (!result.isEmpty()) { System.out.println(result); }
    }

    private void perquisarDadosSobreArtistasOpenAI(Artista artista) {
        QueryOpenAI queryOpenAI = new QueryOpenAI();
        String result = queryOpenAI.getInformation(artista.getNome(), applicationProperties);
        if (!result.isEmpty()) { System.out.println(result); }
    }

}
