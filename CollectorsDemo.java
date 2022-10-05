import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsDemo {

    public static void main(String[] args) {
    	
    	Random rand = new Random();
    	List<Pessoa> pessoas = new ArrayList<Pessoa>();
    	
    	for(int i = 0; i<100; i++) {
    		int idade = rand.nextInt(120);
    		double altura = 0;
    		double peso = 0;
    		long dinheiro = rand.nextLong();
    		
    		boolean alturaAccepted = false;
    		while(!alturaAccepted) {
    			altura = rand.nextDouble() * 2.3;
    			
    			if(altura > 1) alturaAccepted = true;
    		}
    		
    		boolean pesoAccepted = false;
    		while(!pesoAccepted) {
    			if(rand.nextInt(100) < 3) {
    				peso = rand.nextDouble() * 400;
    			} else if(rand.nextInt(20) < 5) {
    				peso = rand.nextDouble() * 150;
    			} else {
    				peso = rand.nextDouble() * 110;	
    			}	
    			
    			if(peso > 15) pesoAccepted = true;
    		}
    		
    		String sexo = "Feminino";
    		if(rand.nextBoolean()) {
    			sexo = "Masculino";
    		}
    		
    		Pessoa pessoa = new Pessoa("Nome " + i, idade, altura, peso, sexo, dinheiro);
    		
    		if(pessoa.getImc() < 150 && pessoa.getImc() > 6) {
    			System.out.println(pessoa);
    			pessoas.add(pessoa);
    		}
    	}
    	
    	Stream<Pessoa> stream = pessoas.stream();
    	Long total = stream.collect(Collectors.counting());
    	System.out.println("Total Pessoas: " + total);
    	
    	Comparator<Pessoa> imcComparator = Comparator.comparingDouble(Pessoa::getImc);
    	
    	stream = pessoas.stream();
    	Optional<Pessoa> gorda = stream.collect(Collectors.maxBy(imcComparator));
    	
    	stream = pessoas.stream();
    	Optional<Pessoa> magra = stream.collect(Collectors.minBy(imcComparator));
    	
    	System.out.println("Gorda: " + gorda.get());
    	System.out.println("Magra: " + magra.get());
    	
    	stream = pessoas.stream();
    	Map<String, List<Pessoa>> mapa = stream.collect(Collectors.groupingBy(Pessoa::getSexo));
    	System.out.println("Feminino: " + mapa.get("Feminino"));
    	System.out.println("Masculino: " + mapa.get("Masculino"));
    	
    	stream = pessoas.stream();
    	Map<String, Map<String, List<Pessoa>>> mapa2 = stream.collect(Collectors.groupingBy(Pessoa::getSexo, Collectors.groupingBy(Pessoa::getValorImc)));
    	System.out.println("Feminino: " + mapa2.get("Feminino"));
    	System.out.println("Masculino: " + mapa2.get("Masculino"));
    	
    	stream = pessoas.stream();
    	Map<String, List<String>> mapa3 = stream.collect(Collectors.groupingBy(Pessoa::getValorImc, Collectors.mapping(Pessoa::getNome, Collectors.toList())));
    	System.out.println(mapa3);
    	
    	stream = pessoas.stream();
    	Map<String, Map<String, Long>> mapa4 = stream.collect(Collectors.groupingBy(Pessoa::getSexo, Collectors.groupingBy(Pessoa::getValorImc, Collectors.counting())));
    	System.out.println("Feminino: " + mapa4.get("Feminino"));
    	System.out.println("Masculino: " + mapa4.get("Masculino"));
    	
    	stream = pessoas.stream();
    	LinkedHashMap<String, Map<String, Long>> mapa40 = stream.collect(Collectors.groupingBy(Pessoa::getValorImc, LinkedHashMap::new, Collectors.groupingBy(Pessoa::getSexo, Collectors.counting())));
    	System.out.println(mapa40);
    	
    	stream = pessoas.stream();
    	ConcurrentMap<String, List<Pessoa>> mapa41 = stream.collect(Collectors.groupingByConcurrent(Pessoa::getSexo));
    	System.out.println("Feminino: " + mapa41.get("Feminino"));
    	System.out.println("Masculino: " + mapa41.get("Masculino"));
    	
    	stream = pessoas.stream();
    	ConcurrentMap<String, Map<String, Long>> mapa42 = stream.collect(Collectors.groupingByConcurrent(Pessoa::getSexo, Collectors.groupingBy(Pessoa::getValorImc, Collectors.counting())));
    	System.out.println("Feminino: " + mapa42.get("Feminino"));
    	System.out.println("Masculino: " + mapa42.get("Masculino"));
    	
    	stream = pessoas.stream();
    	ConcurrentSkipListMap<String, Map<String, Long>> mapa43 = stream.collect(Collectors.groupingByConcurrent(Pessoa::getValorImc, ConcurrentSkipListMap::new, Collectors.groupingBy(Pessoa::getSexo, Collectors.counting())));
    	System.out.println(mapa43);
    	
    	stream = pessoas.stream();
    	Pessoa velha = stream.collect(Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Pessoa::getIdade)), Optional::get));
    	stream = pessoas.stream();
    	Pessoa nova = stream.collect(Collectors.collectingAndThen(Collectors.minBy(Comparator.comparingInt(Pessoa::getIdade)), Optional::get));
    	System.out.println("Velha: " + velha);
    	System.out.println("Nova: " + nova);
    	
    	stream = pessoas.stream();
    	Integer idadeSum = stream.collect(Collectors.summingInt(Pessoa::getIdade));
    	System.out.println("Soma Idade: " + idadeSum);
    	
    	stream = pessoas.stream();
    	Double pesoSum = stream.collect(Collectors.summingDouble(Pessoa::getPeso));
    	System.out.println("Soma Peso: " + pesoSum);
    	
    	stream = pessoas.stream();
    	Double alturaSum = stream.collect(Collectors.summingDouble(Pessoa::getAltura));
    	System.out.println("Soma Altura: " + alturaSum);
    	
    	stream = pessoas.stream();
    	Long dinheiroSum = stream.collect(Collectors.summingLong(Pessoa::getDinheiro));
    	System.out.println("Soma Dinheiro: " + dinheiroSum);
    	
    	stream = pessoas.stream();
    	Double idadeAvg = stream.collect(Collectors.averagingInt(Pessoa::getIdade));
    	System.out.println("Média Idade: " + idadeAvg);
    	
    	stream = pessoas.stream();
    	Double pesoAvg = stream.collect(Collectors.averagingDouble(Pessoa::getPeso));
    	System.out.println("Média Peso: " + pesoAvg);
    	
    	stream = pessoas.stream();
    	Double alturaAvg = stream.collect(Collectors.averagingDouble(Pessoa::getAltura));
    	System.out.println("Média Altura: " + alturaAvg);
    	
    	stream = pessoas.stream();
    	Double dinheiroAvg = stream.collect(Collectors.averagingLong(Pessoa::getDinheiro));
    	System.out.println("Média Dinheiro: " + dinheiroAvg);
    	
    	stream = pessoas.stream();
    	Map<Boolean, List<Pessoa>> pessoasDinheiro = stream.collect(Collectors.partitioningBy((Pessoa p) -> p.getDinheiro() > dinheiroAvg ? true : false));
    	System.out.println("Ricas: " + pessoasDinheiro.get(true));
    	System.out.println("Pobres: " + pessoasDinheiro.get(false));
    	
    	stream = pessoas.stream();
    	Map<Boolean, Map<String, Long>> pessoasDinheiroImc = stream.collect(Collectors.partitioningBy(((Pessoa p) -> p.getDinheiro() > dinheiroAvg ? true : false), Collectors.groupingBy(Pessoa::getValorImc, Collectors.counting())));
    	System.out.println("Ricas: " + pessoasDinheiroImc.get(true));
    	System.out.println("Pobres: " + pessoasDinheiroImc.get(false));
    	
    	Stream<String> streamNomes = pessoas.stream().map(Pessoa::getNome);
    	System.out.println(streamNomes.collect(Collectors.joining()));
    	
    	streamNomes = pessoas.stream().map(Pessoa::getNome);
    	System.out.println(streamNomes.collect(Collectors.joining(", ")));
    	
    	streamNomes = pessoas.stream().map(Pessoa::getNome);
    	System.out.println(streamNomes.collect(Collectors.joining(", ", "Inínio >>> ", " <<< Fim")));
    	
    	stream = pessoas.stream();
    	Optional<Pessoa> rica = stream.collect(Collectors.reducing((p1, p2) -> p1.getDinheiro().compareTo(p2.getDinheiro()) > 0 ? p1:p2 ));
    	System.out.println("Maior rico: " + rica.get());
    	
    	streamNomes = pessoas.stream().map(Pessoa::getNome);
    	String maiorNome = streamNomes.collect(Collectors.reducing("", (nome1, nome2) -> nome1.compareTo(nome2) > 0 ? nome1 : nome2));
    	System.out.println("Maior Nome:" + maiorNome);
    	
    	stream = pessoas.stream();
    	Long somaDinheiro = stream.collect(Collectors.reducing(0L, Pessoa::getDinheiro, (i,j) -> i+j));
    	System.out.println("Total dinheiro: " + somaDinheiro);
    	
    	stream = pessoas.stream();
    	HashSet<Pessoa> set = stream.collect(Collectors.toCollection(HashSet::new));
    	System.out.println(set);
    	
    	stream = pessoas.stream();
    	ConcurrentMap<String, Double> mapa5 = stream.collect(Collectors.toConcurrentMap(Pessoa::getNome, Pessoa::getImc));
    	System.out.println(mapa5);
    	
    	stream = pessoas.stream();
    	ConcurrentMap<String, Integer> mapa6 = stream.collect(Collectors.toConcurrentMap(Pessoa::getValorImc, imc -> 1, (oldValue, newValue) -> newValue + oldValue));
    	System.out.println(mapa6);
    	
    	stream = pessoas.stream();
    	ConcurrentSkipListMap<String, Integer> mapa7 = stream.collect(Collectors.toConcurrentMap(Pessoa::getValorImc, imc -> 1, (oldValue, newValue) -> newValue + oldValue, ConcurrentSkipListMap::new));
    	System.out.println(mapa7);
    	
    	stream = pessoas.stream();
    	List<Pessoa> mapa8 = stream.collect(Collectors.toList());
    	System.out.println(mapa8);
    	
    	stream = pessoas.stream();
    	Set<String> mapa9 = stream.collect(Collectors.mapping(Pessoa::getValorImc, Collectors.toSet()));
    	System.out.println(mapa9);
    	
    	stream = pessoas.stream();
    	Map<String, Integer> mapa10 = stream.collect(Collectors.toMap(Pessoa::getValorImc, imc -> 1, (oldValue, newValue) -> newValue + oldValue));
    	System.out.println(mapa10);
    	
    	stream = pessoas.stream();
    	Map<String, Integer> mapa11 = stream.collect(Collectors.toMap(Pessoa::getValorImc, imc -> 1, (oldValue, newValue) -> newValue + oldValue, TreeMap::new));
    	System.out.println(mapa11);
    	
    	stream = pessoas.stream();
    	Map<String, Double> mapa12 = stream.collect(Collectors.toMap(Pessoa::getNome, Pessoa::getImc));
    	System.out.println(mapa12);
    }
    
    public static class Pessoa implements Comparable<Pessoa> {
    	
    	private Long dinheiro;
    	private String nome;
    	private Integer idade;
    	private Double altura;
    	private Double peso;
    	private String sexo;
    	
		public Pessoa(String nome, Integer idade, Double altura, Double peso, String sexo, Long dinheiro) {
			this.nome = nome;
			this.idade = idade;
			this.altura = altura;
			this.peso = peso;
			this.sexo = sexo;
			this.setDinheiro(dinheiro);
		}

		public String getNome() {
			return nome;
		}
		
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		public Integer getIdade() {
			return idade;
		}
		
		public void setIdade(Integer idade) {
			this.idade = idade;
		}
		
		public Double getAltura() {
			return altura;
		}
		
		public void setAltura(Double altura) {
			this.altura = altura;
		}
		
		public Double getPeso() {
			return peso;
		}
		
		public void setPeso(Double peso) {
			this.peso = peso;
		}
    	
    	public Double getImc() {
    		return peso/(altura*altura);
    	}
    	
    	public String getValorImc() {
    		String imc = "";
    		Double peso = getImc();
    		
    		if(peso < 16) {
    			imc = "Magreza grave";
    		} else if(peso >= 16 && peso < 17) {
    			imc = "Magreza moderada";
    		} else if(peso >= 17 && peso < 18.5) {
    			imc = "Magreza leve";
    		} else if(peso >= 18.5 && peso < 25) {
    			imc = "Saudável";
    		} else if(peso >= 25 && peso < 30) {
    			imc = "Sobrepeso";
    		} else if(peso >= 30 && peso < 35) {
    			imc = "Obesidade Grau I";
    		} else if(peso >= 35 && peso < 40) {
    			imc = "Obesidade Grau II (Severa)";
    		} else if(peso >= 40) {
    			imc = "Obesidade Grau III (Mórbida)";
    		}
    		
    		return imc;
    	}
    	
		public String getSexo() {
			return sexo;
		}

		public void setSexo(String sexo) {
			this.sexo = sexo;
		}

		public Long getDinheiro() {
			return dinheiro;
		}

		public void setDinheiro(Long dinheiro) {
			this.dinheiro = dinheiro;
		}
		
		@Override
		public String toString() {
			return "Pessoa [dinheiro=" + dinheiro + ", nome=" + nome
					+ ", idade=" + idade + ", altura=" + altura + ", peso="
					+ peso + ", sexo=" + sexo + "]" + " IMC >>> " + getImc();
		}

		@Override
		public int compareTo(Pessoa pes) {
			return getImc().compareTo(pes.getImc());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pessoa other = (Pessoa) obj;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			return true;
		}

    }
}