import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Save {

    private InicioDeJogo jogo;
    private static String BASE_DIR = "./BatalhaNaval/src/data";
    private static String FILE_NAME = "save.csv";

    public Save(InicioDeJogo jogo) {


        File diretorio = new File(BASE_DIR);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        this.jogo = jogo;
        salvarJogo();

    }

    public boolean salvarJogo() {

        File arquivo = new File(BASE_DIR+"/"+FILE_NAME);

        if (arquivo.exists()) {
            arquivo.delete();
        }

        try {

            arquivo.createNewFile();

            FileOutputStream fos = new FileOutputStream(arquivo);
            

            fos.write(salvarGrids().getBytes());

            fos.flush();

            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //;

        return true;

    }

    public String salvarGrids() {
        
        String linha = salvarGridAliada();
        linha = linha.concat(salvarGridInimiga());

        return linha;
    }

    public String salvarIa() {

        String linha = "";
        IA ia = jogo.getIa();

        //ia.get
        return null;

    }

    public String salvarGridAliada() {

        String linha = "";
        Grid grid = jogo.getGridAliada();
        Navio[] frota = grid.getNavios();
        Map<String, Linha> linhas = grid.getLinhas();

        for(Navio navio: frota) {
            String dadosNavio = navio.getNome()+";"+navio.getTamanho()+";"+navio.getVida()+";";
            linha = linha.concat(dadosNavio);
        }

        linha = linha.concat("\n");

        for(Entry<String, Linha> li : linhas.entrySet()) {

            for(Quadrado quadrado : li.getValue().getQuadrados()) {
                String dadosQuadrado = quadrado.getDados();
                linha = linha.concat(dadosQuadrado);
            }

        }

        linha = linha.concat("\n");

        return linha;
    }

    public String salvarGridInimiga() {

        String linha = "";
        Grid grid = jogo.getGridInimiga();
        Navio[] frota = grid.getNavios();
        Map<String, Linha> linhas = grid.getLinhas();

        for(Navio navio: frota) {
            String dadosNavio = navio.getNome()+";"+navio.getTamanho()+";"+navio.getVida()+";";
            linha = linha.concat(dadosNavio);
        }

        linha = linha.concat("\n");

        for(Entry<String, Linha> li : linhas.entrySet()) {

            for(Quadrado quadrado : li.getValue().getQuadrados()) {
                String dadosQuadrado = quadrado.getDados();
                linha = linha.concat(dadosQuadrado);
            }

        }

        linha = linha.concat("\n");

        return linha;
    }

    public static void carregarJogo(InicioDeJogo jogo) {

        File arquivo = new File(BASE_DIR+"/"+FILE_NAME);
        if (!arquivo.exists()) {
            System.out.println("Não existe dado salvo");
            return;
        }

        try {
            Scanner fileScanner = new Scanner(arquivo);

            Navio[] frotaAliada = new Navio[6];
            Map<String, Linha> linhasAliadas = new HashMap<>();
            Navio[] frotaInimiga = new Navio[6];
            Map<String, Linha> linhasInimigas = new HashMap<>();


            int i = 0;
            while(fileScanner.hasNextLine()) {

                if(i==0) {
                    System.out.println("Entrou no frota aliada");
                    frotaAliada = carregarNavios(fileScanner.nextLine());

                }

                if(i==1) {

                    linhasAliadas = carregarLinhas(fileScanner.nextLine(), frotaAliada);

                }

                if(i==2) {

                    frotaInimiga = carregarNavios(fileScanner.nextLine());

                }

                if(i==3) {

                    linhasInimigas = carregarLinhas(fileScanner.nextLine(), frotaInimiga);

                }

                i++;
            }


            Grid gridAlida = new GridPlayer(linhasAliadas, frotaAliada);
            Grid gridInimiga = new GridInimiga(linhasInimigas, frotaInimiga);

            jogo.setGrids(gridAlida, gridInimiga);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Navio[] carregarNavios(String linha) {

        String[] naviosString = linha.split(";");
        String[] navio = new String[3];
        Navio[] navios = new Navio[6];
        int contador = 0;
        for(int i = 0; i < naviosString.length; i++) {
            int x = i % 3;
            navio[x] = naviosString[i];
            if(x == 2) {
                navios[contador] = new Navio(navio[0], navio[1], navio[2]);
                contador++;
            }

        }

        return navios;

    }

    public static Map<String, Linha> carregarLinhas(String linha, Navio[] navios) {

        String[] quadradosString = linha.split(";");
        String[] quadradoString = new String[4];
        //Quadrado quadrado;
        Map<String, Linha> linhas = new HashMap<>();
        ArrayList<Quadrado> quadrados = new ArrayList<>();
        int contador = 0;
        //System.out.println("Inicio do for dos quadrados");
        //System.out.println("Tamanho do for: " + quadradosString.length);
        for(int i = 0; i < quadradosString.length; i++) {
            int q = i % 4;
            int l = i % 40;
            quadradoString[q] = quadradosString[i];
            if(q == 3) {

                Navio navio = null;

                for(Navio nav : navios) {
                    if (nav.getNome().equals(quadradoString[3])) {
                        navio = nav;
                    }
                }
                //System.out.println("Criando quadrado");
                Quadrado quadrado = new Quadrado(quadradoString[0], quadradoString[1], quadradoString[2], navio);
                quadrados.add(quadrado);
            }

            if(l == 39) {
                //System.out.println(quadrados);
                Linha linhaDoGrid = new Linha(quadrados);
                linhas.put(Converter.intToStringUp(contador), linhaDoGrid);
                quadrados = new ArrayList<>();
                contador++;

            }

        }

        return linhas;
    }
}