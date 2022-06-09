import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IA {

    private SecureRandom random = new SecureRandom();
    private boolean hunterMode;
    private Direcao direcao;
    private Navio navioAlvo;
    private Map<String, Quadrado> quadradosAlvos;
    private Map<String, Quadrado> adicionarDepois;
    private ArrayList<Quadrado> quadradosVazios;
    private ArrayList<String> removerDepois;
    private Grid grid;

    public IA(Grid grid) {

        direcao = Direcao.RANDOM;
        this.grid = grid;
        hunterMode = false;
        removerDepois = new ArrayList<>();
        quadradosVazios = new ArrayList<>();
        quadradosAlvos = new HashMap<>();
        adicionarDepois = new HashMap<>();

    }

    /*public String getDados() {

        String linha = String.format("%s;%s;%s;", 
        (hunterMode ? "true":"false"),
        (direcao == Direcao.RANDOM ? "random" : (direcao == Direcao.HORIZONTAL ? "horizontal" : "vertical")),
        (navioAlvo.getNome())
        );

    }*/

    public void atirar() {

        String linha;
        int coluna;
        Quadrado quadrado = null;

        if(hunterMode) {

            ArrayList<Quadrado> quadrados = new ArrayList<>();

            for(Entry<String, Quadrado> entry : quadradosAlvos.entrySet()) {

                quadrados.add(entry.getValue());

            }

            //System.out.println("Tamanho do quadrados: " + quadrados.size());

            //System.out.println("Caçando");
            int i = 0;
            do {
                i++;
                quadrado = quadrados.get(random.nextInt(quadrados.size()));

                for(Entry<String, Quadrado> entry : quadradosAlvos.entrySet()) {

                    if(entry.getValue().equals(quadrado)) {
                        //System.out.println(entry.getKey());
                    }
    
                }
                if(i == 15)
                    break;
            } while (!checarQuadradoVazio(quadrado));

            

            //System.out.println();

        } else {

            do {

                linha = Converter.intToString(random.nextInt(10));
                coluna = 1 + random.nextInt(10);
                quadrado = grid.getQuadrado(linha, coluna);

            } while (!checarQuadradoVazio(quadrado));

            
            

        }
        
        /*
        if(quadrado.getNavio()) {

            quadradosAlvos = grid.getAdjacentes(quadrado);
            
            adicionarAosAdjacentes(direcao, quadradosAlvos);
            

        }
        */
        atingirQuadrado(quadrado);
        quadradosVazios.add(quadrado);
        removerDepois();
        adicionarDepois();

        //quadrado.atingir();

    }

    public void adicionarDepois() {
        for(Entry<String, Quadrado> entry : adicionarDepois.entrySet()) {

            quadradosAlvos.put(entry.getKey(), entry.getValue());

        }

        adicionarDepois = new HashMap<>();
    }

    public void removerDepois() {
        for(String key : removerDepois) {
            //System.out.println("Foi removido depois: " + key);
            quadradosAlvos.remove(key);
        }
        removerDepois = new ArrayList<>();
    }

    public void atingirQuadrado(Quadrado quadrado) {

        quadrado.atingir();
        String key = null;

        if(quadrado.getNavio() && hunterMode){

            navioAlvo = quadrado.tipoNavio();

            if (navioAlvo.getVida() > 0) {

                for(Entry<String, Quadrado> entry : quadradosAlvos.entrySet()) {

                    if (entry.getValue().equals(quadrado)) {
    
                        key = entry.getKey();
                        removerDepois.add(key);
                        mudarDirecao(key);
                        removerDosAdjacentes();
                        adicionarAosAdjacentes(grid.getAdjacentes(quadrado));
    
                    }
    
                }

            } else {
                hunterMode = false;
                for(Entry<String, Quadrado> quadradoAlvo : quadradosAlvos.entrySet()) {
                    quadradosVazios.add(quadradoAlvo.getValue());
                }
                quadradosAlvos = new HashMap<>();
            }

        } else if(quadrado.getNavio()) {

            direcao = Direcao.RANDOM;
            hunterMode = true;
            adicionarAosAdjacentes(grid.getAdjacentes(quadrado));

            if (quadrado.tipoNavio().getVida() == 0) {
                for(Entry<String, Quadrado> quadradoAlvo : quadradosAlvos.entrySet()) {
                    quadradosVazios.add(quadradoAlvo.getValue());
                }
                quadradosAlvos = new HashMap<>();
                hunterMode = false;
            }


        } else if (hunterMode) {

            for(Entry<String, Quadrado> entry : quadradosAlvos.entrySet()) {

                if (entry.getValue().equals(quadrado)) {

                    removerDepois.add(entry.getKey());
                    /*if(direcao != Direcao.RANDOM) {
                        mudarDirecaoInvertido(entry.getKey());
                    }
                    removerDosAdjacentes();*/

                }

            }

        }

    }

    public void mudarDirecao(String direcao) {

        if(direcao.equals("up") || direcao.equals("down")) {
            //System.out.println("Mudou para vertical");
            this.direcao = Direcao.VERTICAL;
        } 

        if(direcao.equals("left") || direcao.equals("right")) {
            //System.out.println("Mudou para horizontal");

            this.direcao = Direcao.HORIZONTAL;
        }

    }

    public void mudarDirecaoInvertido(String direcao) {

        if(direcao.equals("up") || direcao.equals("down")) {
            //System.out.println("Mudou para horizontal");
            this.direcao = Direcao.HORIZONTAL;
        } 

        if(direcao.equals("left") || direcao.equals("right")) {
            //System.out.println("Mudou para vertical");
            this.direcao = Direcao.VERTICAL;
        }

    }

    public boolean checarQuadradoVazio(Quadrado quadrado) {


        for(Quadrado quadradoVazio : quadradosVazios) {

            if (quadradoVazio.equals(quadrado)) {
                return false;
            }

        }

        return true;

    }

    public void removerDosAdjacentes(){

        for(Entry<String, Quadrado> entry : quadradosAlvos.entrySet()) {

            if(direcao == Direcao.HORIZONTAL && (entry.getKey() == "up" || entry.getKey() == "down")) {

                //System.out.println("Foi removido: " + entry.getKey());
                removerDepois.add(entry.getKey());
                quadradosVazios.add(entry.getValue());
                
            }

            if(direcao == Direcao.VERTICAL && (entry.getKey() == "left" || entry.getKey() == "right")) {
                
                //System.out.println("Foi removido: " + entry.getKey());
                removerDepois.add(entry.getKey());
                quadradosVazios.add(entry.getValue());

            }

        }


    }

    public void adicionarAosAdjacentes(Map<String, Quadrado> quadrados) {

        for(Entry<String, Quadrado> quadradoAdjacente : quadrados.entrySet()) {

            Quadrado quadrado = quadradoAdjacente.getValue();

            if(checarQuadradoVazio(quadradoAdjacente.getValue())){

                if(direcao == Direcao.RANDOM) {

                    //System.out.println("Foi adicionado: " + quadradoAdjacente.getKey());
                    adicionarDepois.put(quadradoAdjacente.getKey(), quadrado);

                } else if (direcao == Direcao.HORIZONTAL) {

                    if(quadradoAdjacente.getKey().equals("left") || quadradoAdjacente.getKey().equals("right")) {

                        //System.out.println("Foi adicionado: " + quadradoAdjacente.getKey());
                        adicionarDepois.put(quadradoAdjacente.getKey(), quadrado);

                    } else {

                        //System.out.println("Foi removido: " + quadradoAdjacente.getKey());
                        quadradosVazios.add(quadrado);

                    }

                } else {

                    if(quadradoAdjacente.getKey().equals("up") || quadradoAdjacente.getKey().equals("down")) {

                        //System.out.println("Foi adicionado: " + quadradoAdjacente.getKey());
                        adicionarDepois.put(quadradoAdjacente.getKey(), quadrado);

                    } else {

                        //System.out.println("Foi removido: " + quadradoAdjacente.getKey());
                        quadradosVazios.add(quadrado);

                    }

                }
                
            } else {
                quadradosVazios.add(quadrado);
            }

        }

    }
    
}
