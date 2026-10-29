package ejemplos;

import java.util.*;

public class FootballPlayer {
	
	// Atributos
	private Integer dorsal;
	private String name;
	private List<String> demarcation;
	private String team;
	
	// Constructores
	public FootballPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FootballPlayer(Integer dorsal, String name, List<String> demarcation, String team) {
		super();
		this.dorsal = dorsal;
		this.name = name;
		this.demarcation = demarcation;
		this.team = team;
	}

	// Getters & Setters
	public Integer getDorsal() {
		return dorsal;
	}

	public void setDorsal(Integer dorsal) {
		this.dorsal = dorsal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDemarcation() {
		return demarcation;
	}

	public void setDemarcation(List<String> demarcation) {
		this.demarcation = demarcation;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "FootballPlayer [dorsal=" + dorsal + ", name=" + name + ", demarcation=" + demarcation + ", team=" + team + "]";
	}
	
}
