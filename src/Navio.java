public class Navio {
    
    private String nome;
    private int tamanho;
    private int vida;

    public Navio(String nome, int tamanho) {

        this.nome = nome;
        this.tamanho = tamanho;
        vida = tamanho;

    }

    public Navio(String nome, String tamanho, String vida) {

        this.nome = nome;
        this.tamanho = Integer.parseInt(tamanho);
        this.vida = Integer.parseInt(vida);

    }

    public String getNome() {

        return nome;

    }

    public int getTamanho() {

        return tamanho;
    
    }

    public void atingir() {

        vida--;

        if(vida == 0) {
            System.out.println(nome + " afundou");
        }

    }

    public int getVida() {

        return vida;

    }

}
